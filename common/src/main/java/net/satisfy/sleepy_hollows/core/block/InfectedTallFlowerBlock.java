package net.satisfy.sleepy_hollows.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

public class InfectedTallFlowerBlock extends TallFlowerBlock {
    public static final BooleanProperty INFECTED = BooleanProperty.create("infected");

    public InfectedTallFlowerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(INFECTED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, INFECTED);
    }

    @Override
    public void animateTick(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(INFECTED)) {
            VoxelShape voxelShape = this.getShape(state, level, pos, CollisionContext.empty());
            Vec3 center = voxelShape.bounds().getCenter();
            double x = (double) pos.getX() + center.x;
            double z = (double) pos.getZ() + center.z;

            for (int i = 0; i < 3; ++i) {
                if (random.nextBoolean()) {
                    level.addParticle(ParticleTypes.SPORE_BLOSSOM_AIR, x + random.nextDouble() / 5.0, (double) pos.getY() + (0.5 - random.nextDouble()), z + random.nextDouble() / 5.0, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (stack.is(ObjectRegistry.LUMINOUS_WATER.get()) && state.getValue(INFECTED)) {
            if (!level.isClientSide) {
                deactivateNearbyFlowers(level, pos);
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                BlockPos.betweenClosedStream(pos.offset(-3, -3, -3), pos.offset(3, 3, 3)).forEach(p -> level.addParticle(ParticleTypes.FALLING_WATER, p.getX() + 0.5D, p.getY() + 0.5D, p.getZ() + 0.5D, 0.0D, 0.0D, 0.0D));
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private void deactivateNearbyFlowers(Level world, BlockPos pos) {
        BlockPos.betweenClosedStream(pos.offset(-4, -4, -4), pos.offset(4, 4, 4))
                .forEach(p -> {
                    BlockState state = world.getBlockState(p);
                    if (state.getBlock() instanceof InfectedFlowerBlock && state.getValue(INFECTED)) {
                        world.setBlock(p, state.setValue(INFECTED, false), 3);
                    }
                });
    }
}
