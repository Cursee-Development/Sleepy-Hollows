package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

import java.lang.reflect.Method;

import static net.satisfy.sleepy_hollows.core.registry.ObjectRegistry.*;

public class FlammableBlockRegistry {
    public static void init() {
        addFlammable(5, 20,
                HOLLOW_PLANKS.get(), HOLLOW_SLAB.get(), HOLLOW_STAIRS.get(),
                HOLLOW_FENCE.get(), HOLLOW_FENCE_GATE.get(), HOLLOW_WINDOW.get()
        );

        addFlammable(5, 5,
                HOLLOW_LOG.get(), HOLLOW_WOOD.get(), STRIPPED_HOLLOW_LOG.get(), STRIPPED_HOLLOW_WOOD.get()
        );

        addFlammable(30, 60,
                HOLLOW_LEAVES.get()
        );
    }

    @SuppressWarnings("all")
    public static void addFlammable(int burnOdd, int igniteOdd, Block... blocks) {
        FireBlock fireBlock = (FireBlock) Blocks.FIRE;
        try {
            Method setFlammableMethod = FireBlock.class.getDeclaredMethod("setFlammable", Block.class, int.class, int.class);
            setFlammableMethod.setAccessible(true);

            for (Block block : blocks) {
                setFlammableMethod.invoke(fireBlock, block, burnOdd, igniteOdd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
