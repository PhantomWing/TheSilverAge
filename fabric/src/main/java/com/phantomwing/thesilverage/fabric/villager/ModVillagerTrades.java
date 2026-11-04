package com.phantomwing.thesilverage.fabric.villager;

import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;
import com.phantomwing.thesilverage.villager.SilverVillagerTrades;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;

/**
 * Fabric parity for the NeoForge {@code ModVillagerTrades} village-event
 * handlers.
 *
 * <p>NeoForge adds the Silver trade from {@code VillagerTradesEvent} (re-fired
 * whenever a villager's trades are (re)built, so its
 * {@code Configuration.ENABLE_VILLAGER_TRADES} check is live). Fabric's
 * {@link TradeOfferHelper} instead registers a factory <b>once</b> at mod-init,
 * so the config gate is pushed <em>into</em> the listing: when the option is
 * off the factory returns {@code null}, which vanilla's trade assembly skips —
 * giving the same observable, live-toggleable behaviour as NeoForge.</p>
 *
 * <p>The trade content itself comes from the shared
 * {@link SilverVillagerTrades} so both loaders stay byte-identical.</p>
 *
 * <p><b>Why the two-argument adder.</b> The {@code Consumer}-based overload of
 * {@code registerVillagerOffers} documents that it "adds the same trade offers to
 * current and rebalanced trades" — it runs the callback twice, and both passes land
 * in {@link VillagerTrades#TRADES}. That left two copies of the listing in the
 * cleric's pool, so one villager could roll the silver trade twice. The
 * {@code VillagerOffersAdder} overload exposes the {@code rebalanced} flag, so the
 * listing is added on the normal pass only and appears exactly once.</p>
 */
public final class ModVillagerTrades {
    private ModVillagerTrades() {
    }

    public static void register() {
        // Cleric, profession level 2 — parity with the NeoForge
        // VillagerProfession.CLERIC / trades.get(2) branch.
        registerOffer(VillagerProfession.CLERIC, 2, SilverVillagerTrades.clericSilverIngotForEmerald());
        // Fletcher, profession level 5 — the NeoForge FLETCHER / trades.get(5) branch.
        registerOffer(VillagerProfession.FLETCHER, 5, SilverVillagerTrades.fletcherSilverArrows());

        // Wandering trader: parity with NeoForge ModVillagerTrades —
        // addWanderingTraderTrades is gated by ENABLE_WANDERING_TRADER_TRADES
        // but currently registers no offers (the Silver Horse Armor trade is a
        // commented-out TODO upstream). Nothing to register here until that
        // trade is enabled on NeoForge; add the matching
        // TradeOfferHelper.registerWanderingTraderOffers(...) call in lockstep
        // when it is.
    }

    // Fabric API 0.92's offers take no rebalanced flag: 1.20.1 has no rebalanced trades.
    private static void registerOffer(VillagerProfession profession, int level, VillagerTrades.ItemListing trade) {
        TradeOfferHelper.registerVillagerOffers(profession, level, factories ->
                factories.add((trader, random) ->
                        TheSilverAgeFabricConfig.getBooleanConfigurationValue(
                                TheSilverAgeFabricConfig.ENABLE_VILLAGER_TRADES_ID)
                                ? trade.getOffer(trader, random)
                                : null));
    }
}
