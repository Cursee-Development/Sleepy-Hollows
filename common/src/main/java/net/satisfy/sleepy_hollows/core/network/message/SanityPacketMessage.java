package net.satisfy.sleepy_hollows.core.network.message;

import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.core.util.SanityManager;

import java.util.function.Supplier;

public class SanityPacketMessage {
    public final int amountToChangeSanity;

    public SanityPacketMessage(int amountToChangeSanity) {
        this.amountToChangeSanity = amountToChangeSanity;
    }

    public SanityPacketMessage(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.amountToChangeSanity);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {

        if (amountToChangeSanity == 0) return;

        NetworkManager.PacketContext context = contextSupplier.get();
        Env environment = context.getEnvironment();
        Player player = context.getPlayer();


        if (environment == Env.CLIENT && player instanceof LocalPlayer localPlayer) {
            SanityManager.changeClientSanity(localPlayer, this.amountToChangeSanity);
        }
    }
}