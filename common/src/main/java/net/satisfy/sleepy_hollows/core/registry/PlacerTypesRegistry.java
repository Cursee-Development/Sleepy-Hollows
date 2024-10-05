package net.satisfy.sleepy_hollows.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.world.placers.HollowFoliagePlacer;

public class PlacerTypesRegistry {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(Constants.MOD_ID, Registries.FOLIAGE_PLACER_TYPE);

    public static final RegistrySupplier<FoliagePlacerType<HollowFoliagePlacer>> HOLLOW_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("hollow_foliage_placer", () -> new FoliagePlacerType<>(HollowFoliagePlacer.CODEC));

    public static void init() {
        FOLIAGE_PLACER_TYPES.register();
    }
}