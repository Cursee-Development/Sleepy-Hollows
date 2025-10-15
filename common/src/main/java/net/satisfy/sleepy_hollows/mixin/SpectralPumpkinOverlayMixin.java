package net.satisfy.sleepy_hollows.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class SpectralPumpkinOverlayMixin {

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "render", at = @At("HEAD"))
    private void renderSpectralPumpkinOverlay(GuiGraphics guiGraphics, net.minecraft.client.DeltaTracker deltaTracker, CallbackInfo ci) {
        if (this.minecraft.player == null) return;
        ItemStack headItem = this.minecraft.player.getInventory().getArmor(3);

        if (this.minecraft.options.getCameraType().isFirstPerson()) {
            if (headItem.is(ObjectRegistry.SPECTRAL_CARVED_PUMPKIN.get().asItem())
                    || headItem.is(ObjectRegistry.SPECTRAL_JACK_O_LANTERN.get().asItem())) {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                this.renderTextureOverlay(
                        guiGraphics,
                        ResourceLocation.fromNamespaceAndPath("minecraft", "textures/misc/pumpkinblur.png"),
                        0.65F
                );
                RenderSystem.disableBlend();
            }
        }
    }

    @Shadow
    private void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation textureLocation, float opacity) {}
}