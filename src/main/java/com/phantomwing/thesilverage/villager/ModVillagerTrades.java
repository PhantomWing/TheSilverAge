package com.phantomwing.thesilverage.villager;

import com.phantomwing.thesilverage.Configuration;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.item.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = TheSilverAge.MOD_ID)
public class ModVillagerTrades {
    public static float PRICE_MULTIPLIER = 0.05f;

    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event) {
        // Check if trades are enabled.
        if (!Configuration.ENABLE_VILLAGER_TRADES.get()) {
            return;
        }

        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

        if (event.getType() == VillagerProfession.CLERIC) {
            trades.get(2).add((trader, random) -> new MerchantOffer(
                    new ItemCost(ModItems.SILVER_INGOT.get(), 3),
                    new ItemStack(Items.EMERALD, 1),
                    12,
                    10,
                    PRICE_MULTIPLIER
            ));
        }
        else if (event.getType() == VillagerProfession.LIBRARIAN) {
            // TODO: Add trade for moon clock
            /* trades.get(4).add((trader, random) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 5),
                    new ItemStack(ModItems.MOON_CLOCK, 1),
                    12,
                    15,
                    PRICE_MULTIPLIER
            )); */
        }
    }

    @SubscribeEvent
    public static void addWanderingTraderTrades(WandererTradesEvent event) {
        // Check if trades are enabled.
        if (!Configuration.ENABLE_WANDERING_TRADER_TRADES.get()) {
            return;
        }

        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();

        // TODO: Add trades for wandering trader

        /* genericTrades.add((trader, random) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 24),
                new ItemStack(ModItems.SILVER_HORSE_ARMOR.get(), 1),
                1,
                2,
                PRICE_MULTIPLIER
        )); */
    }
}
