package net.satisfy.sleepy_hollows.core.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.sleepy_hollows.core.block.custom.entity.CoffinBlockEntity;
import net.satisfy.sleepy_hollows.core.block.custom.entity.DummyCoffinBlockEntity;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class CoffinBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING;
    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<BedPart> BED_PART;

    public CoffinBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(BED_PART, BedPart.HEAD)
        );
    }

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0, 0.0, 0.0, 1.0, 0.875, 1.0), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625 * 2, 0.875, 0.0, 1.0 - 0.0625 * 2, 1.0, 1.0), BooleanOp.OR);
        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = net.minecraft.Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, SleepyHollowsUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockPos blockEntityPos = pos;
            if (state.getValue(BED_PART) == BedPart.FOOT) {
                blockEntityPos = pos.relative(state.getValue(FACING).getOpposite());
            }
            BlockEntity blockEntity = level.getBlockEntity(blockEntityPos);
            if (blockEntity instanceof CoffinBlockEntity) {
                player.openMenu((CoffinBlockEntity) blockEntity);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? createTickerHelper(blockEntityType, EntityTypeRegistry.COFFIN_BLOCK_ENTITY.get(), CoffinBlockEntity::lidAnimateTick) : null;
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.is(newState.getBlock())) {
            super.onRemove(state, level, pos, newState, isMoving);
            return;
        }

        if (state.getValue(BED_PART) == BedPart.HEAD) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CoffinBlockEntity) {
                Containers.dropContents(level, pos, (Container) blockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }


    public @NotNull ItemStack getCloneItemStack(@NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        ItemStack itemStack = super.getCloneItemStack(blockGetter, blockPos, blockState);
        blockGetter.getBlockEntity(blockPos, EntityTypeRegistry.COFFIN_BLOCK_ENTITY.get()).ifPresent((cofferBlockEntity) -> cofferBlockEntity.saveToItem(itemStack));
        return itemStack;
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public boolean hasAnalogOutputSignal(@NotNull BlockState blockState) {
        return true;
    }

    public int getAnalogOutputSignal(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
    }

    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        BlockPos clickedPos = context.getClickedPos();
        BlockPos footPos = clickedPos.relative(direction);
        Level level = context.getLevel();

        if (level.getBlockState(footPos).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(footPos)) {
            return this.defaultBlockState()
                    .setValue(FACING, direction)
                    .setValue(BED_PART, BedPart.HEAD)
                    .setValue(WATERLOGGED, level.getFluidState(clickedPos).getType() == Fluids.WATER);
        } else {
            return null;
        }
    }

    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (!level.isClientSide) {
            Direction facing = state.getValue(FACING);
            BlockPos footPos = pos.relative(facing);
            level.setBlock(footPos, state.setValue(BED_PART, BedPart.FOOT), 3);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, 3);

            if (stack.hasTag()) {
                CompoundTag blockEntityTag = stack.getTagElement("BlockEntityTag");
                if (blockEntityTag != null) {
                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if (blockEntity instanceof CoffinBlockEntity) {
                        ResourceLocation lootTable = new ResourceLocation(blockEntityTag.getString("LootTable"));
                        long lootTableSeed = blockEntityTag.getLong("LootTableSeed");
                        ((CoffinBlockEntity) blockEntity).setLootTable(lootTable, lootTableSeed);
                        blockEntity.setChanged();
                    }
                }
            }
        }
    }


    @Override
    public void playerWillDestroy(Level level, @NotNull BlockPos pos, BlockState state, @NotNull Player player) {
        BedPart part = state.getValue(BED_PART);
        BlockPos otherPartPos = part == BedPart.HEAD ? pos.relative(state.getValue(FACING)) : pos.relative(state.getValue(FACING).getOpposite());
        BlockState otherPartState = level.getBlockState(otherPartPos);

        if (otherPartState.is(this) && otherPartState.getValue(BED_PART) != part) {
            level.setBlock(otherPartPos, Blocks.AIR.defaultBlockState(), 35);
            level.levelEvent(player, 2001, otherPartPos, Block.getId(otherPartState));
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        if (state.getValue(BED_PART) == BedPart.HEAD) {
            return new CoffinBlockEntity(pos, state);
        } else {
            return new DummyCoffinBlockEntity(pos, state);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, BED_PART);
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull PathComputationType type) {
        return false;
    }


    static {
        FACING = HorizontalDirectionalBlock.FACING;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        BED_PART = BlockStateProperties.BED_PART;
    }
}
