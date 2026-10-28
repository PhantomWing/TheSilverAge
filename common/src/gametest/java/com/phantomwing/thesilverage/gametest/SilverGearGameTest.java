package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.armor.ModTrimMaterials;
import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimPatterns;

import java.util.Map;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/**
 * Game tests for mobs spawning with silver gear. They hand a mob its iron or golden gear and call the
 * spawn hook directly, as many times as it takes, since a swap is a roll. Listed in {@link GameTests}.
 */
public final class SilverGearGameTest {
    /** Tries per mob: a tool swaps one time in ten, so all of them missing is a chance of 0.9^300. */
    private static final int TRIES = 300;

    private SilverGearGameTest() {
    }

    /** Zombies, husks, zombie villagers and vindicators sometimes hold the silver tool in place of the iron one. */
    public static void mobsHoldSilverTools(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Map<EntityType<? extends Mob>, Item> held = Map.of(
                EntityType.ZOMBIE, Items.IRON_SWORD,
                EntityType.HUSK, Items.IRON_SHOVEL,
                EntityType.ZOMBIE_VILLAGER, Items.IRON_SWORD,
                EntityType.VINDICATOR, Items.IRON_AXE);
        for (Map.Entry<EntityType<? extends Mob>, Item> entry : held.entrySet()) {
            Mob mob = entry.getKey().create(level);
            Item silver = silverFor(entry.getValue());
            boolean swapped = false;
            for (int i = 0; i < TRIES && !swapped; i++) {
                mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(entry.getValue()));
                mob.removeTag(MonsterArmorHandler.ROLLED_TAG); // each try a new spawn, not the same mob rolling again
                MonsterArmorHandler.tryEquipSilverGear(mob, level, false);
                swapped = mob.getItemBySlot(EquipmentSlot.MAINHAND).is(silver);
            }
            mob.discard();
            if (!swapped) {
                fail(helper, BuiltInRegistries.ENTITY_TYPE.getKey(entry.getKey()) + " never held a "
                        + BuiltInRegistries.ITEM.getKey(silver) + " in " + TRIES + " spawns");
                return;
            }
        }

