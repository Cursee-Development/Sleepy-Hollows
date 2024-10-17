package net.satisfy.sleepy_hollows.client.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;
import net.satisfy.sleepy_hollows.core.registry.TagRegistry;

import java.util.HashMap;
import java.util.Map;

public class SanityManager {
    private static final int MAX_SANITY = 100;
    private static final int INCREASE_SANITY_INTERVAL = 60;
    private static final int RESET_SANITY_INTERVAL = 300;
    private static final int OUTSIDE_BIOME_SANITY_LOSS_INTERVAL = 200;
    private static final int OUTSIDE_BIOME_SANITY_LOSS = 5;

    private static final Map<Player, Integer> playerSanityMap = new HashMap<>();
    private static final Map<Player, Integer> playerSanityTimersIncrease = new HashMap<>();
    private static final Map<Player, Integer> playerSanityTimersReset = new HashMap<>();
    private static final Map<Player, Integer> playerSanityTimersOutsideBiome = new HashMap<>();

    public static void checkSanityBlocks(Player player) {
        Level level = player.level();
        BlockPos playerPos = player.blockPosition();
        BlockState blockState = level.getBlockState(playerPos);
        Block block = blockState.getBlock();

        ResourceLocation blockId = level.registryAccess().registryOrThrow(Registries.BLOCK).getKey(block);

        boolean inSanityBiome = false;

        if (blockId != null) {
            if (blockState.is(TagRegistry.INCREASE_SANITY)) {
                inSanityBiome = true;
                int timer = playerSanityTimersIncrease.getOrDefault(player, 0);
                if (timer <= 0) {
                    increaseSanity(player);
                    playerSanityTimersIncrease.put(player, INCREASE_SANITY_INTERVAL);
                } else {
                    playerSanityTimersIncrease.put(player, timer - 1);
                }
            }

            if (blockState.is(TagRegistry.RESET_SANITY)) {
                inSanityBiome = true;
                int timer = playerSanityTimersReset.getOrDefault(player, 0);
                if (timer <= 0) {
                    resetSanity(player);
                    playerSanityTimersReset.put(player, RESET_SANITY_INTERVAL);
                } else {
                    playerSanityTimersReset.put(player, timer - 1);
                }
            }
        }

        if (hasSanityImmunity(player)) {
            return;
        }

        if (!inSanityBiome) {
            int outsideBiomeTimer = playerSanityTimersOutsideBiome.getOrDefault(player, 0);
            if (outsideBiomeTimer <= 0) {
                decreaseSanityOutsideBiome(player);
                playerSanityTimersOutsideBiome.put(player, OUTSIDE_BIOME_SANITY_LOSS_INTERVAL);
            } else {
                playerSanityTimersOutsideBiome.put(player, outsideBiomeTimer - 1);
            }
        } else {
            playerSanityTimersOutsideBiome.put(player, OUTSIDE_BIOME_SANITY_LOSS_INTERVAL);
        }
    }

    public static void increaseSanity(Player player) {
        int sanity = playerSanityMap.getOrDefault(player, 0);
        if (sanity < MAX_SANITY) {
            sanity += 2;
            playerSanityMap.put(player, sanity);

            if (sanity >= MAX_SANITY) {
                applyHighSanityEffect(player);
            }
        }
    }

    private static final Map<Player, Boolean> playerSanityImmunityMap = new HashMap<>();

    public static void setSanityImmunity(Player player, boolean isImmune) {
        playerSanityImmunityMap.put(player, isImmune);
    }

    public static boolean hasSanityImmunity(Player player) {
        return playerSanityImmunityMap.getOrDefault(player, false);
    }

    private static void applyHighSanityEffect(Player player) {
        int effectDuration = 400;
        player.addEffect(new MobEffectInstance(MobEffectRegistry.SANITY.get(), effectDuration, 1));
    }

    public static void decreaseSanity(Player player, int amount) {
        int sanity = playerSanityMap.getOrDefault(player, 0);
        sanity -= amount;
        sanity = Math.max(0, sanity);
        playerSanityMap.put(player, sanity);
    }

    private static void resetSanity(Player player) {
        int sanity = playerSanityMap.getOrDefault(player, 0);
        if (sanity >= 6) {
            sanity -= 6;
        } else {
            sanity = 0;
        }
        playerSanityMap.put(player, sanity);
    }

    public static boolean isSanityBarVisible(Player player) {
        return getSanity(player) < MAX_SANITY;
    }

    private static void decreaseSanityOutsideBiome(Player player) {
        int sanity = playerSanityMap.getOrDefault(player, 0);
        sanity -= OUTSIDE_BIOME_SANITY_LOSS;
        sanity = Math.max(0, sanity);
        playerSanityMap.put(player, sanity);
    }

    public static int getSanity(Player player) {
        return playerSanityMap.getOrDefault(player, 0);
    }
}

