package net.satisfy.sleepy_hollows.core.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LuminousWaterItem extends Item {
    public LuminousWaterItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3F).build()));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        if (entity instanceof Player player) {
            SanityManager.decreaseSanity(player, 6);
        }
        return super.finishUsingItem(stack, world, entity);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 32;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        return ItemUtils.startUsingInstantly(world, player, hand);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.sleepy_hollows.luminous_water"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}