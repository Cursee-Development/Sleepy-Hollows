package net.satisfy.sleepy_hollows.core.world.placers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.satisfy.sleepy_hollows.core.registry.FeatureTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class HollowFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<HollowFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance).and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter(p -> p.trunkHeight)).apply(instance, HollowFoliagePlacer::new));
    private final IntProvider trunkHeight;

    public HollowFoliagePlacer(IntProvider intProvider, IntProvider intProvider2, IntProvider trunkHeight) {
        super(intProvider, intProvider2);
        this.trunkHeight = trunkHeight;
    }

    @Override
    protected @NotNull FoliagePlacerType<?> type() {
        return FeatureTypeRegistry.HOLLOW_FOLIAGE_PLACER.get();
    }

    public int foliageHeight(@NotNull RandomSource random, int trunkHeight, @NotNull TreeConfiguration config) {
        return Math.max(12, trunkHeight - this.trunkHeight.sample(random));
    }

    @Override
    protected void createFoliage(@NotNull LevelSimulatedReader levelSimulatedReader, @NotNull FoliageSetter foliageSetter, RandomSource random, @NotNull TreeConfiguration config, int trunkHeight, FoliageAttachment treeNode, int foliageHeight, int radius, int offset) {
        BlockPos blockPos = treeNode.pos();
        BlockPos.MutableBlockPos mutable = blockPos.mutable();
        boolean nextBoolean = random.nextBoolean();
        boolean nextBoolean2 = random.nextBoolean();
        for (int l = offset; l >= -foliageHeight - 2; --l) {
            if (l >= offset - 2) {
                mutable.setWithOffset(blockPos, 0, l, 0);
                if ((nextBoolean && !nextBoolean2) && l == offset) {
                    tryPlaceLeaf(levelSimulatedReader, foliageSetter, random, config, mutable);
                }
                if ((nextBoolean || nextBoolean2) && l == offset - 1) {
                    tryPlaceLeaf(levelSimulatedReader, foliageSetter, random, config, mutable);
                }
                if (l == offset - 2) {
                    tryPlaceLeaf(levelSimulatedReader, foliageSetter, random, config, mutable);
                }
            } else if (l == offset - 3) {
                mutable.setWithOffset(blockPos, 0, l, 0);
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.getRandom(random), Direction.getRandom(random), 1);
                }
                this.placeLeavesRow(levelSimulatedReader, foliageSetter, random, config, blockPos, 1, l, treeNode.doubleTrunk());
            } else if (l == offset - 4) {
                mutable.setWithOffset(blockPos, 0, l, 0);
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.EAST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.WEST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.EAST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.WEST, 0);
                }
                this.placeLeavesRow(levelSimulatedReader, foliageSetter, random, config, blockPos, 1, l, treeNode.doubleTrunk());
            } else if (l == offset - 5) {
                mutable.setWithOffset(blockPos, 0, l, 0);
                if (random.nextBoolean()) {
                    Direction m = random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
                    Direction n = random.nextBoolean() ? Direction.EAST : Direction.WEST;
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, m, n, 0);
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, m.getOpposite(), n.getOpposite(), 0);
                }
                if (random.nextBoolean()) {
                    Direction m = random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
                    Direction n = random.nextBoolean() ? Direction.EAST : Direction.WEST;
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, m, n, 0);
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, m.getOpposite(), n.getOpposite(), 0);
                }
            } else if (l == offset - 6) {
                mutable.setWithOffset(blockPos, 0, l, 0);
                if (random.nextBoolean()) {
                    Direction m = random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
                    Direction n = random.nextBoolean() ? Direction.EAST : Direction.WEST;
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, m, n, 1);
                }
                this.placeLeavesRow(levelSimulatedReader, foliageSetter, random, config, blockPos, 1, l, treeNode.doubleTrunk());
            } else if (l == offset - 7) {
                mutable.setWithOffset(blockPos, 0, l, 0);
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.EAST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.WEST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.EAST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.WEST, 0);
                }
                this.placeLeavesRow(levelSimulatedReader, foliageSetter, random, config, blockPos, 1, l, treeNode.doubleTrunk());
            } else if (l == offset - 8) {
                mutable.setWithOffset(blockPos, 0, l, 0);
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.EAST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.WEST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.EAST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.WEST, 0);
                }
                placeLineConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH);
                placeLineConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.EAST);
                placeLineConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH);
                placeLineConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.WEST);
                this.placeLeavesRow(levelSimulatedReader, foliageSetter, random, config, blockPos, 1, l, treeNode.doubleTrunk());
            } else if (l == offset - 9) {
                mutable.setWithOffset(blockPos, 0, l, 0);
                if (random.nextBoolean()) {
                    Direction m = random.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
                    Direction n = random.nextBoolean() ? Direction.EAST : Direction.WEST;
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, m, n, 1);
                }
                this.placeLeavesRow(levelSimulatedReader, foliageSetter, random, config, blockPos, 1, l, treeNode.doubleTrunk());
            } else if (l == offset - 10) {
                mutable.setWithOffset(blockPos, 0, l, 0);
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.EAST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.WEST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.EAST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.WEST, 0);
                }
                placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.EAST, 0, 2, 1);
                placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.WEST, 0, 2, 1);
                placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.EAST, Direction.NORTH, 0, 2, 1);
                placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.EAST, Direction.SOUTH, 0, 2, 1);
                placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.EAST, 0, 2, 1);
                placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.WEST, 0, 2, 1);
                placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.WEST, Direction.NORTH, 0, 2, 1);
                placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.WEST, Direction.SOUTH, 0, 2, 1);
                this.placeLeavesRow(levelSimulatedReader, foliageSetter, random, config, blockPos, 1, l, treeNode.doubleTrunk());
            } else if (l == offset - 11) {
                mutable.setWithOffset(blockPos, 0, l, 0);
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.EAST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.NORTH, Direction.WEST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.EAST, 0);
                }
                if (random.nextBoolean()) {
                    placeWithConnection(levelSimulatedReader, foliageSetter, random, config, mutable, Direction.SOUTH, Direction.WEST, 0);
                }
            }
        }
    }

    private void placeWithConnection(@NotNull LevelSimulatedReader level, @NotNull FoliageSetter setter, @NotNull RandomSource random, @NotNull TreeConfiguration config, @NotNull BlockPos.MutableBlockPos origin, Direction a, Direction b, int yOffset) {
        BlockPos.MutableBlockPos center = new BlockPos.MutableBlockPos().setWithOffset(origin, 0, yOffset, 0);
        tryPlaceLeaf(level, setter, random, config, center);
        BlockPos.MutableBlockPos posA = new BlockPos.MutableBlockPos().setWithOffset(origin, 0, yOffset, 0);
        posA.move(a, 1);
        tryPlaceLeaf(level, setter, random, config, posA);
        BlockPos.MutableBlockPos posB = new BlockPos.MutableBlockPos().setWithOffset(origin, 0, yOffset, 0);
        posB.move(b, 1);
        tryPlaceLeaf(level, setter, random, config, posB);
        BlockPos.MutableBlockPos diag = new BlockPos.MutableBlockPos().setWithOffset(origin, 0, yOffset, 0);
        diag.move(a, 1);
        diag.move(b, 1);
        tryPlaceLeaf(level, setter, random, config, diag);
    }

    private void placeWithConnection(@NotNull LevelSimulatedReader level, @NotNull FoliageSetter setter, @NotNull RandomSource random, @NotNull TreeConfiguration config, @NotNull BlockPos.MutableBlockPos origin, Direction a, Direction b, int yOffset, int distAB, int distOrth) {
        BlockPos.MutableBlockPos diag = new BlockPos.MutableBlockPos().setWithOffset(origin, 0, yOffset, 0);
        diag.move(a, distAB);
        diag.move(b, distOrth);
        tryPlaceLeaf(level, setter, random, config, diag);
        for (int i = 1; i <= distAB; i++) {
            BlockPos.MutableBlockPos step = new BlockPos.MutableBlockPos().setWithOffset(origin, 0, yOffset, 0);
            step.move(a, i);
            tryPlaceLeaf(level, setter, random, config, step);
        }
        BlockPos.MutableBlockPos orth = new BlockPos.MutableBlockPos().setWithOffset(origin, 0, yOffset, 0);
        orth.move(b, 1);
        tryPlaceLeaf(level, setter, random, config, orth);
    }

    private void placeLineConnection(@NotNull LevelSimulatedReader level, @NotNull FoliageSetter setter, @NotNull RandomSource random, @NotNull TreeConfiguration config, @NotNull BlockPos.MutableBlockPos origin, Direction dir) {
        BlockPos.MutableBlockPos end = new BlockPos.MutableBlockPos().setWithOffset(origin, 0, 0, 0);
        end.move(dir, 2);
        tryPlaceLeaf(level, setter, random, config, end);
        for (int i = 1; i < 2; i++) {
            BlockPos.MutableBlockPos step = new BlockPos.MutableBlockPos().setWithOffset(origin, 0, 0, 0);
            step.move(dir, i);
            tryPlaceLeaf(level, setter, random, config, step);
        }
    }

    @Override
    protected boolean shouldSkipLocation(@NotNull RandomSource random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return dx == radius && dz == radius && radius > 0;
    }

    protected void placeLeavesRow(@NotNull LevelSimulatedReader levelSimulatedReader, @NotNull FoliageSetter foliageSetter, @NotNull RandomSource random, @NotNull TreeConfiguration config, @NotNull BlockPos centerPos, int radius, int y, boolean giantTrunk) {
        int i = giantTrunk ? 1 : 0;
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int j = -radius; j <= radius + i; ++j) {
            for (int k = -radius; k <= radius + i; ++k) {
                if (!this.shouldSkipLocationSigned(random, j, y, k, radius, giantTrunk)) {
                    mutable.setWithOffset(centerPos, j, y, k);
                    tryPlaceLeaf(levelSimulatedReader, foliageSetter, random, config, mutable);
                }
            }
        }
    }
}