package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.item.Item;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundHelmetModel;

import java.util.HashMap;
import java.util.Map;

public class ArmorRegistry {
    private static final Map<Item, HauntboundHelmetModel<?>> models = new HashMap<>();

    public static Model getHatModel(Item item, ModelPart baseHead) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        HauntboundHelmetModel<?> model = models.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.HAUNTBOUND_HELMET.get()) {
                return new HauntboundHelmetModel<>(modelSet.bakeLayer(HauntboundHelmetModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        assert model != null;
        model.copyHead(baseHead);

        return model;
    }
}
