package net.satisfy.sleepy_hollows.forge;

import com.mojang.datafixers.util.Pair;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.client.Minecraft;
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
import net.satisfy.sleepy_hollows.client.SleepyHollowsClient;
import net.satisfy.sleepy_hollows.core.registry.CompostableRegistry;
import net.satisfy.sleepy_hollows.core.registry.FluidRegistry;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsRegion;
import net.satisfy.sleepy_hollows.platform.forge.ForgeLuminousWater;

@Mod(Constants.MOD_ID)
public final class SleepyHollowsForge {

    public SleepyHollowsForge() {
        EventBuses.registerModEventBus(Constants.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        FluidRegistry.LUMINOUS_WATER = Pair.of(
                ForgeLuminousWater.Source::new,
                ForgeLuminousWater.Flowing::new
        );
        SleepyHollows.init();
        Constants.LOG.info("Initialized the mod in Forge.");

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.register(this);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::onClientTick);
        modEventBus.addListener(this::onServerTick);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(SleepyHollowsRegion::loadTerrablender);
        SleepyHollows.commonInit();
    }

    @SubscribeEvent
    public void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(SleepyHollows::commonInit);
    }

    @SubscribeEvent
    public void onLoadComplete(final FMLLoadCompleteEvent event) {
        event.enqueueWork(CompostableRegistry::init);
    }

    private void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.side == LogicalSide.CLIENT) return;
        if (event.phase == TickEvent.Phase.END) return;

        Minecraft instance = Minecraft.getInstance();

        SleepyHollowsClient.onClientTick(instance);
    }

    private void onServerTick(final TickEvent.ServerTickEvent event) {

        if (event.side == LogicalSide.CLIENT) return;
        if (event.phase == TickEvent.Phase.END) return;

        MinecraftServer server = event.getServer();
        SleepyHollows.onServerTick(server);
    }
}
