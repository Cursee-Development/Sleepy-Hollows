package net.satisfy.sleepy_hollows.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.core.registry.SoundEventRegistry;
import org.jetbrains.annotations.NotNull;

public class CreakingPlanksBlock extends Block {
    public static final BooleanProperty CLEANSED = BooleanProperty.create("cleansed");
    private static final int CLEANSE_RADIUS = 32;

    public CreakingPlanksBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(CLEANSED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CLEANSED);
    }

    @Override
    public void stepOn(Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull Entity entity) {
        if (blockState.getValue(CLEANSED)) return;

        if (!level.isClientSide() && level.random.nextInt(1, 50) == 1) creak((ServerLevel) level, blockPos);

        if (!level.isClientSide() && level.isNight() && level.random.nextInt(100) < 25) {
            ((ServerLevel) level).sendParticles(ParticleTypes.SOUL, blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, 10, 0.2, 0.2, 0.2, 0.01);
        }
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!(stack.getItem() instanceof AxeItem) || state.getValue(CLEANSED)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hit);
        }
        if (level instanceof ServerLevel serverLevel) {
            cleanse(serverLevel, pos);
            spawnGuardian(serverLevel, pos.above());
            stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    private void cleanse(ServerLevel level, BlockPos origin) {
        int radiusSqr = CLEANSE_RADIUS * CLEANSE_RADIUS;
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-CLEANSE_RADIUS, -CLEANSE_RADIUS, -CLEANSE_RADIUS), origin.offset(CLEANSE_RADIUS, CLEANSE_RADIUS, CLEANSE_RADIUS))) {
            if (pos.distSqr(origin) > radiusSqr || !level.isLoaded(pos)) continue;
            BlockState current = level.getBlockState(pos);
            if (current.is(this) && !current.getValue(CLEANSED)) {
                level.setBlock(pos, current.setValue(CLEANSED, true), Block.UPDATE_CLIENTS);
            }
        }
        level.sendParticles(ParticleTypes.SOUL, origin.getX() + 0.5, origin.getY() + 1, origin.getZ() + 0.5, 30, 0.4, 0.4, 0.4, 0.05);
        level.playSound(null, origin, SoundEvents.SOUL_ESCAPE.value(), SoundSource.BLOCKS, 1.0f, 1.0f);
    }

    private void spawnGuardian(ServerLevel level, BlockPos pos) {
        Skeleton skeleton = EntityType.SKELETON.create(level);
        if (skeleton == null) return;
        skeleton.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, level.random.nextFloat() * 360.0f, 0.0f);
        skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObjectRegistry.HAUNTBOUND_HELMET.get()));
        skeleton.setItemSlot(EquipmentSlot.CHEST, new ItemStack(ObjectRegistry.HAUNTBOUND_CHESTPLATE.get()));
        skeleton.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ObjectRegistry.HAUNTBOUND_LEGGINGS.get()));
        skeleton.setItemSlot(EquipmentSlot.FEET, new ItemStack(ObjectRegistry.HAUNTBOUND_BOOTS.get()));
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            skeleton.setDropChance(slot, 0.0f);
        }
        level.addFreshEntity(skeleton);
    }

    private void creak(ServerLevel level, BlockPos blockPos) {
        level.playSound(null, blockPos, SoundEventRegistry.PLANKS_CREAKING.get(), SoundSource.BLOCKS, 0.25f, level.random.nextFloat());
    }
}
