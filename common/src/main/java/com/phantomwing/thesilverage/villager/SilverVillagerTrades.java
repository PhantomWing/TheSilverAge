package com.phantomwing.thesilverage.villager;

/**
 * Loader-agnostic source of truth for the Silver villager trades.
 *
 * <p><b>26.1: now DATA-DRIVEN.</b> 26.1 rewrote villager trades from code-defined
 * {@code VillagerTrades.ItemListing} interfaces to a datapack {@code villager_trade} registry
 * (+ {@code tags/villager_trade/<profession>/level_N} tags), and NeoForge removed its
 * {@code VillagerTradesEvent}/{@code WandererTradesEvent}. The Silver cleric trade (Cleric L2:
 * 3 Silver Ingot &rarr; 1 Emerald, maxUses 12, xp 10, reputation_discount 0.05) is therefore
 * defined as pure data — loader-agnostic, no per-loader code:</p>
 * <ul>
 *   <li>{@code data/thesilverage/villager_trade/cleric/2/silver_ingot_emerald.json} — the trade,
 *       gated behind {@code enable_villager_trades} via {@code neoforge:conditions} +
 *       {@code fabric:load_conditions} (config parity on both loaders).</li>
 *   <li>{@code data/minecraft/tags/villager_trade/cleric/level_2.json} — adds the trade to the
 *       cleric L2 pool (a {@code required:false} entry, so it's skipped when the config gates it out).</li>
 * </ul>
 *
 * <p>This class + the per-loader {@code ModVillagerTrades} are now vestigial no-ops (kept only so
 * their setup call sites need no change); {@code PRICE_MULTIPLIER} is mirrored by the data file's
 * {@code reputation_discount}.</p>
 */
public final class SilverVillagerTrades {
    /** Mirrors the data file's {@code reputation_discount} (Cleric L2 price multiplier). */
    public static final float PRICE_MULTIPLIER = 0.05f;

    private SilverVillagerTrades() {
    }
}
