package com.phantomwing.thesilverage.combat;

import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

/**
 * A mob silver harms, with nothing to fight, keeps away from silver light: the blocks in
 * {@code thesilverage:undead_repellents}, the Silver Torch and Lantern. It walks off from one near by as a creeper
 * walks off from a cat, and gives way the moment it has a target, as it does on silver floors: a deterrent for
 * wandering undead, not a shield. The Lead Age's lead torches turn creepers and pillagers away the same way
 * ({@code AvoidRepellentBlockGoal} there), and this is that goal with silver's rule.
 *
 * <p>Vanilla's {@code #minecraft:piglin_repellents} is read only by the piglin brain, so goal-driven undead need the
 * avoidance written out. Only the block search is throttled ({@link #SCAN_INTERVAL}), and it starts from the chunk
 * sections' palettes, which for most of the world say no silver light can be in them. The flee is tried at every goal
 * tick: {@link DefaultRandomPos#getPosAway} misses often by design, and a miss waiting on the next search left mobs
 * standing beside the light.</p>
 *
 * <p>{@code canUse()} is polled every other tick ({@code Mob.serverAiStep} alternates {@code goalSelector.tick()} with
 * {@code tickRunningGoals()}), so intervals here are in goal ticks, roughly double in game ticks.</p>
 */
public class AvoidSilverLightGoal extends Goal {
    /**
     * Below leaving silver underfoot (1) and a skeleton fleeing the sun (3), so a burning mob finds shade first;
     * above everything else that walks an idle undead mob, a zombie's village wander at 6 and every stroll.
     */
    public static final int PRIORITY = 4;
    /** How far silver light keeps undead away, across, and up or down. */
    private static final int RANGE = 6;
    private static final int HEIGHT = 4;
    /** Goal ticks between full searches while no silver light is known near by. */
    private static final int SCAN_INTERVAL = 5;
    /** How far a place away from the light is looked for: vanilla's AvoidEntityGoal's 16 and 7. */
    private static final int FLEE_RANGE = 16;
    private static final int FLEE_HEIGHT = 7;
    /** Within this distance (squared) of the light the mob hurries, then walks once clear, as from a cat. */
    private static final double HURRY_WITHIN_SQR = 16.0;
    private static final double WALK_SPEED = 1.0;
    private static final double HURRY_SPEED = 1.2;
    /** Goal ticks before another path is planned after one that couldn't get away: a mob penned in kept planning. */
    private static final int STUCK_INTERVAL = 10;

    private final PathfinderMob mob;
    /** The light being kept away from, kept between tries so a retry costs no search. */
    @Nullable
    private BlockPos light;
    @Nullable
    private Path fleePath;
    private int scanCooldown;
    private int stuckCooldown;

    public AvoidSilverLightGoal(PathfinderMob mob) {
        this.mob = mob;
        // Staggered, so a crowd of mobs doesn't search on the same tick.
        this.scanCooldown = mob.getRandom().nextInt(SCAN_INTERVAL);
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // Before the search, so the ward costs nothing while off or while the mob is fighting.
        if (mob.getTarget() != null || !CommonConfig.undeadAvoidSilverLight() || !lightNearby()) {
            return false;
        }
        if (stuckCooldown > 0) {
            stuckCooldown--;
            return false;
        }
        Vec3 threat = Vec3.atBottomCenterOf(light);
        Vec3 escape = DefaultRandomPos.getPosAway(mob, FLEE_RANGE, FLEE_HEIGHT, threat);
        // Somewhere no further from the light than the mob already is is no escape.
        if (escape == null || threat.distanceToSqr(escape) < threat.distanceToSqr(mob.position())) {
            return false;
        }
        // A path that stops short is a wall in the way, not an escape.
        fleePath = mob.getNavigation().createPath(escape.x, escape.y, escape.z, 0);
        if (fleePath == null || !fleePath.canReach()) {
            fleePath = null;
            stuckCooldown = STUCK_INTERVAL;
            return false;
        }
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        return mob.getTarget() == null && CommonConfig.undeadAvoidSilverLight() && !mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        mob.getNavigation().moveTo(fleePath, WALK_SPEED);
    }

    @Override
    public void stop() {
        // The light is kept: a flee cut short beside it tries again at once rather than after a search.
        fleePath = null;
    }

    @Override
    public void tick() {
        if (light != null) {
            boolean close = mob.distanceToSqr(Vec3.atCenterOf(light)) < HURRY_WITHIN_SQR;
            mob.getNavigation().setSpeedModifier(close ? HURRY_SPEED : WALK_SPEED);
        }
    }

    /** The silver light being kept away from, or null while none is known near by. */
    @Nullable
    public BlockPos light() {
        return light;
    }

    /** Whether silver light is in range: the known one checked again cheaply, the full search behind it throttled. */
    private boolean lightNearby() {
        if (light != null && light.closerToCenterThan(mob.position(), RANGE) && isLight(light)) {
            return true;
        }
        light = null;
        if (scanCooldown-- > 0) {
            return false;
        }
        scanCooldown = SCAN_INTERVAL;
        if (mayBeNearby()) {
            light = BlockPos.findClosestMatch(mob.blockPosition(), RANGE, HEIGHT, this::isLight).orElse(null);
        }
        return light != null;
    }

    /** Whether a chunk section the search reaches may hold silver light, by its palette: a few looks for a mob nowhere near one. */
    private boolean mayBeNearby() {
        BlockPos at = mob.blockPosition();
        Level level = mob.level();
        for (int x = SectionPos.blockToSectionCoord(at.getX() - RANGE); x <= SectionPos.blockToSectionCoord(at.getX() + RANGE); x++) {
            for (int z = SectionPos.blockToSectionCoord(at.getZ() - RANGE); z <= SectionPos.blockToSectionCoord(at.getZ() + RANGE); z++) {
                ChunkAccess chunk = level.getChunk(x, z, ChunkStatus.FULL, false);
                if (chunk == null) {
                    continue;
                }
                for (int y = SectionPos.blockToSectionCoord(at.getY() - HEIGHT); y <= SectionPos.blockToSectionCoord(at.getY() + HEIGHT); y++) {
                    int index = chunk.getSectionIndexFromSectionY(y);
                    if (index < 0 || index >= chunk.getSectionsCount()) {
                        continue;
                    }
                    LevelChunkSection section = chunk.getSection(index);
                    if (!section.hasOnlyAir() && section.maybeHas(state -> state.is(ModTags.Blocks.UNDEAD_REPELLENTS))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isLight(BlockPos pos) {
        return mob.level().getBlockState(pos).is(ModTags.Blocks.UNDEAD_REPELLENTS);
    }
}
