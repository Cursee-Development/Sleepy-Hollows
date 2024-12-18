package net.satisfy.sleepy_hollows.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.CompostableRegistry;

public final class SleepyHollowsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        SleepyHollows.init();
        SleepyHollows.commonInit();
        CompostableRegistry.init();
        ServerTickEvents.START_SERVER_TICK.register(SleepyHollows::onServerTick);

        Constants.LOG.info("Sleepy Hollows initialized successfully on the Fabric platform.");
    }
}