package com.phantomwing.thesilverage.villager;

import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

/**
 * Loader-agnostic source of truth for the Silver villager trades.
 *
 * <p>Mirrors the "shared spec, per-loader apply" model already used for loot:
 * the trade content (items, counts, uses, xp, price multiplier) lives here once,
 * and each loader merely registers it through its own API — NeoForge from
 * {@code VillagerTradesEvent} / {@code WandererTradesEvent}, Fabric via
 * {@code TradeOfferHelper}. Keeping the {@link MerchantOffer} construction in a
 * single place guarantees the two loaders stay byte-identical.</p>
 *
 * <p><b>Config gating is intentionally NOT done here</b> — it is applied by each
 * loader's registrant at its idiomatic point: NeoForge early-returns from the
 * trade event when the config is off (re-checked every rebuild); Fabric wraps
 * the listing so it returns {@code null} (= no offer, which vanilla skips) while
 * the config is off, because {@code TradeOfferHelper} registers only once at
 * mod-init and must still honour live config toggles.</p>
 */
public final class SilverVillagerTrades {
    /**
     * Carried over verbatim from the original single-loader build so both
     * loaders price the trade identically.
     */
    public static final float PRICE_MULTIPLIER = 0.05f;

    private SilverVillagerTrades() {
    }

    /**
     * Cleric, profession level 2: buy 3 Silver Ingot, sell 1 Emerald
     * (maxUses 12, villagerXp 10). The only active Silver villager trade.
     */
    public static VillagerTrades.ItemListing clericSilverIngotForEmerald() {
        return (level, trader, random) -> new MerchantOffer(
                new ItemCost(ModItems.SILVER_INGOT.get(), 3),
                new ItemStack(Items.EMERALD, 1),
                12,
                10,
                PRICE_MULTIPLIER
        );
    }
}
