package net.satisfy.sleepy_hollows.neoforge.client.extensions;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.satisfy.sleepy_hollows.core.item.HauntboundBootsItem;
import net.satisfy.sleepy_hollows.core.registry.ArmorRegistry;
import org.jetbrains.annotations.NotNull;

public class HauntboundBootsExtensions implements IClientItemExtensions {
    @Override
    public @NotNull Model getGenericArmorModel(@NotNull LivingEntity entity, @NotNull ItemStack stack, @NotNull EquipmentSlot slot, @NotNull HumanoidModel<?> original) {
        if (slot == EquipmentSlot.FEET && stack.getItem() instanceof HauntboundBootsItem hauntboundBootsItem) {
            return ArmorRegistry.getBootsModel(hauntboundBootsItem, original.rightLeg, original.leftLeg);
        }
        return original;
    }
}
