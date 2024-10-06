package net.satisfy.sleepy_hollows.core.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CreakingPlanksBlock extends Block {
    public CreakingPlanksBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {

        // 1 in 50 chance to creak
        if (!level.isClientSide() && level.random.nextInt(1, 50) == 1) creak((ServerLevel) level, blockPos);

        // 25% chance for ghost particles to appear, but (!) only at night
        if (!level.isClientSide() && level.isNight() && level.random.nextInt(100) < 25) {
            ((ServerLevel) level).sendParticles(ParticleTypes.SOUL, blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, 10, 0.2, 0.2, 0.2, 0.01);
        }
    }

    private void creak(ServerLevel level, BlockPos blockPos) {
        // How about this sound here? :D //TODO: we have to change it
        level.playSound(null, blockPos, SoundEvents.GHAST_SCREAM, SoundSource.BLOCKS, 0.25f, level.random.nextFloat());
    }
}
