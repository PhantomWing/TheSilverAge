package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.combat.AvoidSilverLightGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/**
 * Game tests for idle undead keeping away from silver light ({@link AvoidSilverLightGoal}): a husk's goal finds a Silver
 * Torch or Lantern beside it, and none with a plain torch there, with a target, or with
 * {@code undead_avoid_silver_light} off. The goal is asked directly, as many goal ticks as its search waits: the arena is
 * too small to walk off, so where a husk would flee to can't be reached in it. Listed in {@link GameTests}.
 */
public final class SilverLightGameTest {
    /** Goal ticks the goal is given: its search waits up to 5. */
    private static final int TRIES = 10;
    private static final BlockPos LIGHT = new BlockPos(3, 2, 3);

    private SilverLightGameTest() {
    }

    public static void undeadKeepAwayFromSilverTorches(GameTestHelper helper) {
        keepsAway(helper, ModBlocks.SILVER_TORCH.get());
    }

    public static void undeadKeepAwayFromSilverLanterns(GameTestHelper helper) {
        keepsAway(helper, ModBlocks.SILVER_LANTERN.get());
    }

    private static void keepsAway(GameTestHelper helper, Block light) {
        String name = BuiltInRegistries.BLOCK.getKey(light).getPath();
        List<String> problems = new ArrayList<>();

        helper.setBlock(LIGHT, Blocks.TORCH);
        if (lightFound(helper, false) != null) {
            problems.add("a husk beside a plain torch kept away from it");
        }
        helper.setBlock(LIGHT, light);
        BlockPos found = lightFound(helper, false);
        if (!helper.absolutePos(LIGHT).equals(found)) {
            problems.add("a husk beside it found " + (found == null ? "nothing" : "something else") + " to keep away from");
        }
        if (lightFound(helper, true) != null) {
            problems.add("a husk beside it with a target kept away from it");
        }
        if (TestSettings.with("undead_avoid_silver_light", false, () -> lightFound(helper, false)) != null) {
            problems.add("a husk beside it kept away from it with undead_avoid_silver_light off");
        }
        if (problems.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, "Beside a " + name + ", " + String.join("; ", problems));
        }
    }

    /** The light a new husk beside it finds to keep away from, or null. */
    private static BlockPos lightFound(GameTestHelper helper, boolean withTarget) {
        PathfinderMob husk = helper.spawn(EntityType.HUSK, LIGHT.east());
        PathfinderMob target = null;
        if (withTarget) {
            target = helper.spawnWithNoFreeWill(EntityType.VILLAGER, new BlockPos(1, 2, 1));
            husk.setTarget(target);
        }
        AvoidSilverLightGoal goal = new AvoidSilverLightGoal(husk);
        for (int i = 0; i < TRIES && goal.light() == null; i++) {
            goal.canUse();
        }
        husk.discard();
        if (target != null) {
            target.discard();
        }
        return goal.light();
    }
}
