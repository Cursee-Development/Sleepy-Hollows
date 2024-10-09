package net.satisfy.sleepy_hollows.core.item.custom;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReinsOfTheSpectralHorseItem extends ArchitecturySpawnEggItem {
    public ReinsOfTheSpectralHorseItem(RegistrySupplier<? extends EntityType<? extends Mob>> entityType, int backgroundColor, int highlightColor, Properties properties) {
        super(entityType, backgroundColor, highlightColor, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.sleepy_hollows.reins_of_the_spectral_horse").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (!world.isClientSide) {
            world.playSound(null, context.getClickedPos(), SoundEvents.SKELETON_HORSE_AMBIENT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return super.useOn(context);
    }
}
