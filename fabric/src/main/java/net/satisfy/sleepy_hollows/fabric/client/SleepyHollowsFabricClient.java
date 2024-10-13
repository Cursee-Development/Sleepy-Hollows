package net.satisfy.sleepy_hollows.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.client.SleepyHollowsClient;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.fabric.client.renderer.HauntboundHelmetRenderer;
import net.satisfy.sleepy_hollows.fabric.player.layer.HauntboundBootsLayer;
import net.satisfy.sleepy_hollows.fabric.player.layer.HauntboundChestplateLayer;
import net.satisfy.sleepy_hollows.fabric.player.layer.HauntboundLeggingsLayer;
import net.satisfy.sleepy_hollows.fabric.player.model.HauntboundBootsModel;
import net.satisfy.sleepy_hollows.fabric.player.model.HauntboundChestplateModel;
import net.satisfy.sleepy_hollows.fabric.player.model.HauntboundLeggingsModel;


public final class SleepyHollowsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SleepyHollowsClient.initClient();
        SleepyHollowsClient.PreinitClient();

        ArmorRenderer.register(new HauntboundHelmetRenderer(), ObjectRegistry.HAUNTBOUND_HELMET.get());

        EntityModelLayerRegistry.registerModelLayer(SleepyHollowsClient.HAUNTBOUND_CHESTPLATE_LAYER, HauntboundChestplateModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SleepyHollowsClient.HAUNTBOUND_LEGGINGS_LAYER, HauntboundLeggingsModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SleepyHollowsClient.HAUNTBOUND_BOOTS_LAYER, HauntboundBootsModel::createBodyLayer);

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof PlayerRenderer renderer) {
                registrationHelper.register(new HauntboundChestplateLayer<>(renderer));
                registrationHelper.register(new HauntboundLeggingsLayer<>(renderer));
                registrationHelper.register(new HauntboundBootsLayer<>(renderer));
            }
        });

        Constants.LOG.info("Initialized the mod in Fabric Client.");
    }
}
