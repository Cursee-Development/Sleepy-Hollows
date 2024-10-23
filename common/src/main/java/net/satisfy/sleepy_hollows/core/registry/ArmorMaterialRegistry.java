package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ArmorMaterialRegistry {
    public static final ArmorMaterial HAUNTBOUND_ARMOR = new ArmorMaterial() {

        @Override
        public int getDurabilityForType(ArmorItem.@NotNull Type type) {
            return ArmorMaterials.DIAMOND.getDurabilityForType(type);
        }

        @Override
        public int getDefenseForType(ArmorItem.@NotNull Type type) {
            return ArmorMaterials.DIAMOND.getDefenseForType(type);
        }

        @Override
        public int getEnchantmentValue() {
            return 12;
        }

        @Override
        public @NotNull SoundEvent getEquipSound() {
            return SoundEventRegistry.EQUIP_HAUNTBOUND.get();
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(ObjectRegistry.SPECTRAL_ESSENCE.get());
        }

        @Override
        public @NotNull String getName() {
            return "hauntbound";
        }

        @Override
        public float getToughness() {
            return (ArmorMaterials.DIAMOND.getToughness());
        }

        @Override
        public float getKnockbackResistance() {
            return ArmorMaterials.DIAMOND.getKnockbackResistance() + 0.05f;
        }
    };
}
