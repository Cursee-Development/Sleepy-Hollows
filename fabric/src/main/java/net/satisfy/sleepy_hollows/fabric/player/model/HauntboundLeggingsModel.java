package net.satisfy.sleepy_hollows.fabric.player.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.satisfy.sleepy_hollows.Constants;
import org.jetbrains.annotations.NotNull;

public class HauntboundLeggingsModel<T extends LivingEntity> extends HumanoidModel<T> {

    public static final ResourceLocation HAUNTBOUND_LEGGINGS_TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/models/armor/hauntbound_armor_inner_layer.png");
    private final ModelPart body;
    private final ModelPart right_leg;
    private final ModelPart left_leg;


    public HauntboundLeggingsModel(ModelPart root) {
        super(root);
        this.body = root.getChild("body");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition left_leg_armor_bottom_r1 = right_leg.addOrReplaceChild("left_leg_armor_bottom_r1", CubeListBuilder.create().texOffs(50, 0).addBox(-4.9F, -11.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.21F)), PartPose.offsetAndRotation(1.9F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition right_leg_armor_r1 = right_leg.addOrReplaceChild("right_leg_armor_r1", CubeListBuilder.create().texOffs(40, 0).addBox(-5.9F, -13.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.22F)), PartPose.offsetAndRotation(1.9F, 11.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition left_leg_armor_bottom_r2 = left_leg.addOrReplaceChild("left_leg_armor_bottom_r2", CubeListBuilder.create().texOffs(50, 0).mirror().addBox(3.9F, -11.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.21F)).mirror(false), PartPose.offsetAndRotation(-1.9F, 11.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition left_leg_armor_r1 = left_leg.addOrReplaceChild("left_leg_armor_r1", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(4.9F, -13.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-1.9F, 11.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }



    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}