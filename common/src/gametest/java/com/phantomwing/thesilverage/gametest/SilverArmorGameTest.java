package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/**
 * Game tests for silver armour burning the undead that strike its wearer: a fifth of the strikes for each piece, four
 * in five for a full set or Silver Horse Armor, none from a mob silver doesn't harm. A wearer is struck many times and
 * the burns counted. Listed in {@link GameTests}.
 */
public final class SilverArmorGameTest {
    /** Strikes per wearer. Each bound below is at least four standard deviations from what the chance gives. */
    private static final int STRIKES = 200;

    private SilverArmorGameTest() {
    }

    /** A full set burns about 160 husk strikes in 200, one piece about 40, none and a cow's strikes none. */
    public static void silverArmorBurnsUndeadAttackers(GameTestHelper helper) {
        LivingEntity husk = helper.spawnWithNoFreeWill(EntityType.HUSK, new BlockPos(1, 2, 1));
        LivingEntity cow = helper.spawnWithNoFreeWill(EntityType.COW, new BlockPos(5, 2, 1));
        Mob wearer = helper.spawnWithNoFreeWill(EntityType.VINDICATOR, new BlockPos(3, 2, 3));
        Map<EquipmentSlot, Item> fullSet = Map.of(EquipmentSlot.HEAD, ModItems.SILVER_HELMET.get(),
                EquipmentSlot.CHEST, ModItems.SILVER_CHESTPLATE.get(), EquipmentSlot.LEGS, ModItems.SILVER_LEGGINGS.get(),
                EquipmentSlot.FEET, ModItems.SILVER_BOOTS.get());
        List<String> problems = new ArrayList<>();
        expect(problems, "a full set struck by a husk", burns(helper, wearer, fullSet, husk), 120, 190);
        expect(problems, "a Silver Helmet alone struck by a husk", burns(helper, wearer,
                Map.of(EquipmentSlot.HEAD, ModItems.SILVER_HELMET.get()), husk), 15, 70);
        expect(problems, "no armour struck by a husk", burns(helper, wearer, Map.of(), husk), 0, 0);
        expect(problems, "a full set struck by a cow", burns(helper, wearer, fullSet, cow), 0, 0);
        expect(problems, "a full set struck by a husk with enable_silver_thorns off",
                TestSettings.with("enable_silver_thorns", false, () -> burns(helper, wearer, fullSet, husk)), 0, 0);
        expect(problems, "a full set struck by a husk with silver_affects_undead off",
                TestSettings.with("silver_affects_undead", false, () -> burns(helper, wearer, fullSet, husk)), 0, 0);
        finish(helper, problems, husk, cow, wearer);
    }

    /** Silver Horse Armor, all the silver a horse wears, burns as often as a full set. */
    public static void silverHorseArmorCountsAsAFullSet(GameTestHelper helper) {
        LivingEntity husk = helper.spawnWithNoFreeWill(EntityType.HUSK, new BlockPos(1, 2, 1));
        Mob horse = helper.spawnWithNoFreeWill(EntityType.HORSE, new BlockPos(4, 2, 4));
        List<String> problems = new ArrayList<>();
        expect(problems, "Silver Horse Armor struck by a husk",
                burns(helper, horse, Map.of(EquipmentSlot.BODY, ModItems.SILVER_HORSE_ARMOR.get()), husk), 120, 190);
        finish(helper, problems, husk, horse);
    }

    /**
     * How many of {@value #STRIKES} strikes from {@code attacker} on {@code wearer} in {@code armour} burned it. Both are
     * healed and made hurtable again before each, and the armour is new each time, so none wears out.
     */
    private static int burns(GameTestHelper helper, Mob wearer, Map<EquipmentSlot, Item> armour, LivingEntity attacker) {
        ServerLevel level = helper.getLevel();
        int burned = 0;
        for (int i = 0; i < STRIKES; i++) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() != EquipmentSlot.Type.HAND) {
                    Item item = armour.get(slot);
                    wearer.setItemSlot(slot, item == null ? ItemStack.EMPTY : new ItemStack(item));
                }
            }
            wearer.invulnerableTime = 0;
            wearer.setHealth(wearer.getMaxHealth());
            attacker.invulnerableTime = 0;
            attacker.setHealth(attacker.getMaxHealth());
            wearer.hurt(level.damageSources().mobAttack(attacker), 1.0F);
            if (attacker.getHealth() < attacker.getMaxHealth()) {
                burned++;
            }
        }
        return burned;
    }

    private static void expect(List<String> problems, String what, int burned, int min, int max) {
        if (burned < min || burned > max) {
            problems.add(what + " burned " + burned + " of " + STRIKES + " strikes, not " + (min == max ? min : min + " to " + max));
        }
    }

    private static void finish(GameTestHelper helper, List<String> problems, LivingEntity... mobs) {
        for (LivingEntity mob : mobs) {
            mob.discard();
        }
        if (problems.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, "Silver armour: " + String.join("; ", problems));
        }
    }
}
