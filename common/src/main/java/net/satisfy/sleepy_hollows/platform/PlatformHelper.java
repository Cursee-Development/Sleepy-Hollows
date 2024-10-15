package net.satisfy.sleepy_hollows.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.satisfy.sleepy_hollows.core.block.custom.LuminousWaterFluid;

import java.util.function.Supplier;

public class PlatformHelper {

    @ExpectPlatform
    public static void spawnHolyFluidParticles(Level level, BlockPos pos, FluidState state, RandomSource random, int particleIntensity) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static LuminousWaterFluid.Source getLuminousWaterSource() { return null; }

    @ExpectPlatform
    public static LuminousWaterFluid.Flowing getLuminousWaterFlowing() { return null; }
}
