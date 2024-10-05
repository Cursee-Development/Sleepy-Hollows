package net.satisfy.sleepy_hollows.platform.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class PlatformHelperImpl {

    public static String getGameDirectory() {
        return FabricLoader.getInstance().getGameDir().toString();
    }
}
