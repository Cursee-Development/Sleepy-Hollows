package net.satisfy.sleepy_hollows.core.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BigTombstoneBlock extends TombstoneBlock {
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public BigTombstoneBlock(Properties properties) {
        super(properties, TombstoneBlock.createMidTombstoneShape());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ACTIVE, false)
                .setValue(HALF, Half.BOTTOM));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        if (context.getLevel().getBlockState(pos.above()).canBeReplaced(context)) {
            return this.defaultBlockState()
                    .setValue(FACING, context.getHorizontalDirection().getOpposite())
                    .setValue(ACTIVE, false)
                    .setValue(HALF, Half.BOTTOM);
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
        world.setBlock(pos.above(), state.setValue(HALF, Half.TOP), 3);
    }

    @Override
    public void playerWillDestroy(@NotNull Level world, @NotNull BlockPos pos, BlockState state, @NotNull Player player) {
        if (state.getValue(HALF) == Half.BOTTOM) {
            BlockPos topPos = pos.above();
            BlockState topState = world.getBlockState(topPos);
            if (topState.getBlock() == this && topState.getValue(HALF) == Half.TOP) {
                world.destroyBlock(topPos, false);
            }
        } else {
            BlockPos bottomPos = pos.below();
            BlockState bottomState = world.getBlockState(bottomPos);
            if (bottomState.getBlock() == this && bottomState.getValue(HALF) == Half.BOTTOM) {
                world.destroyBlock(bottomPos, false);
            }
        }
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return state.getValue(HALF) == Half.BOTTOM ? TombstoneBlock.createBigTombstoneShapeBottom() : TombstoneBlock.createBigTombstoneShapeTop();
    }
}
