package net.satisfy.sleepy_hollows.neoforge.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public final class SleepyHollowsNeoForgeConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue sanityEnabled;
    public static final ModConfigSpec.IntValue hudX;
    public static final ModConfigSpec.IntValue hudY;

    public static final ModConfigSpec.IntValue candyCornGain;
    public static final ModConfigSpec.IntValue duskBerryGain;
    public static final ModConfigSpec.IntValue luminousWaterGain;
    public static final ModConfigSpec.IntValue pumpkinPieGain;
    public static final ModConfigSpec.IntValue outsideBiomeGain;
    public static final ModConfigSpec.IntValue biomeDayLoss;
    public static final ModConfigSpec.IntValue biomeNightLoss;
    public static final ModConfigSpec.IntValue cursedBlockLoss;
    public static final ModConfigSpec.IntValue infectedLoss;
    public static final ModConfigSpec.IntValue lightRadius;
    public static final ModConfigSpec.IntValue hauntboundPieceProtectionPercent;
    public static final ModConfigSpec.IntValue insanitySeconds;
    public static final ModConfigSpec.IntValue duskBerryNutrition;
    public static final ModConfigSpec.DoubleValue duskBerrySaturation;
    public static final ModConfigSpec.IntValue candyCornNutrition;
    public static final ModConfigSpec.DoubleValue candyCornSaturation;
    public static final ModConfigSpec.IntValue pumpkinPieNutrition;
    public static final ModConfigSpec.DoubleValue pumpkinPieSaturation;
    public static final ModConfigSpec.IntValue luminousWaterNutrition;
    public static final ModConfigSpec.DoubleValue luminousWaterSaturation;
    public static final ModConfigSpec.DoubleValue splashHorsemanDamage;

    public static final ModConfigSpec.BooleanValue fogEnabled;
    public static final ModConfigSpec.IntValue fogStart;
    public static final ModConfigSpec.IntValue fogEnd;
    public static final ModConfigSpec.IntValue fogTransition;
    public static final ModConfigSpec.IntValue fogSanity;

    public static final ModConfigSpec.BooleanValue ambienceEnabled;
    public static final ModConfigSpec.IntValue ambienceDayMin;
    public static final ModConfigSpec.IntValue ambienceDayMax;
    public static final ModConfigSpec.IntValue ambienceNightMin;
    public static final ModConfigSpec.IntValue ambienceNightMax;
    public static final ModConfigSpec.IntValue ambienceVolume;

    public static final ModConfigSpec.DoubleValue horsemanMaxHealth;
    public static final ModConfigSpec.DoubleValue horsemanArmor;
    public static final ModConfigSpec.DoubleValue horsemanMovementSpeed;
    public static final ModConfigSpec.DoubleValue horsemanAttackDamage;
    public static final ModConfigSpec.DoubleValue horsemanAttackKnockback;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> horsemanLootItems;

    public static final ModConfigSpec.DoubleValue fleeingPumpkinHeadMaxHealth;
    public static final ModConfigSpec.DoubleValue fleeingPumpkinHeadArmor;
    public static final ModConfigSpec.DoubleValue fleeingPumpkinHeadMovementSpeed;

    public static final ModConfigSpec.DoubleValue infectedZombieMaxHealth;
    public static final ModConfigSpec.DoubleValue infectedZombieArmor;
    public static final ModConfigSpec.DoubleValue infectedZombieMovementSpeed;
    public static final ModConfigSpec.DoubleValue infectedZombieAttackDamage;

    public static final ModConfigSpec.DoubleValue spectralToolSpeed;
    public static final ModConfigSpec.DoubleValue spectralToolDamage;
    public static final ModConfigSpec.DoubleValue raubbauToolSpeed;
    public static final ModConfigSpec.DoubleValue raubbauToolDamage;

    public static final ModConfigSpec.BooleanValue enableSetBonus;
    public static final ModConfigSpec.IntValue helmetDefense;
    public static final ModConfigSpec.IntValue chestplateDefense;
    public static final ModConfigSpec.IntValue leggingsDefense;
    public static final ModConfigSpec.IntValue bootsDefense;
    public static final ModConfigSpec.IntValue armorDurabilityMultiplier;
    public static final ModConfigSpec.DoubleValue armorToughness;
    public static final ModConfigSpec.DoubleValue armorKnockbackResistance;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("sanity");
        sanityEnabled = builder.define("enabled", true);
        hudX = builder.defineInRange("hudX", 0, -10000, 10000);
        hudY = builder.defineInRange("hudY", 0, -10000, 10000);
        candyCornGain = builder.defineInRange("candyCornGain", 4, 0, 100);
        duskBerryGain = builder.defineInRange("duskBerryGain", 2, 0, 100);
        luminousWaterGain = builder.defineInRange("luminousWaterGain", 6, 0, 100);
        pumpkinPieGain = builder.defineInRange("pumpkinPieGain", 10, 0, 100);
        outsideBiomeGain = builder.defineInRange("outsideBiomeGain", 5, 0, 100);
        biomeDayLoss = builder.defineInRange("biomeDayLoss", 1, 0, 100);
        biomeNightLoss = builder.defineInRange("biomeNightLoss", 2, 0, 100);
        cursedBlockLoss = builder.defineInRange("cursedBlockLoss", 2, 0, 100);
        infectedLoss = builder.defineInRange("infectedLoss", 2, 0, 100);
        lightRadius = builder.defineInRange("lightRadius", 4, 0, 16);
        hauntboundPieceProtectionPercent = builder.defineInRange("hauntboundPieceProtectionPercent", 25, 0, 100);
        insanitySeconds = builder.defineInRange("insanitySeconds", 60, 1, 600);
        builder.pop();

        builder.push("fog");
        fogEnabled = builder.define("enabled", true);
        fogStart = builder.defineInRange("startPercent", 5, 0, 100);
        fogEnd = builder.defineInRange("endPercent", 45, 1, 100);
        fogTransition = builder.defineInRange("transitionSeconds", 6, 0, 30);
        fogSanity = builder.defineInRange("sanityInfluencePercent", 40, 0, 100);
        builder.pop();

        builder.push("ambience");
        ambienceEnabled = builder.define("enabled", true);
        ambienceDayMin = builder.defineInRange("dayMinSeconds", 20, 1, 600);
        ambienceDayMax = builder.defineInRange("dayMaxSeconds", 45, 1, 600);
        ambienceNightMin = builder.defineInRange("nightMinSeconds", 8, 1, 600);
        ambienceNightMax = builder.defineInRange("nightMaxSeconds", 20, 1, 600);
        ambienceVolume = builder.defineInRange("volumePercent", 100, 0, 100);
        builder.pop();

        builder.push("food");
        duskBerryNutrition = builder.defineInRange("duskBerryNutrition", 2, 0, 20);
        duskBerrySaturation = builder.defineInRange("duskBerrySaturation", 0.1, 0.0, 2.0);
        candyCornNutrition = builder.defineInRange("candyCornNutrition", 2, 0, 20);
        candyCornSaturation = builder.defineInRange("candyCornSaturation", 0.1, 0.0, 2.0);
        pumpkinPieNutrition = builder.defineInRange("pumpkinPieNutrition", 8, 0, 20);
        pumpkinPieSaturation = builder.defineInRange("pumpkinPieSaturation", 0.3, 0.0, 2.0);
        luminousWaterNutrition = builder.defineInRange("luminousWaterNutrition", 4, 0, 20);
        luminousWaterSaturation = builder.defineInRange("luminousWaterSaturation", 0.3, 0.0, 2.0);
        builder.pop();

        builder.push("horseman");
        horsemanMaxHealth = builder.defineInRange("maxHealth", 180.0, 1.0, 10000.0);
        horsemanArmor = builder.defineInRange("armor", 26.0, 0.0, 30.0);
        horsemanMovementSpeed = builder.defineInRange("movementSpeed", 0.34, 0.0, 1.0);
        horsemanAttackDamage = builder.defineInRange("attackDamage", 16.0, 0.0, 100.0);
        horsemanAttackKnockback = builder.defineInRange("attackKnockback", 0.0, 0.0, 5.0);
        horsemanLootItems = builder.comment("Format: namespace:item:count").defineListAllowEmpty("lootItems", List.of("sleepy_hollows:lootbag:3"), () -> "sleepy_hollows:lootbag:1", entry -> entry instanceof String string && string.split(":").length == 3);
        builder.pop();

        builder.push("fleeingPumpkinHead");
        fleeingPumpkinHeadMaxHealth = builder.defineInRange("maxHealth", 100.0, 1.0, 1000.0);
        fleeingPumpkinHeadArmor = builder.defineInRange("armor", 22.0, 0.0, 30.0);
        fleeingPumpkinHeadMovementSpeed = builder.defineInRange("movementSpeed", 0.43, 0.0, 1.0);
        builder.pop();

        builder.push("infectedZombie");
        infectedZombieMaxHealth = builder.defineInRange("maxHealth", 30.0, 1.0, 1000.0);
        infectedZombieArmor = builder.defineInRange("armor", 1.0, 0.0, 30.0);
        infectedZombieMovementSpeed = builder.defineInRange("movementSpeed", 0.23, 0.0, 1.0);
        infectedZombieAttackDamage = builder.defineInRange("attackDamage", 5.0, 0.0, 1000.0);
        builder.pop();

        builder.push("weapons");
        spectralToolSpeed = builder.defineInRange("spectralToolSpeed", 8.0, 0.0, 20.0);
        spectralToolDamage = builder.defineInRange("spectralToolDamage", 5.0, 0.0, 20.0);
        raubbauToolSpeed = builder.defineInRange("raubbauToolSpeed", 6.0, 0.0, 20.0);
        raubbauToolDamage = builder.defineInRange("raubbauToolDamage", 3.0, 0.0, 20.0);
        splashHorsemanDamage = builder.defineInRange("splashHorsemanDamage", 20.0, 0.0, 200.0);
        builder.pop();

        builder.push("armor");
        enableSetBonus = builder.define("enableSetBonus", true);
        helmetDefense = builder.defineInRange("helmetDefense", 3, 0, 20);
        chestplateDefense = builder.defineInRange("chestplateDefense", 8, 0, 20);
        leggingsDefense = builder.defineInRange("leggingsDefense", 6, 0, 20);
        bootsDefense = builder.defineInRange("bootsDefense", 3, 0, 20);
        armorDurabilityMultiplier = builder.defineInRange("durabilityMultiplier", 33, 1, 100);
        armorToughness = builder.defineInRange("toughness", 2.0, 0.0, 10.0);
        armorKnockbackResistance = builder.defineInRange("knockbackResistance", 0.05, 0.0, 1.0);
        builder.pop();

        SPEC = builder.build();
    }

    private SleepyHollowsNeoForgeConfig() {
    }
}
