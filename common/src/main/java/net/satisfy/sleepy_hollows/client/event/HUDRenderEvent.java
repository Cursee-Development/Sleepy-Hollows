package net.satisfy.sleepy_hollows.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;

public class HUDRenderEvent {

    private static final ResourceLocation FRAME_TEXTURE = new SleepyHollowsIdentifier("textures/gui/sanity_bar.png");
    private static final ResourceLocation FILL_TEXTURE = new SleepyHollowsIdentifier("textures/gui/sanity_progress.png");

    @SuppressWarnings("unused")
    public static void onRenderHUD(GuiGraphics guiGraphics, float tickDelta) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player == null) return;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int width = 100;
        int height = 5;

        int x = (screenWidth / 2) - (width / 2);
        int y = (screenHeight / 2) + 75;

        guiGraphics.blit(FRAME_TEXTURE, x, y, 0, 0, width, height, width, height);

        int sanity = SanityManager.getSanity(player);
        int fillWidth = (int) ((sanity / 100.0f) * width);

        guiGraphics.blit(FILL_TEXTURE, x, y, 0, 0, fillWidth, height, width, height);
    }
}
