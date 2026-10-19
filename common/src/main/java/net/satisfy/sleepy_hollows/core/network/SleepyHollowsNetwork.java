package net.satisfy.sleepy_hollows.core.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.server.level.ServerPlayer;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;

public class SleepyHollowsNetwork {
    public static void init() {
        if (Platform.getEnvironment() == Env.SERVER) {
            NetworkManager.registerS2CPayloadType(SanityPacketMessage.TYPE, SanityPacketMessage.CODEC);
        } else {
            NetworkManager.registerReceiver(NetworkManager.Side.S2C, SanityPacketMessage.TYPE, SanityPacketMessage.CODEC, SanityPacketMessage::apply);
        }
    }

    public static void sendSanity(ServerPlayer player, int sanity) {
        NetworkManager.sendToPlayer(player, new SanityPacketMessage(sanity));
    }
}
