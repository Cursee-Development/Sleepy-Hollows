package net.satisfy.sleepy_hollows.fabric.terrablender;

import net.satisfy.sleepy_hollows.core.world.SleepyHollowsRegion;
import terrablender.api.TerraBlenderApi;

public class TerrablenderFabric implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        SleepyHollowsRegion.loadTerrablender();
    }
}