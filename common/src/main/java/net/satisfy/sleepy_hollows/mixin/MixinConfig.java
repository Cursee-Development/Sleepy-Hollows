package net.satisfy.sleepy_hollows.mixin;

import net.satisfy.sleepy_hollows.Constants;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinConfig implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
        // load configuration from a file here.
        Constants.LOG.info("Configuration for Mixins has loaded!");
    }

    @Override @Deprecated(forRemoval = false)
    public String getRefMapperConfig() {
        return "";
    }

    @Override @Deprecated(forRemoval = false)
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return false;
    }

    @Override @Deprecated(forRemoval = false)
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override @Deprecated(forRemoval = false)
    public List<String> getMixins() {
        return List.of();
    }

    @Override @Deprecated(forRemoval = false)
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override @Deprecated(forRemoval = false)
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
