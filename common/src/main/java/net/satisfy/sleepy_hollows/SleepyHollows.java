package net.satisfy.sleepy_hollows;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.satisfy.sleepy_hollows.client.event.HUDRenderEvent;
import net.satisfy.sleepy_hollows.client.event.PlayerTickEvent;
import net.satisfy.sleepy_hollows.core.registry.*;

public final class SleepyHollows {

    public static void init() {
        ObjectRegistry.init();
        CompostableRegistry.init();
        TabRegistry.init();
        MobEffectRegistry.init();
        EntityTypeRegistry.init();
        FeatureTypeRegistry.init();

        Constants.LOG.info("Initialized the mod in Common.");

        if (Platform.getEnv() == EnvType.CLIENT) {
            ClientGuiEvent.RENDER_HUD.register(HUDRenderEvent::onRenderHUD);
            ClientTickEvent.CLIENT_POST.register(PlayerTickEvent::onClientTick);
        }
    }
}
