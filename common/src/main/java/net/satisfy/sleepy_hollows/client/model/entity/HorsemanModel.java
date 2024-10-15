package net.satisfy.sleepy_hollows.client.model.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.satisfy.sleepy_hollows.core.entity.Horseman;
import net.satisfy.sleepy_hollows.core.entity.animation.HorsemanAnimation;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import org.jetbrains.annotations.NotNull;

public class HorsemanModel<T extends Horseman> extends HierarchicalModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new SleepyHollowsIdentifier("horseman"), "main");
	private final ModelPart root;


	public HorsemanModel(ModelPart root) {
		this.root = root;
	}

	@SuppressWarnings("unused")
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition horse = root.addOrReplaceChild("horse", CubeListBuilder.create(), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition head = horse.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -13.1963F, -2.1502F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 25).addBox(-2.0F, -13.1963F, -7.1502F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 35).addBox(-2.0F, -8.1963F, -2.1502F, 4.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 35).addBox(-2.0F, -8.1963F, -2.1502F, 4.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(66, 42).addBox(-3.0F, -13.1963F, -2.1502F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.1F))
				.texOffs(64, 82).addBox(-2.0F, -13.1963F, -7.1502F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.1F))
				.texOffs(32, 66).addBox(-0.5F, -15.3497F, -4.559F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(32, 66).addBox(-0.5F, -15.3497F, 0.441F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -10.0F, 0.5498F, 0.0F, 0.0F));

		PartDefinition decor_right_r1 = head.addOrReplaceChild("decor_right_r1", CubeListBuilder.create().texOffs(37, 58).addBox(2.5F, -5.0F, 2.5F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -14.3497F, -4.059F, 0.0F, 0.0F, 0.6109F));

		PartDefinition decor_left_r1 = head.addOrReplaceChild("decor_left_r1", CubeListBuilder.create().texOffs(37, 58).addBox(-2.5F, -5.0F, 2.5F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -14.3497F, -4.059F, 0.0F, 0.0F, -0.6109F));

		PartDefinition HeadSaddle = head.addOrReplaceChild("HeadSaddle", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, -16.0F, -5.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.25F))
				.texOffs(0, 0).addBox(-3.0F, -16.0F, -3.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 2.8037F, 6.8498F, 0.5236F, 0.0F, 0.0F));

		PartDefinition SaddleMouthLineR = head.addOrReplaceChild("SaddleMouthLineR", CubeListBuilder.create().texOffs(32, 2).addBox(-3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.8037F, 6.8498F));

		PartDefinition SaddleMouthLine = head.addOrReplaceChild("SaddleMouthLine", CubeListBuilder.create().texOffs(32, 2).addBox(3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.8037F, 6.8498F));

		PartDefinition MuleEarR = head.addOrReplaceChild("MuleEarR", CubeListBuilder.create().texOffs(0, 12).addBox(1.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.8037F, 6.8498F, 0.5236F, 0.0F, -0.2618F));

		PartDefinition MuleEarL = head.addOrReplaceChild("MuleEarL", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-3.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 2.8037F, 6.8498F, 0.5236F, 0.0F, 0.2618F));

		PartDefinition SaddleMouthL = head.addOrReplaceChild("SaddleMouthL", CubeListBuilder.create().texOffs(29, 5).addBox(2.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.8037F, 6.8498F, 0.5236F, 0.0F, 0.0F));

		PartDefinition SaddleMouthR = head.addOrReplaceChild("SaddleMouthR", CubeListBuilder.create().texOffs(29, 5).addBox(-3.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.8037F, 6.8498F, 0.5236F, 0.0F, 0.0F));

		PartDefinition Leg4A = horse.addOrReplaceChild("Leg4A", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 7.0F, -9.0F));

		PartDefinition Leg3A = horse.addOrReplaceChild("Leg3A", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 7.0F, -9.0F));

		PartDefinition TailA = horse.addOrReplaceChild("TailA", CubeListBuilder.create().texOffs(42, 36).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 11.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition body = horse.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -8.0F, -20.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 9.0F));

		PartDefinition additional_armor = body.addOrReplaceChild("additional_armor", CubeListBuilder.create().texOffs(64, 0).addBox(-5.0F, -0.0528F, -11.7686F, 10.0F, 16.0F, 22.0F, new CubeDeformation(0.2F))
				.texOffs(0, 104).addBox(-5.0F, -0.0528F, -11.7686F, 10.0F, 15.0F, 9.0F, new CubeDeformation(0.25F))
				.texOffs(64, 54).addBox(-5.0F, -0.0528F, 3.2314F, 10.0F, 16.0F, 10.0F, new CubeDeformation(0.25F))
				.texOffs(32, 79).addBox(-6.0F, -5.0528F, 3.2214F, 12.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 74).addBox(-4.0F, -8.0528F, 3.2214F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 69).addBox(-5.0F, -11.0528F, 3.2214F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.9472F, -8.2314F));

		PartDefinition saddle_decoration_r1 = additional_armor.addOrReplaceChild("saddle_decoration_r1", CubeListBuilder.create().texOffs(88, 115).addBox(-10.0F, -34.0F, 1.0F, 20.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 64).addBox(-5.0F, -19.0F, -16.0F, 10.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.9472F, -0.7686F, -0.2182F, 0.0F, 0.0F));

		PartDefinition armor_body_front_decoration_left_r1 = additional_armor.addOrReplaceChild("armor_body_front_decoration_left_r1", CubeListBuilder.create().texOffs(56, 4).addBox(-5.0F, -28.0F, 5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(56, 4).mirror().addBox(-5.0F, -28.0F, 21.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 20.9472F, -16.7686F, 0.0F, 0.0F, 0.48F));

		PartDefinition armor_body_front_decoration_right_r1 = additional_armor.addOrReplaceChild("armor_body_front_decoration_right_r1", CubeListBuilder.create().texOffs(56, 4).addBox(5.0F, -28.0F, 5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(56, 4).mirror().addBox(5.0F, -28.0F, 21.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 20.9472F, -16.7686F, 0.0F, 0.0F, -0.48F));

		PartDefinition armor_body_front_r1 = additional_armor.addOrReplaceChild("armor_body_front_r1", CubeListBuilder.create().texOffs(38, 107).addBox(-5.0F, -20.0F, -14.0F, 10.0F, 15.0F, 6.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 20.9472F, -0.7686F, -0.1309F, 0.0F, 0.0F));

		PartDefinition Leg1A = horse.addOrReplaceChild("Leg1A", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 7.0F, 9.0F));

		PartDefinition Leg2A = horse.addOrReplaceChild("Leg2A", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 7.0F, 9.0F));

		PartDefinition rider = horse.addOrReplaceChild("rider", CubeListBuilder.create().texOffs(216, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -6.0F, 1.0F));

		PartDefinition body_armor = rider.addOrReplaceChild("body_armor", CubeListBuilder.create().texOffs(0, 192).addBox(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(28, 214).addBox(-4.0F, -14.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition body_plate_r1 = body_armor.addOrReplaceChild("body_plate_r1", CubeListBuilder.create().texOffs(18, 225).addBox(-2.0F, -23.5F, -3.5F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.0509F, 0.085F, -0.0436F, 0.0F, 0.0F));

		PartDefinition head2 = rider.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(200, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition PUMPKIN = head2.addOrReplaceChild("PUMPKIN", CubeListBuilder.create().texOffs(148, 0).addBox(-12.0F, -13.0F, -1.0F, 13.0F, 13.0F, 13.0F, new CubeDeformation(-1.5F)), PartPose.offset(5.5F, 2.0F, -5.0F));

		PartDefinition right_arm = rider.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-6.0F, -10.364F, -0.136F));

		PartDefinition RightArm_r1 = right_arm.addOrReplaceChild("RightArm_r1", CubeListBuilder.create().texOffs(240, 16).addBox(-8.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 16.364F, -15.864F, -0.7854F, 0.0F, 0.0F));

		PartDefinition right_arm2 = right_arm.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(0, 221).addBox(-8.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(0, 237).addBox(-8.0F, 8.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.275F))
				.texOffs(42, 220).addBox(-8.0F, 8.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.3F))
				.texOffs(24, 195).mirror().addBox(-7.0F, 0.5F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(9, 226).mirror().addBox(-6.5F, -3.3F, -3.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(9, 226).mirror().addBox(-6.5F, 1.7F, -3.5F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(20, 192).mirror().addBox(-7.0F, 0.5F, 3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, -0.636F, 1.136F, -0.7854F, 0.0F, 0.0F));

		PartDefinition shoulder_r1 = right_arm2.addOrReplaceChild("shoulder_r1", CubeListBuilder.create().texOffs(38, 196).addBox(-0.5F, -1.5F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(-6.8F, 1.5F, 0.0F, 0.0F, 3.1416F, 0.6109F));

		PartDefinition shoulder_bottom_r1 = right_arm2.addOrReplaceChild("shoulder_bottom_r1", CubeListBuilder.create().texOffs(14, 214).addBox(-2.5F, 0.5F, -3.0F, 4.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.8F, 1.5F, 0.0F, 0.0F, 3.1416F, 0.1309F));

		PartDefinition shoulder_support_bottom_r1 = right_arm2.addOrReplaceChild("shoulder_support_bottom_r1", CubeListBuilder.create().texOffs(19, 231).addBox(-1.5F, -3.5F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(0, 208).addBox(-1.5F, -3.5F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-6.8F, 1.5F, 0.0F, 0.0F, 3.1416F, 0.7854F));

		PartDefinition left_arm = rider.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(6.0F, -10.364F, -0.136F));

		PartDefinition LeftArm_r1 = left_arm.addOrReplaceChild("LeftArm_r1", CubeListBuilder.create().texOffs(240, 16).mirror().addBox(4.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, 16.364F, -15.864F, -0.7854F, 0.0F, 0.0F));

		PartDefinition left_arm2 = left_arm.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(0, 221).mirror().addBox(-2.1279F, -2.1248F, -1.8958F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(9, 226).addBox(-0.6279F, -5.3739F, -3.3108F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.25F))
				.texOffs(20, 192).addBox(-1.1279F, -1.5739F, 3.1892F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
				.texOffs(24, 195).addBox(-1.1279F, -1.5739F, -3.8108F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
				.texOffs(42, 220).mirror().addBox(-2.1279F, 5.9261F, -1.8108F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.34F)).mirror(false)
				.texOffs(0, 237).mirror().addBox(-2.1279F, 6.2752F, -1.8108F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offsetAndRotation(0.1279F, 0.4888F, 0.0319F, -0.7854F, 0.0F, 0.0F));

		PartDefinition shoulder_r2 = left_arm2.addOrReplaceChild("shoulder_r2", CubeListBuilder.create().texOffs(0, 208).mirror().addBox(-2.5F, -3.5F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.26F)).mirror(false)
				.texOffs(19, 231).addBox(0.5F, -3.5F, -3.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.25F))
				.texOffs(0, 208).addBox(-3.5F, -2.5F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.6721F, -0.5739F, 0.1892F, 0.0F, 3.1416F, -0.7854F));

		PartDefinition shoulder_bottom_r2 = left_arm2.addOrReplaceChild("shoulder_bottom_r2", CubeListBuilder.create().texOffs(24, 192).addBox(-1.5F, -0.5F, -3.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.23F)), PartPose.offsetAndRotation(0.6721F, -0.5739F, 0.1892F, 0.0F, 3.1416F, -0.1309F));

		PartDefinition shoulder_spike_r1 = left_arm2.addOrReplaceChild("shoulder_spike_r1", CubeListBuilder.create().texOffs(41, 251).addBox(-11.5F, 0.5F, 0.0F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.6721F, -0.5739F, 0.1892F, 0.0F, 3.1416F, -1.5708F));

		PartDefinition right_leg = rider.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-3.093F, 5.0F, -2.2568F));

		PartDefinition RightLeg_r1 = right_leg.addOrReplaceChild("RightLeg_r1", CubeListBuilder.create().texOffs(200, 16).addBox(-3.9F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.907F, 2.0F, -7.7432F, -1.0472F, 0.48F, 0.0F));

		PartDefinition armor = right_leg.addOrReplaceChild("armor", CubeListBuilder.create().texOffs(24, 153).addBox(0.193F, 7.0F, -0.7432F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F))
				.texOffs(29, 239).addBox(-0.807F, 16.0F, -0.7432F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(48, 225).addBox(-0.807F, 15.8F, -0.7432F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.4F))
				.texOffs(32, 202).addBox(0.093F, 15.0F, -1.2432F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(3.0F, -9.0F, 8.0F, -1.0472F, 0.48F, 0.0F));

		PartDefinition right_leg_armor_bottom_r1 = armor.addOrReplaceChild("right_leg_armor_bottom_r1", CubeListBuilder.create().texOffs(50, 153).addBox(-4.9F, -11.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.21F)), PartPose.offsetAndRotation(3.093F, 19.0F, 1.2568F, 0.0F, 0.0F, 0.0436F));

		PartDefinition right_leg_armor_r1 = armor.addOrReplaceChild("right_leg_armor_r1", CubeListBuilder.create().texOffs(40, 153).addBox(-5.9F, -13.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.22F)), PartPose.offsetAndRotation(3.093F, 19.0F, 1.2568F, 0.0F, 0.0F, 0.0873F));

		PartDefinition left_leg = rider.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(192, 48).addBox(-3.193F, -5.0F, 0.2568F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(3.093F, 5.0F, -2.2568F));

		PartDefinition LeftLeg_r1 = left_leg.addOrReplaceChild("LeftLeg_r1", CubeListBuilder.create().texOffs(200, 16).addBox(-0.1F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.907F, 2.0F, -7.7432F, -1.0472F, -0.48F, 0.0F));

		PartDefinition sword = right_arm2.addOrReplaceChild("sword", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition sword_r1 = sword.addOrReplaceChild("sword_r1", CubeListBuilder.create().texOffs(155, 56).addBox(1.0F, -16.0F, -1.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 21.0F, -10.0F, 0.6981F, 0.0F, 0.0F));

		PartDefinition armor2 = left_leg.addOrReplaceChild("armor2", CubeListBuilder.create().texOffs(24, 153).mirror().addBox(-25.193F, 7.0F, -0.7432F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false)
				.texOffs(29, 239).mirror().addBox(-25.193F, 16.0F, -0.7432F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(32, 202).mirror().addBox(-24.093F, 15.0F, -1.2432F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(48, 225).mirror().addBox(-25.193F, 15.8F, -0.7432F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offsetAndRotation(17.0F, -9.0F, 18.0F, -1.0472F, -0.48F, 0.0F));

		PartDefinition left_leg_armor_bottom_r1 = armor2.addOrReplaceChild("left_leg_armor_bottom_r1", CubeListBuilder.create().texOffs(50, 153).mirror().addBox(3.9F, -11.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.21F)).mirror(false), PartPose.offsetAndRotation(-25.093F, 19.0F, 1.2568F, 0.0F, 0.0F, -0.0436F));

		PartDefinition left_leg_armor_r1 = armor2.addOrReplaceChild("left_leg_armor_r1", CubeListBuilder.create().texOffs(40, 153).mirror().addBox(4.9F, -13.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offsetAndRotation(-25.093F, 19.0F, 1.2568F, 0.0F, 0.0F, -0.0873F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root.getAllParts().forEach(ModelPart::resetPose);

		this.animate(entity.idleAnimationState, HorsemanAnimation.idle, ageInTicks, 1f);
		this.animateWalk(HorsemanAnimation.walk, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(entity.attackAnimationState, HorsemanAnimation.attack, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public @NotNull ModelPart root() {
		return root;
	}
}