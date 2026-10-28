package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/**
 * Game tests for the Silver Pressure Plate: no hostile mob presses it, the undead are hurt standing in it, and the more
 * it has tarnished the less each mob on it adds to its signal. The plates lie on the arena's floor, at y = 2, and the
 * mobs on them are kept from walking off. Listed in {@link GameTests}.
 */
public final class SilverPressurePlateGameTest {
    /** Ticks the mobs stand on their plates before the plates are read. */
    private static final int STANDING = 40;

    private SilverPressurePlateGameTest() {
    }

    /** A villager presses a plate to a full signal; a husk and a creeper press nothing, and the husk is hurt. */
    public static void hostileMobsDontPressSilverPlates(GameTestHelper helper) {
        BlockPos villagerPlate = new BlockPos(1, 2, 3);
        BlockPos huskPlate = new BlockPos(3, 2, 3);
        BlockPos creeperPlate = new BlockPos(5, 2, 3);
        LivingEntity villager = standOn(helper, villagerPlate, ModBlocks.SILVER_PRESSURE_PLATE.get(), EntityType.VILLAGER);
        LivingEntity husk = standOn(helper, huskPlate, ModBlocks.SILVER_PRESSURE_PLATE.get(), EntityType.HUSK);
        LivingEntity creeper = standOn(helper, creeperPlate, ModBlocks.SILVER_PRESSURE_PLATE.get(), EntityType.CREEPER);
        helper.runAfterDelay(STANDING, () -> {
            List<String> problems = new ArrayList<>();
            expectPower(helper, problems, villagerPlate, "a villager", 15);
            expectPower(helper, problems, huskPlate, "a husk", 0);
            expectPower(helper, problems, creeperPlate, "a creeper", 0);
            if (husk.getHealth() >= husk.getMaxHealth()) {
                problems.add("a husk standing in one for " + STANDING + " ticks wasn't hurt");
            }
            finish(helper, problems, villager, husk, creeper);
        });
    }

    /** One villager on each stage's plate, waxed or not, gives 15, 5, 3 and 1: full power at 1, 3, 5 and 15 mobs. */
    public static void tarnishedPlatesNeedMoreMobs(GameTestHelper helper) {
        List<Block> plates = List.of(ModBlocks.SILVER_PRESSURE_PLATE.get(), ModBlocks.EXPOSED_SILVER_PRESSURE_PLATE.get(),
                ModBlocks.WEATHERED_SILVER_PRESSURE_PLATE.get(), ModBlocks.OXIDIZED_SILVER_PRESSURE_PLATE.get(),
                ModBlocks.WAXED_SILVER_PRESSURE_PLATE.get(), ModBlocks.WAXED_EXPOSED_SILVER_PRESSURE_PLATE.get(),
                ModBlocks.WAXED_WEATHERED_SILVER_PRESSURE_PLATE.get(), ModBlocks.WAXED_OXIDIZED_SILVER_PRESSURE_PLATE.get());
        int[] signals = {15, 5, 3, 1, 15, 5, 3, 1};
        List<BlockPos> places = new ArrayList<>();
        List<LivingEntity> villagers = new ArrayList<>();
        for (int i = 0; i < plates.size(); i++) {
            BlockPos place = new BlockPos(1 + 2 * (i % 3), 2, 1 + 2 * (i / 3));
            places.add(place);
            villagers.add(standOn(helper, place, plates.get(i), EntityType.VILLAGER));
        }
        helper.runAfterDelay(STANDING, () -> {
            List<String> problems = new ArrayList<>();
            for (int i = 0; i < plates.size(); i++) {
                expectPower(helper, problems, places.get(i), "a villager", signals[i]);
            }
            finish(helper, problems, villagers.toArray(LivingEntity[]::new));
        });
    }

    /** A {@code type} standing on {@code plate}, placed at {@code at}, that doesn't walk off it. */
    private static LivingEntity standOn(GameTestHelper helper, BlockPos at, Block plate, EntityType<? extends LivingEntity> type) {
        helper.setBlock(at, plate);
        LivingEntity mob = helper.spawn(type, at);
        mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0);
        return mob;
    }

    private static void expectPower(GameTestHelper helper, List<String> problems, BlockPos at, String who, int power) {
        BlockState state = helper.getBlockState(at);
        int found = state.hasProperty(WeightedPressurePlateBlock.POWER) ? state.getValue(WeightedPressurePlateBlock.POWER) : -1;
        if (found != power) {
            problems.add(BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath() + " with " + who + " on it gives "
                    + found + ", not " + power);
        }
    }

    private static void finish(GameTestHelper helper, List<String> problems, LivingEntity... mobs) {
        for (LivingEntity mob : mobs) {
            mob.discard();
        }
        if (problems.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, "Silver Pressure Plates: " + String.join("; ", problems));
        }
    }
}
