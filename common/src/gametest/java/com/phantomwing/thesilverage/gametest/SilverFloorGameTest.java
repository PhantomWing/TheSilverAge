package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.combat.SilverFloor;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/**
 * Game tests for silver floors ({@link SilverFloor}): a mob silver harms is hurt standing on silver, the more slowly the
 * more the silver has tarnished, can't spawn on it, walks round it and never picks it as somewhere to go; other mobs
 * take no notice. Listed in {@link GameTests}.
 */
public final class SilverFloorGameTest {
    /**
     * The height of the arena's stone floor in a test's coordinates, which count from the structure block a layer below
     * the arena: what stands on the floor stands at {@code FLOOR + 1}.
     */
    private static final int FLOOR = 1;
    /**
     * Ticks each mob stands on its floor before its health is read: ten or eleven of fresh silver's hits, and a count no
     * other stage's next hit (at 120) can change.
     */
    private static final int STANDING = 110;

    private SilverFloorGameTest() {
    }

    /**
     * Mobs in pens, each on its own floor: an undead loses health at its silver's pace, fresh silver four times as fast as
     * oxidized and waxed silver as fast as its stage; a wither skeleton, which fire can't hurt, is hurt too; a husk on
     * stone and a cow on silver aren't.
     */
    public static void silverFloorsHurtTheUndead(GameTestHelper helper) {
        pens(helper);
        List<Pen> pens = List.of(
                new Pen(1, 1, ModBlocks.SILVER_BLOCK.get(), EntityType.HUSK, 10, 11),
                new Pen(3, 1, ModBlocks.EXPOSED_SILVER.get(), EntityType.HUSK, 5, 5),
                new Pen(5, 1, ModBlocks.WEATHERED_SILVER.get(), EntityType.HUSK, 3, 3),
                new Pen(1, 3, ModBlocks.OXIDIZED_SILVER.get(), EntityType.HUSK, 2, 2),
                new Pen(3, 3, ModBlocks.WAXED_OXIDIZED_SILVER.get(), EntityType.HUSK, 2, 2),
                new Pen(5, 3, ModBlocks.WAXED_SILVER_BRICK_SLAB.get(), EntityType.HUSK, 10, 11),
                new Pen(1, 5, ModBlocks.SILVER_BLOCK.get(), EntityType.WITHER_SKELETON, 10, 11),
                new Pen(3, 5, Blocks.STONE, EntityType.HUSK, 0, 0),
                new Pen(5, 5, ModBlocks.SILVER_BLOCK.get(), EntityType.COW, 0, 0));
        List<LivingEntity> mobs = new ArrayList<>();
        for (Pen pen : pens) {
            helper.setBlock(floor(pen.x, pen.z), pen.floor);
            LivingEntity mob = helper.spawn(pen.type, floor(pen.x, pen.z).above());
            // A husk's own armour takes a little off each hit; without it each hit is one, and the hits can be counted.
            mob.getAttribute(Attributes.ARMOR).setBaseValue(0.0);
            mobs.add(mob);
        }
        helper.runAfterDelay(STANDING, () -> {
            List<String> problems = new ArrayList<>();
            for (int i = 0; i < pens.size(); i++) {
                Pen pen = pens.get(i);
                LivingEntity mob = mobs.get(i);
                float lost = mob.getMaxHealth() - mob.getHealth();
                if (lost < pen.minLost || lost > pen.maxLost) {
                    problems.add(BuiltInRegistries.ENTITY_TYPE.getKey(pen.type).getPath() + " on "
                            + BuiltInRegistries.BLOCK.getKey(pen.floor).getPath() + " lost " + lost + " health, not "
                            + (pen.minLost == pen.maxLost ? pen.minLost : pen.minLost + " to " + pen.maxLost));
                }
                mob.discard();
            }
            succeedUnless(helper, problems.isEmpty() ? null : String.join("; ", problems));
        });
    }

