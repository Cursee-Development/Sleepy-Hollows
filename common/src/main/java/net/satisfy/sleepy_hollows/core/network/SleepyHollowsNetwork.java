package net.satisfy.sleepy_hollows.core.network;

import dev.architectury.networking.NetworkChannel;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;


public class SleepyHollowsNetwork {
    public static final NetworkChannel SANITY_CHANNEL = NetworkChannel.create(new SleepyHollowsIdentifier("sanity_channel"));

    public static void init() {
        SANITY_CHANNEL.register(SanityPacketMessage.class, SanityPacketMessage::encode, SanityPacketMessage::new, SanityPacketMessage::apply);
    }
}
