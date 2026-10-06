package net.satisfy.sleepy_hollows.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.satisfy.sleepy_hollows.client.HollowFog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {
    @Inject(method = "setupFog", at = @At("TAIL"))
    private static void sleepyHollows$hollowFog(Camera camera, FogRenderer.FogMode mode, float renderDistance, boolean thickFog, float partialTick, CallbackInfo ci) {
        HollowFog.apply(camera, mode, renderDistance, partialTick);
    }
}
