package net.satisfy.sleepy_hollows.fabric.config;

public class InitializationStatus {
    private static boolean modMenuInitialized = false;

    public static void setModMenuInitialized(boolean initialized) {
        modMenuInitialized = initialized;
    }

    public static boolean isModMenuInitialized() {
        return modMenuInitialized;
    }
}
