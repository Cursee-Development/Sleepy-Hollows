package net.satisfy.sleepy_hollows.core.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class SpectralLanternBlock extends LanternBlock {
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    protected static final VoxelShape STANDING_SHAPE = createStandingLanternShape();

    public SpectralLanternBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(HANGING, false).setValue(WATERLOGGED, false));
    }

    private static VoxelShape createHangingLanternShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0.0625, 0.125, 0.875, 0.1875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.25, 0.75, 0.25, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.1875, 0.8125, 0.375, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.375, 0.25, 0.75, 0.75, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.75, 0.125, 0.875, 0.875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.6875, 0.1875, 0.3125, 0.9375, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.6875, 0.6875, 0.3125, 0.9375, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.6875, 0.6875, 0.8125, 0.9375, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.6875, 0.1875, 0.8125, 0.9375, 0.3125), BooleanOp.OR);
        return shape;
    }

    private static VoxelShape createStandingLanternShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.125, 0.25, 0.75, 0.1875, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.1875, 0.1875, 0.8125, 0.3125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.3125, 0.25, 0.75, 0.6875, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.6875, 0.125, 0.875, 0.8125, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.625, 0.1875, 0.3125, 0.875, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.625, 0.6875, 0.3125, 0.875, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.625, 0.6875, 0.8125, 0.875, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.625, 0.1875, 0.8125, 0.875, 0.3125), BooleanOp.OR);
        return shape;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(HANGING) ? createHangingLanternShape() : STANDING_SHAPE;
    }

    @Override
    public int getLightBlock(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        RandomSource random = RandomSource.create();
        return 6 + random.nextInt(7);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HANGING, WATERLOGGED);
    }
}
