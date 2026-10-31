package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CopperBulbBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/**
 * Game tests for silver as a material, the ones The Tin Age and The Lead Age have for theirs: its ore and its drops, its
 * storage recipes, its gear smelting down, which pickaxe each block takes, the stonecutter's brick set, the bulb's
 * toggle, its horse armour and its blocks' names, and honeycomb from a dispenser with oxidation off. Listed in
 * {@link GameTests}.
 */
public final class SilverMaterialGameTest {
    private SilverMaterialGameTest() {
    }

    /** Silver ore and its deepslate twin drop Raw Silver to an iron pickaxe. */
    public static void silverOreDropsRawSilver(GameTestHelper helper) {
        ItemStack pickaxe = new ItemStack(Items.IRON_PICKAXE);
        for (Block ore : List.of(ModBlocks.SILVER_ORE.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get())) {
            List<ItemStack> drops = dropsOf(helper, ore.defaultBlockState(), pickaxe);
            TestCompat.assertTrue(helper, !drops.isEmpty() && drops.stream().allMatch(drop -> drop.is(ModItems.RAW_SILVER.get())),
                    ore + " mined with an iron pickaxe should drop Raw Silver, dropped " + drops);
        }
        helper.succeed();
    }

    /** Silk Touch keeps the ores as they are. */
    public static void silkTouchKeepsSilverOre(GameTestHelper helper) {
        ItemStack pickaxe = TestCompat.silkTouchPickaxe(helper.getLevel());
        for (Block ore : List.of(ModBlocks.SILVER_ORE.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get())) {
            List<ItemStack> drops = dropsOf(helper, ore.defaultBlockState(), pickaxe);
            TestCompat.assertTrue(helper, drops.size() == 1 && drops.get(0).is(ore.asItem()),
                    ore + " mined with Silk Touch should drop itself, dropped " + drops);
        }
        helper.succeed();
    }

    /** Nine nuggets make an ingot and back, nine ingots a Block of Silver and back, nine Raw Silver a block and back. */
    public static void silverStorageRecipesRoundTrip(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        roundTrip(helper, level, ModItems.SILVER_NUGGET.get().getDefaultInstance(), ModItems.SILVER_INGOT.get().getDefaultInstance());
        roundTrip(helper, level, ModItems.SILVER_INGOT.get().getDefaultInstance(), ModItems.SILVER_BLOCK.get().getDefaultInstance());
        roundTrip(helper, level, ModItems.RAW_SILVER.get().getDefaultInstance(), ModItems.RAW_SILVER_BLOCK.get().getDefaultInstance());
        helper.succeed();
    }

    /** Every silver tool, piece of armour and the horse armour smelts into a Silver Nugget, as iron's and gold's do. */
    public static void silverGearSmeltsIntoANugget(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        for (Supplier<? extends net.minecraft.world.item.Item> gear : List.of(ModItems.SILVER_SWORD, ModItems.SILVER_PICKAXE,
                ModItems.SILVER_AXE, ModItems.SILVER_SHOVEL, ModItems.SILVER_HOE, ModItems.SILVER_HELMET,
                ModItems.SILVER_CHESTPLATE, ModItems.SILVER_LEGGINGS, ModItems.SILVER_BOOTS, ModItems.SILVER_HORSE_ARMOR)) {
            ItemStack result = TestCompat.smelt(level, new ItemStack(gear.get()));
            TestCompat.assertTrue(helper, result.is(ModItems.SILVER_NUGGET.get()),
                    gear.get() + " should smelt into a Silver Nugget, gave " + result);
        }
        helper.succeed();
    }

