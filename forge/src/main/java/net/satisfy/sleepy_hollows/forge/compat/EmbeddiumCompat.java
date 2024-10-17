package net.satisfy.sleepy_hollows.forge.compat;

import net.satisfy.sleepy_hollows.client.renderer.LuminousWaterRenderer;
import net.satisfy.sleepy_hollows.core.registry.FluidRegistry;
import org.embeddedt.embeddium.api.BlockRendererRegistry;

public class EmbeddiumCompat {
    public static void init() {
        BlockRendererRegistry.instance().registerRenderPopulator((resultList, ctxDumb) -> {
            if (ctxDumb.state().getFluidState().isSourceOfType(FluidRegistry.LUMINOUS_WATER_SOURCE.get())) {
                resultList.add((ctx, randomSource, vertexConsumer) -> {
                    LuminousWaterRenderer.renderFluid(
                            ctx.state().getFluidState(), ctx.localSlice(),
                            ctx.pos(), vertexConsumer, ctx.state()
                    );
                    return BlockRendererRegistry.RenderResult.OVERRIDE;
                });
            }
        });
    }
}
