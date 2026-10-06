package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import org.jetbrains.annotations.NotNull;

public class InfectedEffect extends MobEffect {
    private static final int DAMAGE_INTERVAL = 40;
    private static final int SANITY_INTERVAL = 60;

    public InfectedEffect() {
        super(MobEffectCategory.HARMFUL, 0x800080);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide()) return true;
        MobEffectInstance instance = entity.getEffect(MobEffectRegistry.holder(MobEffectRegistry.INFECTED));
        int remaining = instance == null ? 0 : instance.getDuration();
        if (remaining % DAMAGE_INTERVAL == 0) {
            entity.hurt(entity.damageSources().wither(), 1.0F);
        }
        if (entity instanceof ServerPlayer player && remaining % SANITY_INTERVAL == 0) {
            SanityManager.changeSanity(player, SanityManager.Modifiers.INFECTED_EFFECT);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
