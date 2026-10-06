package net.satisfy.sleepy_hollows.core.network;

import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;

public class SleepyHollowsNetwork {
    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, SanityPacketMessage.TYPE, SanityPacketMessage.CODEC, SanityPacketMessage::apply);
    }

    public static void sendSanity(ServerPlayer player, int sanity) {
        NetworkManager.sendToPlayer(player, new SanityPacketMessage(sanity));
    }
}
