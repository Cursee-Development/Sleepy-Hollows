package net.satisfy.sleepy_hollows.core.entity.ai;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Random;

public class RandomActionGoal extends Goal {
    private final RandomAction action;
    int counter;

    public RandomActionGoal(RandomAction mob) {
        this.action = mob;
        setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE, Flag.JUMP));
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean isInterruptable() {
        return action.isInterruptable();
    }


    @Override
    public boolean canUse() {
        var r = new Random().nextFloat();
        return r < action.chance() && action.isPossible();
    }

    @Override
    public boolean canContinueToUse() {
        return counter > 0 && counter < action.duration() && action.isPossible();
    }

    @Override
    public void tick() {
        counter++;
        action.onTick(counter);
    }

    public static final AttributeModifier modifier = new AttributeModifier("random_action_do_not_move", -1000, AttributeModifier.Operation.ADDITION);

    @Override
    public void start() {
        counter = 0;
        action.onStart();
        Objects.requireNonNull(action.getAttribute(Attributes.MOVEMENT_SPEED)).addTransientModifier(modifier);
        super.start();
    }

    @Override
    public void stop() {
        action.onStop();
        Objects.requireNonNull(action.getAttribute(Attributes.MOVEMENT_SPEED)).removeModifier(modifier);
        super.stop();
    }
}
