package net.satisfy.sleepy_hollows.core.util;

import net.minecraft.resources.ResourceLocation;
import net.satisfy.sleepy_hollows.Constants;

public class SleepyHollowsIdentifier extends ResourceLocation {
    public SleepyHollowsIdentifier(String path) {
        super(Constants.MOD_ID, path);
    }
}
