package net.satisfy.sleepy_hollows.core.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

//TODO: Active doesn't change properly yet
@SuppressWarnings("deprecation")
public class TombstoneBlock extends Block  {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private final VoxelShape shape;

    public TombstoneBlock(Properties properties, VoxelShape shape) {
        super(properties);
        this.shape = shape;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ACTIVE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!world.isClientSide) {
            if (state.getValue(ACTIVE)) {
                spawnSkeletons(world, pos);
            } else if (player.getItemInHand(hand).getItem() == ObjectRegistry.SPECTRAL_ESSENCE.get()) {
                if (!player.isCreative()) {
                    player.getItemInHand(hand).shrink(1);
                }
                world.setBlock(pos, state.setValue(ACTIVE, true), 3);
                world.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
                spawnParticles(world, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void spawnSkeletons(Level world, BlockPos pos) {
        for (int i = 0; i < 3; i++) {
            Skeleton skeleton = EntityType.SKELETON.create(world);
            if (skeleton != null) {
                skeleton.setPos(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.CARVED_PUMPKIN));
                skeleton.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
                skeleton.setItemSlot(EquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
                skeleton.setItemSlot(EquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
                world.addFreshEntity(skeleton);
            }
        }
    }

    private void spawnParticles(Level world, BlockPos pos) {
        world.addParticle(ParticleTypes.SOUL, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 0, 0, 0);
    }

    @Override
    public void attack(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player) {
        if (state.getValue(ACTIVE)) {
            spawnSkeletons(world, pos);
            if (!player.isInvulnerableTo(world.damageSources().magic())) {
                player.hurt(world.damageSources().magic(), 5.0F);
            }
        }
    }

    public static VoxelShape createSmallTombstoneShape() {
        return Shapes.box(0.125, 0, 0.375, 0.875, 1, 0.625);
    }

    public static VoxelShape createMidTombstoneShape() {
        VoxelShape bottom = Shapes.box(0.0, 0.0, 0.25, 1.0, 0.25, 0.75);
        VoxelShape top = Shapes.box(0.0, 0.25, 0.375, 1.0, 1.0, 0.625);
        return Shapes.or(bottom, top);
    }

    public static VoxelShape createWoodenTombstoneShape() {
        VoxelShape bottom = Shapes.box(0.3125, 0, 0.3125, 0.8125, 0.25, 0.8125);
        VoxelShape top = Shapes.box(0.4375, 0.25, 0.4375, 0.6875, 1, 0.6875);
        return Shapes.or(bottom, top);
    }

    public static VoxelShape createBigTombstoneShapeBottom() {
        VoxelShape bottom = Shapes.box(0, 0, 0.25, 1, 0.25, 0.75);
        VoxelShape top = Shapes.box(0, 0.25, 0.375, 1, 1, 0.625);
        return Shapes.or(bottom, top);
    }

    public static VoxelShape createBigTombstoneShapeTop() {
        VoxelShape bottom = Shapes.box(0, 0, 0.375, 1, 0.125, 0.625);
        VoxelShape top = Shapes.box(0.125, 0.125, 0.375, 0.875, 0.25, 0.625);
        return Shapes.or(bottom, top);
    }


    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return shape;
    }
}

