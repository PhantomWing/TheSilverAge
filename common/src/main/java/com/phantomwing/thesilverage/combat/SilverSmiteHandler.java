package com.phantomwing.thesilverage.combat;

import com.phantomwing.thesilverage.entity.SilverArrow;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.platform.SmitePlatform;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Innate anti-undead damage for silver tools ("silver burns the undead").
 *
 * <p>Behaves like a built-in Smite, but is NOT an enchantment: it is not applied
 * to the stack, cannot be removed at a grindstone, and stacks additively on top
 * of a real Smite enchantment.</p>
 *
 * <p>A player's swing takes it where it takes Smite's, in {@code ServerPlayer#getEnchantedDamage} (each loader's
 * {@code ServerPlayerMixin}), so it is scaled by how charged the swing was and shown with vanilla's magic-crit burst. Every
 * other hit takes it as it comes in, before armour and resistance: NeoForge's {@code LivingIncomingDamageEvent} and a
 * Fabric mixin, bridged through {@link SmitePlatform}.</p>
 */
public final class SilverSmiteHandler {
    /**
     * Extra damage dealt to undead targets by default ({@code silver_smite_damage}). Vanilla Smite is 2.5 per level,
     * so this sits deliberately below Smite I and leaves room for the real enchantment on top.
     */
    public static final float BONUS_DAMAGE = 1.0f;

    /**
     * The Silver Hoe's share of the bonus: hoes swing fast enough that the full
     * bonus would make it the strongest anti-undead option in the set.
     */
    public static final float HOE_SHARE = 0.5f;

    /** A Silver Arrow's bonus by default ({@code silver_arrow_smite_damage}): a shot is slower than a swing, and the arrow is used up. */
    public static final float ARROW_BONUS_DAMAGE = 2.0f;

    /** The most either bonus can be set to: Smite V's 12.5, and some. */
    public static final double MAX_BONUS_DAMAGE = 20.0;

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
    public static float getBonusFor(ItemStack stack, SilverUndeadSettings settings) {
        if (!appliesTo(stack)) {
            return 0.0f;
        }

        return stack.is(ModItems.SILVER_HOE.get()) ? settings.smiteDamage() * HOE_SHARE : settings.smiteDamage();
    }

    /**
     * The bonus a stack's tooltip names: a tool's when swung, a Silver Arrow's when fired. Kept apart from
     * {@link #getBonusFor}, which the melee hit reads, so a punch with arrows in hand gets nothing.
     */
    public static float getTooltipBonusFor(ItemStack stack, SilverUndeadSettings settings) {
        return stack.is(ModItems.SILVER_ARROW.get()) ? settings.arrowSmiteDamage() : getBonusFor(stack, settings);
    }

    /**
     * The bonus damage to add to an incoming attack, or {@code 0}. Both loaders add it before armour and resistance.
     *
     * <p>Direct melee attacks count, and a Silver Arrow's hit: {@code getDirectEntity() == getEntity()}
     * excludes every other projectile, which carries its own damage and
     * would otherwise inherit the wielder's held weapon bonus.</p>
     */
    public static float handleIncomingDamage(LivingEntity target, DamageSource source) {
        return isPlayerSwing(source) ? 0.0f : getBonusDamage(target, source);
    }

    /** The bonus a player's swing at {@code target} adds to its enchanted damage, or {@code 0}. */
    public static float getSwingBonus(Entity target, DamageSource source) {
        return target instanceof LivingEntity living && isPlayerSwing(source) ? getBonusDamage(living, source) : 0.0f;
    }

    /**
     * Sparks for a Silver Arrow's bonus once its hit has landed. Called by each loader's {@code LivingEntityMixin}
     * where {@code hurt} answers true, so a hit a shield or invulnerability refused shows nothing. A swing's bonus is
     * part of its enchanted damage, which vanilla's magic crit already shows.
     */
    public static void onHurt(LivingEntity target, DamageSource source) {
        if (source.getDirectEntity() instanceof SilverArrow && target.level() instanceof ServerLevel serverLevel
                && getBonusDamage(target, source) > 0.0f) {
            SilverSparks.burst(serverLevel, target);
        }
    }

    private static boolean isPlayerSwing(DamageSource source) {
        return source.is(DamageTypes.PLAYER_ATTACK) && source.getEntity() instanceof Player
                && source.getDirectEntity() == source.getEntity();
    }

    private static boolean isMelee(DamageSource source) {
        return source.is(DamageTypes.PLAYER_ATTACK) || source.is(DamageTypes.MOB_ATTACK)
                || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO);
    }

    /** Pure calculation of the bonus for this hit, with no side effects. */
    private static float getBonusDamage(LivingEntity target, DamageSource source) {
        // Server-authoritative only. LivingEntity#hurt also runs client-side (hurt
        // animation / knockback prediction); the Fabric mixin sits on that method,
        // so without this the client would compute a different number than the
        // server. Health is synced from the server either way.
        if (target.level().isClientSide()) {
            return 0.0f;
        }
        SilverUndeadSettings settings = SilverUndeadSettings.fromConfig();
        if (!settings.smite()) {
            return 0.0f;
        }

        // Holds every mob vanilla Smite targets, so modded undead are covered too.
        if (!target.getType().is(ModTags.EntityTypes.SENSITIVE_TO_SILVER)) {
            return 0.0f;
        }

        if (source.getDirectEntity() instanceof SilverArrow) {
            return settings.arrowSmiteDamage();
        }

        // Otherwise a melee hit from a living attacker only. Thorns names the wearer as both its entities too, so
        // without the type a burn from silver or Thorns armour took the bonus of the weapon its wearer held.
        if (!isMelee(source) || source.getDirectEntity() != source.getEntity()
                || !(source.getEntity() instanceof LivingEntity attacker)) {
            return 0.0f;
        }

        return getBonusFor(attacker.getItemBySlot(EquipmentSlot.MAINHAND), settings);
    }
}
