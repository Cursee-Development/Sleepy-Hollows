package net.satisfy.sleepy_hollows.core.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticleArc {
    private final Vec3 startPos;
    private final Vec3 endPos;
    private final Vec3 midPos;
    private final int numSteps;
    private int currentStep;
    private final int stepsPerTick;

    public ParticleArc(Vec3 startPos, Vec3 endPos, int duration) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.midPos = startPos.add(endPos).scale(0.5).add(0, 5, 0);
        this.numSteps = 50;
        this.currentStep = 0;
        this.stepsPerTick = Math.max(1, numSteps / duration);
    }

    public boolean isFinished() {
        return currentStep >= numSteps;
    }

    public void tick(Level level) {
        for (int i = 0; i < stepsPerTick && currentStep < numSteps; i++, currentStep++) {
            double t = currentStep / (double) numSteps;
            Vec3 point = quadraticBezier(startPos, midPos, endPos, t);
            level.addParticle(ParticleTypes.SOUL, point.x, point.y, point.z, 0.0D, 0.0D, 0.0D);
        }
    }

    private Vec3 quadraticBezier(Vec3 p0, Vec3 p1, Vec3 p2, double t) {
        double oneMinusT = 1 - t;
        return p0.scale(oneMinusT * oneMinusT)
                .add(p1.scale(2 * oneMinusT * t))
                .add(p2.scale(t * t));
    }
}