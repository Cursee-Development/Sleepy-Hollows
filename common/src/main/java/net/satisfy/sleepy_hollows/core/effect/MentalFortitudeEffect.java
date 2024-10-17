package net.satisfy.sleepy_hollows.core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import org.jetbrains.annotations.NotNull;

public class MentalFortitudeEffect extends MobEffect {
    public MentalFortitudeEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FF00);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            SanityManager.setSanityImmunity(player, true);
        }
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity livingEntity, @NotNull net.minecraft.world.entity.ai.attributes.AttributeMap attributeMap, int amplifier) {
        if (livingEntity instanceof Player player) {
            SanityManager.setSanityImmunity(player, false);
        }
        super.removeAttributeModifiers(livingEntity, attributeMap, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
