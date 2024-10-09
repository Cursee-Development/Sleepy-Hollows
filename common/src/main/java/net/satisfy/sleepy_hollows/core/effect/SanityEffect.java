package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SanityEffect extends MobEffect {
    private double rotationDirection, motionDirection;

    public SanityEffect() {
        super(MobEffectCategory.HARMFUL, 0x800080);
    }

    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getUseItem().is(Items.MILK_BUCKET) || livingEntity.isUsingItem()) {
            livingEntity.stopUsingItem();
        }
        this.distractEntity(livingEntity);

        Objects.requireNonNull(livingEntity.getAttributes().getInstance(Attributes.ATTACK_DAMAGE))
                .setBaseValue(livingEntity.getAttributes().getBaseValue(Attributes.ATTACK_DAMAGE) * 0.5);

        Objects.requireNonNull(livingEntity.getAttributes().getInstance(Attributes.ARMOR))
                .setBaseValue(livingEntity.getAttributes().getBaseValue(Attributes.ARMOR) * 0.5);

        Objects.requireNonNull(livingEntity.getAttributes().getInstance(Attributes.MOVEMENT_SPEED))
                .setBaseValue(livingEntity.getAttributes().getBaseValue(Attributes.MOVEMENT_SPEED) * 0.8);

        if (!livingEntity.hasEffect(MobEffects.BLINDNESS)) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * 25, 0));
        }
    }

    private void distractEntity(LivingEntity livingEntity) {
        double gaussian = livingEntity.level().getRandom().nextGaussian();
        double newMotionDirection = 0.3 * gaussian;
        double newRotationDirection = (Math.PI / 1.5) * gaussian;

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
        return true;
    }
}
