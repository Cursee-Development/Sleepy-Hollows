package net.satisfy.sleepy_hollows.core.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
import net.satisfy.sleepy_hollows.core.util.SoulfireSpiral;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Horseman extends Monster implements EntityWithAttackAnimation, PowerableMob {
    private static final EntityDataAccessor<Boolean> IMMUNE = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    private static final float[] HEALTH_THRESHOLDS = {0.75f, 0.50f, 0.25f};
    private static final EntityDataAccessor<Boolean> HAS_ACTIVE_PUMPKIN_HEAD = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LAUGHING = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private final ServerBossEvent bossEvent = new ServerBossEvent(Component.translatable("entity.sleepy_hollows.horseman"), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);
    private final List<ParticleArc> activeParticleArcs = new ArrayList<>();
    public AnimationState laughingAnimationState = new AnimationState();
    private int nextSummonIndex = 0;
    private int idleAnimationTimeout = 0;
    private int skeletonSpawnTimer = 25 * 20;
    private int attackCounter = 0;

    public Horseman(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.setCustomName(Component.translatable("entity.sleepy_hollows.horseman"));
        this.setCustomNameVisible(false);
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
                return 0.01f;
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
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }

        if (this.hasActivePumpkinHead() && !isPumpkinHeadAlive()) {
            this.setActivePumpkinHead(false);
        }

        if (!this.level().isClientSide() && this.hasActivePumpkinHead()) {
            this.level().broadcastEntityEvent(this, (byte) 10);
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

        float[] SOULFIRE_THRESHOLDS = getSoulfireThresholds();

        if (nextSoulfireIndex < SOULFIRE_THRESHOLDS.length && this.getHealth() <= SOULFIRE_THRESHOLDS[nextSoulfireIndex]) {
            castSoulfireSpiral();
            nextSoulfireIndex++;
        }

        if (!this.level().isClientSide()) {
            if (!activeSoulfireSpirals.isEmpty()) {
                Iterator<SoulfireSpiral> iterator = activeSoulfireSpirals.iterator();
                while (iterator.hasNext()) {
                    SoulfireSpiral spiral = iterator.next();
                    spiral.tick();
                    if (spiral.isFinished()) {
                        iterator.remove();
                    }
                }
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

        if (this.hasActivePumpkinHead()) {
            for (int i = 0; i < 3; ++i) {
                double offsetX = this.getX() + this.random.nextGaussian() * 0.3;
                double offsetY = this.getY() + 1.0 + this.random.nextGaussian() * 0.3;
                double offsetZ = this.getZ() + this.random.nextGaussian() * 0.3;
                this.level().addParticle(ParticleTypes.SMOKE, offsetX, offsetY, offsetZ, 0.0, 0.0, 0.0);

                if (this.random.nextInt(4) == 0) {
                    this.level().addParticle(ParticleTypes.ENTITY_EFFECT, offsetX, offsetY, offsetZ, 0.7, 0.7, 0.5);
                }
            }
        }

        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    private void castSoulfireSpiral() {
        SoulfireSpiral spiral = new SoulfireSpiral(this.level(), this.position());
        activeSoulfireSpirals.add(spiral);

        if (!this.level().isClientSide()) {
            this.level().playSound(null, this.blockPosition(), SoundEvents.BLAZE_BURN, SoundSource.HOSTILE, 1.0F, 1.0F);
        }
    }

    private boolean isMoving() {
        return this.getDeltaMovement().lengthSqr() > 0.01;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    private void summonPumpkinHead() {
        if (!this.hasActivePumpkinHead()) {
            this.setActivePumpkinHead(true);
        }

        Vec3 spawnPos = this.position().add(0, 1.5D, 0);

        FleeingPumpkinHead pumpkinHead = EntityTypeRegistry.FLEEING_PUMPKIN_HEAD.get().create(this.level());

        if (pumpkinHead != null) {
            pumpkinHead.setPos(spawnPos.x(), spawnPos.y(), spawnPos.z());
            pumpkinHead.setSummoner(this);
            this.level().addFreshEntity(pumpkinHead);
            this.entityData.set(IMMUNE, true);

            pumpkinHead.startFlyingAway();
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

            skeleton.setCustomName(Component.translatable("entity.sleepy_hollows.hauntbound_skeleton"));
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

        attackCounter++;

        if (attackCounter >= 7) {
            removeWaterInRadius();
            attackCounter = 0;
        }
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

    public void setActivePumpkinHead(boolean active) {
        this.entityData.set(HAS_ACTIVE_PUMPKIN_HEAD, active);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("HasActivePumpkinHead", this.hasActivePumpkinHead());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 10) {
            this.setActivePumpkinHead(true);
        } else if (id == 11) {
            this.setActivePumpkinHead(false);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (isPumpkinHeadAlive()) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void die(@NotNull DamageSource cause) {
        super.die(cause);
        for (int i = 0; i < 70; ++i) {
            double offsetX = this.getX() + (this.random.nextDouble() - 0.5) * 2.0;
            double offsetY = this.getY() + this.random.nextDouble() * 2.0;
            double offsetZ = this.getZ() + (this.random.nextDouble() - 0.5) * 2.0;
            this.level().addParticle(ParticleTypes.SMOKE, offsetX, offsetY, offsetZ, 0.0, 0.0, 0.0);
            this.level().addParticle(ParticleTypes.SOUL, offsetX, offsetY, offsetZ, 0.0, 0.0, 0.0);
        }
        if (!this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            int experienceAmount = 250;
            ExperienceOrb.award((ServerLevel) this.level(), this.position(), experienceAmount);
        }
    }

    private void removeWaterInRadius() {
        AABB area = new AABB(this.blockPosition()).inflate(10);

        BlockPos.betweenClosedStream(area).forEach(pos -> {
            if (level().getFluidState(pos).isSource()) {
                if (this.level().isClientSide()) {
                    level().levelEvent(2001, pos, Block.getId(level().getBlockState(pos)));
                }

                level().setBlock(pos, ObjectRegistry.GRAVESTONE.get().defaultBlockState(), 3);

                if (this.level().isClientSide()) {
                    Vec3 targetPos = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                    Vec3 horsemanPos = new Vec3(this.getX(), this.getY() + 1.0D, this.getZ());

                    ParticleArc arc = new ParticleArc(targetPos, horsemanPos, 40);
                    activeParticleArcs.add(arc);
                }
            }
        });
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
        return SoundEvents.SKELETON_HORSE_AMBIENT;
    }

    private final List<SoulfireSpiral> activeSoulfireSpirals = new ArrayList<>();
    private int nextSoulfireIndex = 0;

    private float[] getSoulfireThresholds() {
        float maxHealth = this.getMaxHealth();
        float[] thresholds = new float[10];
        for (int i = 0; i < 10; i++) {
            thresholds[i] = maxHealth * (1 - (i * 0.1f));
        }
        return thresholds;
    }

    @Override
    public boolean shouldDropExperience() {
        return true;
    }

    @Override
    public boolean isPowered() {
        return this.entityData.get(IMMUNE);
    }
}
