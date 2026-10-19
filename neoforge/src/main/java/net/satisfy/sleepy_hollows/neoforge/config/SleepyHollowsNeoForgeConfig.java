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

        builder.comment("Sanity loss and recovery in the Sleepy Hollows biome.").push("Sanity");
        sanityEnabled = builder.comment("Players lose sanity in the Sleepy Hollows biome and suffer when it runs out.").define("enableSanity", true);
        hudX = builder.comment("Moves the sanity bar sideways in pixels. Negative values move it to the left.").defineInRange("hudX", 0, -10000, 10000);
        hudY = builder.comment("Moves the sanity bar up or down in pixels. Negative values move it up.").defineInRange("hudY", 0, -10000, 10000);
        candyCornGain = builder.comment("Sanity points restored by eating Candy Corn.").defineInRange("candyCornGain", 4, 0, 100);
        duskBerryGain = builder.comment("Sanity points restored by eating Dusk Berries.").defineInRange("duskBerryGain", 2, 0, 100);
        luminousWaterGain = builder.comment("Sanity points restored by drinking Luminous Water.").defineInRange("luminousWaterGain", 6, 0, 100);
        pumpkinPieGain = builder.comment("Sanity points restored by eating Spectral Pumpkin Pie.").defineInRange("pumpkinPieGain", 10, 0, 100);
        outsideBiomeGain = builder.comment("Sanity points restored every 5 seconds while outside the biome.").defineInRange("outsideBiomeGain", 5, 0, 100);
        biomeDayLoss = builder.comment("Sanity points lost every 5 seconds in the biome during the day.").defineInRange("biomeDayLoss", 1, 0, 100);
        biomeNightLoss = builder.comment("Sanity points lost every 5 seconds in the biome at night.").defineInRange("biomeNightLoss", 2, 0, 100);
        cursedBlockLoss = builder.comment("Sanity points lost every second while standing on cursed blocks.").defineInRange("cursedBlockLoss", 2, 0, 100);
        infectedLoss = builder.comment("Sanity points lost every 3 seconds while infected.").defineInRange("infectedLoss", 2, 0, 100);
        lightRadius = builder.comment("Radius in blocks around a light source in which you lose no sanity.").defineInRange("lightRadius", 4, 0, 16);
        hauntboundPieceProtectionPercent = builder.comment("Percent less sanity loss for each Hauntbound armor piece you wear.").defineInRange("hauntboundPieceProtectionPercent", 25, 0, 100);
        insanitySeconds = builder.comment("How many seconds the insanity lasts once your sanity runs out.").defineInRange("insanitySeconds", 60, 1, 600);
        builder.pop();

        builder.comment("Fog in the Sleepy Hollows biome. Only affects your own screen.").push("Fog");
        fogEnabled = builder.comment("Thick fog covers the Sleepy Hollows biome.").define("enableFog", true);
        fogStart = builder.comment("Where the fog starts, in percent of your render distance.").defineInRange("startPercent", 5, 0, 100);
        fogEnd = builder.comment("Where the fog is fully opaque, in percent of your render distance.").defineInRange("endPercent", 45, 1, 100);
        fogTransition = builder.comment("How many seconds the fog takes to fade in and out.").defineInRange("transitionSeconds", 6, 0, 30);
        fogSanity = builder.comment("How much thicker the fog gets at low sanity, in percent.").defineInRange("sanityInfluencePercent", 40, 0, 100);
        builder.pop();

        builder.comment("Ambient sounds in the Sleepy Hollows biome. Only affects your own screen.").push("Ambience");
        ambienceEnabled = builder.comment("Plays eerie ambient sounds in the Sleepy Hollows biome.").define("enableAmbience", true);
        ambienceDayMin = builder.comment("Shortest pause between two sounds during the day, in seconds.").defineInRange("dayMinSeconds", 20, 1, 600);
        ambienceDayMax = builder.comment("Longest pause between two sounds during the day, in seconds.").defineInRange("dayMaxSeconds", 45, 1, 600);
        ambienceNightMin = builder.comment("Shortest pause between two sounds at night, in seconds.").defineInRange("nightMinSeconds", 8, 1, 600);
        ambienceNightMax = builder.comment("Longest pause between two sounds at night, in seconds.").defineInRange("nightMaxSeconds", 20, 1, 600);
        ambienceVolume = builder.comment("Volume of the ambient sounds in percent.").defineInRange("volumePercent", 100, 0, 100);
        builder.pop();

        builder.comment("Attributes of the Horseman.").push("Horseman");
        horsemanMaxHealth = builder.comment("Health points of the Horseman. 2 points = 1 heart.").defineInRange("maxHealth", 180.0, 1.0, 10000.0);
        horsemanArmor = builder.comment("Armor points of the Horseman.").defineInRange("armor", 26.0, 0.0, 30.0);
        horsemanMovementSpeed = builder.comment("Movement speed of the Horseman. A player walks at 0.1.").defineInRange("movementSpeed", 0.34, 0.0, 1.0);
        horsemanAttackDamage = builder.comment("Damage per hit of the Horseman. 2 points = 1 heart.").defineInRange("attackDamage", 16.0, 0.0, 100.0);
        horsemanAttackKnockback = builder.comment("How far the Horseman knocks you back.").defineInRange("attackKnockback", 0.0, 0.0, 5.0);
        horsemanLootItems = builder.comment("Items the Horseman drops, one entry per item as namespace:item:count.").defineListAllowEmpty("lootItems", List.of("sleepy_hollows:lootbag:3"), () -> "sleepy_hollows:lootbag:1", entry -> entry instanceof String string && string.split(":").length == 3);
        builder.pop();

        builder.comment("Attributes of the Fleeing Pumpkin Head.").push("Fleeing Pumpkin Head");
        fleeingPumpkinHeadMaxHealth = builder.comment("Health points of the Fleeing Pumpkin Head. 2 points = 1 heart.").defineInRange("maxHealth", 100.0, 1.0, 1000.0);
        fleeingPumpkinHeadArmor = builder.comment("Armor points of the Fleeing Pumpkin Head.").defineInRange("armor", 22.0, 0.0, 30.0);
        fleeingPumpkinHeadMovementSpeed = builder.comment("Movement speed of the Fleeing Pumpkin Head. A player walks at 0.1.").defineInRange("movementSpeed", 0.43, 0.0, 1.0);
        builder.pop();

        builder.comment("Attributes of the Infected Zombie.").push("Infected Zombie");
        infectedZombieMaxHealth = builder.comment("Health points of the Infected Zombie. 2 points = 1 heart.").defineInRange("maxHealth", 30.0, 1.0, 1000.0);
        infectedZombieArmor = builder.comment("Armor points of the Infected Zombie.").defineInRange("armor", 1.0, 0.0, 30.0);
        infectedZombieMovementSpeed = builder.comment("Movement speed of the Infected Zombie. A player walks at 0.1.").defineInRange("movementSpeed", 0.23, 0.0, 1.0);
        infectedZombieAttackDamage = builder.comment("Damage per hit of the Infected Zombie. 2 points = 1 heart.").defineInRange("attackDamage", 5.0, 0.0, 1000.0);
        builder.pop();

        builder.comment("Attributes of the weapons and tools.").push("Weapons");
        spectralToolSpeed = builder.comment("Mining speed of the spectral tools.").defineInRange("spectralToolSpeed", 8.0, 0.0, 20.0);
        spectralToolDamage = builder.comment("Attack damage of the spectral tools. 2 points = 1 heart.").defineInRange("spectralToolDamage", 5.0, 0.0, 20.0);
        raubbauToolSpeed = builder.comment("Mining speed of the Raubbau Shovel.").defineInRange("raubbauToolSpeed", 6.0, 0.0, 20.0);
        raubbauToolDamage = builder.comment("Attack damage of the Raubbau Shovel. 2 points = 1 heart.").defineInRange("raubbauToolDamage", 3.0, 0.0, 20.0);
        splashHorsemanDamage = builder.comment("Damage that Splash Luminous Water deals to the Horseman. 2 points = 1 heart.").defineInRange("splashHorsemanDamage", 20.0, 0.0, 200.0);
        builder.pop();

        builder.comment("Attributes of the Hauntbound armor.").push("Armor");
        enableSetBonus = builder.comment("Wearing the full Hauntbound armor gives a set bonus.").define("enableSetBonus", true);
        helmetDefense = builder.comment("Armor points of the Hauntbound Helmet.").defineInRange("helmetDefense", 3, 0, 20);
        chestplateDefense = builder.comment("Armor points of the Hauntbound Chestplate.").defineInRange("chestplateDefense", 8, 0, 20);
        leggingsDefense = builder.comment("Armor points of the Hauntbound Leggings.").defineInRange("leggingsDefense", 6, 0, 20);
        bootsDefense = builder.comment("Armor points of the Hauntbound Boots.").defineInRange("bootsDefense", 3, 0, 20);
        armorDurabilityMultiplier = builder.comment("Durability multiplier of the Hauntbound armor. Iron armor uses 15.").defineInRange("durabilityMultiplier", 33, 1, 100);
        armorToughness = builder.comment("Armor toughness of each Hauntbound piece.").defineInRange("toughness", 2.0, 0.0, 10.0);
        armorKnockbackResistance = builder.comment("Knockback resistance of each Hauntbound piece. 0.1 = 10 percent.").defineInRange("knockbackResistance", 0.05, 0.0, 1.0);
        builder.pop();

        builder.comment("Hunger and saturation of the food items. Changes need a game restart.").push("Food");
        duskBerryNutrition = builder.comment("Hunger points restored by Dusk Berries. 2 points = 1 drumstick.").defineInRange("duskBerryNutrition", 2, 0, 20);
        duskBerrySaturation = builder.comment("Saturation given by Dusk Berries, multiplied with its hunger points.").defineInRange("duskBerrySaturation", 0.1, 0.0, 2.0);
        candyCornNutrition = builder.comment("Hunger points restored by Candy Corn. 2 points = 1 drumstick.").defineInRange("candyCornNutrition", 2, 0, 20);
        candyCornSaturation = builder.comment("Saturation given by Candy Corn, multiplied with its hunger points.").defineInRange("candyCornSaturation", 0.1, 0.0, 2.0);
        pumpkinPieNutrition = builder.comment("Hunger points restored by Spectral Pumpkin Pie. 2 points = 1 drumstick.").defineInRange("pumpkinPieNutrition", 8, 0, 20);
        pumpkinPieSaturation = builder.comment("Saturation given by Spectral Pumpkin Pie, multiplied with its hunger points.").defineInRange("pumpkinPieSaturation", 0.3, 0.0, 2.0);
        luminousWaterNutrition = builder.comment("Hunger points restored by Luminous Water. 2 points = 1 drumstick.").defineInRange("luminousWaterNutrition", 4, 0, 20);
        luminousWaterSaturation = builder.comment("Saturation given by Luminous Water, multiplied with its hunger points.").defineInRange("luminousWaterSaturation", 0.3, 0.0, 2.0);
        builder.pop();

        SPEC = builder.build();
    }

    private SleepyHollowsNeoForgeConfig() {
    }
}
