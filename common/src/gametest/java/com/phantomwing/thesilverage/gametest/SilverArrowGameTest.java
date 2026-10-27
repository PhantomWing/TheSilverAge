package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.combat.SilverSmiteHandler;
import com.phantomwing.thesilverage.entity.ModEntityTypes;
import com.phantomwing.thesilverage.entity.SilverArrow;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;

import java.util.ArrayList;
import java.util.List;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/** Game tests for the Silver Arrow. Listed in {@link GameTests}. */
public final class SilverArrowGameTest {
    private SilverArrowGameTest() {
    }

    /** A Silver Arrow hurts an undead mob 2 more than a plain arrow of the same damage, and any other mob no more. */
    public static void silverArrowsHurtTheUndeadMore(GameTestHelper helper) {
        float zombie = extraDamage(helper, EntityType.ZOMBIE, 1);
        float pig = extraDamage(helper, EntityType.PIG, 4);
        if (Math.abs(zombie - SilverSmiteHandler.ARROW_BONUS_DAMAGE) > 0.5f) {
            fail(helper, "A Silver Arrow hurt a zombie " + zombie + " more than a plain arrow, where it should be about "
                    + SilverSmiteHandler.ARROW_BONUS_DAMAGE + " (before the zombie's armour)");
        } else if (pig != 0.0f) {
            fail(helper, "A Silver Arrow hurt a pig " + pig + " more than a plain arrow");
        } else {
            helper.succeed();
        }
    }

    /** A Silver Arrow leaves a bow 15% faster than a plain one, which is what makes it hit harder. */
    public static void silverArrowsFlyFaster(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Arrow plain = new Arrow(level, 0, 0, 0, new ItemStack(Items.ARROW), null);
        SilverArrow silver = new SilverArrow(level, 0, 0, 0, new ItemStack(ModItems.SILVER_ARROW.get()), null);
        plain.shoot(0, 0, 1, 3.0f, 0.0f);
        silver.shoot(0, 0, 1, 3.0f, 0.0f);
        double ratio = silver.getDeltaMovement().length() / plain.getDeltaMovement().length();
        if (Math.abs(ratio - SilverArrow.SPEED_MULTIPLIER) > 0.001) {
            fail(helper, "A Silver Arrow flew " + ratio + " times as fast as a plain one, not " + SilverArrow.SPEED_MULTIPLIER);
        } else {
            helper.succeed();
        }
    }

    /** Bows and crossbows fire Silver Arrows, eight arrows around an ingot make eight, and one picked up is silver. */
    public static void silverArrowsAreArrows(GameTestHelper helper) {
        ItemStack stack = new ItemStack(ModItems.SILVER_ARROW.get());
        List<String> problems = new ArrayList<>();
        if (!stack.is(ItemTags.ARROWS)) {
            problems.add("it isn't in minecraft:arrows");
        }
        for (Item weapon : List.of(Items.BOW, Items.CROSSBOW)) {
            if (!((ProjectileWeaponItem) weapon).getAllSupportedProjectiles().test(stack)) {
                problems.add(weapon + " doesn't fire it");
            }
        }

        Item arrow = Items.ARROW;
        Item ingot = ModItems.SILVER_INGOT.get();
        ItemStack crafted = TestCompat.craft(helper.getLevel(), CraftingInput.of(3, 3, List.of(
                new ItemStack(arrow), new ItemStack(arrow), new ItemStack(arrow),
                new ItemStack(arrow), new ItemStack(ingot), new ItemStack(arrow),
                new ItemStack(arrow), new ItemStack(arrow), new ItemStack(arrow))));
        if (!crafted.is(ModItems.SILVER_ARROW.get()) || crafted.getCount() != 8) {
            problems.add("eight arrows around a Silver Ingot craft " + crafted);
        }

        SilverArrow fired = new SilverArrow(helper.getLevel(), 0, 0, 0, stack.copy(), null);
        if (!fired.getPickupItemStackOrigin().is(ModItems.SILVER_ARROW.get())) {
            problems.add("one picked up gives " + fired.getPickupItemStackOrigin());
        }

        if (problems.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, "Silver Arrow: " + String.join(", ", problems));
        }
    }

    /** A powered dispenser fires a Silver Arrow, which needs a behaviour of its own. */
    public static void dispensersFireSilverArrows(GameTestHelper helper) {
        BlockPos dispenser = new BlockPos(1, 2, 3);
        helper.setBlock(dispenser, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.EAST));
        helper.setBlock(new BlockPos(5, 2, 3), Blocks.STONE); // stops the arrow inside the arena
        if (!(helper.getBlockEntity(dispenser) instanceof DispenserBlockEntity contents)) {
            fail(helper, "No dispenser was placed");
            return;
        }
        contents.setItem(0, new ItemStack(ModItems.SILVER_ARROW.get()));
        helper.setBlock(dispenser.below(), Blocks.REDSTONE_BLOCK);
        helper.succeedWhen(() -> helper.assertEntityPresent(ModEntityTypes.SILVER_ARROW.get()));
    }

    /**
     * How much more a Silver Arrow hurts a fresh mob of this kind than a plain arrow does, each arrow dealing 4 to a
     * mob of its own, so neither hit is shortened by the other's invulnerability.
     */
    private static float extraDamage(GameTestHelper helper, EntityType<? extends Mob> type, int z) {
        ServerLevel level = helper.getLevel();
        Mob forPlain = helper.spawnWithNoFreeWill(type, new BlockPos(1, 2, z));
        Mob forSilver = helper.spawnWithNoFreeWill(type, new BlockPos(5, 2, z));
        return lost(forSilver, new SilverArrow(level, 0, 0, 0, new ItemStack(ModItems.SILVER_ARROW.get()), null))
                - lost(forPlain, new Arrow(level, 0, 0, 0, new ItemStack(Items.ARROW), null));
    }

    private static float lost(LivingEntity mob, AbstractArrow arrow) {
        float before = mob.getHealth();
        mob.hurt(mob.level().damageSources().arrow(arrow, null), 4.0f);
        return before - mob.getHealth();
    }
}
