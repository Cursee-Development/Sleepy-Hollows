package net.satisfy.sleepy_hollows.fabric.player.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.satisfy.sleepy_hollows.client.SleepyHollowsClient;
import net.satisfy.sleepy_hollows.core.item.custom.HauntboundArmorItem;
import net.satisfy.sleepy_hollows.fabric.player.model.HauntboundChestplateModel;
import org.jetbrains.annotations.NotNull;

public class HauntboundChestplateLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {

    private final HauntboundChestplateModel<T> model;

    public HauntboundChestplateLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
        this.model = new HauntboundChestplateModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SleepyHollowsClient.HAUNTBOUND_CHESTPLATE_LAYER));
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, @NotNull T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof Player player) {
            ItemStack chestItem = player.getInventory().armor.get(2);
            boolean chestSlotHasHauntboundChestplate = chestItem.getItem() instanceof HauntboundArmorItem;

            if (chestSlotHasHauntboundChestplate) {
                this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

                poseStack.pushPose();
                poseStack.scale(1.05F, 1.05F, 1.05F);
                poseStack.translate(0.0d, 0.0d, 0.0d);
                renderColoredCutoutModel(this.model, getTextureLocation(entity), poseStack, multiBufferSource, i, entity, 1.0f, 1.0f, 1.0f);
                poseStack.popPose();
            }
        }
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(@NotNull T entity) {
        return HauntboundChestplateModel.HAUNTBOUND_CHESTPLATE_TEXTURE;
    }
}
