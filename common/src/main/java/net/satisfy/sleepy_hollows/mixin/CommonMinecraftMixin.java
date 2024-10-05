package net.satisfy.sleepy_hollows.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class CommonMinecraftMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void CommonMinecraftMixin$onInstanceInit(CallbackInfo ci) {}
}
