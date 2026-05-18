package com.phantomwing.thesilverage.armor;

import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.platform.MonsterArmorPlatform;
import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

/**
 * Replaces naturally-spawned mobs' golden armor with silver armor.
 *
 * <p>The transmute LOGIC is loader-agnostic and lives here. The actual event
 * subscription is loader-specific (NeoForge keeps its exact
 * {@code EntityJoinLevelEvent} + {@code loadedFromDisk()} gating; Fabric uses an
 * Architectury {@code EntityEvent.ADD} shell) and is bridged through
 * {@link MonsterArmorPlatform}.</p>
 */
public final class MonsterArmorHandler {
    private static final float REPLACE_CHANCE = 0.5f; // 50% chance to replace full set

    private MonsterArmorHandler() {
    }

    /** Wires the loader-specific entity-join hook. Called from {@code TheSilverAgeCommon.init()}. */
    public static void register() {
        MonsterArmorPlatform.registerMobSpawnHandler();
    }

    /**
     * Loader-agnostic equip logic. The caller is responsible for the spawn gating
     * (client-side / loaded-from-disk) so NeoForge can preserve its precise
     * {@code EntityJoinLevelEvent.loadedFromDisk()} semantics.
     *
     * @param entity        the entity that just joined the level
     * @param level         the level it joined
     * @param loadedFromDisk whether the entity was loaded from disk (NeoForge: from
     *                       the event; Fabric shell: always {@code false})
     */
    public static void tryEquipSilverArmor(Entity entity, Level level, boolean loadedFromDisk) {
        // Only apply when the mob is spawning naturally in the world, not when loaded from disk or on the client side.
        if (level.isClientSide() || loadedFromDisk) {
            return;
        }

        // Check if we should equip Silver armor.
        if (entity.getType().is(ModTags.EntityTypes.CAN_WEAR_SILVER_ARMOR) && entity instanceof Mob mob) {
            RandomSource random = mob.getRandom();

            ItemStack helmet = mob.getItemBySlot(EquipmentSlot.HEAD);
            ItemStack chestplate = mob.getItemBySlot(EquipmentSlot.CHEST);
            ItemStack leggings = mob.getItemBySlot(EquipmentSlot.LEGS);
            ItemStack boots = mob.getItemBySlot(EquipmentSlot.FEET);

            boolean hasGoldenHelmet = helmet.is(Items.GOLDEN_HELMET);
            boolean hasGoldenChestplate = chestplate.is(Items.GOLDEN_CHESTPLATE);
            boolean hasGoldenLeggings = leggings.is(Items.GOLDEN_LEGGINGS);
            boolean hasGoldenBoots = boots.is(Items.GOLDEN_BOOTS);
            boolean hasFullSet = hasGoldenHelmet && hasGoldenChestplate && hasGoldenLeggings && hasGoldenBoots;

            // If wearing a full set of Golden armor, try to replace the entire set with Silver armor.
            if (hasFullSet) {
                if (random.nextFloat() < REPLACE_CHANCE) {
                    mob.setItemSlot(EquipmentSlot.HEAD, ItemUtils.tryTransmuteStack(helmet, ModItems.SILVER_HELMET.get()));
                    mob.setItemSlot(EquipmentSlot.CHEST, ItemUtils.tryTransmuteStack(chestplate, ModItems.SILVER_CHESTPLATE.get()));
                    mob.setItemSlot(EquipmentSlot.LEGS, ItemUtils.tryTransmuteStack(leggings, ModItems.SILVER_LEGGINGS.get()));
                    mob.setItemSlot(EquipmentSlot.FEET, ItemUtils.tryTransmuteStack(boots, ModItems.SILVER_BOOTS.get()));
                }
            } else {
                // If mob is wearing any separate pieces of Golden armor, try to individually replace with a piece of Silver armor.
                if (hasGoldenHelmet && random.nextFloat() < REPLACE_CHANCE) {
                    mob.setItemSlot(EquipmentSlot.HEAD, ItemUtils.tryTransmuteStack(helmet, ModItems.SILVER_HELMET.get()));
                }

                if (hasGoldenChestplate && random.nextFloat() < REPLACE_CHANCE) {
                    mob.setItemSlot(EquipmentSlot.CHEST, ItemUtils.tryTransmuteStack(chestplate, ModItems.SILVER_CHESTPLATE.get()));
                }

                if (hasGoldenLeggings && random.nextFloat() < REPLACE_CHANCE) {
                    mob.setItemSlot(EquipmentSlot.LEGS, ItemUtils.tryTransmuteStack(leggings, ModItems.SILVER_LEGGINGS.get()));
                }

                if (hasGoldenBoots && random.nextFloat() < REPLACE_CHANCE) {
                    mob.setItemSlot(EquipmentSlot.FEET, ItemUtils.tryTransmuteStack(boots, ModItems.SILVER_BOOTS.get()));
                }
            }
        }
    }
}
