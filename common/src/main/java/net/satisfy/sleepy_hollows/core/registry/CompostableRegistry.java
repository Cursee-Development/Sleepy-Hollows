package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {

    public static void init() {
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.HOLLOW_SAPLING.get().asItem(), 0.25F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.HOLLOW_LEAVES.get().asItem(), 0.4F);
    }
}
