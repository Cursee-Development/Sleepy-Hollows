package net.satisfy.sleepy_hollows.core.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.satisfy.sleepy_hollows.core.registry.ToolTiersRegistry;
import org.jetbrains.annotations.NotNull;

public class SpectralWarAxeItem extends AxeItem {
    public SpectralWarAxeItem(Properties properties) {
        super(ToolTiersRegistry.SPECTRAL, properties.attributes(ShovelItem.createAttributes(ToolTiersRegistry.SPECTRAL, 1.0F, -2.8F)));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (result && !target.level().isClientSide()) {
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120, 0));
        }

        if (!target.level().isClientSide) {
            if (target.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 10; ++i) {
                    double px = target.getX() + target.getLookAngle().x;
                    double py = target.getY() + target.getBbHeight();
                    double pz = target.getZ() + target.getLookAngle().z;

                    serverLevel.sendParticles(ParticleTypes.WHITE_ASH, px, py, pz, 1, 0.0, 0.0, 0.0, 0.02);
                    serverLevel.sendParticles(ParticleTypes.SOUL, px, py, pz, 1, 0.0, 0.0, 0.0, 0.02);
                }
            }
        }

        return result;
    }

    @Override
    public float getAttackDamageBonus(@NotNull Entity target, float damage, @NotNull DamageSource source) {
        if (!(target instanceof LivingEntity living) || damage <= 0) return 0;
        float armor = living.getArmorValue();
        float toughness = (float) living.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
        float withFullArmor = CombatRules.getDamageAfterAbsorb(living, damage, source, armor, toughness);
        float withHalfArmor = CombatRules.getDamageAfterAbsorb(living, damage, source, armor * 0.5F, toughness);
        return withFullArmor <= 0 ? 0 : damage * withHalfArmor / withFullArmor - damage;
    }
}