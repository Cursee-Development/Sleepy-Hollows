package net.satisfy.sleepy_hollows.core.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.Mob;
import net.satisfy.sleepy_hollows.core.util.MobSpawnHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.core.registry.SoundEventRegistry;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FleeingPumpkinHead extends Monster {
    private final ServerBossEvent bossEvent = new ServerBossEvent(
            Component.translatable("entity.sleepy_hollows.fleeing_pumpkin_head"),
            BossEvent.BossBarColor.PURPLE,
            BossEvent.BossBarOverlay.PROGRESS
    );
    private boolean summonedZombiesAndSkeletonsAt75 = false;
    private boolean summonedZombiesAndSkeletonsAt25 = false;
    private boolean isFrozen = false;
    private long frozenUntil = 0;
    private boolean increasedArmor = false;
    private Horseman summoner;
    private final Map<LivingEntity, Integer> flyingEntities = new HashMap<>();
    private static final double MAX_DISTANCE = 32.0;
    private boolean isFlyingAway = false;
    private int flightDuration;
    private int flightTicks;
    private Vec3 flightStartPos;
    private Vec3 flightEndPos;
    private double flightArcHeight;

    public FleeingPumpkinHead(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.setGlowingTag(true);
        this.setCustomName(Component.translatable("entity.sleepy_hollows.fleeing_pumpkin_head"));
        this.setCustomNameVisible(true);
        this.goalSelector.addGoal(0, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(2, new AvoidPlayerGoal(this, 1.1D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 15.0F));
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, PlatformHelper.getFleeingPumpkinMovementSpeed())
                .add(Attributes.MAX_HEALTH, PlatformHelper.getFleeingPumpkinMaxHealth())
                .add(Attributes.ATTACK_DAMAGE, 0.0)
                .add(Attributes.ARMOR, PlatformHelper.getFleeingPumpkinArmor());
    }

    public Horseman getSummoner() {
        return this.summoner;
    }

    public void setSummoner(Horseman summoner) {
        this.summoner = summoner;
    }

    @Override
    public void tick() {
        super.tick();
        bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        if (this.isMoving()) {
            this.level().addParticle(ParticleTypes.SOUL, this.getX(), this.getY() + 1.0D, this.getZ(), 0.0D, 0.0D, 0.0D);
        }

        Iterator<Map.Entry<LivingEntity, Integer>> iterator = flyingEntities.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<LivingEntity, Integer> entry = iterator.next();
            LivingEntity entity = entry.getKey();
            int remainingTicks = entry.getValue();

            this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, entity.getX(), entity.getY() + 0.5D, entity.getZ(), 0.0D, 0.0D, 0.0D);

            Vec3 position = entity.position();
            BlockPos currentPos = new BlockPos((int) position.x,(int) position.y,(int) position.z);

            if (!this.level().getBlockState(currentPos).isAir()) {
                entity.setNoGravity(false);
                iterator.remove();
                continue;
            }

            remainingTicks--;
            if (remainingTicks <= 0) {
                entity.setNoGravity(false);
                iterator.remove();
            } else {
                entry.setValue(remainingTicks);
            }
        }

        if (!this.level().isClientSide && !this.isFlyingAway && this.tickCount % 20 == 0 && this.isInWall()) {
            landSafely();
        }

        if (this.isFlyingAway) {
            double t = Math.min(1.0, (double) this.flightTicks / (double) this.flightDuration);
            double x = Mth.lerp(t, this.flightStartPos.x, this.flightEndPos.x);
            double z = Mth.lerp(t, this.flightStartPos.z, this.flightEndPos.z);
            double y = Mth.lerp(t, this.flightStartPos.y, this.flightEndPos.y) + this.flightArcHeight * Math.sin(Math.PI * t);
            this.teleportTo(x, y, z);
            this.setYRot(this.getYRot() + 10);
            this.yHeadRot = this.getYRot();
            this.yBodyRot = this.getYRot();
            this.level().addParticle(ParticleTypes.SOUL, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
            if (++this.flightTicks > this.flightDuration) {
                this.isFlyingAway = false;
                this.noPhysics = false;
                landSafely();
            }
        } else {
            if (this.summoner != null && this.summoner.isAlive()) {
                double distanceSq = this.distanceToSqr(this.summoner);
                if (distanceSq > MAX_DISTANCE * MAX_DISTANCE) {
                    this.getNavigation().moveTo(this.summoner, 1.2D);
                }
            } else {
                this.summoner = null;
            }

            if (isFrozen && level().getGameTime() >= frozenUntil) {
                isFrozen = false;
            }

            if (isFrozen) {
                this.setDeltaMovement(Vec3.ZERO);
                applyTremblingEffect();
            }

            if (this.getHealth() <= this.getMaxHealth() * 0.75 && !summonedZombiesAndSkeletonsAt75) {
                summonZombiesAndSkeletons();
                summonedZombiesAndSkeletonsAt75 = true;
            }
            if (this.getHealth() <= this.getMaxHealth() * 0.25 && !summonedZombiesAndSkeletonsAt25) {
                summonZombiesAndSkeletons();
                summonedZombiesAndSkeletonsAt25 = true;
            }
            if (this.getHealth() <= this.getMaxHealth() * 0.50 && !increasedArmor) {
                increaseArmorByTenPercent();
                increasedArmor = true;
            }
        }
    }

    private boolean isPositionSafe(Vec3 pos) {
        BlockPos blockPos = new BlockPos((int)Math.floor(pos.x), (int)Math.floor(pos.y), (int)Math.floor(pos.z));
        return this.level().isEmptyBlock(blockPos) && this.level().isEmptyBlock(blockPos.above());
    }

    private void increaseArmorByTenPercent() {
        Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(
                Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).getBaseValue() * 1.10
        );
    }
    
    private boolean isMoving() {
        return this.getDeltaMovement().lengthSqr() > 0.01;
    }
    
    private void applyTremblingEffect() {
        double trembleAmount = 0.05;
        double trembleX = (Math.random() - 0.5) * trembleAmount;
        double trembleZ = (Math.random() - 0.5) * trembleAmount;
        this.setPos(this.getX() + trembleX, this.getY(), this.getZ() + trembleZ);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (isFlyingAway) {
            return false;
        }
        boolean damaged = super.hurt(source, amount);
        if (damaged) {
            freezeFor();
        }
        return damaged;
    }

    private void freezeFor() {
        isFrozen = true;
        frozenUntil = level().getGameTime() + 10;
    }

    private void summonZombiesAndSkeletons() {
        level().playSound(null, this.blockPosition(), SoundEventRegistry.FLEEING_PUMPKIN_SUMMONING.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        for (Player player : this.level().players()) {
            if (player instanceof ServerPlayer serverPlayer && serverPlayer.hasLineOfSight(this) && serverPlayer.distanceTo(this) <= 10) {
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 3));
            }
        }
        for (int i = 0; i < 7; i++) {
            BlockPos offset = this.blockPosition().offset(this.random.nextInt(5) - 2, 0, this.random.nextInt(5) - 2);
            BlockPos spawnPos = MobSpawnHelper.findGroundedSpawn(this.level(), offset, 3, 0);
            if (spawnPos == null) continue;
            Mob minion = i < 5 ? createHauntboundZombie() : createHauntboundMarksman();
            if (minion != null) {
                MobSpawnHelper.spawnAt(this.level(), minion, spawnPos);
                this.level().addParticle(ParticleTypes.FLASH, minion.getX(), minion.getY() + 0.5D, minion.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private Mob createHauntboundZombie() {
        Zombie zombie = EntityTypeRegistry.INFECTED_ZOMBIE.get().create(this.level());
        if (zombie == null) return null;
        MobSpawnHelper.equipHauntbound(zombie, new ItemStack(ObjectRegistry.SPECTRAL_JACK_O_LANTERN.get()), new ItemStack(ObjectRegistry.SPECTRAL_WARAXE.get()), 0.01F);
        zombie.setDropChance(EquipmentSlot.MAINHAND, 0.03F);
        AttributeInstance armor = zombie.getAttribute(Attributes.ARMOR);
        if (armor != null) armor.setBaseValue(Math.max(0, armor.getBaseValue() - 8));
        zombie.setCustomName(Component.translatable("entity.sleepy_hollows.hauntbound_zombie"));
        zombie.setCustomNameVisible(false);
        return zombie;
    }

    private Mob createHauntboundMarksman() {
        Skeleton skeleton = EntityType.SKELETON.create(this.level());
        if (skeleton == null) return null;
        MobSpawnHelper.equipHauntbound(skeleton, new ItemStack(ObjectRegistry.SPECTRAL_JACK_O_LANTERN.get()), new ItemStack(Items.BOW), 0.01F);
        skeleton.setCustomName(Component.translatable("entity.sleepy_hollows.hauntbound_marksman"));
        skeleton.setCustomNameVisible(false);
        return skeleton;
    }

    private void landSafely() {
        BlockPos ground = MobSpawnHelper.findGroundedSpawn(this.level(), this.blockPosition(), 8, 3);
        if (ground != null) {
            this.teleportTo(ground.getX() + 0.5, ground.getY(), ground.getZ() + 0.5);
        }
    }

    public void startFlyingAway() {
        this.isFlyingAway = true;
        this.flightDuration = 60;
        this.flightTicks = 0;
        this.flightStartPos = this.position();
        double angle = this.random.nextDouble() * 2 * Math.PI;
        double distance = 15;
        double dx = Math.cos(angle) * distance;
        double dz = Math.sin(angle) * distance;
        BlockPos endGround = MobSpawnHelper.findGroundedSpawn(this.level(), BlockPos.containing(this.flightStartPos.add(dx, 0, dz)), 6, 2);
        this.flightEndPos = endGround != null ? Vec3.atBottomCenterOf(endGround) : this.flightStartPos;
        this.flightArcHeight = 5 + Math.abs(this.flightEndPos.y - this.flightStartPos.y);
        this.noPhysics = true;

        this.level().playSound(null, this.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.HOSTILE, 1.0F, 1.0F);
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossEvent.removePlayer(player);
    }

    @Override
    public void die(@NotNull DamageSource cause) {
        super.die(cause);
        bossEvent.removeAllPlayers();
        spawnFlashParticles();
    }
    
    private void spawnFlashParticles() {
        for (int i = 0; i < 60; i++) {
            double xOffset = (Math.random() - 0.5) * 2.0;
            double yOffset = Math.random() * 2.0;
            double zOffset = (Math.random() - 0.5) * 2.0;

            this.level().addParticle(ParticleTypes.FLASH, this.getX() + xOffset, this.getY() + yOffset, this.getZ() + zOffset, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventRegistry.FLEEING_PUMPKIN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEventRegistry.FLEEING_PUMPKIN_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEventRegistry.FLEEING_PUMPKIN_AMBIENT.get();
    }

    @Override
    public boolean shouldDropExperience() {
        return false;
    }
    
    public static class AvoidPlayerGoal extends Goal {
        private final FleeingPumpkinHead entity;
        private final double speed;

        public AvoidPlayerGoal(FleeingPumpkinHead entity, double speed) {
            this.entity = entity;
            this.speed = speed;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            Player nearestPlayer = entity.level().getNearestPlayer(entity, 10);
            return nearestPlayer != null && !entity.isFrozen;
        }

        @Override
        public void start() {
            Player nearestPlayer = entity.level().getNearestPlayer(entity, 10);
            if (nearestPlayer != null) {
                Vec3 awayVector = entity.position().subtract(nearestPlayer.position()).normalize();
                Vec3 targetPos = entity.position().add(awayVector.scale(10));
                if (entity.summoner != null) {
                    Vec3 leash = targetPos.subtract(entity.summoner.position());
                    if (leash.horizontalDistance() > MAX_DISTANCE - 4) {
                        targetPos = entity.summoner.position().add(leash.normalize().scale(MAX_DISTANCE - 4));
                    }
                }
                if (entity.isPositionSafe(targetPos)) {
                    entity.getNavigation().moveTo(targetPos.x, targetPos.y, targetPos.z, speed);
                } else {
                    entity.getNavigation().moveTo(
                            entity.getX() + awayVector.x * 5,
                            entity.getY(),
                            entity.getZ() + awayVector.z * 5,
                            speed
                    );
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            Player nearestPlayer = entity.level().getNearestPlayer(entity, 10);
            return nearestPlayer != null && !entity.isFrozen;
        }
    }
}
