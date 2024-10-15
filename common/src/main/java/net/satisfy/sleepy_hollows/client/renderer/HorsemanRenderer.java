package net.satisfy.sleepy_hollows.client.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.sleepy_hollows.client.model.entity.HorsemanModel;
import net.satisfy.sleepy_hollows.core.entity.Horseman;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import org.jetbrains.annotations.NotNull;

@Environment(value= EnvType.CLIENT)
public class HorsemanRenderer<T extends Horseman> extends MobRenderer<T, HorsemanModel<T>> {
    private static final ResourceLocation TEXTURE = new SleepyHollowsIdentifier("textures/entity/horseman.png");

    public HorsemanRenderer(EntityRendererProvider.Context context) {
        super(context, new HorsemanModel<>(context.bakeLayer(HorsemanModel.LAYER_LOCATION)), 0.75f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Horseman entity) {
        return TEXTURE;
    }

}
