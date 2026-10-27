package com.phantomwing.thesilverage.combat;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

/**
 * A mob silver harms, standing on silver with nothing to fight, walks off it, as a skeleton caught in the sun looks for
 * shade. One with a target keeps after it, silver or not, and the goal gives way the moment it has one.
 */
public class LeaveSilverFloorGoal extends Goal {
    /**
     * Above every goal that walks an undead mob: a zombie's attack is 2, a skeleton's sun goals 2 and 3. Those wait on
     * a target or the sun, and this one gives way to a target.
     */
    public static final int PRIORITY = 1;
    /** A hurry, as a panicking horse's. */
    private static final double SPEED = 1.2;
    /** How far a place off silver is looked for, across and up or down: a panicking animal's. */
    private static final int RANGE = 5;
    private static final int HEIGHT = 4;

    private final PathfinderMob mob;
    @Nullable
    private Vec3 safety;

    public LeaveSilverFloorGoal(PathfinderMob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() != null || !SilverFloor.isHurting(mob)) {
            return false;
        }
        safety = findSafety();
        return safety != null;
    }

    @Override
    public void start() {
        if (safety != null) {
            mob.getNavigation().moveTo(safety.x, safety.y, safety.z, SPEED);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return mob.getTarget() == null && !mob.getNavigation().isDone();
    }

    @Override
    public void stop() {
        safety = null;
    }

    /** A place near by off silver, if one is found: the places a mob picks at random already score silver worst for it. */
    @Nullable
    private Vec3 findSafety() {
        Vec3 place = DefaultRandomPos.getPos(mob, RANGE, HEIGHT);
        return place != null && !SilverFloor.isOnSilver(mob.level(), BlockPos.containing(place)) ? place : null;
    }
}
