package net.satisfy.sleepy_hollows.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.world.decorators.SpectralLanternDecorator;
import net.satisfy.sleepy_hollows.core.world.placers.HollowFoliagePlacer;

public class FeatureTypeRegistry {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(Constants.MOD_ID, Registries.FOLIAGE_PLACER_TYPE);
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(Constants.MOD_ID, Registries.TREE_DECORATOR_TYPE);

    public static final RegistrySupplier<FoliagePlacerType<HollowFoliagePlacer>> HOLLOW_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("hollow_foliage_placer", () -> new FoliagePlacerType<>(HollowFoliagePlacer.CODEC));
    public static final RegistrySupplier<TreeDecoratorType<SpectralLanternDecorator>> SPECTRAL_LANTERN_DECORATOR = TREE_DECORATOR_TYPES.register("spectral_lantern_decorator", () -> new TreeDecoratorType<>(SpectralLanternDecorator.CODEC));

    public static void init() {
        FOLIAGE_PLACER_TYPES.register();
        TREE_DECORATOR_TYPES.register();
    }
}