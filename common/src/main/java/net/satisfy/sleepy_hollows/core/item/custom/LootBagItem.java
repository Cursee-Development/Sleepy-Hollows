package net.satisfy.sleepy_hollows.core.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.satisfy.sleepy_hollows.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class LootBagItem extends Item {
    public LootBagItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        InteractionResultHolder<ItemStack> resultHolder = super.use(world, player, hand);
        ItemStack stack = resultHolder.getObject();
        player.swing(hand);
        if (!world.isClientSide) {
            world.playSound(player, player.blockPosition().above(),
                    SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1, 1);
            final MinecraftServer minecraftServer = player.level().getServer();
            if (minecraftServer != null && player.level() instanceof ServerLevel server) {
                LootParams lootContext = new LootParams.Builder(server)
                        .withParameter(LootContextParams.THIS_ENTITY, player)
                        .withParameter(LootContextParams.ORIGIN, player.position())
                        .create(LootContextParamSets.GIFT);
                LootTable treasure = minecraftServer.getLootData().getLootTable(new ResourceLocation(Constants.MOD_ID, "gameplay/lootbag"));

                Random random = new Random();
                int itemCount = 2 + random.nextInt(3);

                List<ItemStack> lootItems = treasure.getRandomItems(lootContext);
                for (int i = 0; i < itemCount && i < lootItems.size(); i++) {
                    player.addItem(lootItems.get(i));
                }
            }
        }
        stack.shrink(1);
        return resultHolder;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.sleepy_hollows.item.lootbag").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }
}
