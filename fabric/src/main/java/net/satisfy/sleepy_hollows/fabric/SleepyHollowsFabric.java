package net.satisfy.sleepy_hollows.fabric;

import net.fabricmc.api.ModInitializer;

import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.SleepyHollows;

public final class SleepyHollowsFabric implements ModInitializer {

    @Override
    public void onInitialize() {

        SleepyHollows.init();

        Constants.LOG.info("Initialized the mod in Fabric.");
    }
}