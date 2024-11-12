package net.satisfy.sleepy_hollows.fabric.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "sleepy_hollows")
@Config.Gui.Background("sleepy_hollows:textures/block/gravestone.png")
public class SleepyHollowsFabricConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int terrablenderRegionWeight = 2;

    @ConfigEntry.Gui.CollapsibleObject
    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 1, max = 10000)
    public double horsemanmaxHealth = 400.0;

    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
    public double horsemanArmor = 26.0;

    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
    public double horsemanmovementSpeed = 0.34;

    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public double horsemanattackDamage = 16.0;

    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    public double horsemanattackKnockback = 0.0;

    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
    public double fleeingpumpkinheadmaxHealth = 100.0;

    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
    public double fleeingpumpkinArmor = 22.0;

    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
    public double fleeingpumpkinheadmovementSpeed = 0.43000000417232513;

    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
    public double infectedzombiemaxHealth = 30.0;

    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
    public double infectedzombieArmor = 1.0;

    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1)
    public double infectedzombiemovementSpeed = 0.23000000417232513;

    @ConfigEntry.Gui.NoTooltip
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
    public double infectedzombieattackDamage = 5.0;
}
