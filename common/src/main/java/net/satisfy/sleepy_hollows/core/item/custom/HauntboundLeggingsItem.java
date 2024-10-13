package net.satisfy.sleepy_hollows.core.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.satisfy.sleepy_hollows.core.registry.ArmorRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HauntboundLeggingsItem extends ArmorItem {
    private final ResourceLocation leggingsTexture;

    public HauntboundLeggingsItem(ArmorMaterial armorMaterial, Type type, Properties properties, ResourceLocation leggingsTexture) {
        super(armorMaterial, type, properties);
        this.leggingsTexture = leggingsTexture;
    }

    public ResourceLocation getLeggingsTexture() {
        return leggingsTexture;
    }

    @Override
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.LEGS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag context) {
        if (world != null && world.isClientSide()) {
            ArmorRegistry.appendToolTip(tooltip);
        }
    }
}
