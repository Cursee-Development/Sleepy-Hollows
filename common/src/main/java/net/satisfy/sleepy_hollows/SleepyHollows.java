package net.satisfy.sleepy_hollows;

import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.core.registry.TabRegistry;

public final class SleepyHollows {

    public static void init() {

        ObjectRegistry.init();
        TabRegistry.init();
        EntityTypeRegistry.init();

        Constants.LOG.info("Initialized the mod in Common.");
    }
}
