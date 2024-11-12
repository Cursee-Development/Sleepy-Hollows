package net.satisfy.sleepy_hollows.platform.fabric;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.satisfy.sleepy_hollows.fabric.config.SleepyHollowsFabricConfig;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;

import java.util.ArrayList;
import java.util.List;

public class PlatformHelperImpl extends PlatformHelper {
    public static int getTerrablenderRegionWeight() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.biome.terrablenderRegionWeight;
    }

    public static double getHorsemanMovementSpeed() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.horseman.movementSpeed;
    }

    public static double getHorsemanMaxHealth() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.horseman.maxHealth;
    }

    public static double getHorsemanAttackDamage() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.horseman.attackDamage;
    }

    public static double getHorsemanAttackKnockback() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.horseman.attackKnockback;
    }

    public static double getHorsemanArmor() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.horseman.armor;
    }

    public static double getFleeingPumpkinMaxHealth() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.fleeingPumpkinHead.maxHealth;
    }

    public static double getFleeingPumpkinMovementSpeed() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.fleeingPumpkinHead.movementSpeed;
    }

    public static double getFleeingPumpkinArmor() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.fleeingPumpkinHead.armor;
    }

    public static double getInfectedZombieMaxHealth() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.infectedZombie.maxHealth;
    }

    public static double getInfectedZombieArmor() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.infectedZombie.armor;
    }

    public static double getInfectedZombieMovementSpeed() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.infectedZombie.movementSpeed;
    }

    public static double getInfectedZombieAttackDamage() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.infectedZombie.attackDamage;
    }

    public static double getSpectralToolSpeed() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.weapons.spectralToolSpeed;
    }

    public static double getSpectralToolDamage() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.weapons.spectralToolDamage;
    }

    public static double getRaubbauToolSpeed() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.weapons.raubbauToolSpeed;
    }

    public static double getRaubbauToolDamage() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.weapons.raubbauToolDamage;
    }

    public static boolean isHauntboundSetBonusEnabled() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.armor.enableHauntboundSetBonus;
    }

    public static int getHauntboundDurability(ArmorItem.Type type) {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return switch (type) {
            case HELMET -> config.armor.hauntboundHelmetDurability;
            case CHESTPLATE -> config.armor.hauntboundChestplateDurability;
            case LEGGINGS -> config.armor.hauntboundLeggingsDurability;
            case BOOTS -> config.armor.hauntboundBootsDurability;
        };
    }

    public static int getHauntboundDefense(ArmorItem.Type type) {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return switch (type) {
            case HELMET -> config.armor.hauntboundHelmetDefense;
            case CHESTPLATE -> config.armor.hauntboundChestplateDefense;
            case LEGGINGS -> config.armor.hauntboundLeggingsDefense;
            case BOOTS -> config.armor.hauntboundBootsDefense;
        };
    }

    public static double getHauntboundToughness() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.armor.hauntboundToughness;
    }

    public static double getHauntboundKnockbackResistance() {
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();
        return config.armor.hauntboundKnockbackResistance;
    }

    public static List<ItemStack> getHorsemanLootItems() {
        assert Minecraft.getInstance().level != null;
        RegistryAccess registryAccess = Minecraft.getInstance().level.registryAccess();
        return getHorsemanLootItems(registryAccess);
    }

    private static List<ItemStack> getHorsemanLootItems(RegistryAccess registryAccess) {
        List<ItemStack> loot = new ArrayList<>();
        SleepyHollowsFabricConfig config = AutoConfig.getConfigHolder(SleepyHollowsFabricConfig.class).getConfig();

        for (String lootString : config.horseman.loot.horsemanLootItems) {
            String[] parts = lootString.split(":");
            if (parts.length == 3) {
                String modId = parts[0];
                String itemId = parts[1];
                int count;
                try {
                    count = Integer.parseInt(parts[2]);
                } catch (NumberFormatException e) {
                    count = 1;
                }
                ResourceLocation rl = new ResourceLocation(modId, itemId);
                Item item = registryAccess.registryOrThrow(Registries.ITEM).get(rl);
                if (item != null) {
                    loot.add(new ItemStack(item, count));
                } else {
                    System.err.println("Horseman Loot Item not found: " + rl);
                }
            }
        }
        return loot;
    }
}
