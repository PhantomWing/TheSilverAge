package com.phantomwing.thesilverage.combat;

import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.platform.SmitePlatform;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

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
    public static final float BONUS_DAMAGE = 1.0f;

    /**
     * The Silver Hoe gets a reduced bonus: hoes swing fast enough that the full
     * bonus would make it the strongest anti-undead option in the set.
     */
    public static final float HOE_BONUS_DAMAGE = 0.5f;

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
     * The bonus this stack grants, or {@code 0} if it grants none. Single source
     * of truth for both the damage hook and the tooltip, so the number shown is
     * always the number dealt.
     */
    public static float getBonusFor(ItemStack stack) {
        if (!appliesTo(stack)) {
            return 0.0f;
        }

        return stack.is(ModItems.SILVER_HOE.get()) ? HOE_BONUS_DAMAGE : BONUS_DAMAGE;
    }

    /**
     * Handles an incoming attack: returns the bonus damage to add (or {@code 0}),
     * and emits vanilla's magic-crit particle when the bonus applies.
     *
     * <p>Both loaders funnel through this single method so the damage and the
     * particle can never diverge between them.</p>
     *
     * <p>Only direct melee attacks count: {@code getDirectEntity() == getEntity()}
     * excludes arrows and other projectiles, which carry their own damage and
     * would otherwise inherit the wielder's held weapon bonus.</p>
     *
     * @param target the entity being hurt
     * @param source the damage source
     */
    public static float handleIncomingDamage(LivingEntity target, DamageSource source) {
        float bonus = getBonusDamage(target, source);
        if (bonus > 0.0f) {
            spawnMagicCritParticle(target, source);
        }

        return bonus;
    }

    /**
     * Vanilla's "magic crit" effect (the {@code ENCHANTED_HIT} particle burst
     * Smite produces), via the same {@code Player#magicCrit} path vanilla uses in
     * {@code Player#attack}.
     *
     * <p>Vanilla ALREADY fires that effect whenever an enchantment added damage to
     * the hit, so calling it again for an enchanted silver tool would double the
     * particles. The check below probes the weapon's enchantments against this
     * exact target: if they contribute any damage (Smite on undead, Sharpness on
     * anything, ...), vanilla has it covered and this does nothing.</p>
     */
    private static void spawnMagicCritParticle(LivingEntity target, DamageSource source) {
        // Vanilla only emits this for player attacks; mobs swinging silver get no particle.
        if (!(source.getEntity() instanceof Player player)
                || !(target.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        ItemStack weapon = player.getItemBySlot(EquipmentSlot.MAINHAND);

        // Probe value is arbitrary: we only care whether enchantments raise it.
        float probe = 1.0f;
        if (EnchantmentHelper.modifyDamage(serverLevel, weapon, target, source, probe) > probe) {
            return;
        }

        player.magicCrit(target);
    }

    /** Pure calculation of the bonus for this hit, with no side effects. */
    private static float getBonusDamage(LivingEntity target, DamageSource source) {
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
        return getBonusFor(attacker.getItemBySlot(EquipmentSlot.MAINHAND));
    }
}
