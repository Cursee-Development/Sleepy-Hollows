package net.satisfy.sleepy_hollows.client;

import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.registry.SoundEventRegistry;
import net.satisfy.sleepy_hollows.core.util.SanityManager;

import java.util.ArrayList;
import java.util.List;

public final class SanityAmbience {
    private static final List<Scheduled> SCHEDULED = new ArrayList<>();
    private static int ambientCooldown = 200;
    private static int sanityCooldown = 200;

    private SanityAmbience() {
    }

    public static void init() {
        ClientTickEvent.CLIENT_POST.register(SanityAmbience::tick);
    }

    private static void tick(Minecraft minecraft) {
        Player player = minecraft.player;
        ClientLevel level = minecraft.level;
        if (player == null || level == null) {
            SCHEDULED.clear();
            return;
        }
        if (minecraft.isPaused()) return;
        playScheduled(level);

        if (!level.getBiome(player.blockPosition()).is(SleepyHollows.SLEEPY_HOLLOWS_BIOME)) return;
        RandomSource random = player.getRandom();
        int sanity = SanityManager.getClientSanity();

        if (PlatformHelper.isAmbienceEnabled() && --ambientCooldown <= 0) {
            boolean night = level.isNight();
            int min = night ? PlatformHelper.getAmbienceNightMinSeconds() : PlatformHelper.getAmbienceDayMinSeconds();
            int max = night ? PlatformHelper.getAmbienceNightMaxSeconds() : PlatformHelper.getAmbienceDayMaxSeconds();
            float sanityFactor = PlatformHelper.isSanityEnabled() ? 0.5F + 0.5F * sanity / SanityManager.MAXIMUM_SANITY : 1.0F;
            ambientCooldown = Math.max(20, (int) ((min + random.nextInt(Math.max(1, max - min + 1))) * 20 * sanityFactor));
            playWithEcho(level, aroundPlayer(player, random, 4, 9), SoundEventRegistry.SLEEPY_HOLLOWS_AMBIENT.get(), PlatformHelper.getAmbienceVolume(), 0.8F + random.nextFloat() * 0.3F, random);
        }

        if (!PlatformHelper.isSanityEnabled() || sanity >= 50 || --sanityCooldown > 0) return;
        sanityCooldown = 100 + random.nextInt(200);
        Vec3 behind = player.position().subtract(player.getLookAngle().multiply(4.0, 0.0, 4.0));
        if (sanity < 25 && random.nextBoolean()) {
            for (int i = 0; i < 3; i++) {
                schedule(i * 6, behind, SoundEvents.GRASS_STEP, 0.6F, 0.8F + random.nextFloat() * 0.2F);
            }
            sanityCooldown = 60 + random.nextInt(100);
        } else if (random.nextInt(3) == 0) {
            playWithEcho(level, behind.add(0, 1, 0), SoundEventRegistry.HORSEMAN_LAUGH.get(), 0.2F, 0.6F + random.nextFloat() * 0.2F, random);
        } else {
            level.playLocalSound(behind.x, behind.y, behind.z, SoundEvents.AMBIENT_CAVE.value(), SoundSource.AMBIENT, 0.5F, 0.7F + random.nextFloat() * 0.3F, false);
        }
    }

    private static Vec3 aroundPlayer(Player player, RandomSource random, double min, double max) {
        double angle = random.nextDouble() * Math.PI * 2;
        double distance = min + random.nextDouble() * (max - min);
        return player.position().add(Math.cos(angle) * distance, 1 + random.nextDouble() * 3, Math.sin(angle) * distance);
    }

    private static void playWithEcho(ClientLevel level, Vec3 pos, SoundEvent sound, float volume, float pitch, RandomSource random) {
        level.playLocalSound(pos.x, pos.y, pos.z, sound, SoundSource.AMBIENT, volume, pitch, false);
        int echoes = 2 + random.nextInt(2);
        int delay = 0;
        for (int i = 1; i <= echoes; i++) {
            delay += 8 + random.nextInt(6);
            Vec3 echoPos = pos.add((random.nextDouble() - 0.5) * 12, 0, (random.nextDouble() - 0.5) * 12);
            schedule(delay, echoPos, sound, volume * (float) Math.pow(0.45, i), pitch - 0.03F * i);
        }
    }

    private static void schedule(int delay, Vec3 pos, SoundEvent sound, float volume, float pitch) {
        SCHEDULED.add(new Scheduled(delay, pos, sound, volume, pitch));
    }

    private static void playScheduled(ClientLevel level) {
        SCHEDULED.removeIf(entry -> {
            if (--entry.delay > 0) return false;
            level.playLocalSound(entry.pos.x, entry.pos.y, entry.pos.z, entry.sound, SoundSource.AMBIENT, entry.volume, entry.pitch, false);
            return true;
        });
    }

    private static final class Scheduled {
        private int delay;
        private final Vec3 pos;
        private final SoundEvent sound;
        private final float volume;
        private final float pitch;

        private Scheduled(int delay, Vec3 pos, SoundEvent sound, float volume, float pitch) {
            this.delay = delay;
            this.pos = pos;
            this.sound = sound;
            this.volume = volume;
            this.pitch = pitch;
        }
    }
}
