package net.satisfy.sleepy_hollows.forge.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.satisfy.sleepy_hollows.core.item.custom.HauntboundChestplateItem;
import net.satisfy.sleepy_hollows.core.registry.ArmorRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Consumer;

@Mixin(HauntboundChestplateItem.class)
public abstract class ChestplateItemMixin extends ArmorItem {
    @Shadow
    @Final
    private ResourceLocation chestplateTexture;

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(
                new IClientItemExtensions() {
                    @Override
                    public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                        return ArmorRegistry.getChestplateModel(itemStack.getItem(), original.body, original.leftArm, original.rightArm);
                    }
                }
        );
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return chestplateTexture.toString();
    }

    private ChestplateItemMixin(ArmorMaterial armorMaterial, Type armorType, Properties itemProperties) {
        super(armorMaterial, armorType, itemProperties);
    }
}