    /** Silver's blocks need an iron pickaxe, as gold's do: a stone one mines them for nothing. */
    public static void silverBlocksNeedAnIronPickaxe(GameTestHelper helper) {
        for (Block block : List.of(ModBlocks.SILVER_ORE.get(), ModBlocks.SILVER_BLOCK.get(), ModBlocks.CUT_SILVER.get(),
                ModBlocks.SILVER_BRICKS.get(), ModBlocks.SILVER_GRATE.get(), ModBlocks.SILVER_BULB.get())) {
            BlockState state = block.defaultBlockState();
            boolean stone = new ItemStack(Items.STONE_PICKAXE).isCorrectToolForDrops(state);
            boolean iron = new ItemStack(Items.IRON_PICKAXE).isCorrectToolForDrops(state);
            TestCompat.assertTrue(helper, state.requiresCorrectToolForDrops() && !stone && iron,
                    block + " takes a stone pickaxe: " + stone + ", an iron one: " + iron);
        }
        helper.succeed();
    }

    /** The bars, chain, lantern and pressure plate drop to any pickaxe, as vanilla's iron ones and weighted plates do. */
    public static void silverFittingsDropToAnyPickaxe(GameTestHelper helper) {
        for (Block block : List.of(ModBlocks.SILVER_BARS.get(), ModBlocks.OXIDIZED_SILVER_BARS.get(),
                ModBlocks.SILVER_CHAIN.get(), ModBlocks.WAXED_SILVER_CHAIN.get(), ModBlocks.SILVER_LANTERN.get(),
                ModBlocks.WEATHERED_SILVER_LANTERN.get(), ModBlocks.SILVER_PRESSURE_PLATE.get())) {
            BlockState state = block.defaultBlockState();
            TestCompat.assertTrue(helper, new ItemStack(Items.WOODEN_PICKAXE).isCorrectToolForDrops(state),
                    block + " doesn't drop to a wooden pickaxe");
        }
        helper.succeed();
    }

    /** The stonecutter makes the brick set from Cut Silver, one for one as the 2x2 craft does. */
    public static void theStonecutterCutsSilverBricksFromCutSilver(GameTestHelper helper) {
        List<ItemStack> cuts = TestCompat.stonecut(helper.getLevel(), new ItemStack(ModItems.CUT_SILVER.get()));
        for (ItemStack expected : List.of(new ItemStack(ModItems.SILVER_BRICKS.get()), new ItemStack(ModItems.SILVER_BRICK_STAIRS.get()),
                new ItemStack(ModItems.SILVER_BRICK_SLAB.get(), 2), new ItemStack(ModItems.SILVER_BRICK_WALL.get()))) {
            TestCompat.assertTrue(helper, cuts.stream().anyMatch(cut -> ItemStack.matches(cut, expected)),
                    "the stonecutter doesn't make " + expected + " from Cut Silver, only " + cuts);
        }
        helper.succeed();
    }

    /** A waxed Silver Bulb turns on at a pulse, stays on when the power goes, and turns off at the next pulse. */
    public static void silverBulbTogglesOnEachPulse(GameTestHelper helper) {
        BlockPos bulb = new BlockPos(1, 2, 1);
        BlockPos power = bulb.above();
        helper.setBlock(bulb, ModBlocks.WAXED_SILVER_BULB.get());
        helper.setBlock(power, Blocks.REDSTONE_BLOCK);
        helper.runAfterDelay(2, () -> {
            if (!helper.getBlockState(bulb).getValue(CopperBulbBlock.LIT)) {
                fail(helper, "A redstone pulse didn't turn the Waxed Silver Bulb on");
                return;
            }
            helper.setBlock(power, Blocks.AIR);
            helper.runAfterDelay(2, () -> {
                if (!helper.getBlockState(bulb).getValue(CopperBulbBlock.LIT)) {
                    fail(helper, "The Waxed Silver Bulb turned off when its power went");
                    return;
                }
                helper.setBlock(power, Blocks.REDSTONE_BLOCK);
                helper.runAfterDelay(2, () -> {
                    if (helper.getBlockState(bulb).getValue(CopperBulbBlock.LIT)) {
                        fail(helper, "A second redstone pulse didn't turn the Waxed Silver Bulb off");
                    } else {
                        helper.succeed();
                    }
                });
            });
        });
    }

