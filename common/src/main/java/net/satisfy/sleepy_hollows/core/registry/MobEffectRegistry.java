package net.satisfy.sleepy_hollows.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.satisfy.sleepy_hollows.SleepyHollows;
import net.satisfy.sleepy_hollows.core.effect.BadDreamEffect;
import net.satisfy.sleepy_hollows.core.effect.InfectedEffect;
import net.satisfy.sleepy_hollows.core.effect.InsanityEffect;
import net.satisfy.sleepy_hollows.core.effect.MentalFortitudeEffect;

public class MobEffectRegistry {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(SleepyHollows.MOD_ID, Registries.MOB_EFFECT);

    public static final RegistrySupplier<MobEffect> INSANITY = MOB_EFFECTS.register("insanity", InsanityEffect::new);
    public static final RegistrySupplier<MobEffect> INFECTED = MOB_EFFECTS.register("infected", InfectedEffect::new);
    public static final RegistrySupplier<MobEffect> MENTAL_FORTITUDE = MOB_EFFECTS.register("mental_fortitude", MentalFortitudeEffect::new);
    public static final RegistrySupplier<MobEffect> BAD_DREAM = MOB_EFFECTS.register("bad_dream", BadDreamEffect::new);

    public static Holder<MobEffect> holder(RegistrySupplier<MobEffect> effect) {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect.get());
    }

    public static void init() {
        MOB_EFFECTS.register();
    }
}
