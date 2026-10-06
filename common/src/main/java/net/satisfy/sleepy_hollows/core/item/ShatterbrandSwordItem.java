package net.satisfy.sleepy_hollows.core.item;

import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.satisfy.sleepy_hollows.core.registry.ToolTiersRegistry;
import org.jetbrains.annotations.NotNull;

public class ShatterbrandSwordItem extends SwordItem {
    public ShatterbrandSwordItem(Properties properties) {
        super(ToolTiersRegistry.SPECTRAL, properties.attributes(ShovelItem.createAttributes(ToolTiersRegistry.SPECTRAL, 3, -2.4F)));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        if (!target.level().isClientSide) {
            if (target.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 20; ++i) {
                    double px = target.getX() + target.getLookAngle().x;
                    double py = target.getY() + target.getBbHeight();
                    double pz = target.getZ() + target.getLookAngle().z;
                    serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, px, py, pz, 1, 0.0, 0.0, 0.0, 0.02);
                    serverLevel.sendParticles(ParticleTypes.SOUL, px, py, pz, 1, 0.0, 0.0, 0.0, 0.02);
                    serverLevel.sendParticles(ParticleTypes.WHITE_ASH, px, py, pz, 1, 0.0, 0.0, 0.0, 0.02);
                }
            }
            if (result) {
                target.addEffect(new MobEffectInstance(MobEffectRegistry.holder(MobEffectRegistry.INFECTED), 30, 1));
            }
        }
        return result;
    }
}