package net.satisfy.sleepy_hollows.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsBiomeKeys;

public class HUDRenderEvent {

    private static final ResourceLocation FRAME_TEXTURE = new SleepyHollowsIdentifier("textures/gui/sanity_meter.png");
    private static final ResourceLocation FILL_TEXTURE = new SleepyHollowsIdentifier("textures/gui/sanity_meter_progress.png");

    @SuppressWarnings("unused")
    public static void onRenderHUD(GuiGraphics guiGraphics, float tickDelta) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null || mc.isPaused()) return;

        if (!player.level().getBiome(player.blockPosition()).is(SleepyHollowsBiomeKeys.SLEEPY_HOLLOWS)) return;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int frameWidth = 214;
        int frameHeight = 30;
        int barWidth = 182;
        int barXOffset = 10;
        int barYOffset = 0;

        int x = (screenWidth / 2) - (frameWidth / 2);
        int y = (screenHeight / 2) - 132;

        int sanity = SanityManager.getSanity(player);
        int fillWidth = (int) ((sanity / 100.0f) * barWidth);

        guiGraphics.blit(FILL_TEXTURE, x + barXOffset, y + barYOffset, 0, 0, fillWidth, frameHeight, barWidth, frameHeight);

        guiGraphics.blit(FRAME_TEXTURE, x, y, 0, 0, frameWidth, frameHeight, frameWidth, frameHeight);
    }
}
