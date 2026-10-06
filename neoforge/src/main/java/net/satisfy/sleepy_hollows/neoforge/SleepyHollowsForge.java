package net.satisfy.sleepy_hollows.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.CompostableRegistry;
import net.satisfy.sleepy_hollows.neoforge.config.SleepyHollowsNeoForgeConfig;


@Mod(SleepyHollows.MOD_ID)
public final class SleepyHollowsForge {
    public SleepyHollowsForge(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.STARTUP, SleepyHollowsNeoForgeConfig.SPEC);
        SleepyHollows.init();
        modEventBus.addListener(this::onCommonSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::init);
    }
}