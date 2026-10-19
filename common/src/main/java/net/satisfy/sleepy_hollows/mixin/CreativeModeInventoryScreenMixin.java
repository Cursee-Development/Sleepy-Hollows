package net.satisfy.sleepy_hollows.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.CreativeModeTab;
import net.satisfy.foundation.rarity.FoundationRarity;
import net.satisfy.foundation.rarity.RarityTooltipFrame;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.TabRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Optional;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin {
    @Redirect(method = "checkTabHovering", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderTooltip(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;II)V"))
    private void sleepy_hollows$renderTabTooltip(GuiGraphics graphics, Font font, Component title, int mouseX, int mouseY, GuiGraphics original, CreativeModeTab tab, int tabMouseX, int tabMouseY) {
        if (tab != TabRegistry.SLEEPY_HOLLOWS_TAB.get()) {
            graphics.renderTooltip(font, title, mouseX, mouseY);
            return;
        }
        FoundationRarity rarity = FoundationRarity.EPIC;
        Component modId = Component.literal("@" + SleepyHollows.MOD_ID).withStyle(style -> style.withColor(TextColor.fromRgb(rarity.baseColor())).withItalic(true));
        RarityTooltipFrame.mark(rarity, tab.getIconItem());
        graphics.renderTooltip(font, List.of(title, modId), Optional.empty(), mouseX, mouseY);
    }
}
