package net.satisfy.sleepy_hollows.neoforge.config;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class SleepyHollowsNeoForgeConfig {
    public static final ModConfigSpec COMMON_CONFIG;
    public static final ModConfigSpec.DoubleValue HORSEMAN_MAX_HEALTH;
    public static final ModConfigSpec.DoubleValue HORSEMAN_ARMOR;
    public static final ModConfigSpec.DoubleValue HORSEMAN_MOVEMENT_SPEED;
    public static final ModConfigSpec.DoubleValue HORSEMAN_ATTACK_DAMAGE;
    public static final ModConfigSpec.DoubleValue HORSEMAN_ATTACK_KNOCKBACK;
    public static final ModConfigSpec.DoubleValue FLEEING_PUMPKINHEAD_MAX_HEALTH;
    public static final ModConfigSpec.DoubleValue FLEEING_PUMPKINHEAD_ARMOR;
    public static final ModConfigSpec.DoubleValue FLEEING_PUMPKINHEAD_MOVEMENT_SPEED;
    public static final ModConfigSpec.DoubleValue INFECTED_ZOMBIE_MAX_HEALTH;
    public static final ModConfigSpec.DoubleValue INFECTED_ZOMBIE_ARMOR;
    public static final ModConfigSpec.DoubleValue INFECTED_ZOMBIE_MOVEMENT_SPEED;
    public static final ModConfigSpec.DoubleValue INFECTED_ZOMBIE_ATTACK_DAMAGE;
    public static final ModConfigSpec.DoubleValue SPECTRAL_TOOL_SPEED;
    public static final ModConfigSpec.DoubleValue SPECTRAL_TOOL_DAMAGE;
    public static final ModConfigSpec.DoubleValue RAUBBAU_TOOL_SPEED;
    public static final ModConfigSpec.DoubleValue RAUBBAU_TOOL_DAMAGE;
    public static final ModConfigSpec.BooleanValue ENABLE_HAUNTBOUND_SET_BONUS;
    public static final ModConfigSpec.IntValue HAUNTBOUND_HELMET_DURABILITY;
    public static final ModConfigSpec.IntValue HAUNTBOUND_CHESTPLATE_DURABILITY;
    public static final ModConfigSpec.IntValue HAUNTBOUND_LEGGINGS_DURABILITY;
    public static final ModConfigSpec.IntValue HAUNTBOUND_BOOTS_DURABILITY;
    public static final ModConfigSpec.IntValue HAUNTBOUND_HELMET_DEFENSE;
    public static final ModConfigSpec.IntValue HAUNTBOUND_CHESTPLATE_DEFENSE;
    public static final ModConfigSpec.IntValue HAUNTBOUND_LEGGINGS_DEFENSE;
    public static final ModConfigSpec.IntValue HAUNTBOUND_BOOTS_DEFENSE;
    public static final ModConfigSpec.DoubleValue HAUNTBOUND_TOUGHNESS;
    public static final ModConfigSpec.DoubleValue HAUNTBOUND_KNOCKBACK_RESISTANCE;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> HORSEMAN_LOOT_ITEMS;

    public static double horsemanMaxHealth = 400.0;
    public static double horsemanArmor = 26.0;
    public static double horsemanMovementSpeed = 0.34;
    public static double horsemanAttackDamage = 16.0;
    public static double horsemanAttackKnockback = 0.0;
    public static double fleeingPumpkinheadMaxHealth = 100.0;
    public static double fleeingPumpkinheadArmor = 22.0;
    public static double fleeingPumpkinheadMovementSpeed = 0.43;
    public static double infectedZombieMaxHealth = 30.0;
    public static double infectedZombieArmor = 1.0;
    public static double infectedZombieMovementSpeed = 0.23000000417232513;
    public static double infectedZombieAttackDamage = 5.0;
    public static double spectralToolSpeed = 8.0;
    public static double spectralToolDamage = 5.0;
    public static double raubbauToolSpeed = 6.0;
    public static double raubbauToolDamage = 3.0;
    public static boolean enableHauntboundSetBonus = true;
    public static int hauntboundHelmetDurability = 13;
    public static int hauntboundChestplateDurability = 15;
    public static int hauntboundLeggingsDurability = 16;
    public static int hauntboundBootsDurability = 11;
    public static int hauntboundHelmetDefense = 3;
    public static int hauntboundChestplateDefense = 6;
    public static int hauntboundLeggingsDefense = 8;
    public static int hauntboundBootsDefense = 3;
    public static double hauntboundToughness = 2.0;
    public static double hauntboundKnockbackResistance = 0.05;
    public static List<? extends String> horsemanLootItems = Arrays.asList("sleepy_hollows:lootbag:3");

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("Horseman Attributes");
        HORSEMAN_MAX_HEALTH = builder.defineInRange("horsemanMaxHealth", 200.0, 1.0, 10000.0);
        HORSEMAN_ARMOR = builder.defineInRange("horsemanArmor", 26.0, 0.0, 1000.0);
        HORSEMAN_MOVEMENT_SPEED = builder.defineInRange("horsemanMovementSpeed", 0.34, 0.0, 1.0);
        HORSEMAN_ATTACK_DAMAGE = builder.defineInRange("horsemanAttackDamage", 16.0, 0.0, 100.0);
        HORSEMAN_ATTACK_KNOCKBACK = builder.defineInRange("horsemanAttackKnockback", 0.0, 0.0, 100.0);
        builder.pop();

        builder.push("Fleeing Pumpkin Head Attributes");
        FLEEING_PUMPKINHEAD_MAX_HEALTH = builder.defineInRange("fleeingPumpkinheadMaxHealth", 100.0, 0.0, 1000.0);
        FLEEING_PUMPKINHEAD_ARMOR = builder.defineInRange("fleeingPumpkinheadArmor", 22.0, 0.0, 1000.0);
        FLEEING_PUMPKINHEAD_MOVEMENT_SPEED = builder.defineInRange("fleeingPumpkinheadMovementSpeed", 0.43, 0.0, 1.0);
        builder.pop();

        builder.push("InfectedZombie Attributes");
        INFECTED_ZOMBIE_MAX_HEALTH = builder.defineInRange("infectedZombieMaxHealth", 30.0, 0.0, 1000.0);
        INFECTED_ZOMBIE_ARMOR = builder.defineInRange("infectedZombieArmor", 1.0, 0.0, 1000.0);
        INFECTED_ZOMBIE_MOVEMENT_SPEED = builder.defineInRange("infectedZombieMovementSpeed", 0.23000000417232513, 0.0, 1.0);
        INFECTED_ZOMBIE_ATTACK_DAMAGE = builder.defineInRange("infectedZombieAttackDamage", 5.0, 0.0, 1000.0);
        builder.pop();

        builder.push("Tool Attributes");
        SPECTRAL_TOOL_SPEED = builder.defineInRange("spectralToolSpeed", 8.0, 0.0, 20.0);
        SPECTRAL_TOOL_DAMAGE = builder.defineInRange("spectralToolDamage", 5.0, 0.0, 20.0);
        RAUBBAU_TOOL_SPEED = builder.defineInRange("raubbauToolSpeed", 6.0, 0.0, 20.0);
        RAUBBAU_TOOL_DAMAGE = builder.defineInRange("raubbauToolDamage", 3.0, 0.0, 20.0);
        builder.pop();

        builder.push("Armor Attributes");
        ENABLE_HAUNTBOUND_SET_BONUS = builder.define("enableHauntboundSetBonus", true);
        HAUNTBOUND_HELMET_DURABILITY = builder.defineInRange("hauntboundHelmetDurability", 13, 1, 1000);
        HAUNTBOUND_CHESTPLATE_DURABILITY = builder.defineInRange("hauntboundChestplateDurability", 15, 1, 1000);
        HAUNTBOUND_LEGGINGS_DURABILITY = builder.defineInRange("hauntboundLeggingsDurability", 16, 1, 1000);
        HAUNTBOUND_BOOTS_DURABILITY = builder.defineInRange("hauntboundBootsDurability", 11, 1, 1000);
        HAUNTBOUND_HELMET_DEFENSE = builder.defineInRange("hauntboundHelmetDefense", 3, 0, 20);
        HAUNTBOUND_CHESTPLATE_DEFENSE = builder.defineInRange("hauntboundChestplateDefense", 6, 0, 20);
        HAUNTBOUND_LEGGINGS_DEFENSE = builder.defineInRange("hauntboundLeggingsDefense", 8, 0, 20);
        HAUNTBOUND_BOOTS_DEFENSE = builder.defineInRange("hauntboundBootsDefense", 3, 0, 20);
        HAUNTBOUND_TOUGHNESS = builder.defineInRange("hauntboundToughness", 2.0, 0.0, 10.0);
        HAUNTBOUND_KNOCKBACK_RESISTANCE = builder.defineInRange("hauntboundKnockbackResistance", 0.05, 0.0, 1.0);
        builder.pop();

        builder.push("Horseman Loot");
        HORSEMAN_LOOT_ITEMS = builder.defineList("horsemanLootItems", Arrays.asList("sleepy_hollows:lootbag:3"), obj -> obj instanceof String);
        builder.pop();

        COMMON_CONFIG = builder.build();
    }

    public static void onLoad(ModConfigEvent.Loading event) {
        sync();
    }

    public static void onReload(ModConfigEvent.Reloading event) {
        sync();
    }

    public static void sync() {
        horsemanMaxHealth = HORSEMAN_MAX_HEALTH.get();
        horsemanArmor = HORSEMAN_ARMOR.get();
        horsemanMovementSpeed = HORSEMAN_MOVEMENT_SPEED.get();
        horsemanAttackDamage = HORSEMAN_ATTACK_DAMAGE.get();
        horsemanAttackKnockback = HORSEMAN_ATTACK_KNOCKBACK.get();
        fleeingPumpkinheadMaxHealth = FLEEING_PUMPKINHEAD_MAX_HEALTH.get();
        fleeingPumpkinheadArmor = FLEEING_PUMPKINHEAD_ARMOR.get();
        fleeingPumpkinheadMovementSpeed = FLEEING_PUMPKINHEAD_MOVEMENT_SPEED.get();
        infectedZombieMaxHealth = INFECTED_ZOMBIE_MAX_HEALTH.get();
        infectedZombieArmor = INFECTED_ZOMBIE_ARMOR.get();
        infectedZombieMovementSpeed = INFECTED_ZOMBIE_MOVEMENT_SPEED.get();
        infectedZombieAttackDamage = INFECTED_ZOMBIE_ATTACK_DAMAGE.get();
        spectralToolSpeed = SPECTRAL_TOOL_SPEED.get();
        spectralToolDamage = SPECTRAL_TOOL_DAMAGE.get();
        raubbauToolSpeed = RAUBBAU_TOOL_SPEED.get();
        raubbauToolDamage = RAUBBAU_TOOL_DAMAGE.get();
        enableHauntboundSetBonus = ENABLE_HAUNTBOUND_SET_BONUS.get();
        hauntboundHelmetDurability = HAUNTBOUND_HELMET_DURABILITY.get();
        hauntboundChestplateDurability = HAUNTBOUND_CHESTPLATE_DURABILITY.get();
        hauntboundLeggingsDurability = HAUNTBOUND_LEGGINGS_DURABILITY.get();
        hauntboundBootsDurability = HAUNTBOUND_BOOTS_DURABILITY.get();
        hauntboundHelmetDefense = HAUNTBOUND_HELMET_DEFENSE.get();
        hauntboundChestplateDefense = HAUNTBOUND_CHESTPLATE_DEFENSE.get();
        hauntboundLeggingsDefense = HAUNTBOUND_LEGGINGS_DEFENSE.get();
        hauntboundBootsDefense = HAUNTBOUND_BOOTS_DEFENSE.get();
        hauntboundToughness = HAUNTBOUND_TOUGHNESS.get();
        hauntboundKnockbackResistance = HAUNTBOUND_KNOCKBACK_RESISTANCE.get();
        horsemanLootItems = HORSEMAN_LOOT_ITEMS.get();
    }
}
