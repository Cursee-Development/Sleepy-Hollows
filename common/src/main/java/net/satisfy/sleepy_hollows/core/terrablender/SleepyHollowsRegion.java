package net.satisfy.sleepy_hollows.core.terrablender;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsSurfaceRules;
import terrablender.api.*;

import java.util.List;
import java.util.function.Consumer;

public class SleepyHollowsRegion extends Region {

    public SleepyHollowsRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addModifiedVanillaOverworldBiomes(mapper, builder -> {

            List<Climate.ParameterPoint> sleepyHollowsPoints = new ParameterUtils.ParameterPointListBuilder()
                    .temperature(Climate.Parameter.span(-0.45F, -0.15F), Climate.Parameter.span(-0.15F, 0.2F))
                    .humidity(Climate.Parameter.span(-0.35F, -0.1F), Climate.Parameter.span(-0.1F, 0.1F), Climate.Parameter.span(0.1F, 0.3F))
                    .continentalness(ParameterUtils.Continentalness.MID_INLAND)
                    .erosion(ParameterUtils.Erosion.FULL_RANGE)
                    .depth(ParameterUtils.Depth.SURFACE)
                    .weirdness(ParameterUtils.Weirdness.MID_SLICE_NORMAL_DESCENDING)
                    .build();

            sleepyHollowsPoints.forEach(point -> builder.replaceBiome(point, SleepyHollowsSurfaceRules.SLEEPY_HOLLOWS_KEY));
        });
    }

    public static void loadTerrablender() {
        Regions.register(new SleepyHollowsRegion(new ResourceLocation(Constants.MOD_ID, "overworld"), 5));
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Constants.MOD_ID, SleepyHollowsSurfaceRules.makeRules());
    }
}
