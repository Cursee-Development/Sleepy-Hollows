package net.satisfy.sleepy_hollows.platform.forge;


import net.minecraftforge.common.extensions.IForgeFluid;
import net.minecraftforge.fluids.FluidType;
import net.satisfy.sleepy_hollows.core.block.custom.LuminousWaterFluid;

public final class ForgeLuminousWater {
    public static class Source extends LuminousWaterFluid.Source implements IForgeFluid {

        @Override
        public FluidType getFluidType() {
            return new FluidType(FluidType.Properties.create());
        }
    }

    public static class Flowing extends LuminousWaterFluid.Flowing implements IForgeFluid {
        @Override
        public FluidType getFluidType() {
            return new FluidType(FluidType.Properties.create());
        }
    }
}
