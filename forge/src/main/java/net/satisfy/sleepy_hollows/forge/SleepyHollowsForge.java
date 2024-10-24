package net.satisfy.sleepy_hollows.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.CompostableRegistry;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsRegion;

@Mod(Constants.MOD_ID)
public final class SleepyHollowsForge {

    public SleepyHollowsForge() {
        EventBuses.registerModEventBus(Constants.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        SleepyHollows.init();
        Constants.LOG.info("Sleepy Hollows initialized successfully on the Forge platform.");

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);

        modEventBus.addListener(this::onCommonSetup);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(SleepyHollowsRegion::loadTerrablender);
        SleepyHollows.commonInit();
    }

    @SubscribeEvent
    public void onLoadComplete(final FMLLoadCompleteEvent event) {
        event.enqueueWork(CompostableRegistry::init);
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class SleepyHollowsEventHandlers {
        @SubscribeEvent
        public static void onServerTick(final TickEvent.ServerTickEvent event) {
            if (event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.START) {
                MinecraftServer server = event.getServer();
                SleepyHollows.onServerTick(server);
            }
        }
    }
}