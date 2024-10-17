package net.satisfy.sleepy_hollows.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsBiomeKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void adjustBossBarYOffset(GuiGraphics guiGraphics, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        // Überprüfen, ob der Spieler existiert und die SanityBar angezeigt wird
        if (player != null && SanityManager.isSanityBarVisible(player) && isInSleepyHollowsBiome(player)) {
            // Sanity ist unter 100 und die SanityBar wird angezeigt, also wird die BossBar nach unten verschoben
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0, 30, 0);
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void resetBossBarYOffset(GuiGraphics guiGraphics, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        // Überprüfen, ob der Spieler existiert und die SanityBar angezeigt wird
        if (player != null && SanityManager.isSanityBarVisible(player) && isInSleepyHollowsBiome(player)) {
            // Sanity ist unter 100 und die SanityBar wird angezeigt, also wird die Verschiebung der BossBar zurückgesetzt
            guiGraphics.pose().popPose();
        }
    }

    private boolean isInSleepyHollowsBiome(Player player) {
        // Überprüfen, ob der Spieler sich in der Sleepy Hollows-Biome befindet
        return player.level().getBiome(player.blockPosition()).is(SleepyHollowsBiomeKeys.SLEEPY_HOLLOWS);
    }
}


