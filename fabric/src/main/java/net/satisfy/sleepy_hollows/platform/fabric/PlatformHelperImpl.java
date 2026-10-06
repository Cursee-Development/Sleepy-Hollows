package net.satisfy.sleepy_hollows.platform.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.world.item.ArmorItem;
import net.satisfy.sleepy_hollows.fabric.config.SleepyHollowsFabricConfig;

import java.util.List;

public class PlatformHelperImpl {
    private static SleepyHollowsFabricConfig c() {
        return AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
    }

    public static double getHorsemanMovementSpeed() {
        return c().horseman.movementSpeed;
    }

    public static double getHorsemanMaxHealth() {
        return c().horseman.maxHealth;
    }

    public static double getHorsemanAttackDamage() {
        return c().horseman.attackDamage;
    }

    public static double getHorsemanAttackKnockback() {
        return c().horseman.attackKnockback;
    }

    public static double getHorsemanArmor() {
        return c().horseman.armor;
    }

    public static List<String> getHorsemanLootEntries() {
        return c().horseman.lootItems;
    }

    public static double getFleeingPumpkinMaxHealth() {
        return c().fleeingPumpkinHead.maxHealth;
    }

    public static double getFleeingPumpkinMovementSpeed() {
        return c().fleeingPumpkinHead.movementSpeed;
    }

    public static double getFleeingPumpkinArmor() {
        return c().fleeingPumpkinHead.armor;
    }

    public static double getInfectedZombieMaxHealth() {
        return c().infectedZombie.maxHealth;
    }

    public static double getInfectedZombieArmor() {
        return c().infectedZombie.armor;
    }

    public static double getInfectedZombieMovementSpeed() {
        return c().infectedZombie.movementSpeed;
    }

    public static double getInfectedZombieAttackDamage() {
        return c().infectedZombie.attackDamage;
    }

    public static double getSpectralToolSpeed() {
        return c().weapons.spectralToolSpeed;
    }

    public static double getSpectralToolDamage() {
        return c().weapons.spectralToolDamage;
    }

    public static double getRaubbauToolSpeed() {
        return c().weapons.raubbauToolSpeed;
    }

    public static double getRaubbauToolDamage() {
        return c().weapons.raubbauToolDamage;
    }

    public static boolean isHauntboundSetBonusEnabled() {
        return c().armor.enableSetBonus;
    }

    public static int getHauntboundDurabilityMultiplier() {
        return c().armor.durabilityMultiplier;
    }

    public static float getHauntboundToughness() {
        return (float) c().armor.toughness;
    }

    public static float getHauntboundKnockbackResistance() {
        return (float) c().armor.knockbackResistance;
    }

    public static boolean isSanityEnabled() {
        return c().sanity.enabled;
    }

    public static int getHUDX() {
        return c().sanity.hudX;
    }

    public static int getHUDY() {
        return c().sanity.hudY;
    }

    public static boolean isHollowFogEnabled() {
        return c().fog.enabled;
    }

    public static float getHollowFogStart() {
        return c().fog.startPercent / 100.0F;
    }

    public static float getHollowFogEnd() {
        return c().fog.endPercent / 100.0F;
    }

    public static int getHollowFogTransitionSeconds() {
        return c().fog.transitionSeconds;
    }

    public static float getHollowFogSanityInfluence() {
        return c().fog.sanityInfluencePercent / 100.0F;
    }

    public static int getHauntboundDefense(ArmorItem.Type type) {
        SleepyHollowsFabricConfig.ArmorSettings armor = c().armor;
        return switch (type) {
            case HELMET -> armor.helmetDefense;
            case CHESTPLATE -> armor.chestplateDefense;
            case LEGGINGS -> armor.leggingsDefense;
            case BOOTS -> armor.bootsDefense;
            default -> 0;
        };
    }

    public static boolean isAmbienceEnabled() {
        return c().ambience.enabled;
    }

    public static int getAmbienceDayMinSeconds() {
        return c().ambience.dayMinSeconds;
    }

    public static int getAmbienceDayMaxSeconds() {
        return Math.max(c().ambience.dayMinSeconds, c().ambience.dayMaxSeconds);
    }

    public static int getAmbienceNightMinSeconds() {
        return c().ambience.nightMinSeconds;
    }

    public static int getAmbienceNightMaxSeconds() {
        return Math.max(c().ambience.nightMinSeconds, c().ambience.nightMaxSeconds);
    }

    public static float getAmbienceVolume() {
        return c().ambience.volumePercent / 100.0F;
    }

    public static int getSanityCandyCornGain() {
        return c().sanity.candyCornGain;
    }

    public static int getSanityDuskBerryGain() {
        return c().sanity.duskBerryGain;
    }

    public static int getSanityLuminousWaterGain() {
        return c().sanity.luminousWaterGain;
    }

    public static int getSanityPumpkinPieGain() {
        return c().sanity.pumpkinPieGain;
    }

    public static int getSanityOutsideBiomeGain() {
        return c().sanity.outsideBiomeGain;
    }

    public static int getSanityBiomeDayLoss() {
        return c().sanity.biomeDayLoss;
    }

    public static int getSanityBiomeNightLoss() {
        return c().sanity.biomeNightLoss;
    }

    public static int getSanityCursedBlockLoss() {
        return c().sanity.cursedBlockLoss;
    }

    public static int getSanityInfectedLoss() {
        return c().sanity.infectedLoss;
    }

    public static int getSanityLightRadius() {
        return c().sanity.lightRadius;
    }

    public static int getSanityHauntboundProtection() {
        return c().sanity.hauntboundPieceProtectionPercent;
    }

    public static int getSanityInsanitySeconds() {
        return c().sanity.insanitySeconds;
    }

    public static int getFoodDuskBerryNutrition() {
        return c().food.duskBerryNutrition;
    }

    public static float getFoodDuskBerrySaturation() {
        return (float) c().food.duskBerrySaturation;
    }

    public static int getFoodCandyCornNutrition() {
        return c().food.candyCornNutrition;
    }

    public static float getFoodCandyCornSaturation() {
        return (float) c().food.candyCornSaturation;
    }

    public static int getFoodPumpkinPieNutrition() {
        return c().food.pumpkinPieNutrition;
    }

    public static float getFoodPumpkinPieSaturation() {
        return (float) c().food.pumpkinPieSaturation;
    }

    public static int getFoodLuminousWaterNutrition() {
        return c().food.luminousWaterNutrition;
    }

    public static float getFoodLuminousWaterSaturation() {
        return (float) c().food.luminousWaterSaturation;
    }

    public static float getSplashHorsemanDamage() {
        return (float) c().weapons.splashHorsemanDamage;
    }
}
