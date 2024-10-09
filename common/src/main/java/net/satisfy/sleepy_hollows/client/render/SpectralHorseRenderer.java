package net.satisfy.sleepy_hollows.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.satisfy.sleepy_hollows.client.model.SpectralHorseModel;
import net.satisfy.sleepy_hollows.core.entity.SpectralHorse;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import org.jetbrains.annotations.NotNull;

@Environment(value= EnvType.CLIENT)
public class SpectralHorseRenderer<T extends SpectralHorse> extends MobRenderer<T, SpectralHorseModel<T>> {
    private static final ResourceLocation TEXTURE = new SleepyHollowsIdentifier("textures/entity/spectral_horse.png");

    public SpectralHorseRenderer(EntityRendererProvider.Context context) {
        super(context, new SpectralHorseModel<>(context.bakeLayer(SpectralHorseModel.LAYER_LOCATION)), 1.1f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SpectralHorse entity) {
        return TEXTURE;
    }

}
