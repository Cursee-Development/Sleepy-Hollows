package net.satisfy.sleepy_hollows.core.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.satisfy.sleepy_hollows.SleepyHollows;
import org.jetbrains.annotations.NotNull;

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
            world.playSound(null, player.blockPosition().above(),
                    SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.PLAYERS, 1, 1);
            final MinecraftServer minecraftServer = player.level().getServer();
            if (minecraftServer != null && player.level() instanceof ServerLevel server) {
                LootParams lootContext = new LootParams.Builder(server)
                        .withParameter(LootContextParams.THIS_ENTITY, player)
                        .withParameter(LootContextParams.ORIGIN, player.position())
                        .create(LootContextParamSets.GIFT);
                LootTable treasure = minecraftServer.reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, SleepyHollows.identifier("gameplay/lootbag")));

                for (ItemStack loot : treasure.getRandomItems(lootContext)) {
                    if (!player.addItem(loot)) {
                        player.drop(loot, false);
                    }
                }
            }
        }
        stack.shrink(1);
        return resultHolder;
    }
}
