package com.phantomwing.thesilverage.neoforge.gametest;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.custom.MoonPhaseDetectorBlock;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityTypes;
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
 * Automated regression tests, registered as TEST_FUNCTION entries bound to test_instance JSON
 * under src/gametest/resources. This is a dedicated gametest source set, not in the production jar.
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

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, path);
    }

    // ------------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------------

    /**
     * Full-moon night, NORMAL mode must emit POWER 15. The ticker only refreshes POWER when
     * gameTime % 20 == 0, so wait 25 ticks (guaranteed to cross a boundary) before asserting.
     */
    private static void moonPhaseDetectorFullMoon(GameTestHelper helper) {
        // Day 0 = moon phase 0 (full); 14000 ticks-of-day is night.
        ServerLevel level = helper.getLevel();
        Holder<WorldClock> overworldClock = level.registryAccess()
                .lookupOrThrow(Registries.WORLD_CLOCK).getOrThrow(WorldClocks.OVERWORLD);
        level.clockManager().setTotalTicks(overworldClock, 14000L);
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
        helper.setBlock(CENTER.above(), Blocks.REDSTONE_BLOCK);
        helper.startSequence()
                .thenExecuteAfter(3, () ->
                        helper.assertBlockProperty(CENTER, BlockStateProperties.LIT, true))
                .thenSucceed();
    }

    /**
     * Killing silverfish drops Silver Nuggets via the GLM. Drop count is uniform 0..2, so kill a
     * batch of 15 to make at least one nugget overwhelmingly certain.
     */
    private static void silverfishDropsSilver(GameTestHelper helper) {
        for (int i = 0; i < 15; i++) {
            Silverfish silverfish = helper.spawnWithNoFreeWill(EntityTypes.SILVERFISH, CENTER);
            helper.kill(silverfish);
        }
        helper.startSequence()
                .thenExecuteAfter(2, () ->
                        helper.assertItemEntityPresent(ModItems.SILVER_NUGGET.get()))
                .thenSucceed();
    }
}
