package net.satisfy.sleepy_hollows;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.satisfy.sleepy_hollows.client.event.HUDRenderEvent;
import net.satisfy.sleepy_hollows.core.event.ArmorEffectHandler;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import net.satisfy.sleepy_hollows.core.registry.*;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsBiomeKeys;

public final class SleepyHollows {

    public static void init() {
        ObjectRegistry.init();
        TabRegistry.init();
        MobEffectRegistry.init();
        EntityTypeRegistry.init();
        SoundEventRegistry.init();
        FeatureTypeRegistry.init();
        SleepyHollowsNetwork.init();
        ArmorEffectHandler.init();
        Constants.LOG.info("Sleepy Hollows has been initialized in the common setup phase.");

        if (Platform.getEnv() == EnvType.CLIENT) {
            ClientGuiEvent.RENDER_HUD.register(HUDRenderEvent::onRenderHUD);
        }
    }

    public static void commonInit() {
        FlammableBlockRegistry.init();
        AxeItemHooks.addStrippable(ObjectRegistry.HOLLOW_LOG.get(), ObjectRegistry.STRIPPED_HOLLOW_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.HOLLOW_WOOD.get(), ObjectRegistry.STRIPPED_HOLLOW_WOOD.get());
    }

    public static void onServerTick(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {

            if (SanityManager.getSanity(player) <= 0 && player.gameMode.isSurvival()) {
                player.addEffect(new MobEffectInstance(MobEffectRegistry.INSANITY.get(), 1200));

                SanityManager.changeSanity(player, 100);
                SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(player, new SanityPacketMessage(100));
            }

            if (server.getTickCount() % 20 == 0) {
                SanityManager.doBlockCheck(player);
            }

            if (server.getTickCount() % (5 * 20) == 0) {

                if (SanityManager.isImmune(player) || player.level().getBlockState(player.blockPosition()).is(TagRegistry.RESET_SANITY))
                    return;


                if (!player.level().getBiome(player.getOnPos()).is(SleepyHollowsBiomeKeys.SLEEPY_HOLLOWS)) {
                    SanityManager.changeSanity(player, SanityManager.Modifiers.OUTSIDE_BIOME.getValue());
                    SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(player, new SanityPacketMessage(SanityManager.Modifiers.OUTSIDE_BIOME.getValue()));
                } else {
                    SanityManager.changeSanity(player, SanityManager.Modifiers.INSIDE_BIOME.getValue());
                    SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(player, new SanityPacketMessage(SanityManager.Modifiers.INSIDE_BIOME.getValue()));
                }
            }
        }
    }
}