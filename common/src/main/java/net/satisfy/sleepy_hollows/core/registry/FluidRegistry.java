package net.satisfy.sleepy_hollows.core.registry;

import com.mojang.datafixers.util.Pair;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.core.block.custom.LuminousWaterFluid;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsIdentifier;
import net.satisfy.sleepy_hollows.core.util.SleepyHollowsUtil;

import java.util.function.Supplier;

public class FluidRegistry {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Constants.MOD_ID, Registries.FLUID);
    public static final Registrar<Fluid> FLUID_REGISTRAR = FLUIDS.getRegistrar();

    public static Pair<Supplier<Fluid>, Supplier<Fluid>> LUMINOUS_WATER = Pair.of(
            LuminousWaterFluid.Source::new,
            LuminousWaterFluid.Flowing::new
    );

    public static RegistrySupplier<Fluid> LUMINOUS_WATER_SOURCE = registerFluid("luminous_water", LUMINOUS_WATER.getFirst());
    public static RegistrySupplier<Fluid> LUMINOUS_WATER_FLOWING = registerFluid("flowing_luminous_water", LUMINOUS_WATER.getSecond());

    public static <T extends Fluid> RegistrySupplier<T> registerFluid(String path, Supplier<T> fluidSupplier) {
        return SleepyHollowsUtil.abstractFluidRegistration(FLUIDS, FLUID_REGISTRAR, new SleepyHollowsIdentifier(path), fluidSupplier);
    }

    public static void init() {
        FLUIDS.register();
    }
}
