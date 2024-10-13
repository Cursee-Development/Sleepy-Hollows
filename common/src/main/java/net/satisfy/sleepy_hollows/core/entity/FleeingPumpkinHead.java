package net.satisfy.sleepy_hollows.core.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
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
import net.minecraft.world.item.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.core.registry.SoundEventRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Objects;

public class FleeingPumpkinHead extends Monster {
    private final ServerBossEvent bossEvent = new ServerBossEvent(Component.translatable("entity.sleepy_hollows.fleeing_pumpkin_head"),
            BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);


    private boolean isFrozen = false;
    private long frozenUntil = 0;
    private boolean summonedZombiesAndSkeletons = false;
    private boolean increasedArmor = false;

    public FleeingPumpkinHead(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.goalSelector.addGoal(0, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(2, new AvoidPlayerGoal(this, 1.1D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 15.0F));

    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.43000000417232513)
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.ATTACK_DAMAGE, 0.0)
                .add(Attributes.ARMOR, 12.0);
    }

    @Override
    public void tick() {
        super.tick();
        bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        if (isFrozen && level().getGameTime() >= frozenUntil) {
            isFrozen = false;
        }

        if (isFrozen) {
            this.setDeltaMovement(Vec3.ZERO);
            applyTremblingEffect();
        }

        if (this.getHealth() <= this.getMaxHealth() * 0.5 && !summonedZombiesAndSkeletons) {
            summonZombiesAndSkeletons();
            summonedZombiesAndSkeletons = true;
        }

        if (this.getHealth() <= this.getMaxHealth() * 0.25 && !increasedArmor) {
            increaseArmor();
            increasedArmor = true;
        }
    }

    private void applyTremblingEffect() {
        double trembleAmount = 0.05;
        double trembleX = (Math.random() - 0.5) * trembleAmount;
        double trembleZ = (Math.random() - 0.5) * trembleAmount;
        this.setPos(this.getX() + trembleX, this.getY(), this.getZ() + trembleZ);
    }


    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean damaged = super.hurt(source, amount);
        if (damaged) {
            freezeFor();
        }
        return damaged;
    }

    private void freezeFor() {
        isFrozen = true;
        frozenUntil = level().getGameTime() + 60;
    }

    private void summonZombiesAndSkeletons() {
        level().playSound(null, this.blockPosition(), SoundEventRegistry.FLEEING_PUMPKIN_SUMMONING.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
        for (int i = 0; i < 5; i++) {
            Zombie zombie = EntityTypeRegistry.INFECTED_ZOMBIE.get().create(this.level());
            if (zombie != null) {
                double xPos = this.getX() + randomPosition();
                double zPos = this.getZ() + randomPosition();
                zombie.setPos(xPos, this.getY(), zPos);

                zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObjectRegistry.SPECTRAL_JACK_O_LANTERN.get()));
                zombie.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ObjectRegistry.HAUNTBOUND_CHESTPLATE.get()));
                zombie.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ObjectRegistry.HAUNTBOUND_LEGGINGS.get()));
                zombie.setItemSlot(EquipmentSlot.FEET, new ItemStack(ObjectRegistry.HAUNTBOUND_BOOTS.get()));
                ItemStack enchantedSword = new ItemStack(Items.IRON_SWORD);
                enchantedSword.enchant(Enchantments.SHARPNESS, 10);
                enchantedSword.enchant(Enchantments.FIRE_ASPECT, 1);

                zombie.setItemSlot(EquipmentSlot.MAINHAND, enchantedSword);

                this.level().addFreshEntity(zombie);
                spawnSummonParticles(xPos, zPos);
            }
        }

        for (int i = 0; i < 2; i++) {
            Skeleton skeleton = EntityType.SKELETON.create(this.level());
            if (skeleton != null) {
                double xPos = this.getX() + randomPosition();
                double zPos = this.getZ() + randomPosition();
                skeleton.setPos(xPos, this.getY(), zPos);

                skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObjectRegistry.SPECTRAL_JACK_O_LANTERN.get()));
                skeleton.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ObjectRegistry.HAUNTBOUND_CHESTPLATE.get()));
                skeleton.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ObjectRegistry.HAUNTBOUND_LEGGINGS.get()));
                skeleton.setItemSlot(EquipmentSlot.FEET, new ItemStack(ObjectRegistry.HAUNTBOUND_BOOTS.get()));
                skeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));

                this.level().addFreshEntity(skeleton);
                spawnSummonParticles(xPos, zPos);
            }
        }
    }

    private void spawnSummonParticles(double x, double z) {
        level().addParticle(ParticleTypes.SOUL, x, this.getY() + 0.5D, z, 0.0D, 0.0D, 0.0D);
    }

    private void increaseArmor() {
        Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).getBaseValue() * 2);
    }

    private double randomPosition() {
        return (Math.random() - 0.5) * 20;
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
    }

    private void spawnFireworkExplosionParticles() {
        for (int i = 0; i < 50; i++) {
            double xOffset = (Math.random() - 0.5) * 2.0;
            double yOffset = Math.random() * 2.0;
            double zOffset = (Math.random() - 0.5) * 2.0;

            this.level().addParticle(ParticleTypes.FIREWORK, this.getX() + xOffset, this.getY() + yOffset, this.getZ() + zOffset, 0.0, 0.0, 0.0);
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
            Player nearestPlayer = entity.level().getNearestPlayer(entity, 10);
            return nearestPlayer != null && !entity.isFrozen;
        }

        @Override
        public void start() {
            Player nearestPlayer = entity.level().getNearestPlayer(entity, 10);
            if (nearestPlayer != null) {
                Vec3 awayVector = entity.position().subtract(nearestPlayer.position()).normalize();
                entity.getNavigation().moveTo(entity.getX() + awayVector.x * 10, entity.getY(), entity.getZ() + awayVector.z * 10, speed);
            }
        }

        @Override
        public boolean canContinueToUse() {
            Player nearestPlayer = entity.level().getNearestPlayer(entity, 10);
            return nearestPlayer != null && !entity.isFrozen;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventRegistry.FLEEING_PUMPKIN_DEATH.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEventRegistry.FLEEING_PUMPKIN_AMBIENT.get();
    }
}
