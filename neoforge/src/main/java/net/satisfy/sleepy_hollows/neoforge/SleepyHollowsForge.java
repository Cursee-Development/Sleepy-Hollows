package net.satisfy.sleepy_hollows.neoforge;

import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.CompostableRegistry;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.neoforge.config.SleepyHollowsNeoForgeConfig;

import java.util.function.Supplier;

@Mod(SleepyHollows.MOD_ID)
public final class SleepyHollowsForge {
    public SleepyHollowsForge(IEventBus modEventBus, ModContainer modContainer) {
        SleepyHollows.init();
        modContainer.registerConfig(ModConfig.Type.COMMON, SleepyHollowsNeoForgeConfig.COMMON_CONFIG);
        modEventBus.addListener(this::onCommonSetup);
        registerMonster(EntityTypeRegistry.INFECTED_ZOMBIE, Monster::checkMonsterSpawnRules);
    }

    private static <T extends Monster> void registerMonster(Supplier<? extends EntityType<T>> typeSupplier, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacementsRegistry.register(typeSupplier, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, predicate);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(CompostableRegistry::init);
    }
}