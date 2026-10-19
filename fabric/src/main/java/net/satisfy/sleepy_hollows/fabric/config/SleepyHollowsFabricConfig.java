package net.satisfy.sleepy_hollows.fabric.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;

@Config(name = "sleepy_hollows")
@Config.Gui.Background("sleepy_hollows:textures/block/gravestone.png")
public class SleepyHollowsFabricConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public SanitySettings sanity = new SanitySettings();

    @ConfigEntry.Gui.CollapsibleObject
    public FogSettings fog = new FogSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public AmbienceSettings ambience = new AmbienceSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public HorsemanSettings horseman = new HorsemanSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public FleeingPumpkinHeadSettings fleeingPumpkinHead = new FleeingPumpkinHeadSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public InfectedZombieSettings infectedZombie = new InfectedZombieSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public WeaponsSettings weapons = new WeaponsSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public ArmorSettings armor = new ArmorSettings();

    @ConfigEntry.Gui.CollapsibleObject
    public FoodSettings food = new FoodSettings();

    public static class SanitySettings {
        @ConfigEntry.Gui.Tooltip
        public boolean enableSanity = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = -10000, max = 10000)
        public int hudX = 0;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = -10000, max = 10000)
        public int hudY = 0;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int candyCornGain = 4;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int duskBerryGain = 2;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int luminousWaterGain = 6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int pumpkinPieGain = 10;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int outsideBiomeGain = 5;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int biomeDayLoss = 1;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int biomeNightLoss = 2;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int cursedBlockLoss = 2;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int infectedLoss = 2;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
        public int lightRadius = 4;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int hauntboundPieceProtectionPercent = 25;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 600)
        public int insanitySeconds = 60;
    }

    public static class FogSettings {
        @ConfigEntry.Gui.Tooltip
        public boolean enableFog = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int startPercent = 5;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int endPercent = 45;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 30)
        public int transitionSeconds = 6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int sanityInfluencePercent = 40;
    }

    public static class AmbienceSettings {
        @ConfigEntry.Gui.Tooltip
        public boolean enableAmbience = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 600)
        public int dayMinSeconds = 20;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 600)
        public int dayMaxSeconds = 45;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 600)
        public int nightMinSeconds = 8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 600)
        public int nightMaxSeconds = 20;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int volumePercent = 100;
    }

    public static class HorsemanSettings {
        @ConfigEntry.Gui.Tooltip
        public double maxHealth = 180.0;

        @ConfigEntry.Gui.Tooltip
        public double armor = 26.0;

        @ConfigEntry.Gui.Tooltip
        public double movementSpeed = 0.34;

        @ConfigEntry.Gui.Tooltip
        public double attackDamage = 16.0;

        @ConfigEntry.Gui.Tooltip
        public double attackKnockback = 0.0;

        @ConfigEntry.Gui.Tooltip
        public List<String> lootItems = new ArrayList<>(List.of("sleepy_hollows:lootbag:3"));
    }

    public static class FleeingPumpkinHeadSettings {
        @ConfigEntry.Gui.Tooltip
        public double maxHealth = 100.0;

        @ConfigEntry.Gui.Tooltip
        public double armor = 22.0;

        @ConfigEntry.Gui.Tooltip
        public double movementSpeed = 0.43;
    }

    public static class InfectedZombieSettings {
        @ConfigEntry.Gui.Tooltip
        public double maxHealth = 30.0;

        @ConfigEntry.Gui.Tooltip
        public double armor = 1.0;

        @ConfigEntry.Gui.Tooltip
        public double movementSpeed = 0.23;

        @ConfigEntry.Gui.Tooltip
        public double attackDamage = 5.0;
    }

    public static class WeaponsSettings {
        @ConfigEntry.Gui.Tooltip
        public double spectralToolSpeed = 8.0;

        @ConfigEntry.Gui.Tooltip
        public double spectralToolDamage = 5.0;

        @ConfigEntry.Gui.Tooltip
        public double raubbauToolSpeed = 6.0;

        @ConfigEntry.Gui.Tooltip
        public double raubbauToolDamage = 3.0;

        @ConfigEntry.Gui.Tooltip
        public double splashHorsemanDamage = 20.0;
    }

    public static class ArmorSettings {
        @ConfigEntry.Gui.Tooltip
        public boolean enableSetBonus = true;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int helmetDefense = 3;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int chestplateDefense = 8;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int leggingsDefense = 6;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int bootsDefense = 3;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int durabilityMultiplier = 33;

        @ConfigEntry.Gui.Tooltip
        public double toughness = 2.0;

        @ConfigEntry.Gui.Tooltip
        public double knockbackResistance = 0.05;
    }

    public static class FoodSettings {
        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int duskBerryNutrition = 2;

        @ConfigEntry.Gui.Tooltip
        public double duskBerrySaturation = 0.1;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int candyCornNutrition = 2;

        @ConfigEntry.Gui.Tooltip
        public double candyCornSaturation = 0.1;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int pumpkinPieNutrition = 8;

        @ConfigEntry.Gui.Tooltip
        public double pumpkinPieSaturation = 0.3;

        @ConfigEntry.Gui.Tooltip
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int luminousWaterNutrition = 4;

        @ConfigEntry.Gui.Tooltip
        public double luminousWaterSaturation = 0.3;
    }

    @Override
    public void validatePostLoad() {
        horseman.maxHealth = Math.clamp(horseman.maxHealth, 1.0, 10000.0);
        horseman.armor = Math.clamp(horseman.armor, 0.0, 30.0);
        horseman.movementSpeed = Math.clamp(horseman.movementSpeed, 0.0, 1.0);
        horseman.attackDamage = Math.clamp(horseman.attackDamage, 0.0, 100.0);
        horseman.attackKnockback = Math.clamp(horseman.attackKnockback, 0.0, 5.0);
        fleeingPumpkinHead.maxHealth = Math.clamp(fleeingPumpkinHead.maxHealth, 1.0, 1000.0);
        fleeingPumpkinHead.armor = Math.clamp(fleeingPumpkinHead.armor, 0.0, 30.0);
        fleeingPumpkinHead.movementSpeed = Math.clamp(fleeingPumpkinHead.movementSpeed, 0.0, 1.0);
        infectedZombie.maxHealth = Math.clamp(infectedZombie.maxHealth, 1.0, 1000.0);
        infectedZombie.armor = Math.clamp(infectedZombie.armor, 0.0, 30.0);
        infectedZombie.movementSpeed = Math.clamp(infectedZombie.movementSpeed, 0.0, 1.0);
        infectedZombie.attackDamage = Math.clamp(infectedZombie.attackDamage, 0.0, 1000.0);
        weapons.spectralToolSpeed = Math.clamp(weapons.spectralToolSpeed, 0.0, 20.0);
        weapons.spectralToolDamage = Math.clamp(weapons.spectralToolDamage, 0.0, 20.0);
        weapons.raubbauToolSpeed = Math.clamp(weapons.raubbauToolSpeed, 0.0, 20.0);
        weapons.raubbauToolDamage = Math.clamp(weapons.raubbauToolDamage, 0.0, 20.0);
        weapons.splashHorsemanDamage = Math.clamp(weapons.splashHorsemanDamage, 0.0, 200.0);
        armor.toughness = Math.clamp(armor.toughness, 0.0, 10.0);
        armor.knockbackResistance = Math.clamp(armor.knockbackResistance, 0.0, 1.0);
        food.duskBerrySaturation = Math.clamp(food.duskBerrySaturation, 0.0, 2.0);
        food.candyCornSaturation = Math.clamp(food.candyCornSaturation, 0.0, 2.0);
        food.pumpkinPieSaturation = Math.clamp(food.pumpkinPieSaturation, 0.0, 2.0);
        food.luminousWaterSaturation = Math.clamp(food.luminousWaterSaturation, 0.0, 2.0);
        fog.endPercent = Math.max(fog.endPercent, fog.startPercent + 1);
        if (horseman.lootItems == null) horseman.lootItems = new ArrayList<>();
    }
}
