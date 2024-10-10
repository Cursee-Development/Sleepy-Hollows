package net.satisfy.sleepy_hollows.core.registry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.block.custom.entity.CoffinBlockEntity;
import net.satisfy.sleepy_hollows.core.block.custom.entity.PedestalBlockEntity;
import net.satisfy.sleepy_hollows.core.entity.SpectralHorse;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;

import java.util.function.Supplier;

public class EntityTypeRegistry {
    private static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Constants.MOD_ID, Registries.BLOCK_ENTITY_TYPE).getRegistrar();
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Constants.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<PedestalBlockEntity>> DISPLAY_BLOCK_ENTITY = registerBlockEntity("display", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, ObjectRegistry.PEDESTAL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CoffinBlockEntity>> COFFIN_BLOCK_ENTITY = registerBlockEntity("coffin", () -> BlockEntityType.Builder.of(CoffinBlockEntity::new, ObjectRegistry.COFFIN.get()).build(null));

    public static final RegistrySupplier<EntityType<SpectralHorse>> SPECTRAL_HORSE = registerEntity("spectral_horse",  () -> EntityType.Builder.of(SpectralHorse::new, MobCategory.CREATURE).sized(0.9f, 1.87f).clientTrackingRange(10).updateInterval(3).build(new SleepyHollowsIdentifier("spectral_horse").toString()));

    public static void init() {
        ENTITY_TYPES.register();
        registerAttributes();
    }

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> registerBlockEntity(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(new SleepyHollowsIdentifier(path), type);
    }

    private static <T extends EntityType<?>> RegistrySupplier<T> registerEntity(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.register(path, type);
    }

    static void registerAttributes(){
        EntityAttributeRegistry.register(SPECTRAL_HORSE, SpectralHorse::createAttributes);
    }
}
