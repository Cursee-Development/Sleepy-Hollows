package net.satisfy.sleepy_hollows.fabric.platform;

import me.shedaniel.autoconfig.AutoConfig;
import net.satisfy.sleepy_hollows.core.platform.PlatformHelper;
import net.satisfy.sleepy_hollows.fabric.config.SleepyHollowsFabricConfig;

@SuppressWarnings("unused")
public class PlatformHelperImpl extends PlatformHelper {
    public static int getTerrablenderRegionWeight() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.terrablenderRegionWeight;
    }

    public static double getHorsemanMovementSpeed() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.horsemanmovementSpeed;
    }

    public static double getHorsemanMaxHealth() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.horsemanmaxHealth;
    }

    public static double getHorsemanAttackDamage() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.horsemanattackDamage;
    }

    public static double getHorsemanAttackKnockback() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.horsemanattackKnockback;
    }

    public static double getHorsemanArmor() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.horsemanArmor;
    }

    public static double getFleeingPumpkinMaxHealth() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.fleeingpumpkinheadmaxHealth;
    }

    public static double getFleeingPumpkinMovementSpeed() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.fleeingpumpkinheadmovementSpeed;
    }

    public static double getFleeingPumpkinArmor() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.fleeingpumpkinArmor;
    }

    public static double getInfectedZombieMaxHealth() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.infectedzombiemaxHealth;
    }

    public static double getInfectedZombieArmor() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.infectedzombieArmor;
    }

    public static double getInfectedZombieMovementSpeed() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.infectedzombiemovementSpeed;
    }

    public static double getInfectedZombieAttackDamage() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.infectedzombieattackDamage;
    }
}
