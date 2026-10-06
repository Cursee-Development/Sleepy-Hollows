package net.satisfy.sleepy_hollows.core.block;

import net.satisfy.foundation.registry.FoundationParticles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ColorParticleOption;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

public class InfectedFlowerBlock extends FlowerBlock {
    public static final BooleanProperty INFECTED = BooleanProperty.create("infected");

    public InfectedFlowerBlock(Holder<MobEffect> effectHolder, int duration, Properties properties) {
        super(effectHolder, duration, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(INFECTED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(INFECTED);
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
            if (random.nextInt(4) == 0) {
                level.addParticle(ColorParticleOption.create(FoundationParticles.COLORED_STEAM.get(), 0.45F, 0.15F, 0.55F), x + (random.nextDouble() - 0.5) * 0.4, pos.getY() + 0.3, z + (random.nextDouble() - 0.5) * 0.4, 0.0, 0.02, 0.0);
            }
        }
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (stack.is(ObjectRegistry.LUMINOUS_WATER.get()) && state.getValue(INFECTED)) {
            if (!level.isClientSide) {
                InfectedFlowerBlock.cleanse(level, pos, 4);
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (level instanceof ServerLevel serverLevel) {
                    serverLevel.sendParticles(ParticleTypes.FALLING_WATER, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 60, 3.0, 1.0, 3.0, 0.0);
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    @Override
    protected void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (state.getValue(INFECTED) && !level.isClientSide && level.getDifficulty() != Difficulty.PEACEFUL && entity instanceof Player player && !player.isCreative() && !player.isSpectator() && !player.isInvulnerableTo(level.damageSources().wither())) {
            player.addEffect(new MobEffectInstance(MobEffectRegistry.holder(MobEffectRegistry.INFECTED), 40));
        }
    }

    public static void cleanse(Level level, BlockPos center, int radius) {
        BlockPos.betweenClosedStream(center.offset(-radius, -radius, -radius), center.offset(radius, radius, radius)).forEach(p -> {
            BlockState state = level.getBlockState(p);
            if (state.hasProperty(INFECTED) && state.getValue(INFECTED) && (state.getBlock() instanceof InfectedFlowerBlock || state.getBlock() instanceof InfectedTallFlowerBlock)) {
                level.setBlock(p, state.setValue(INFECTED, false), 3);
            }
        });
    }
}