    /** No zombie or skeleton spawns on silver, waxed or tarnished; one spawns on stone, and a cow spawns on silver. */
    public static void undeadCantSpawnOnSilver(GameTestHelper helper) {
        BlockPos silver = floor(1, 1);
        BlockPos tarnished = floor(3, 1);
        BlockPos stone = floor(5, 1);
        helper.setBlock(silver, ModBlocks.SILVER_BLOCK.get());
        helper.setBlock(tarnished, ModBlocks.WAXED_OXIDIZED_CUT_SILVER.get());
        succeedUnless(helper, spawnsOn(helper, EntityType.ZOMBIE, silver) ? "a zombie can spawn on a Block of Silver"
                : spawnsOn(helper, EntityType.SKELETON, silver) ? "a skeleton can spawn on a Block of Silver"
                : spawnsOn(helper, EntityType.ZOMBIE, tarnished) ? "a zombie can spawn on Waxed Oxidized Cut Silver"
                : !spawnsOn(helper, EntityType.ZOMBIE, stone) ? "The test's setup failed, not the mod: a zombie can't spawn on stone ("
                        + whatIsAt(helper, stone) + ")"
                : !spawnsOn(helper, EntityType.COW, silver) ? "a cow can't spawn on a Block of Silver"
                : null);
    }

    /**
     * A strip of silver across the arena with a way round its end: a husk's route goes round it, a cow's straight across,
     * which shows the strip is on the straight way.
     */
    public static void undeadWalkRoundSilver(GameTestHelper helper) {
        for (int x = 0; x <= 4; x++) {
            helper.setBlock(floor(x, 3), ModBlocks.SILVER_BLOCK.get());
        }
        BlockPos target = helper.absolutePos(floor(1, 5).above());
        Path husk = route(helper, EntityType.HUSK, target);
        Path cow = route(helper, EntityType.COW, target);
        succeedUnless(helper, husk == null || !husk.canReach() || cow == null || !cow.canReach()
                ? "The test's setup failed, not the mod: a husk or a cow found no way across the arena (husk "
                        + describe(helper, husk) + "; cow " + describe(helper, cow) + ")"
                : crossesSilver(helper, husk) ? "a husk walked across silver when it could go round"
                : !crossesSilver(helper, cow) ? "a cow went round silver it has no reason to avoid"
                : null);
    }

    /** Offered a place on silver and one on stone, a husk picks the stone; a cow picks as it would without the mod. */
    public static void undeadNeverPickSilverToGoTo(GameTestHelper helper) {
        BlockPos onSilver = floor(1, 1).above();
        BlockPos onStone = floor(5, 1).above();
        helper.setBlock(onSilver.below(), ModBlocks.SILVER_BLOCK.get());
        PathfinderMob husk = helper.spawn(EntityType.HUSK, floor(3, 4).above());
        PathfinderMob cow = helper.spawn(EntityType.COW, floor(3, 6).above());
        BlockPos[] places = {helper.absolutePos(onSilver), helper.absolutePos(onStone)};
        Vec3 huskPicked = RandomPos.generateRandomPos(husk, offering(places));
        Vec3 cowPicked = RandomPos.generateRandomPos(cow, offering(places));
        Vec3 cowWithoutMod = RandomPos.generateRandomPos(offering(places), cow::getWalkTargetValue);
        husk.discard();
        cow.discard();
        succeedUnless(helper, !Vec3.atBottomCenterOf(places[1]).equals(huskPicked) ? "a husk picked a place on silver to go to"
                : cowPicked == null || !cowPicked.equals(cowWithoutMod) ? "silver changed where a cow goes"
                : null);
    }

