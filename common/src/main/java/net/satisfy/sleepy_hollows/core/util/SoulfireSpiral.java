package net.satisfy.sleepy_hollows.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class SoulfireSpiral {
    private static final double ANGLE_INCREMENT = 0.05;
    private static final double RADIUS_MULTIPLIER = 0.6;
    private static final double RADIUS_FACTOR = 0.67;
    private static final double Y_OFFSET = 0.3;
    private static final float SOUL_FIRE_CHANCE = 0.4f;
    private static final int SOULFIRE_TICKS = 30;
    private static final int LARGE_SMOKE_PARTICLES = 100;
    private static final int TOTAL_POINTS = 1000;
    private static final int POINTS_PER_TICK = 5;
    private static final int UPDATE_ALL = 3;

    private final Vec3 center;
    private final double y;
    private int currentPoint;
    private final Level level;
    private final Queue<SoulfireBlock> activeSoulfireBlocks = new ArrayDeque<>();

    public SoulfireSpiral(Level level, Vec3 center) {
        this.level = level;
        this.center = center;
        this.y = center.y;
        this.currentPoint = 0;
    }

    public boolean isFinished() {
        return currentPoint >= TOTAL_POINTS;
    }

    public void tick() {
        for (int i = 0; i < POINTS_PER_TICK && currentPoint < TOTAL_POINTS; i++, currentPoint++) {
            double angle = currentPoint * ANGLE_INCREMENT;
            double radius = RADIUS_MULTIPLIER * angle * RADIUS_FACTOR;

            double x = center.x + radius * Math.cos(angle);
            double z = center.z + radius * Math.sin(angle);
            double y = this.y + Y_OFFSET;

            BlockPos pos = BlockPos.containing(x, y, z);
            BlockPos belowPos = pos.below();

            if (level instanceof ServerLevel serverLevel) {
                if (level.random.nextFloat() < SOUL_FIRE_CHANCE) {
                    if (level.isEmptyBlock(pos) && level.isEmptyBlock(belowPos)) {
                        level.setBlock(pos, Blocks.SOUL_FIRE.defaultBlockState(), UPDATE_ALL);
                        activeSoulfireBlocks.add(new SoulfireBlock(pos));
                    }
                }

                double particleX = x + (level.random.nextDouble() - 0.5);
                double particleY = y + (level.random.nextDouble() - 0.5);
                double particleZ = z + (level.random.nextDouble() - 0.5);

                spawnParticles(serverLevel, particleX, particleY, particleZ);

                AABB detectionBox = new AABB(
                        particleX - 0.5, particleY - 0.5, particleZ - 0.5,
                        particleX + 0.5, particleY + 0.5, particleZ + 0.5
                );

                List<Player> players = serverLevel.getEntitiesOfClass(
                        Player.class, detectionBox, EntitySelector.NO_SPECTATORS
                );

                for (Player player : players) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        GameType gameMode = serverPlayer.gameMode.getGameModeForPlayer();
                        if (gameMode == GameType.SURVIVAL || gameMode == GameType.ADVENTURE) {
                            serverPlayer.setSecondsOnFire(4);
                        }
                    }
                }

                if (currentPoint % (TOTAL_POINTS / 10) == 0) {
                    serverLevel.sendParticles(
                            ParticleTypes.LARGE_SMOKE, x, y + 1, z,
                            LARGE_SMOKE_PARTICLES, 1.0, 1.0, 1.0, 0.05
                    );
                }
            }
        }

        if (level instanceof ServerLevel) {
            if (!activeSoulfireBlocks.isEmpty()) {
                Iterator<SoulfireBlock> iterator = activeSoulfireBlocks.iterator();
                while (iterator.hasNext()) {
                    SoulfireBlock sfBlock = iterator.next();
                    sfBlock.ticksRemaining--;
                    if (sfBlock.ticksRemaining <= 0) {
                        if (level.getBlockState(sfBlock.pos).getBlock() == Blocks.SOUL_FIRE) {
                            level.setBlock(sfBlock.pos, Blocks.AIR.defaultBlockState(), UPDATE_ALL);
                        }
                        iterator.remove();
                    }
                }
            }
        }
    }

    private void spawnParticles(ServerLevel serverLevel, double x, double y, double z) {
        serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 2, 0.2, 0.2, 0.2, 0.05);
        serverLevel.sendParticles(ParticleTypes.ASH, x, y, z, 1, 0.2, 0.2, 0.2, 0.05);
    }

    private static class SoulfireBlock {
        BlockPos pos;
        int ticksRemaining;

        SoulfireBlock(BlockPos pos) {
            this.pos = pos;
            this.ticksRemaining = SOULFIRE_TICKS;
        }
    }
}
