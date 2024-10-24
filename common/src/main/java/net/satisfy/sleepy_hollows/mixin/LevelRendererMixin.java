package net.satisfy.sleepy_hollows.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsBiomeKeys;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(net.minecraft.client.renderer.LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyExpressionValue(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/DimensionSpecialEffects;isFoggyAt(II)Z"))
    private boolean foggyHollow(boolean original, @Share("hollowed") LocalBooleanRef hollowed) {
        assert this.minecraft.level != null;
        Vec3 cam = this.minecraft.gameRenderer.getMainCamera().getPosition();
        hollowed.set(this.minecraft.level.getBiome(
                new BlockPos((int) cam.x, (int) cam.y, (int) cam.z)
        ).is(SleepyHollowsBiomeKeys.SLEEPY_HOLLOWS));
        return hollowed.get();
    }

    @ModifyArg(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FogRenderer;setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V"), index = 4)
    private float modifyFogDensity(float farPlaneDistance, @Share("hollowed") LocalBooleanRef hollowed) {
        if (hollowed.get()) {
            return 1F;
        }
        return farPlaneDistance;
    }
}
