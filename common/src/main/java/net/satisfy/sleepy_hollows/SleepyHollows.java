package net.satisfy.sleepy_hollows;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.satisfy.sleepy_hollows.client.event.HUDRenderEvent;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import net.satisfy.sleepy_hollows.core.registry.*;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsBiomeKeys;

public final class SleepyHollows {

    public static void init() {
        FluidRegistry.init();
        ObjectRegistry.init();
        TabRegistry.init();
        MobEffectRegistry.init();
        EntityTypeRegistry.init();
        SoundEventRegistry.init();
        FeatureTypeRegistry.init();
        SleepyHollowsNetwork.init();

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

        // every second
        if (server.getTickCount() % 20 == 0) {

            // for every player
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {

                // check for valid blocks
                SanityManager.doBlockCheck(player);
            }
        }

        // every five seconds
        if (server.getTickCount() % (5 * 20) == 0) {

            // for every player
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {

                if (SanityManager.isImmune(player) || player.level().getBlockState(player.blockPosition()).is(TagRegistry.RESET_SANITY)) return;

                // if they do not have mental fortitude
                if (!player.level().getBiome(player.getOnPos()).is(SleepyHollowsBiomeKeys.SLEEPY_HOLLOWS)) {
                    SanityManager.changeSanity(player, SanityManager.Modifiers.OUTSIDE_BIOME.getValue()); // update server
                    SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(player, new SanityPacketMessage(SanityManager.Modifiers.OUTSIDE_BIOME.getValue())); // update client
                }
                else {
                    SanityManager.changeSanity(player, SanityManager.Modifiers.INSIDE_BIOME.getValue()); // update server
                    SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(player, new SanityPacketMessage(SanityManager.Modifiers.INSIDE_BIOME.getValue())); // update client
                }
            }
        }
    }


    /**
     * General TODO List:
     * (5) - Implement networking functionality (currently being handled by Jason13Official)
     * (3) - Fix Horseman entity spawning inside walls
     * (2) - Balance adjustments for Horseman
     * (1) - Pumpkin Nametag
     * (1) - Explosion particles on Horseman death
     * (2) - Health & Attack based on difficulty - currently disabled
     * (1) - Ensure particle arcs are emitted from Horseman to Pumpkin Head
     * (5) - Rename "Sanity Bar" to "Insanity Bar" -> instead of filling up it should start at 100 and slowly decrease (currently being handled by Jason13Official)
     * ----
     * 5 = highest Priority, 1 = lowest Priority
     */
}
