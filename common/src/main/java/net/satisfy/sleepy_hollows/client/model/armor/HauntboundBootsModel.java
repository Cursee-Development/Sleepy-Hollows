package net.satisfy.sleepy_hollows.client.model.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import org.jetbrains.annotations.NotNull;

public class HauntboundBootsModel<T extends Entity> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new SleepyHollowsIdentifier("hauntbound_boots"), "main");
    private final ModelPart right_leg;
    private final ModelPart left_leg;

    public HauntboundBootsModel(ModelPart root) {
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(29, 47).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(32, 10).addBox(-1.1F, 7.0F, -2.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(48, 33).addBox(-2.0F, 7.8F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(29, 47).mirror().addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(32, 10).mirror().addBox(-0.9F, 7.0F, -2.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(48, 33).mirror().addBox(-2.0F, 7.8F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.scale(1.05F, 1.05F, 1.05F);
        poseStack.translate(0F, -0.095F, 0F);
        right_leg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        left_leg.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    @Override
    public void setupAnim(@NotNull T entity, float f, float g, float h, float i, float j) {
    }

    public void copyLegs(ModelPart rightLegModel, ModelPart leftLegModel) {
        this.right_leg.copyFrom(rightLegModel);
        this.left_leg.copyFrom(leftLegModel);
    }
}
