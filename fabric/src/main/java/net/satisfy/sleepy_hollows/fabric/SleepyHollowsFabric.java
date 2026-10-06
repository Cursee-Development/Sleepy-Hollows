package net.satisfy.sleepy_hollows.fabric;

import net.fabricmc.api.ModInitializer;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.CompostableRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.satisfy.sleepy_hollows.fabric.config.SleepyHollowsFabricConfig;

public final class SleepyHollowsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AutoConfig.register(SleepyHollowsFabricConfig.class, GsonConfigSerializer::new);

        SleepyHollows.init();
        CompostableRegistry.init();
    }
}
