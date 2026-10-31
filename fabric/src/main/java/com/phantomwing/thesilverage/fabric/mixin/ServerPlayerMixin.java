package com.phantomwing.thesilverage.fabric.mixin;

import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * A silver tool's bonus on a player's swing, added to its enchanted damage as Smite's is, so {@code Player#attack}
 * scales it by the swing's charge and shows it with the magic crit. {@code ServerPlayer} is where the enchantments add
 * theirs: {@code Player}'s own only gives the damage back. NeoForge's twin mixin is the same.
 */
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Inject(method = "getEnchantedDamage(Lnet/minecraft/world/entity/Entity;FLnet/minecraft/world/damagesource/DamageSource;)F",
            at = @At("RETURN"), cancellable = true)
    private void thesilverage$addSilverSmiteDamage(Entity target, float damage, DamageSource source, CallbackInfoReturnable<Float> cir) {
        float bonus = SilverSmiteHandler.getSwingBonus(target, source);
        if (bonus > 0.0f) {
            cir.setReturnValue(cir.getReturnValueF() + bonus);
        }
    }
}
