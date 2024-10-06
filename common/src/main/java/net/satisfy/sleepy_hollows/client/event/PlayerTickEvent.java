package net.satisfy.sleepy_hollows.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.minecraft.core.BlockPos;
import net.satisfy.sleepy_hollows.core.world.SleepyHollowsBiomeKeys;

public class PlayerTickEvent {
    private static final int TICK_INTERVAL = 200;
    private static int tickCounter = 0;

    public static void onClientTick(Minecraft client) {
        Player player = client.player;

        if (player == null) return;

        BlockPos playerPos = player.blockPosition();

        if (player.level().getBiome(playerPos).is(SleepyHollowsBiomeKeys.SLEEPY_HOLLOWS)) {
            tickCounter++;
            if (tickCounter >= TICK_INTERVAL) {
                SanityManager.increaseSanity(player);
                tickCounter = 0;
            }
        }
        SanityManager.checkSanityBlocks(player);
    }
}
