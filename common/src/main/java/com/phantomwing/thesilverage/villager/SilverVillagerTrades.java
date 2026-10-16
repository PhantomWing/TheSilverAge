package com.phantomwing.thesilverage.villager;

/**
 * The Silver villager trade (Cleric L2) is data-driven (datapack {@code villager_trade} registry).
 * This class is a vestigial no-op kept only so its setup call sites need no change;
 * {@code PRICE_MULTIPLIER} mirrors the data file's {@code reputation_discount}.
 */
public final class SilverVillagerTrades {
    /** Mirrors the data file's {@code reputation_discount} (Cleric L2 price multiplier). */
    public static final float PRICE_MULTIPLIER = 0.05f;

    private SilverVillagerTrades() {
    }
}
