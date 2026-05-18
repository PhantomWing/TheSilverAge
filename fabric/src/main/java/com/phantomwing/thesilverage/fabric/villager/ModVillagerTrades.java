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
 */
public final class ModVillagerTrades {
    private ModVillagerTrades() {
    }

    public static void register() {
        VillagerTrades.ItemListing clericTrade = SilverVillagerTrades.clericSilverIngotForEmerald();

        // Cleric, profession level 2 — parity with the NeoForge
        // VillagerProfession.CLERIC / trades.get(2) branch.
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 2, factories ->
                factories.add((trader, random) ->
                        TheSilverAgeFabricConfig.getBooleanConfigurationValue(
                                TheSilverAgeFabricConfig.ENABLE_VILLAGER_TRADES_ID)
                                ? clericTrade.getOffer(trader, random)
                                : null));

        // Wandering trader: parity with NeoForge ModVillagerTrades —
        // addWanderingTraderTrades is gated by ENABLE_WANDERING_TRADER_TRADES
        // but currently registers no offers (the Silver Horse Armor trade is a
        // commented-out TODO upstream). Nothing to register here until that
        // trade is enabled on NeoForge; add the matching
        // TradeOfferHelper.registerWanderingTraderOffers(...) call in lockstep
        // when it is.
    }
}
