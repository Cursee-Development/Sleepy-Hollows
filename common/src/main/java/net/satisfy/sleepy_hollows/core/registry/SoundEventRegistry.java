package net.satisfy.sleepy_hollows.core.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;

public class SoundEventRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Constants.MOD_ID, Registries.SOUND_EVENT);

    public static final RegistrySupplier<SoundEvent> COFFIN_OPEN = create("coffin_open");
    public static final RegistrySupplier<SoundEvent> COFFIN_CLOSE = create("coffin_close");
    public static final RegistrySupplier<SoundEvent> PLANKS_CREAKING = create("planks_creaking");
    public static final RegistrySupplier<SoundEvent> SLEEPY_HOLLOWS_AMBIENT = create("sleepy_hollows_ambient");

    private static RegistrySupplier<SoundEvent> create(String name) {
        ResourceLocation id = new SleepyHollowsIdentifier(name);
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void init() {
        SOUND_EVENTS.register();
    }
}
