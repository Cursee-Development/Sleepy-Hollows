package net.satisfy.sleepy_hollows.core.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.satisfy.sleepy_hollows.core.registry.ToolTiersRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class RaubbauItem extends ShovelItem {
    public RaubbauItem(Properties properties) {
        super(ToolTiersRegistry.RAUBBAU, properties.attributes(ShovelItem.createAttributes(ToolTiersRegistry.RAUBBAU, 1.5F, -3.0F)));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide) {
            HolderLookup.Provider provider = level.registryAccess();
            HolderLookup.RegistryLookup<Enchantment> enchRegistry = provider.lookupOrThrow(Registries.ENCHANTMENT);
            Holder<Enchantment> efficiency = enchRegistry.getOrThrow(Enchantments.EFFICIENCY);
            if (EnchantmentHelper.getItemEnchantmentLevel(efficiency, stack) < 8) {
                EnchantmentHelper.updateEnchantments(stack, mutable -> mutable.upgrade(efficiency, 8));
            }
        }
        super.inventoryTick(stack, level, entity, slot, selected);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, Level world, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            if (player.isUsingItem() && player.getUseItem() == stack) {
                player.stopUsingItem();
            }
            player.swing(InteractionHand.MAIN_HAND, true);
        }
        if (!world.isClientSide && state.getDestroySpeed(world, pos) != 0.0F) {
            if (entityLiving instanceof Player) {
                Random random = new Random();
                if (random.nextFloat() < 0.2F) {
                    Player player = (Player) entityLiving;
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 120, 5));
                }
            }
        }
        return super.mineBlock(stack, world, state, pos, entityLiving);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull Item.TooltipContext context, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.sleepy_hollows.lore.raubbau").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }
}