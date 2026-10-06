package net.satisfy.sleepy_hollows.core.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;

public final class SleepyHollowsSurfaceRules {

    private static final ResourceKey<NormalNoise.NoiseParameters> SURFACE = noise("surface");
    private static final ResourceKey<NormalNoise.NoiseParameters> DIRT = noise("dirt");
    private static final ResourceKey<NormalNoise.NoiseParameters> CRACKS = noise("cracks");

    public static void register() {
        SurfaceGeneration.addOverworldSurfaceRules(SleepyHollows.identifier("surface_rules"), makeRules());
    }

    private static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.RuleSource stone = makeStateRule(ObjectRegistry.GRAVESTONE.get());
        SurfaceRules.RuleSource grassBlock = makeStateRule(Blocks.GRASS_BLOCK);
        SurfaceRules.RuleSource coarseDirt = makeStateRule(Blocks.COARSE_DIRT);
        SurfaceRules.RuleSource cobbledGravestone = makeStateRule(ObjectRegistry.COBBLED_GRAVESTONE.get());
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassWithCoarseDirt = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, -0.05, 0.05), coarseDirt),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassBlock)
        );

        SurfaceRules.RuleSource cobbledGravestoneWithCoarseDirt = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, -0.2, 0.2), coarseDirt),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, 0.2, Double.MAX_VALUE), cobbledGravestone)
        );

        SurfaceRules.RuleSource sleepyHollows = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(CRACKS, -0.05, 0.05),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(CRACKS, -0.01, 0.01), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, coarseDirt)),
                                grassWithCoarseDirt)),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, 0, Double.MAX_VALUE), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(DIRT, 0.3, Double.MAX_VALUE), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, cobbledGravestoneWithCoarseDirt))),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, 0.2, Double.MAX_VALUE), grassWithCoarseDirt),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, 0.2, Double.MAX_VALUE), stone),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, 0, Double.MAX_VALUE), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, cobbledGravestoneWithCoarseDirt))
        );

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(SleepyHollows.SLEEPY_HOLLOWS_BIOME), SurfaceRules.ifTrue(isAtOrAboveWaterLevel,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.steep(), stone), sleepyHollows))));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    private static ResourceKey<NormalNoise.NoiseParameters> noise(String name) {
        return ResourceKey.create(Registries.NOISE, SleepyHollows.identifier(name));
    }
}
