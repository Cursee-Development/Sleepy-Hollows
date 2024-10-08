package net.satisfy.sleepy_hollows.core.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;

public class SleepyHollowsSurfaceRules {

    private static final ResourceKey<NormalNoise.NoiseParameters> SURFACE = register("surface");
    private static final ResourceKey<NormalNoise.NoiseParameters> DIRT = register("dirt");
    private static final ResourceKey<NormalNoise.NoiseParameters> CRACKS = register("cracks");

    private static final SurfaceRules.RuleSource STONE = makeStateRule(ObjectRegistry.GRAVESTONE.get());
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    private static final SurfaceRules.RuleSource COBBLED_GRAVESTONE = makeStateRule(ObjectRegistry.COBBLED_GRAVESTONE.get());

    public static final ResourceKey<Biome> SLEEPY_HOLLOWS_KEY = createBiomeKey();

    private static ResourceKey<Biome> createBiomeKey() {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(Constants.MOD_ID, "sleepy_hollows"));
    }

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassWithCoarseDirt = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, -0.05, 0.05), COARSE_DIRT),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_BLOCK)
        );

        SurfaceRules.RuleSource cobbledGravestoneWithCoarseDirt = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, -0.2, 0.2), COARSE_DIRT),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, 0.2, Double.parseDouble("1.7976931348623157e+308")), COBBLED_GRAVESTONE)
        );

        SurfaceRules.RuleSource sleepy_hollows = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(CRACKS, -0.05, 0.05),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(CRACKS, -0.01, 0.01), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, COARSE_DIRT)),
                                grassWithCoarseDirt)),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, 0, Double.parseDouble("1.7976931348623157e+308")), SurfaceRules.ifTrue(SurfaceRules.noiseCondition(DIRT, 0.3, Double.parseDouble("1e+308")), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, cobbledGravestoneWithCoarseDirt))),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, 0.2, Double.parseDouble("1.7976931348623157e+308")), grassWithCoarseDirt),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, 0.2, Double.parseDouble("1.7976931348623157e+308")), STONE),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE, 0, Double.parseDouble("1.7976931348623157e+308")), SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, cobbledGravestoneWithCoarseDirt))
        );

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(SLEEPY_HOLLOWS_KEY), SurfaceRules.ifTrue(isAtOrAboveWaterLevel,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.steep(), STONE), sleepy_hollows))));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    private static ResourceKey<NormalNoise.NoiseParameters> register(String name) {
        return ResourceKey.create(Registries.NOISE, new ResourceLocation(Constants.MOD_ID, name));
    }
}
