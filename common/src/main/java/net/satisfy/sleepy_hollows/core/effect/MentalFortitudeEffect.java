package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import org.jetbrains.annotations.NotNull;

public class MentalFortitudeEffect extends MobEffect {

    public MentalFortitudeEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FF00);
    }

    @Override
    @SuppressWarnings("removal")
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayer player && !player.level().isClientSide()) {
            if (SanityManager.getSanity(player) < 100) {
                int change = SanityManager.Modifiers.MENTAL_FORTITUDE.getValue();
                SanityManager.changeSanity(player, change);
                SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(player, new SanityPacketMessage(change));
            }
        }
        return true;
    }
}