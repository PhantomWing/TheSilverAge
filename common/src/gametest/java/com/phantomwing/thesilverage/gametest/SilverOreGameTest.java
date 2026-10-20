package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.world.ConfigBooleanPlacementFilter;
import com.phantomwing.thesilverage.world.ModPlacedFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;
import java.util.Optional;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/** Game tests for silver ore generation. Listed in {@link GameTests}. */
public final class SilverOreGameTest {
    private SilverOreGameTest() {
    }

    /**
     * Each silver ore placement asks {@code generate_silver_ore} before anything else, and the filter lets a position
     * through while its option is on and none while it's off: an option no setting has reads as off.
     */
    public static void silverOreFollowsItsConfig(GameTestHelper helper) {
        ServerLevel level = helper.getLevel();
        Registry<PlacedFeature> placed = level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
        for (ResourceKey<PlacedFeature> key : List.of(ModPlacedFeatures.ORE_SILVER, ModPlacedFeatures.ORE_SILVER_LOWER, ModPlacedFeatures.ORE_SILVER_EXTRA)) {
            List<PlacementModifier> placement = placed.getOrThrow(key).placement();
            if (placement.isEmpty() || !(placement.get(0) instanceof ConfigBooleanPlacementFilter filter)
                    || !ModPlacedFeatures.GENERATE_SILVER_ORE_ID.equals(filter.settingId())) {
                fail(helper, key.location() + " doesn't ask generate_silver_ore before placing");
                return;
            }
        }
        PlacementContext context = new PlacementContext(level, level.getChunkSource().getGenerator(), Optional.empty());
        BlockPos pos = helper.absolutePos(BlockPos.ZERO);
        long on = new ConfigBooleanPlacementFilter(ModPlacedFeatures.GENERATE_SILVER_ORE_ID).getPositions(context, RandomSource.create(), pos).count();
        long off = new ConfigBooleanPlacementFilter("no_such_setting").getPositions(context, RandomSource.create(), pos).count();
        if (on != 1 || off != 0) {
            fail(helper, "The ore's config filter placed " + on + " with its option on and " + off + " with it off, not 1 and 0");
        } else {
            helper.succeed();
        }
    }
}
