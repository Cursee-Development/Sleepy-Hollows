package net.satisfy.sleepy_hollows.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.sleepy_hollows.client.model.entity.HorsemanEnergyLayer;
import net.satisfy.sleepy_hollows.client.model.entity.HorsemanModel;
import net.satisfy.sleepy_hollows.core.entity.Horseman;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HorsemanRenderer<T extends Horseman> extends MobRenderer<T, HorsemanModel<T>> {
    private static final ResourceLocation TEXTURE = new SleepyHollowsIdentifier("textures/entity/horseman.png");
    private static final ResourceLocation TEXTURE_PUMPKIN_ACTIVE = new SleepyHollowsIdentifier("textures/entity/real_horseman.png");

    public HorsemanRenderer(EntityRendererProvider.Context context) {
        super(context, new HorsemanModel<>(context.bakeLayer(HorsemanModel.LAYER_LOCATION)), 0.875f);
        //this.layers.add(new HorsemanEnergyLayer<>(this, context.getModelSet()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Horseman entity) {
        return entity.hasActivePumpkinHead() ? TEXTURE_PUMPKIN_ACTIVE : TEXTURE;
    }

    @Override
    public void render(@NotNull T entity, float entityYaw, float partialTicks, @NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);

    }
}
