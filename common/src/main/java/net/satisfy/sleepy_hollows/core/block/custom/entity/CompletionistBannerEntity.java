package net.satisfy.sleepy_hollows.core.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;

public class CompletionistBannerEntity extends BlockEntity {
    public CompletionistBannerEntity(BlockPos blockPos, BlockState state) {
        super(EntityTypeRegistry.COMPLETIONIST_BANNER_ENTITY.get(), blockPos, state);
    }
}