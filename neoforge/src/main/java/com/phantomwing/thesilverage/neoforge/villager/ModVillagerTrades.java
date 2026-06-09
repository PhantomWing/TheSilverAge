package com.phantomwing.thesilverage.neoforge.villager;

import com.phantomwing.thesilverage.villager.SilverVillagerTrades;

/**
 * NeoForge trade registrant.
 *
 * <p><b>26.1: now DATA-DRIVEN — this class is vestigial.</b> 26.1 moved villager trades to a
 * datapack {@code villager_trade} registry and REMOVED NeoForge's {@code VillagerTradesEvent} /
 * {@code WandererTradesEvent}. The Silver cleric trade is now defined as pure data (see
 * {@link SilverVillagerTrades} for the file layout) — loader-agnostic, so there is no NeoForge
 * registration code anymore. Kept only to hold {@code PRICE_MULTIPLIER} for reference.</p>
 */
public final class ModVillagerTrades {
    /** Kept for source compatibility; the value is owned by the shared spec. */
    public static final float PRICE_MULTIPLIER = SilverVillagerTrades.PRICE_MULTIPLIER;

    private ModVillagerTrades() {
    }
}
