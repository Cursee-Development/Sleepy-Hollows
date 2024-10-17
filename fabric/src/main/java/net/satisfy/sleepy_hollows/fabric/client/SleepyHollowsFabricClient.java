package net.satisfy.sleepy_hollows.fabric.client;

import dev.architectury.event.events.client.ClientTickEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.satisfy.sleepy_hollows.client.SleepyHollowsClient;
import net.satisfy.sleepy_hollows.core.registry.FluidRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.fabric.client.renderer.*;
import net.satisfy.sleepy_hollows.fabric.client.renderer.FabricLWRenderWrapper;


public final class SleepyHollowsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SleepyHollowsClient.initClient();
        SleepyHollowsClient.preInitClient();

        ArmorRenderer.register(new HauntboundHelmetRenderer(), ObjectRegistry.HAUNTBOUND_HELMET.get());
        ArmorRenderer.register(new HauntboundChestplateRenderer(), ObjectRegistry.HAUNTBOUND_CHESTPLATE.get());
        ArmorRenderer.register(new HauntboundLeggingsRenderer(), ObjectRegistry.HAUNTBOUND_LEGGINGS.get());
        ArmorRenderer.register(new HauntboundBootsRenderer(), ObjectRegistry.HAUNTBOUND_BOOTS.get());

        FluidRenderHandlerRegistry.INSTANCE.register(FluidRegistry.LUMINOUS_WATER_SOURCE.get(), new FabricLWRenderWrapper());
        FluidRenderHandlerRegistry.INSTANCE.register(FluidRegistry.LUMINOUS_WATER_FLOWING.get(), new FabricLWRenderWrapper());

        ClientTickEvent.CLIENT_PRE.register(SleepyHollowsClient::onClientTick);
    }
}
