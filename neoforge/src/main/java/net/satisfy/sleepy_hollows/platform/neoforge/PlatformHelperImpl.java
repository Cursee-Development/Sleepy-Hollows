package net.satisfy.sleepy_hollows.platform.neoforge;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.satisfy.sleepy_hollows.neoforge.config.SleepyHollowsNeoForgeConfig;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class PlatformHelperImpl extends PlatformHelper {
    public static double getHorsemanMovementSpeed() {
        return SleepyHollowsNeoForgeConfig.horsemanMovementSpeed;
    }

    public static double getHorsemanMaxHealth() {
        return SleepyHollowsNeoForgeConfig.horsemanMaxHealth;
    }

    public static double getHorsemanAttackDamage() {
        return SleepyHollowsNeoForgeConfig.horsemanAttackDamage;
    }

    public static double getHorsemanAttackKnockback() {
        return SleepyHollowsNeoForgeConfig.horsemanAttackKnockback;
    }

    public static double getHorsemanArmor() {
        return SleepyHollowsNeoForgeConfig.horsemanArmor;
    }

    public static double getFleeingPumpkinMaxHealth() {
        return SleepyHollowsNeoForgeConfig.fleeingPumpkinheadMaxHealth;
    }

    public static double getFleeingPumpkinMovementSpeed() {
        return SleepyHollowsNeoForgeConfig.fleeingPumpkinheadMovementSpeed;
    }

    public static double getFleeingPumpkinArmor() {
        return SleepyHollowsNeoForgeConfig.fleeingPumpkinheadArmor;
    }

    public static double getInfectedZombieMaxHealth() {
        return SleepyHollowsNeoForgeConfig.infectedZombieMaxHealth;
    }

    public static double getInfectedZombieArmor() {
        return SleepyHollowsNeoForgeConfig.infectedZombieArmor;
    }

    public static double getInfectedZombieMovementSpeed() {
        return SleepyHollowsNeoForgeConfig.infectedZombieMovementSpeed;
    }

    public static double getInfectedZombieAttackDamage() {
        return SleepyHollowsNeoForgeConfig.infectedZombieAttackDamage;
    }

    public static double getSpectralToolSpeed() {
        return SleepyHollowsNeoForgeConfig.spectralToolSpeed;
    }

    public static double getSpectralToolDamage() {
        return SleepyHollowsNeoForgeConfig.spectralToolDamage;
    }

    public static double getRaubbauToolSpeed() {
        return SleepyHollowsNeoForgeConfig.raubbauToolSpeed;
    }

    public static double getRaubbauToolDamage() {
        return SleepyHollowsNeoForgeConfig.raubbauToolDamage;
    }

    public static boolean isHauntboundSetBonusEnabled() {
        return SleepyHollowsNeoForgeConfig.enableHauntboundSetBonus;
    }

    public static int getHauntboundDurability(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> SleepyHollowsNeoForgeConfig.hauntboundHelmetDurability;
            case CHESTPLATE -> SleepyHollowsNeoForgeConfig.hauntboundChestplateDurability;
            case LEGGINGS -> SleepyHollowsNeoForgeConfig.hauntboundLeggingsDurability;
            case BOOTS -> SleepyHollowsNeoForgeConfig.hauntboundBootsDurability;
            default -> 0;
        };
    }

    public static int getHauntboundDefense(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> SleepyHollowsNeoForgeConfig.hauntboundHelmetDefense;
            case CHESTPLATE -> SleepyHollowsNeoForgeConfig.hauntboundChestplateDefense;
            case LEGGINGS -> SleepyHollowsNeoForgeConfig.hauntboundLeggingsDefense;
            case BOOTS -> SleepyHollowsNeoForgeConfig.hauntboundBootsDefense;
            default -> 0;
        };
    }

    public static double getHauntboundToughness() {
        return SleepyHollowsNeoForgeConfig.hauntboundToughness;
    }

    public static double getHauntboundKnockbackResistance() {
        return SleepyHollowsNeoForgeConfig.hauntboundKnockbackResistance;
    }

    public static List<ItemStack> getHorsemanLootItems() {
        List<ItemStack> loot = new ArrayList<>();
        for (String entry : SleepyHollowsNeoForgeConfig.horsemanLootItems) {
            String[] parts = entry.split(":");
            if (parts.length == 3) {
                String namespace = parts[0];
                String path = parts[1];
                int count;
                try {
                    count = Integer.parseInt(parts[2]);
                } catch (NumberFormatException ignored) {
                    count = 1;
                }
                ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace, path);
                Item item = BuiltInRegistries.ITEM.getOptional(id).orElse(null);
                if (item != null) {
                    loot.add(new ItemStack(item, count));
                }
            }
        }
        return loot;
    }
}