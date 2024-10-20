package net.satisfy.sleepy_hollows.core.event;

import dev.architectury.event.events.common.TickEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.satisfy.sleepy_hollows.client.util.SanityManager;
import net.satisfy.sleepy_hollows.core.item.custom.HauntboundBootsItem;
import net.satisfy.sleepy_hollows.core.item.custom.HauntboundChestplateItem;
import net.satisfy.sleepy_hollows.core.item.custom.HauntboundHelmetItem;
import net.satisfy.sleepy_hollows.core.item.custom.HauntboundLeggingsItem;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;

public class ArmorEffectHandler {
    public static void init() {
        TickEvent.PLAYER_POST.register(ArmorEffectHandler::onPlayerTick);
    }
    private static int tickCounter = 0;

    private static void onPlayerTick(Player player) {
        if (player.level().isClientSide()) return;

        tickCounter++;
        if (tickCounter >= 10) {
            handleArmorEffect(player);
            tickCounter = 0;
        }
    }

    private static void handleArmorEffect(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        boolean hasFullSet = helmet.getItem() instanceof HauntboundHelmetItem &&
                chestplate.getItem() instanceof HauntboundChestplateItem &&
                leggings.getItem() instanceof HauntboundLeggingsItem &&
                boots.getItem() instanceof HauntboundBootsItem;

        if (hasFullSet) {
            if (!SanityManager.isImmune(player)) {
                player.addEffect(new MobEffectInstance(MobEffectRegistry.MENTAL_FORTITUDE.get(), 20, 0, true, false));
            }
        } else {
            if (player.hasEffect(MobEffectRegistry.MENTAL_FORTITUDE.get())) {
                player.removeEffect(MobEffectRegistry.MENTAL_FORTITUDE.get());
            }
        }
    }
}
