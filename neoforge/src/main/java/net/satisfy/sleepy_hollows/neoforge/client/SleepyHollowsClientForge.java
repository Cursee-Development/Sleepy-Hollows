package net.satisfy.sleepy_hollows.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.client.SleepyHollowsClient;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.neoforge.client.extensions.HauntboundBootsExtensions;
import net.satisfy.sleepy_hollows.neoforge.client.extensions.HauntboundChestplateExtensions;
import net.satisfy.sleepy_hollows.neoforge.client.extensions.HauntboundHelmetExtensions;
import net.satisfy.sleepy_hollows.neoforge.client.extensions.HauntboundLeggingsExtensions;

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
        event.registerItem(new HauntboundHelmetExtensions(), ObjectRegistry.HAUNTBOUND_HELMET.get());
        event.registerItem(new HauntboundChestplateExtensions(), ObjectRegistry.HAUNTBOUND_CHESTPLATE.get());
        event.registerItem(new HauntboundLeggingsExtensions(), ObjectRegistry.HAUNTBOUND_LEGGINGS.get());
        event.registerItem(new HauntboundBootsExtensions(), ObjectRegistry.HAUNTBOUND_BOOTS.get());
    }
}
