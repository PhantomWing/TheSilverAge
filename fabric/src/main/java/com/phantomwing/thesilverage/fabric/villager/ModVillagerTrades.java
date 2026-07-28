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
        VillagerTrades.ItemListing clericTrade = SilverVillagerTrades.clericSilverIngotForEmerald();

        // Cleric, profession level 2 — parity with the NeoForge
        // VillagerProfession.CLERIC / trades.get(2) branch.
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 2, (factories, rebalanced) -> {
            // Checked INSIDE the callback: TradeOfferHelper only sets up its trade maps
            // when registerVillagerOffers is first called, so testing beforehand would
            // read an uninitialised state and wrongly report the pools as distinct.
            if (rebalanced && sharesRebalancedPool(VillagerProfession.CLERIC)) {
                return;
            }
            factories.add((trader, random) ->
                    TheSilverAgeFabricConfig.getBooleanConfigurationValue(
                            TheSilverAgeFabricConfig.ENABLE_VILLAGER_TRADES_ID)
                            ? clericTrade.getOffer(trader, random)
                            : null);
        });

        // NOTE for the wandering-trader trade below: if it is ever enabled, use the same
        // sharesRebalancedPool guard. Vanilla DOES rebalance the wandering trader, so its
        // two pools are separate objects and the listing must be added on both passes —
        // an unconditional `if (rebalanced) return;` would drop it in rebalanced worlds.

        // Wandering trader: parity with NeoForge ModVillagerTrades —
        // addWanderingTraderTrades is gated by ENABLE_WANDERING_TRADER_TRADES
        // but currently registers no offers (the Silver Horse Armor trade is a
        // commented-out TODO upstream). Nothing to register here until that
        // trade is enabled on NeoForge; add the matching
        // TradeOfferHelper.registerWanderingTraderOffers(...) call in lockstep
        // when it is.
    }

    /**
     * True when the profession's normal and rebalanced trade pools are the same object.
     *
     * <p>On this MC version the trade maps are keyed by {@link VillagerProfession} itself;
     * later versions key them by {@code ResourceKey<VillagerProfession>}.</p>
     */
    private static boolean sharesRebalancedPool(VillagerProfession profession) {
        var normal = VillagerTrades.TRADES.get(profession);
        var rebalanced = VillagerTrades.EXPERIMENTAL_TRADES.get(profession);
        // A null rebalanced entry means Fabric will create a fresh map for it, so the
        // two are distinct and the listing must be added on both passes.
        return normal != null && normal == rebalanced;
    }
}
