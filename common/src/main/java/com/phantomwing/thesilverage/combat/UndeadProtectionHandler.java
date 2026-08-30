package com.phantomwing.thesilverage.combat;

import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Innate anti-undead damage reduction for silver armour — the defensive twin of
 * {@link SilverSmiteHandler} ("silver wards off the undead").
 *
 * <p>Behaves like a weak Protection, but is NOT an enchantment: it is not applied
 * to the stack, cannot be removed at a grindstone, and stacks on top of a real
 * Protection enchantment. Each piece grants {@link #PROTECTION_PER_PIECE} point,
 * so a full silver set is worth 4 — {@code 4%} less damage per point, exactly
 * vanilla's rate, giving 16% off a full set.</p>
 *
 * <p>The reduction is applied at vanilla's <i>magic absorb</i> stage, the same
 * place the Protection enchantment lands, rather than at the incoming-damage hook
 * the smite bonus uses. That distinction matters: vanilla's armour formula
 * subtracts {@code damage / (2 + toughness/4)} from the armour value, so it is
 * damage-dependent, and reducing the damage before armour rather than after would
 * quietly make silver stronger than the equivalent Protection level.</p>
 *
 * <p>The LOGIC is loader-agnostic and lives here; the hook is loader-specific
 * (NeoForge's {@code ENCHANTMENTS} reduction stage, a Fabric mixin on
 * {@code getDamageAfterMagicAbsorb}) — both land on the same stage, so the two
 * loaders reduce identically.</p>
 */
public final class UndeadProtectionHandler {
    /**
     * Protection points granted per silver armour piece. Vanilla scores one point
     * per Protection level, so a full set matches Protection I on every piece —
     * deliberately weak, and it leaves the real enchantment worth having.
     */
    public static final float PROTECTION_PER_PIECE = 1.0f;

    private UndeadProtectionHandler() {
    }

    /** Whether this stack grants protection (drives both the reduction and the tooltip). */
    public static boolean appliesTo(ItemStack stack) {
        return stack.is(ModTags.Items.UNDEAD_PROTECTION_ARMOR);
    }

    /**
     * The points this stack grants, or {@code 0} if it grants none. Single source
     * of truth for the reduction and the tooltip, so the number shown is always
     * the number applied.
     */
    public static float getProtectionFor(ItemStack stack) {
        return appliesTo(stack) ? PROTECTION_PER_PIECE : 0.0f;
    }

    /**
     * Points from every armour piece the entity is wearing.
     *
     * <p>Worn slots only — a silver chestplate held in hand protects nobody — but
     * that means both {@code HUMANOID_ARMOR} (the four player pieces) and
     * {@code ANIMAL_ARMOR}, which is the type of the {@code BODY} slot that horse
     * armour occupies since 1.20.5. Filtering to humanoid armour alone would leave
     * Silver Horse Armor showing the tooltip while protecting nothing.</p>
     */
    public static float getTotalProtection(LivingEntity entity) {
        float total = 0.0f;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.HAND) {
                continue;
            }

            total += getProtectionFor(entity.getItemBySlot(slot));
        }

        return total;
    }

    /**
     * Reduces an incoming hit if it came from an undead attacker and the target is
     * wearing silver. Returns {@code damage} unchanged otherwise.
     *
     * <p>Both loaders funnel through this one method, so their numbers cannot
     * diverge.</p>
     */
    public static float applyReduction(LivingEntity target, DamageSource source, float damage) {
        if (damage <= 0.0f || !isUndeadAttack(source)) {
            return damage;
        }

        // Server-authoritative only, for the same reason as the smite bonus: the
        // Fabric hook sits on a method that also runs client-side, and a client
        // computing a different number than the server would mispredict.
        if (target.level().isClientSide() || !CommonConfig.undeadProtection()) {
            return damage;
        }

        // Anything that ignores the Protection enchantment ignores this too.
        if (source.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
            return damage;
        }

        float points = getTotalProtection(target);
        if (points <= 0.0f) {
            return damage;
        }

        // Vanilla's own formula, so the rate per point matches Protection exactly.
        return CombatRules.getDamageAfterMagicAbsorb(damage, points);
    }

    /**
     * Whether the hit came from an undead attacker. Uses the causing entity rather
     * than the direct one, so a skeleton's arrow still counts — unlike the smite
     * bonus, which is deliberately melee-only.
     *
     * <p>Same tag vanilla Smite targets, so modded undead are covered too.</p>
     */
    private static boolean isUndeadAttack(DamageSource source) {
        return source.getEntity() != null
                && source.getEntity().getType().is(EntityTypeTags.SENSITIVE_TO_SMITE);
    }
}
