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

public class HauntboundHelmetModel<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(SleepyHollows.identifier("hauntbound_helmet"), "main");

    public HauntboundHelmetModel(ModelPart root) {
        super(root);
        setAllVisible(false);
        head.visible = true;
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(56, 7).addBox(-5.0F, -13.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 15).addBox(-7.0F, -14.0F, 4.0F, 14.0F, 14.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).mirror().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)).mirror(false)
                .texOffs(0, 27).mirror().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition helmet_protection_r1 = head.addOrReplaceChild("helmet_protection_r1", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-4.0F, -2.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition decor_right_r1 = head.addOrReplaceChild("decor_right_r1", CubeListBuilder.create().texOffs(16, -2).addBox(5.0F, -10.0F, 0.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.5672F, 0.0F));

        PartDefinition horn_left_1_r1 = head.addOrReplaceChild("horn_left_1_r1", CubeListBuilder.create().texOffs(48, 0).addBox(1.0F, -9.0F, -3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -0.7854F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        poseStack.pushPose();
        poseStack.scale(1.075F, 1.075F, 1.075F);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        poseStack.popPose();
    }
}
