package net.satisfy.sleepy_hollows.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.client.SleepyHollowsClient;

public final class SleepyHollowsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SleepyHollowsClient.initClient();
        Constants.LOG.info("Initialized the mod in Fabric Client.");
    }
}
