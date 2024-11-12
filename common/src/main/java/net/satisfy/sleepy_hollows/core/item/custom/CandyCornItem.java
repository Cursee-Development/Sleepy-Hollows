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
import net.satisfy.sleepy_hollows.core.network.SleepyHollowsNetwork;
import net.satisfy.sleepy_hollows.core.network.message.SanityPacketMessage;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CandyCornItem extends Item {
    public CandyCornItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3F).alwaysEat().build()));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        if (!world.isClientSide() && entity instanceof ServerPlayer player) {
            SanityManager.changeSanity(player, SanityManager.Modifiers.CANDY_CORN.getValue());
            SleepyHollowsNetwork.SANITY_CHANNEL.sendToPlayer(player, new SanityPacketMessage(SanityManager.Modifiers.CANDY_CORN.getValue()));
        }
        return super.finishUsingItem(stack, world, entity);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.sleepy_hollows.item.candy_corn"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}