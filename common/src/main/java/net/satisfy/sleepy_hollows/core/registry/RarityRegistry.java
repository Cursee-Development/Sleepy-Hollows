package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.world.level.ItemLike;
import net.satisfy.foundation.rarity.FoundationRarities;
import net.satisfy.foundation.rarity.FoundationRarity;

import java.util.function.Supplier;

public final class RarityRegistry {
    private RarityRegistry() {
    }

    public static void init() {
        register(FoundationRarity.COMMON,
                ObjectRegistry.INFECTED_ZOMBIE_SPAWN_EGG,
                ObjectRegistry.FLEEING_PUMPKIN_HEAD_SPAWN_EGG,
                ObjectRegistry.HORSEMAN_SPAWN_EGG);
        register(FoundationRarity.UNCOMMON,
                ObjectRegistry.SPECTRAL_ESSENCE,
                ObjectRegistry.DUSK_BERRIES,
                ObjectRegistry.CANDY_CORN,
                ObjectRegistry.SPECTRAL_LANTERN);
        register(FoundationRarity.RARE,
                ObjectRegistry.MUSIC_DISC_SLEEPY_HOLLOWS,
                ObjectRegistry.ESSENCE_OF_UNDEAD,
                ObjectRegistry.HAUNTBOUND_BOOTS,
                ObjectRegistry.HAUNTBOUND_CHESTPLATE,
                ObjectRegistry.SPECTRAL_PUMPKIN_PIE);
        register(FoundationRarity.EPIC,
                ObjectRegistry.HAUNTBOUND_HELMET,
                ObjectRegistry.HAUNTBOUND_LEGGINGS,
                ObjectRegistry.LUMINOUS_ESSENCE);
        register(FoundationRarity.MYTHIC,
                ObjectRegistry.LUMINOUS_WATER,
                ObjectRegistry.LUMINOUS_WATER_SPLASH,
                ObjectRegistry.LOOTBAG);
        register(FoundationRarity.LEGENDARY,
                ObjectRegistry.SPECTRAL_WARAXE,
                ObjectRegistry.SLEEPY_HOLLOWS_STANDARD);
        register(FoundationRarity.DIVINE,
                ObjectRegistry.REINS_OF_THE_SPECTRAL_HORSE);
        register(FoundationRarity.ETERNAL,
                ObjectRegistry.RAUBBAU,
                ObjectRegistry.SHATTERBRAND);
    }

    @SafeVarargs
    private static void register(FoundationRarity rarity, Supplier<? extends ItemLike>... items) {
        for (Supplier<? extends ItemLike> item : items) {
            FoundationRarities.register(item.get(), rarity);
        }
    }
}
