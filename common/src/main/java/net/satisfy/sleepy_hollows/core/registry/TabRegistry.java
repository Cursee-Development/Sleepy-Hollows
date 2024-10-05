package net.satisfy.sleepy_hollows.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.satisfy.sleepy_hollows.Constants;

public class TabRegistry {

    public static final DeferredRegister<CreativeModeTab> SLEEPY_HOLLOWS_TABS = DeferredRegister.create(Constants.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> SLEEPY_HOLLOWS_TAB = SLEEPY_HOLLOWS_TABS.register(Constants.MOD_ID, () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> new ItemStack(ObjectRegistry.CREAKING_FLOORBOARD.get()))
            .title(Component.translatable("itemGroup.sleepyHollows").withStyle(ChatFormatting.ITALIC))
            .displayItems(((itemDisplayParameters, output) -> {
                output.accept(ObjectRegistry.CREAKING_FLOORBOARD.get());
            }))
            .build());

    public static void init() {
        SLEEPY_HOLLOWS_TABS.register();
    }
}
