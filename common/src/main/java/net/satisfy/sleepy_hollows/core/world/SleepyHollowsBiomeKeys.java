package net.satisfy.sleepy_hollows.core.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.satisfy.sleepy_hollows.Constants;

public class SleepyHollowsBiomeKeys {
    public static final ResourceKey<Biome> SLEEPY_HOLLOWS = register();

    private static ResourceKey<Biome> register() {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Constants.MOD_ID, "sleepy_hollows"));
    }
}
