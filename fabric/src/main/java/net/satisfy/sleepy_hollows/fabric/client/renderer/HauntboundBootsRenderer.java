package net.satisfy.sleepy_hollows.fabric.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.item.HauntboundBootsItem;
import net.satisfy.sleepy_hollows.core.registry.ArmorRegistry;

public class HauntboundBootsRenderer implements ArmorRenderer {
    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (slot != EquipmentSlot.FEET) return;
        if (!(stack.getItem() instanceof HauntboundBootsItem hauntboundBootsItem)) return;
        Model model = ArmorRegistry.getBootsModel(hauntboundBootsItem, contextModel.rightLeg, contextModel.leftLeg);
        ResourceLocation texture = SleepyHollows.identifier("textures/models/armor/hauntbound_outer.png");
        model.renderToBuffer(poseStack, buffers.getBuffer(model.renderType(texture)), light, OverlayTexture.NO_OVERLAY);
    }
}
