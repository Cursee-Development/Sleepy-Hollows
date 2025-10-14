package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.core.registries.Registries;
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
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide() && entity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getRespawnPosition() != null) {
                ResourceKey<Level> respawnDimension = serverPlayer.getRespawnDimension();
                ServerLevel targetLevel = serverPlayer.server.getLevel(respawnDimension);
                BlockPos respawnPos = serverPlayer.getRespawnPosition();
                if (targetLevel != null && targetLevel.getBlockState(respawnPos).getBlock() instanceof BedBlock) {
                    Vec3 pos = Vec3.atBottomCenterOf(respawnPos);
                    serverPlayer.teleportTo(targetLevel, pos.x, pos.y, pos.z,
                            Mth.wrapDegrees(serverPlayer.getYRot()), Mth.wrapDegrees(serverPlayer.getXRot()));
                }
            } else {
                ServerLevel currentLevel = serverPlayer.serverLevel();
                Vec3 pos = Vec3.atBottomCenterOf(currentLevel.getSharedSpawnPos());
                serverPlayer.teleportTo(currentLevel, pos.x, pos.y, pos.z,
                        Mth.wrapDegrees(serverPlayer.getYRot()), Mth.wrapDegrees(serverPlayer.getXRot()));
            }
        }

        var effectHolder = entity.level().registryAccess()
                .registryOrThrow(Registries.MOB_EFFECT)
                .getResourceKey(this)
                .flatMap(k -> entity.level().registryAccess().registryOrThrow(Registries.MOB_EFFECT).getHolder(k));

        effectHolder.ifPresent(holder -> {
            MobEffectInstance instance = entity.getEffect(holder);
            if (instance != null && instance.getDuration() == 1) {
                sendEndEffectMessage(entity);
            }
        });

        return true;
    }

    private void sendEndEffectMessage(LivingEntity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            Component message = Component.translatable("message.sleepy_hollows.insanity").withStyle(style -> style.withItalic(true).withColor(TextColor.fromRgb(0x8A2BE2)));
            serverPlayer.sendSystemMessage(message, false);
        }
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }
}
