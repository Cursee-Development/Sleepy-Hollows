package net.satisfy.sleepy_hollows.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow @Final private Minecraft minecraft;

    @ModifyExpressionValue(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/DimensionSpecialEffects;isFoggyAt(II)Z"))
    private boolean foggyHollow(boolean original, @Share("hollowed") LocalBooleanRef hollowed) {
        Vec3 cam = this.minecraft.gameRenderer.getMainCamera().getPosition();
        Biome biome = this.minecraft.level.getBiome(BlockPos.containing(cam)).value();
        boolean isHollow = biome.is(SleepyHollowsBiomeKeys.SLEEPY_HOLLOWS);
        hollowed.set(isHollow);
        return isHollow || original;
    }

    @ModifyArg(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/FogRenderer;setupFog(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/FogRenderer$FogMode;FZF)V"), index = 2)
    private float modifyFogDistance(float distance, @Share("hollowed") LocalBooleanRef hollowed) {
        return hollowed.get() ? 1F : distance;
    }
}