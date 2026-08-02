package com.phantomwing.thesilverage.combat;

import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.platform.SmitePlatform;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Innate anti-undead damage for silver tools ("silver burns the undead").
 *
 * <p>Behaves like a built-in Smite, but is NOT an enchantment: it is not applied
 * to the stack, cannot be removed at a grindstone, and stacks additively on top
 * of a real Smite enchantment.</p>
 *
 * <p>The bonus is added before armour/resistance reduction, which is exactly
 * where vanilla applies the Smite enchantment's damage (see
 * {@code Player#attack}), so it scales identically against armoured undead.</p>
 *
 * <p>The LOGIC is loader-agnostic and lives here; the damage hook itself is
 * loader-specific (NeoForge {@code LivingIncomingDamageEvent}, Fabric mixin)
 * and is bridged through {@link SmitePlatform} — mirroring
 * {@link com.phantomwing.thesilverage.armor.MonsterArmorHandler}.</p>
 */
public final class SilverSmiteHandler {
    /**
     * Extra damage dealt to undead targets. Vanilla Smite is 2.5 per level, so
     * this sits deliberately below Smite I and leaves room for the real
     * enchantment on top.
     */
    public static final float BONUS_DAMAGE = 1.5f;

    private SilverSmiteHandler() {
    }

    /** Wires the loader-specific damage hook. Called from {@code TheSilverAgeCommon.init()}. */
    public static void register() {
        SmitePlatform.registerDamageHandler();
    }

    /** Whether this stack grants the bonus (drives both the damage and the tooltip). */
    public static boolean appliesTo(ItemStack stack) {
        return stack.is(ModTags.Items.SILVER_SMITE_TOOLS);
    }

    /**
     * The bonus damage to add for an incoming attack, or {@code 0} when it does
     * not apply.
     *
     * <p>Only direct melee attacks count: {@code getDirectEntity() == getEntity()}
     * excludes arrows and other projectiles, which carry their own damage and
     * would otherwise inherit the wielder's held weapon bonus.</p>
     *
     * @param target the entity being hurt
     * @param source the damage source
     */
    public static float getBonusDamage(LivingEntity target, DamageSource source) {
        // Server-authoritative only. LivingEntity#hurt also runs client-side (hurt
        // animation / knockback prediction); the Fabric mixin sits on that method,
        // so without this the client would compute a different number than the
        // server. Health is synced from the server either way.
        if (target.level().isClientSide() || !CommonConfig.silverSmite()) {
            return 0.0f;
        }

        // Direct melee hit from a living attacker only.
        if (source.getDirectEntity() != source.getEntity()
                || !(source.getEntity() instanceof LivingEntity attacker)) {
            return 0.0f;
        }

        if (!target.getType().is(EntityTypeTags.SENSITIVE_TO_SMITE)) {
            return 0.0f;
        }

        // Same tag vanilla Smite targets, so modded undead are covered too.
        return appliesTo(attacker.getItemBySlot(EquipmentSlot.MAINHAND)) ? BONUS_DAMAGE : 0.0f;
    }
}
