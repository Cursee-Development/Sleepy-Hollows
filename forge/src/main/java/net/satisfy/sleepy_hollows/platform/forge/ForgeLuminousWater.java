package net.satisfy.sleepy_hollows.platform.forge;


import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.extensions.IForgeFluid;
import net.minecraftforge.fluids.FluidType;
import net.satisfy.sleepy_hollows.core.block.custom.LuminousWaterFluid;

public interface ForgeLuminousWater extends IForgeFluid, IClientFluidTypeExtensions {
    class Source extends LuminousWaterFluid.Source implements ForgeLuminousWater {
    }

    class Flowing extends LuminousWaterFluid.Flowing implements ForgeLuminousWater {
    }

    @Override
    default FluidType getFluidType() {
        return new FluidType(FluidType.Properties.create());
    }

    @Override
    default ResourceLocation getStillTexture() {
        return new ResourceLocation("sleepy_hollows", "block/luminous_water_still");
    }

    @Override
    default ResourceLocation getFlowingTexture() {
        return new ResourceLocation("sleepy_hollows", "block/luminous_water_flow");
    }
}
