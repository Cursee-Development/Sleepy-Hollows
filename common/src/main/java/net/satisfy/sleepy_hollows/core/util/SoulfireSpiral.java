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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SoulfireSpiral {
    private final Vec3 center;
    private final double y;
    private final int totalPoints;
    private int currentPoint;
    private final Level level;
    private final List<SoulfireBlock> activeSoulfireBlocks = new ArrayList<>();
    private final Random random = new Random();

    public SoulfireSpiral(Level level, Vec3 center) {
        this.level = level;
        this.center = center;
        this.y = center.y;
        this.totalPoints = 1000;
        this.currentPoint = 0;
    }

    public boolean isFinished() {
        return currentPoint >= totalPoints;
    }

    public void tick() {
        int pointsPerTick = 5;

        for (int i = 0; i < pointsPerTick && currentPoint < totalPoints; i++, currentPoint++) {
            double angleIncrement = 0.05;
            double angle = currentPoint * angleIncrement;
            double radius = 0.6 * angle * 0.67;

            double x = center.x + radius * Math.cos(angle);
            double z = center.z + radius * Math.sin(angle);
            double y = this.y + 1;

            BlockPos pos = BlockPos.containing(x, y, z);
            BlockPos belowPos = pos.below();

            if (!level.isClientSide()) {
                if (random.nextFloat() < 0.4f) {
                    if (level.isEmptyBlock(pos) && level.isEmptyBlock(belowPos)) {
                        level.setBlock(pos, Blocks.SOUL_FIRE.defaultBlockState(), 3);
                        activeSoulfireBlocks.add(new SoulfireBlock(pos));
                    }
                }

                if (level instanceof ServerLevel serverLevel) {
                    double particleX = x + (level.random.nextDouble() - 0.5);
                    double particleY = y + (level.random.nextDouble() - 0.5);
                    double particleZ = z + (level.random.nextDouble() - 0.5);
                    serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, particleX, particleY, particleZ, 3, 0.2, 0.2, 0.2, 0.05);
                    serverLevel.sendParticles(ParticleTypes.ASH, particleX, particleY, particleZ, 2, 0.2, 0.2, 0.2, 0.05);

                    AABB detectionBox = new AABB(particleX - 0.5, particleY - 0.5, particleZ - 0.5, particleX + 0.5, particleY + 0.5, particleZ + 0.5);
                    List<Player> players = serverLevel.getEntitiesOfClass(Player.class, detectionBox, EntitySelector.NO_SPECTATORS);
                    for (Player player : players) {
                        if (player instanceof ServerPlayer serverPlayer && serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL) {
                            serverPlayer.setSecondsOnFire(4);
                        }
                    }
                }
            }

            if (currentPoint % (totalPoints / 10) == 0) {
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y + 1, z, 100, 1.0, 1.0, 1.0, 0.05);
                }
            }
        }

        if (!level.isClientSide()) {
            if (!activeSoulfireBlocks.isEmpty()) {
                Iterator<SoulfireBlock> iterator = activeSoulfireBlocks.iterator();
                while (iterator.hasNext()) {
                    SoulfireBlock sfBlock = iterator.next();
                    sfBlock.ticksRemaining--;
                    if (sfBlock.ticksRemaining <= 0) {
                        if (level.getBlockState(sfBlock.pos).getBlock() == Blocks.SOUL_FIRE) {
                            level.setBlock(sfBlock.pos, Blocks.AIR.defaultBlockState(), 3);
                        }
                        iterator.remove();
                    }
                }
            }
        }
    }

    private static class SoulfireBlock {
        BlockPos pos;
        int ticksRemaining;

        SoulfireBlock(BlockPos pos) {
            this.pos = pos;
            this.ticksRemaining = 30;
        }
    }
}
