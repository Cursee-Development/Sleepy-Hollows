package net.satisfy.sleepy_hollows.client;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;

import static net.satisfy.sleepy_hollows.core.registry.ObjectRegistry.*;


@Environment(EnvType.CLIENT)
public class SleepyHollowsClient {
    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(),
                GRAVE_LILY.get(), POTTED_GRAVE_LILY.get(), DREAMSHADE.get(), POTTED_DREAMSHADE.get(), TALL_DREAMSHADE.get(),
                HOLLOW_SAPLING.get(), POTTED_HOLLOW_SAPLING.get(), HOLLOW_TRAPDOOR.get(), HOLLOW_DOOR.get(), MOONVEIL_GRASS.get(),
                HOLLOW_WINDOW.get()
        );
    }
}

