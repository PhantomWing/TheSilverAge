package com.phantomwing.thesilverage.neoforge.villager;

import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.villager.SilverVillagerTrades;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;

/**
 * NeoForge trade registrant. The trade content is defined once in the shared
 * {@link SilverVillagerTrades} (mirroring the loot "shared spec, per-loader
 * apply" model); this class only applies it through the NeoForge village
 * events, re-checking the config every rebuild. The Fabric twin lives at
 * {@code com.phantomwing.thesilverage.fabric.villager.ModVillagerTrades}.
 */
@EventBusSubscriber(modid = TheSilverAge.MOD_ID)
public class ModVillagerTrades {
    /** Kept for source compatibility; the value is owned by the shared spec. */
    public static final float PRICE_MULTIPLIER = SilverVillagerTrades.PRICE_MULTIPLIER;

    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event) {
        // Check if trades are enabled.
        if (!Configuration.ENABLE_VILLAGER_TRADES.get()) {
            return;
        }

        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

        if (event.getType() == VillagerProfession.CLERIC) {
            trades.get(2).add(SilverVillagerTrades.clericSilverIngotForEmerald());
        }
        else if (event.getType() == VillagerProfession.LIBRARIAN) {
            // TODO: Add trade for moon clock (define it in the shared
            //       SilverVillagerTrades, then add it here + on Fabric).
        }
    }

    @SubscribeEvent
    public static void addWanderingTraderTrades(WandererTradesEvent event) {
        // Check if trades are enabled.
        if (!Configuration.ENABLE_WANDERING_TRADER_TRADES.get()) {
            return;
        }

        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();

        // TODO: Add trades for wandering trader (e.g. Silver Horse Armor for 24
        //       emeralds). Define it in the shared SilverVillagerTrades, then
        //       add it here and via TradeOfferHelper on the Fabric side.
    }
}
