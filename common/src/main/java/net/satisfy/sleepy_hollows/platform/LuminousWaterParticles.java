package net.satisfy.sleepy_hollows.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.satisfy.sleepy_hollows.core.entity.ThrownLuminousWater;

public class LuminousWaterParticles {

    @ExpectPlatform
    public static void spawnBlock(Level level, BlockPos pos, FluidState state, RandomSource random, int particleIntensity) {
    }

    @ExpectPlatform
    public static void spawnItem(ThrownLuminousWater bottle, RandomSource random) {
    }

}
