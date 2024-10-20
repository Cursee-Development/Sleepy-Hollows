package net.satisfy.sleepy_hollows.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.satisfy.sleepy_hollows.core.entity.LingeringSoul;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class TamableAnimalMixin {

    @Inject(method = "remove", at = @At("HEAD"))
    private void dontLeaveMeIBegYou(Entity.RemovalReason reason, CallbackInfo ci) {
        if ((Object)this instanceof TamableAnimal pet) {
            if (pet.isTame()) {
                LingeringSoul lingeringSoul = new LingeringSoul(pet.level(), pet);
                lingeringSoul.setPos(pet.getX(), pet.getY(), pet.getZ());
                pet.level().addFreshEntity(lingeringSoul);
            }
        }
    }

}
