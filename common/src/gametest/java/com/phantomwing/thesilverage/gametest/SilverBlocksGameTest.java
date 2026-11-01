package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.custom.MoonPhaseDetectorBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/** Game tests for silver blocks. Listed in {@link GameTests}. */
public final class SilverBlocksGameTest {
    /** The middle of the arena's stone floor: a test's y counts from the structure block, a layer below the arena. */
    static final BlockPos CENTER = new BlockPos(3, 1, 3);

    private SilverBlocksGameTest() {
    }

    /**
     * A Moon Phase Detector at a full-moon night, in NORMAL (non-inverted) mode, emits a full-strength
     * signal. Its ticker only refreshes POWER when {@code gameTime % 20 == 0}, so it waits 25 ticks.
     */
    public static void moonPhaseDetectorFullMoon(GameTestHelper helper) {
        // Day 0 is a full moon; 14000 is night, so the detector reads the phase rather than a day change.
        helper.setDayTime(14000);
        helper.setBlock(CENTER, ModBlocks.MOON_PHASE_DETECTOR.get());
        helper.startSequence()
                .thenExecuteAfter(25, () -> helper.assertBlockProperty(CENTER, MoonPhaseDetectorBlock.POWER, 15))
                .thenSucceed();
    }

    /** A silver torch on a wall drops a silver torch: the copy of vanilla's wall torch carried a torch's drop on Fabric. */
    public static void silverWallTorchDropsASilverTorch(GameTestHelper helper) {
        List<ItemStack> drops = Block.getDrops(ModBlocks.SILVER_WALL_TORCH.get().defaultBlockState(), helper.getLevel(),
                helper.absolutePos(CENTER.above()), null);
        if (drops.size() == 1 && drops.get(0).is(ModBlocks.SILVER_TORCH.get().asItem())) {
            helper.succeed();
        } else {
            fail(helper, "A silver wall torch drops " + drops + ", not a silver torch");
        }
    }

    /** Right-clicking an unwaxed Block of Silver with a Honeycomb waxes it. */
    public static void waxingSilverBlock(GameTestHelper helper) {
        helper.setBlock(CENTER, ModBlocks.SILVER_BLOCK.get());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.HONEYCOMB));
        helper.useBlock(CENTER, player);
        helper.succeedWhenBlockPresent(ModBlocks.WAXED_SILVER_BLOCK.get(), CENTER);
    }

    /** A Silver Bulb starts unlit and lights up when it receives redstone power. */
    public static void silverBulbLighting(GameTestHelper helper) {
        helper.setBlock(CENTER, ModBlocks.SILVER_BULB.get());
        helper.assertBlockProperty(CENTER, BlockStateProperties.LIT, false);
        helper.setBlock(CENTER.above(), Blocks.REDSTONE_BLOCK);
        helper.startSequence()
                .thenExecuteAfter(3, () -> helper.assertBlockProperty(CENTER, BlockStateProperties.LIT, true))
                .thenSucceed();
    }

    /** A wind charge bursting at a Silver Door opens it, as it does a copper one. */
    public static void windChargeOpensSilverDoor(GameTestHelper helper) {
        BlockState lower = ModBlocks.SILVER_DOOR.get().defaultBlockState();
        helper.setBlock(CENTER, lower.setValue(DoorBlock.HALF, DoubleBlockHalf.LOWER));
        helper.setBlock(CENTER.above(), lower.setValue(DoorBlock.HALF, DoubleBlockHalf.UPPER));
        TestCompat.windChargeBurst(helper.getLevel(), Vec3.atCenterOf(helper.absolutePos(CENTER)));
        helper.startSequence()
                .thenExecuteAfter(1, () -> helper.assertBlockProperty(CENTER, DoorBlock.OPEN, true))
                .thenSucceed();
    }

    /** A wind charge bursting at a Silver Trapdoor opens it, as it does a copper one. */
    public static void windChargeOpensSilverTrapdoor(GameTestHelper helper) {
        helper.setBlock(CENTER, ModBlocks.SILVER_TRAPDOOR.get());
        TestCompat.windChargeBurst(helper.getLevel(), Vec3.atCenterOf(helper.absolutePos(CENTER)));
        helper.startSequence()
                .thenExecuteAfter(1, () -> helper.assertBlockProperty(CENTER, TrapDoorBlock.OPEN, true))
                .thenSucceed();
    }
}
