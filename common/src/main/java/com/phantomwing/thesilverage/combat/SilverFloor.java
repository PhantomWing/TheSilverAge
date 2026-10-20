package com.phantomwing.thesilverage.combat;

import com.phantomwing.thesilverage.block.SilverWeatheringSpec;
import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathfindingContext;
import org.jetbrains.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.ToDoubleFunction;

/**
 * Silver floors ({@code thesilverage:silver_floors}) against the mobs silver harms
 * ({@code thesilverage:sensitive_to_silver}), as magma against the mobs fire harms: one standing on silver is hurt,
 * the more slowly the more the silver has tarnished, none spawns on it, and a path across it costs one as much as a
 * path across magma, so it walks round silver where it can and never picks it as somewhere to wander to. Each loader's
 * mixins call in: {@code BlockMixin} as a mob steps, {@code BlockStateBaseMixin} as one spawns,
 * {@code WalkNodeEvaluatorMixin} and {@code RandomPosMixin} as one finds its way. Path types can't carry this, as
 * magma's {@code DAMAGE_FIRE} does: a level works each block's out once for every mob, so silver's would turn villagers
 * and cows away too. All of it is off with {@code enable_silver_floor_damage}.
 */
public final class SilverFloor {
    /** What one hit deals: magma's. */
    private static final float DAMAGE = 1.0F;
    /**
     * Ticks between hits on fresh silver, which is magma's pace once its hurt cooldown has its say; each tarnish stage
     * adds as many again, so oxidized silver hits every two seconds.
     */
    private static final int TICKS_PER_STAGE = 10;
    /** What a path's step onto silver costs on top of what the step costs already: as much as one onto magma. */
    private static final float PATH_COST = 16.0F;
    /** How much worse a place on silver scores as somewhere to go: more than any mob likes a place. */
    private static final double PLACE_PENALTY = 1000.0;

    private SilverFloor() {
    }

    /** {@code entity} stepping on {@code state}: hurt, once its stage's time has come round, where silver harms it. */
    public static void stepOn(Level level, BlockState state, Entity entity) {
        if (level.isClientSide() || !state.is(ModTags.Blocks.SILVER_FLOORS) || !(entity instanceof LivingEntity living)
                || entity.isSteppingCarefully() || !isSensitive(entity.getType()) || !CommonConfig.silverFloorDamage()) {
            return;
        }
        if (living.tickCount % hitInterval(state) == 0) {
            living.hurt(ModDamageTypes.silverFloor(level), DAMAGE);
        }
    }

    /** Whether a {@code type} may spawn on {@code state}: not where it is silver that harms it. */
    public static boolean allowsSpawn(BlockState state, EntityType<?> type) {
        return !state.is(ModTags.Blocks.SILVER_FLOORS) || !isSensitive(type) || !CommonConfig.silverFloorDamage();
    }

    /**
     * A path's step, costing {@link #PATH_COST} more than its kind does where it stands on a silver floor and silver
     * harms {@code mob}. The floor is read through the path's own context, so no chunk is loaded for it. Asked again
     * of a step a path finds twice, so the cost is set rather than added to.
     */
    @Nullable
    public static Node costed(@Nullable Node node, Mob mob, PathfindingContext context) {
        if (node == null || node.costMalus < 0.0F || !isSensitive(mob.getType())) {
            return node;
        }
        float cost = Math.max(0.0F, mob.getPathfindingMalus(node.type)) + PATH_COST;
        if (node.costMalus < cost && context.getBlockState(new BlockPos(node.x, node.y - 1, node.z)).is(ModTags.Blocks.SILVER_FLOORS)
                && CommonConfig.silverFloorDamage()) {
            node.costMalus = cost;
        }
        return node;
    }

    /** {@code scorer}, the mob's own liking for a place to go, with a place on silver scoring worst where silver harms it. */
    public static ToDoubleFunction<BlockPos> scorer(PathfinderMob mob, ToDoubleFunction<BlockPos> scorer) {
        if (!isSensitive(mob.getType()) || !CommonConfig.silverFloorDamage()) {
            return scorer;
        }
        Level level = mob.level();
        return pos -> scorer.applyAsDouble(pos) - (level.getBlockState(pos.below()).is(ModTags.Blocks.SILVER_FLOORS) ? PLACE_PENALTY : 0.0);
    }

    private static boolean isSensitive(EntityType<?> type) {
        return type.is(ModTags.EntityTypes.SENSITIVE_TO_SILVER);
    }

    /** Ticks between hits on {@code state}: magma's pace on fresh silver, half a second more for each tarnish stage. */
    private static int hitInterval(BlockState state) {
        return TICKS_PER_STAGE * (stageOf(state.getBlock()).ordinal() + 1);
    }

    /** How far {@code block} has tarnished: its own stage, its unwaxed twin's when waxed, and fresh for silver of another mod's. */
    private static WeatheringCopper.WeatherState stageOf(Block block) {
        if (block instanceof WeatheringCopper weathering) {
            return weathering.getAge();
        }
        return WaxedStages.OF.getOrDefault(block, WeatheringCopper.WeatherState.UNAFFECTED);
    }

    /** Each waxed silver block's stage, read off its unwaxed twin; built on first use, after the blocks are registered. */
    private static final class WaxedStages {
        private static final Map<Block, WeatheringCopper.WeatherState> OF = build();

        private static Map<Block, WeatheringCopper.WeatherState> build() {
            Map<Block, WeatheringCopper.WeatherState> stages = new IdentityHashMap<>();
            for (SilverWeatheringSpec.Pair pair : SilverWeatheringSpec.waxablePairs()) {
                if (pair.from().get() instanceof WeatheringCopper unwaxed) {
                    stages.put(pair.to().get(), unwaxed.getAge());
                }
            }
            return stages;
        }
    }
}
