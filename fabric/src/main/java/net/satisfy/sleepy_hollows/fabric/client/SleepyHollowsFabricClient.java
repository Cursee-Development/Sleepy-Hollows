package net.satisfy.sleepy_hollows.fabric.client;

import net.satisfy.foundation.fabric.client.FoundationArmorRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.satisfy.sleepy_hollows.client.SleepyHollowsClient;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;


public final class SleepyHollowsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SleepyHollowsClient.initClient();
        SleepyHollowsClient.preInitClient();

        ArmorRenderer.register(FoundationArmorRenderer.INSTANCE, ObjectRegistry.HAUNTBOUND_HELMET.get(), ObjectRegistry.HAUNTBOUND_CHESTPLATE.get(), ObjectRegistry.HAUNTBOUND_LEGGINGS.get(), ObjectRegistry.HAUNTBOUND_BOOTS.get());
    }
}
