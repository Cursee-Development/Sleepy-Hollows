package net.satisfy.sleepy_hollows.fabric;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.block.custom.LuminousWaterFluid;
import net.satisfy.sleepy_hollows.core.registry.CompostableRegistry;
import net.satisfy.sleepy_hollows.core.registry.FluidRegistry;

public final class SleepyHollowsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        SleepyHollows.init();
        SleepyHollows.commonInit();
        CompostableRegistry.init();
        ServerTickEvents.START_SERVER_TICK.register(SleepyHollows::onServerTick);

        Constants.LOG.info("Initialized the mod in Fabric.");
    }
}