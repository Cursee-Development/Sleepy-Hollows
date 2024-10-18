package net.satisfy.sleepy_hollows.client.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;
import net.satisfy.sleepy_hollows.core.registry.TagRegistry;
import net.satisfy.sleepy_hollows.core.util.IEntitySavedData;
import org.jetbrains.annotations.NotNull;

public class SanityManager {

    public static final String SANITY = "sanity";
    public static final int MINIMUM_SANITY = 0;
    public static final int MAXIMUM_SANITY = 100;

    public static IEntitySavedData getSavedData(Player player) {
        return (IEntitySavedData) player;
    }

    public static CompoundTag getSanityTag(IEntitySavedData playerData) {
        return playerData.impl$getPersistentData();
    }

    public static int getSanity(Player player) {
        CompoundTag nbt = getSanityTag(getSavedData(player));
        if (!nbt.contains(SANITY, Tag.TAG_INT)) nbt.putInt(SANITY, 100);
        return nbt.getInt(SANITY);
    }

    /** @param amount expects a negative integer
     *  @return The amount by which sanity was modified */
    private static int decreaseSanity(Player player, int amount) {

        final int currentSanity = getSanity(player);
        if (player.hasEffect(MobEffectRegistry.MENTAL_FORTITUDE.get())) return currentSanity;
        final int newSanity = Math.max(MINIMUM_SANITY, currentSanity + amount);
        getSanityTag(getSavedData(player)).putInt(SANITY, safeSanity(newSanity));

        return amount; // to synch
    }

    /** @param amount expects a positive integer
     *  @return The amount by which sanity was modified */
    private static int increaseSanity(Player player, int amount) {

        final int currentSanity = getSanity(player);
        final int newSanity = Math.min(MAXIMUM_SANITY, currentSanity + amount);
        getSanityTag(getSavedData(player)).putInt(SANITY, safeSanity(newSanity));

        return amount; // to synch
    }

    /** @param player An instance of a Player
     *  @param amount A positive or negative integer to change the Sanity by
     *  @return The amount by which sanity was modified */
    public static int changeSanity(Player player, int amount) {
        if (isImmune(player)) return 0;
        // if 0 return 0, else if more than 0 increase, else decrease
        return amount == 0 ? 0 : amount > 0 ? increaseSanity(player, amount) : decreaseSanity(player, amount);
    }

    /** Used to ensure that a given value is within the bounds of 0 to 100 */
    private static int safeSanity(int amount) {
        if (amount < MINIMUM_SANITY) amount = MINIMUM_SANITY;
        if (amount > MAXIMUM_SANITY) amount = MAXIMUM_SANITY;
        return amount;
    }

    public static void doBlockCheck(@NotNull ServerPlayer serverPlayer) {

        Level level = serverPlayer.level();
        BlockPos blockPos = serverPlayer.blockPosition();
        BlockState blockState = level.getBlockState(blockPos);

        if (blockState.is(TagRegistry.RESET_SANITY)) {
            final int amount = MAXIMUM_SANITY;
            changeSanity(serverPlayer, amount); // update server
            SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(serverPlayer, new SanityPacketMessage(amount)); // update client
        }

        if (!isImmune(serverPlayer) && blockState.is(TagRegistry.INCREASE_SANITY)) {
            final int amount = -2;
            changeSanity(serverPlayer, amount); // update server
            SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(serverPlayer, new SanityPacketMessage(amount)); // update client
        }
    }

    public static boolean isImmune(Player player) {
        return player.hasEffect(MobEffectRegistry.MENTAL_FORTITUDE.get());
    }
}
