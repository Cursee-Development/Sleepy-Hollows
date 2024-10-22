package net.satisfy.sleepy_hollows.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.sleepy_hollows.client.SleepyHollowsClient;
import net.satisfy.sleepy_hollows.core.registry.FluidRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.fabric.client.renderer.HauntboundBootsRenderer;
import net.satisfy.sleepy_hollows.fabric.client.renderer.HauntboundChestplateRenderer;
import net.satisfy.sleepy_hollows.fabric.client.renderer.HauntboundHelmetRenderer;
import net.satisfy.sleepy_hollows.fabric.client.renderer.HauntboundLeggingsRenderer;


public final class SleepyHollowsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SleepyHollowsClient.initClient();
        SleepyHollowsClient.preInitClient();

        ArmorRenderer.register(new HauntboundHelmetRenderer(), ObjectRegistry.HAUNTBOUND_HELMET.get());
        ArmorRenderer.register(new HauntboundChestplateRenderer(), ObjectRegistry.HAUNTBOUND_CHESTPLATE.get());
        ArmorRenderer.register(new HauntboundLeggingsRenderer(), ObjectRegistry.HAUNTBOUND_LEGGINGS.get());
        ArmorRenderer.register(new HauntboundBootsRenderer(), ObjectRegistry.HAUNTBOUND_BOOTS.get());

        FluidRenderHandlerRegistry.INSTANCE.register(
                FluidRegistry.LUMINOUS_WATER_SOURCE.get(),
                FluidRegistry.LUMINOUS_WATER_FLOWING.get(),
                new SimpleFluidRenderHandler(
                        new ResourceLocation("sleepy_hollows", "block/luminous_water_still"),
                        new ResourceLocation("sleepy_hollows", "block/luminous_water_flow")
                )
        );
    }
}
