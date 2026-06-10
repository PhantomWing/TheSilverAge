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
 * Datagen for the Silver villager trade. 26.1 moved villager trades to the data-driven
 * {@code villager_trade} registry (+ {@code tags/villager_trade/<profession>/level_N} pools) and
 * deleted NeoForge's trade-registration events, so this is now pure data — loader-agnostic (the
 * generated files ship in the common jar and apply on both Fabric and NeoForge).
 *
 * <p>There is no dedicated vanilla/NeoForge "villager trade" datagen provider, and the standard
 * datapack-registry path ({@code DatapackBuiltinEntriesProvider} + {@code RegistrySetBuilder})
 * cannot attach load-conditions to an entry. So this small {@link DataProvider} writes the trade
 * itself — encoding the {@link VillagerTrade} through its own {@code CODEC} (schema-correct by
 * construction) — then injects the {@code neoforge:conditions} gate. {@link FabricConditionsProvider}
 * runs after this and mirrors that into {@code fabric:load_conditions}, so the
 * {@code enable_villager_trades} config gates the trade identically on both loaders.</p>
 *
 * <p>It also emits the cleric level-2 pool tag entry (additive merge with vanilla; {@code required:
 * false} so the pool tolerates the trade being condition-removed when the config gates it out).</p>
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

            // Cleric, profession level 2: buy 3 Silver Ingot, sell 1 Emerald (maxUses 12, villagerXp 10,
            // reputation_discount = PRICE_MULTIPLIER). The only active Silver villager trade.
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
