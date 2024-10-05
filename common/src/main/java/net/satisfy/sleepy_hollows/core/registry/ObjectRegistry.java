package net.satisfy.sleepy_hollows.core.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.block.custom.CreakingFloorboardBlock;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ObjectRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Constants.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Constants.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();

    public static final RegistrySupplier<Block> CREAKING_FLOORBOARD = registerBlockWithBlockItem("creaking_floorboard", CreakingFloorboardBlock::new);

    public static void init() {
        BLOCKS.register();
        ITEMS.register();
    }

    public static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
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

    public static <T extends Block> RegistrySupplier<T> registerBlockWithBlockItem(String name, Supplier<T> block) {
        return SleepyHollowsUtil.abstractBlockItemRegistration(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new SleepyHollowsIdentifier(name), block);
    }
}
