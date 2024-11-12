package net.satisfy.sleepy_hollows.platform.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import net.satisfy.sleepy_hollows.forge.config.SleepyHollowsForgeConfig;

import java.util.ArrayList;
import java.util.List;

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

    public static double getSpectralToolSpeed() {
        return SleepyHollowsForgeConfig.SPECTRAL_TOOL_SPEED.get();
    }

    public static double getSpectralToolDamage() {
        return SleepyHollowsForgeConfig.SPECTRAL_TOOL_DAMAGE.get();
    }

    public static double getRaubbauToolSpeed() {
        return SleepyHollowsForgeConfig.RAUBBAU_TOOL_SPEED.get();
    }

    public static double getRaubbauToolDamage() {
        return SleepyHollowsForgeConfig.RAUBBAU_TOOL_DAMAGE.get();
    }

    public static boolean isHauntboundSetBonusEnabled() {
        return SleepyHollowsForgeConfig.ENABLE_HAUNTBOUND_SET_BONUS.get();
    }

    public static int getHauntboundDurability(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> SleepyHollowsForgeConfig.HAUNTBOUND_HELMET_DURABILITY.get();
            case CHESTPLATE -> SleepyHollowsForgeConfig.HAUNTBOUND_CHESTPLATE_DURABILITY.get();
            case LEGGINGS -> SleepyHollowsForgeConfig.HAUNTBOUND_LEGGINGS_DURABILITY.get();
            case BOOTS -> SleepyHollowsForgeConfig.HAUNTBOUND_BOOTS_DURABILITY.get();
        };
    }

    public static int getHauntboundDefense(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> SleepyHollowsForgeConfig.HAUNTBOUND_HELMET_DEFENSE.get();
            case CHESTPLATE -> SleepyHollowsForgeConfig.HAUNTBOUND_CHESTPLATE_DEFENSE.get();
            case LEGGINGS -> SleepyHollowsForgeConfig.HAUNTBOUND_LEGGINGS_DEFENSE.get();
            case BOOTS -> SleepyHollowsForgeConfig.HAUNTBOUND_BOOTS_DEFENSE.get();
        };
    }

    public static double getHauntboundToughness() {
        return SleepyHollowsForgeConfig.HAUNTBOUND_TOUGHNESS.get();
    }

    public static double getHauntboundKnockbackResistance() {
        return SleepyHollowsForgeConfig.HAUNTBOUND_KNOCKBACK_RESISTANCE.get();
    }

    public static List<ItemStack> getHorsemanLootItems() {
        List<ItemStack> loot = new ArrayList<>();
        for (String lootString : SleepyHollowsForgeConfig.HORSEMAN_LOOT_ITEMS.get()) {
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
                Item item = ForgeRegistries.ITEMS.getValue(rl);
                if (item != null) {
                    loot.add(new ItemStack(item, count));
                } else {
                    System.err.println("Horseman Loot Item not found: " + rl);
                }

            }
        }
        return loot;
    }

    public static int getHUDX() {
        return SleepyHollowsForgeConfig.HUD_X.get();
    }

    public static int getHUDY() {
        return SleepyHollowsForgeConfig.HUD_Y.get();
    }
}
