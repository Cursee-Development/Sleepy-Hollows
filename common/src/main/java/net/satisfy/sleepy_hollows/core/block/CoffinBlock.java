package net.satisfy.sleepy_hollows.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
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
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.sleepy_hollows.core.block.entity.CoffinBlockEntity;
import net.satisfy.sleepy_hollows.core.block.entity.DummyCoffinBlockEntity;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CoffinBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<CoffinBlock> CODEC = simpleCodec(CoffinBlock::new);
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

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
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
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        BlockPos bePos = pos;
        if (state.getValue(BED_PART) == BedPart.FOOT) {
            bePos = pos.relative(state.getValue(FACING).getOpposite());
        }
        BlockEntity be = level.getBlockEntity(bePos);
        if (be instanceof CoffinBlockEntity) {
            player.openMenu((CoffinBlockEntity) be);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
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

            CustomData data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
            if (data != null) {
                CompoundTag tag = data.copyTag();
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof CoffinBlockEntity coffin) {
                    String lootTableString = tag.getString("LootTable");
                    long lootTableSeed = tag.getLong("LootTableSeed");
                    if (!lootTableString.isEmpty()) {
                        ResourceKey<LootTable> lootKey = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(lootTableString));
                        coffin.setLootTable(lootKey, lootTableSeed);
                        be.setChanged();
                    }
                }
            }
        }
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, BlockState state, @NotNull Player player) {
        BedPart part = state.getValue(BED_PART);
        BlockPos otherPartPos = part == BedPart.HEAD ? pos.relative(state.getValue(FACING)) : pos.relative(state.getValue(FACING).getOpposite());
        BlockState otherPartState = level.getBlockState(otherPartPos);
        if (otherPartState.is(this) && otherPartState.getValue(BED_PART) != part) {
            level.setBlock(otherPartPos, Blocks.AIR.defaultBlockState(), 35);
            level.levelEvent(player, 2001, otherPartPos, Block.getId(otherPartState));
        }
        return super.playerWillDestroy(level, pos, state, player);
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

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        BED_PART = BlockStateProperties.BED_PART;
    }
}
