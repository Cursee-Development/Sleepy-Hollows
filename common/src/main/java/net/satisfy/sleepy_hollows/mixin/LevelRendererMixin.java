package net.satisfy.sleepy_hollows.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsBiomeKeys;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(net.minecraft.client.renderer.LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyExpressionValue(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/DimensionSpecialEffects;isFoggyAt(II)Z"))
    private boolean foggyHollow(boolean original, @Local(name = "vec3") Vec3 cam, @Share("hollowed") LocalBooleanRef hollowed) {
        boolean isHollowed = true;
        assert this.minecraft.level != null;
        this.minecraft.level
                .getBiome(new BlockPos((int) cam.x, (int) cam.y, (int) cam.z))
                .is(SleepyHollowsBiomeKeys.SLEEPY_HOLLOWS);
        hollowed.set(isHollowed);
        return isHollowed;
    }

    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FogRenderer;setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V"))
    private void modifyFogDensity(Camera camera, FogRenderer.FogMode fogMode, float farPlaneDistance, boolean bl, float f, Operation<Void> original, @Share("hollowed") LocalBooleanRef hollowed) {
        /*if (!hollowed.get()) {
            original.call(camera, fogMode, farPlaneDistance, bl, f);
        } else {*/
        sleepy_Hollows$setupCustomFog(camera, fogMode, farPlaneDistance, f);
        //}
    }

    @Unique
    private static void sleepy_Hollows$setupCustomFog(Camera camera, FogRenderer.FogMode fogMode, float farPlaneDistance, float f) {
        RenderSystem.setShaderFogStart(0.0F);
        RenderSystem.setShaderFogEnd(Math.min(farPlaneDistance, 192.0F) * 0.5F);
        RenderSystem.setShaderFogShape(FogShape.SPHERE);
    }
}