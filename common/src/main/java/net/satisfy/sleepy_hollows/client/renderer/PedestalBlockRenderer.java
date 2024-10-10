package net.satisfy.sleepy_hollows.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.satisfy.sleepy_hollows.core.block.custom.entity.PedestalBlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PedestalBlockRenderer implements BlockEntityRenderer<PedestalBlockEntity> {

    public PedestalBlockRenderer() {
    }

    @Override
    public void render(PedestalBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        ItemStack itemStack = blockEntity.getDisplayedItem();
        if (!itemStack.isEmpty()) {
            poseStack.pushPose();

            double offset = Math.sin((Objects.requireNonNull(blockEntity.getLevel()).getGameTime() + partialTick) / 4.0) * 0.1;
            float rotation = (Objects.requireNonNull(blockEntity.getLevel()).getGameTime() + partialTick) * 4; // Adjust the speed of rotation

            poseStack.translate(0.5, 1.25 + offset * 0.05, 0.5);

            poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(rotation));

            poseStack.scale(0.75f, 0.75f, 0.75f);
            Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.GROUND, combinedLight, combinedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }
    }
}
