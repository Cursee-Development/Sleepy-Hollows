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

public class HauntboundHelmetModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new SleepyHollowsIdentifier("hauntbound_helmet"), "main");
    private final ModelPart hauntbound_helmet;

    public HauntboundHelmetModel(ModelPart root) {
        this.hauntbound_helmet = root.getChild("hauntbound_helmet");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition hauntbound_helmet = partdefinition.addOrReplaceChild("hauntbound_helmet", CubeListBuilder.create().texOffs(56, 7).addBox(-5.0F, -13.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 15).addBox(-7.0F, -14.0F, 4.0F, 14.0F, 14.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).mirror().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)).mirror(false)
                .texOffs(0, 27).mirror().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition helmet_protection_r1 = hauntbound_helmet.addOrReplaceChild("helmet_protection_r1", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-4.0F, -2.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition decor_right_r1 = hauntbound_helmet.addOrReplaceChild("decor_right_r1", CubeListBuilder.create().texOffs(16, -2).addBox(5.0F, -10.0F, 0.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.5672F, 0.0F));

        PartDefinition horn_left_1_r1 = hauntbound_helmet.addOrReplaceChild("horn_left_1_r1", CubeListBuilder.create().texOffs(48, 0).addBox(1.0F, -9.0F, -3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -0.7854F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.scale(1.075F, 1.075F, 1.075F);
        hauntbound_helmet.render(poseStack, buffer, packedLight, packedOverlay);
        poseStack.popPose();
    }

    @Override
    public void setupAnim(@NotNull T entity, float f, float g, float h, float i, float j) {

    }

    public void copyHead(ModelPart model) {
        hauntbound_helmet.copyFrom(model);
    }
}
