package com.phantomwing.thesilverage.neoforge.gametest;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.custom.MoonPhaseDetectorBlock;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Consumer;

/**
 * Automated regression tests for The Silver Age, on Minecraft 1.21.5+'s data-driven
 * GameTest framework.
 *
 * <p><b>This whole package is a dedicated {@code gametest} source set</b> (see
 * neoforge/build.gradle). Nothing here — neither these classes nor the {@code test_instance}
 * JSON / arena structure under {@code src/gametest/resources} — is part of {@code main}, so it
 * never ships in the production jar and never sits on the runClient/runServer classpath. It is
 * wired onto the {@code :neoforge:runGameTest} run ONLY.</p>
 *
 * <p><b>How the new system fits together.</b> 1.21.5 replaced annotation-based GameTests with
 * registry objects:
 * <ul>
 *   <li>The Java assertion logic lives here as {@link Consumer}&lt;{@link GameTestHelper}&gt;
 *       entries in the {@link Registries#TEST_FUNCTION} registry. They are registered via
 *       {@link RegisterEvent} below — fired because this class is an {@link EventBusSubscriber}
 *       and the source set is on the gametest run's (mod) classpath.</li>
 *   <li>Each function is bound to a runnable test by a {@code test_instance} datapack JSON
 *       (type {@code minecraft:function}) under
 *       {@code src/gametest/resources/data/thesilverage/test_instance/}, which also names the
 *       built-in {@code minecraft:default} environment and the shared arena structure.</li>
 *   <li>The arena ({@code silver_test_arena.nbt}, a 7x5x7 stone-floored box) is a committed
 *       static resource under {@code src/gametest/resources/data/thesilverage/structure/}.</li>
 * </ul>
 */
@EventBusSubscriber(modid = TheSilverAge.MOD_ID)
public final class TheSilverAgeGameTests {
    /** All assertions run inside the shared arena; the floor sits at relative y=0. */
    private static final BlockPos CENTER = new BlockPos(3, 1, 3);

    private TheSilverAgeGameTests() {
    }

    @SubscribeEvent
    static void registerTestFunctions(RegisterEvent event) {
        event.register(Registries.TEST_FUNCTION, helper -> {
            helper.register(id("moon_phase_detector_full_moon"),
                    (Consumer<GameTestHelper>) TheSilverAgeGameTests::moonPhaseDetectorFullMoon);
            helper.register(id("waxing_silver_block"),
                    (Consumer<GameTestHelper>) TheSilverAgeGameTests::waxingSilverBlock);
            helper.register(id("silver_bulb_lighting"),
                    (Consumer<GameTestHelper>) TheSilverAgeGameTests::silverBulbLighting);
            helper.register(id("silverfish_drops_silver"),
                    (Consumer<GameTestHelper>) TheSilverAgeGameTests::silverfishDropsSilver);
        });
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, path);
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
