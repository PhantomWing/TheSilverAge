package com.phantomwing.thesilverage.fabric.mixin;

import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import com.phantomwing.thesilverage.combat.SilverThornsHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fabric hooks for silver against the undead: the tools' bonus damage, and the armour's burn on undead attackers.
 *
 * <p>The bonus goes in at the head of {@code LivingEntity#hurt(DamageSource, float)}, before the damage cooldown,
 * armour and resistance, as Forge's twin mixin puts it in (Forge's {@code LivingHurtEvent} comes after the cooldown).
 * A player's own swing takes it in {@code Player#attack} instead ({@code PlayerMixin}). All the gating (config,
 * melee-only, which mobs) lives in {@link SilverSmiteHandler}.</p>
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    // Handler captures the modified value followed by the target's FULL argument
    // list (Mixin rejects a partial capture), hence the duplicated float.
    @ModifyVariable(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float thesilverage$addSilverSmiteDamage(float value, DamageSource source, float amount) {
        float bonus = SilverSmiteHandler.handleIncomingDamage((LivingEntity) (Object) this, source);
        return bonus > 0.0f ? value + bonus : value;
    }

    /**
     * Silver armour's burn on undead attackers, and the tools' bonus shown, once a hit has landed: {@code hurt} answers true when it did, as it
     * does for Thorns, and false for a hit a shield took whole. Forge's twin mixin is the same, so the loaders agree
     * on which hits count. All the gating lives in {@link SilverThornsHandler}.
     */
    @Inject(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("RETURN"))
    private void thesilverage$silverThorns(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            SilverThornsHandler.onHurt((LivingEntity) (Object) this, source);
            SilverSmiteHandler.onHurt((LivingEntity) (Object) this, source);
        }
    }
}
