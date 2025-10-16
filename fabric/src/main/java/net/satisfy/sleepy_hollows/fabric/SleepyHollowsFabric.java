package net.satisfy.sleepy_hollows.fabric;

import net.fabricmc.api.ModInitializer;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.CompostableRegistry;
import net.satisfy.sleepy_hollows.fabric.config.ConfigManager;

public final class SleepyHollowsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigManager.registerConfig();

        SleepyHollows.init();
        CompostableRegistry.init();
    }
}
