package net.satisfy.sleepy_hollows.fabric.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.architectury.platform.Platform;
import me.jellysquid.mods.sodium.client.compatibility.checks.PreLaunchChecks;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = PreLaunchChecks.class, remap = false)
public class SodiumAcceptAnyLWJGL {

    @WrapMethod(method = "beforeLWJGLInit")
    private static void acceptAnyLWJGL(Operation<Void> original) {
        if (!Platform.isDevelopmentEnvironment()) original.call();
    }

}
