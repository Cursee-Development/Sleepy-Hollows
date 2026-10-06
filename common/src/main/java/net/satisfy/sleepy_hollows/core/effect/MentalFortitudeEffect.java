package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import org.jetbrains.annotations.NotNull;

public class MentalFortitudeEffect extends MobEffect {
    public MentalFortitudeEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FF00);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayer player && SanityManager.getSanity(player) < SanityManager.MAXIMUM_SANITY) {
            SanityManager.changeSanity(player, SanityManager.Modifiers.MENTAL_FORTITUDE);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
