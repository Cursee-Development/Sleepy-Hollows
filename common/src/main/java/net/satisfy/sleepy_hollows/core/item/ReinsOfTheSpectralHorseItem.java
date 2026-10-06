package net.satisfy.sleepy_hollows.core.item;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.satisfy.sleepy_hollows.core.entity.SpectralHorse;
import net.satisfy.sleepy_hollows.core.registry.EntityTypeRegistry;
import org.jetbrains.annotations.NotNull;

public class ReinsOfTheSpectralHorseItem extends ArchitecturySpawnEggItem {
    public ReinsOfTheSpectralHorseItem(RegistrySupplier<? extends EntityType<? extends Mob>> entityType, int backgroundColor, int highlightColor, Properties properties) {
        super(entityType, backgroundColor, highlightColor, properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (!(level instanceof ServerLevel serverLevel) || player == null) return InteractionResult.SUCCESS;

        BlockPos clicked = context.getClickedPos();
        BlockPos pos = level.getBlockState(clicked).getCollisionShape(level, clicked).isEmpty() ? clicked : clicked.relative(context.getClickedFace());
        SpectralHorse horse = EntityTypeRegistry.SPECTRAL_HORSE.get().create(serverLevel);
        if (horse == null) return InteractionResult.FAIL;

        horse.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, player.getYRot() + 180.0F, 0.0F);
        horse.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(pos), MobSpawnType.SPAWN_EGG, null);
        horse.tameWithName(player);
        horse.equipSaddle(new ItemStack(Items.SADDLE), null);
        horse.setPersistenceRequired();
        serverLevel.addFreshEntity(horse);
        level.playSound(null, pos, SoundEvents.SKELETON_HORSE_AMBIENT, SoundSource.PLAYERS, 1.0F, 1.0F);
        context.getItemInHand().consume(1, player);
        return InteractionResult.CONSUME;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}
