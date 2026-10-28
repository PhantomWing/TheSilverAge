package com.phantomwing.thesilverage.armor;

import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.platform.MonsterArmorPlatform;
import com.phantomwing.thesilverage.tags.ModTags;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * Replaces naturally-spawned mobs' golden armor with silver armor, and their iron
 * swords, axes and shovels with silver ones.
 *
 * <p>The transmute LOGIC is loader-agnostic and lives here. The actual event
 * subscription is loader-specific (NeoForge keeps its exact
 * {@code EntityJoinLevelEvent} + {@code loadedFromDisk()} gating; Fabric uses a
 * {@code PersistentEntitySectionManager} mixin that carries the same
 * loaded-from-storage flag) and is bridged through {@link MonsterArmorPlatform}.</p>
 *
 * <p>Whatever gave the mob its gear - its own spawn, a raid, a trial spawner's equipment
 * table - has done so before it joins the level, so all of them are covered here.</p>
 */
public final class MonsterArmorHandler {
    private static final float ARMOR_REPLACE_CHANCE = 0.5f; // 50% chance per piece / full set
    private static final float TOOL_REPLACE_CHANCE = 0.1f;
    /**
     * The tag a mob gets once its gear has had its chance, which it keeps through a portal: a mob crossing one joins the
     * other level as a new entity. A map maker summoning a mob with it keeps its iron and gold as given.
     */
    public static final String ROLLED_TAG = "thesilverage.rolled_silver_gear";

    private MonsterArmorHandler() {
    }

    /**
     * A mob converting - a husk drowning into a zombie, a skeleton freezing into a stray - is replaced by a new one
     * that keeps its gear but not its tags, and joins as a fresh spawn: without its old roll it had another chance at
     * silver. Called by each loader's {@code MobMixin} as {@code Mob#convertTo} adds the new mob.
     */
    public static Entity carryRoll(Mob from, Entity to) {
        if (from.getTags().contains(ROLLED_TAG)) {
            to.addTag(ROLLED_TAG);
        }
        return to;
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
     *                       the event; Fabric: from the section-manager mixin)
     */
    public static void tryEquipSilverGear(Entity entity, Level level, boolean loadedFromDisk) {
        // Only apply when the mob is spawning naturally in the world, not when loaded from disk or on the client side.
        if (level.isClientSide() || loadedFromDisk || !(entity instanceof Mob mob) || !CommonConfig.mobSilverEquipment()) {
            return;
        }
        boolean armor = entity.getType().is(ModTags.EntityTypes.CAN_WEAR_SILVER_ARMOR);
        boolean tools = entity.getType().is(ModTags.EntityTypes.CAN_HOLD_SILVER_TOOLS);
        if (!armor && !tools || !mob.addTag(ROLLED_TAG)) {
            return; // not one that wears silver, or its gear had its chance before a portal took it to this level
        }
        if (armor) {
            replaceArmor(mob);
        }
        if (tools) {
            replaceTool(mob);
        }
    }

    private static void replaceArmor(Mob mob) {
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
            if (random.nextFloat() < ARMOR_REPLACE_CHANCE) {
                mob.setItemSlot(EquipmentSlot.HEAD, ItemUtils.tryTransmuteStack(helmet, ModItems.SILVER_HELMET.get()));
                mob.setItemSlot(EquipmentSlot.CHEST, ItemUtils.tryTransmuteStack(chestplate, ModItems.SILVER_CHESTPLATE.get()));
                mob.setItemSlot(EquipmentSlot.LEGS, ItemUtils.tryTransmuteStack(leggings, ModItems.SILVER_LEGGINGS.get()));
                mob.setItemSlot(EquipmentSlot.FEET, ItemUtils.tryTransmuteStack(boots, ModItems.SILVER_BOOTS.get()));
            }
        } else {
            // If mob is wearing any separate pieces of Golden armor, try to individually replace with a piece of Silver armor.
            if (hasGoldenHelmet && random.nextFloat() < ARMOR_REPLACE_CHANCE) {
                mob.setItemSlot(EquipmentSlot.HEAD, ItemUtils.tryTransmuteStack(helmet, ModItems.SILVER_HELMET.get()));
            }

            if (hasGoldenChestplate && random.nextFloat() < ARMOR_REPLACE_CHANCE) {
                mob.setItemSlot(EquipmentSlot.CHEST, ItemUtils.tryTransmuteStack(chestplate, ModItems.SILVER_CHESTPLATE.get()));
            }

            if (hasGoldenLeggings && random.nextFloat() < ARMOR_REPLACE_CHANCE) {
                mob.setItemSlot(EquipmentSlot.LEGS, ItemUtils.tryTransmuteStack(leggings, ModItems.SILVER_LEGGINGS.get()));
            }

            if (hasGoldenBoots && random.nextFloat() < ARMOR_REPLACE_CHANCE) {
                mob.setItemSlot(EquipmentSlot.FEET, ItemUtils.tryTransmuteStack(boots, ModItems.SILVER_BOOTS.get()));
            }
        }
    }

    // A zombie's iron sword or shovel, a vindicator's iron axe. Enchantments, as a raid's axe has, carry over.
    private static void replaceTool(Mob mob) {
        ItemStack held = mob.getItemBySlot(EquipmentSlot.MAINHAND);
        Item silver = silverToolFor(held);
        if (silver != null && mob.getRandom().nextFloat() < TOOL_REPLACE_CHANCE) {
            mob.setItemSlot(EquipmentSlot.MAINHAND, ItemUtils.tryTransmuteStack(held, silver));
        }
    }

    @Nullable
    private static Item silverToolFor(ItemStack held) {
        if (held.is(Items.IRON_SWORD)) {
            return ModItems.SILVER_SWORD.get();
        }
        if (held.is(Items.IRON_AXE)) {
            return ModItems.SILVER_AXE.get();
        }
        if (held.is(Items.IRON_SHOVEL)) {
            return ModItems.SILVER_SHOVEL.get();
        }
        return null;
    }
}
