package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SanityEffect extends MobEffect {
    private double rotationDirection, motionDirection;

    public SanityEffect() {
        super(MobEffectCategory.HARMFUL, 0x800080);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (Objects.requireNonNull(livingEntity.getEffect(this)).getDuration() > 0) {
            this.distractEntity(livingEntity);

            if (livingEntity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) == 1.0) {
                Objects.requireNonNull(livingEntity.getAttributes().getInstance(Attributes.ATTACK_DAMAGE))
                        .setBaseValue(0.5);
            }

            if (livingEntity.getAttributeBaseValue(Attributes.ARMOR) == 1.0) {
                Objects.requireNonNull(livingEntity.getAttributes().getInstance(Attributes.ARMOR))
                        .setBaseValue(0.5);
            }

            if (livingEntity.getAttributeBaseValue(Attributes.MOVEMENT_SPEED) == 1.0) {
                Objects.requireNonNull(livingEntity.getAttributes().getInstance(Attributes.MOVEMENT_SPEED))
                        .setBaseValue(0.8);
            }

            if (!livingEntity.hasEffect(MobEffects.BLINDNESS)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * 25, 0));
            }
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull net.minecraft.world.entity.ai.attributes.AttributeMap attributeMap, int amplifier) {
        Objects.requireNonNull(livingEntity.getAttributes().getInstance(Attributes.ATTACK_DAMAGE))
                .setBaseValue(1.0);

        Objects.requireNonNull(livingEntity.getAttributes().getInstance(Attributes.ARMOR))
                .setBaseValue(1.0);

        Objects.requireNonNull(livingEntity.getAttributes().getInstance(Attributes.MOVEMENT_SPEED))
                .setBaseValue(1.0);

        super.removeAttributeModifiers(livingEntity, attributeMap, amplifier);
    }

    private void distractEntity(LivingEntity livingEntity) {
        double gaussian = livingEntity.level().getRandom().nextGaussian();
        double newMotionDirection = 0.25 * gaussian;
        double newRotationDirection = (Math.PI / 0.75) * gaussian;

        this.rotationDirection = 0.45 * newRotationDirection + (1.1 - 0.45) * this.rotationDirection;
        livingEntity.setYRot((float) (livingEntity.getYRot() + this.rotationDirection));
        livingEntity.setXRot((float) (livingEntity.getXRot() + this.rotationDirection));

        this.motionDirection = 0.35 * newMotionDirection + 0.8 * this.motionDirection;
        livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(this.motionDirection, 0, this.motionDirection));

        if (livingEntity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, Items.PURPLE_DYE.getDefaultInstance()),
                    livingEntity.getX(), livingEntity.getY() + livingEntity.getBbHeight() * 0.8, livingEntity.getZ(),
                    2, 0.0, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0 && duration % 20 == 0;
    }
}
