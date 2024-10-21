package net.satisfy.sleepy_hollows.core.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class DummyCoffinBlockEntity extends BlockEntity {
    public DummyCoffinBlockEntity(BlockPos pos, BlockState state) {
        super(EntityTypeRegistry.DUMMY_COFFIN_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
    }
}
