package net.satisfy.sleepy_hollows.fabric;

import net.fabricmc.api.ModInitializer;

import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.CompostableRegistry;

public final class SleepyHollowsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        SleepyHollows.init();
        SleepyHollows.commonInit();
        CompostableRegistry.init();

        Constants.LOG.info("Initialized the mod in Fabric.");
    }
}