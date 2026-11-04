package com.phantomwing.thesilverage.combat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;

/**
 * The goals that keep a mob silver harms away from silver while it has nothing to fight: walking off silver underfoot
 * ({@link LeaveSilverFloorGoal}) and keeping away from silver light ({@link AvoidSilverLightGoal}).
 */
public final class SilverUndeadGoals {
    private SilverUndeadGoals() {
    }

    /**
     * Gives a mob silver harms the goals as it joins a server level, once. Each loader calls it from the hook that sees
     * every mob join, those loaded with their chunk too.
     */
    public static void addTo(Entity entity) {
        // Every entity joining a level comes through here, which is how SilverSensitivity learns the undead types.
        if (entity instanceof PathfinderMob mob && !mob.level().isClientSide() && SilverSensitivity.isSensitive(mob)
                && mob.goalSelector.getAvailableGoals().stream().noneMatch(goal -> goal.getGoal() instanceof LeaveSilverFloorGoal)) {
            mob.goalSelector.addGoal(LeaveSilverFloorGoal.PRIORITY, new LeaveSilverFloorGoal(mob));
            mob.goalSelector.addGoal(AvoidSilverLightGoal.PRIORITY, new AvoidSilverLightGoal(mob));
        }
    }
}
