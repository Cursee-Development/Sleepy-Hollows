package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public enum ToolTiersRegistry implements Tier {
    SPECTRAL(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2031, (float) PlatformHelper.getSpectralToolSpeed(), (float) PlatformHelper.getSpectralToolDamage(), 15, () -> Ingredient.of(ObjectRegistry.LUMINOUS_ESSENCE.get())),
    RAUBBAU(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2031, (float) PlatformHelper.getRaubbauToolSpeed(), (float) PlatformHelper.getRaubbauToolDamage(), 15, () -> Ingredient.of(ObjectRegistry.LUMINOUS_ESSENCE.get()));

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ToolTiersRegistry(TagKey<Block> incorrectBlocksForDrops, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairSupplier) {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairSupplier);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }
}