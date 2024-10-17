package net.satisfy.sleepy_hollows.core.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import net.satisfy.sleepy_hollows.platform.LuminousWaterParticles;

import java.util.List;

public class ThrownLuminousWater extends ThrowableItemProjectile implements ItemSupplier {

    public ThrownLuminousWater(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownLuminousWater(Level level, LivingEntity shooter) {
        super(EntityType.POTION, shooter, level);
    }

    public ThrownLuminousWater(Level level, double x, double y, double z) {
        super(EntityType.POTION, x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ObjectRegistry.LUMINOUS_WATER_SPLASH.get();
    }

    @Override
    protected float getGravity() {
        return 0.05F;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        applySplash();
    }

    @Override
    protected void onHit(HitResult result) {
        applySplash();
    }

    private void applySplash() {
        AABB aABB = this.getBoundingBox().inflate(4.0, 2.0, 4.0);
        List<Player> list = this.level().getEntitiesOfClass(Player.class, aABB);
        list.forEach(player -> SanityManager.decreaseSanity(player, 4));
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            LuminousWaterParticles.spawnItem(this, this.random);
        }
    }
}
