package net.satisfy.sleepy_hollows.client;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.satisfy.foundation.ambient.FireflyAmbience;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.satisfy.foundation.banner.CompletionistBannerRenderer;
import net.satisfy.foundation.client.armor.ArmorModels;
import net.satisfy.foundation.tooltip.InfoTooltip;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundBootsModel;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundChestplateModel;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundHelmetModel;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundLeggingsModel;
import net.satisfy.sleepy_hollows.client.model.entity.FleeingPumpkinHeadModel;
import net.satisfy.sleepy_hollows.client.model.entity.HorsemanModel;
import net.satisfy.sleepy_hollows.client.model.entity.SpectralHorseModel;
import net.satisfy.sleepy_hollows.client.renderer.*;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;

import static net.satisfy.sleepy_hollows.core.registry.ObjectRegistry.*;


@Environment(EnvType.CLIENT)
public class SleepyHollowsClient {

    public static void initClient() {
        SanityAmbience.init();
        HollowFog.init();
        FireflyAmbience.init(() -> true);
        InfoTooltip.of(LUMINOUS_WATER_SPLASH.get()).line("tooltip.sleepy_hollows.item.splash_luminous_water").register();
        InfoTooltip.of(LUMINOUS_WATER.get()).line("tooltip.sleepy_hollows.item.luminous_water").register();
        InfoTooltip.of(REINS_OF_THE_SPECTRAL_HORSE.get()).line("tooltip.sleepy_hollows.lore.reins_of_the_spectral_horse").register();
        InfoTooltip.of(CANDY_CORN.get()).line("tooltip.sleepy_hollows.item.candy_corn").register();
        InfoTooltip.of(RAUBBAU.get()).line("tooltip.sleepy_hollows.lore.raubbau").register();
        InfoTooltip.of(SHATTERBRAND.get()).line("tooltip.sleepy_hollows.lore.shatterbrand").register();
        InfoTooltip.of(SPECTRAL_PUMPKIN_PIE.get()).line("tooltip.sleepy_hollows.item.spectral_pumpkin_pie").register();
        InfoTooltip.of(LOOTBAG.get()).line("tooltip.sleepy_hollows.item.lootbag").register();
        InfoTooltip.of(SPECTRAL_WARAXE.get()).line("tooltip.sleepy_hollows.lore.spectral_waraxe").register();
        InfoTooltip.of(DUSK_BERRIES.get()).line("tooltip.sleepy_hollows.item.dusk_berry").register();
        RenderTypeRegistry.register(RenderType.cutout(), GRAVE_LILY.get(), POTTED_GRAVE_LILY.get(), DREAMSHADE.get(), POTTED_DREAMSHADE.get(), TALL_DREAMSHADE.get(), HOLLOW_SAPLING.get(), POTTED_HOLLOW_SAPLING.get(), HOLLOW_TRAPDOOR.get(), HOLLOW_DOOR.get(), MOONVEIL_GRASS.get(), HOLLOW_WINDOW.get(), TALL_MOONVEIL_GRASS.get(), SHADOWBLOOM.get(), POTTED_SHADOWBLOOM.get(), DUSKBERRY_BUSH.get(), SPECTRAL_LANTERN.get(), WROUGHT_IRON_FENCE.get());

        BlockEntityRendererRegistry.register(EntityTypeRegistry.DISPLAY_BLOCK_ENTITY.get(), context -> new PedestalBlockRenderer());
        BlockEntityRendererRegistry.register(EntityTypeRegistry.COFFIN_BLOCK_ENTITY.get(), CoffinRenderer::new);
        BlockEntityRendererRegistry.register(EntityTypeRegistry.COMPLETIONIST_BANNER_ENTITY.get(), CompletionistBannerRenderer::new);

        ArmorModels.register(HauntboundHelmetModel.LAYER_LOCATION, HauntboundHelmetModel::new, HAUNTBOUND_HELMET.get());
        ArmorModels.register(HauntboundChestplateModel.LAYER_LOCATION, HauntboundChestplateModel::new, HAUNTBOUND_CHESTPLATE.get());
        ArmorModels.register(HauntboundLeggingsModel.LAYER_LOCATION, HauntboundLeggingsModel::new, HAUNTBOUND_LEGGINGS.get());
        ArmorModels.register(HauntboundBootsModel.LAYER_LOCATION, HauntboundBootsModel::new, HAUNTBOUND_BOOTS.get());
    }

    public static void preInitClient() {
        EntityModelLayerRegistry.register(CoffinRenderer.LAYER_LOCATION, CoffinRenderer::getTexturedModelData);
        EntityModelLayerRegistry.register(SpectralHorseModel.LAYER_LOCATION, SpectralHorseModel::getTexturedModelData);
        EntityModelLayerRegistry.register(FleeingPumpkinHeadModel.LAYER_LOCATION, FleeingPumpkinHeadModel::getTexturedModelData);
        EntityModelLayerRegistry.register(HorsemanModel.LAYER_LOCATION, HorsemanModel::getTexturedModelData);
        EntityModelLayerRegistry.register(HorsemanModel.SHIELD_LAYER_LOCATION, HorsemanModel::getShieldModelData);
        EntityModelLayerRegistry.register(HauntboundHelmetModel.LAYER_LOCATION, HauntboundHelmetModel::createBodyLayer);
        EntityModelLayerRegistry.register(HauntboundChestplateModel.LAYER_LOCATION, HauntboundChestplateModel::createBodyLayer);
        EntityModelLayerRegistry.register(HauntboundLeggingsModel.LAYER_LOCATION, HauntboundLeggingsModel::createBodyLayer);
        EntityModelLayerRegistry.register(HauntboundBootsModel.LAYER_LOCATION, HauntboundBootsModel::createBodyLayer);

        EntityRendererRegistry.register(EntityTypeRegistry.SPECTRAL_HORSE, SpectralHorseRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.INFECTED_ZOMBIE, InfectedZombieRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.FLEEING_PUMPKIN_HEAD, FleeingPumpkinHeadRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.HORSEMAN, HorsemanRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.SPLASH_LUMINOUS_WATER, ThrownItemRenderer::new);
    }
}

