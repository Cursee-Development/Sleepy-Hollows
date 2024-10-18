package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import org.jetbrains.annotations.NotNull;

public class MentalFortitudeEffect extends MobEffect {

    public MentalFortitudeEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FF00);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player && !player.level().isClientSide()) {
            if (SanityManager.getSanity(player) < 100) {
                SanityManager.changeSanity(player, SanityManager.Modifiers.MENTAL_FORTITUDE.getValue()); // update server
                SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer((ServerPlayer) player, new SanityPacketMessage(SanityManager.Modifiers.MENTAL_FORTITUDE.getValue())); // update client
            }
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull net.minecraft.world.entity.ai.attributes.AttributeMap attributeMap, int amplifier) {
        // NO-OP
        // if the player has this effect they are assumed to be immune to sanity modifiers
        super.removeAttributeModifiers(livingEntity, attributeMap, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
