package net.satisfy.sleepy_hollows.neoforge.client;

import net.satisfy.foundation.neoforge.client.FoundationArmorExtensions;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.client.SleepyHollowsClient;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = SleepyHollows.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class SleepyHollowsClientForge {
    @SubscribeEvent
    public static void beforeClientSetup(RegisterEvent event) {
        SleepyHollowsClient.preInitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        SleepyHollowsClient.initClient();
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(FoundationArmorExtensions.INSTANCE, ObjectRegistry.HAUNTBOUND_HELMET.get(), ObjectRegistry.HAUNTBOUND_CHESTPLATE.get(), ObjectRegistry.HAUNTBOUND_LEGGINGS.get(), ObjectRegistry.HAUNTBOUND_BOOTS.get());
    }
}
