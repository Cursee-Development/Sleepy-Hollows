package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
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
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        MobEffectInstance currentEffect = livingEntity.getEffect(this);
        if (currentEffect != null && currentEffect.getDuration() > 0) {
            this.distractEntity(livingEntity);

            int remainingDuration = currentEffect.getDuration();
            livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, remainingDuration, 0, false, false));

            if (remainingDuration == 20) {
                livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.BAD_DREAM.get(), 10, 1));
            }
        }
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
            serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, Items.PURPLE_DYE.getDefaultInstance()),
                    livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * 0.8, livingEntity.getZ(),
                    15, 0.0, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0 && (duration % 20 == 0);
    }
}
