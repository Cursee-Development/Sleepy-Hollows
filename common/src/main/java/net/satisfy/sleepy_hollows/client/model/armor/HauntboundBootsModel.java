package net.satisfy.sleepy_hollows.client.model.armor;

import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.model.HumanoidModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.satisfy.sleepy_hollows.SleepyHollows;
import org.jetbrains.annotations.NotNull;

public class HauntboundBootsModel<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(SleepyHollows.identifier("hauntbound_boots"), "main");

    public HauntboundBootsModel(ModelPart root) {
        super(root);
        setAllVisible(false);
        rightLeg.visible = true;
        leftLeg.visible = true;
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(29, 47).addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(32, 10).addBox(-1.1F, 7.0F, -2.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(48, 33).addBox(-2.0F, 7.8F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(29, 47).mirror().addBox(-2.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(32, 10).mirror().addBox(-0.9F, 7.0F, -2.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(48, 33).mirror().addBox(-2.0F, 7.8F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        poseStack.pushPose();
        poseStack.scale(1.075F, 1.075F, 1.075F);
        poseStack.translate(0F, -0.095F, 0F);
        rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        poseStack.popPose();
    }
}
