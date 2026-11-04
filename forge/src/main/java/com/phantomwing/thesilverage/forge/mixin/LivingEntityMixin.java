package com.phantomwing.thesilverage.forge.mixin;

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
 * Silver against the undead on Forge, as Fabric's twin mixin has it: the tools' and arrows' bonus damage added at the
 * head of {@code hurt}, before the damage cooldown and armour, and the armour's burn on undead attackers once a hit
 * has landed. Forge's {@code LivingHurtEvent} comes later, inside {@code actuallyHurt}, so a bonus added there skipped
 * a hit inside the cooldown that Fabric's counted, and the loaders disagreed on the damage.
 *
 * <p>Silver armour's burn on undead attackers, and the tools' bonus shown, once a hit has landed: {@code hurt} answers true when it did, as it does
 * for Thorns, and false for a hit a shield took whole. Fabric's twin mixin is the same, so the loaders agree on which
 * hits count.</p>
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    // Mixin wants the target's full argument list after the modified value, hence the float twice.
    @ModifyVariable(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float thesilverage$addSilverSmiteDamage(float value, DamageSource source, float amount) {
        float bonus = SilverSmiteHandler.handleIncomingDamage((LivingEntity) (Object) this, source);
        return bonus > 0.0f ? value + bonus : value;
    }

    @Inject(method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", at = @At("RETURN"))
    private void thesilverage$silverThorns(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            SilverThornsHandler.onHurt((LivingEntity) (Object) this, source);
            SilverSmiteHandler.onHurt((LivingEntity) (Object) this, source);
        }
    }
}
