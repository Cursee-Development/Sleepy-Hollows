package net.satisfy.sleepy_hollows.platform.neoforge;

import net.minecraft.world.item.ArmorItem;
import net.satisfy.sleepy_hollows.neoforge.config.SleepyHollowsNeoForgeConfig;

import java.util.List;

public class PlatformHelperImpl {
    public static double getHorsemanMovementSpeed() {
        return SleepyHollowsNeoForgeConfig.horsemanMovementSpeed.get();
    }

    public static double getHorsemanMaxHealth() {
        return SleepyHollowsNeoForgeConfig.horsemanMaxHealth.get();
    }

    public static double getHorsemanAttackDamage() {
        return SleepyHollowsNeoForgeConfig.horsemanAttackDamage.get();
    }

    public static double getHorsemanAttackKnockback() {
        return SleepyHollowsNeoForgeConfig.horsemanAttackKnockback.get();
    }

    public static double getHorsemanArmor() {
        return SleepyHollowsNeoForgeConfig.horsemanArmor.get();
    }

    public static List<String> getHorsemanLootEntries() {
        return List.copyOf(SleepyHollowsNeoForgeConfig.horsemanLootItems.get());
    }

    public static double getFleeingPumpkinMaxHealth() {
        return SleepyHollowsNeoForgeConfig.fleeingPumpkinHeadMaxHealth.get();
    }

    public static double getFleeingPumpkinMovementSpeed() {
        return SleepyHollowsNeoForgeConfig.fleeingPumpkinHeadMovementSpeed.get();
    }

    public static double getFleeingPumpkinArmor() {
        return SleepyHollowsNeoForgeConfig.fleeingPumpkinHeadArmor.get();
    }

    public static double getInfectedZombieMaxHealth() {
        return SleepyHollowsNeoForgeConfig.infectedZombieMaxHealth.get();
    }

    public static double getInfectedZombieArmor() {
        return SleepyHollowsNeoForgeConfig.infectedZombieArmor.get();
    }

    public static double getInfectedZombieMovementSpeed() {
        return SleepyHollowsNeoForgeConfig.infectedZombieMovementSpeed.get();
    }

    public static double getInfectedZombieAttackDamage() {
        return SleepyHollowsNeoForgeConfig.infectedZombieAttackDamage.get();
    }

    public static double getSpectralToolSpeed() {
        return SleepyHollowsNeoForgeConfig.spectralToolSpeed.get();
    }

    public static double getSpectralToolDamage() {
        return SleepyHollowsNeoForgeConfig.spectralToolDamage.get();
    }

    public static double getRaubbauToolSpeed() {
        return SleepyHollowsNeoForgeConfig.raubbauToolSpeed.get();
    }

    public static double getRaubbauToolDamage() {
        return SleepyHollowsNeoForgeConfig.raubbauToolDamage.get();
    }

    public static boolean isHauntboundSetBonusEnabled() {
        return SleepyHollowsNeoForgeConfig.enableSetBonus.get();
    }

    public static int getHauntboundDurabilityMultiplier() {
        return SleepyHollowsNeoForgeConfig.armorDurabilityMultiplier.get();
    }

    public static float getHauntboundToughness() {
        return SleepyHollowsNeoForgeConfig.armorToughness.get().floatValue();
    }

    public static float getHauntboundKnockbackResistance() {
        return SleepyHollowsNeoForgeConfig.armorKnockbackResistance.get().floatValue();
    }

    public static boolean isSanityEnabled() {
        return SleepyHollowsNeoForgeConfig.sanityEnabled.get();
    }

    public static int getHUDX() {
        return SleepyHollowsNeoForgeConfig.hudX.get();
    }

    public static int getHUDY() {
        return SleepyHollowsNeoForgeConfig.hudY.get();
    }

    public static boolean isHollowFogEnabled() {
        return SleepyHollowsNeoForgeConfig.fogEnabled.get();
    }

    public static float getHollowFogStart() {
        return SleepyHollowsNeoForgeConfig.fogStart.get() / 100.0F;
    }

    public static float getHollowFogEnd() {
        return SleepyHollowsNeoForgeConfig.fogEnd.get() / 100.0F;
    }

    public static int getHollowFogTransitionSeconds() {
        return SleepyHollowsNeoForgeConfig.fogTransition.get();
    }

    public static float getHollowFogSanityInfluence() {
        return SleepyHollowsNeoForgeConfig.fogSanity.get() / 100.0F;
    }

