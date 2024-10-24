package net.satisfy.sleepy_hollows.core.registry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.block.custom.entity.CoffinBlockEntity;
import net.satisfy.sleepy_hollows.core.block.custom.entity.CompletionistBannerEntity;
import net.satisfy.sleepy_hollows.core.block.custom.entity.DummyCoffinBlockEntity;
import net.satisfy.sleepy_hollows.core.block.custom.entity.PedestalBlockEntity;
import net.satisfy.sleepy_hollows.core.entity.*;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;

import java.util.function.Supplier;

public class EntityTypeRegistry {


    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Constants.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Constants.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<PedestalBlockEntity>> DISPLAY_BLOCK_ENTITY = registerBlockEntity("display", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, ObjectRegistry.PEDESTAL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CoffinBlockEntity>> COFFIN_BLOCK_ENTITY = registerBlockEntity("coffin", () -> BlockEntityType.Builder.of(CoffinBlockEntity::new, ObjectRegistry.COFFIN.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CompletionistBannerEntity>> COMPLETIONIST_BANNER_ENTITY = registerBlockEntity("completionist_banner", () -> BlockEntityType.Builder.of(CompletionistBannerEntity::new, ObjectRegistry.COMPLETIONIST_BANNER.get(), ObjectRegistry.COMPLETIONIST_WALL_BANNER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<DummyCoffinBlockEntity>> DUMMY_COFFIN_BLOCK_ENTITY = registerBlockEntity("dummy_coffin", () -> BlockEntityType.Builder.of(DummyCoffinBlockEntity::new, ObjectRegistry.COFFIN.get()).build(null));

    public static final RegistrySupplier<EntityType<SpectralHorse>> SPECTRAL_HORSE = registerEntity("spectral_horse", () -> EntityType.Builder.of(SpectralHorse::new, MobCategory.CREATURE).sized(0.9f, 1.87f).clientTrackingRange(10).updateInterval(3).build(new SleepyHollowsIdentifier("spectral_horse").toString()));
    public static final RegistrySupplier<EntityType<InfectedZombie>> INFECTED_ZOMBIE = registerEntity("infected_zombie", () -> EntityType.Builder.of(InfectedZombie::new, MobCategory.MONSTER).sized(0.6F, 1.95F).build(new SleepyHollowsIdentifier("infected_zombie").toString()));
    public static final RegistrySupplier<EntityType<FleeingPumpkinHead>> FLEEING_PUMPKIN_HEAD = registerEntity("fleeing_pumpkin_head", () -> EntityType.Builder.of(FleeingPumpkinHead::new, MobCategory.MONSTER).sized(0.6F, 1.95F).build(new SleepyHollowsIdentifier("fleeing_pumpkin_head").toString()));
    public static final RegistrySupplier<EntityType<Horseman>> HORSEMAN = registerEntity("horseman", () -> EntityType.Builder.of(Horseman::new, MobCategory.MONSTER).sized(1.4F, 2.6F).build(new SleepyHollowsIdentifier("horseman").toString()));

    public static <T extends EntityType<?>> RegistrySupplier<T> registerEntity(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.register(new SleepyHollowsIdentifier(path), type);
    }

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> registerBlockEntity(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(new SleepyHollowsIdentifier(path), type);
    }

    static void registerAttributes() {
        EntityAttributeRegistry.register(SPECTRAL_HORSE, SpectralHorse::createAttributes);
        EntityAttributeRegistry.register(INFECTED_ZOMBIE, InfectedZombie::createAttributes);
        EntityAttributeRegistry.register(FLEEING_PUMPKIN_HEAD, FleeingPumpkinHead::createAttributes);
        EntityAttributeRegistry.register(HORSEMAN, Horseman::createAttributes);
    }

    public static void init() {
        ENTITY_TYPES.register();
        BLOCK_ENTITY_TYPES.register();
        registerAttributes();
    }
}
