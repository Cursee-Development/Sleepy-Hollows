package net.satisfy.sleepy_hollows.core.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.satisfy.sleepy_hollows.core.util.SanityManager;
import org.jetbrains.annotations.NotNull;

public class SpectralPumpkinPieItem extends Item {
    public SpectralPumpkinPieItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity) {
        if (!world.isClientSide() && entity instanceof ServerPlayer player) {
            SanityManager.changeSanity(player, SanityManager.Modifiers.SPECTRAL_PUMPKIN_PIE);
        }
        return super.finishUsingItem(stack, world, entity);
    }
}