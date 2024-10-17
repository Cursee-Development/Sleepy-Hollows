package net.satisfy.sleepy_hollows.client.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.satisfy.sleepy_hollows.core.util.IEntityDataSaver;

public class PlayerSanityProvider {

    public static final String SANITY = "sanity";
    private static final int MINIMUM_SANITY = 0;
    private static final int MAXIMUM_SANITY = 100;

    public static CompoundTag getSanityTag(IEntityDataSaver player) {
        return player.impl$getPersistentData();
    }

    public static int getSanity(IEntityDataSaver player) {
        CompoundTag nbt = getSanityTag(player);
        if (!nbt.contains(SANITY, Tag.TAG_INT)) nbt.putInt(SANITY, 100);
        return nbt.getInt(SANITY);
    }

    public static int decreaseSanity(IEntityDataSaver player, int amount) {
        final int currentSanity = getSanity(player);
        final int newSanity = currentSanity - amount;
        getSanityTag(player).putInt(SANITY, Math.max(MINIMUM_SANITY, newSanity));
        return newSanity; // to synch
    }

    public static int increaseSanity(IEntityDataSaver player, int amount) {
        final int currentSanity = getSanity(player);
        final int newSanity = currentSanity + amount;
        getSanityTag(player).putInt(SANITY, Math.min(MAXIMUM_SANITY, newSanity));
        return newSanity; // to synch
    }
}
