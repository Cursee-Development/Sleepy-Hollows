package net.satisfy.sleepy_hollows.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import org.jetbrains.annotations.Nullable;

public final class MobSpawnHelper {
    private MobSpawnHelper() {
    }

    public static boolean canSpawnAt(Level level, BlockPos pos) {
        BlockPos below = pos.below();
        return level.isEmptyBlock(pos) && level.isEmptyBlock(pos.above()) && !level.getBlockState(below).getCollisionShape(level, below).isEmpty();
    }

    public static @Nullable BlockPos findGroundedSpawn(Level level, BlockPos origin, int climb, int radius) {
        BlockPos found = groundedAt(level, origin.getX(), origin.getZ(), climb);
        for (int dx = -radius; found == null && dx <= radius; dx++) {
            for (int dz = -radius; found == null && dz <= radius; dz++) {
                if (dx != 0 || dz != 0) found = groundedAt(level, origin.getX() + dx, origin.getZ() + dz, climb);
            }
        }
        return found;
    }

    private static @Nullable BlockPos groundedAt(Level level, int x, int z, int climb) {
        BlockPos ground = new BlockPos(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z), z);
        for (int dy = 0; dy <= climb; dy++) {
            if (canSpawnAt(level, ground.above(dy))) return ground.above(dy);
        }
        return null;
    }

    public static void spawnAt(Level level, Mob mob, BlockPos pos) {
        mob.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, level.random.nextFloat() * 360.0F, 0.0F);
        level.addFreshEntity(mob);
    }

    public static void equip(Mob mob, EquipmentSlot slot, ItemStack stack, float dropChance) {
        mob.setItemSlot(slot, stack);
        mob.setDropChance(slot, dropChance);
    }

    public static void equipHauntbound(Mob mob, ItemStack head, ItemStack mainHand, float armorDropChance) {
        equip(mob, EquipmentSlot.HEAD, head, 0.1F);
        equip(mob, EquipmentSlot.CHEST, new ItemStack(ObjectRegistry.HAUNTBOUND_CHESTPLATE.get()), armorDropChance);
        equip(mob, EquipmentSlot.LEGS, new ItemStack(ObjectRegistry.HAUNTBOUND_LEGGINGS.get()), armorDropChance);
        equip(mob, EquipmentSlot.FEET, new ItemStack(ObjectRegistry.HAUNTBOUND_BOOTS.get()), armorDropChance);
        equip(mob, EquipmentSlot.MAINHAND, mainHand, armorDropChance);
    }
}
