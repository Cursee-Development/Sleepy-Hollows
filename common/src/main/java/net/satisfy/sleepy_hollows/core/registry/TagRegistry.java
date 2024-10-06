package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;

@SuppressWarnings("unused")
public class TagRegistry {
    public static final TagKey<Block> RESET_SANITY = TagKey.create(Registries.BLOCK, new SleepyHollowsIdentifier("reset_sanity"));
    public static final TagKey<Block> INCREASE_SANITY = TagKey.create(Registries.BLOCK, new SleepyHollowsIdentifier("increase_sanity"));
}
