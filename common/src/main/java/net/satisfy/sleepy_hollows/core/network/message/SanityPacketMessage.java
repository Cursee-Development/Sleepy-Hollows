package net.satisfy.sleepy_hollows.core.network.message;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import org.jetbrains.annotations.NotNull;

public record SanityPacketMessage(int sanity) implements CustomPacketPayload {
    public static final Type<SanityPacketMessage> TYPE = new Type<>(SleepyHollows.identifier("sanity"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SanityPacketMessage> CODEC = ByteBufCodecs.VAR_INT.map(SanityPacketMessage::new, SanityPacketMessage::sanity).cast();

    public static void apply(SanityPacketMessage message, NetworkManager.PacketContext context) {
        context.queue(() -> SanityManager.setClientSanity(message.sanity()));
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
