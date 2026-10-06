package net.satisfy.sleepy_hollows.core.registry;

import net.minecraft.world.entity.LivingEntity;
import net.satisfy.foundation.armor.ArmorSet;
import net.satisfy.sleepy_hollows.platform.PlatformHelper;

public final class ArmorSetRegistry {
    private static ArmorSet hauntbound;

    private ArmorSetRegistry() {
    }

    public static void init() {
        hauntbound = ArmorSet.builder("tooltip.sleepy_hollows.armor.hauntbound_armor_0")
                .piece(ObjectRegistry.HAUNTBOUND_HELMET)
                .piece(ObjectRegistry.HAUNTBOUND_CHESTPLATE)
                .piece(ObjectRegistry.HAUNTBOUND_LEGGINGS)
                .piece(ObjectRegistry.HAUNTBOUND_BOOTS)
                .bonus("tooltip.sleepy_hollows.armor.hauntbound_armor_2")
                .bonusActive(entity -> PlatformHelper.isHauntboundSetBonusEnabled() && hauntbound.isComplete(entity))
                .register();
    }

    public static boolean hasHauntboundBonus(LivingEntity entity) {
        return hauntbound != null && hauntbound.isBonusActive(entity);
    }

    public static int hauntboundPieces(LivingEntity entity) {
        return hauntbound == null ? 0 : hauntbound.wornPieces(entity);
    }
}
