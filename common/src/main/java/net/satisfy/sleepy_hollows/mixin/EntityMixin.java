package net.satisfy.sleepy_hollows.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.satisfy.sleepy_hollows.Constants;
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
        if(this.unique$persistentData == null) {
            this.unique$persistentData = new CompoundTag();
        }
        return unique$persistentData;
    }

    @Inject(method = "save", at = @At("HEAD"))
    protected void inject$save(CompoundTag nbt, CallbackInfoReturnable<Boolean> info) {
        if(unique$persistentData != null) {
            nbt.put(Constants.MOD_DATA_ID, unique$persistentData);
        }
    }

    @Inject(method = "load", at = @At("HEAD"))
    protected void inject$load(CompoundTag nbt, CallbackInfo info) {
        if (nbt.contains(Constants.MOD_DATA_ID, Tag.TAG_COMPOUND)) {
            unique$persistentData = nbt.getCompound(Constants.MOD_DATA_ID);
        }
    }
}
