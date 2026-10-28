package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.SilverWeatheringSpec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/**
 * Game tests for {@code enable_silver_oxidation}: turned off, no silver block tarnishes and honeycomb waxes none, while
 * an axe still scrapes them back down. Each turns the option off only for as long as it takes, within one tick. Listed
 * in {@link GameTests}.
 */
public final class SilverOxidationGameTest {
    private static final String OPTION = "enable_silver_oxidation";
    /** Random ticks a block gets with the option off: an isolated block tarnishes on about one in 25. */
    private static final int TICKS_OFF = 600;
    /** Random ticks a block may take to tarnish with the option on. */
    private static final int TICKS_ON = 3000;
    private static final BlockPos AT = new BlockPos(3, 2, 3);

    private SilverOxidationGameTest() {
    }

    /** Every silver block that tarnishes stays as it is through its random ticks with the option off, and tarnishes with it on. */
    public static void silverDoesntTarnishWithOxidationOff(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        BlockPos at = helper.absolutePos(AT);
        RandomSource random = level.getRandom();
        List<String> problems = new ArrayList<>();
        for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.oxidationPairs()) {
            Block from = pair.from().get();
            Block to = pair.to().get();
            place(helper, from);
            Block off = TestSettings.with(OPTION, false, () -> tick(level, at, random, from, TICKS_OFF));
            if (off != from) {
                problems.add(name(from) + " became " + name(off) + " with the option off");
            } else {
                Block on = tick(level, at, random, from, TICKS_ON);
                if (on != to) {
                    problems.add(name(from) + (on == from ? " never tarnished" : " became " + name(on)) + " with the option on");
                }
            }
            clear(helper);
        }
        if (problems.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, "Silver oxidation: " + String.join("; ", problems));
        }
    }

    /**
     * With the option off, honeycomb leaves a Block of Silver as it is, and an axe still scrapes a waxed and a tarnished
     * one back down; with it on, honeycomb waxes. Clicked as a player clicks, so the right-click events on both modloaders
     * see it.
     */
    public static void honeycombDoesntWaxWithOxidationOff(GameTestHelper helper) {
        ServerPlayer player = TestCompat.serverPlayer(helper);
        try {
            List<String> problems = TestSettings.with(OPTION, false, () -> {
                List<String> found = new ArrayList<>();
                expectUse(helper, player, ModBlocks.SILVER_BLOCK.get(), Items.HONEYCOMB, ModBlocks.SILVER_BLOCK.get(), found);
                expectUse(helper, player, ModBlocks.WAXED_SILVER_BLOCK.get(), Items.IRON_AXE, ModBlocks.SILVER_BLOCK.get(), found);
                expectUse(helper, player, ModBlocks.EXPOSED_SILVER.get(), Items.IRON_AXE, ModBlocks.SILVER_BLOCK.get(), found);
                return found;
            });
            List<String> on = new ArrayList<>();
            expectUse(helper, player, ModBlocks.SILVER_BLOCK.get(), Items.HONEYCOMB, ModBlocks.WAXED_SILVER_BLOCK.get(), on);
            on.replaceAll(problem -> problem + " with the option on");
            problems.addAll(on);
            if (problems.isEmpty()) {
                helper.succeed();
            } else {
                fail(helper, "With " + OPTION + " off, " + String.join("; ", problems));
            }
        } finally {
            TestCompat.removePlayer(player);
        }
    }

    private static void expectUse(GameTestHelper helper, ServerPlayer player, Block block, Item item, Block expected, List<String> problems) {
        helper.setBlock(AT, block);
        BlockPos at = helper.absolutePos(AT);
        ItemStack stack = new ItemStack(item);
        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        player.gameMode.useItemOn(player, helper.getLevel(), stack, InteractionHand.MAIN_HAND,
                new BlockHitResult(Vec3.atCenterOf(at), Direction.UP, at, false));
        Block found = helper.getBlockState(AT).getBlock();
        if (found != expected) {
            problems.add(BuiltInRegistries.ITEM.getKey(item).getPath() + " on " + name(block) + " made " + name(found)
                    + ", not " + name(expected));
        }
        helper.setBlock(AT, Blocks.AIR);
    }

    /** The block at {@code at} after up to {@code ticks} random ticks, stopping once it has changed. */
    private static Block tick(ServerLevel level, BlockPos at, RandomSource random, Block from, int ticks) {
        for (int i = 0; i < ticks && level.getBlockState(at).is(from); i++) {
            level.getBlockState(at).randomTick(level, at, random);
        }
        return level.getBlockState(at).getBlock();
    }

    /** {@code block} on the arena's floor, a door with its upper half. */
    private static void place(GameTestHelper helper, Block block) {
        BlockState state = block.defaultBlockState();
        helper.setBlock(AT, state);
        if (block instanceof DoorBlock) {
            helper.setBlock(AT.above(), state.setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER));
        }
    }

    private static void clear(GameTestHelper helper) {
        helper.setBlock(AT.above(), Blocks.AIR);
        helper.setBlock(AT, Blocks.AIR);
    }

    private static String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
