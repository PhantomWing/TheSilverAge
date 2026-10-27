package com.phantomwing.thesilverage.combat;

import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;

/**
 * Silver armour burning the undead that strike its wearer, as Thorns burns any attacker: each piece worn adds
 * {@link #CHANCE_PER_PIECE}, so a full set burns four strikes in five, and Silver Horse Armor, all the silver a horse
 * wears, counts as a full set. A burn deals Thorns' damage at a smaller roll, with Smite's sparks and Thorns' sound, and
 * knocks the attacker back as Thorns does. The chance is added up rather than rolled per piece, so a strike is burned
 * once at most.
 *
 * <p>Not an enchantment: it stacks with Thorns and costs the armour no durability. Only a melee strike counts, since
 * an arrow never touches the silver, and only from a mob silver harms ({@code thesilverage:sensitive_to_silver}); an
 * undead mob wearing silver is never burned by its own. Each loader's {@code LivingEntityMixin} calls in once a hit
 * has landed.</p>
 */
public final class SilverThornsHandler {
    /** A worn piece's share of the chance a strike is burned. */
    public static final float CHANCE_PER_PIECE = 0.2F;
    /** The pieces of a full set, which animal armour counts as. */
    private static final int FULL_SET = 4;
    private static final int MIN_DAMAGE = 1;
    private static final int MAX_DAMAGE = 2;

    private SilverThornsHandler() {
    }

    /** The chance {@code stack} adds when worn, for the burn and the tooltip alike. */
    public static float chanceFor(ItemStack stack) {
        if (!stack.is(ModTags.Items.SILVER_THORNS_ARMOR)) {
            return 0.0F;
        }
        return CHANCE_PER_PIECE * (stack.getItem() instanceof AnimalArmorItem ? FULL_SET : 1);
    }

    /** The chance from everything {@code wearer} wears: its armour slots and the body slot horse armour goes in. */
    public static float chanceOf(LivingEntity wearer) {
        float chance = 0.0F;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.HAND) {
                chance += chanceFor(wearer.getItemBySlot(slot));
            }
        }
        return Math.min(chance, 1.0F);
    }

    /** {@code wearer} struck by {@code source}, the strike having landed: its attacker burned, at its armour's chance. */
    public static void onHurt(LivingEntity wearer, DamageSource source) {
        // A burn is Thorns damage itself, so one silver wearer burning another can't answer it.
        if (!(wearer.level() instanceof ServerLevel level) || !(source.getEntity() instanceof LivingEntity attacker)
                || source.getDirectEntity() != attacker || source.is(DamageTypes.THORNS)
                || !attacker.getType().is(ModTags.EntityTypes.SENSITIVE_TO_SILVER) || !CommonConfig.silverThorns()) {
            return;
        }
        float chance = chanceOf(wearer);
        RandomSource random = wearer.getRandom();
        if (chance <= 0.0F || random.nextFloat() >= chance) {
            return;
        }
        float damage = MIN_DAMAGE + random.nextInt(MAX_DAMAGE - MIN_DAMAGE + 1);
        if (attacker.hurt(level.damageSources().thorns(wearer), damage)) {
            SilverSparks.burst(level, attacker);
            level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.THORNS_HIT, attacker.getSoundSource(), 1.0F, 1.0F);
        }
    }
}
