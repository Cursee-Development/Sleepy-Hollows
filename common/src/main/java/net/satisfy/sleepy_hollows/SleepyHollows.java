package net.satisfy.sleepy_hollows;

import java.util.List;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.SpawnPlacementTypes;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.biome.sub.CriterionBuilder;
import com.terraformersmc.biolith.api.biome.sub.RatioTargets;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.satisfy.sleepy_hollows.client.event.HUDRenderEvent;
import net.satisfy.sleepy_hollows.core.event.ArmorEffectHandler;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.registry.*;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsSurfaceRules;

public final class SleepyHollows {
    public static final String MOD_ID = "sleepy_hollows";
    public static final String MOD_DATA_ID = MOD_ID + ".data";
    public static final ResourceKey<Biome> SLEEPY_HOLLOWS_BIOME = ResourceKey.create(Registries.BIOME, identifier("sleepy_hollows"));
    private static final List<ResourceKey<Biome>> SLEEPY_HOLLOWS_PARENTS = List.of(Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.TAIGA, Biomes.DARK_FOREST, Biomes.FOREST);

    public static ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void init() {
        MobEffectRegistry.init();
        ObjectRegistry.init();
        ArmorSetRegistry.init();
        TabRegistry.init();
        EntityTypeRegistry.init();
        SoundEventRegistry.init();
        FeatureTypeRegistry.init();
        SleepyHollowsNetwork.init();
        ArmorEffectHandler.init();
        TickEvent.SERVER_POST.register(SleepyHollows::onServerTick);
        PlayerEvent.PLAYER_JOIN.register(SanityManager::sync);
        PlayerEvent.PLAYER_RESPAWN.register((player, conqueredEnd, removalReason) -> SanityManager.sync(player));
        if (Platform.getEnv() == EnvType.CLIENT) {
            ClientGuiEvent.RENDER_HUD.register(HUDRenderEvent::onRenderHUD);
        }
        LifecycleEvent.SETUP.register(SleepyHollows::setupSerial);
        SpawnPlacementsRegistry.register(EntityTypeRegistry.INFECTED_ZOMBIE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);

        for (ResourceKey<Biome> parent : SLEEPY_HOLLOWS_PARENTS) {
            BiomePlacement.addSubOverworld(parent, SLEEPY_HOLLOWS_BIOME, CriterionBuilder.allOf(CriterionBuilder.ratioMax(RatioTargets.CENTER, 0.35f), CriterionBuilder.not(CriterionBuilder.neighbor(BiomeTags.IS_RIVER)), CriterionBuilder.not(CriterionBuilder.neighbor(BiomeTags.IS_OCEAN)), CriterionBuilder.not(CriterionBuilder.neighbor(BiomeTags.IS_BEACH))));
        }
    }

    private static void onServerTick(MinecraftServer server) {
        if (!PlatformHelper.isSanityEnabled()) return;
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (!player.gameMode.isSurvival()) continue;

            if (SanityManager.getSanity(player) <= SanityManager.MINIMUM_SANITY) {
                player.addEffect(new MobEffectInstance(MobEffectRegistry.holder(MobEffectRegistry.INSANITY), PlatformHelper.getSanityInsanitySeconds() * 20));
                SanityManager.setSanity(player, SanityManager.MAXIMUM_SANITY);
            }

            if (server.getTickCount() % 20 == 0) {
                SanityManager.doBlockCheck(player);
            }

            if (server.getTickCount() % (5 * 20) == 0 && !player.level().getBlockState(player.blockPosition()).is(TagRegistry.RESET_SANITY)) {
                if (!player.level().getBiome(player.getOnPos()).is(SLEEPY_HOLLOWS_BIOME)) {
                    SanityManager.changeSanity(player, SanityManager.Modifiers.OUTSIDE_BIOME);
                } else if (!SanityManager.isNearLight(player)) {
                    SanityManager.changeSanity(player, player.level().isNight() ? SanityManager.Modifiers.INSIDE_BIOME_NIGHT : SanityManager.Modifiers.INSIDE_BIOME);
                }
            }
        }
    }

    private static void setupSerial() {
        FlammableBlockRegistry.init();
        SleepyHollowsSurfaceRules.register();
        RarityRegistry.init();
        AxeItemHooks.addStrippable(ObjectRegistry.HOLLOW_LOG.get(), ObjectRegistry.STRIPPED_HOLLOW_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.HOLLOW_WOOD.get(), ObjectRegistry.STRIPPED_HOLLOW_WOOD.get());
    }
}