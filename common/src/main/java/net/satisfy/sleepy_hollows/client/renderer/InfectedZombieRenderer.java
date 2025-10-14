package net.satisfy.sleepy_hollows.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import net.satisfy.sleepy_hollows.SleepyHollows;
import org.jetbrains.annotations.NotNull;

public class InfectedZombieRenderer extends ZombieRenderer {
    public InfectedZombieRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Zombie entity) {
        return SleepyHollows.identifier("textures/entity/infected_zombie.png");
    }
}
