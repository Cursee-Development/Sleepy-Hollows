package net.satisfy.sleepy_hollows.core.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.satisfy.sleepy_hollows.core.entity.ai.AnimationAttackGoal;
import net.satisfy.sleepy_hollows.core.entity.ai.NearestAttackablePlayerGoal;
import net.satisfy.sleepy_hollows.core.entity.ai.RandomAction;
import net.satisfy.sleepy_hollows.core.entity.ai.RandomActionGoal;
import net.satisfy.sleepy_hollows.core.entity.animation.ServerAnimationDurations;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.core.registry.SoundEventRegistry;
import net.satisfy.sleepy_hollows.core.util.ParticleArc;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Horseman extends Monster implements EntityWithAttackAnimation {
    private static final EntityDataAccessor<Boolean> IMMUNE = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    private static final float[] HEALTH_THRESHOLDS = {0.75f, 0.50f, 0.25f};
    private static final EntityDataAccessor<Boolean> HAS_ACTIVE_PUMPKIN_HEAD = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LAUGHING = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private final ServerBossEvent bossEvent = new ServerBossEvent(Component.translatable("entity.sleepy_hollows.horseman"),
            BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);
    public AnimationState laughingAnimationState = new AnimationState();
    private int nextSummonIndex = 0;
    private int idleAnimationTimeout = 0;
    private int skeletonSpawnTimer = 25 * 20;
    private final List<ParticleArc> activeParticleArcs = new ArrayList<>();

    public Horseman(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        currentDifficulty = getCurrentServerDifficulty(world);
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(0, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(1, new AnimationAttackGoal(this, 1.0D, true, (int) (ServerAnimationDurations.horseman_attack * 20 + 2), 8));
        this.goalSelector.addGoal(1, new NearestAttackablePlayerGoal(this, 30.0D));
        this.goalSelector.addGoal(2, new RandomActionGoal(new RandomAction() {
            @Override
            public boolean isInterruptable() {
                return false;
            }

            @Override
            public void onStart() {
                setLaughing(true);
            }

            @Override
            public void onStop() {
                setLaughing(false);
            }

            @Override
            public boolean isPossible() {
                return true;
            }

            @Override
            public void onTick(int tick) {
                if (tick == 20) {
                    level().playSound(null, Horseman.this, SoundEventRegistry.HORSEMAN_LAUGH.get(), SoundSource.NEUTRAL, 1, 1);
                }
            }

            @Override
            public int duration() {
                return (int) (ServerAnimationDurations.horseman_laugh * 20);
            }

            @Override
            public float chance() {
                return 0.005f;
            }

            @Override
            public AttributeInstance getAttribute(Attribute movementSpeed) {
                return Horseman.this.getAttribute(movementSpeed);
            }
        }));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 25.0F));
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.34000000417232513)
                .add(Attributes.MAX_HEALTH, 400.0)
                .add(Attributes.ATTACK_DAMAGE, 16.0)
                .add(Attributes.ATTACK_KNOCKBACK, 0)
                .add(Attributes.ARMOR, 26.0);
    }

    public void tick() {
        super.tick();
        updateAttributesBasedOnDifficulty();

        if (this.level().isClientSide()) {
            setupAnimationStates();
        }

        if (this.isMoving()) {
            this.level().addParticle(ParticleTypes.ASH, this.getX(), this.getY() + 1.0D, this.getZ(), 0.0D, 0.0D, 0.0D);
            this.level().addParticle(ParticleTypes.WHITE_ASH, this.getX(), this.getY() + 1.0D, this.getZ(), 0.0D, 0.0D, 0.0D);
        }

        float currentHealthRatio = this.getHealth() / this.getMaxHealth();
        if (nextSummonIndex < HEALTH_THRESHOLDS.length && currentHealthRatio <= HEALTH_THRESHOLDS[nextSummonIndex]) {
            summonPumpkinHead();
            nextSummonIndex++;
        }


        if (!this.level().isClientSide()) {
            skeletonSpawnTimer--;
            if (skeletonSpawnTimer <= 0) {
                spawnArmoredSkeleton();
                skeletonSpawnTimer = 25 * 20;
            }
        }

        if (!activeParticleArcs.isEmpty()) {
            Iterator<ParticleArc> iterator = activeParticleArcs.iterator();
            while (iterator.hasNext()) {
                ParticleArc arc = iterator.next();
                arc.tick(level());
                if (arc.isFinished()) {
                    iterator.remove();
                }
            }
        }

        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    private boolean isMoving() {
        return this.getDeltaMovement().lengthSqr() > 0.01;
    }

    private void summonPumpkinHead() {
        Vec3 summonPos = this.position().add(this.random.nextGaussian() * 12, 0, this.random.nextGaussian() * 12);

        FleeingPumpkinHead pumpkinHead = EntityTypeRegistry.FLEEING_PUMPKIN_HEAD.get().create(this.level());

        if (pumpkinHead != null) {
            pumpkinHead.setPos(summonPos.x(), summonPos.y(), summonPos.z());
            pumpkinHead.setSummoner(this);
            this.level().addFreshEntity(pumpkinHead);
            this.entityData.set(IMMUNE, true);

            activeParticleArcs.add(new ParticleArc(this.position(), summonPos, 40));
        }
    }

    private void spawnArmoredSkeleton() {
        Vec3 spawnPos = this.position().add(this.random.nextGaussian() * 8, 0, this.random.nextGaussian() * 8);
        Skeleton skeleton = EntityType.SKELETON.create(this.level());

        if (skeleton != null) {
            skeleton.setPos(spawnPos.x(), spawnPos.y(), spawnPos.z());
            skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObjectRegistry.HAUNTBOUND_HELMET.get()));
            skeleton.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ObjectRegistry.HAUNTBOUND_CHESTPLATE.get()));
            skeleton.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ObjectRegistry.HAUNTBOUND_LEGGINGS.get()));
            skeleton.setItemSlot(EquipmentSlot.FEET, new ItemStack(ObjectRegistry.HAUNTBOUND_BOOTS.get()));
            skeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));

            skeleton.setCustomName(Component.literal("Hauntbound Skeleton"));
            skeleton.setCustomNameVisible(false);

            this.level().addFreshEntity(skeleton);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_ACTIVE_PUMPKIN_HEAD, false);
        this.entityData.define(ATTACKING, false);
        this.entityData.define(IMMUNE, false);
        this.entityData.define(LAUGHING, false);
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
        attackAnimationState.animateWhen(this.isAttacking(), tickCount);
        this.laughingAnimationState.animateWhen(this.isLaughing(), this.tickCount);
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking_(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    private boolean isLaughing() {
        return this.entityData.get(LAUGHING);
    }

    public void setLaughing(boolean laughing) {
        this.entityData.set(LAUGHING, laughing);
    }


    @Override
    public Vec3 getPosition_(int i) {
        return super.getPosition(i);
    }

    @Override
    public void doHurtTarget_(LivingEntity targetEntity) {
        super.doHurtTarget(targetEntity);
    }

    @Override
    public LivingEntity getTarget_() {
        return getTarget();
    }

    public double getMeleeAttackRangeSqr_(LivingEntity entity) {
        return super.getMeleeAttackRangeSqr(entity);
    }

    private boolean isPumpkinHeadAlive() {
        AABB searchArea = this.getBoundingBox().inflate(100);

        return !this.level().getEntitiesOfClass(FleeingPumpkinHead.class, searchArea, Entity::isAlive).isEmpty();
    }

    public boolean hasActivePumpkinHead() {
        return this.entityData.get(HAS_ACTIVE_PUMPKIN_HEAD);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (isPumpkinHeadAlive()) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void knockback(double strength, double xRatio, double zRatio) {
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventRegistry.HORSEMAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEventRegistry.HORSEMAN_HIT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    public enum DifficultyLevel {
        EASY,
        NORMAL,
        HARD
    }

    private static DifficultyLevel currentDifficulty = DifficultyLevel.NORMAL;

    public static void setDifficulty(DifficultyLevel difficulty) {
        currentDifficulty = difficulty;
    }

    public void updateAttributesBasedOnDifficulty() {
        AttributeInstance maxHealth = this.getAttribute(Attributes.MAX_HEALTH);
        AttributeInstance attackDamage = this.getAttribute(Attributes.ATTACK_DAMAGE);
        AttributeInstance movementSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance armor = this.getAttribute(Attributes.ARMOR);

        if (maxHealth != null) {
            switch (currentDifficulty) {
                case EASY:
                    maxHealth.setBaseValue(200.0);
                    break;
                case NORMAL:
                    maxHealth.setBaseValue(400.0);
                    break;
                case HARD:
                    maxHealth.setBaseValue(600.0);
                    break;
            }
        }

        if (attackDamage != null) {
            switch (currentDifficulty) {
                case EASY:
                    attackDamage.setBaseValue(10.0);
                    break;
                case NORMAL:
                    attackDamage.setBaseValue(16.0);
                    break;
                case HARD:
                    attackDamage.setBaseValue(22.0);
                    break;
            }
        }

        if (movementSpeed != null) {
            switch (currentDifficulty) {
                case EASY:
                    movementSpeed.setBaseValue(0.3);
                    break;
                case NORMAL:
                    movementSpeed.setBaseValue(0.34);
                    break;
                case HARD:
                    movementSpeed.setBaseValue(0.38);
                    break;
            }
        }

        if (armor != null) {
            switch (currentDifficulty) {
                case EASY:
                    armor.setBaseValue(20.0);
                    break;
                case NORMAL:
                    armor.setBaseValue(26.0);
                    break;
                case HARD:
                    armor.setBaseValue(32.0);
                    break;
            }
        }

        assert maxHealth != null;
        this.setHealth((float) maxHealth.getBaseValue());
    }

    public static DifficultyLevel getCurrentServerDifficulty(Level level) {
        return switch (level.getDifficulty()) {
            case PEACEFUL, EASY -> DifficultyLevel.EASY;
            case HARD -> DifficultyLevel.HARD;
            default -> DifficultyLevel.NORMAL;
        };
    }


}