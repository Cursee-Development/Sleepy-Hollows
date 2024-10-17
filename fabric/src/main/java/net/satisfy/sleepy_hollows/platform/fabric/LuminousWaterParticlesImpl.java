package net.satisfy.sleepy_hollows.platform.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
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

    }
}
