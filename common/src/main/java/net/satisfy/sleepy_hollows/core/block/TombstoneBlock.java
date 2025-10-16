package net.satisfy.sleepy_hollows.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EntityDimensions;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TombstoneBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private final VoxelShape shape;

    public TombstoneBlock(Properties properties, VoxelShape shape) {
        super(properties.lightLevel(state -> state.getValue(ACTIVE) ? 10 : 0));
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
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
        if (!level.isClientSide && state.getValue(ACTIVE)) {
            spawnSkeletonsAndParticles(level, pos);
            level.setBlock(pos, state.setValue(ACTIVE, false), 3);
        }
        return state.getValue(ACTIVE) ? InteractionResult.sidedSuccess(level.isClientSide) : InteractionResult.PASS;
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!level.isClientSide && !state.getValue(ACTIVE) && stack.is(ObjectRegistry.SPECTRAL_ESSENCE.get())) {
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            level.setBlock(pos, state.setValue(ACTIVE, true), 3);
            level.playSound(null, pos, SoundEvents.WITHER_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
            spawnParticles(level, pos);
            return ItemInteractionResult.sidedSuccess(false);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private BlockPos findSafeSpawnPos(Level level, BlockPos desiredPos) {
        int[][] offsets = {
                {0,0},{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,1},{1,-1},{-1,-1},{2,0},{-2,0},{0,2},{0,-2}
        };
        for (int dy = 0; dy <= 2; dy++) {
            for (int i = 0; i < offsets.length; i++) {
                int dx = offsets[i][0];
                int dz = offsets[i][1];
                BlockPos candidate = desiredPos.offset(dx, dy, dz);
                BlockPos below = candidate.below();
                if (!level.getBlockState(candidate).isAir()) continue;
                if (!level.getBlockState(candidate.above()).isAir()) continue;
                if (!level.getBlockState(below).isFaceSturdy(level, below, Direction.UP)) continue;
                EntityDimensions dims = EntityType.SKELETON.getDimensions();
                AABB box = dims.makeBoundingBox(candidate.getX() + 0.5, candidate.getY(), candidate.getZ() + 0.5);
                if (!level.noCollision(box)) continue;
                return candidate;
            }
        }
        return null;
    }

    private boolean trySpawnSkeleton(Level level, BlockPos spawnPos, ItemStack[] equipment, EquipmentSlot[] slots, Random random) {
        BlockPos safe = findSafeSpawnPos(level, spawnPos);
        if (safe == null) return false;
        Skeleton skeleton = EntityType.SKELETON.create(level);
        if (skeleton == null) return false;
        skeleton.setPos(safe.getX() + 0.5, safe.getY(), safe.getZ() + 0.5);
        skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObjectRegistry.SPECTRAL_JACK_O_LANTERN.get()));
        skeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ObjectRegistry.SPECTRAL_WARAXE.get()));
        for (int j = 0; j < equipment.length; j++) {
            if (random.nextFloat() < 0.3F) skeleton.setItemSlot(slots[j], equipment[j]);
        }
        level.addFreshEntity(skeleton);
        spawnParticles(level, safe);
        if (!level.isClientSide) level.playSound(null, safe, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.BLOCKS, 1.0F, 1.0F);
        return true;
    }

    private void spawnSkeletonsAndParticles(Level world, BlockPos pos) {
        BlockPos[] desired = { pos.offset(2,0,0), pos.offset(-2,0,0), pos.offset(0,0,-2) };
        Random random = new Random();
        ItemStack[] armorPieces = {
                new ItemStack(ObjectRegistry.HAUNTBOUND_CHESTPLATE.get()),
                new ItemStack(ObjectRegistry.HAUNTBOUND_LEGGINGS.get()),
                new ItemStack(ObjectRegistry.HAUNTBOUND_BOOTS.get()),
                new ItemStack(Items.SHIELD),
        };
        EquipmentSlot[] armorSlots = { EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.OFFHAND };
        int spawned = 0;
        for (int i = 0; i < desired.length; i++) {
            if (trySpawnSkeleton(world, desired[i], armorPieces, armorSlots, random)) spawned++;
            if (!world.isClientSide) world.scheduleTick(pos, this, i * 10);
        }
        if (spawned > 0 && !world.isClientSide) {
            ItemStack dropItem = new ItemStack(ObjectRegistry.ESSENCE_OF_UNDEAD.get());
            popResource(world, pos, dropItem);
        }
    }

    private void spawnParticles(Level world, BlockPos pos) {
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SOUL, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.05);
        }
    }

    @Override
    public void attack(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player) {
        if (state.getValue(ACTIVE)) {
            spawnSkeletonsAndParticles(world, pos);
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
        VoxelShape bottom = Shapes.box(0.25, 0, 0.25, 0.75, 0.25, 0.75);
        VoxelShape top = Shapes.box(0.375, 0.25, 0.375, 0.625, 1, 0.625);
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
        Direction facing = state.getValue(FACING);
        return SleepyHollowsUtil.rotateShape(Direction.NORTH, facing, this.shape);
    }
}

