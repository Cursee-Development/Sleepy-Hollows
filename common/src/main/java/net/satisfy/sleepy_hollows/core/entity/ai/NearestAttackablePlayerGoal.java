package net.satisfy.sleepy_hollows.core.entity.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class NearestAttackablePlayerGoal extends Goal {
    private final Mob mob;
    private final double attackRadius;

    public NearestAttackablePlayerGoal(Mob mob, double attackRadius) {
        this.mob = mob;
        this.attackRadius = attackRadius;
    }

    @Override
    public boolean canUse() {
        AABB searchArea = this.mob.getBoundingBox().inflate(attackRadius);
        List<Player> playersInRange = this.mob.level().getEntitiesOfClass(Player.class, searchArea);

        return playersInRange.stream().anyMatch(player -> !player.isCreative() && !player.isSpectator());
    }

    @Override
    public void tick() {
        AABB searchArea = this.mob.getBoundingBox().inflate(attackRadius);
        List<Player> playersInRange = this.mob.level().getEntitiesOfClass(Player.class, searchArea);

        if (!playersInRange.isEmpty()) {
            playersInRange.stream()
                    .filter(player -> !player.isCreative() && !player.isSpectator())
                    .findFirst().ifPresent(this.mob::setTarget);
        }
    }
}
