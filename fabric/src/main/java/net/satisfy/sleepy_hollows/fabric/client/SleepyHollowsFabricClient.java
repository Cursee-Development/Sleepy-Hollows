package net.satisfy.sleepy_hollows.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.satisfy.sleepy_hollows.Constants;

public final class SleepyHollowsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Constants.LOG.info("Initialized the mod in Fabric Client.");
    }
}
