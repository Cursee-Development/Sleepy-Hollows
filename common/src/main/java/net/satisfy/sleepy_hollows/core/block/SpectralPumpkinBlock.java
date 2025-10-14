package net.satisfy.sleepy_hollows.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.PumpkinBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

public class SpectralPumpkinBlock extends PumpkinBlock {
    public SpectralPumpkinBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (stack.is(Items.SHEARS)) {
            if (!level.isClientSide) {
                Direction direction = hit.getDirection();
                Direction facing = direction.getAxis() == Axis.Y ? player.getDirection().getOpposite() : direction;
                level.playSound(null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, ObjectRegistry.SPECTRAL_CARVED_PUMPKIN.get().defaultBlockState().setValue(CarvedPumpkinBlock.FACING, facing), 11);
                ItemEntity seeds = new ItemEntity(level, pos.getX() + 0.5 + facing.getStepX() * 0.65, pos.getY() + 0.1, pos.getZ() + 0.5 + facing.getStepZ() * 0.65, new ItemStack(Items.PUMPKIN_SEEDS, 4));
                seeds.setDeltaMovement(0.05 * facing.getStepX() + level.random.nextDouble() * 0.02, 0.05, 0.05 * facing.getStepZ() + level.random.nextDouble() * 0.02);
                level.addFreshEntity(seeds);
                stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                level.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
