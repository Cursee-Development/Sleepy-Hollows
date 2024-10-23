package net.satisfy.sleepy_hollows.platform.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.satisfy.sleepy_hollows.core.entity.LingeringSoul;
import net.satisfy.sleepy_hollows.core.entity.ThrownLuminousWater;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;

public class LuminousWaterParticlesImpl {

    public static void spawnBlock(Level level, BlockPos pos, FluidState state, RandomSource random, int particleIntensity) {
        if (random.nextInt(15 - Math.min(10, particleIntensity)) == 1) {
            double fluidLevel = ((double) state.getAmount() / 8) * .875 + .2;
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .disableCull().setForceSpawn(true)
                    .setTransparencyData(GenericParticleData.create(.8f, 0)
                            .setEasing(Easing.QUARTIC_IN)
                            .build())
                    .setColorData(ColorParticleData.create(new Color(210, 240, 255), new Color(38, 154, 227))
                            .setEasing(Easing.QUARTIC_IN).build())
                    .setScaleData(GenericParticleData.create(.15f, .1f)
                            .setEasing(Easing.QUARTIC_IN).build())
                    .setLifetime(25 / particleIntensity)
                    .addMotion(0, .1 * particleIntensity, 0)
                    .spawn(level, pos.getX() + random.nextDouble(), pos.getY() + fluidLevel, pos.getZ() + random.nextDouble());
        }
    }

    public static void spawnItem(ThrownLuminousWater bottle, RandomSource random) {
        if (random.nextInt(10) == 1) {
            float offset1 = random.nextFloat() * .8f * (random.nextBoolean() ? 1 : -1);
            float offset2 = random.nextFloat() * .8f * (random.nextBoolean() ? 1 : -1);
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .disableCull().setForceSpawn(true)
                    .setTransparencyData(GenericParticleData.create(.8f, 0)
                            .setEasing(Easing.QUARTIC_IN)
                            .build())
                    .setColorData(ColorParticleData.create(new Color(210, 240, 255), new Color(38, 154, 227))
                            .setEasing(Easing.QUARTIC_IN).build())
                    .setScaleData(GenericParticleData.create(.1f, .05f)
                            .setEasing(Easing.QUARTIC_IN).build())
                    .setLifetime(12)
                    .addMotion(0, .2, 0)
                    .spawn(bottle.level(), bottle.position().x() + offset1, bottle.position().y(), bottle.position().z() + offset2);
        }
    }

    public static void createPreReincarnate(LingeringSoul soul, RandomSource random) {
        for (int i = 0; i < 26; i++) {
            float offset1 = random.nextFloat() * 3 * (random.nextBoolean() ? 1 : -1);
            float offset2 = random.nextFloat() * 3 * (random.nextBoolean() ? 1 : -1);
            float offset3 = random.nextFloat() * 3 * (random.nextBoolean() ? 1 : -1);
            Vec3 originalParticlePosition = new Vec3(
                    soul.position().x() + offset1,
                    soul.position().y() + offset2,
                    soul.position().z() + offset3
            );
            float originalSize = random.nextFloat() * .1f + .05f;
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                    .disableCull().setForceSpawn(true)
                    .setTransparencyData(GenericParticleData.create(.0f, .8f)
                            .setEasing(Easing.QUAD_IN, Easing.QUINTIC_OUT)
                            .setCoefficient(3F)
                            .build())
                    .setColorData(ColorParticleData.create(new Color(38, 154, 227)).build())
                    .setScaleData(GenericParticleData.create(originalSize, .05f)
                            .setEasing(Easing.QUINTIC_IN).build())
                    .setLifetime(80)
                    .addTickActor(particle -> {
                        double progress = Easing.QUINTIC_IN.ease(
                                (float) particle.getAge(), 0, 1,
                                (float) particle.getLifetime()
                        );
                        Vec3 newPos = originalParticlePosition.lerp(soul.position(), progress);
                        particle.move(newPos.x, newPos.y, newPos.z);
                    })
                    .spawn(soul.level(), originalParticlePosition.x, originalParticlePosition.y, originalParticlePosition.z);
        }
    }
}
