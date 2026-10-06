package net.satisfy.sleepy_hollows.core.util;

import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import net.satisfy.sleepy_hollows.core.registry.ArmorSetRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;
import net.satisfy.sleepy_hollows.core.registry.TagRegistry;

public class SanityManager {

    public static final String SANITY = "sanity";
    public static final int MINIMUM_SANITY = 0;
    public static final int MAXIMUM_SANITY = 100;

    private static int clientSanity = MAXIMUM_SANITY;

    public enum Modifiers {
        CANDY_CORN, DUSK_BERRY, LUMINOUS_WATER, SPECTRAL_PUMPKIN_PIE,
        INSIDE_BIOME, INSIDE_BIOME_NIGHT, OUTSIDE_BIOME,
        RESET_SANITY, DECREASE_SANITY, INFECTED_EFFECT, MENTAL_FORTITUDE;

        public int getValue() {
            return switch (this) {
                case CANDY_CORN -> PlatformHelper.getSanityCandyCornGain();
                case DUSK_BERRY -> PlatformHelper.getSanityDuskBerryGain();
                case LUMINOUS_WATER -> PlatformHelper.getSanityLuminousWaterGain();
                case SPECTRAL_PUMPKIN_PIE -> PlatformHelper.getSanityPumpkinPieGain();
                case INSIDE_BIOME -> -PlatformHelper.getSanityBiomeDayLoss();
                case INSIDE_BIOME_NIGHT -> -PlatformHelper.getSanityBiomeNightLoss();
                case OUTSIDE_BIOME -> PlatformHelper.getSanityOutsideBiomeGain();
                case DECREASE_SANITY -> -PlatformHelper.getSanityCursedBlockLoss();
                case INFECTED_EFFECT -> -PlatformHelper.getSanityInfectedLoss();
                case RESET_SANITY, MENTAL_FORTITUDE -> MAXIMUM_SANITY;
            };
        }
    }

    public static boolean isImmune(Player player) {
        return player.hasEffect(MobEffectRegistry.holder(MobEffectRegistry.MENTAL_FORTITUDE));
    }

    private static CompoundTag getSanityTag(ServerPlayer player) {
        return ((IEntitySavedData) player).impl$getPersistentData();
    }

    public static int getSanity(ServerPlayer player) {
        CompoundTag nbt = getSanityTag(player);
        if (!nbt.contains(SANITY, Tag.TAG_INT)) nbt.putInt(SANITY, MAXIMUM_SANITY);
        return nbt.getInt(SANITY);
    }

    public static void setSanity(ServerPlayer player, int sanity) {
        getSanityTag(player).putInt(SANITY, Math.clamp(sanity, MINIMUM_SANITY, MAXIMUM_SANITY));
        sync(player);
    }

    public static void changeSanity(ServerPlayer player, int amount) {
        if (amount == 0 || (amount < 0 && isImmune(player))) return;
        if (amount < 0) {
            float protection = ArmorSetRegistry.hauntboundPieces(player) * PlatformHelper.getSanityHauntboundProtection() / 100.0F;
            int prevented = 0;
            for (int i = 0; i < -amount; i++) {
                if (player.getRandom().nextFloat() < protection) prevented++;
            }
            amount += prevented;
            if (amount == 0) return;
        }
        int current = getSanity(player);
        int updated = Math.clamp(current + amount, MINIMUM_SANITY, MAXIMUM_SANITY);
        if (updated != current) setSanity(player, updated);
    }

    public static void changeSanity(ServerPlayer player, Modifiers modifier) {
        changeSanity(player, modifier.getValue());
    }

    public static void sync(ServerPlayer player) {
        SleepyHollowsNetwork.sendSanity(player, getSanity(player));
    }

    public static void doBlockCheck(ServerPlayer serverPlayer) {
        Level level = serverPlayer.level();
        BlockPos blockPos = serverPlayer.blockPosition();
        BlockState blockState = level.getBlockState(blockPos);

        if (blockState.is(TagRegistry.RESET_SANITY)) {
            changeSanity(serverPlayer, Modifiers.RESET_SANITY);
        }

        if (blockState.is(TagRegistry.DECREASE_SANITY)) {
            changeSanity(serverPlayer, Modifiers.DECREASE_SANITY);
        }
    }

    public static boolean isNearLight(ServerPlayer player) {
        int radius = PlatformHelper.getSanityLightRadius();
        if (radius <= 0) return false;
        BlockPos center = player.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-radius, -2, -radius), center.offset(radius, 3, radius))) {
            if (player.level().getBlockState(pos).is(TagRegistry.SANITY_LIGHT)) return true;
        }
        return false;
    }

    public static int getClientSanity() {
        return clientSanity;
    }

    public static void setClientSanity(int sanity) {
        clientSanity = sanity;
    }

    public static boolean isSanityBarVisible() {
        return clientSanity < MAXIMUM_SANITY;
    }
}
