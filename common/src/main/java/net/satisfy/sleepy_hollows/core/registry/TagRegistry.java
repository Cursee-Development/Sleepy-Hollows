package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.satisfy.sleepy_hollows.SleepyHollows;

public class TagRegistry {
    public static final TagKey<Block> RESET_SANITY = TagKey.create(Registries.BLOCK, SleepyHollows.identifier("reset_sanity"));
    public static final TagKey<Block> DECREASE_SANITY = TagKey.create(Registries.BLOCK, SleepyHollows.identifier("decrease_sanity"));
}
