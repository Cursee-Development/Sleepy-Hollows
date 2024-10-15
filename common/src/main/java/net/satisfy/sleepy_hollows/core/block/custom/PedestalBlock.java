package net.satisfy.sleepy_hollows.core.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Zombie;
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
import net.satisfy.sleepy_hollows.core.block.custom.entity.PedestalBlockEntity;
import net.satisfy.sleepy_hollows.core.entity.SpectralHorse;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class PedestalBlock extends Block implements EntityBlock {
    private static final VoxelShape SHAPE = Shapes.or(box(0, 0, 0, 16, 4, 16), box(2, 4, 2, 14, 12, 14), box(0, 12, 0, 16, 16, 16));
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private static long lastUseTime = 0;

    public PedestalBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof PedestalBlockEntity displayBlockEntity)) return InteractionResult.PASS;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUseTime < 300000) {
            return InteractionResult.PASS;
        }

        ItemStack stackInHand = player.getItemInHand(hand);

        if (!stackInHand.isEmpty()) {
            if (!level.isClientSide && displayBlockEntity.getDisplayedItem().isEmpty()) {
                ItemStack singleItemStack = stackInHand.copy();
                singleItemStack.setCount(1);
                if (displayBlockEntity.setDisplayedItem(singleItemStack)) {
                    if (!player.getAbilities().instabuild) {
                        stackInHand.shrink(1);
                    }
                    level.setBlock(pos, state.setValue(ACTIVE, true), 3);
                    level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);

                    if (singleItemStack.is(ObjectRegistry.LUMINOUS_ESSENCE.get())) {
                        BlockPos spawnPos = pos.north(5);
                        SpectralHorse spectralHorse = EntityTypeRegistry.SPECTRAL_HORSE.get().create(level);
                        //TODO @Jason13 - just change this entity here to the actual boss entity. :) It will spawn 5 blocks north to the pedestal block
                        //TODO on a spectral horse when you use a Spectral Essence on the Pedestal. It has a 5 Min cooldown.
                        Zombie zombie = EntityType.ZOMBIE.create(level);

                        if (spectralHorse != null && zombie != null) {
                            spectralHorse.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                            zombie.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());

                            level.addFreshEntity(spectralHorse);
                            level.addFreshEntity(zombie);
                            zombie.startRiding(spectralHorse);

                            LightningBolt lightningBolt1 = EntityType.LIGHTNING_BOLT.create(level);
                            if (lightningBolt1 != null) {
                                lightningBolt1.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                                lightningBolt1.setVisualOnly(true);
                                level.addFreshEntity(lightningBolt1);
                            }

                            LightningBolt lightningBolt2 = EntityType.LIGHTNING_BOLT.create(level);
                            if (lightningBolt2 != null) {
                                lightningBolt2.setPos(pos.getX(), pos.getY(), pos.getZ());
                                lightningBolt2.setVisualOnly(true);
                                level.addFreshEntity(lightningBolt2);
                            }

                            displayBlockEntity.removeDisplayedItem(1);

                            level.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 1.0F, 1.0F);
                        }

                        lastUseTime = currentTime;
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.CONSUME;
        }

        if (!displayBlockEntity.getDisplayedItem().isEmpty()) {
            if (!level.isClientSide) {
                ItemStack displayedItem = displayBlockEntity.getDisplayedItem().copy();
                if (!player.addItem(displayedItem)) {
                    ItemEntity itemEntity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), displayedItem);
                    level.addFreshEntity(itemEntity);
                }
                displayBlockEntity.removeDisplayedItem(1);
                level.setBlock(pos, state.setValue(ACTIVE, false), 3);
                level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
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
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }
}
