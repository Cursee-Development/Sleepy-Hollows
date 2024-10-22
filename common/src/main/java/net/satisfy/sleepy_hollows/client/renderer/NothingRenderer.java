package net.satisfy.sleepy_hollows.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class NothingRenderer<T extends Entity> extends EntityRenderer<T> {
    public NothingRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(T a, float b, float c, PoseStack d, MultiBufferSource e, int f) {}

    @Override
    public ResourceLocation getTextureLocation(T entity) { return null;}
}
