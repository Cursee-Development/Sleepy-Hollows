package net.satisfy.sleepy_hollows.core.effect;

import net.satisfy.foundation.overlay.BlockNotice;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.phys.Vec3;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;
import org.jetbrains.annotations.NotNull;

public class BadDreamEffect extends MobEffect {
    public BadDreamEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (!(entity instanceof ServerPlayer player)) return true;
        MobEffectInstance instance = player.getEffect(MobEffectRegistry.holder(MobEffectRegistry.BAD_DREAM));
        if (instance == null || instance.getDuration() != 1) return true;

        ServerLevel targetLevel = player.server.getLevel(player.getRespawnDimension());
        BlockPos respawnPos = player.getRespawnPosition();
        if (targetLevel == null || respawnPos == null || !(targetLevel.getBlockState(respawnPos).getBlock() instanceof BedBlock)) {
            targetLevel = player.serverLevel();
            respawnPos = targetLevel.getSharedSpawnPos();
        }
        Vec3 pos = Vec3.atBottomCenterOf(respawnPos);
        player.teleportTo(targetLevel, pos.x, pos.y, pos.z, Mth.wrapDegrees(player.getYRot()), Mth.wrapDegrees(player.getXRot()));
        BlockNotice.send(player, BlockPos.containing(player.getEyePosition().add(player.getLookAngle().scale(3))), Component.translatable("message.sleepy_hollows.insanity"));
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
