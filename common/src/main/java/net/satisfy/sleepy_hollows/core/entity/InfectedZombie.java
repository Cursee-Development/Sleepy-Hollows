package net.satisfy.sleepy_hollows.core.entity;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.satisfy.sleepy_hollows.core.registry.MobEffectRegistry;
import net.satisfy.sleepy_hollows.core.registry.ObjectRegistry;
import org.jetbrains.annotations.NotNull;

public class InfectedZombie extends Zombie {
    public InfectedZombie(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23000000417232513)
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.ARMOR, 3.0)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    protected boolean isSunSensitive() {
        return false;
    }

    @Override
    protected void populateDefaultEquipmentSlots(@NotNull RandomSource randomSource, @NotNull DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        if (randomSource.nextFloat() < 0.3f) {
            this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(ObjectRegistry.SPECTRAL_LANTERN.get()));
        }

        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ObjectRegistry.SPECTRAL_JACK_O_LANTERN.get()));
        super.populateDefaultEquipmentSlots(randomSource, difficulty);
    }


    public boolean canBeAffected(MobEffectInstance effectInstance) {
        return effectInstance.getEffect() != MobEffectRegistry.INFECTED.get() && super.canBeAffected(effectInstance);
    }
}
