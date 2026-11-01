package com.phantomwing.thesilverage.neoforge.mixin;

import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import com.phantomwing.thesilverage.combat.SilverThornsHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Silver armour's burn on undead attackers, and the tools' bonus shown, once a hit has landed: {@code hurtServer} answers true when it did, as it does
 * for Thorns, and false for a hit a shield took whole. Fabric's twin mixin is the same, so the loaders agree on which
 * hits count.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("RETURN"))
    private void thesilverage$silverThorns(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            SilverThornsHandler.onHurt((LivingEntity) (Object) this, source);
            SilverSmiteHandler.onHurt((LivingEntity) (Object) this, source);
        }
    }
}
