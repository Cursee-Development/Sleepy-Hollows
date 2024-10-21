package net.satisfy.sleepy_hollows.core.network;

import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;


public class SleepyHollowsNetwork {

    public static final NetworkChannel SANITY_CHANNEL = NetworkChannel.create(new SleepyHollowsIdentifier("sanity_channel"));

    public static void init() {
        SANITY_CHANNEL.register(SanityPacketMessage.class, SanityPacketMessage::encode, SanityPacketMessage::new, SanityPacketMessage::apply);
    }


    public static class Packets {
        public static final ResourceLocation SANITY_PACKET = new SleepyHollowsIdentifier("sanity_packet");


        public static void receiverForClient(FriendlyByteBuf buffer, NetworkManager.PacketContext context) {


            Player player = context.getPlayer();
            player.sendSystemMessage(Component.literal("You have received a Sanity Packet!"));

            SanityPacketMessage message = new SanityPacketMessage(buffer);
            message.apply(() -> context);
        }


        public static void receiverForServer(FriendlyByteBuf buffer, NetworkManager.PacketContext context) {


            Player player = context.getPlayer();
            player.sendSystemMessage(Component.literal("The server has received your Sanity Packet!"));
            Constants.LOG.info("The server received a Sanity Packet from {}", player.getScoreboardName());

            SanityPacketMessage message = new SanityPacketMessage(buffer);
            message.apply(() -> context);
        }
    }
}
