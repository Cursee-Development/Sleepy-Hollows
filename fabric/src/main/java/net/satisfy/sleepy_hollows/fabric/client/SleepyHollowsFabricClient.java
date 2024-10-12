package net.satisfy.sleepy_hollows.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.client.SleepyHollowsClient;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.fabric.client.renderer.HauntboundHelmetRenderer;

public final class SleepyHollowsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SleepyHollowsClient.initClient();
        SleepyHollowsClient.PreinitClient();

        ArmorRenderer.register(new HauntboundHelmetRenderer(), ObjectRegistry.HAUNTBOUND_HELMET.get());

        Constants.LOG.info("Initialized the mod in Fabric Client.");
    }
}
