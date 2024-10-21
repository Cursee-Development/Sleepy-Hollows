package net.satisfy.sleepy_hollows.core.entity.ai;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

public interface RandomAction {
    boolean isInterruptable();

    void onStart();

    default void onTick(int tick) {
    }

    void onStop();

    boolean isPossible();

    int duration();

    float chance();

    default boolean canMove() {
        return false;
    }

    AttributeInstance getAttribute(Attribute movementSpeed);
}
