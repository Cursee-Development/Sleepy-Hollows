package net.satisfy.sleepy_hollows.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.FogType;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;

public final class HollowFog {
    private static float strength;
    private static float previousStrength;

    private HollowFog() {
    }

    public static void init() {
        ClientTickEvent.CLIENT_POST.register(HollowFog::tick);
    }

    private static void tick(Minecraft minecraft) {
        previousStrength = strength;
        if (minecraft.level == null || minecraft.player == null) {
            strength = 0;
            return;
        }
        boolean inHollow = PlatformHelper.isHollowFogEnabled() && minecraft.level.getBiome(minecraft.gameRenderer.getMainCamera().getBlockPosition()).is(SleepyHollows.SLEEPY_HOLLOWS_BIOME);
        float step = 1.0F / Math.max(1, PlatformHelper.getHollowFogTransitionSeconds() * 20);
        strength = Mth.clamp(strength + (inHollow ? step : -step), 0.0F, 1.0F);
    }

    public static void apply(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick) {
        if (mode != FogRenderer.FogMode.FOG_TERRAIN || camera.getFluidInCamera() != FogType.NONE) return;
        float factor = Mth.lerp(partialTick, previousStrength, strength);
        if (factor <= 0) return;
        factor = factor * factor * (3.0F - 2.0F * factor);
        float sanity = SanityManager.getClientSanity() / (float) SanityManager.MAXIMUM_SANITY;
        float density = 1.0F - (1.0F - sanity) * PlatformHelper.getHollowFogSanityInfluence();
        float start = renderDistance * PlatformHelper.getHollowFogStart() * density;
        float end = renderDistance * PlatformHelper.getHollowFogEnd() * density;
        RenderSystem.setShaderFogStart(Mth.lerp(factor, RenderSystem.getShaderFogStart(), start));
        RenderSystem.setShaderFogEnd(Mth.lerp(factor, RenderSystem.getShaderFogEnd(), end));
    }
}
