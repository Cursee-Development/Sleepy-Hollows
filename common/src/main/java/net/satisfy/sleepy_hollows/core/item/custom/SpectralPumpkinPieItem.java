package net.satisfy.sleepy_hollows.core.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpectralPumpkinPieItem extends Item {
    public SpectralPumpkinPieItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3F).build()));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        if (entity instanceof Player player) {
            if (!world.isClientSide()) {
                SanityManager.changeSanity(player, SanityManager.Modifiers.SPECTRAL_PUMPKIN_PIE.getValue()); 
                SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer((ServerPlayer) player, new SanityPacketMessage(SanityManager.Modifiers.SPECTRAL_PUMPKIN_PIE.getValue())); 
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.sleepy_hollows.item.spectral_pumpkin_pie"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}