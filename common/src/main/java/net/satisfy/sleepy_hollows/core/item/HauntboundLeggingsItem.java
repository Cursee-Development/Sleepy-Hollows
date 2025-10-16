package net.satisfy.sleepy_hollows.core.item;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.satisfy.sleepy_hollows.core.registry.ArmorRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HauntboundLeggingsItem extends ArmorItem {
    private final ResourceLocation leggingsTexture;

    public HauntboundLeggingsItem(Holder<ArmorMaterial> material, Type type, Properties properties, ResourceLocation leggingsTexture) {
        super(material, type, properties);
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        ArmorRegistry.appendToolTip(tooltip);
    }
}