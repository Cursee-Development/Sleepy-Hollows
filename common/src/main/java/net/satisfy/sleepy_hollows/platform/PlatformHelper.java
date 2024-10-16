package net.satisfy.sleepy_hollows.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;

public class PlatformHelper {

    @ExpectPlatform
    public static void spawnHolyFluidParticles(Level level, BlockPos pos, FluidState state, RandomSource random, int particleIntensity) {
        throw new AssertionError();
    }
}
