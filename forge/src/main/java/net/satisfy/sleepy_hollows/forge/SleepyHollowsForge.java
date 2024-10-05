package net.satisfy.sleepy_hollows.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.SleepyHollows;

@Mod(Constants.MOD_ID)
public final class SleepyHollowsForge {

    public SleepyHollowsForge() {
        EventBuses.registerModEventBus(Constants.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        SleepyHollows.init();
        Constants.LOG.info("Initialized the mod in Forge.");
    }
}
