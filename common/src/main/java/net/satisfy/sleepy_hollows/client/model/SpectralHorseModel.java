package net.satisfy.sleepy_hollows.client.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public class SpectralHorseModel<T extends AbstractHorse> extends AgeableListModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new SleepyHollowsIdentifier("spectral_horse"), "main");

	private static final float DEG_125 = 2.1816616F;
	private static final float DEG_60 = 1.0471976F;
	private static final float DEG_45 = 0.7853982F;
	private static final float DEG_30 = 0.5235988F;
	private static final float DEG_15 = 0.2617994F;
	protected static final String HEAD_PARTS = "head_parts";
	private static final String LEFT_HIND_BABY_LEG = "left_hind_baby_leg";
	private static final String RIGHT_HIND_BABY_LEG = "right_hind_baby_leg";
	private static final String LEFT_FRONT_BABY_LEG = "left_front_baby_leg";
	private static final String RIGHT_FRONT_BABY_LEG = "right_front_baby_leg";
	private static final String SADDLE = "saddle";
	private static final String LEFT_SADDLE_MOUTH = "left_saddle_mouth";
	private static final String LEFT_SADDLE_LINE = "left_saddle_line";
	private static final String RIGHT_SADDLE_MOUTH = "right_saddle_mouth";
	private static final String RIGHT_SADDLE_LINE = "right_saddle_line";
	private static final String HEAD_SADDLE = "head_saddle";
	private static final String MOUTH_SADDLE_WRAP = "mouth_saddle_wrap";
	protected final ModelPart body;
	protected final ModelPart headParts;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightHindBabyLeg;
	private final ModelPart leftHindBabyLeg;
	private final ModelPart rightFrontBabyLeg;
	private final ModelPart leftFrontBabyLeg;
	private final ModelPart tail;
	private final ModelPart[] saddleParts;
	private final ModelPart[] ridingParts;


	public SpectralHorseModel(ModelPart modelPart) {

		super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
		this.body = modelPart.getChild("body");
		this.headParts = modelPart.getChild("head_parts");
		this.rightHindLeg = modelPart.getChild("right_hind_leg");
		this.leftHindLeg = modelPart.getChild("left_hind_leg");
		this.rightFrontLeg = modelPart.getChild("right_front_leg");
		this.leftFrontLeg = modelPart.getChild("left_front_leg");
		this.rightHindBabyLeg = modelPart.getChild("right_hind_baby_leg");
		this.leftHindBabyLeg = modelPart.getChild("left_hind_baby_leg");
		this.rightFrontBabyLeg = modelPart.getChild("right_front_baby_leg");
		this.leftFrontBabyLeg = modelPart.getChild("left_front_baby_leg");
		this.tail = this.body.getChild("tail");
		ModelPart modelPart2 = this.body.getChild("saddle");
		ModelPart modelPart3 = this.headParts.getChild("left_saddle_mouth");
		ModelPart modelPart4 = this.headParts.getChild("right_saddle_mouth");
		ModelPart modelPart5 = this.headParts.getChild("left_saddle_line");
		ModelPart modelPart6 = this.headParts.getChild("right_saddle_line");
		ModelPart modelPart7 = this.headParts.getChild("head_saddle");
		ModelPart modelPart8 = this.headParts.getChild("mouth_saddle_wrap");
		this.saddleParts = new ModelPart[]{modelPart2, modelPart3, modelPart4, modelPart7, modelPart8};
		this.ridingParts = new ModelPart[]{modelPart5, modelPart6};

	}

	public static LayerDefinition getTexturedModelData() {
		CubeDeformation cubeDeformation = new CubeDeformation(0.0F);
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition partDefinition2 = partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 11.0F, 5.0F));
		PartDefinition additional_armor = partDefinition2.addOrReplaceChild("additional_armor", CubeListBuilder.create().texOffs(0, 64).addBox(-5.0F, -21.0F, -11.0F, 10.0F, 16.0F, 22.0F, new CubeDeformation(0.2F))
				.texOffs(0, 104).addBox(-5.0F, -21.0F, -11.0F, 10.0F, 15.0F, 9.0F, new CubeDeformation(0.25F))
				.texOffs(64, 54).addBox(-5.0F, -21.0F, 4.0F, 10.0F, 16.0F, 10.0F, new CubeDeformation(0.25F))
				.texOffs(32, 79).addBox(-6.0F, -26.0F, 3.99F, 12.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 74).addBox(-4.0F, -29.0F, 3.99F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(32, 69).addBox(-5.0F, -32.0F, 3.99F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, -9.0F));

		PartDefinition saddle_decoration_r1 = additional_armor.addOrReplaceChild("saddle_decoration_r1", CubeListBuilder.create().texOffs(55, 0).addBox(-10.0F, -34.0F, 1.0F, 20.0F, 13.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 64).addBox(-5.0F, -19.0F, -16.0F, 10.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition armor_body_front_decoration_left_r1 = additional_armor.addOrReplaceChild("armor_body_front_decoration_left_r1", CubeListBuilder.create().texOffs(56, 4).addBox(-5.0F, -28.0F, 5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(56, 4).mirror().addBox(-5.0F, -28.0F, 21.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -16.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition armor_body_front_decoration_right_r1 = additional_armor.addOrReplaceChild("armor_body_front_decoration_right_r1", CubeListBuilder.create().texOffs(56, 4).addBox(5.0F, -28.0F, 5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(56, 4).mirror().addBox(5.0F, -28.0F, 21.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -16.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition armor_body_front_r1 = additional_armor.addOrReplaceChild("armor_body_front_r1", CubeListBuilder.create().texOffs(38, 107).addBox(-5.0F, -20.0F, -14.0F, 10.0F, 15.0F, 6.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));


		PartDefinition partDefinition3 = partDefinition.addOrReplaceChild("head_parts", CubeListBuilder.create().texOffs(0, 35).addBox(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F), PartPose.offsetAndRotation(0.0F, 4.0F, -12.0F, 0.5235988F, 0.0F, 0.0F));


		PartDefinition partDefinition4 = partDefinition3.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, cubeDeformation), PartPose.ZERO);

		PartDefinition additional_armor_head = partDefinition4.addOrReplaceChild("additional_armor_head", CubeListBuilder.create().texOffs(66, 42).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.1F))
				.texOffs(64, 82).addBox(-2.0F, -5.0F, -11.0F, 4.0F, 8.0F, 5.0F, new CubeDeformation(0.1F))
				.texOffs(32, 66).addBox(-0.5F, -7.1534F, -8.4088F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(32, 66).addBox(-0.5F, -7.1534F, -3.4088F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 5.0F));

		partDefinition3.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(56, 36).addBox(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, cubeDeformation), PartPose.ZERO);

		partDefinition3.addOrReplaceChild("upper_mouth", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, cubeDeformation), PartPose.ZERO);
		partDefinition.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, cubeDeformation), PartPose.offset(4.0F, 14.0F, 7.0F));
		partDefinition.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, cubeDeformation), PartPose.offset(-4.0F, 14.0F, 7.0F));
		partDefinition.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, cubeDeformation), PartPose.offset(4.0F, 14.0F, -12.0F));
		partDefinition.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, cubeDeformation), PartPose.offset(-4.0F, 14.0F, -12.0F));
		CubeDeformation cubeDeformation2 = cubeDeformation.extend(0.0F, 5.5F, 0.0F);
		partDefinition.addOrReplaceChild("left_hind_baby_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, cubeDeformation2), PartPose.offset(4.0F, 14.0F, 7.0F));
		partDefinition.addOrReplaceChild("right_hind_baby_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, cubeDeformation2), PartPose.offset(-4.0F, 14.0F, 7.0F));
		partDefinition.addOrReplaceChild("left_front_baby_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, cubeDeformation2), PartPose.offset(4.0F, 14.0F, -12.0F));
		partDefinition.addOrReplaceChild("right_front_baby_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, cubeDeformation2), PartPose.offset(-4.0F, 14.0F, -12.0F));
		partDefinition2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(42, 36).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, cubeDeformation), PartPose.offsetAndRotation(0.0F, -5.0F, 2.0F, 0.5235988F, 0.0F, 0.0F));
		partDefinition2.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(26, 0).addBox(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
		partDefinition3.addOrReplaceChild("left_saddle_mouth", CubeListBuilder.create().texOffs(29, 5).addBox(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, cubeDeformation), PartPose.ZERO);
		partDefinition3.addOrReplaceChild("right_saddle_mouth", CubeListBuilder.create().texOffs(29, 5).addBox(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, cubeDeformation), PartPose.ZERO);
		partDefinition3.addOrReplaceChild("left_saddle_line", CubeListBuilder.create().texOffs(32, 2).addBox(3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F), PartPose.rotation(-0.5235988F, 0.0F, 0.0F));
		partDefinition3.addOrReplaceChild("right_saddle_line", CubeListBuilder.create().texOffs(32, 2).addBox(-3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F), PartPose.rotation(-0.5235988F, 0.0F, 0.0F));
		partDefinition3.addOrReplaceChild("head_saddle", CubeListBuilder.create().texOffs(1, 1).addBox(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.22F)), PartPose.ZERO);
		partDefinition3.addOrReplaceChild("mouth_saddle_wrap", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.ZERO);
		partDefinition4.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(19, 16).addBox(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.ZERO);
		partDefinition4.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(19, 16).addBox(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.ZERO);

		return LayerDefinition.create(meshDefinition, 128, 128);
	}

	public void setupAnim(T abstractHorse, float f, float g, float h, float i, float j) {
		boolean bl = abstractHorse.isSaddled();
		boolean bl2 = abstractHorse.isVehicle();
		ModelPart[] var9 = this.saddleParts;
		int var10 = var9.length;

		int var11;
		ModelPart modelPart;
		for (var11 = 0; var11 < var10; ++var11) {
			modelPart = var9[var11];
			modelPart.visible = bl;
		}

		var9 = this.ridingParts;
		var10 = var9.length;

		for (var11 = 0; var11 < var10; ++var11) {
			modelPart = var9[var11];
			modelPart.visible = bl2 && bl;
		}

		this.body.y = 11.0F;
	}

	public @NotNull Iterable<ModelPart> headParts() {
		return ImmutableList.of(this.headParts);
	}

	@Override
	protected @NotNull Iterable<ModelPart> bodyParts() {
		return ImmutableList.of(this.body, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.rightHindBabyLeg, this.leftHindBabyLeg, this.rightFrontBabyLeg, this.leftFrontBabyLeg);
	}

	//TODO @Jason these are just the horse animations - maybe we can refine these a bit?
	@Override
	public void prepareMobModel(@NotNull T abstractHorse, float limbSwing, float limbSwingAmount, float partialTick) {

		// super.prepareMobModel(abstractHorse, limbSwing, limbSwingAmount, partialTick); // unnecessary, no implementation in super

		this.bodyParts().forEach(ModelPart::resetPose);
		this.headParts().forEach(ModelPart::resetPose);

		// rotLerp returns a float between 0.0 and 360.0 that is between the given points
		float yBodyLerpedRotationDegrees = Mth.rotLerp(partialTick, abstractHorse.yBodyRotO, abstractHorse.yBodyRot);
		float yHeadLerpedRotationDegrees = Mth.rotLerp(partialTick, abstractHorse.yHeadRotO, abstractHorse.yHeadRot);
		float xEntityLerpedRotationDegrees = Mth.lerp(partialTick, abstractHorse.xRotO, abstractHorse.getXRot());
		float headRotToBodyRotDifferenceDegrees = yHeadLerpedRotationDegrees - yBodyLerpedRotationDegrees;

		float xEntityLerpedRotationRadians = xEntityLerpedRotationDegrees * 0.017453292F; // converting from degrees to radians

		if (headRotToBodyRotDifferenceDegrees > 20.0F) {
			headRotToBodyRotDifferenceDegrees = 20.0F;
		}

		if (headRotToBodyRotDifferenceDegrees < -20.0F) {
			headRotToBodyRotDifferenceDegrees = -20.0F;
		}

		if (limbSwingAmount > 0.2F) {
			xEntityLerpedRotationRadians += Mth.cos(limbSwing * 0.8F) * 0.15F * limbSwingAmount;
		}

		float eatingAnimationNextValue = abstractHorse.getEatAnim(partialTick);
		float standingAnimationNextValue = abstractHorse.getStandAnim(partialTick);
		float standingAnimationOffset = 1.0F - standingAnimationNextValue;
		float mouthAnimationNextValue = abstractHorse.getMouthAnim(partialTick);
		boolean movingTail = abstractHorse.tailCounter != 0;
		float tickCountNextValue = (float) abstractHorse.tickCount + partialTick;

		// resets??
		this.headParts.y = 4.0F;
		this.headParts.z = -12.0F;
		this.body.xRot = 0.0F;

		this.headParts.xRot = 0.5235988F + xEntityLerpedRotationRadians;
		this.headParts.yRot = headRotToBodyRotDifferenceDegrees * 0.017453292F;

		float notSwimmingAnimationDampener = abstractHorse.isInWater() ? 0.2F : 1.0F;
		float legSwingAnimationNextValueMultiplier = Mth.cos(notSwimmingAnimationDampener * limbSwing * 0.6662F + 3.1415927F);
		float legSwingAnimationNextValue = legSwingAnimationNextValueMultiplier * 0.8F * limbSwingAmount;
		float calculateHeadAdditionalRotation = (1.0F - Math.max(standingAnimationNextValue, eatingAnimationNextValue)) * (0.5235988F + xEntityLerpedRotationRadians + mouthAnimationNextValue * Mth.sin(tickCountNextValue) * 0.05F);

		this.headParts.xRot = standingAnimationNextValue * (0.2617994F + xEntityLerpedRotationRadians) + eatingAnimationNextValue * (2.1816616F + Mth.sin(tickCountNextValue) * 0.05F) + calculateHeadAdditionalRotation;
		this.headParts.yRot = standingAnimationNextValue * headRotToBodyRotDifferenceDegrees * 0.017453292F + (1.0F - Math.max(standingAnimationNextValue, eatingAnimationNextValue)) * this.headParts.yRot;
		this.headParts.y = standingAnimationNextValue * -4.0F + eatingAnimationNextValue * 11.0F + (1.0F - Math.max(standingAnimationNextValue, eatingAnimationNextValue)) * this.headParts.y;
		this.headParts.z = standingAnimationNextValue * -4.0F + eatingAnimationNextValue * -12.0F + (1.0F - Math.max(standingAnimationNextValue, eatingAnimationNextValue)) * this.headParts.z;
		this.body.xRot = standingAnimationNextValue * -0.7853982F + standingAnimationOffset * this.body.xRot;

		float updatedAnimationAmountWithStandingAnim = 0.2617994F * standingAnimationNextValue; // roughly multiplier by pi/12 in radians or 15 degrees
		float smoothingValue = Mth.cos(tickCountNextValue * 0.6F + 3.1415927F);

		this.leftFrontLeg.y = 2.0F * standingAnimationNextValue + 14.0F * standingAnimationOffset;
		this.leftFrontLeg.z = -6.0F * standingAnimationNextValue - 10.0F * standingAnimationOffset;
		this.rightFrontLeg.y = this.leftFrontLeg.y;
		this.rightFrontLeg.z = this.leftFrontLeg.z;

		float leftFrontLegNewRotation = (-1.0471976F + smoothingValue) * standingAnimationNextValue + legSwingAnimationNextValue * standingAnimationOffset;
		float rightFrontLegNewRotation = (-1.0471976F - smoothingValue) * standingAnimationNextValue - legSwingAnimationNextValue * standingAnimationOffset;

		this.leftHindLeg.xRot = updatedAnimationAmountWithStandingAnim - legSwingAnimationNextValueMultiplier * 0.5F * limbSwingAmount * standingAnimationOffset;
		this.rightHindLeg.xRot = updatedAnimationAmountWithStandingAnim + legSwingAnimationNextValueMultiplier * 0.5F * limbSwingAmount * standingAnimationOffset;
		this.leftFrontLeg.xRot = leftFrontLegNewRotation;
		this.rightFrontLeg.xRot = rightFrontLegNewRotation;
		this.tail.xRot = 0.5235988F + limbSwingAmount * 0.75F;
		this.tail.y = -5.0F + limbSwingAmount;
		this.tail.z = 2.0F + limbSwingAmount * 2.0F;

		if (movingTail) {
			this.tail.yRot = Mth.cos(tickCountNextValue * 0.7F);
		} else {
			this.tail.yRot = 0.0F;
		}

		this.rightHindBabyLeg.y = this.rightHindLeg.y;
		this.rightHindBabyLeg.z = this.rightHindLeg.z;
		this.rightHindBabyLeg.xRot = this.rightHindLeg.xRot;
		this.leftHindBabyLeg.y = this.leftHindLeg.y;
		this.leftHindBabyLeg.z = this.leftHindLeg.z;
		this.leftHindBabyLeg.xRot = this.leftHindLeg.xRot;
		this.rightFrontBabyLeg.y = this.rightFrontLeg.y;
		this.rightFrontBabyLeg.z = this.rightFrontLeg.z;
		this.rightFrontBabyLeg.xRot = this.rightFrontLeg.xRot;
		this.leftFrontBabyLeg.y = this.leftFrontLeg.y;
		this.leftFrontBabyLeg.z = this.leftFrontLeg.z;
		this.leftFrontBabyLeg.xRot = this.leftFrontLeg.xRot;

		boolean isBabyHorse = abstractHorse.isBaby();
		this.rightHindLeg.visible = !isBabyHorse;
		this.leftHindLeg.visible = !isBabyHorse;
		this.rightFrontLeg.visible = !isBabyHorse;
		this.leftFrontLeg.visible = !isBabyHorse;
		this.rightHindBabyLeg.visible = isBabyHorse;
		this.leftHindBabyLeg.visible = isBabyHorse;
		this.rightFrontBabyLeg.visible = isBabyHorse;
		this.leftFrontBabyLeg.visible = isBabyHorse;
		this.body.y = isBabyHorse ? 10.8F : 0.0F;
	}
}