    /** Every mob Smite targets is sensitive to silver, and so are Werewolves' werewolves where it's installed; no other is. */
    public static void silverHarmsWhatSmiteDoes(GameTestHelper helper) {
        TagKey<EntityType<?>> werewolves = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("werewolves", "werewolf"));
        List<String> problems = new ArrayList<>();
        for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
            boolean sensitive = type.is(EntityTypeTags.SENSITIVE_TO_SMITE) || type.is(werewolves);
            if (sensitive != type.is(ModTags.EntityTypes.SENSITIVE_TO_SILVER)) {
                problems.add(BuiltInRegistries.ENTITY_TYPE.getKey(type) + (sensitive ? " isn't" : " is"));
            }
        }
        if (problems.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, "thesilverage:sensitive_to_silver differs from minecraft:sensitive_to_smite and the werewolves: " + String.join(", ", problems));
        }
    }

    /** One mob penned on its own floor, and how much health it should have lost by the time it is read. */
    private record Pen(int x, int z, Block floor, EntityType<? extends LivingEntity> type, float minLost, float maxLost) {
    }

    /** The arena's floor at {@code x, z}. */
    private static BlockPos floor(int x, int z) {
        return new BlockPos(x, FLOOR, z);
    }

    /** Glass two blocks high over the whole floor but for nine one-block pens, at odd x and z. */
    private static void pens(GameTestHelper helper) {
        for (int x = 0; x <= 6; x++) {
            for (int z = 0; z <= 6; z++) {
                boolean pen = x % 2 == 1 && z % 2 == 1;
                for (int y = 1; y <= 2; y++) {
                    helper.setBlock(floor(x, z).above(y), pen ? Blocks.AIR : Blocks.GLASS);
                }
            }
        }
    }

    private static boolean spawnsOn(GameTestHelper helper, EntityType<?> type, BlockPos floor) {
        return SpawnPlacements.isSpawnPositionOk(type, helper.getLevel(), helper.absolutePos(floor.above()));
    }

    /** The route a {@code type} standing at the arena's near side plans to {@code target}. */
    private static Path route(GameTestHelper helper, EntityType<? extends Mob> type, BlockPos target) {
        Mob mob = helper.spawn(type, floor(1, 1).above());
        // A walker plans only once it stands on something, which its first tick would find out.
        mob.setOnGround(true);
        Path path = mob.getNavigation().createPath(target, 0);
        mob.discard();
        return path;
    }

    /** A route as a failure message tells it: none, or how long it is, where it ends and whether it gets there. */
    private static String describe(GameTestHelper helper, Path path) {
        if (path == null) {
            return "planned no route";
        }
        // relativePos turns an unrotated test's positions half round, so the end is told from the test's corner instead.
        BlockPos end = path.getNodeCount() == 0 ? null
                : path.getNodePos(path.getNodeCount() - 1).subtract(helper.absolutePos(BlockPos.ZERO));
        return path.getNodeCount() + " steps ending at " + end + (path.canReach() ? "" : ", short of the target");
    }

    /** The blocks a spawn on {@code floor} would stand on and in, for a failure message. */
    private static String whatIsAt(GameTestHelper helper, BlockPos floor) {
        BlockPos at = helper.absolutePos(floor);
        return BuiltInRegistries.BLOCK.getKey(helper.getLevel().getBlockState(at).getBlock()).getPath() + " under "
                + BuiltInRegistries.BLOCK.getKey(helper.getLevel().getBlockState(at.above()).getBlock()).getPath() + " and "
                + BuiltInRegistries.BLOCK.getKey(helper.getLevel().getBlockState(at.above(2)).getBlock()).getPath();
    }

    private static boolean crossesSilver(GameTestHelper helper, Path path) {
        for (int i = 0; i < path.getNodeCount(); i++) {
            if (helper.getLevel().getBlockState(path.getNodePos(i).below()).is(ModTags.Blocks.SILVER_FLOORS)) {
                return true;
            }
        }
        return false;
    }

    /** The places, one after another, as {@link RandomPos} asks for ten. */
    private static Supplier<BlockPos> offering(BlockPos[] places) {
        int[] asked = {0};
        return () -> places[asked[0]++ % places.length];
    }

    private static void succeedUnless(GameTestHelper helper, String problem) {
        if (problem == null) {
            helper.succeed();
        } else {
            fail(helper, "On silver floors, " + problem);
        }
    }
}
