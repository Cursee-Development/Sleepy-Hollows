package net.satisfy.sleepy_hollows.client.renderer;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.level.Level;
import net.minecraft.util.Mth;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.satisfy.sleepy_hollows.core.block.PedestalBlock;
import net.satisfy.sleepy_hollows.core.block.entity.PedestalBlockEntity;
import org.jetbrains.annotations.NotNull;


public class PedestalBlockRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    public PedestalBlockRenderer() {
    }

    @Override
    public void render(PedestalBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (!blockEntity.getBlockState().getValue(PedestalBlock.ACTIVE)) return;
        ItemStack itemStack = blockEntity.getDisplayedItem();
        if (itemStack.isEmpty()) return;

        Level level = blockEntity.getLevel();
        if (level == null) return;
        float time = level.getGameTime() + partialTick;
        float appear = Mth.clamp((time - blockEntity.getDisplayedSince()) / 10.0F, 0.0F, 1.0F);
        float scale = 0.75F * (1.0F - (1.0F - appear) * (1.0F - appear));

        float ritual = blockEntity.getRitualProgress(partialTick);
        float rise = ritual * ritual * 0.8F;
        float pulse = 1.0F + ritual * 0.15F * Mth.sin(time * (0.3F + ritual));
        scale *= pulse;

        poseStack.pushPose();
        poseStack.translate(0.5, 1.2 + Mth.sin(time / 10.0F) * 0.06 + (1.0F - appear) * 0.25 + rise, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(time * (2.0F + ritual * ritual * 30.0F)));
        poseStack.scale(scale, scale, scale);
        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.GROUND, LightTexture.FULL_BRIGHT, combinedOverlay, poseStack, bufferSource, level, 0);
        poseStack.popPose();
    }
}