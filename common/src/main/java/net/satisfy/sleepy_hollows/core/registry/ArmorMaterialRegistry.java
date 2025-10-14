package net.satisfy.sleepy_hollows.core.registry;

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
    private static final float TOUGHNESS = 2.5F;
    private static final float KNOCKBACK_RESISTANCE = 0.05F;

    public static final ArmorMaterial HAUNTBOUND_ARMOR = createMaterial("hauntbound_armor", Ingredient.of(ObjectRegistry.SPECTRAL_ESSENCE.get()));

    private static ArmorMaterial createMaterial(String name, Ingredient repairIngredient) {
        return register(
                slots(14, 16, 17, 12, 0),
                ENCHANTMENT_VALUE,
                EQUIP_SOUND,
                TOUGHNESS,
                KNOCKBACK_RESISTANCE,
                () -> repairIngredient,
                List.of(new ArmorMaterial.Layer(SleepyHollows.identifier(name), "", false))
        );
    }

    private static ArmorMaterial register(EnumMap<ArmorItem.Type, Integer> health, int enchantValue, Holder<SoundEvent> equipSound, float toughness, float knockback, Supplier<Ingredient> repair, List<ArmorMaterial.Layer> layers) {
        EnumMap<ArmorItem.Type, Integer> copy = new EnumMap<>(ArmorItem.Type.class);
        copy.putAll(health);
        return new ArmorMaterial(copy, enchantValue, equipSound, repair, layers, toughness, knockback);
    }

    private static EnumMap<ArmorItem.Type, Integer> slots(int boots, int leggings, int chestplate, int helmet, int body) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.BOOTS, boots);
        map.put(ArmorItem.Type.LEGGINGS, leggings);
        map.put(ArmorItem.Type.CHESTPLATE, chestplate);
        map.put(ArmorItem.Type.HELMET, helmet);
        if (ArmorItem.Type.values().length > 4) map.put(ArmorItem.Type.BODY, body);
        return map;
    }
}