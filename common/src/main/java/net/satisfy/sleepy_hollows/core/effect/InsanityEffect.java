package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;
import org.jetbrains.annotations.NotNull;

public class InsanityEffect extends MobEffect {
    private double rotationDirection, motionDirection;

    public InsanityEffect() {
        super(MobEffectCategory.HARMFUL, 0x800080);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        var registry = entity.level().registryAccess().registryOrThrow(Registries.MOB_EFFECT);
        registry.getResourceKey(this).flatMap(registry::getHolder).ifPresent(selfHolder -> {
            var instance = entity.getEffect(selfHolder);
            if (instance != null && instance.getDuration() > 0) {
                distractEntity(entity);
                int remaining = instance.getDuration();
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, remaining, 0, false, false));
                registry.getResourceKey(MobEffectRegistry.BAD_DREAM.get()).flatMap(registry::getHolder).ifPresent(badHolder -> {
                    if (remaining == 20) {
                        entity.addEffect(new MobEffectInstance(badHolder, 10, 1));
                    }
                });
            }
        });
        return true;
    }

    private void distractEntity(LivingEntity livingEntity) {
        double gaussian = livingEntity.level().getRandom().nextGaussian();
        double newMotionDirection = 0.35 * gaussian;
        double newRotationDirection = (Math.PI / 0.75) * gaussian;

        this.rotationDirection = 0.75 * newRotationDirection + (1.2 - 0.5) * this.rotationDirection;
        livingEntity.setYRot((float) (livingEntity.getYRot() + this.rotationDirection));
        livingEntity.setXRot((float) (livingEntity.getXRot() + this.rotationDirection));

        this.motionDirection = 0.35 * newMotionDirection + 0.8 * this.motionDirection;
        livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(this.motionDirection, 0, this.motionDirection));

        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, Items.PURPLE_DYE.getDefaultInstance()), livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * 0.8, livingEntity.getZ(), 15, 0.0, 0.0, 0.0, 0.0);
        }
    }
}
