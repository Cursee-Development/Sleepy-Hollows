package net.satisfy.sleepy_hollows.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.sleepy_hollows.client.model.entity.FleeingPumpkinHeadModel;
import net.satisfy.sleepy_hollows.core.entity.FleeingPumpkinHead;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class FleeingPumpkinHeadRenderer<T extends FleeingPumpkinHead> extends MobRenderer<T, FleeingPumpkinHeadModel<T>> {
    private static final ResourceLocation TEXTURE = new SleepyHollowsIdentifier("textures/entity/fleeing_pumpkin_head.png");

    public FleeingPumpkinHeadRenderer(EntityRendererProvider.Context context) {
        super(context, new FleeingPumpkinHeadModel<>(context.bakeLayer(FleeingPumpkinHeadModel.LAYER_LOCATION)), 0.75f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FleeingPumpkinHead entity) {
        return TEXTURE;
    }

}
