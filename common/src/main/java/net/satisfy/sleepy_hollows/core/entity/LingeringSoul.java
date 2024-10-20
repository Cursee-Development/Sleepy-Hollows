package net.satisfy.sleepy_hollows.core.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;

public class LingeringSoul extends Entity {
    TamableAnimal remnant;
    int lifetime = 10;
    boolean isBeingResurrected = false;

    public LingeringSoul(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.remnant = null;
        this.noPhysics = true;
    }

    public LingeringSoul(Level level, TamableAnimal remnant) {
        this(EntityTypeRegistry.LUMINOUS_WATER_THROWN.get(), level);
        this.remnant = remnant;
    }

    @Override
    public void tick() {
        if (this.isBeingResurrected) return;
        if (this.level().getGameTime() % 20 == 0) {
            this.lifetime--;
        }
        if (this.lifetime == 0) this.discard();
    }

    public void resurrect() {
        this.isBeingResurrected = true;
        remnant.setRemoved(null);
        this.level().addFreshEntity(remnant);
        this.discard();
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {}

    @Override
    public boolean hurt(DamageSource source, float amount) { return false; }

    /*@Override
    public boolean shouldRender(double x, double y, double z) { return false; }*/
}