    public static int getHauntboundDefense(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> SleepyHollowsNeoForgeConfig.helmetDefense.get();
            case CHESTPLATE -> SleepyHollowsNeoForgeConfig.chestplateDefense.get();
            case LEGGINGS -> SleepyHollowsNeoForgeConfig.leggingsDefense.get();
            case BOOTS -> SleepyHollowsNeoForgeConfig.bootsDefense.get();
            default -> 0;
        };
    }

    public static boolean isAmbienceEnabled() {
        return SleepyHollowsNeoForgeConfig.ambienceEnabled.get();
    }

    public static int getAmbienceDayMinSeconds() {
        return SleepyHollowsNeoForgeConfig.ambienceDayMin.get();
    }

    public static int getAmbienceDayMaxSeconds() {
        return Math.max(SleepyHollowsNeoForgeConfig.ambienceDayMin.get(), SleepyHollowsNeoForgeConfig.ambienceDayMax.get());
    }

    public static int getAmbienceNightMinSeconds() {
        return SleepyHollowsNeoForgeConfig.ambienceNightMin.get();
    }

    public static int getAmbienceNightMaxSeconds() {
        return Math.max(SleepyHollowsNeoForgeConfig.ambienceNightMin.get(), SleepyHollowsNeoForgeConfig.ambienceNightMax.get());
    }

    public static float getAmbienceVolume() {
        return SleepyHollowsNeoForgeConfig.ambienceVolume.get() / 100.0F;
    }

    public static int getSanityCandyCornGain() {
        return SleepyHollowsNeoForgeConfig.candyCornGain.get();
    }

    public static int getSanityDuskBerryGain() {
        return SleepyHollowsNeoForgeConfig.duskBerryGain.get();
    }

    public static int getSanityLuminousWaterGain() {
        return SleepyHollowsNeoForgeConfig.luminousWaterGain.get();
    }

    public static int getSanityPumpkinPieGain() {
        return SleepyHollowsNeoForgeConfig.pumpkinPieGain.get();
    }

    public static int getSanityOutsideBiomeGain() {
        return SleepyHollowsNeoForgeConfig.outsideBiomeGain.get();
    }

    public static int getSanityBiomeDayLoss() {
        return SleepyHollowsNeoForgeConfig.biomeDayLoss.get();
    }

    public static int getSanityBiomeNightLoss() {
        return SleepyHollowsNeoForgeConfig.biomeNightLoss.get();
    }

    public static int getSanityCursedBlockLoss() {
        return SleepyHollowsNeoForgeConfig.cursedBlockLoss.get();
    }

    public static int getSanityInfectedLoss() {
        return SleepyHollowsNeoForgeConfig.infectedLoss.get();
    }

    public static int getSanityLightRadius() {
        return SleepyHollowsNeoForgeConfig.lightRadius.get();
    }

    public static int getSanityHauntboundProtection() {
        return SleepyHollowsNeoForgeConfig.hauntboundPieceProtectionPercent.get();
    }

    public static int getSanityInsanitySeconds() {
        return SleepyHollowsNeoForgeConfig.insanitySeconds.get();
    }

    public static int getFoodDuskBerryNutrition() {
        return SleepyHollowsNeoForgeConfig.duskBerryNutrition.get();
    }

    public static float getFoodDuskBerrySaturation() {
        return SleepyHollowsNeoForgeConfig.duskBerrySaturation.get().floatValue();
    }

    public static int getFoodCandyCornNutrition() {
        return SleepyHollowsNeoForgeConfig.candyCornNutrition.get();
    }

    public static float getFoodCandyCornSaturation() {
        return SleepyHollowsNeoForgeConfig.candyCornSaturation.get().floatValue();
    }

    public static int getFoodPumpkinPieNutrition() {
        return SleepyHollowsNeoForgeConfig.pumpkinPieNutrition.get();
    }

    public static float getFoodPumpkinPieSaturation() {
        return SleepyHollowsNeoForgeConfig.pumpkinPieSaturation.get().floatValue();
    }

    public static int getFoodLuminousWaterNutrition() {
        return SleepyHollowsNeoForgeConfig.luminousWaterNutrition.get();
    }

    public static float getFoodLuminousWaterSaturation() {
        return SleepyHollowsNeoForgeConfig.luminousWaterSaturation.get().floatValue();
    }

    public static float getSplashHorsemanDamage() {
        return SleepyHollowsNeoForgeConfig.splashHorsemanDamage.get().floatValue();
    }
}
