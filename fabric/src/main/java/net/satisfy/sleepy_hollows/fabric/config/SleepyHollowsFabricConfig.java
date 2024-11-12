package net.satisfy.sleepy_hollows.fabric.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.Arrays;
import java.util.List;

@Config(name = "sleepy_hollows")
@Config.Gui.Background("sleepy_hollows:textures/block/gravestone.png")
public class SleepyHollowsFabricConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public BiomeSettings biome = new BiomeSettings();
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

    public static class BiomeSettings {
        @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
        public int terrablenderRegionWeight = 2;
    }

    public static class HorsemanSettings {
        @ConfigEntry.BoundedDiscrete(min = 1, max = 10000)
        public double maxHealth = 400.0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
        public double armor = 26.0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double movementSpeed = 0.34;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public double attackDamage = 16.0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public double attackKnockback = 0.0;

        @ConfigEntry.Gui.CollapsibleObject
        public LootSettings loot = new LootSettings();
    }

    @SuppressWarnings("all")
    public static class LootSettings {
        public List<String> horsemanLootItems = Arrays.asList(
                "sleepy_hollows:lootbag:3"
        );
    }

    public static class FleeingPumpkinHeadSettings {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
        public double maxHealth = 100.0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
        public double armor = 22.0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double movementSpeed = 0.43000000417232513;
    }

    public static class InfectedZombieSettings {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
        public double maxHealth = 30.0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
        public double armor = 1.0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double movementSpeed = 0.23000000417232513;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
        public double attackDamage = 5.0;
    }

    public static class WeaponsSettings {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public double spectralToolSpeed = 8.0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public double spectralToolDamage = 5.0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public double raubbauToolSpeed = 6.0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public double raubbauToolDamage = 3.0;
    }

    public static class ArmorSettings {
        @ConfigEntry.BoundedDiscrete(min = 1, max = 1000)
        public int hauntboundHelmetDurability = 13;

        @ConfigEntry.BoundedDiscrete(min = 1, max = 1000)
        public int hauntboundChestplateDurability = 15;

        @ConfigEntry.BoundedDiscrete(min = 1, max = 1000)
        public int hauntboundLeggingsDurability = 16;

        @ConfigEntry.BoundedDiscrete(min = 1, max = 1000)
        public int hauntboundBootsDurability = 11;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int hauntboundHelmetDefense = 3;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int hauntboundChestplateDefense = 6;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int hauntboundLeggingsDefense = 8;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 20)
        public int hauntboundBootsDefense = 3;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 10)
        public double hauntboundToughness = 2.0;

        @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
        public double hauntboundKnockbackResistance = 0.05;

        @ConfigEntry.Gui.CollapsibleObject
        @ConfigEntry.Gui.NoTooltip
        public boolean enableHauntboundSetBonus = true;
    }

    @ConfigEntry.Gui.CollapsibleObject
    public HUDSettings hud = new HUDSettings();

    public static class HUDSettings {
        @ConfigEntry.BoundedDiscrete(min = -1000, max = 1000)
        public int hudX = 0;

        @ConfigEntry.BoundedDiscrete(min = -1000, max = 1000)
        public int hudY = 0;
    }
}
