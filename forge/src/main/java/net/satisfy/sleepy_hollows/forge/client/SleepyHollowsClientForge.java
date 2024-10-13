package net.satisfy.sleepy_hollows.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.satisfy.sleepy_hollows.Constants;
import net.satisfy.sleepy_hollows.client.SleepyHollowsClient;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SleepyHollowsClientForge {
    @SubscribeEvent
    public static void beforeClientSetup(RegisterEvent event) {
        SleepyHollowsClient.PreinitClient();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        SleepyHollowsClient.initClient();
    }
}
