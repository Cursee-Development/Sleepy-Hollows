package net.satisfy.sleepy_hollows.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.sleepy_hollows.core.block.PedestalBlock;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PedestalBlockEntity extends BlockEntity implements Clearable {
    private ItemStack displayedItem = ItemStack.EMPTY;

    public PedestalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EntityTypeRegistry.DISPLAY_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    public ItemStack getDisplayedItem() {
        return this.displayedItem;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        this.displayedItem = tag.contains("DisplayedItem", 10) ? ItemStack.parseOptional(provider, tag.getCompound("DisplayedItem")) : ItemStack.EMPTY;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (!this.displayedItem.isEmpty()) {
            tag.put("DisplayedItem", this.displayedItem.save(provider, new CompoundTag()));
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        if (!this.displayedItem.isEmpty()) {
            tag.put("DisplayedItem", this.displayedItem.save(provider, new CompoundTag()));
        }
        return tag;
    }

    public boolean setDisplayedItem(ItemStack stack) {
        if (!this.displayedItem.isEmpty()) return false;
        this.displayedItem = stack.copyWithCount(1);
        this.updateBlockState(true);
        return true;
    }

    public void removeDisplayedItem(int count) {
        if (!this.displayedItem.isEmpty()) {
            this.displayedItem.shrink(count);
            if (this.displayedItem.isEmpty()) {
                this.displayedItem = ItemStack.EMPTY;
                this.updateBlockState(false);
            } else {
                this.markUpdated();
            }
        }
    }

    public void dropContents() {
        if (!this.displayedItem.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(Objects.requireNonNull(this.level), worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), this.displayedItem);
            this.level.addFreshEntity(itemEntity);
            this.displayedItem = ItemStack.EMPTY;
            this.updateBlockState(false);
        } else {
            this.markUpdated();
        }
    }

    private void updateBlockState(boolean active) {
        if (this.level != null) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            if (state.hasProperty(PedestalBlock.ACTIVE)) {
                this.level.setBlock(this.worldPosition, state.setValue(PedestalBlock.ACTIVE, active), 3);
            }
            this.markUpdated();
        }
    }

    private void markUpdated() {
        this.setChanged();
        Objects.requireNonNull(this.getLevel()).sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public void clearContent() {
        this.displayedItem = ItemStack.EMPTY;
        this.updateBlockState(false);
    }
}