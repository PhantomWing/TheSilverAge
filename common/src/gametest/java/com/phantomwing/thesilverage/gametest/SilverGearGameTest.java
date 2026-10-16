package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.armor.MonsterArmorHandler;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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

    private static Item silverFor(Item iron) {
        if (iron == Items.IRON_SWORD) return ModItems.SILVER_SWORD.get();
        if (iron == Items.IRON_AXE) return ModItems.SILVER_AXE.get();
        if (iron == Items.IRON_SHOVEL) return ModItems.SILVER_SHOVEL.get();
        throw new IllegalArgumentException("No silver counterpart for " + iron);
    }
}
