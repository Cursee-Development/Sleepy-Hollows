package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundBootsModel;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundChestplateModel;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundHelmetModel;
import net.satisfy.sleepy_hollows.client.model.armor.HauntboundLeggingsModel;
import net.satisfy.sleepy_hollows.core.item.custom.HauntboundArmorItem;
import net.satisfy.sleepy_hollows.core.item.custom.HauntboundHelmetItem;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArmorRegistry {
    private static final Map<Item, HauntboundHelmetModel<?>> models = new HashMap<>();
    private static final Map<Item, HauntboundChestplateModel<?>> chestplateModels = new HashMap<>();
    private static final Map<Item, HauntboundLeggingsModel<?>> leggingsModels = new HashMap<>();
    private static final Map<Item, HauntboundBootsModel<?>> bootsModels = new HashMap<>();

    public static Model getHatModel(Item item, ModelPart baseHead) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        HauntboundHelmetModel<?> model = models.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.HAUNTBOUND_HELMET.get()) {
                return new HauntboundHelmetModel<>(modelSet.bakeLayer(HauntboundHelmetModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        assert model != null;
        model.copyHead(baseHead);

        return model;
    }

    public static Model getChestplateModel(Item item, ModelPart body, ModelPart leftArm, ModelPart rightArm) {
        HauntboundChestplateModel<?> model = chestplateModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.HAUNTBOUND_CHESTPLATE.get()) {
                return new HauntboundChestplateModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(HauntboundChestplateModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        assert model != null;
        model.copyBody(body, leftArm, rightArm);

        return model;
    }

    public static Model getLeggingsModel(Item item, ModelPart rightLeg, ModelPart leftLeg) {
        HauntboundLeggingsModel<?> model = leggingsModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.HAUNTBOUND_LEGGINGS.get()) {
                return new HauntboundLeggingsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(HauntboundLeggingsModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        assert model != null;
        model.copyLegs(rightLeg, leftLeg);

        return model;
    }

    public static Model getBootsModel(Item item, ModelPart rightLeg, ModelPart leftLeg) {
        HauntboundBootsModel<?> model = bootsModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.HAUNTBOUND_BOOTS.get()) {
                return new HauntboundBootsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(HauntboundBootsModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        assert model != null;
        model.copyLegs(rightLeg, leftLeg);

        return model;
    }

    public static void appendToolTip(@NotNull List<Component> tooltip) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        boolean hasFullSet = helmet.getItem() instanceof HauntboundHelmetItem &&
                chestplate.getItem() instanceof HauntboundArmorItem &&
                leggings.getItem() instanceof HauntboundArmorItem &&
                boots.getItem() instanceof HauntboundArmorItem;

        tooltip.add(Component.nullToEmpty(""));
        tooltip.add(Component.nullToEmpty(ChatFormatting.DARK_GREEN + I18n.get("tooltip.sleepy_hollows.hauntbound_armor_0")));
        tooltip.add(Component.nullToEmpty((helmet.getItem() instanceof HauntboundHelmetItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.HAUNTBOUND_HELMET.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((chestplate.getItem() instanceof HauntboundArmorItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.HAUNTBOUND_CHESTPLATE.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((leggings.getItem() instanceof HauntboundArmorItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.HAUNTBOUND_LEGGINGS.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty((boots.getItem() instanceof HauntboundArmorItem ? ChatFormatting.GREEN.toString() : ChatFormatting.GRAY.toString()) + "- [" + ObjectRegistry.HAUNTBOUND_BOOTS.get().getDescription().getString() + "]"));
        tooltip.add(Component.nullToEmpty(""));

        ChatFormatting color = hasFullSet ? ChatFormatting.GREEN : ChatFormatting.GRAY;
        tooltip.add(Component.nullToEmpty(color + I18n.get("tooltip.sleepy_hollows.hauntbound_armor_1")));
        tooltip.add(Component.nullToEmpty(color + I18n.get("tooltip.sleepy_hollows.hauntbound_armor_2")));
    }
}
