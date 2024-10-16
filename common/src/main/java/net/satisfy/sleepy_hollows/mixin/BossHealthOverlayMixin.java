package net.satisfy.sleepy_hollows.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.BossEvent;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void adjustBossBarYOffset(GuiGraphics guiGraphics, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null && SanityManager.getSanity(player) < 100) {
            Map<UUID, BossEvent> events = sleepy_Hollows$getBossEvents(mc.gui.getBossOverlay());

            if (events != null && !events.isEmpty()) {
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate(0, 30, 0);
            }
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void resetBossBarYOffset(GuiGraphics guiGraphics, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null && SanityManager.getSanity(player) < 100) {
            Map<UUID, BossEvent> events = sleepy_Hollows$getBossEvents(mc.gui.getBossOverlay());

            if (events != null && !events.isEmpty()) {
                guiGraphics.pose().popPose();
            }
        }
    }

    @Unique
    @SuppressWarnings("unchecked")
    private Map<UUID, BossEvent> sleepy_Hollows$getBossEvents(BossHealthOverlay bossOverlay) {
        try {
            Field eventsField = BossHealthOverlay.class.getDeclaredField("events");
            eventsField.setAccessible(true);
            return (Map<UUID, BossEvent>) eventsField.get(bossOverlay);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Constants.LOG.error("Failed to access boss bar events in BossHealthOverlay", e);
            return null;
        }
    }
}
