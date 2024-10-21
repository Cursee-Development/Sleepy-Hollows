package net.satisfy.sleepy_hollows.core.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.sleepy_hollows.core.registry.ToolTiersRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class RaubbauItem extends ShovelItem {
    public RaubbauItem(Properties properties) {
        super(ToolTiersRegistry.RAUBBAU, ToolTiersRegistry.RAUBBAU.getAttackDamageBonus(), ToolTiersRegistry.RAUBBAU.getSpeed(), properties);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, Level world, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        if (!world.isClientSide && state.getDestroySpeed(world, pos) != 0.0F) {
            if (entityLiving instanceof Player) {
                Random random = new Random();
                if (random.nextFloat() < 0.2) {
                    Player player = (Player) entityLiving;
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 120, 5));
                }
            }
        }
        return super.mineBlock(stack, world, state, pos, entityLiving);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.sleepy_hollows.lore.raubbau").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }
}
