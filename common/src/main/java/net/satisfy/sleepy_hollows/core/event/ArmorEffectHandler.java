package net.satisfy.sleepy_hollows.core.event;

import dev.architectury.event.events.common.TickEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.core.registry.ArmorSetRegistry;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;

public class ArmorEffectHandler {
    public static void init() {
        TickEvent.PLAYER_POST.register(ArmorEffectHandler::onPlayerTick);
    }

    private static void onPlayerTick(Player player) {
        if (player instanceof ServerPlayer && player.tickCount % 10 == 0 && ArmorSetRegistry.hasHauntboundBonus(player)) {
            player.addEffect(new MobEffectInstance(MobEffectRegistry.holder(MobEffectRegistry.MENTAL_FORTITUDE), 40, 0, true, false));
        }
    }
}
