package net.satisfy.sleepy_hollows.core.entity;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.foundation.overlay.BlockNotice;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.resources.ResourceLocation;
import java.util.UUID;
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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.satisfy.foundation.entity.ai.AnimationAttackGoal;
import net.satisfy.foundation.entity.ai.AttackAnimationMob;
import net.satisfy.foundation.entity.ai.RandomAction;
import net.satisfy.foundation.entity.ai.RandomActionGoal;
import net.satisfy.sleepy_hollows.core.entity.animation.ServerAnimationDurations;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.core.registry.SoundEventRegistry;
import net.satisfy.sleepy_hollows.core.util.MobSpawnHelper;
import net.satisfy.sleepy_hollows.core.util.ParticleArc;
import net.satisfy.sleepy_hollows.core.util.SoulfireSpiral;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Horseman extends Monster implements AttackAnimationMob, RandomAction, PowerableMob {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int PHASE_ONE = 0;
    private static final int FIRST_HEAD = 1;
    private static final int PHASE_TWO = 2;
    private static final int SECOND_HEAD = 3;
    private static final int RAGE = 4;
    private static final int SPIRAL_WARNING_TICKS = 30;
    private static final int HEAD_LOST_TIMEOUT = 200;
    private static final ResourceLocation RAGE_SPEED = SleepyHollows.identifier("horseman_rage");

    private static final EntityDataAccessor<Boolean> HAS_ACTIVE_PUMPKIN_HEAD = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IMMUNE = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LAUGHING = SynchedEntityData.defineId(Horseman.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    private final ServerBossEvent bossEvent = new ServerBossEvent(Component.translatable("entity.sleepy_hollows.horseman"), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);
    private final List<ParticleArc> activeParticleArcs = new ArrayList<>();
    public AnimationState laughingAnimationState = new AnimationState();
    private int phase = PHASE_ONE;
    private final List<LaughEcho> laughEchoes = new ArrayList<>();
    private UUID headId;
    private int headMissingTicks;
    private int spiralCooldown = 200;
    private int spiralWarning = -1;
    private int skeletonCooldown;
    private int idleAnimationTimeout = 0;
    private int attackCounter = 0;

    public Horseman(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.setCustomName(Component.translatable("entity.sleepy_hollows.horseman"));
        this.setCustomNameVisible(true);
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(0, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(0, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(1, new AnimationAttackGoal(this, 1.0D, true, (int) (ServerAnimationDurations.horseman_attack * 20 + 2), 8));
        this.goalSelector.addGoal(2, new RandomActionGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 25.0F));
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, PlatformHelper.getHorsemanMovementSpeed())
                .add(Attributes.MAX_HEALTH, PlatformHelper.getHorsemanMaxHealth())
                .add(Attributes.ATTACK_DAMAGE, PlatformHelper.getHorsemanAttackDamage())
                .add(Attributes.ATTACK_KNOCKBACK, PlatformHelper.getHorsemanAttackKnockback())
                .add(Attributes.ARMOR, PlatformHelper.getHorsemanArmor());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
            tickParticleArcs();
            if (this.isMoving()) {
                this.level().addParticle(ParticleTypes.ASH, this.getX(), this.getY() + 1.0D, this.getZ(), 0.0D, 0.0D, 0.0D);
                this.level().addParticle(ParticleTypes.WHITE_ASH, this.getX(), this.getY() + 1.0D, this.getZ(), 0.0D, 0.0D, 0.0D);
            }
            if (this.isPowered()) {
                for (int i = 0; i < 3; ++i) {
                    this.level().addParticle(ParticleTypes.SMOKE, this.getX() + this.random.nextGaussian() * 0.3, this.getY() + 1.0 + this.random.nextGaussian() * 0.3, this.getZ() + this.random.nextGaussian() * 0.3, 0.0, 0.0, 0.0);
                }
            }
            return;
        }

        float health = this.getHealth() / this.getMaxHealth();
        if (phase == PHASE_ONE && health <= 0.66F) {
            startHeadPhase(FIRST_HEAD);
        } else if (phase == PHASE_TWO && health <= 0.33F) {
            startHeadPhase(SECOND_HEAD);
        }

        if (phase == FIRST_HEAD || phase == SECOND_HEAD) {
            tickHeadPhase();
        } else {
            tickSpirals();
        }

        laughEchoes.removeIf(echo -> {
            if (--echo.delay > 0) return false;
            this.level().playSound(null, this.getX() + echo.dx, this.getY() + 2, this.getZ() + echo.dz, SoundEventRegistry.HORSEMAN_LAUGH.get(), SoundSource.HOSTILE, echo.volume, echo.pitch);
            return true;
        });
        activeSoulfireSpirals.removeIf(spiral -> {
            spiral.tick();
            return spiral.isFinished();
        });
        this.bossEvent.setProgress(health);
    }

    private void tickParticleArcs() {
        activeParticleArcs.removeIf(arc -> {
            arc.tick(level());
            return arc.isFinished();
        });
    }

    private void startHeadPhase(int headPhase) {
        phase = headPhase;
        skeletonCooldown = 60;
        headMissingTicks = 0;
        spiralWarning = -1;
        setImmune(true);
        setActivePumpkinHead(true);
        announce("message.sleepy_hollows.horseman.head_flees");
        BlockPos base = MobSpawnHelper.findGroundedSpawn(level(), blockPosition(), 4, 3);
        if (base == null) base = blockPosition().above();
        FleeingPumpkinHead head = EntityTypeRegistry.FLEEING_PUMPKIN_HEAD.get().create(level());
        if (head != null) {
            head.setPos(base.getX() + 0.5, base.getY(), base.getZ() + 0.5);
            head.setSummoner(this);
            level().addFreshEntity(head);
            head.startFlyingAway();
            headId = head.getUUID();
        }
    }

    private void tickHeadPhase() {
        Entity head = headId == null ? null : ((ServerLevel) level()).getEntity(headId);
        if (head instanceof FleeingPumpkinHead pumpkinHead && pumpkinHead.getSummoner() == null) {
            pumpkinHead.setSummoner(this);
        }
        headMissingTicks = head == null ? headMissingTicks + 1 : 0;
        if ((head != null && !head.isAlive()) || headMissingTicks > HEAD_LOST_TIMEOUT) {
            endHeadPhase();
            return;
        }
        if (--skeletonCooldown <= 0) {
            int count = phase == SECOND_HEAD ? 2 : 1;
            for (int i = 0; i < count; i++) {
                spawnArmoredSkeleton();
            }
            skeletonCooldown = 200;
        }
    }

    private void endHeadPhase() {
        this.addEffect(new MobEffectInstance(MobEffects.GLOWING, 160, 0, false, false));
        headId = null;
        setImmune(false);
        setActivePumpkinHead(false);
        phase = phase == FIRST_HEAD ? PHASE_TWO : RAGE;
        spiralCooldown = 80;
        if (phase == RAGE) {
            applyRage();
            announce("message.sleepy_hollows.horseman.rage");
        } else {
            announce("message.sleepy_hollows.horseman.head_returns");
        }
    }

    private void applyRage() {
        AttributeInstance speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed != null && !speed.hasModifier(RAGE_SPEED)) {
            speed.addPermanentModifier(new AttributeModifier(RAGE_SPEED, 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    }

    private void tickSpirals() {
        if (spiralWarning >= 0) {
            if (this.level() instanceof ServerLevel serverLevel) {
                double angle = spiralWarning * 0.6;
                serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, getX() + Math.cos(angle) * 2.5, getY() + 0.1, getZ() + Math.sin(angle) * 2.5, 2, 0.1, 0.0, 0.1, 0.01);
            }
            if (spiralWarning-- == 0) {
                castSoulfireSpiral();
                spiralCooldown = switch (phase) {
                    case PHASE_ONE -> 300;
                    case PHASE_TWO -> 220;
                    default -> 160;
                };
            }
        } else if (getTarget() != null && --spiralCooldown <= 0) {
            spiralWarning = SPIRAL_WARNING_TICKS;
            this.level().playSound(null, blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 2.0F, 0.5F);
            announce("message.sleepy_hollows.horseman.soulfire");
        }
    }

    private void announce(String key) {
        Component message = Component.translatable(key);
        for (ServerPlayer player : ((ServerLevel) level()).getPlayers(player -> player.distanceToSqr(this) < 48 * 48)) {
            BlockNotice.send(player, blockPosition().above(2), message);
        }
    }

    private void setImmune(boolean immune) {
        this.entityData.set(IMMUNE, immune);
    }

    private void castSoulfireSpiral() {
        if (this.level() instanceof ServerLevel serverLevel) activeSoulfireSpirals.add(new SoulfireSpiral(serverLevel, this.position()));
        this.level().playSound(null, this.blockPosition(), SoundEvents.BLAZE_BURN, SoundSource.HOSTILE, 1.0F, 1.0F);
    }

    private boolean isMoving() {
        return this.getDeltaMovement().lengthSqr() > 0.01;
    }

    private void spawnArmoredSkeleton() {
        int x = (int) Math.floor(getX() + random.nextGaussian() * 8);
        int z = (int) Math.floor(getZ() + random.nextGaussian() * 8);
        BlockPos pos = new BlockPos(x, level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z), z);
        if (!MobSpawnHelper.canSpawnAt(level(), pos)) return;
        Skeleton skeleton = EntityType.SKELETON.create(level());
        if (skeleton != null) {
            MobSpawnHelper.equipHauntbound(skeleton, new ItemStack(ObjectRegistry.HAUNTBOUND_HELMET.get()), new ItemStack(Items.BOW), 0.085F);
            skeleton.setCustomName(Component.translatable("entity.sleepy_hollows.hauntbound_skeleton"));
            skeleton.setCustomNameVisible(false);
            MobSpawnHelper.spawnAt(level(), skeleton, pos);
        }
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

    @Override
    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    private boolean isLaughing() {
        return this.entityData.get(LAUGHING);
    }

    public void setLaughing(boolean laughing) {
        this.entityData.set(LAUGHING, laughing);
    }

    @Override
    public void performAttack(LivingEntity targetEntity) {
        super.doHurtTarget(targetEntity);

        attackCounter++;

        if (attackCounter >= 7) {
            removeWaterInRadius();
            attackCounter = 0;
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HAS_ACTIVE_PUMPKIN_HEAD, false);
        builder.define(ATTACKING, false);
        builder.define(IMMUNE, false);
        builder.define(LAUGHING, false);
    }

    @Override
    public LivingEntity getAttackTarget() {
        return getTarget();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }

    @Override
    public void onStart() {
        this.setLaughing(true);
    }

    @Override
    public void onStop() {
        this.setLaughing(false);
    }

    @Override
    public boolean isPossible() {
        return true;
    }

    @Override
    public void onTick(int tick) {
        if (tick == 2) {
            float pitch = 0.85F + this.random.nextFloat() * 0.3F;
            this.level().playSound(null, this, SoundEventRegistry.HORSEMAN_LAUGH.get(), SoundSource.HOSTILE, 1.5F, pitch);
            int delay = 0;
            for (int i = 1; i <= 2 + this.random.nextInt(2); i++) {
                delay += 8 + this.random.nextInt(6);
                laughEchoes.add(new LaughEcho(delay, (float) Math.pow(0.45, i) * 1.5F, pitch - 0.03F * i, (this.random.nextDouble() - 0.5) * 16, (this.random.nextDouble() - 0.5) * 16));
            }
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

    public boolean hasActivePumpkinHead() {
        return this.entityData.get(HAS_ACTIVE_PUMPKIN_HEAD);
    }

    public void setActivePumpkinHead(boolean active) {
        this.entityData.set(HAS_ACTIVE_PUMPKIN_HEAD, active);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Phase", this.phase);
        tag.putInt("SpiralCooldown", this.spiralCooldown);
        if (this.headId != null) {
            tag.putUUID("Head", this.headId);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.phase = tag.getInt("Phase");
        this.spiralCooldown = tag.getInt("SpiralCooldown");
        this.headId = tag.hasUUID("Head") ? tag.getUUID("Head") : null;
        boolean headPhase = this.phase == FIRST_HEAD || this.phase == SECOND_HEAD;
        setImmune(headPhase);
        setActivePumpkinHead(headPhase);
        if (this.phase == RAGE) {
            applyRage();
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.isPowered() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
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
            this.spawnAtLocation(new ItemStack(ObjectRegistry.REINS_OF_THE_SPECTRAL_HORSE.get()));
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

    @Override
    public boolean shouldDropExperience() {
        return true;
    }

    @Override
    public boolean isPowered() {
        return this.entityData.get(IMMUNE);
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        for (String entry : PlatformHelper.getHorsemanLootEntries()) {
            String[] parts = entry.split(":");
            ResourceLocation id = parts.length >= 2 ? ResourceLocation.tryBuild(parts[0], parts[1]) : null;
            Item item = id == null ? null : BuiltInRegistries.ITEM.getOptional(id).orElse(null);
            if (item == null) {
                LOGGER.warn("Ignoring invalid Horseman loot entry '{}'", entry);
                continue;
            }
            int count = 1;
            if (parts.length >= 3) {
                try {
                    count = Math.max(1, Integer.parseInt(parts[2]));
                } catch (NumberFormatException ignored) {
                    LOGGER.warn("Invalid count in Horseman loot entry '{}'", entry);
                }
            }
            this.spawnAtLocation(new ItemStack(item, count));
        }
    }

    private static final class LaughEcho {
        private int delay;
        private final float volume;
        private final float pitch;
        private final double dx;
        private final double dz;

        private LaughEcho(int delay, float volume, float pitch, double dx, double dz) {
            this.delay = delay;
            this.volume = volume;
            this.pitch = pitch;
            this.dx = dx;
            this.dz = dz;
        }
    }
}
