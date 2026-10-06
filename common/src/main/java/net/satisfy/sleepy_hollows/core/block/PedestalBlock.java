package net.satisfy.sleepy_hollows.core.block;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.sleepy_hollows.core.block.entity.PedestalBlockEntity;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends Block implements EntityBlock {
    private static final VoxelShape SHAPE = Shapes.or(box(0, 0, 0, 16, 4, 16), box(2, 4, 2, 14, 12, 14), box(0, 12, 0, 16, 16, 16));
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public PedestalBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof PedestalBlockEntity displayBlockEntity)) return InteractionResult.PASS;
        if (displayBlockEntity.getDisplayedItem().isEmpty() || displayBlockEntity.isRitualActive()) return InteractionResult.PASS;
        if (!level.isClientSide) {
            ItemStack displayedItem = displayBlockEntity.getDisplayedItem().copy();
            if (!player.addItem(displayedItem)) {
                ItemEntity itemEntity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), displayedItem);
                level.addFreshEntity(itemEntity);
            }
            displayBlockEntity.removeDisplayedItem(1);
            level.setBlock(pos, state.setValue(ACTIVE, false), 3);
            level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof PedestalBlockEntity displayBlockEntity)) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (stack.isEmpty()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

        if (!level.isClientSide && displayBlockEntity.getDisplayedItem().isEmpty()) {
            ItemStack singleItemStack = stack.copy();
            singleItemStack.setCount(1);
            if (displayBlockEntity.setDisplayedItem(singleItemStack)) {
                if (!player.getAbilities().instabuild) stack.shrink(1);
                level.setBlock(pos, state.setValue(ACTIVE, true), 3);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (singleItemStack.is(ObjectRegistry.LUMINOUS_ESSENCE.get())) {
                    displayBlockEntity.tryStartRitual(player);
                }
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof PedestalBlockEntity displayBlockEntity) {
                displayBlockEntity.dropContents();
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return type == EntityTypeRegistry.DISPLAY_BLOCK_ENTITY.get() ? (lvl, pos, st, be) -> ((PedestalBlockEntity) be).tick() : null;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(ACTIVE) && random.nextInt(3) == 0) {
            double angle = random.nextDouble() * Math.PI * 2;
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX() + 0.5 + Math.cos(angle) * 0.4, pos.getY() + 1.0, pos.getZ() + 0.5 + Math.sin(angle) * 0.4, 0.0, 0.02, 0.0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }
}
