package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class FlammableBlockRegistry {

    public static void init() {
        addFlammable(5, 20,
                ObjectRegistry.HOLLOW_PLANKS.get(), ObjectRegistry.HOLLOW_SLAB.get(), ObjectRegistry.HOLLOW_STAIRS.get(),
                ObjectRegistry.HOLLOW_FENCE.get(), ObjectRegistry.HOLLOW_FENCE_GATE.get()
        );

        addFlammable(5, 5,
                ObjectRegistry.HOLLOW_LOG.get(), ObjectRegistry.HOLLOW_WOOD.get(), ObjectRegistry.STRIPPED_HOLLOW_LOG.get(), ObjectRegistry.STRIPPED_HOLLOW_WOOD.get()
        );

        addFlammable(30, 60,
                ObjectRegistry.HOLLOW_LEAVES.get()
        );
    }

    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        for (Block block : blocks) {
            fireBlock.setFlammable(block, burnOdd, igniteOdd);
        }
    }
}
