package net.satisfy.sleepy_hollows.fabric.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.satisfy.sleepy_hollows.core.item.custom.HauntboundBootsItem;
import net.satisfy.sleepy_hollows.core.registry.ArmorRegistry;

public class HauntboundBootsRenderer implements ArmorRenderer {
    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (stack.getItem() instanceof HauntboundBootsItem boots) {
            Model model = ArmorRegistry.getBootsModel(boots, contextModel.rightLeg, contextModel.leftLeg);

            model.renderToBuffer(matrices, vertexConsumers.getBuffer(model.renderType(boots.getBootsTexture())), light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
        }
    }
}