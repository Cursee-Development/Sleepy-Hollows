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
     (5) - Effects don't run out - only affecting sanity and mental fortitude
     (5) - Implement networking functionality (currently being handled by Jason13Official).
     (5) - Rename "Sanity Bar" to "Insanity Bar" -> Instead of filling up, it should start at 100 and slowly decrease (currently being handled by Jason13Official).
     (4) - Documentation / Wiki
     (4) - Fix Horseman summoned entities spawning inside walls (Skeletons, Zombies, and Pumpkin Head).
     (2) - Balance adjustments for Horseman -> A few people (3) are currently playtesting this, but we might need more input data for further adjustments.
     (1) - Explosion particles on Horseman death.
     (2) - Health & attack based on difficulty - currently disabled.
     (4) - Ensure particle arcs are emitted from Horseman to Pumpkin Head -> Let's change the Pumpkin Head spawning method: it spawns at the Horseman's 'head' part and
     then 'flies' 10 blocks in a random direction, immune to any damage for 3 seconds. This might be easier to do and would visually make more sense. :)
     ----
     5 = highest Priority, 1 = lowest Priority


     Further adjustments planned for the Horseman Fight:
     * New Attack: Casts a Soulfire Spiral every X seconds, setting blocks on fire and making the boss fight less static/tank & spank only during the fighting phase.

     */

}
