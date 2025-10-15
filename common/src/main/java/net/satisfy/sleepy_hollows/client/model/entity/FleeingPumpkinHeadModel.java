package net.satisfy.sleepy_hollows.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.satisfy.sleepy_hollows.SleepyHollows;
import org.jetbrains.annotations.NotNull;

public class FleeingPumpkinHeadModel<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(SleepyHollows.identifier("fleeing_pumpkin_head"), "main");
    private final ModelPart head;

    public FleeingPumpkinHeadModel(ModelPart root) {
        this.head = root.getChild("head");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition getTexturedModelData() {

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -29.0F, -5.0F, 13.0F, 13.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float bounceHeight = 1.0F;
        float bounceSpeed = 0.1F;
        this.head.y = 24.0F + (float) Math.sin(ageInTicks * bounceSpeed) * bounceHeight;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, int k) {
        head.render(poseStack, vertexConsumer, i, j, k);
    }
}