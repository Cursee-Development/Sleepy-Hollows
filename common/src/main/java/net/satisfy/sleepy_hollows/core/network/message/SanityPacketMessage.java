package net.satisfy.sleepy_hollows.core.network.message;

import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.client.util.SanityManager;

import java.util.function.Supplier;

public class SanityPacketMessage {

    public final int amountToChangeSanity;

    public SanityPacketMessage(int amountToChangeSanity) {
        this.amountToChangeSanity = amountToChangeSanity;
    } // create message, then encode

    public SanityPacketMessage(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    } // decode message, then apply

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.amountToChangeSanity);
    } // after a message was created

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {

        if (amountToChangeSanity == 0) return;

        NetworkManager.PacketContext context = contextSupplier.get();
        Env environment = context.getEnvironment();
        Player player = context.getPlayer();

        // when a client receives a packet from the server
        if (environment == Env.CLIENT) {

            // we update the player's sanity to match the packet
            final int amount = this.amountToChangeSanity;
            SanityManager.changeSanity(player, amount); // update client
        }
    } // after a message was decoded
}
