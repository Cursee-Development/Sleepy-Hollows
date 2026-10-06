package net.satisfy.sleepy_hollows.core.entity;

import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.satisfy.sleepy_hollows.core.block.InfectedFlowerBlock;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import org.jetbrains.annotations.NotNull;

public class SplashLuminousWater extends ThrowableItemProjectile {
    private static final int RADIUS = 4;
    private static final int COLOR = 0x7FD4FF;

    public SplashLuminousWater(EntityType<? extends SplashLuminousWater> type, Level level) {
        super(type, level);
    }

    public SplashLuminousWater(Level level, LivingEntity owner) {
        super(EntityTypeRegistry.SPLASH_LUMINOUS_WATER.get(), owner, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ObjectRegistry.LUMINOUS_WATER_SPLASH.get();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.05;
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        if (this.level().isClientSide) return;
        InfectedFlowerBlock.cleanse(this.level(), this.blockPosition(), RADIUS);
        for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, new AABB(this.blockPosition()).inflate(RADIUS))) {
            entity.removeEffect(MobEffectRegistry.holder(MobEffectRegistry.INFECTED));
            if (entity instanceof Horseman horseman) {
                horseman.hurt(this.damageSources().indirectMagic(this, this.getOwner()), PlatformHelper.getSplashHorsemanDamage());
            }
            if (entity instanceof ServerPlayer player) {
                SanityManager.changeSanity(player, SanityManager.Modifiers.LUMINOUS_WATER);
            }
        }
        this.level().levelEvent(2002, this.blockPosition(), COLOR);
        this.discard();
    }
}
