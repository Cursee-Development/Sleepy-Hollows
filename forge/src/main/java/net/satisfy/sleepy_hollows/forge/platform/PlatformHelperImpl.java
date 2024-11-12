package net.satisfy.sleepy_hollows.forge.platform;

import net.satisfy.sleepy_hollows.core.platform.PlatformHelper;
import net.satisfy.sleepy_hollows.forge.config.SleepyHollowsForgeConfig;

@SuppressWarnings("unused")
public class PlatformHelperImpl extends PlatformHelper {
    public static int getTerrablenderRegionWeight() {
        return SleepyHollowsForgeConfig.TERRABLENDER_REGION_WEIGHT.get();
    }

    public static double getHorsemanMovementSpeed() {
        return SleepyHollowsForgeConfig.HORSEMAN_MOVEMENT_SPEED.get();
    }

    public static double getHorsemanMaxHealth() {
        return SleepyHollowsForgeConfig.HORSEMAN_MAX_HEALTH.get();
    }

    public static double getHorsemanAttackDamage() {
        return SleepyHollowsForgeConfig.HORSEMAN_ATTACK_DAMAGE.get();
    }

    public static double getHorsemanAttackKnockback() {
        return SleepyHollowsForgeConfig.HORSEMAN_ATTACK_KNOCKBACK.get();
    }

    public static double getHorsemanArmor() {
        return SleepyHollowsForgeConfig.HORSEMAN_ARMOR.get();
    }

    public static double getFleeingPumpkinMaxHealth() {
        return SleepyHollowsForgeConfig.FLEEING_PUMPKINHEAD_MAX_HEALTH.get();
    }

    public static double getFleeingPumpkinMovementSpeed() {
        return SleepyHollowsForgeConfig.FLEEING_PUMPKINHEAD_MOVEMENT_SPEED.get();
    }

    public static double getFleeingPumpkinArmor() {
        return SleepyHollowsForgeConfig.FLEEING_PUMPKINHEAD_ARMOR.get();
    }

    public static double getInfectedZombieMaxHealth() {
        return SleepyHollowsForgeConfig.INFECTED_ZOMBIE_MAX_HEALTH.get();
    }

    public static double getInfectedZombieArmor() {
        return SleepyHollowsForgeConfig.INFECTED_ZOMBIE_ARMOR.get();
    }

    public static double getInfectedZombieMovementSpeed() {
        return SleepyHollowsForgeConfig.INFECTED_ZOMBIE_MOVEMENT_SPEED.get();
    }

    public static double getInfectedZombieAttackDamage() {
        return SleepyHollowsForgeConfig.INFECTED_ZOMBIE_ATTACK_DAMAGE.get();
    }
}
