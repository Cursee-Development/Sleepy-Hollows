package net.satisfy.sleepy_hollows.client.model.entity;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.satisfy.sleepy_hollows.core.entity.Horseman;

public class HorsemanEnergyLayer<T extends Horseman, M extends HorsemanModel<T>> extends EnergySwirlLayer<T, M> {

    private static final ResourceLocation WITHER_ARMOR_LOCATION = new ResourceLocation("textures/entity/wither/wither_armor.png");
    public final M model;

    public HorsemanEnergyLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = (M) new HorsemanModel<>(modelSet.bakeLayer(ModelLayers.WITHER_ARMOR));
    }

    @Override
    protected float xOffset(float tickCount) {
        return Mth.cos(tickCount * 0.02F) * 3.0F;
    }

    @Override
    protected ResourceLocation getTextureLocation() {
        return WITHER_ARMOR_LOCATION;
    }

    @Override
    protected M model() {
        return model;
    }
}
