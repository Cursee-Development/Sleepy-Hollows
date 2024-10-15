package net.satisfy.sleepy_hollows.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.satisfy.sleepy_hollows.core.block.custom.CoffinBlock;
import net.satisfy.sleepy_hollows.core.block.custom.entity.CoffinBlockEntity;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@SuppressWarnings("unused")
public class CoffinRenderer implements BlockEntityRenderer<CoffinBlockEntity> {
    private static final ResourceLocation TEXTURE = new SleepyHollowsIdentifier("textures/entity/coffin.png");
    private final ModelPart coffin;
    private final ModelPart lid;

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new SleepyHollowsIdentifier("coffin"), "main");

    public CoffinRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelPart = context.bakeLayer(LAYER_LOCATION);
        this.coffin = modelPart.getChild("coffin");
        this.lid = this.coffin.getChild("lid");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition coffin = partdefinition.addOrReplaceChild("coffin", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -10.0F, 0.0F, 16.0F, 10.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

        PartDefinition lid = coffin.addOrReplaceChild("lid", CubeListBuilder.create()
                .texOffs(0, 42).addBox(-16.0F, 1.5F, -16.0F, 16.0F, 4.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(0, 78).addBox(-14.0F, -0.5F, -16.0F, 12.0F, 2.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -15.5F, 16.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void render(CoffinBlockEntity blockEntity, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getBlockState().getValue(CoffinBlock.BED_PART) != BedPart.HEAD) {
            return;
        }

        float lidAngle = blockEntity.getOpenNess(partialTicks);
        lidAngle = 1.0F - lidAngle;
        lidAngle = 1.0F - (float) Math.pow(lidAngle, 1);

        float slideDistance = lidAngle * 8.0F;

        this.lid.x = -slideDistance;

        poseStack.pushPose();
        Direction facing = blockEntity.getBlockState().getValue(CoffinBlock.FACING);
        switch (facing) {
            case NORTH:
                poseStack.translate(1D, 1D, 0D);
                break;
            case SOUTH:
                poseStack.translate(0D, 1D, 1D);
                break;
            case EAST:
                poseStack.translate(1D, 1D, 1D);
                break;
            case WEST:
                poseStack.translate(0D, 1D, 0D);
                break;
        }
        poseStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(-facing.toYRot() + 180)));
        poseStack.mulPose(new Quaternionf().rotateX((float) Math.toRadians(180)));
        poseStack.translate(-0.5D, -0.5D, -0.5D);

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.coffin.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        poseStack.popPose();
    }
}