    /** Silver Horse Armor never wears out, as vanilla's don't. */
    public static void horseArmorHasNoDurability(GameTestHelper helper) {
        ItemStack armor = new ItemStack(ModItems.SILVER_HORSE_ARMOR.get());
        if (armor.isDamageableItem()) {
            fail(helper, "Silver Horse Armor wears out: it has a durability of " + armor.getMaxDamage());
            return;
        }
        helper.succeed();
    }

    /** Every block of the mod is named by its own translation key, or its item's. */
    public static void blocksAreNamedByTheirOwnKeys(GameTestHelper helper) {
        List<String> misnamed = new ArrayList<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            var id = BuiltInRegistries.BLOCK.getKey(block);
            // Util.makeDescriptionId's key, written out: Util moved package in 1.21.11.
            String own = "block." + id.getNamespace() + "." + id.getPath().replace('/', '.');
            String name = block.getDescriptionId();
            if (id.getNamespace().equals(TheSilverAge.MOD_ID) && !name.equals(own)
                    && !name.equals(block.asItem().getDescriptionId())) {
                misnamed.add(id + " is named " + name);
            }
        }
        if (misnamed.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, String.join(", ", misnamed));
        }
    }

    /** With {@code enable_silver_oxidation} off a dispenser's honeycomb waxes no silver, and stays in the dispenser. */
    public static void dispensersDontWaxWithOxidationOff(GameTestHelper helper) {
        BlockPos dispenser = new BlockPos(1, 2, 1);
        BlockPos target = dispenser.east();
        helper.setBlock(dispenser, Blocks.DISPENSER.defaultBlockState().setValue(DispenserBlock.FACING, Direction.EAST));
        ServerLevel level = helper.getLevel();
        BlockPos at = helper.absolutePos(dispenser);
        BlockState state = level.getBlockState(at);
        DispenserBlockEntity entity = (DispenserBlockEntity) level.getBlockEntity(at);
        BlockSource source = new BlockSource(level, at, state, entity);
        List<String> problems = new ArrayList<>();

        helper.setBlock(target, ModBlocks.SILVER_BLOCK.get());
        ItemStack off = TestSettings.with("enable_silver_oxidation", false,
                () -> DispenserBlock.DISPENSER_REGISTRY.get(Items.HONEYCOMB).dispense(source, new ItemStack(Items.HONEYCOMB)));
        if (!helper.getBlockState(target).is(ModBlocks.SILVER_BLOCK.get()) || off.isEmpty()) {
            problems.add("with the option off it made " + helper.getBlockState(target).getBlock() + " and kept " + off);
        }

        ItemStack on = DispenserBlock.DISPENSER_REGISTRY.get(Items.HONEYCOMB).dispense(source, new ItemStack(Items.HONEYCOMB));
        if (!helper.getBlockState(target).is(ModBlocks.WAXED_SILVER_BLOCK.get()) || !on.isEmpty()) {
            problems.add("with the option on it made " + helper.getBlockState(target).getBlock() + " and kept " + on);
        }
        if (problems.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, "A dispenser's honeycomb on a Block of Silver: " + String.join("; ", problems));
        }
    }

    private static List<ItemStack> dropsOf(GameTestHelper helper, BlockState state, ItemStack tool) {
        BlockPos pos = helper.absolutePos(new BlockPos(0, 2, 0));
        return Block.getDrops(state, helper.getLevel(), pos, null, null, tool);
    }

    private static void roundTrip(GameTestHelper helper, ServerLevel level, ItemStack small, ItemStack packed) {
        ItemStack up = TestCompat.craft(level, CraftingInput.of(3, 3, Collections.nCopies(9, small)));
        TestCompat.assertTrue(helper, up.is(packed.getItem()) && up.getCount() == 1, "9 " + small + " should make " + packed + ", made " + up);
        ItemStack down = TestCompat.craft(level, CraftingInput.of(1, 1, List.of(packed)));
        TestCompat.assertTrue(helper, down.is(small.getItem()) && down.getCount() == 9, packed + " should make 9 " + small + ", made " + down);
    }
}
