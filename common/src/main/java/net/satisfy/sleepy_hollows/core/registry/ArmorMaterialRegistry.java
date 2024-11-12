package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import org.jetbrains.annotations.NotNull;

public class ArmorMaterialRegistry {
    public static final ArmorMaterial HAUNTBOUND_ARMOR = new ArmorMaterial() {

        @Override
        public int getDurabilityForType(ArmorItem.@NotNull Type type) {
            return PlatformHelper.getHauntboundDurability(type);
        }

        @Override
        public int getDefenseForType(ArmorItem.@NotNull Type type) {
            return PlatformHelper.getHauntboundDefense(type);
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
            return (float) PlatformHelper.getHauntboundToughness();
        }

        @Override
        public float getKnockbackResistance() {
            return (float) PlatformHelper.getHauntboundKnockbackResistance();
        }
    };
}
