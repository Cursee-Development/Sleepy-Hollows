package net.satisfy.sleepy_hollows.fabric.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.satisfy.sleepy_hollows.Constants;

public class ConfigManager {
    private static boolean isRegistered = false;

    public static void registerConfig() {
        if (!isRegistered) {
            AutoConfig.register(SleepyHollowsFabricConfig.class, GsonConfigSerializer::new);
            isRegistered = true;
            Constants.LOG.info("SleepyHollowsFabricConfig wurde erfolgreich registriert.");
        } else {
            Constants.LOG.info("SleepyHollowsFabricConfig ist bereits registriert.");
        }
    }
}
