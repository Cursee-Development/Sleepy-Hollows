package net.satisfy.sleepy_hollows.core.util;

import net.minecraft.world.item.ItemStack;

public interface EnchantingBehavior {
	@SuppressWarnings("all")
	default boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return true;
	}
}