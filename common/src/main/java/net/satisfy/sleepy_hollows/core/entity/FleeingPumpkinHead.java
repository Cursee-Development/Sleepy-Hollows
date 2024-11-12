package net.satisfy.sleepy_hollows.core.entity;

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
import net.minecraft.world.entity.MobType;
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
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.core.registry.SoundEventRegistry;
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
    private boolean isFlyingAway = false;
    private int flightDuration;
    private int flightTicks;
    private Vec3 flightStartPos;
    private Vec3 flightEndPos;
    private double flightArcHeight;

    public FleeingPumpkinHead(EntityType<? extends Monster> type, Level world) {
        super(type, world);
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

        if (this.isFlyingAway) {
            double t = (double) this.flightTicks / (double) this.flightDuration;
            if (t >= 1.0) {
                this.isFlyingAway = false;
                this.noPhysics = false;
            } else {
                double x = this.flightStartPos.x + (this.flightEndPos.x - this.flightStartPos.x) * t;
                double z = this.flightStartPos.z + (this.flightEndPos.z - this.flightStartPos.z) * t;
                double y = this.flightStartPos.y + this.flightArcHeight * Math.sin(Math.PI * t);

                Vec3 newPos = new Vec3(x, y, z);
                BlockPos currentPos = new BlockPos((int)Math.floor(newPos.x), (int)Math.floor(newPos.y), (int)Math.floor(newPos.z));

                if (!this.level().getBlockState(currentPos).isAir()) {
                    this.isFlyingAway = false;
                    this.noPhysics = false;
                    return;
                }

                if (isPositionSafe(newPos)) {
                    this.teleportTo(newPos.x, newPos.y, newPos.z);
                } else {
                    this.isFlyingAway = false;
                    this.noPhysics = false;
                    return;
                }

                this.yRotO = this.getYRot();
                this.setYRot(this.getYRot() + 10);
                this.yHeadRot = this.getYRot();
                this.yBodyRot = this.getYRot();
                this.level().addParticle(ParticleTypes.SOUL, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);
                this.flightTicks++;
            }
        } else {
            if (this.summoner != null && this.summoner.isAlive()) {
                double distanceSq = this.distanceToSqr(this.summoner);
                if (distanceSq > 75 * 75) {
                    this.getNavigation().moveTo(this.summoner, 1.0D);
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
    
    private boolean canSpawnEntityAt(BlockPos pos) {
        return this.level().isEmptyBlock(pos) && this.level().isEmptyBlock(pos.above());
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

        for (int i = 0; i < 5; i++) {
            BlockPos spawnPos = this.blockPosition().offset(
                    this.random.nextInt(5) - 2, 
                    this.random.nextInt(3) - 1, 
                    this.random.nextInt(5) - 2  
            );
            if (canSpawnEntityAt(spawnPos)) {
                Zombie zombie = EntityTypeRegistry.INFECTED_ZOMBIE.get().create(this.level());
                if (zombie != null) {
                    zombie.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, this.random.nextFloat() * 360.0F, 0.0F);
                    double speed = 0.3;
                    double angle = this.random.nextDouble() * 2 * Math.PI;
                    double vx = Math.cos(angle) * speed;
                    double vz = Math.sin(angle) * speed;
                    double vy = this.random.nextDouble() * 0.125 + 0.125;
                    zombie.setDeltaMovement(new Vec3(vx, vy, vz));
                    zombie.setNoGravity(true);
                    flyingEntities.put(zombie, 20);
                    zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObjectRegistry.SPECTRAL_JACK_O_LANTERN.get()));
                    zombie.setDropChance(EquipmentSlot.HEAD, 0.1f);
                    zombie.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ObjectRegistry.HAUNTBOUND_CHESTPLATE.get()));
                    zombie.setDropChance(EquipmentSlot.CHEST, 0.01f);
                    zombie.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ObjectRegistry.HAUNTBOUND_LEGGINGS.get()));
                    zombie.setDropChance(EquipmentSlot.LEGS, 0.01f);
                    zombie.setItemSlot(EquipmentSlot.FEET, new ItemStack(ObjectRegistry.HAUNTBOUND_BOOTS.get()));
                    zombie.setDropChance(EquipmentSlot.FEET, 0.01f);
                    ItemStack enchantedSword = new ItemStack(ObjectRegistry.SPECTRAL_WARAXE.get());
                    enchantedSword.enchant(Enchantments.SHARPNESS, 3);
                    enchantedSword.enchant(Enchantments.FIRE_ASPECT, 1);
                    zombie.setItemSlot(EquipmentSlot.MAINHAND, enchantedSword);
                    zombie.setDropChance(EquipmentSlot.MAINHAND, 0.03f);
                    if (zombie.getAttribute(Attributes.ARMOR) != null) {
                        double currentArmor = Objects.requireNonNull(zombie.getAttribute(Attributes.ARMOR)).getBaseValue();
                        Objects.requireNonNull(zombie.getAttribute(Attributes.ARMOR)).setBaseValue(Math.max(0, currentArmor - 8));
                    }
                    zombie.setCustomName(Component.translatable("entity.sleepy_hollows.hauntbound_zombie"));
                    zombie.setCustomNameVisible(false);
                    this.level().addFreshEntity(zombie);
                    this.level().addParticle(ParticleTypes.FLASH, zombie.getX(), zombie.getY() + 0.5D, zombie.getZ(), 0.0D, 0.0D, 0.0D);
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            BlockPos spawnPos = this.blockPosition().offset(
                    this.random.nextInt(5) - 2, 
                    this.random.nextInt(3) - 1, 
                    this.random.nextInt(5) - 2  
            );
            if (canSpawnEntityAt(spawnPos)) {
                Skeleton skeleton = EntityType.SKELETON.create(this.level());
                if (skeleton != null) {
                    skeleton.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, this.random.nextFloat() * 360.0F, 0.0F);
                    double speed = 0.3;
                    double angle = this.random.nextDouble() * 2 * Math.PI;
                    double vx = Math.cos(angle) * speed;
                    double vz = Math.sin(angle) * speed;
                    double vy = this.random.nextDouble() * 0.125 + 0.125;
                    skeleton.setDeltaMovement(new Vec3(vx, vy, vz));
                    skeleton.setNoGravity(true);
                    flyingEntities.put(skeleton, 20);
                    skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObjectRegistry.SPECTRAL_JACK_O_LANTERN.get()));
                    skeleton.setDropChance(EquipmentSlot.HEAD, 0.1f);
                    skeleton.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ObjectRegistry.HAUNTBOUND_CHESTPLATE.get()));
                    skeleton.setDropChance(EquipmentSlot.CHEST, 0.01f);
                    skeleton.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ObjectRegistry.HAUNTBOUND_LEGGINGS.get()));
                    skeleton.setDropChance(EquipmentSlot.LEGS, 0.01f);
                    skeleton.setItemSlot(EquipmentSlot.FEET, new ItemStack(ObjectRegistry.HAUNTBOUND_BOOTS.get()));
                    skeleton.setDropChance(EquipmentSlot.FEET, 0.01f);
                    ItemStack enchantedBow = new ItemStack(Items.BOW);
                    enchantedBow.enchant(Enchantments.POWER_ARROWS, 10);
                    skeleton.setItemSlot(EquipmentSlot.MAINHAND, enchantedBow);
                    skeleton.setDropChance(EquipmentSlot.MAINHAND, 0.01f);
                    skeleton.setCustomName(Component.translatable("entity.sleepy_hollows.hauntbound_marksman"));
                    skeleton.setCustomNameVisible(false);
                    this.level().addFreshEntity(skeleton);
                    this.level().addParticle(ParticleTypes.FLASH, skeleton.getX(), skeleton.getY() + 0.5D, skeleton.getZ(), 0.0D, 0.0D, 0.0D);
                }
            }
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
        this.flightEndPos = this.flightStartPos.add(dx, 0, dz);
        this.flightArcHeight = 5;
        this.noPhysics = true;

        this.level().playSound(null, this.blockPosition(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.HOSTILE, 1.0F, 1.0F);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
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
