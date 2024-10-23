package net.satisfy.sleepy_hollows.core.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.platform.LuminousWaterParticles;

public class LingeringSoul extends Entity {
    TamableAnimal remnant;
    int lifetime = 200;
    int resurrectionTicks = 0;
    boolean isBeingResurrected = false;

    public LingeringSoul(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.remnant = null;
        this.noPhysics = true;
    }

    @Override
    public void tick() {
        if (this.isBeingResurrected) {
            if (this.resurrectionTicks >= 80) {
                remnant.setRemoved(null);
                this.level().addFreshEntity(remnant);
                this.discard();
            }
            this.resurrectionTicks++; return;
        }
        this.lifetime--;
        if (this.lifetime == 0) this.discard();
    }

    public void resurrect() {
        this.isBeingResurrected = true;
        if (this.level().isClientSide()) LuminousWaterParticles.createPreReincarnate(this, this.random);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    public void setRemnant(net.minecraft.world.entity.TamableAnimal remnant) {
        this.remnant = remnant;
    }
}
