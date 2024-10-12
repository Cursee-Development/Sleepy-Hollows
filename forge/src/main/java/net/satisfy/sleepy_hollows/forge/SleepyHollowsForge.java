package net.satisfy.sleepy_hollows.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.CompostableRegistry;

@Mod(Constants.MOD_ID)
public final class SleepyHollowsForge {

    public SleepyHollowsForge() {
        EventBuses.registerModEventBus(Constants.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        SleepyHollows.init();
        SleepyHollows.commonInit();
        Constants.LOG.info("Initialized the mod in Forge.");

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);
    }

    @SubscribeEvent
    public void onLoadComplete(final FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            CompostableRegistry.init();
        });
    }
}
