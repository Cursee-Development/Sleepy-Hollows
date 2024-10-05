package net.satisfy.sleepy_hollows.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class PlatformHelper {

    @ExpectPlatform
    public static String getGameDirectory() {
        throw new AssertionError();
    }
}
