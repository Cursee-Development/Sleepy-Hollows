package net.satisfy.sleepy_hollows.core.entity;

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
import net.minecraft.world.entity.*;
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
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.core.registry.SoundEventRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FleeingPumpkinHead extends Monster {

    
    private static final String ENTITY_NAME_KEY = "entity.sleepy_hollows.fleeing_pumpkin_head";
    private static final double MOVEMENT_SPEED = 0.43;
    private static final double MAX_HEALTH = 100.0;
    private static final double ATTACK_DAMAGE = 0.0;
    private static final double BASE_ARMOR = 18.0;
    private static final double ARMOR_INCREASE_FACTOR = 1.10;
    private static final double HEALTH_THRESHOLD_75 = 0.75;
    private static final double HEALTH_THRESHOLD_50 = 0.50;
    private static final double HEALTH_THRESHOLD_25 = 0.25;
    private static final int FREEZE_DURATION_TICKS = 20;
    private static final int FLYING_ENTITY_DURATION = 20;
    private static final int FLIGHT_DURATION_TICKS = 40;
    private static final double FLIGHT_ARC_HEIGHT = 5.0;
    private static final double TREMBLE_AMOUNT = 0.05;
    private static final double AVOID_PLAYER_RADIUS = 10.0;
    private static final double SUMMON_RADIUS = 20.0;
    private static final int PARTICLE_COUNT = 50;

    private final ServerBossEvent bossEvent = new ServerBossEvent(
            Component.translatable(ENTITY_NAME_KEY), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);

    private boolean summonedAt75 = false;
    private boolean summonedAt25 = false;
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
        this.setCustomName(Component.translatable(ENTITY_NAME_KEY));
        this.setCustomNameVisible(false);

        
        this.goalSelector.addGoal(0, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(2, new AvoidPlayerGoal(this, 1.1D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 15.0F));
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE)
                .add(Attributes.ARMOR, BASE_ARMOR);
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

        processFlyingEntities();

        if (this.isFlyingAway) {
            handleFlyingAway();
        } else {
            handleBehavior();
        }
    }

    private void processFlyingEntities() {
        Iterator<Map.Entry<LivingEntity, Integer>> iterator = flyingEntities.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<LivingEntity, Integer> entry = iterator.next();
            LivingEntity entity = entry.getKey();
            int flightTicks = entry.getValue();

            
            this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, entity.getX(), entity.getY() + 0.5D, entity.getZ(), 0.0D, 0.0D, 0.0D);

            flightTicks--;
            if (flightTicks <= 0) {
                entity.setNoGravity(false);
                iterator.remove();
            } else {
                entry.setValue(flightTicks);
            }
        }
    }

    private void handleFlyingAway() {
        double flightProgress = (double) this.flightTicks / (double) this.flightDuration;

        if (flightProgress >= 1.0) {
            this.isFlyingAway = false;
            this.noPhysics = false;
        } else {
            
            double x = this.flightStartPos.x + (this.flightEndPos.x - this.flightStartPos.x) * flightProgress;
            double z = this.flightStartPos.z + (this.flightEndPos.z - this.flightStartPos.z) * flightProgress;
            double y = this.flightStartPos.y + this.flightArcHeight * Math.sin(Math.PI * flightProgress);

            this.setPos(x, y, z);
            this.yRotO = this.getYRot();
            this.setYRot(this.getYRot() + 10);
            this.yHeadRot = this.getYRot();
            this.yBodyRot = this.getYRot();

            
            this.level().addParticle(ParticleTypes.SOUL, this.getX(), this.getY() + 0.5D, this.getZ(), 0.0D, 0.0D, 0.0D);

            this.flightTicks++;
        }
    }

    private void handleBehavior() {
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

        double healthRatio = this.getHealth() / this.getMaxHealth();

        if (healthRatio <= HEALTH_THRESHOLD_75 && !summonedAt75) {
            summonEntities();
            summonedAt75 = true;
        }
        if (healthRatio <= HEALTH_THRESHOLD_25 && !summonedAt25) {
            summonEntities();
            summonedAt25 = true;
        }
        if (healthRatio <= HEALTH_THRESHOLD_50 && !increasedArmor) {
            increaseArmor();
            increasedArmor = true;
        }
    }

    private void increaseArmor() {
        Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(
                Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).getBaseValue() * ARMOR_INCREASE_FACTOR
        );
    }

    private boolean isMoving() {
        return this.getDeltaMovement().lengthSqr() > 0.01;
    }

    private void applyTremblingEffect() {
        double trembleX = (Math.random() - 0.5) * TREMBLE_AMOUNT;
        double trembleZ = (Math.random() - 0.5) * TREMBLE_AMOUNT;
        this.setPos(this.getX() + trembleX, this.getY(), this.getZ() + trembleZ);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (isFlyingAway) {
            return false;
        }
        boolean damaged = super.hurt(source, amount);
        if (damaged) {
            freezeEntity();
        }
        return damaged;
    }

    private void freezeEntity() {
        isFrozen = true;
        frozenUntil = level().getGameTime() + FREEZE_DURATION_TICKS;
    }

    private void summonEntities() {
        level().playSound(null, this.blockPosition(), SoundEventRegistry.FLEEING_PUMPKIN_SUMMONING.get(), SoundSource.HOSTILE, 1.0F, 1.0F);

        
        for (Player player : this.level().players()) {
            if (player instanceof ServerPlayer serverPlayer && serverPlayer.hasLineOfSight(this) && serverPlayer.distanceTo(this) <= SUMMON_RADIUS) {
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30, 3));
            }
        }

        
        for (int i = 0; i < 5; i++) {
            summonZombie();
        }

        
        for (int i = 0; i < 2; i++) {
            summonSkeleton();
        }
    }

    private void summonZombie() {
        Zombie zombie = EntityTypeRegistry.INFECTED_ZOMBIE.get().create(this.level());
        if (zombie != null) {
            setupSummonedEntity(zombie, "entity.sleepy_hollows.hauntbound_zombie");
            equipZombie(zombie);
            this.level().addFreshEntity(zombie);
            spawnSummonParticle(zombie);
        }
    }

    private void equipZombie(Zombie zombie) {
        
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
            Objects.requireNonNull(zombie.getAttribute(Attributes.ARMOR)).setBaseValue(Math.max(0, currentArmor - 10));
        }
    }

    private void summonSkeleton() {
        Skeleton skeleton = EntityType.SKELETON.create(this.level());
        if (skeleton != null) {
            setupSummonedEntity(skeleton, "entity.sleepy_hollows.hauntbound_marksman");
            equipSkeleton(skeleton);
            this.level().addFreshEntity(skeleton);
            spawnSummonParticle(skeleton);
        }
    }

    private void equipSkeleton(Skeleton skeleton) {
        
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
    }

    private void setupSummonedEntity(Mob entity, String nameKey) {
        entity.setPos(this.getX(), this.getY(), this.getZ());

        
        double speed = 0.3;
        double angle = this.random.nextDouble() * 2 * Math.PI;
        double vx = Math.cos(angle) * speed;
        double vz = Math.sin(angle) * speed;
        double vy = this.random.nextDouble() * 0.125 + 0.125;

        entity.setDeltaMovement(new Vec3(vx, vy, vz));
        entity.setNoGravity(true);
        flyingEntities.put(entity, FLYING_ENTITY_DURATION);

        entity.setCustomName(Component.translatable(nameKey));
        entity.setCustomNameVisible(false);
    }

    private void spawnSummonParticle(Entity entity) {
        this.level().addParticle(ParticleTypes.FLASH, entity.getX(), entity.getY() + 0.5D, entity.getZ(), 0.0D, 0.0D, 0.0D);
    }

    public void startFlyingAway() {
        this.isFlyingAway = true;
        this.flightDuration = FLIGHT_DURATION_TICKS;
        this.flightTicks = 0;
        this.flightStartPos = this.position();

        double angle = this.random.nextDouble() * 2 * Math.PI;
        double distance = 12;
        double dx = Math.cos(angle) * distance;
        double dz = Math.sin(angle) * distance;
        this.flightEndPos = this.flightStartPos.add(dx, 0, dz);
        this.flightArcHeight = FLIGHT_ARC_HEIGHT;
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
        spawnFireworkExplosionParticles();

        if (this.summoner != null) {
            this.summoner.setImmune(false);
        }
    }

    private void spawnFireworkExplosionParticles() {
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            double xOffset = (Math.random() - 0.5) * 2.0;
            double yOffset = Math.random() * 2.0;
            double zOffset = (Math.random() - 0.5) * 2.0;

            this.level().addParticle(ParticleTypes.FLASH, this.getX() + xOffset, this.getY() + yOffset, this.getZ() + zOffset, 0.0, 0.0, 0.0);
        }
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
            Player nearestPlayer = entity.level().getNearestPlayer(entity, AVOID_PLAYER_RADIUS);
            return nearestPlayer != null && !entity.isFrozen;
        }

        @Override
        public void start() {
            Player nearestPlayer = entity.level().getNearestPlayer(entity, AVOID_PLAYER_RADIUS);
            if (nearestPlayer != null) {
                Vec3 awayVector = entity.position().subtract(nearestPlayer.position()).normalize();
                entity.getNavigation().moveTo(entity.getX() + awayVector.x * 10, entity.getY(), entity.getZ() + awayVector.z * 10, speed);
            }
        }

        @Override
        public boolean canContinueToUse() {
            Player nearestPlayer = entity.level().getNearestPlayer(entity, AVOID_PLAYER_RADIUS);
            return nearestPlayer != null && !entity.isFrozen;
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
}
