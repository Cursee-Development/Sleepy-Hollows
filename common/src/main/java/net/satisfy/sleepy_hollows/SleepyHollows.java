package net.satisfy.sleepy_hollows;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import net.satisfy.sleepy_hollows.client.event.HUDRenderEvent;
import net.satisfy.sleepy_hollows.client.event.PlayerTickEvent;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.satisfy.sleepy_hollows.core.entity.Horseman;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import net.satisfy.sleepy_hollows.core.registry.*;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsUtil;

public final class SleepyHollows {

    public static void init() {
        FluidRegistry.init();
        ObjectRegistry.init();
        TabRegistry.init();
        MobEffectRegistry.init();
        EntityTypeRegistry.init();
        SoundEventRegistry.init();
        FeatureTypeRegistry.init();

        SleepyHollowsNetwork.init(); // registers the channel for our message to pass through

        Constants.LOG.info("Sleepy Hollows has been initialized in the common setup phase.");

        if (Platform.getEnv() == EnvType.CLIENT) {
            ClientGuiEvent.RENDER_HUD.register(HUDRenderEvent::onRenderHUD);
            ClientTickEvent.CLIENT_POST.register(PlayerTickEvent::onClientTick);
        }

        // MANUAL ???
        // register a receiver for a Client-to-Server (C2S) packet, to handle the packet when the server acquires it
        // NetworkManager.registerReceiver(NetworkManager.Side.C2S, SleepyHollowsNetwork.Packets.SANITY_PACKET, SleepyHollowsNetwork.Packets::receiverForServer);
    }

    public static void commonInit() {
        FlammableBlockRegistry.init();
        AxeItemHooks.addStrippable(ObjectRegistry.HOLLOW_LOG.get(), ObjectRegistry.STRIPPED_HOLLOW_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.HOLLOW_WOOD.get(), ObjectRegistry.STRIPPED_HOLLOW_WOOD.get());
    }

    // send a packet to every player, once every ten seconds
    public static void onServerTick(MinecraftServer server) {

        // every ten seconds
        if (server.getTickCount() % (10 * 20) == 0) {

            for (ServerPlayer serverPlayer : server.getPlayerList().getPlayers()) {

                if (SanityManager.hasSanityImmunity(serverPlayer)) return;

                Holder<Biome> biomeHolder = serverPlayer.level().getBiome(serverPlayer.getOnPos());
                if (!SleepyHollowsUtil.unwrappedBiome(biomeHolder).contains("sleepy_hollow")) return;

                // MANUAL ???
//                // create a new buffer to store encoded data
//                FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
//
//                // create a new message to store raw information
//                SanityPacketMessage sanityPacketMessage = new SanityPacketMessage(true);
//
//                // encode our message onto the buffer
//                sanityPacketMessage.encode(buf);
//
//                NetworkManager.sendToPlayer(serverPlayer, SleepyHollowsNetwork.Packets.SANITY_PACKET, buf);

                SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(serverPlayer, new SanityPacketMessage(true));
            }
        }
    }


    /**
     * General TODO List:
     ***** - Implement networking functionality (currently being handled by Jason13Official)
     *** - Fix Horseman entity spawning inside walls
     **** - Balance adjustments for Horseman
     ** - Ensure particle arcs are emitted from Horseman to Pumpkin Head
     * - Make the Pumpkin model part invisible when the Head is active
     ***** - Rename "Sanity Bar" to "Insanity Bar"
     ***** - Modify the (In)Sanity Bar to function like a Boss Bar
     */

}
