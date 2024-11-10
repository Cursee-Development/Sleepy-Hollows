package net.satisfy.sleepy_hollows.core.util;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;
import net.satisfy.sleepy_hollows.core.registry.TagRegistry;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/** Handles updating server and client values for Sanity */
public class SanityManager {
    
    public static final String SANITY = "sanity";
    public static final int MINIMUM_SANITY = 0;
    public static final int MAXIMUM_SANITY = 100;
    
    public enum Modifiers {
        CANDY_CORN(4), DUSK_BERRY(2),
        LUMINOUS_WATER(6), SPECTRAL_PUMPKIN_PIE(10),

        INSIDE_BIOME(-1), OUTSIDE_BIOME(5),
        RESET_SANITY(MAXIMUM_SANITY), DECREASE_SANITY(-2),
        INFECTED_EFFECT(-2), MENTAL_FORTITUDE(MAXIMUM_SANITY);

        final int value;

        Modifiers(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private static int safeSanity(int amount) {
        if (amount < MINIMUM_SANITY) return MINIMUM_SANITY;
        if (amount > MAXIMUM_SANITY) return MAXIMUM_SANITY;
        return amount;
    }

    public static boolean isImmune(ServerPlayer player) {
        return player.hasEffect(MobEffectRegistry.MENTAL_FORTITUDE.get());
    }

    public static boolean isClientImmune(LocalPlayer player) {
        return player.hasEffect(MobEffectRegistry.MENTAL_FORTITUDE.get());
    }
    
    public static IEntitySavedData getSavedData(ServerPlayer serverPlayer) {
        return (IEntitySavedData) serverPlayer;
    }
    
    public static IEntitySavedData getClientSavedData(LocalPlayer clientPlayer) {
        return (IEntitySavedData) clientPlayer;
    }
    
    public static CompoundTag getSanityTag(IEntitySavedData playerData) {
        return playerData.impl$getPersistentData();
    }
    
    public static CompoundTag getClientSanityTag(IEntitySavedData playerData) {
        return playerData.impl$getPersistentData();
    }

    public static int getSanity(ServerPlayer player) {
        CompoundTag nbt = SanityManager.getSanityTag(SanityManager.getSavedData(player));
        if (!nbt.contains(SANITY, Tag.TAG_INT)) nbt.putInt(SANITY, 100);
        return nbt.getInt(SANITY);
    }

    public static int getClientSanity(LocalPlayer player) {
        CompoundTag nbt = SanityManager.getSanityTag(SanityManager.getClientSavedData(player));
        if (!nbt.contains(SANITY, Tag.TAG_INT)) nbt.putInt(SANITY, 100);
        return nbt.getInt(SANITY);
    }

    /**
     * @param amount expects a negative integer
     */
    public static void decreaseSanity(ServerPlayer player, int amount) {

        final int currentSanity = getSanity(player);
        if (SanityManager.isImmune(player)) return;
        final int newSanity = Math.max(MINIMUM_SANITY, currentSanity + amount);
        getSanityTag(getSavedData(player)).putInt(SANITY, safeSanity(newSanity));

    }
    
    public static void decreaseClientSanity(LocalPlayer player, int amount) {

        final int currentSanity = getClientSanity(player);
        if (SanityManager.isClientImmune(player)) return;
        final int newSanity = Math.max(MINIMUM_SANITY, currentSanity + amount);
        getClientSanityTag(getClientSavedData(player)).putInt(SANITY, safeSanity(newSanity));

    }

    /**
     * @param amount expects a positive integer
     */
    private static void increaseSanity(ServerPlayer player, int amount) {

        final int currentSanity = getSanity(player);
        final int newSanity = Math.min(MAXIMUM_SANITY, currentSanity + amount);
        getSanityTag(getSavedData(player)).putInt(SANITY, safeSanity(newSanity));

    }
    
    private static void increaseClientSanity(LocalPlayer player, int amount) {

        final int currentSanity = getClientSanity(player);
        final int newSanity = Math.min(MAXIMUM_SANITY, currentSanity + amount);
        getClientSanityTag(getClientSavedData(player)).putInt(SANITY, safeSanity(newSanity));

    }

    public static void changeSanity(ServerPlayer player, int amount) {
        if (isImmune(player)) return;

        if (amount != 0) {
            if (amount > 0) {
                increaseSanity(player, amount);
            } else {
                decreaseSanity(player, amount);
            }
        }
    }

    public static void changeClientSanity(LocalPlayer player, int amount) {
        if (isClientImmune(player)) return;

        if (amount != 0) {
            if (amount > 0) {
                increaseClientSanity(player, amount);
            } else {
                decreaseClientSanity(player, amount);
            }
        }
    }

    public static void doBlockCheck(ServerPlayer serverPlayer) {

        Level level = serverPlayer.level();
        BlockPos blockPos = serverPlayer.blockPosition();
        BlockState blockState = level.getBlockState(blockPos);

        if (blockState.is(TagRegistry.RESET_SANITY)) {
            changeSanity(serverPlayer, Modifiers.RESET_SANITY.getValue());
            SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(serverPlayer, new SanityPacketMessage(Modifiers.RESET_SANITY.getValue()));
        }

        if (!isImmune(serverPlayer) && blockState.is(TagRegistry.DECREASE_SANITY)) {
            changeSanity(serverPlayer, Modifiers.DECREASE_SANITY.getValue());
            SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(serverPlayer, new SanityPacketMessage(Modifiers.DECREASE_SANITY.getValue()));
        }
    }

    public static boolean isSanityBarVisible(LocalPlayer player) {
        return getClientSanity(player) < MAXIMUM_SANITY;
    }
}
