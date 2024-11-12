package net.satisfy.sleepy_hollows.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.satisfy.sleepy_hollows.core.registry.TagRegistry;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsBiomeKeys;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;

public class HUDRenderEvent {

    private static final ResourceLocation FRAME_TEXTURE = new SleepyHollowsIdentifier("textures/gui/sanity_meter_bar.png");
    private static final ResourceLocation FILL_TEXTURE = new SleepyHollowsIdentifier("textures/gui/sanity_meter_progress.png");

    private static long lastExitedBiomeTime = 0;
    private static final long DISPLAY_DURATION_AFTER_EXIT = 45000;

    @SuppressWarnings("unused")
    public static void onRenderHUD(GuiGraphics guiGraphics, float tickDelta) {

        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        Player player = mc.player;

        if (!(player instanceof LocalPlayer localPlayer) || level == null || mc.isPaused()) return;

        if (SanityManager.isClientImmune(localPlayer) || mc.level.getBlockState(player.blockPosition()).is(TagRegistry.RESET_SANITY)) return;

        int sanity = SanityManager.getClientSanity(localPlayer);

        boolean isInSleepyHollows = player.level().getBiome(player.blockPosition()).is(SleepyHollowsBiomeKeys.SLEEPY_HOLLOWS);

        if (!isInSleepyHollows && lastExitedBiomeTime == 0) {
            lastExitedBiomeTime = System.currentTimeMillis();
        }

        if (isInSleepyHollows) {
            lastExitedBiomeTime = 0;
        }

        boolean shouldRender = isInSleepyHollows ||
                (lastExitedBiomeTime != 0 &&
                        System.currentTimeMillis() - lastExitedBiomeTime <= DISPLAY_DURATION_AFTER_EXIT);

        if (!shouldRender || sanity == SanityManager.MAXIMUM_SANITY) return;

        int hudX = PlatformHelper.getHUDX();
        int hudY = PlatformHelper.getHUDY();

        int frameWidth = 214;
        int frameHeight = 32;
        int barWidth = 214;
        int barXOffset = 0;
        int barYOffset = 0;

        int x = (mc.getWindow().getGuiScaledWidth() / 2) - (frameWidth / 2) + hudX;
        int y = (mc.getWindow().getGuiScaledHeight() / 2) - 132 + hudY;

        int fillWidth = (int) ((sanity / 100.0f) * barWidth);

        guiGraphics.blit(FILL_TEXTURE, x + barXOffset, y + barYOffset, 0, 0, fillWidth, frameHeight, barWidth, frameHeight);
        guiGraphics.blit(FRAME_TEXTURE, x, y, 0, 0, frameWidth, frameHeight, frameWidth, frameHeight);
    }
}
