package com.phantomwing.thesilverage.neoforge.gametest;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.custom.MoonPhaseDetectorBlock;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Automated regression tests for The Silver Age, on Minecraft 1.21.5+'s data-driven
 * GameTest framework.
 *
 * <p><b>How the new system fits together.</b> 1.21.5 replaced the old annotation-based
 * GameTests ({@code @GameTest}/{@code @GameTestHolder}) with registry objects:
 * <ul>
 *   <li>The Java assertion logic lives here as {@link Consumer}&lt;{@link GameTestHelper}&gt;
 *       entries in the {@link Registries#TEST_FUNCTION} registry (registered below).</li>
 *   <li>Each is bound to a runnable test by a {@code test_instance} datapack JSON
 *       (type {@code minecraft:function}) under
 *       {@code neoforge/src/main/resources/data/thesilverage/test_instance/}, which also
 *       names the {@code test_environment} and the {@code structure} the test runs in.</li>
 *   <li>The shared empty 7x5x7 stone-floored arena is generated as an NBT structure by
 *       {@code GameTestStructureProvider} (datagen) at
 *       {@code data/thesilverage/structure/silver_test_arena.nbt}.</li>
 * </ul>
 *
 * <p><b>Why this ships in the main jar.</b> The {@code test_instance} entries reference
 * these function ids, so the functions are registered <em>unconditionally</em> — that way
 * the datapack registry always resolves cleanly on any server (the tests simply never run
 * outside the dedicated gametest server). The footprint is a handful of tiny JSON files,
 * one small structure NBT and these lambdas. Run them with the {@code :neoforge:runGameTest}
 * Gradle task (see neoforge/build.gradle).
 */
public final class SilverGameTests {
    /** All assertions run inside the shared arena; the floor sits at relative y=0. */
    private static final BlockPos CENTER = new BlockPos(3, 1, 3);

    public static final DeferredRegister<Consumer<GameTestHelper>> TEST_FUNCTIONS =
            DeferredRegister.create(Registries.TEST_FUNCTION, TheSilverAge.MOD_ID);

    public static final Supplier<Consumer<GameTestHelper>> MOON_PHASE_DETECTOR_FULL_MOON =
            TEST_FUNCTIONS.register("moon_phase_detector_full_moon",
                    () -> (Consumer<GameTestHelper>) SilverGameTests::moonPhaseDetectorFullMoon);
    public static final Supplier<Consumer<GameTestHelper>> WAXING_SILVER_BLOCK =
            TEST_FUNCTIONS.register("waxing_silver_block",
                    () -> (Consumer<GameTestHelper>) SilverGameTests::waxingSilverBlock);
    public static final Supplier<Consumer<GameTestHelper>> SILVER_BULB_LIGHTING =
            TEST_FUNCTIONS.register("silver_bulb_lighting",
                    () -> (Consumer<GameTestHelper>) SilverGameTests::silverBulbLighting);
    public static final Supplier<Consumer<GameTestHelper>> SILVERFISH_DROPS_SILVER =
            TEST_FUNCTIONS.register("silverfish_drops_silver",
                    () -> (Consumer<GameTestHelper>) SilverGameTests::silverfishDropsSilver);

    private SilverGameTests() {
    }

    public static void register(IEventBus modEventBus) {
        TEST_FUNCTIONS.register(modEventBus);
    }

    // ------------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------------

    /**
     * A Moon Phase Detector at a full-moon night, in NORMAL (non-inverted) mode, must emit a
     * full-strength redstone signal (POWER 15). The block's BlockEntity ticker only refreshes
     * POWER when {@code gameTime % 20 == 0}, so we wait 25 ticks (guaranteed to cross a
     * boundary) before asserting.
     */
    private static void moonPhaseDetectorFullMoon(GameTestHelper helper) {
        // Day 0 -> moon phase 0 (full); 14000 ticks-of-day is night, so the detector reads
        // the current (full) phase rather than a day transition. NORMAL mode: POWER = 15 - 0.
        helper.setDayTime(14000);
        helper.setBlock(CENTER, ModBlocks.MOON_PHASE_DETECTOR.get());
        helper.startSequence()
                .thenExecuteAfter(25, () ->
                        helper.assertBlockProperty(CENTER, MoonPhaseDetectorBlock.POWER, 15))
                .thenSucceed();
    }

    /** Right-clicking an unwaxed Block of Silver with a Honeycomb waxes it. */
    private static void waxingSilverBlock(GameTestHelper helper) {
        helper.setBlock(CENTER, ModBlocks.SILVER_BLOCK.get());
        Player player = helper.makeMockPlayer(GameType.SURVIVAL);
        player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.HONEYCOMB));
        helper.useBlock(CENTER, player);
        helper.succeedWhenBlockPresent(ModBlocks.WAXED_SILVER_BLOCK.get(), CENTER);
    }

    /** A Silver Bulb starts unlit and lights up (LIT=true) when it receives redstone power. */
    private static void silverBulbLighting(GameTestHelper helper) {
        helper.setBlock(CENTER, ModBlocks.SILVER_BULB.get());
        helper.assertBlockProperty(CENTER, BlockStateProperties.LIT, false);
        // A redstone block placed adjacent powers the bulb; copper-bulb logic flips LIT on the
        // rising power edge.
        helper.setBlock(CENTER.above(), Blocks.REDSTONE_BLOCK);
        helper.startSequence()
                .thenExecuteAfter(3, () ->
                        helper.assertBlockProperty(CENTER, BlockStateProperties.LIT, true))
                .thenSucceed();
    }

    /**
     * Killing silverfish drops Silver Nuggets (via the silverfish-drop GLM). The drop count is
     * a uniform 0..2, so a single kill can yield nothing — we kill a batch so at least one
     * nugget is overwhelmingly certain (P(no drop) = (1/3)^15 ~ 7e-8).
     */
    private static void silverfishDropsSilver(GameTestHelper helper) {
        for (int i = 0; i < 15; i++) {
            Silverfish silverfish = helper.spawnWithNoFreeWill(EntityType.SILVERFISH, CENTER);
            helper.kill(silverfish);
        }
        helper.startSequence()
                .thenExecuteAfter(2, () ->
                        helper.assertItemEntityPresent(ModItems.SILVER_NUGGET.get()))
                .thenSucceed();
    }
}
