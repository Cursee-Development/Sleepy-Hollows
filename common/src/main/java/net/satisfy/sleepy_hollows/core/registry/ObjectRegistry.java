package net.satisfy.sleepy_hollows.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.block.custom.CreakingPlanksBlock;
import net.satisfy.sleepy_hollows.core.block.custom.InfectedFlowerBlock;
import net.satisfy.sleepy_hollows.core.block.custom.InfectedTallFlowerBlock;
import net.satisfy.sleepy_hollows.core.block.custom.WindowBlock;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ObjectRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Constants.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Constants.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();

    public static final RegistrySupplier<Block> HOLLOW_LOG = registerBlockWithBlockItem("hollow_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> HOLLOW_WOOD = registerBlockWithBlockItem("hollow_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_HOLLOW_WOOD = registerBlockWithBlockItem("stripped_hollow_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_HOLLOW_LOG = registerBlockWithBlockItem("stripped_hollow_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> HOLLOW_PLANKS = registerBlockWithBlockItem("hollow_planks", () -> new CreakingPlanksBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.COLOR_BLACK)));
    public static final RegistrySupplier<Block> HOLLOW_STAIRS = registerBlockWithBlockItem("hollow_stairs", () -> new StairBlock(HOLLOW_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> HOLLOW_PRESSURE_PLATE = registerBlockWithBlockItem("hollow_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(HOLLOW_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> HOLLOW_DOOR = registerBlockWithBlockItem("hollow_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(HOLLOW_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> HOLLOW_FENCE_GATE = registerBlockWithBlockItem("hollow_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(HOLLOW_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> HOLLOW_SLAB = registerBlockWithBlockItem("hollow_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> HOLLOW_BUTTON = registerBlockWithBlockItem("hollow_button", () -> {BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);properties = properties.requiredFeatures(FeatureFlags.VANILLA);return new ButtonBlock(properties, BlockSetType.OAK, 30, true);});
    public static final RegistrySupplier<Block> HOLLOW_TRAPDOOR = registerBlockWithBlockItem("hollow_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> HOLLOW_FENCE = registerBlockWithBlockItem("hollow_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> HOLLOW_WINDOW = registerBlockWithBlockItem("hollow_window", () -> new WindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> HOLLOW_LEAVES = registerBlockWithBlockItem("hollow_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false).mapColor(MapColor.GRASS)));
    public static final RegistrySupplier<Block> HOLLOW_SAPLING = registerBlockWithBlockItem("hollow_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {
        @Override
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
            return configuredFeatureKey("plains_hollow_tree");
        }
    }, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> MOONVEIL_GRASS = registerBlockWithBlockItem("moonveil_grass", () -> new InfectedFlowerBlock(Objects.requireNonNull(MobEffect.byId(3)), 1, BlockBehaviour.Properties.copy(Blocks.GRASS)));
    public static final RegistrySupplier<Block> TALL_MOONVEIL_GRASS = registerBlockWithBlockItem("tall_moonveil_grass", () -> new InfectedTallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));

    public static final RegistrySupplier<Block> GRAVE_LILY = registerBlockWithBlockItem("grave_lily", () -> new InfectedFlowerBlock(Objects.requireNonNull(MobEffect.byId(5)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> DREAMSHADE = registerBlockWithBlockItem("dreamshade", () -> new InfectedFlowerBlock(Objects.requireNonNull(MobEffect.byId(1)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> TALL_DREAMSHADE = registerBlockWithBlockItem("tall_dreamshade", () -> new InfectedTallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)));


    public static final RegistrySupplier<Block> POTTED_HOLLOW_SAPLING = registerBlockWithoutItem("potted_hollow_sapling", () -> new FlowerPotBlock(HOLLOW_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> POTTED_GRAVE_LILY = registerBlockWithoutItem("potted_grave_lily", () -> new FlowerPotBlock(GRAVE_LILY.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> POTTED_DREAMSHADE = registerBlockWithoutItem("potted_dreamshade", () -> new FlowerPotBlock(DREAMSHADE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));

    public static void init() {
        BLOCKS.register();
        ITEMS.register();
    }

    public static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Constants.MOD_ID, name));
    }

    public static Item.Properties getSettings() {
        return getSettings(settings -> {});
    }

    public static <T extends Block> RegistrySupplier<T> registerBlock(String path, Supplier<T> block) {
        return SleepyHollowsUtil.abstractBlockRegistration(BLOCKS, BLOCK_REGISTRAR, new SleepyHollowsIdentifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return SleepyHollowsUtil.abstractItemRegistration(ITEMS, ITEM_REGISTRAR, new SleepyHollowsIdentifier(path), itemSupplier);
    }

    public static <T extends Block> RegistrySupplier<T> registerBlockWithoutItem(String path, Supplier<T> block) {
        return SleepyHollowsUtil.abstractBlockWithoutItemRegistration(BLOCKS, BLOCK_REGISTRAR, new SleepyHollowsIdentifier(path), block);
    }

    public static <T extends Block> RegistrySupplier<T> registerBlockWithBlockItem(String name, Supplier<T> block) {
        return SleepyHollowsUtil.abstractBlockItemRegistration(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new SleepyHollowsIdentifier(name), block);
    }
}
