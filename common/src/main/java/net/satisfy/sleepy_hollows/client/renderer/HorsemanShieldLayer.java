package net.satisfy.sleepy_hollows.client.renderer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.sleepy_hollows.client.model.entity.HorsemanModel;
import net.satisfy.sleepy_hollows.core.entity.Horseman;
import org.jetbrains.annotations.NotNull;

public class HorsemanShieldLayer<T extends Horseman> extends EnergySwirlLayer<T, HorsemanModel<T>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/wither/wither_armor.png");
    private final HorsemanModel<T> model;

    public HorsemanShieldLayer(RenderLayerParent<T, HorsemanModel<T>> parent, EntityModelSet models) {
        super(parent);
        this.model = new HorsemanModel<>(models.bakeLayer(HorsemanModel.SHIELD_LAYER_LOCATION));
    }

    @Override
    protected float xOffset(float ticks) {
        return (float) Math.cos(ticks * 0.02F) * 3.0F;
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation() {
        return TEXTURE;
    }

    @Override
    protected @NotNull EntityModel<T> model() {
        return this.model;
    }
}
