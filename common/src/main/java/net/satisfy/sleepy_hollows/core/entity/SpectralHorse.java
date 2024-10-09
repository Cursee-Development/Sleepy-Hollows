package net.satisfy.sleepy_hollows.core.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpectralHorse extends AbstractChestedHorse {
	public SpectralHorse(EntityType<? extends SpectralHorse> entityType, Level world) {

		super(entityType, world);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.0));
		this.goalSelector.addGoal(3, new PanicGoal(this, 1.0));
		this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
		this.goalSelector.addGoal(5, new TemptGoal(this, 1.0, Ingredient.of(ObjectRegistry.SPECTRAL_ESSENCE.get()), false));
		this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}


	public static AttributeSupplier.Builder createAttributes() {
		return Horse.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 20.0)
				.add(Attributes.ARMOR, 1.5)
				.add(Attributes.ARMOR_TOUGHNESS, 1.5)
				.add(Attributes.JUMP_STRENGTH, 0.7)
				.add(Attributes.MOVEMENT_SPEED, 0.15);
	}

	@Override
	public SpectralHorse getBreedOffspring(@NotNull ServerLevel serverWorld, @NotNull AgeableMob passiveEntity) {
		return EntityTypeRegistry.SPECTRAL_HORSE.get().create(this.level());
	}

	@Override
	protected SoundEvent getAmbientSound() {
		super.getAmbientSound();
		return SoundEvents.SKELETON_HORSE_AMBIENT;
	}

	@Override
	protected SoundEvent getAngrySound() {
		super.getAngrySound();
		return SoundEvents.SKELETON_HORSE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		super.getDeathSound();
		return SoundEvents.SKELETON_HORSE_DEATH;
	}

	@Override
	@Nullable
	protected SoundEvent getEatingSound() {
		return SoundEvents.HORSE_EAT;
	}

	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource source) {
		super.getHurtSound(source);
		return SoundEvents.SKELETON_HORSE_HURT;
	}

	@Override
	public void tick() {
		super.tick();

		if (this.level().isClientSide()) {
			if (this.random.nextInt(24) == 0 && !this.isSilent()) {
				this.level().playLocalSound(this.getX() + 0.5, this.getY() + 0.5, this.getZ() + 0.5, SoundEvents.BLAZE_BURN, this.getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
			}

			for (int i = 0; i < 2; ++i) {
				this.level().addParticle(ParticleTypes.WHITE_ASH, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
				this.level().addParticle(ParticleTypes.ASH, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.0, 0.0);
			}
		}
	}
}