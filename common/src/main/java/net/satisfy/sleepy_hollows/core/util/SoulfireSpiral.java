package net.satisfy.sleepy_hollows.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SoulfireSpiral {
    private static final int TOTAL_POINTS = 320;
    private static final int POINTS_PER_TICK = 4;
    private static final int BURN_TICKS = 40;
    private static final double STEP = 0.12;
    private static final double GROWTH = 0.4;
    private static final int SEARCH_UP = 4;
    private static final int SEARCH_DOWN = 6;

    private final ServerLevel level;
    private final Vec3 center;
    private final List<HotSpot> hotSpots = new ArrayList<>();
    private int currentPoint;

    public SoulfireSpiral(ServerLevel level, Vec3 center) {
        this.level = level;
        this.center = center;
    }

    public boolean isFinished() {
        return currentPoint >= TOTAL_POINTS && hotSpots.isEmpty();
    }

    public void tick() {
        for (int i = 0; i < POINTS_PER_TICK && currentPoint < TOTAL_POINTS; i++, currentPoint++) {
            double angle = currentPoint * STEP;
            double radius = 1.0 + GROWTH * angle;
            double x = center.x + radius * Math.cos(angle);
            double z = center.z + radius * Math.sin(angle);
            Vec3 surface = findSurface(x, z);
            if (surface == null) continue;
            hotSpots.add(new HotSpot(surface));
            level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, surface.x, surface.y + 0.1, surface.z, 6, 0.2, 0.3, 0.2, 0.02);
            level.sendParticles(ParticleTypes.LARGE_SMOKE, surface.x, surface.y + 0.4, surface.z, 1, 0.1, 0.2, 0.1, 0.01);
            if (currentPoint % 16 == 0) {
                level.playSound(null, surface.x, surface.y, surface.z, SoundEvents.SOUL_ESCAPE.value(), SoundSource.HOSTILE, 0.8F, 0.6F + level.random.nextFloat() * 0.4F);
            }
        }

        hotSpots.removeIf(spot -> {
            if (--spot.ticks <= 0) return true;
            if (spot.ticks % 4 == 0) {
                level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, spot.pos.x, spot.pos.y + 0.1, spot.pos.z, 1, 0.2, 0.15, 0.2, 0.01);
            }
            burnPlayers(spot.pos);
            return false;
        });
    }

    private @Nullable Vec3 findSurface(double x, double z) {
        BlockPos.MutableBlockPos pos = BlockPos.containing(x, center.y + SEARCH_UP, z).mutable();
        for (int i = 0; i <= SEARCH_UP + SEARCH_DOWN; i++, pos.move(Direction.DOWN)) {
            BlockState state = level.getBlockState(pos);
            BlockState below = level.getBlockState(pos.below());
            boolean passable = state.isAir() || state.canBeReplaced() || state.getCollisionShape(level, pos).isEmpty();
            if (passable && below.isFaceSturdy(level, pos.below(), Direction.UP)) {
                return new Vec3(x, pos.getY(), z);
            }
        }
        return null;
    }

    private void burnPlayers(Vec3 pos) {
        AABB area = new AABB(pos.x - 0.7, pos.y - 0.5, pos.z - 0.7, pos.x + 0.7, pos.y + 2.0, pos.z + 0.7);
        for (Player player : level.getEntitiesOfClass(Player.class, area, player -> !player.isCreative() && !player.isSpectator())) {
            player.igniteForSeconds(5);
            player.hurt(level.damageSources().inFire(), 3.0F);
        }
    }

    private static final class HotSpot {
        private final Vec3 pos;
        private int ticks = BURN_TICKS;

        private HotSpot(Vec3 pos) {
            this.pos = pos;
        }
    }
}
