package net.satisfy.sleepy_hollows;

import net.satisfy.sleepy_hollows.core.registry.*;

public final class SleepyHollows {

    public static void init() {
        ObjectRegistry.init();
        CompostableRegistry.init();
        TabRegistry.init();
        EntityTypeRegistry.init();
        PlacerTypesRegistry.init();

        Constants.LOG.info("Initialized the mod in Common.");
    }
}
