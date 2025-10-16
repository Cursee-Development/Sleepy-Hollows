package net.satisfy.sleepy_hollows.core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.satisfy.sleepy_hollows.core.registry.ToolTiersRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class SpectralWarAxeItem extends AxeItem {
    public SpectralWarAxeItem(Properties properties) {
        super(ToolTiersRegistry.SPECTRAL, properties.attributes(ShovelItem.createAttributes(ToolTiersRegistry.SPECTRAL, 1.0F, -2.8F)));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (result && !target.getCommandSenderWorld().isClientSide()) {
            double originalArmor = Objects.requireNonNull(target.getAttribute(Attributes.ARMOR)).getValue();
            double armorPenetration = originalArmor * 0.5;

            double damageWithoutArmor = Objects.requireNonNull(attacker.getAttribute(Attributes.ATTACK_DAMAGE)).getValue();
            double finalDamage = damageWithoutArmor - (armorPenetration / 5.0);

            target.hurt(attacker.level().damageSources().mobAttack(attacker), (float) finalDamage);

            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120, 0));
        }

        if (!target.level().isClientSide) {
            if (target.level() instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 10; ++i) {
                    double px = target.getX() + target.getLookAngle().x;
                    double py = target.getY() + target.getBbHeight();
                    double pz = target.getZ() + target.getLookAngle().z;

                    serverLevel.sendParticles(ParticleTypes.WHITE_ASH, px, py, pz, 1, 0.0, 0.0, 0.0, 0.02);
                    serverLevel.sendParticles(ParticleTypes.SOUL, px, py, pz, 1, 0.0, 0.0, 0.0, 0.02);
                }
            }
        }

        return result;
    }


    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.sleepy_hollows.lore.spectral_waraxe").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }
}