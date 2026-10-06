package net.satisfy.sleepy_hollows.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.TagRegistry;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;

public class HUDRenderEvent {
    private static final ResourceLocation FRAME_TEXTURE = SleepyHollows.identifier("textures/gui/sanity_meter_bar.png");
    private static final ResourceLocation FILL_TEXTURE = SleepyHollows.identifier("textures/gui/sanity_meter_progress.png");

    private static final int FILL_START = 16;
    private static final int FILL_END = 198;

    private static long lastExitedBiomeTime = 0;
    private static final long DISPLAY_DURATION_AFTER_EXIT = 45000;

    @SuppressWarnings("unused")
    public static void onRenderHUD(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {

        Minecraft mc = Minecraft.getInstance();
        Level level = mc.level;
        Player player = mc.player;

        if (!PlatformHelper.isSanityEnabled() || player == null || level == null || mc.isPaused()) return;

        if (SanityManager.isImmune(player) || mc.level.getBlockState(player.blockPosition()).is(TagRegistry.RESET_SANITY)) return;

        int sanity = SanityManager.getClientSanity();

        boolean isInSleepyHollows = player.level().getBiome(player.blockPosition()).is(SleepyHollows.SLEEPY_HOLLOWS_BIOME);

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

        int fillWidth = FILL_START + Math.round(sanity / 100.0f * (FILL_END - FILL_START));

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(FILL_TEXTURE, x + barXOffset, y + barYOffset, 0, 0, fillWidth, frameHeight, barWidth, frameHeight);
        guiGraphics.blit(FRAME_TEXTURE, x, y, 0, 0, frameWidth, frameHeight, frameWidth, frameHeight);
        RenderSystem.disableBlend();
    }
}
