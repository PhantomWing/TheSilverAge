package com.phantomwing.thesilverage.neoforge.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.neoforge.Configuration;
import com.phantomwing.thesilverage.villager.SilverVillagerTrades;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Writes the Silver villager_trade JSON (gated by enable_villager_trades) plus an additive
 * cleric level-2 pool tag entry. Encodes the trade via its CODEC, then injects the
 * neoforge:conditions gate; FabricConditionsProvider mirrors it to fabric:load_conditions.
 */
public class ModVillagerTradeProvider implements DataProvider {
    /** {@code data/thesilverage/villager_trade/cleric/2/silver_ingot_emerald.json} */
    private static final String TRADE_PATH = "thesilverage/villager_trade/cleric/2/silver_ingot_emerald.json";
    private static final String TRADE_ID = "thesilverage:cleric/2/silver_ingot_emerald";
    /** Vanilla cleric L2 trade pool — additive. */
    private static final String TAG_PATH = "minecraft/tags/villager_trade/cleric/level_2.json";

    private final PackOutput packOutput;
    private final CompletableFuture<HolderLookup.Provider> registries;

    public ModVillagerTradeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        this.packOutput = packOutput;
        this.registries = registries;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        return registries.thenCompose(provider -> {
            Path data = packOutput.getOutputFolder(PackOutput.Target.DATA_PACK);
            RegistryOps<JsonElement> ops = provider.createSerializationContext(JsonOps.INSTANCE);
            List<CompletableFuture<?>> futures = new ArrayList<>();

            // Cleric L2: buy 3 Silver Ingot, sell 1 Emerald.
            VillagerTrade trade = new VillagerTrade(
                    new TradeCost(ModItems.SILVER_INGOT.get(), 3),   // wants
                    new ItemStackTemplate(Items.EMERALD),            // gives
                    12, 10, SilverVillagerTrades.PRICE_MULTIPLIER,
                    Optional.empty(), List.of());
            JsonObject tradeJson = VillagerTrade.CODEC.encodeStart(ops, trade).getOrThrow().getAsJsonObject();
            tradeJson.add("neoforge:conditions", configCondition(Configuration.ENABLE_VILLAGER_TRADES_ID));
            futures.add(DataProvider.saveStable(cache, tradeJson, data.resolve(TRADE_PATH)));

            futures.add(DataProvider.saveStable(cache, clericPoolTag(), data.resolve(TAG_PATH)));

            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        });
    }

    /** {@code thesilverage:config_boolean} condition array gating on the given setting. */
    private static JsonArray configCondition(String settingId) {
        JsonObject cond = new JsonObject();
        cond.addProperty("type", "thesilverage:config_boolean");
        cond.addProperty("settingId", settingId);
        JsonArray arr = new JsonArray();
        arr.add(cond);
        return arr;
    }

    /** Additive cleric L2 pool tag adding our trade as an optional entry. */
    private static JsonObject clericPoolTag() {
        JsonObject entry = new JsonObject();
        entry.addProperty("id", TRADE_ID);
        entry.addProperty("required", false);
        JsonArray values = new JsonArray();
        values.add(entry);
        JsonObject tag = new JsonObject();
        tag.add("values", values);
        return tag;
    }

    @Override
    public @NotNull String getName() {
        return "The Silver Age Villager Trades";
    }
}
