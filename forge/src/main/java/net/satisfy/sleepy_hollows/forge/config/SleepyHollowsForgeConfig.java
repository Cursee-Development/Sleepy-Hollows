package net.satisfy.sleepy_hollows.forge.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.io.File;

public class SleepyHollowsForgeConfig {
    public static ForgeConfigSpec COMMON_CONFIG;

    // Terrablender
    public static final ForgeConfigSpec.IntValue TERRABLENDER_REGION_WEIGHT;

    // Entities

    // Horseman
    public static final ForgeConfigSpec.DoubleValue HORSEMAN_MAX_HEALTH;
    public static final ForgeConfigSpec.DoubleValue HORSEMAN_ARMOR;
    public static final ForgeConfigSpec.DoubleValue HORSEMAN_MOVEMENT_SPEED;
    public static final ForgeConfigSpec.DoubleValue HORSEMAN_ATTACK_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue HORSEMAN_ATTACK_KNOCKBACK;

    // Fleeing Pumpkinhead
    public static final ForgeConfigSpec.DoubleValue FLEEING_PUMPKINHEAD_MAX_HEALTH;
    public static final ForgeConfigSpec.DoubleValue FLEEING_PUMPKINHEAD_ARMOR;
    public static final ForgeConfigSpec.DoubleValue FLEEING_PUMPKINHEAD_MOVEMENT_SPEED;

    // Infected Zombie
    public static final ForgeConfigSpec.DoubleValue INFECTED_ZOMBIE_MAX_HEALTH;
    public static final ForgeConfigSpec.DoubleValue INFECTED_ZOMBIE_ARMOR;
    public static final ForgeConfigSpec.DoubleValue INFECTED_ZOMBIE_MOVEMENT_SPEED;
    public static final ForgeConfigSpec.DoubleValue INFECTED_ZOMBIE_ATTACK_DAMAGE;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        TERRABLENDER_REGION_WEIGHT = COMMON_BUILDER
                .comment("Sleepy Hollows Terrablender Region Weight")
                .defineInRange("terrablenderRegionWeight", 2, 1, 100);

        COMMON_BUILDER.push("Horseman");

        HORSEMAN_MAX_HEALTH = COMMON_BUILDER
                .comment("Horseman Max Health")
                .defineInRange("horsemanMaxHealth", 400.0, 1.0, 10000.0);

        HORSEMAN_ARMOR = COMMON_BUILDER
                .comment("Horseman Armor")
                .defineInRange("horsemanArmor", 26.0, 0.0, 1000.0);

        HORSEMAN_MOVEMENT_SPEED = COMMON_BUILDER
                .comment("Horseman Movement Speed")
                .defineInRange("horsemanMovementSpeed", 0.34, 0.0, 1.0);

        HORSEMAN_ATTACK_DAMAGE = COMMON_BUILDER
                .comment("Horseman Attack Damage")
                .defineInRange("horsemanAttackDamage", 16.0, 0.0, 100.0);

        HORSEMAN_ATTACK_KNOCKBACK = COMMON_BUILDER
                .comment("Horseman Attack Knockback")
                .defineInRange("horsemanAttackKnockback", 0.0, 0.0, 100.0);

        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("FleeingPumpkinhead");

        FLEEING_PUMPKINHEAD_MAX_HEALTH = COMMON_BUILDER
                .comment("Fleeing Pumpkinhead Max Health")
                .defineInRange("fleeingPumpkinheadMaxHealth", 100.0, 0.0, 1000.0);

        FLEEING_PUMPKINHEAD_ARMOR = COMMON_BUILDER
                .comment("Fleeing Pumpkinhead Armor")
                .defineInRange("fleeingPumpkinheadArmor", 22.0, 0.0, 1000.0);

        FLEEING_PUMPKINHEAD_MOVEMENT_SPEED = COMMON_BUILDER
                .comment("Fleeing Pumpkinhead Movement Speed")
                .defineInRange("fleeingPumpkinheadMovementSpeed", 0.43, 0.0, 1.0);

        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("InfectedZombie");

        INFECTED_ZOMBIE_MAX_HEALTH = COMMON_BUILDER
                .comment("Infected Zombie Max Health")
                .defineInRange("infectedZombieMaxHealth", 30.0, 0.0, 1000.0);

        INFECTED_ZOMBIE_ARMOR = COMMON_BUILDER
                .comment("Infected Zombie Armor")
                .defineInRange("infectedZombieArmor", 1.0, 0.0, 1000.0);

        INFECTED_ZOMBIE_MOVEMENT_SPEED = COMMON_BUILDER
                .comment("Infected Zombie Movement Speed")
                .defineInRange("infectedZombieMovementSpeed", 0.23000000417232513, 0.0, 1.0);

        INFECTED_ZOMBIE_ATTACK_DAMAGE = COMMON_BUILDER
                .comment("Infected Zombie Attack Damage")
                .defineInRange("infectedZombieAttackDamage", 5.0, 0.0, 1000.0);

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path))
                .sync()
                .preserveInsertionOrder()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        file.load();
        config.setConfig(file);
    }
}