        // A mob loaded from disk keeps what it had.
        Mob loaded = EntityType.ZOMBIE.create(level);
        for (int i = 0; i < TRIES; i++) {
            loaded.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            MonsterArmorHandler.tryEquipSilverGear(loaded, level, true);
            if (!loaded.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.IRON_SWORD)) {
                loaded.discard();
                fail(helper, "A zombie loaded from disk had its iron sword swapped");
                return;
            }
        }
        loaded.discard();
        helper.succeed();
    }

    /** A husk spawning in golden armor sometimes wears silver armor instead, as a zombie does. */
    public static void husksWearSilverArmor(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Mob husk = EntityType.HUSK.create(level);
        boolean swapped = false;
        for (int i = 0; i < TRIES && !swapped; i++) {
            husk.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
            husk.removeTag(MonsterArmorHandler.ROLLED_TAG);
            MonsterArmorHandler.tryEquipSilverGear(husk, level, false);
            swapped = husk.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SILVER_HELMET.get());
        }
        husk.discard();
        if (swapped) {
            helper.succeed();
        } else {
            fail(helper, "A husk never wore a Silver Helmet in " + TRIES + " spawns");
        }
    }

    /**
     * A mob's gear has its chance once: a mob crossing a portal joins the other level as a new entity, and without the
     * tag rolled again each time.
     */
    public static void mobRollsOnce(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Mob zombie = EntityType.ZOMBIE.create(level);
        for (int i = 0; i < TRIES; i++) {
            zombie.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            MonsterArmorHandler.tryEquipSilverGear(zombie, level, false);
            if (i > 0 && !zombie.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.IRON_SWORD)) {
                zombie.discard();
                fail(helper, "A zombie's iron sword was swapped for silver on its spawn number " + (i + 1));
                return;
            }
        }
        zombie.discard();
        helper.succeed();
    }

    /**
     * A mob that converts keeps its roll: a husk drowning into a zombie is replaced by a new mob, which joined as a
     * fresh spawn and had another chance at silver.
     */
    public static void convertedMobsKeepTheirRoll(GameTestHelper helper) {
        Mob husk = helper.spawnWithNoFreeWill(EntityType.HUSK, new BlockPos(3, 2, 3));
        husk.addTag(MonsterArmorHandler.ROLLED_TAG);
        Mob zombie = husk.convertTo(EntityType.ZOMBIE, true);
        if (zombie == null) {
            fail(helper, "The husk didn't convert into a zombie");
        } else if (!zombie.getTags().contains(MonsterArmorHandler.ROLLED_TAG)) {
            fail(helper, "A husk that converted into a zombie lost its silver gear roll, so it rolls again");
        } else {
            helper.succeed();
        }
    }

    /**
     * Thorns damage takes no bonus from the weapon its armour's wearer holds: Thorns names the wearer as both its
     * entities, as a melee hit does, and silver armour's burn added its wearer's Silver Sword's bonus.
     */
    public static void thornsTakeNoWeaponBonus(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Mob armed = EntityType.ZOMBIE.create(level);
        armed.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.SILVER_SWORD.get()));
        Mob unarmed = EntityType.ZOMBIE.create(level);
        Mob burnedByArmed = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
        Mob burnedByUnarmed = helper.spawnWithNoFreeWill(EntityType.ZOMBIE, new BlockPos(5, 2, 1));
        float armedLoss = burnedByArmed.getHealth();
        burnedByArmed.hurt(level.damageSources().thorns(armed), 2.0f);
        armedLoss -= burnedByArmed.getHealth();
        float unarmedLoss = burnedByUnarmed.getHealth();
        burnedByUnarmed.hurt(level.damageSources().thorns(unarmed), 2.0f);
        unarmedLoss -= burnedByUnarmed.getHealth();
        armed.discard();
        unarmed.discard();
        if (armedLoss != unarmedLoss) {
            fail(helper, "Thorns from a wearer holding a Silver Sword dealt " + armedLoss + ", from one holding nothing " + unarmedLoss);
        } else {
            helper.succeed();
        }
    }

    /**
     * Iron gear swapped for silver keeps how worn it was as a share of its durability, its name and its trim: loot
     * wears iron by a share of its 250, which as damage would leave a silver tool (208) nearer breaking.
     */
    public static void swappedGearKeepsItsWear(GameTestHelper helper) {
        ItemStack sword = new ItemStack(Items.IRON_SWORD);
        sword.setDamageValue(sword.getMaxDamage() / 2);
        sword.set(DataComponents.CUSTOM_NAME, Component.literal("Old Faithful"));
        ItemStack silver = ItemUtils.tryTransmuteStack(sword, ModItems.SILVER_SWORD.get());
        if (silver.getDamageValue() != Math.round(silver.getMaxDamage() / 2.0F) || !silver.has(DataComponents.CUSTOM_NAME)) {
            fail(helper, "A half-worn named iron sword became a silver one worn " + silver.getDamageValue() + " of "
                    + silver.getMaxDamage() + (silver.has(DataComponents.CUSTOM_NAME) ? "" : ", its name lost"));
            return;
        }
        ItemStack nearlyBroken = new ItemStack(Items.IRON_AXE);
        nearlyBroken.setDamageValue(nearlyBroken.getMaxDamage() - 1);
        ItemStack silverAxe = ItemUtils.tryTransmuteStack(nearlyBroken, ModItems.SILVER_AXE.get());
        if (silverAxe.getDamageValue() != silverAxe.getMaxDamage() - 1) {
            fail(helper, "An iron axe with one use left became a silver one worn " + silverAxe.getDamageValue() + " of " + silverAxe.getMaxDamage());
            return;
        }
        ServerLevel level = helper.getLevel();
        ArmorTrim trim = new ArmorTrim(level.registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolderOrThrow(TrimMaterials.COPPER),
                level.registryAccess().registryOrThrow(Registries.TRIM_PATTERN).getHolderOrThrow(TrimPatterns.FLOW));
        ItemStack helmet = new ItemStack(Items.GOLDEN_HELMET);
        helmet.set(DataComponents.TRIM, trim);
        if (!trim.equals(ItemUtils.tryTransmuteStack(helmet, ModItems.SILVER_HELMET.get()).get(DataComponents.TRIM))) {
            fail(helper, "A trimmed golden helmet became an untrimmed silver one");
            return;
        }
        helper.succeed();
    }

    /** The silver trim on worn silver armour takes its darker palette, which only the registry's holder of the material finds. */
    public static void silverTrimDarkensOnSilverArmor(GameTestHelper helper) {
        Holder<TrimMaterial> silver = helper.getLevel().registryAccess().registryOrThrow(Registries.TRIM_MATERIAL)
                .getHolderOrThrow(ModTrimMaterials.SILVER);
        String palette = silver.value().overrideArmorMaterials().get(((ArmorItem) ModItems.SILVER_CHESTPLATE.get()).getMaterial());
        if ("silver_darker".equals(palette)) {
            helper.succeed();
        } else {
            fail(helper, "A silver trim on silver armour draws with the palette " + palette + ", not silver_darker");
        }
    }

    private static Item silverFor(Item iron) {
        if (iron == Items.IRON_SWORD) return ModItems.SILVER_SWORD.get();
        if (iron == Items.IRON_AXE) return ModItems.SILVER_AXE.get();
        if (iron == Items.IRON_SHOVEL) return ModItems.SILVER_SHOVEL.get();
        throw new IllegalArgumentException("No silver counterpart for " + iron);
    }
}
