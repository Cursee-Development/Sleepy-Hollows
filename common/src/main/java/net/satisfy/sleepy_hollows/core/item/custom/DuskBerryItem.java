package net.satisfy.sleepy_hollows.core.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DuskBerryItem extends ItemNameBlockItem {

    public DuskBerryItem(Block block, Properties properties) {
        super(block, properties.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3F).build()));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        if (entity instanceof Player player) {
            SanityManager.decreaseSanity(player, 2);
        }
        return super.finishUsingItem(stack, world, entity);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.sleepy_hollows.dusk_berry").withStyle(style -> style.withColor(0xFFFFFF)));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}