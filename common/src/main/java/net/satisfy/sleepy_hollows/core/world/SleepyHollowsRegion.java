package net.satisfy.sleepy_hollows.core.world;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.satisfy.sleepy_hollows.Constants;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import java.util.function.Consumer;

public class SleepyHollowsRegion extends Region {

    public SleepyHollowsRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        {
            this.addModifiedVanillaOverworldBiomes(mapper, builder -> addBiomeSimilar(mapper, Biomes.TAIGA, SleepyHollowsBiomeKeys.SLEEPY_HOLLOWS));
        }
    }

    public static void loadTerrablender() {
        Regions.register(new SleepyHollowsRegion(new ResourceLocation(Constants.MOD_ID, "overworld"), 5));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Constants.MOD_ID, SleepyHollowsSurfaceRules.makeRules());
    }
}
