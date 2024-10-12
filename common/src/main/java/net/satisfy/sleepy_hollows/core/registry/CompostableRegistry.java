package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.world.level.block.ComposterBlock;

public class CompostableRegistry {

    public static void init() {
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.HOLLOW_SAPLING.get().asItem(), 0.25F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.HOLLOW_LEAVES.get().asItem(), 0.4F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.GRAVE_LILY.get().asItem(), 0.25F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.DREAMSHADE.get().asItem(), 0.25F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SHADOWBLOOM.get().asItem(), 0.25F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.TALL_DREAMSHADE.get().asItem(), 0.35F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SPECTRAL_PUMPKIN.get().asItem(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SPECTRAL_CARVED_PUMPKIN.get().asItem(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SPECTRAL_JACK_O_LANTERN.get().asItem(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.CANDY_CORN.get().asItem(), 0.1F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.DUSK_BERRIES.get().asItem(), 0.1F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.LUMINOUS_WATER.get().asItem(), 0.15F);
        ComposterBlock.COMPOSTABLES.put(ObjectRegistry.SPECTRAL_PUMPKIN_PIE.get().asItem(), 0.35F);
    }
}
