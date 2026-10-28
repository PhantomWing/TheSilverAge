package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.function.Predicate;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/**
 * Game tests for the villager trades: the Cleric buys Silver Ingots and the master Fletcher sells Silver Arrows, each
 * once, read from the trade lists both modloaders fill. Listed in {@link GameTests}.
 */
public final class SilverTradesGameTest {
    private SilverTradesGameTest() {
    }

    /** One of the Cleric's trades buys Silver Ingots: Fabric registered it twice, and a Cleric could offer it twice. */
    public static void clericsBuySilverIngotsOnce(GameTestHelper helper) {
        long trades = count(helper, VillagerProfession.CLERIC, 0, offer -> offer.getCostA().is(ModItems.SILVER_INGOT.get()));
        if (trades == 1) {
            helper.succeed();
        } else {
            fail(helper, trades + " of the Cleric's trades buy Silver Ingots, not 1");
        }
    }

    /** One of the master Fletcher's trades sells Silver Arrows, and none does with enable_silver_arrows off. */
    public static void masterFletchersSellSilverArrows(GameTestHelper helper) {
        Predicate<MerchantOffer> sellsArrows = offer -> offer.getResult().is(ModItems.SILVER_ARROW.get());
        long on = count(helper, VillagerProfession.FLETCHER, 5, sellsArrows);
        long off = TestSettings.with("enable_silver_arrows", false, () -> count(helper, VillagerProfession.FLETCHER, 5, sellsArrows));
        if (on == 1 && off == 0) {
            helper.succeed();
        } else {
            fail(helper, on + " of the master Fletcher's trades sell Silver Arrows, not 1, and " + off
                    + " with enable_silver_arrows off, not 0");
        }
    }

    /** How many of {@code profession}'s trades at {@code level}, or at every level for 0, give an offer that matches. */
    private static long count(GameTestHelper helper, VillagerProfession profession, int level, Predicate<MerchantOffer> matches) {
        Villager villager = EntityType.VILLAGER.create(helper.getLevel());
        RandomSource random = RandomSource.create(0);
        long found = 0;
        for (var entry : VillagerTrades.TRADES.get(profession).int2ObjectEntrySet()) {
            if (level != 0 && entry.getIntKey() != level) {
                continue;
            }
            for (VillagerTrades.ItemListing listing : entry.getValue()) {
                MerchantOffer offer = listing.getOffer(villager, random);
                if (offer != null && matches.test(offer)) {
                    found++;
                }
            }
        }
        villager.discard();
        return found;
    }
}
