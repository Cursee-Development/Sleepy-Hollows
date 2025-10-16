package net.satisfy.sleepy_hollows.neoforge.client.extensions;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.satisfy.sleepy_hollows.core.item.HauntboundChestplateItem;
import net.satisfy.sleepy_hollows.core.registry.ArmorRegistry;
import org.jetbrains.annotations.NotNull;

public class HauntboundChestplateExtensions implements IClientItemExtensions {
    
    @Override
    public @NotNull Model getGenericArmorModel(@NotNull LivingEntity entity, @NotNull ItemStack stack, @NotNull EquipmentSlot slot, @NotNull HumanoidModel<?> original) {
        if (slot == EquipmentSlot.CHEST && stack.getItem() instanceof HauntboundChestplateItem chest) {
            return ArmorRegistry.getChestplateModel(chest, original.body, original.leftArm, original.rightArm);
        }
        return original;
    }
}
