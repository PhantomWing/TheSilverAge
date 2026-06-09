package com.phantomwing.thesilverage.fabric.villager;

import com.phantomwing.thesilverage.villager.SilverVillagerTrades;

/**
 * Fabric parity for the NeoForge {@code ModVillagerTrades}.
 *
 * <p><b>26.1: now DATA-DRIVEN — this class is vestigial.</b> Villager trades moved to a datapack
 * {@code villager_trade} registry (see {@link SilverVillagerTrades} for the file layout). The
 * Silver cleric trade is now pure data shared with NeoForge — no Fabric registration code.
 * {@link #register()} is kept as a no-op so the mod-init call site needs no change.</p>
 */
public final class ModVillagerTrades {
    private ModVillagerTrades() {
    }

    /** No-op on 26.1 (villager trades temporarily disabled — see class doc + SilverVillagerTrades). */
    public static void register() {
    }
}
