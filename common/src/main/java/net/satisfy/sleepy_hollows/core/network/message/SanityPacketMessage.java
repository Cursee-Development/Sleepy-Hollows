package net.satisfy.sleepy_hollows.core.network.message;

import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class SanityPacketMessage {

    public final boolean inSleepyHollows;

    public SanityPacketMessage(boolean inSleepyHollows) {
        this.inSleepyHollows = inSleepyHollows;
    } // create message, then encode

    public SanityPacketMessage(FriendlyByteBuf buffer) {
        this(buffer.readBoolean());
    } // decode message, then apply

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.inSleepyHollows);
    } // after a message was created

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {

        if (!inSleepyHollows) return;

        NetworkManager.PacketContext context = contextSupplier.get();

        Env environment = context.getEnvironment();

        Player player = context.getPlayer();

        if (environment == Env.CLIENT) {
            player.displayClientMessage(Component.literal("client received a sanity packet to apply"), true);
        }

        if (environment == Env.SERVER) {

            ServerPlayer serverPlayer = (ServerPlayer) player;

            serverPlayer.sendSystemMessage(Component.literal("server received a sanity packet to apply"));
        }
    } // after a message was decoded
}
