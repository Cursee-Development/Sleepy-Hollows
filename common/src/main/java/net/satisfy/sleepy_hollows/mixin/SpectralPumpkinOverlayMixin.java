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

    @Final
    @Shadow
    private Minecraft minecraft;

    @Inject(method = "render", at = @At("HEAD"))
    private void renderSpectralPumpkinOverlay(GuiGraphics guiGraphics, float partialTick, CallbackInfo ci) {
        assert this.minecraft.player != null;
        ItemStack itemstack = this.minecraft.player.getInventory().getArmor(3);

        if (this.minecraft.options.getCameraType().isFirstPerson()) {
            if (itemstack.is(ObjectRegistry.SPECTRAL_CARVED_PUMPKIN.get().asItem()) || itemstack.is(ObjectRegistry.SPECTRAL_JACK_O_LANTERN.get().asItem())) {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                this.renderTextureOverlay(guiGraphics, new ResourceLocation("minecraft", "textures/misc/pumpkinblur.png"), 0.65F);
                RenderSystem.disableBlend();
            }
        }
    }

    @Shadow
    private void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation textureLocation, float opacity) {
    }
}
