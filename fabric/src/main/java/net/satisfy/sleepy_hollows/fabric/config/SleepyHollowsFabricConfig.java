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
    public FoodSettings food = new FoodSettings();
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

    public static class SanitySettings {
        public boolean enabled = true;
        public int hudX = 0;
        public int hudY = 0;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int candyCornGain = 4;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int duskBerryGain = 2;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int luminousWaterGain = 6;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int pumpkinPieGain = 10;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int outsideBiomeGain = 5;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int biomeDayLoss = 1;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int biomeNightLoss = 2;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int cursedBlockLoss = 2;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int infectedLoss = 2;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 16)
        public int lightRadius = 4;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int hauntboundPieceProtectionPercent = 25;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 600)
        public int insanitySeconds = 60;
    }

    public static class FogSettings {
        public boolean enabled = true;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int startPercent = 5;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int endPercent = 45;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 30)
        public int transitionSeconds = 6;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int sanityInfluencePercent = 40;
    }

    public static class AmbienceSettings {
        public boolean enabled = true;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 600)
        public int dayMinSeconds = 20;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 600)
        public int dayMaxSeconds = 45;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 600)
        public int nightMinSeconds = 8;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 600)
        public int nightMaxSeconds = 20;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int volumePercent = 100;
    }

    public static class FoodSettings {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int duskBerryNutrition = 2;
        public double duskBerrySaturation = 0.1;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int candyCornNutrition = 2;
        public double candyCornSaturation = 0.1;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int pumpkinPieNutrition = 8;
        public double pumpkinPieSaturation = 0.3;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int luminousWaterNutrition = 4;
        public double luminousWaterSaturation = 0.3;
    }

    public static class HorsemanSettings {
        public double maxHealth = 180.0;
        public double armor = 26.0;
        public double movementSpeed = 0.34;
        public double attackDamage = 16.0;
        public double attackKnockback = 0.0;
        public List<String> lootItems = new ArrayList<>(List.of("sleepy_hollows:lootbag:3"));
    }

    public static class FleeingPumpkinHeadSettings {
        public double maxHealth = 100.0;
        public double armor = 22.0;
        public double movementSpeed = 0.43;
    }

    public static class InfectedZombieSettings {
        public double maxHealth = 30.0;
        public double armor = 1.0;
        public double movementSpeed = 0.23;
        public double attackDamage = 5.0;
    }

    public static class WeaponsSettings {
        public double spectralToolSpeed = 8.0;
        public double spectralToolDamage = 5.0;
        public double raubbauToolSpeed = 6.0;
        public double raubbauToolDamage = 3.0;
        public double splashHorsemanDamage = 20.0;
    }

    public static class ArmorSettings {
        public boolean enableSetBonus = true;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int helmetDefense = 3;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int chestplateDefense = 8;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int leggingsDefense = 6;
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int bootsDefense = 3;
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int durabilityMultiplier = 33;
        public double toughness = 2.0;
        public double knockbackResistance = 0.05;
    }

    @Override
    public void validatePostLoad() {
        horseman.maxHealth = Math.max(1, horseman.maxHealth);
        fleeingPumpkinHead.maxHealth = Math.max(1, fleeingPumpkinHead.maxHealth);
        infectedZombie.maxHealth = Math.max(1, infectedZombie.maxHealth);
        fog.endPercent = Math.max(fog.endPercent, fog.startPercent + 1);
        armor.knockbackResistance = Math.clamp(armor.knockbackResistance, 0.0, 1.0);
        if (horseman.lootItems == null) horseman.lootItems = new ArrayList<>();
    }
}
