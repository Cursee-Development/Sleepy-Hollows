package net.satisfy.sleepy_hollows.fabric.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class  ConfigManager {
    private static boolean isRegistered = false;

    public static void registerConfig() {
        if (!isRegistered) {
            AutoConfig.register(SleepyHollowsFabricConfig.class, GsonConfigSerializer::new);
            isRegistered = true;
        }
    }
}
