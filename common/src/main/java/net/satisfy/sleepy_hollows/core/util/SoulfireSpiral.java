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

public class SoulfireSpiral {
    private final Vec3 center;
    private final double y;
    private final int totalPoints = 800;
    private int currentPoint = 0;
    private final Level level;
    private final List<SoulfireBlock> activeSoulfireBlocks = new ArrayList<>();

    public SoulfireSpiral(Level level, Vec3 center) {
        this.level = level;
        this.center = center;
        this.y = center.y;
    }

    public boolean isFinished() {
        return currentPoint >= totalPoints;
    }

    public void tick() {
        int pointsPerTick = 5;

        for (int i = 0; i < pointsPerTick && currentPoint < totalPoints; i++, currentPoint++) {
            double angle = currentPoint * 0.05;
            double radius = 0.6 * angle * 0.67;
            double x = center.x + radius * Math.cos(angle);
            double z = center.z + radius * Math.sin(angle);
            double y = this.y + 0.3;

            BlockPos pos = BlockPos.containing(x, y, z);
            BlockPos belowPos = pos.below();

            if (level.isEmptyBlock(pos) && level.getBlockState(belowPos).canOcclude()) {
                level.setBlock(pos, Blocks.SOUL_FIRE.defaultBlockState(), 3);
                activeSoulfireBlocks.add(new SoulfireBlock(pos));
            }

            sendParticles(x, y, z);

            if (level instanceof ServerLevel serverLevel) {
                ignitePlayersNearby(serverLevel, x, y, z);
            }
        }

        cleanupSoulfireBlocks();
    }

    private void sendParticles(double x, double y, double z) {
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 2, 0.2, 0.2, 0.2, 0.05);
            serverLevel.sendParticles(ParticleTypes.ASH, x, y, z, 1, 0.2, 0.2, 0.2, 0.05);

            if (currentPoint % (totalPoints / 10) == 0) {
                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y + 1, z, 60, 1.0, 1.0, 1.0, 0.05);
            } else if (currentPoint % (totalPoints / 15) == 0) {
                serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y + 1, z, 50, 1.0, 1.0, 1.0, 0.05);
            } else if (currentPoint % (totalPoints / 5) == 0) {
                serverLevel.sendParticles(ParticleTypes.ASH, x, y + 1, z, 75, 1.0, 1.0, 1.0, 0.05);
            }
        }
    }

    private void ignitePlayersNearby(ServerLevel serverLevel, double x, double y, double z) {
        AABB detectionBox = new AABB(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5);
        List<Player> players = serverLevel.getEntitiesOfClass(Player.class, detectionBox, EntitySelector.NO_CREATIVE_OR_SPECTATOR);
        for (Player player : players) {
            if (player instanceof ServerPlayer serverPlayer && serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL) {
                serverPlayer.setSecondsOnFire(8);
            }
        }
    }

    private void cleanupSoulfireBlocks() {
        if (!level.isClientSide() && !activeSoulfireBlocks.isEmpty()) {
            Iterator<SoulfireBlock> iterator = activeSoulfireBlocks.iterator();
            while (iterator.hasNext()) {
                SoulfireBlock sfBlock = iterator.next();
                sfBlock.ticksRemaining--;
                if (sfBlock.ticksRemaining <= 0 && level.getBlockState(sfBlock.pos).getBlock() == Blocks.SOUL_FIRE) {
                    level.setBlock(sfBlock.pos, Blocks.AIR.defaultBlockState(), 3);
                    iterator.remove();
                }
            }
        }
    }

    private static class SoulfireBlock {
        BlockPos pos;
        int ticksRemaining = 30;

        SoulfireBlock(BlockPos pos) {
            this.pos = pos;
        }
    }
}
