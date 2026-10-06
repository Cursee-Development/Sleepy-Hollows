package net.satisfy.sleepy_hollows.core.block.entity;

import net.satisfy.sleepy_hollows.core.util.MobSpawnHelper;
import net.satisfy.sleepy_hollows.core.registry.SoundEventRegistry;
import net.satisfy.sleepy_hollows.core.entity.Horseman;
import net.satisfy.foundation.overlay.BlockNotice;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.commands.arguments.EntityAnchorArgument;
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
    private long displayedSince;
    private int ritualTicks = -1;
    private static final int EMERGE_TICKS = 60;
    private static final int ORBIT_TICKS = 60;
    private static final int GATHER_TICKS = 30;
    public static final int RITUAL_DURATION = EMERGE_TICKS + ORBIT_TICKS + GATHER_TICKS;
    private static final int SOULS = 14;
    private static final double ORBIT_RADIUS = 1.5;
    private BlockPos spawnPos;

    public PedestalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(EntityTypeRegistry.DISPLAY_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    public boolean isRitualActive() {
        return this.ritualTicks >= 0;
    }

    public float getRitualProgress(float partialTick) {
        return isRitualActive() ? Math.min(1.0F, (this.ritualTicks + partialTick) / RITUAL_DURATION) : 0.0F;
    }

    public void tryStartRitual(Player player) {
        if (!(this.level instanceof ServerLevel serverLevel) || isRitualActive()) return;
        if (!serverLevel.getEntitiesOfClass(Horseman.class, new AABB(this.worldPosition).inflate(64)).isEmpty()) {
            if (player instanceof ServerPlayer serverPlayer) {
                BlockNotice.send(serverPlayer, this.worldPosition, Component.translatable("message.sleepy_hollows.pedestal.horseman_alive"));
            }
            return;
        }
        this.ritualTicks = 0;
        BlockPos spawn = MobSpawnHelper.findGroundedSpawn(serverLevel, this.worldPosition.north(5), 3, 3);
        this.spawnPos = spawn != null ? spawn : this.worldPosition.above();
        serverLevel.playSound(null, this.worldPosition, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.5F, 0.5F);
        for (ServerPlayer nearby : serverLevel.getPlayers(p -> p.distanceToSqr(Vec3.atCenterOf(this.worldPosition)) < 48 * 48)) {
            BlockNotice.send(nearby, this.worldPosition, Component.translatable("message.sleepy_hollows.pedestal.ritual"));
        }
        this.markUpdated();
    }

    public void tick() {
        if (!isRitualActive() || this.level == null) return;
        this.ritualTicks++;
        if (this.level.isClientSide) {
            spawnRitualParticles();
            return;
        }
        if (this.ritualTicks % 20 == 0 && this.ritualTicks < RITUAL_DURATION) {
            this.level.playSound(null, this.worldPosition, SoundEvents.WARDEN_HEARTBEAT, SoundSource.HOSTILE, 2.0F, 0.6F + getRitualProgress(0) * 0.6F);
        }
        if (this.ritualTicks == EMERGE_TICKS + ORBIT_TICKS) {
            this.level.playSound(null, this.worldPosition, SoundEvents.SOUL_ESCAPE.value(), SoundSource.HOSTILE, 2.0F, 0.5F);
        }
        if (this.ritualTicks >= RITUAL_DURATION) {
            finishRitual((ServerLevel) this.level);
        }
    }

    private void spawnRitualParticles() {
        Vec3 item = Vec3.atCenterOf(this.worldPosition).add(0, 0.8 + getRitualProgress(0) * getRitualProgress(0) * 0.8, 0);
        Vec3 target = this.spawnPos == null ? item : Vec3.atBottomCenterOf(this.spawnPos).add(0, 1.2, 0);
        int t = this.ritualTicks;
        for (int i = 0; i < SOULS; i++) {
            Vec3 pos = soulPosition(i, t, item, target);
            this.level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y, pos.z, 0, 0, 0);
            if ((t + i) % 3 == 0) {
                this.level.addParticle(ParticleTypes.SOUL, pos.x, pos.y, pos.z, 0, 0.01, 0);
            }
        }
        if (t < EMERGE_TICKS && t % 2 == 0) {
            RandomSource random = this.level.random;
            this.level.addParticle(ParticleTypes.SOUL, item.x, item.y, item.z, (random.nextDouble() - 0.5) * 0.15, random.nextDouble() * 0.1, (random.nextDouble() - 0.5) * 0.15);
        }
    }

    private Vec3 soulPosition(int index, int t, Vec3 item, Vec3 target) {
        RandomSource seed = RandomSource.create(this.worldPosition.asLong() * 31 + index);
        double baseAngle = seed.nextDouble() * Math.PI * 2;
        double speed = 0.08 + seed.nextDouble() * 0.04;
        double waveSpeed = 0.1 + seed.nextDouble() * 0.15;
        double waveHeight = 0.4 + seed.nextDouble() * 0.8;
        double emergeHeight = (seed.nextDouble() - 0.3) * 1.2;
        double angle = baseAngle + Math.max(0, t - EMERGE_TICKS * 0.5) * speed;
        Vec3 orbit = item.add(Math.cos(angle) * ORBIT_RADIUS, Math.sin(t * waveSpeed + baseAngle) * waveHeight, Math.sin(angle) * ORBIT_RADIUS);
        if (t < EMERGE_TICKS) {
            double e = ease(t / (double) EMERGE_TICKS);
            Vec3 burst = item.add(Math.cos(baseAngle) * ORBIT_RADIUS * e, emergeHeight * Math.sin(e * Math.PI) + (orbit.y - item.y) * e, Math.sin(baseAngle) * ORBIT_RADIUS * e);
            return burst.lerp(orbit, e * e);
        }
        if (t < EMERGE_TICKS + ORBIT_TICKS) {
            return orbit;
        }
        double g = ease((t - EMERGE_TICKS - ORBIT_TICKS) / (double) GATHER_TICKS);
        double hoverAngle = baseAngle + t * 0.3;
        Vec3 hover = target.add(Math.cos(hoverAngle) * 0.6, Math.sin(t * waveSpeed * 2 + baseAngle) * 0.5, Math.sin(hoverAngle) * 0.6);
        return orbit.lerp(hover, Math.min(1, g * 1.6));
    }

    private static double ease(double t) {
        return t * t * (3 - 2 * t);
    }

    private void finishRitual(ServerLevel level) {
        this.ritualTicks = -1;
        BlockPos spawnPos = this.spawnPos != null ? this.spawnPos : this.worldPosition.above();
        this.spawnPos = null;
        Horseman horseman = EntityTypeRegistry.HORSEMAN.get().create(level);
        if (horseman != null) {
            MobSpawnHelper.spawnAt(level, horseman, spawnPos);
            horseman.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(this.worldPosition));
            LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
            if (lightning != null) {
                lightning.moveTo(Vec3.atBottomCenterOf(spawnPos));
                lightning.setVisualOnly(true);
                level.addFreshEntity(lightning);
            }
            level.sendParticles(ParticleTypes.LARGE_SMOKE, spawnPos.getX() + 0.5, spawnPos.getY() + 1, spawnPos.getZ() + 0.5, 80, 1.0, 1.5, 1.0, 0.05);
            level.sendParticles(ParticleTypes.SOUL, spawnPos.getX() + 0.5, spawnPos.getY() + 1.2, spawnPos.getZ() + 0.5, 60, 0.2, 0.3, 0.2, 0.35);
            level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, spawnPos.getX() + 0.5, spawnPos.getY() + 1.2, spawnPos.getZ() + 0.5, 40, 0.2, 0.3, 0.2, 0.25);
            level.playSound(null, spawnPos, SoundEventRegistry.HORSEMAN_LAUGH.get(), SoundSource.HOSTILE, 2.0F, 0.8F);
            this.removeDisplayedItem(1);
        }
        this.markUpdated();
    }

    public long getDisplayedSince() {
        return this.displayedSince;
    }

    public ItemStack getDisplayedItem() {
        return this.displayedItem;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        this.ritualTicks = tag.contains("Ritual") ? tag.getInt("Ritual") : -1;
        this.spawnPos = tag.contains("RitualSpawn") ? BlockPos.of(tag.getLong("RitualSpawn")) : null;
        boolean wasEmpty = this.displayedItem.isEmpty();
        this.displayedItem = tag.contains("DisplayedItem", 10) ? ItemStack.parseOptional(provider, tag.getCompound("DisplayedItem")) : ItemStack.EMPTY;
        if (wasEmpty && !this.displayedItem.isEmpty() && this.level != null) {
            this.displayedSince = this.level.getGameTime();
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);
        if (this.isRitualActive()) tag.putInt("Ritual", this.ritualTicks);
        if (this.isRitualActive() && this.spawnPos != null) tag.putLong("RitualSpawn", this.spawnPos.asLong());
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
        if (this.isRitualActive()) tag.putInt("Ritual", this.ritualTicks);
        if (this.isRitualActive() && this.spawnPos != null) tag.putLong("RitualSpawn", this.spawnPos.asLong());
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

    public void markUpdated() {
        this.setChanged();
        Objects.requireNonNull(this.getLevel()).sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public void clearContent() {
        this.displayedItem = ItemStack.EMPTY;
        this.updateBlockState(false);
    }
}