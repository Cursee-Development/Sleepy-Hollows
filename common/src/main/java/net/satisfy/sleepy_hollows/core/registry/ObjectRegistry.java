package net.satisfy.sleepy_hollows.core.registry;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.block.custom.*;
import net.satisfy.sleepy_hollows.core.item.custom.*;
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

    public static final RegistrySupplier<Block> GRAVESTONE = registerBlockWithBlockItem("gravestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> GRAVESTONE_STAIRS = registerBlockWithBlockItem("gravestone_stairs", () -> new StairBlock(GRAVESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(GRAVESTONE.get())));
    public static final RegistrySupplier<Block> GRAVESTONE_SLAB = registerBlockWithBlockItem("gravestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(GRAVESTONE.get())));
    public static final RegistrySupplier<Block> GRAVESTONE_WALL = registerBlockWithBlockItem("gravestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(GRAVESTONE.get())));
    public static final RegistrySupplier<Block> COBBLED_GRAVESTONE = registerBlockWithBlockItem("cobbled_gravestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> COBBLED_GRAVESTONE_STAIRS = registerBlockWithBlockItem("cobbled_gravestone_stairs", () -> new StairBlock(COBBLED_GRAVESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(GRAVESTONE.get())));
    public static final RegistrySupplier<Block> COBBLED_GRAVESTONE_SLAB = registerBlockWithBlockItem("cobbled_gravestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(COBBLED_GRAVESTONE.get())));
    public static final RegistrySupplier<Block> COBBLED_GRAVESTONE_WALL = registerBlockWithBlockItem("cobbled_gravestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(COBBLED_GRAVESTONE.get())));
    public static final RegistrySupplier<Block> CHISELED_GRAVESTONE = registerBlockWithBlockItem("chiseled_gravestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> GRAVESTONE_BRICKS = registerBlockWithBlockItem("gravestone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> GRAVESTONE_BRICK_STAIRS = registerBlockWithBlockItem("gravestone_brick_stairs", () -> new StairBlock(GRAVESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(GRAVESTONE.get())));
    public static final RegistrySupplier<Block> GRAVESTONE_BRICK_SLAB = registerBlockWithBlockItem("gravestone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(GRAVESTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> GRAVESTONE_BRICK_WALL = registerBlockWithBlockItem("gravestone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(GRAVESTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> CRACKED_GRAVESTONE_BRICKS = registerBlockWithBlockItem("cracked_gravestone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_GRAVESTONE_BRICKS = registerBlockWithBlockItem("mossy_gravestone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_GRAVESTONE_BRICK_STAIRS = registerBlockWithBlockItem("mossy_gravestone_brick_stairs", () -> new StairBlock(MOSSY_GRAVESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(GRAVESTONE.get())));
    public static final RegistrySupplier<Block> MOSSY_GRAVESTONE_BRICK_SLAB = registerBlockWithBlockItem("mossy_gravestone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_GRAVESTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_GRAVESTONE_BRICK_WALL = registerBlockWithBlockItem("mossy_gravestone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_GRAVESTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_GRAVESTONE = registerBlockWithBlockItem("mossy_cobbled_gravestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_GRAVESTONE_STAIRS = registerBlockWithBlockItem("mossy_cobbled_gravestone_stairs", () -> new StairBlock(MOSSY_GRAVESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(GRAVESTONE.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_GRAVESTONE_SLAB = registerBlockWithBlockItem("mossy_cobbled_gravestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_GRAVESTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_GRAVESTONE_WALL = registerBlockWithBlockItem("mossy_cobbled_gravestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_GRAVESTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_CHISELED_GRAVESTONE = registerBlockWithBlockItem("mossy_chiseled_gravestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_GRAVESTONE = registerBlockWithBlockItem("mossy_gravestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> MOSSY_GRAVESTONE_STAIRS = registerBlockWithBlockItem("mossy_gravestone_stairs", () -> new StairBlock(MOSSY_GRAVESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(MOSSY_GRAVESTONE.get())));
    public static final RegistrySupplier<Block> MOSSY_GRAVESTONE_SLAB = registerBlockWithBlockItem("mossy_gravestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_GRAVESTONE.get())));
    public static final RegistrySupplier<Block> MOSSY_GRAVESTONE_WALL = registerBlockWithBlockItem("mossy_gravestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_GRAVESTONE.get())));
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
        protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {return configuredFeatureKey("hollow_tree_mid");}}, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> MOONVEIL_GRASS = registerBlockWithBlockItem("moonveil_grass", () -> new InfectedFlowerBlock(Objects.requireNonNull(MobEffect.byId(3)), 1, BlockBehaviour.Properties.copy(Blocks.GRASS)));
    public static final RegistrySupplier<Block> TALL_MOONVEIL_GRASS = registerBlockWithBlockItem("tall_moonveil_grass", () -> new InfectedTallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistrySupplier<Block> DUSKBERRY_BUSH = registerBlockWithoutItem("duskberry_bush", () -> new DuskberryBushBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH)));
    public static final RegistrySupplier<Block> GRAVE_LILY = registerBlockWithBlockItem("grave_lily", () -> new InfectedFlowerBlock(Objects.requireNonNull(MobEffect.byId(5)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> DREAMSHADE = registerBlockWithBlockItem("dreamshade", () -> new InfectedFlowerBlock(Objects.requireNonNull(MobEffect.byId(1)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> SHADOWBLOOM = registerBlockWithBlockItem("shadowbloom", () -> new InfectedFlowerBlock(Objects.requireNonNull(MobEffect.byId(7)), 1, BlockBehaviour.Properties.copy(Blocks.RED_TULIP)));
    public static final RegistrySupplier<Block> TALL_DREAMSHADE = registerBlockWithBlockItem("tall_dreamshade", () -> new InfectedTallFlowerBlock(BlockBehaviour.Properties.copy(Blocks.ROSE_BUSH)));
    public static final RegistrySupplier<Block> PEDESTAL = registerBlockWithBlockItem("pedestal", () -> new PedestalBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).strength(36000).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> WOODEN_TOMBSTONE = registerBlockWithBlockItem("wooden_tombstone", () -> new TombstoneBlock(BlockBehaviour.Properties.copy(GRAVESTONE.get()).lightLevel(state -> state.getValue(TombstoneBlock.ACTIVE) ? 10 : 0), TombstoneBlock.createWoodenTombstoneShape()));
    public static final RegistrySupplier<Block> SMALL_TOMBSTONE = registerBlockWithBlockItem("small_tombstone", () -> new TombstoneBlock(BlockBehaviour.Properties.copy(GRAVESTONE.get()).lightLevel(state -> state.getValue(TombstoneBlock.ACTIVE) ? 10 : 0), TombstoneBlock.createSmallTombstoneShape()));
    public static final RegistrySupplier<Block> MID_TOMBSTONE = registerBlockWithBlockItem("mid_tombstone", () -> new TombstoneBlock(BlockBehaviour.Properties.copy(GRAVESTONE.get()).lightLevel(state -> state.getValue(TombstoneBlock.ACTIVE) ? 10 : 0), TombstoneBlock.createMidTombstoneShape()));
    public static final RegistrySupplier<Block> BIG_TOMBSTONE = registerBlockWithBlockItem("big_tombstone", () -> new BigTombstoneBlock(BlockBehaviour.Properties.copy(GRAVESTONE.get())));
    public static final RegistrySupplier<Block> WROUGHT_IRON_FENCE = registerBlockWithBlockItem("wrought_iron_fence", () -> new IronBarsBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BARS)));
    public static final RegistrySupplier<Block> SPECTRAL_LANTERN = registerBlockWithBlockItem("spectral_lantern", () -> new SpectralLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN)));
    public static final RegistrySupplier<Block> SPECTRAL_PUMPKIN = registerBlockWithBlockItem("spectral_pumpkin", () -> new SpectralPumpkinBlock(BlockBehaviour.Properties.copy(Blocks.PUMPKIN)));
    public static final RegistrySupplier<Block> SPECTRAL_CARVED_PUMPKIN = registerBlockWithBlockItem("spectral_carved_pumpkin", () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.copy(Blocks.CARVED_PUMPKIN)));
    public static final RegistrySupplier<Block> SPECTRAL_JACK_O_LANTERN = registerBlockWithBlockItem("spectral_jack_o_lantern", () -> new CarvedPumpkinBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOD).lightLevel((blockStatex) -> 15).isValidSpawn((state, world, pos, entityType) -> true).pushReaction(PushReaction.DESTROY)));
    public static final RegistrySupplier<Item> HAUNTBOUND_HELMET = registerItem("hauntbound_helmet", () -> new HauntboundHelmetItem(ArmorMaterialRegistry.HAUNTBOUND_ARMOR, ArmorItem.Type.HELMET, getSettings().rarity(Rarity.EPIC), new SleepyHollowsIdentifier("textures/models/armor/hauntbound_helmet.png")));
    public static final RegistrySupplier<Item> HAUNTBOUND_CHESTPLATE = registerItem("hauntbound_chestplate", () -> new HauntboundChestplateItem(ArmorMaterialRegistry.HAUNTBOUND_ARMOR, ArmorItem.Type.CHESTPLATE, getSettings().rarity(Rarity.EPIC), new SleepyHollowsIdentifier("textures/models/armor/hauntbound_armor_outer_layer.png")));
    public static final RegistrySupplier<Item> HAUNTBOUND_LEGGINGS = registerItem("hauntbound_leggings", () -> new HauntboundLeggingsItem(ArmorMaterialRegistry.HAUNTBOUND_ARMOR, ArmorItem.Type.LEGGINGS, getSettings().rarity(Rarity.EPIC), new SleepyHollowsIdentifier("textures/models/armor/hauntbound_armor_inner_layer.png")));
    public static final RegistrySupplier<Item> HAUNTBOUND_BOOTS = registerItem("hauntbound_boots", () -> new HauntboundBootsItem(ArmorMaterialRegistry.HAUNTBOUND_ARMOR, ArmorItem.Type.BOOTS, getSettings().rarity(Rarity.EPIC), new SleepyHollowsIdentifier("textures/models/armor/hauntbound_armor_outer_layer.png")));
    public static final RegistrySupplier<Block> COFFIN = registerBlockWithBlockItem("coffin", () -> new CoffinBlock(BlockBehaviour.Properties.copy(Blocks.STONE).pushReaction(PushReaction.DESTROY)));
    public static final RegistrySupplier<Item> SPECTRAL_ESSENCE = registerItem("spectral_essence", () -> new Item(getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> ESSENCE_OF_UNDEAD = registerItem("essence_of_undead", () -> new Item(getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> LUMINOUS_ESSENCE = registerItem("luminous_essence", () -> new Item(getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> LUMINOUS_WATER = registerItem("luminous_water", () -> new LuminousWaterItem(getSettings().food(Foods.CHICKEN).rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> DUSK_BERRIES = registerItem("dusk_berries", () -> new DuskBerryItem(DUSKBERRY_BUSH.get(), (new Item.Properties()).food(Foods.SWEET_BERRIES)));
    public static final RegistrySupplier<Item> SPECTRAL_PUMPKIN_PIE = registerItem("spectral_pumpkin_pie", () -> new SpectralPumpkinPieItem(getSettings().food(Foods.PUMPKIN_PIE).rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> CANDY_CORN = registerItem("candy_corn", () -> new CandyCornItem(getSettings().food(Foods.APPLE)));
    public static final RegistrySupplier<Item> LOOTBAG = registerItem("lootbag", () -> new LootBagItem(getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item>  REINS_OF_THE_SPECTRAL_HORSE = registerItem("reins_of_the_spectral_horse",  () -> new ReinsOfTheSpectralHorseItem(EntityTypeRegistry.SPECTRAL_HORSE, -1, -1, getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item>  INFECTED_ZOMBIE_SPAWN_EGG = registerItem("infected_zombie_spawn_egg",  () -> new ArchitecturySpawnEggItem(EntityTypeRegistry.INFECTED_ZOMBIE, -1, -1, getSettings()));
    public static final RegistrySupplier<Item>  FLEEING_PUMPKIN_HEAD_SPAWN_EGG = registerItem("fleeing_pumpkin_head_spawn_egg",  () -> new ArchitecturySpawnEggItem(EntityTypeRegistry.FLEEING_PUMPKIN_HEAD, -1, -1, getSettings()));
    public static final RegistrySupplier<Block> COMPLETIONIST_BANNER = registerBlockWithBlockItem("completionist_banner", () -> new CompletionistBannerBlock(BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> COMPLETIONIST_WALL_BANNER = registerBlockWithoutItem("completionist_wall_banner", () -> new CompletionistWallBannerBlock(BlockBehaviour.Properties.of().strength(1F).instrument(NoteBlockInstrument.BASS).noCollission().sound(SoundType.WOOD)));
    public static final RegistrySupplier<Item> RAUBBAU = registerItem("raubbau", () -> new RaubbauItem(getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> SPECTRAL_WARAXE = registerItem("spectral_waraxe", () -> new SpectralWarAxeItem(getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> SHATTERBRAND = registerItem("shatterbrand", () -> new ShatterbrandSwordItem(getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> HEADLESS_HORSEMAN_SPAWN_EGG = registerItem("headless_horseman_spawn_egg", () -> new Item(getSettings()));


    public static final RegistrySupplier<Block> POTTED_HOLLOW_SAPLING = registerBlockWithoutItem("potted_hollow_sapling", () -> new FlowerPotBlock(HOLLOW_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> POTTED_GRAVE_LILY = registerBlockWithoutItem("potted_grave_lily", () -> new FlowerPotBlock(GRAVE_LILY.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> POTTED_DREAMSHADE = registerBlockWithoutItem("potted_dreamshade", () -> new FlowerPotBlock(DREAMSHADE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));
    public static final RegistrySupplier<Block> POTTED_SHADOWBLOOM = registerBlockWithoutItem("potted_shadowbloom", () -> new FlowerPotBlock(SHADOWBLOOM.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT)));

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
