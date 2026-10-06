package net.satisfy.sleepy_hollows.core.item;

import net.satisfy.sleepy_hollows.core.util.SanityManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LuminousWaterItem extends Item {
    public LuminousWaterItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 32;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player.isShiftKeyDown()) return super.use(world, player, hand);
        return ItemUtils.startUsingInstantly(world, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        if (entity instanceof ServerPlayer player) {
            SanityManager.changeSanity(player, SanityManager.Modifiers.LUMINOUS_WATER);
        }
        return super.finishUsingItem(stack, level, entity);
    }
}
