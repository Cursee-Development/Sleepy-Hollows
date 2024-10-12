package net.satisfy.sleepy_hollows.fabric.player.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import org.jetbrains.annotations.NotNull;

public class HauntboundChestplateModel<T extends LivingEntity> extends HumanoidModel<T> {

    public static final ResourceLocation HAUNTBOUND_CHESTPLATE_TEXTURE = new SleepyHollowsIdentifier("textures/models/armor/hauntbound_armor_outer_layer.png");

    private final ModelPart body;
    private final ModelPart body_decor;
    private final ModelPart left_arm;
    private final ModelPart right_arm;

    public HauntboundChestplateModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.body_decor = this.body.getChild("body_decor");
        this.left_arm = root.getChild("left_arm");
        this.right_arm = root.getChild("right_arm");
    }


    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_plate_r1 = body.addOrReplaceChild("body_plate_r1", CubeListBuilder.create().texOffs(18, 33).addBox(-2.0F, -23.5F, -3.5F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition body_decor = body.addOrReplaceChild("body_decor", CubeListBuilder.create().texOffs(28, 22).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.16F)).mirror(false)
                .texOffs(0, 45).mirror().addBox(-1.0F, 6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.16F)).mirror(false)
                .texOffs(42, 28).mirror().addBox(-1.0F, 6.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(24, 3).addBox(0.0F, -1.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(0.0F, -1.5F, 3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(9, 34).addBox(0.5F, -5.3F, -3.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(9, 34).addBox(0.5F, -0.3F, -3.5F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition shoulder_bottom_r1 = left_arm.addOrReplaceChild("shoulder_bottom_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-1.5F, -0.5F, -3.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8F, -0.5F, 0.0F, 0.0F, 3.1416F, -0.1309F));

        PartDefinition shoulder_support_bottom_r1 = left_arm.addOrReplaceChild("shoulder_support_bottom_r1", CubeListBuilder.create().texOffs(19, 39).addBox(0.5F, -3.5F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.25F))
                .texOffs(0, 16).addBox(-3.5F, -2.5F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).mirror().addBox(-2.5F, -3.5F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(1.8F, -0.5F, 0.0F, 0.0F, 3.1416F, -0.7854F));

        PartDefinition shoulder_spike_r1 = left_arm.addOrReplaceChild("shoulder_spike_r1", CubeListBuilder.create().texOffs(41, 59).addBox(-11.5F, 0.5F, 0.0F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8F, -0.5F, 0.0F, 0.0F, 3.1416F, -1.5708F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 29).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.16F))
                .texOffs(0, 45).addBox(-3.0F, 6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.16F))
                .texOffs(42, 28).addBox(-3.0F, 6.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(24, 3).mirror().addBox(-2.0F, -1.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(9, 34).mirror().addBox(-1.5F, -5.3F, -3.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 0).mirror().addBox(-2.0F, -1.5F, 3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(9, 34).mirror().addBox(-1.5F, -0.3F, -3.5F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition shoulder_bottom_r2 = right_arm.addOrReplaceChild("shoulder_bottom_r2", CubeListBuilder.create().texOffs(14, 22).addBox(-2.5F, 0.5F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8F, -0.5F, 0.0F, 0.0F, 3.1416F, 0.1309F));

        PartDefinition shoulder_support_bottom_r2 = right_arm.addOrReplaceChild("shoulder_support_bottom_r2", CubeListBuilder.create().texOffs(19, 39).addBox(-1.5F, -3.5F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.25F))
                .texOffs(0, 16).addBox(-1.5F, -3.5F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.8F, -0.5F, 0.0F, 0.0F, 3.1416F, 0.7854F));

        PartDefinition shoulder_r1 = right_arm.addOrReplaceChild("shoulder_r1", CubeListBuilder.create().texOffs(38, 4).addBox(-0.5F, -1.5F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(-1.8F, -0.5F, 0.0F, 0.0F, 3.1416F, 0.6109F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.left_arm.copyFrom(this.leftArm);
        this.right_arm.copyFrom(this.rightArm);

        if (entity instanceof Player player && (!player.getMainHandItem().isEmpty() || player.isUsingItem())) {
            this.right_arm.xRot = this.rightArm.xRot;
            this.right_arm.yRot = this.rightArm.yRot;
            this.right_arm.zRot = this.rightArm.zRot;

            this.left_arm.xRot = this.leftArm.xRot;
            this.left_arm.yRot = this.leftArm.yRot;
            this.left_arm.zRot = this.leftArm.zRot;
        }
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}