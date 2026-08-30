package com.phantomwing.thesilverage.fabric.mixin;

import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import com.phantomwing.thesilverage.combat.UndeadProtectionHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Fabric hook for the innate silver anti-undead damage.
 *
 * <p>NeoForge adds the bonus from {@code LivingIncomingDamageEvent}. Fabric API
 * has no equivalent that can change the damage amount
 * ({@code ServerLivingEntityEvents.ALLOW_DAMAGE} is allow/deny only), so the
 * amount is modified directly on the vanilla entry point:</p>
 *
 * <pre>{@code
 * net.minecraft.world.entity.LivingEntity
 *     #hurt(DamageSource, float)                 // 1.21.1 Mojmap
 *     // descriptor: (Lnet/minecraft/world/damagesource/DamageSource;F)Z
 * }</pre>
 *
 * <p>Injecting at HEAD puts the bonus in before armour and resistance are
 * applied, which is the same point vanilla adds Smite's damage, so both loaders
 * behave identically. All the gating (config, melee-only, tag checks) lives in
 * {@link SilverSmiteHandler}.</p>
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
     * Fabric hook for the innate silver armour reduction against undead attackers.
     *
     * <p>Deliberately a different injection point from the smite bonus above.
     * NeoForge registers this on the {@code ENCHANTMENTS} reduction stage, which
     * runs after armour; the vanilla method below is that same stage — it is where
     * {@code CombatRules.getDamageAfterMagicAbsorb} applies the Protection
     * enchantment:</p>
     *
     * <pre>{@code
     * net.minecraft.world.entity.LivingEntity
     *     #getDamageAfterMagicAbsorb(DamageSource, float)   // 1.21.1 Mojmap
     *     // descriptor: (Lnet/minecraft/world/damagesource/DamageSource;F)F
     * }</pre>
     *
     * <p>Injecting at HEAD (as the smite bonus does) would put the reduction before
     * armour, and vanilla's armour formula is damage-dependent, so the two loaders
     * would disagree and silver would out-perform the Protection level it is meant
     * to imitate. All the gating lives in {@link UndeadProtectionHandler}.</p>
     */
    @Inject(method = "getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F",
            at = @At("RETURN"), cancellable = true)
    private void thesilverage$applyUndeadProtection(DamageSource source, float damage,
                                                    CallbackInfoReturnable<Float> cir) {
        float reduced = UndeadProtectionHandler.applyReduction(
                (LivingEntity) (Object) this, source, cir.getReturnValueF());

        if (reduced != cir.getReturnValueF()) {
            cir.setReturnValue(reduced);
        }
    }
}
