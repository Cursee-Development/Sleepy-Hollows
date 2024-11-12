package net.satisfy.sleepy_hollows.fabric.terrablender;

import net.satisfy.sleepy_hollows.core.world.SleepyHollowsRegion;
import terrablender.api.TerraBlenderApi;
import net.satisfy.sleepy_hollows.fabric.config.InitializationStatus;

public class TerrablenderFabric implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        if (InitializationStatus.isModMenuInitialized()) {
            SleepyHollowsRegion.loadTerrablender();
        } else {
             new Thread(() -> {
                while (!InitializationStatus.isModMenuInitialized()) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                SleepyHollowsRegion.loadTerrablender();
            }).start();
        }
    }
}
