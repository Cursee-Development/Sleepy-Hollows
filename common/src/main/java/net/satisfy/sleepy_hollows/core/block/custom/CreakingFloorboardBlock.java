package net.satisfy.sleepy_hollows.core.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CreakingFloorboardBlock extends Block {

    public CreakingFloorboardBlock() {
        super(Properties.copy(Blocks.BAMBOO_PLANKS));
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {

        // if you stand on the block, creaking continues
        // this method is called every tick, maybe we should add a blockstate value to check whether it has creaked recently, and
        // either random tick or schedule a tick to cooldown for the next creak?

        // only operate on server to play sound for all players
        // 1 in 50 chance to creak (1 in 100 felt too spaced apart)
        if (!level.isClientSide() && level.random.nextInt(1, 50) == 1) creak((ServerLevel) level, blockPos);
    }

    private void creak(ServerLevel level, BlockPos blockPos) {

        // todo: register custom sound ?
        level.playSound(null, blockPos, SoundEvents.GHAST_SCREAM, SoundSource.BLOCKS, 0.25f, level.random.nextFloat());
    }
}
