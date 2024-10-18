package net.satisfy.sleepy_hollows;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.biome.Biome;
import net.satisfy.sleepy_hollows.client.event.HUDRenderEvent;
import net.satisfy.sleepy_hollows.client.event.PlayerTickEvent;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
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

    public static final int NEW_STRUCTURE_SIZE = 512;


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
     General TODO List:
     (5) - Effects don't run out - only affecting sanity and mental fortitude -> I will do this as soon as Networking is done.
     (5) - Implement networking functionality (currently being handled by Jason13Official).
     (5) - Rename "Sanity Bar" to "Insanity Bar" -> Instead of filling up, it should start at 100 and slowly decrease (currently being handled by Jason13Official).
     (4) - Documentation -> Readme should be enough, right?
     (2) - Health & attack based on difficulty - currently disabled - no clue. Wtf! :D
     ----
     5 = highest Priority, 1 = lowest Priority


     */

}
