package net.satisfy.sleepy_hollows.client;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.satisfy.sleepy_hollows.client.model.SpectralHorseModel;
import net.satisfy.sleepy_hollows.client.renderer.CoffinRenderer;
import net.satisfy.sleepy_hollows.client.renderer.PedestalBlockRenderer;
import net.satisfy.sleepy_hollows.client.renderer.SpectralHorseRenderer;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;

import static net.satisfy.sleepy_hollows.core.registry.ObjectRegistry.*;


@Environment(EnvType.CLIENT)
public class SleepyHollowsClient {
    public static void initClient() {
        RenderTypeRegistry.register(RenderType.cutout(),
                GRAVE_LILY.get(), POTTED_GRAVE_LILY.get(), DREAMSHADE.get(), POTTED_DREAMSHADE.get(), TALL_DREAMSHADE.get(),
                HOLLOW_SAPLING.get(), POTTED_HOLLOW_SAPLING.get(), HOLLOW_TRAPDOOR.get(), HOLLOW_DOOR.get(), MOONVEIL_GRASS.get(),
                HOLLOW_WINDOW.get(), TALL_MOONVEIL_GRASS.get(), SHADOWBLOOM.get(), POTTED_SHADOWBLOOM.get(), DUSKBERRY_BUSH.get(),
                SPECTRAL_LANTERN.get()
        );

        BlockEntityRendererRegistry.register(EntityTypeRegistry.DISPLAY_BLOCK_ENTITY.get(), context -> new PedestalBlockRenderer());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.COFFIN_BLOCK_ENTITY.get(), CoffinRenderer::new);

    }

    public static void PreinitClient() {
        EntityModelLayerRegistry.register(CoffinRenderer.LAYER_LOCATION, CoffinRenderer::getTexturedModelData);
        EntityModelLayerRegistry.register(SpectralHorseModel.LAYER_LOCATION, SpectralHorseModel::getTexturedModelData);
        EntityRendererRegistry.register(EntityTypeRegistry.SPECTRAL_HORSE, SpectralHorseRenderer::new);

    }
}

