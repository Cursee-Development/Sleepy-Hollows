package net.satisfy.sleepy_hollows.core.registry;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.effect.SanityEffect;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;

import java.util.function.Supplier;

public class MobEffectRegistry {
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Constants.MOD_ID, Registries.MOB_EFFECT);
    private static final Registrar<MobEffect> MOB_EFFECTS_REGISTRAR = MOB_EFFECTS.getRegistrar();

    public static final RegistrySupplier<MobEffect> SANITY;

    private static RegistrySupplier<MobEffect> registerEffect(String name, Supplier<MobEffect> effect){
        if(Platform.isForge()){
            return MOB_EFFECTS.register(name, effect);
        }
        return MOB_EFFECTS_REGISTRAR.register(new SleepyHollowsIdentifier(name), effect);
    }

    public static void init(){
        MOB_EFFECTS.register();
    }

    static {
        SANITY = registerEffect("sanity", SanityEffect::new);
    }
}
