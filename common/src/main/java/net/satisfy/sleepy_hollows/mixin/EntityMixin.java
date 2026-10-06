package net.satisfy.sleepy_hollows.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.util.IEntitySavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntitySavedData {
    @Unique
    private CompoundTag unique$persistentData;

    @Override
    public CompoundTag impl$getPersistentData() {
        if (this.unique$persistentData == null) this.unique$persistentData = new CompoundTag();
        return unique$persistentData;
    }

    @Inject(method = "saveWithoutId", at = @At("RETURN"))
    private void inject$saveWithoutId(CompoundTag compound, CallbackInfoReturnable<CompoundTag> cir) {
        if (unique$persistentData != null) compound.put(SleepyHollows.MOD_DATA_ID, unique$persistentData);
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void inject$load(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains(SleepyHollows.MOD_DATA_ID, Tag.TAG_COMPOUND)) unique$persistentData = compound.getCompound(SleepyHollows.MOD_DATA_ID);
    }
}