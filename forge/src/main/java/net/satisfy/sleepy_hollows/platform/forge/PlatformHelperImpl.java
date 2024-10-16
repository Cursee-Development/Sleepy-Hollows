package net.satisfy.sleepy_hollows.platform.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.satisfy.sleepy_hollows.core.block.custom.LuminousWaterFluid;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;
import java.util.function.Supplier;

public class PlatformHelperImpl {
    public static void spawnHolyFluidParticles(Level level, BlockPos pos, FluidState state, RandomSource random, int particleIntensity) {
        if (random.nextInt(15 - Math.min(10, particleIntensity)) == 1) {
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .setTransparencyData(GenericParticleData.constrictTransparency(
                            GenericParticleData.create(1, 0)
                                    .setEasing(Easing.QUARTIC_IN)
                                    .build()
                    ))
                    .setColorData(ColorParticleData.create(Color.WHITE, Color.CYAN)
                            .setEasing(Easing.QUARTIC_IN).build())
                    .setScaleData(GenericParticleData.create(0.2f, 0.1f)
                            .setEasing(Easing.QUARTIC_IN).build())
                    .setLifetime(30 / particleIntensity)
                    .addMotion(0, 0.1 * particleIntensity, 0)
                    .spawn(level, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble());
        }
    }
}
