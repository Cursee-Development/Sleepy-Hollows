package net.satisfy.sleepy_hollows;

import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.biome.sub.CriterionBuilder;
import com.terraformersmc.biolith.api.biome.sub.RatioTargets;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.satisfy.sleepy_hollows.core.registry.*;

public final class SleepyHollows {
    public static final String MOD_ID = "sleepy_hollows";
    public static final ResourceKey<Biome> SLEEPY_HOLLOWS_BIOME = ResourceKey.create(Registries.BIOME, identifier("sleepy_hollows"));

    public static ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void init() {
        ObjectRegistry.init();
        TabRegistry.init();
        EntityTypeRegistry.init();
        SoundEventRegistry.init();
        FeatureTypeRegistry.init();

        BiomePlacement.addSubOverworld(Biomes.OLD_GROWTH_PINE_TAIGA, SLEEPY_HOLLOWS_BIOME, CriterionBuilder.allOf(CriterionBuilder.ratioMax(RatioTargets.CENTER, 0.35f), CriterionBuilder.not(CriterionBuilder.neighbor(BiomeTags.IS_RIVER)), CriterionBuilder.not(CriterionBuilder.neighbor(BiomeTags.IS_OCEAN)), CriterionBuilder.not(CriterionBuilder.neighbor(BiomeTags.IS_BEACH))));
    }


    public static void commonInit() {
        FlammableBlockRegistry.init();
        AxeItemHooks.addStrippable(ObjectRegistry.HOLLOW_LOG.get(), ObjectRegistry.STRIPPED_HOLLOW_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.HOLLOW_WOOD.get(), ObjectRegistry.STRIPPED_HOLLOW_WOOD.get());
    }
}