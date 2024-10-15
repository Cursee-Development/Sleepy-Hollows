package net.satisfy.sleepy_hollows.core.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public interface EnchantingBehavior {
	default boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return true;
	}

	default boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment.category.canEnchant(stack.getItem());
	}
}