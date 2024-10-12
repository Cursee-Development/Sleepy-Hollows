package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import net.satisfy.sleepy_hollows.client.util.SanityManager;

import java.util.Objects;

public class InfectedEffect extends MobEffect {

    private static final int SANITY_INCREASE_INTERVAL = 60;
    private static final int TOTAL_SANITY_DURATION = 300;

    public InfectedEffect() {
        super(MobEffectCategory.HARMFUL, 0x800080);
    }

    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (!livingEntity.level().isClientSide()) {
            if (livingEntity instanceof Player player) {
                player.hurt(player.damageSources().wither(), 1.0F);

                int effectDuration = Objects.requireNonNull(player.getEffect(this)).getDuration();
                if (effectDuration % SANITY_INCREASE_INTERVAL == 0 && effectDuration <= TOTAL_SANITY_DURATION) {
                    SanityManager.increaseSanity(player);
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
