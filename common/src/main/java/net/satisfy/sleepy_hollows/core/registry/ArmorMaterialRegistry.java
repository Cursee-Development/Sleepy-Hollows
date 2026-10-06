package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.world.item.Item;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.sleepy_hollows.SleepyHollows;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public class ArmorMaterialRegistry {
    private static final int ENCHANTMENT_VALUE = 15;
    private static final Holder<SoundEvent> EQUIP_SOUND = SoundEvents.ARMOR_EQUIP_NETHERITE;

    public static final ArmorMaterial HAUNTBOUND_ARMOR_INNER = createMaterial("hauntbound_inner", () -> Ingredient.of(ObjectRegistry.SPECTRAL_ESSENCE.get()));
    public static final ArmorMaterial HAUNTBOUND_ARMOR_OUTER = createMaterial("hauntbound_outer", () -> Ingredient.of(ObjectRegistry.SPECTRAL_ESSENCE.get()));
    public static final ArmorMaterial HAUNTBOUND_HELMET = createMaterial("hauntbound_helmet", () -> Ingredient.of(ObjectRegistry.SPECTRAL_ESSENCE.get()));

    private static ArmorMaterial createMaterial(String name, Supplier<Ingredient> repairSupplier) {
        return register(defense(), ENCHANTMENT_VALUE, EQUIP_SOUND, PlatformHelper.getHauntboundToughness(), PlatformHelper.getHauntboundKnockbackResistance(), repairSupplier, List.of(new ArmorMaterial.Layer(SleepyHollows.identifier(name), "", false)));
    }

    private static ArmorMaterial register(EnumMap<ArmorItem.Type, Integer> health, int enchantValue, Holder<SoundEvent> equipSound, float toughness, float knockback, Supplier<Ingredient> repairSupplier, List<ArmorMaterial.Layer> layers) {
        EnumMap<ArmorItem.Type, Integer> copy = new EnumMap<>(ArmorItem.Type.class);
        copy.putAll(health);
        return new ArmorMaterial(copy, enchantValue, equipSound, repairSupplier, layers, toughness, knockback);
    }

    private static EnumMap<ArmorItem.Type, Integer> defense() {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            map.put(type, PlatformHelper.getHauntboundDefense(type));
        }
        return map;
    }

    public static Item.Properties durability(Item.Properties properties, ArmorItem.Type type) {
        return properties.durability(type.getDurability(PlatformHelper.getHauntboundDurabilityMultiplier()));
    }
}
