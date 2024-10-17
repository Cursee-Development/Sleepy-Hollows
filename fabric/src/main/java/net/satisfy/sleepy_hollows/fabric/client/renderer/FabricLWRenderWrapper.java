package net.satisfy.sleepy_hollows.fabric.client.renderer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.satisfy.sleepy_hollows.client.renderer.LuminousWaterRenderer;
import org.jetbrains.annotations.Nullable;

public class FabricLWRenderWrapper implements FluidRenderHandler {
    @Override
    public TextureAtlasSprite[] getFluidSprites(@Nullable BlockAndTintGetter blockAndTintGetter, @Nullable BlockPos blockPos, FluidState fluidState) {
        return new TextureAtlasSprite[0];
    }

    @Override
    public void renderFluid(BlockPos pos, BlockAndTintGetter world, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState) {
        LuminousWaterRenderer.renderFluid(fluidState, world, pos, vertexConsumer, blockState);
    }
}
