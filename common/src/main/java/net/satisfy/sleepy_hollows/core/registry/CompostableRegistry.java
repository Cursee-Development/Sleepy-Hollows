package net.satisfy.sleepy_hollows.core.registry;

import net.satisfy.foundation.compostable.FoundationCompostables;

public class CompostableRegistry {
    public static void init() {
        FoundationCompostables.register(0.25F, ObjectRegistry.HOLLOW_SAPLING);
        FoundationCompostables.register(0.4F, ObjectRegistry.HOLLOW_LEAVES);
        FoundationCompostables.register(0.25F, ObjectRegistry.GRAVE_LILY);
        FoundationCompostables.register(0.25F, ObjectRegistry.DREAMSHADE);
        FoundationCompostables.register(0.25F, ObjectRegistry.SHADOWBLOOM);
        FoundationCompostables.register(0.35F, ObjectRegistry.TALL_DREAMSHADE);
        FoundationCompostables.register(0.5F, ObjectRegistry.SPECTRAL_PUMPKIN);
        FoundationCompostables.register(0.5F, ObjectRegistry.SPECTRAL_CARVED_PUMPKIN);
        FoundationCompostables.register(0.5F, ObjectRegistry.SPECTRAL_JACK_O_LANTERN);
        FoundationCompostables.register(0.1F, ObjectRegistry.CANDY_CORN);
        FoundationCompostables.register(0.1F, ObjectRegistry.DUSK_BERRIES);
        FoundationCompostables.register(0.15F, ObjectRegistry.LUMINOUS_WATER);
        FoundationCompostables.register(0.35F, ObjectRegistry.SPECTRAL_PUMPKIN_PIE);
    }
}
