package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;

public class BadDreamEffect extends MobEffect {

    public BadDreamEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide() && livingEntity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getRespawnPosition() != null) {
                ResourceKey<Level> respawnDimension = serverPlayer.getRespawnDimension();
                ServerLevel targetLevel = serverPlayer.server.getLevel(respawnDimension);
                BlockPos respawnPos = serverPlayer.getRespawnPosition();
                if (targetLevel != null && (targetLevel.getBlockState(respawnPos).getBlock() instanceof BedBlock)) {
                    Vec3 pos = Vec3.atBottomCenterOf(respawnPos);
                    serverPlayer.teleportTo(targetLevel, pos.x, pos.y, pos.z, Mth.wrapDegrees(serverPlayer.getYRot()), Mth.wrapDegrees(serverPlayer.getXRot()));
                }
            } else {
                Vec3 pos = Vec3.atBottomCenterOf(serverPlayer.level().getSharedSpawnPos());
                serverPlayer.connection.teleport(pos.x, pos.y, pos.z, Mth.wrapDegrees(serverPlayer.getYRot()), Mth.wrapDegrees(serverPlayer.getXRot()));
            }
        }

        MobEffectInstance currentEffect = livingEntity.getEffect(this);
        if (currentEffect != null && currentEffect.getDuration() == 1) {
            sendEndEffectMessage(livingEntity);
        }
    }

    private void sendEndEffectMessage(LivingEntity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            Component message = Component.translatable("message.sleepy_hollows.insanity").withStyle(style -> style.withItalic(true).withColor(TextColor.fromRgb(0x8A2BE2)));
            serverPlayer.sendSystemMessage(message, false);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration >= 1;
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }
}
