package net.satisfy.sleepy_hollows.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsBiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void adjustBossBarYOffset(GuiGraphics guiGraphics, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null && SanityManager.isSanityBarVisible(player) && sleepy_Hollows$isInSleepyHollowsBiome(player)) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0, 30, 0);
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void resetBossBarYOffset(GuiGraphics guiGraphics, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null && SanityManager.isSanityBarVisible(player) && sleepy_Hollows$isInSleepyHollowsBiome(player)) {
            guiGraphics.pose().popPose();
        }
    }

    @Unique
    private boolean sleepy_Hollows$isInSleepyHollowsBiome(Player player) {
        return player.level().getBiome(player.blockPosition()).is(SleepyHollowsBiomeKeys.SLEEPY_HOLLOWS);
    }
}


