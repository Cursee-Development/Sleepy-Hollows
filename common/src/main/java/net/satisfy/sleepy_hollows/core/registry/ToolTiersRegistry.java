package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public enum ToolTiersRegistry implements Tier {

    SPECTRAL(4, 2031, PlatformHelper.getSpectralToolSpeed(), PlatformHelper.getSpectralToolDamage(), 15, () -> Ingredient.of(ObjectRegistry.LUMINOUS_ESSENCE.get())),
    RAUBBAU(4, 2031, PlatformHelper.getRaubbauToolSpeed(), PlatformHelper.getRaubbauToolDamage(), 15, () -> Ingredient.of(ObjectRegistry.LUMINOUS_ESSENCE.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ToolTiersRegistry(int j, int k, double speed, double damage, int l, Supplier<Ingredient> supplier) {
        this.level = j;
        this.uses = k;
        this.speed = (float) speed;
        this.damage = (float) damage;
        this.enchantmentValue = l;
        this.repairIngredient = new LazyLoadedValue<>(supplier);
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
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
