package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class InfectedEffect extends MobEffect {

    private static final int SANITY_INCREASE_INTERVAL = 60;
    private static final int TOTAL_SANITY_DURATION = 300;

    public InfectedEffect() {
        super(MobEffectCategory.HARMFUL, 0x800080);
    }

    @Override
    @SuppressWarnings("removal")
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide() && entity instanceof ServerPlayer player) {
            player.hurt(player.damageSources().wither(), 1.0F);
            var registry = entity.level().registryAccess().registryOrThrow(Registries.MOB_EFFECT);
            var holderOpt = registry.getResourceKey(this).flatMap(registry::getHolder);
            holderOpt.ifPresent(holder -> {
                var instance = player.getEffect(holder);
                if (instance != null) {
                    int remaining = instance.getDuration();
                    if (remaining % SANITY_INCREASE_INTERVAL == 0 && remaining <= TOTAL_SANITY_DURATION) {
                        SanityManager.changeSanity(player, SanityManager.Modifiers.INFECTED_EFFECT.getValue());
                        SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(player, new SanityPacketMessage(SanityManager.Modifiers.INFECTED_EFFECT.getValue()));
                    }
                }
            });
        }
        return true;
    }
}
