package net.satisfy.sleepy_hollows.forge.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class SleepyHollowsForgeConfig {
    public static ForgeConfigSpec COMMON_CONFIG;

    public static final ForgeConfigSpec.IntValue TERRABLENDER_REGION_WEIGHT;
    public static final ForgeConfigSpec.DoubleValue HORSEMAN_MAX_HEALTH;
    public static final ForgeConfigSpec.DoubleValue HORSEMAN_ARMOR;
    public static final ForgeConfigSpec.DoubleValue HORSEMAN_MOVEMENT_SPEED;
    public static final ForgeConfigSpec.DoubleValue HORSEMAN_ATTACK_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue HORSEMAN_ATTACK_KNOCKBACK;
    public static final ForgeConfigSpec.DoubleValue FLEEING_PUMPKINHEAD_MAX_HEALTH;
    public static final ForgeConfigSpec.DoubleValue FLEEING_PUMPKINHEAD_ARMOR;
    public static final ForgeConfigSpec.DoubleValue FLEEING_PUMPKINHEAD_MOVEMENT_SPEED;
    public static final ForgeConfigSpec.DoubleValue INFECTED_ZOMBIE_MAX_HEALTH;
    public static final ForgeConfigSpec.DoubleValue INFECTED_ZOMBIE_ARMOR;
    public static final ForgeConfigSpec.DoubleValue INFECTED_ZOMBIE_MOVEMENT_SPEED;
    public static final ForgeConfigSpec.DoubleValue INFECTED_ZOMBIE_ATTACK_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue SPECTRAL_TOOL_SPEED;
    public static final ForgeConfigSpec.DoubleValue SPECTRAL_TOOL_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue RAUBBAU_TOOL_SPEED;
    public static final ForgeConfigSpec.DoubleValue RAUBBAU_TOOL_DAMAGE;
    public static final ForgeConfigSpec.BooleanValue ENABLE_HAUNTBOUND_SET_BONUS;
    public static final ForgeConfigSpec.IntValue HAUNTBOUND_HELMET_DURABILITY;
    public static final ForgeConfigSpec.IntValue HAUNTBOUND_CHESTPLATE_DURABILITY;
    public static final ForgeConfigSpec.IntValue HAUNTBOUND_LEGGINGS_DURABILITY;
    public static final ForgeConfigSpec.IntValue HAUNTBOUND_BOOTS_DURABILITY;
    public static final ForgeConfigSpec.IntValue HAUNTBOUND_HELMET_DEFENSE;
    public static final ForgeConfigSpec.IntValue HAUNTBOUND_CHESTPLATE_DEFENSE;
    public static final ForgeConfigSpec.IntValue HAUNTBOUND_LEGGINGS_DEFENSE;
    public static final ForgeConfigSpec.IntValue HAUNTBOUND_BOOTS_DEFENSE;
    public static final ForgeConfigSpec.DoubleValue HAUNTBOUND_TOUGHNESS;
    public static final ForgeConfigSpec.DoubleValue HAUNTBOUND_KNOCKBACK_RESISTANCE;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> HORSEMAN_LOOT_ITEMS;
    public static final ForgeConfigSpec.IntValue HUD_X;
    public static final ForgeConfigSpec.IntValue HUD_Y;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        TERRABLENDER_REGION_WEIGHT = COMMON_BUILDER
                .comment("Sleepy Hollows Biome Weight")
                .defineInRange("terrablenderRegionWeight", 2, 1, 100);

        COMMON_BUILDER.push("Horseman Attributes");
        HORSEMAN_MAX_HEALTH = COMMON_BUILDER
                .comment("Max Health")
                .defineInRange("horsemanMaxHealth", 400.0, 1.0, 10000.0);

        HORSEMAN_ARMOR = COMMON_BUILDER
                .comment("Armor")
                .defineInRange("horsemanArmor", 26.0, 0.0, 1000.0);

        HORSEMAN_MOVEMENT_SPEED = COMMON_BUILDER
                .comment("Movement Speed")
                .defineInRange("horsemanMovementSpeed", 0.34, 0.0, 1.0);

        HORSEMAN_ATTACK_DAMAGE = COMMON_BUILDER
                .comment("Attack Damage")
                .defineInRange("horsemanAttackDamage", 16.0, 0.0, 100.0);

        HORSEMAN_ATTACK_KNOCKBACK = COMMON_BUILDER
                .comment("Attack Knockback")
                .defineInRange("horsemanAttackKnockback", 0.0, 0.0, 100.0);

        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("Fleeing Pumpkin Head Attributes");

        FLEEING_PUMPKINHEAD_MAX_HEALTH = COMMON_BUILDER
                .comment("Max Health")
                .defineInRange("fleeingPumpkinheadMaxHealth", 100.0, 0.0, 1000.0);

        FLEEING_PUMPKINHEAD_ARMOR = COMMON_BUILDER
                .comment("Armor")
                .defineInRange("fleeingPumpkinheadArmor", 22.0, 0.0, 1000.0);

        FLEEING_PUMPKINHEAD_MOVEMENT_SPEED = COMMON_BUILDER
                .comment("Movement Speed")
                .defineInRange("fleeingPumpkinheadMovementSpeed", 0.43, 0.0, 1.0);

        COMMON_BUILDER.pop();
        COMMON_BUILDER.push("InfectedZombie Attributes");

        INFECTED_ZOMBIE_MAX_HEALTH = COMMON_BUILDER
                .comment("Max Health")
                .defineInRange("infectedZombieMaxHealth", 30.0, 0.0, 1000.0);

        INFECTED_ZOMBIE_ARMOR = COMMON_BUILDER
                .comment("Armor")
                .defineInRange("infectedZombieArmor", 1.0, 0.0, 1000.0);

        INFECTED_ZOMBIE_MOVEMENT_SPEED = COMMON_BUILDER
                .comment("Movement Speed")
                .defineInRange("infectedZombieMovementSpeed", 0.23000000417232513, 0.0, 1.0);

        INFECTED_ZOMBIE_ATTACK_DAMAGE = COMMON_BUILDER
                .comment("Attack Damage")

                .defineInRange("infectedZombieAttackDamage", 5.0, 0.0, 1000.0);

        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("Tool Attributes");

        SPECTRAL_TOOL_SPEED = COMMON_BUILDER
                .comment("Spectral Weapons Speed")
                .defineInRange("spectralToolSpeed", 8.0, 0.0, 20.0);

        SPECTRAL_TOOL_DAMAGE = COMMON_BUILDER
                .comment("Spectral Weapons Damage")
                .defineInRange("spectralToolDamage", 5.0, 0.0, 20.0);

        RAUBBAU_TOOL_SPEED = COMMON_BUILDER
                .comment("Raubbau Speed")
                .defineInRange("raubbauToolSpeed", 6.0, 0.0, 20.0);

        RAUBBAU_TOOL_DAMAGE = COMMON_BUILDER
                .comment("Raubbau Damage")
                .defineInRange("raubbauToolDamage", 3.0, 0.0, 20.0);

        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("Armor Attributes");

        ENABLE_HAUNTBOUND_SET_BONUS = COMMON_BUILDER
                .comment("Enable or disable Hauntbound Armor set bonus")
                .define("enableHauntboundSetBonus", true);

        HAUNTBOUND_HELMET_DURABILITY = COMMON_BUILDER
                .comment("Hauntbound Helmet Durability")
                .defineInRange("hauntboundHelmetDurability", 13, 1, 1000);

        HAUNTBOUND_CHESTPLATE_DURABILITY = COMMON_BUILDER
                .comment("Hauntbound Chestplate Durability")
                .defineInRange("hauntboundChestplateDurability", 15, 1, 1000);

        HAUNTBOUND_LEGGINGS_DURABILITY = COMMON_BUILDER
                .comment("Hauntbound Leggings Durability")
                .defineInRange("hauntboundLeggingsDurability", 16, 1, 1000);

        HAUNTBOUND_BOOTS_DURABILITY = COMMON_BUILDER
                .comment("Hauntbound Boots Durability")
                .defineInRange("hauntboundBootsDurability", 11, 1, 1000);

        HAUNTBOUND_HELMET_DEFENSE = COMMON_BUILDER
                .comment("Hauntbound Helmet Defense")
                .defineInRange("hauntboundHelmetDefense", 3, 0, 20);

        HAUNTBOUND_CHESTPLATE_DEFENSE = COMMON_BUILDER
                .comment("Hauntbound Chestplate Defense")
                .defineInRange("hauntboundChestplateDefense", 6, 0, 20);

        HAUNTBOUND_LEGGINGS_DEFENSE = COMMON_BUILDER
                .comment("Hauntbound Leggings Defense")
                .defineInRange("hauntboundLeggingsDefense", 8, 0, 20);

        HAUNTBOUND_BOOTS_DEFENSE = COMMON_BUILDER
                .comment("Hauntbound Boots Defense")
                .defineInRange("hauntboundBootsDefense", 3, 0, 20);

        HAUNTBOUND_TOUGHNESS = COMMON_BUILDER
                .comment("Hauntbound Armor Toughness")
                .defineInRange("hauntboundToughness", 2.0, 0.0, 10.0);

        HAUNTBOUND_KNOCKBACK_RESISTANCE = COMMON_BUILDER
                .comment("Hauntbound Armor Knockback Resistance")
                .defineInRange("hauntboundKnockbackResistance", 0.05, 0.0, 1.0);

        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("Horseman Loot");

        HORSEMAN_LOOT_ITEMS = COMMON_BUILDER
                .comment("List of items to drop on Horseman death in the format 'modid:itemid:count'")
                .defineList("horsemanLootItems", Arrays.asList(
                        "sleepy_hollows:lootbag:3"
                ), obj -> obj instanceof String);

        COMMON_BUILDER.pop();

        COMMON_BUILDER.push("HUD Settings");

        HUD_X = COMMON_BUILDER
                .comment("HUD X Position")
                .defineInRange("hudX", 0, -1000, 1000);

        HUD_Y = COMMON_BUILDER
                .comment("HUD Y Position")
                .defineInRange("hudY", 0, -1000, 1000);

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
