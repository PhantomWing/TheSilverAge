package com.phantomwing.thesilverage.neoforge.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Cross-loader condition bridge for the single shared generated tree.
 *
 * <p>Background: this project keeps one shared generated data tree
 * ({@code common/src/generated/resources}) authored by the NeoForge
 * {@code GatherDataEvent} pipeline (the zero-diff single-source model, Phase 2).
 * Conditional recipes / recipe-advancements (Create-compat recipes, the
 * {@code crushed_raw_silver} smelt + its advancement, and the
 * {@code OVERRIDE_VANILLA_RECIPES} conditional/fallback pairs) are gated with
 * NeoForge-only JSON:</p>
 *
 * <pre>"neoforge:conditions": [ { "type": "...", ... } ]</pre>
 *
 * <p>NeoForge honours that and skips the recipe when the condition is unmet.
 * Fabric does not parse {@code neoforge:conditions}, so on Fabric these recipes
 * would load unconditionally and reference {@code create:*} items that don't
 * exist on a Create-less Fabric runtime — spamming load errors.</p>
 *
 * <p>This provider runs <strong>last</strong> in {@code DataGenerators} (NeoForge's
 * {@code DataGenerator.run()} executes providers sequentially in registration
 * order, each {@code .join()}-ed before the next — verified against
 * {@code net.minecraft.data.DataGenerator}). By the time it runs, every recipe /
 * advancement / Create-compat JSON has been written to disk. It walks the output
 * tree and, for every JSON carrying a top-level {@code "neoforge:conditions"}
 * array, additionally writes a translated {@code "fabric:load_conditions"} array
 * (Fabric Resource Conditions API v1) into the same file. NeoForge keeps reading
 * {@code neoforge:conditions} and ignores {@code fabric:load_conditions}; Fabric
 * does the reverse. One file, both dialects, identical gating on both loaders.</p>
 *
 * <p>The rewrite goes back through {@link DataProvider#saveStable} — the exact
 * serializer the recipe/advancement providers used — so the file stays
 * byte-stable across reruns (same key comparator, indent, no trailing newline)
 * and the operation is idempotent (the translation is recomputed from
 * {@code neoforge:conditions} every run and overwrites in place).</p>
 *
 * <p>Translation table (each NeoForge condition type currently emitted into the
 * shared tree → its Fabric equivalent):</p>
 * <ul>
 *   <li>{@code neoforge:mod_loaded {modid:"X"}} →
 *       {@code fabric:all_mods_loaded {values:["X"]}} (built into fabric-api).</li>
 *   <li>{@code neoforge:not {value:{...}}} →
 *       {@code fabric:not {value:{...}}} (inner translated recursively;
 *       built into fabric-api).</li>
 *   <li>{@code thesilverage:config_boolean {settingId:"X"}} →
 *       {@code thesilverage:config_boolean {settingId:"X"}} (same id + field;
 *       only the dispatch key changes from {@code "type"} to {@code "condition"}.
 *       Runtime handler:
 *       {@code com.phantomwing.thesilverage.fabric.condition.ConfigBooleanResourceCondition},
 *       registered in {@code TheSilverAgeFabric}).</li>
 * </ul>
 *
 * <p>If an unrecognised NeoForge condition type ever appears, this provider
 * fails the build (rather than silently emitting an under-gated Fabric recipe)
 * so the parity contract can't drift unnoticed.</p>
 */
public class FabricConditionsProvider implements DataProvider {
    /** NeoForge's top-level conditions key (see {@code ConditionalOps.DEFAULT_CONDITIONS_KEY}). */
    private static final String NEOFORGE_KEY = "neoforge:conditions";
    /** Fabric's top-level conditions key (see {@code ResourceConditions.CONDITIONS_KEY}). */
    private static final String FABRIC_KEY = "fabric:load_conditions";
    /** Fabric dispatch key inside each condition object (see {@code ResourceCondition.CODEC}). */
    private static final String FABRIC_CONDITION = "condition";
    /** NeoForge dispatch key inside each condition object. */
    private static final String NEOFORGE_TYPE = "type";

    private final PackOutput packOutput;

    public FabricConditionsProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        Path root = packOutput.getOutputFolder();
        List<Path> jsonFiles = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(root)) {
            walk.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".json"))
                    .forEach(jsonFiles::add);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(e);
        }

        List<CompletableFuture<?>> futures = new ArrayList<>();
        for (Path path : jsonFiles) {
            JsonObject json;
            try {
                String content = Files.readString(path, StandardCharsets.UTF_8);
                JsonElement parsed = JsonParser.parseString(content);
                if (!parsed.isJsonObject()) {
                    continue;
                }
                json = parsed.getAsJsonObject();
            } catch (IOException e) {
                return CompletableFuture.failedFuture(e);
            }

            if (!json.has(NEOFORGE_KEY) || !json.get(NEOFORGE_KEY).isJsonArray()) {
                continue;
            }

            JsonArray neoforgeConditions = json.getAsJsonArray(NEOFORGE_KEY);
            JsonArray fabricConditions = new JsonArray();
            for (JsonElement element : neoforgeConditions) {
                fabricConditions.add(translate(element.getAsJsonObject(), path));
            }

            // Recompute + overwrite every run (idempotent, deterministic).
            json.add(FABRIC_KEY, fabricConditions);

            // Re-serialize through the same path the recipe/advancement
            // providers used: applies KEY_COMPARATOR ordering + the datagen
            // indent and writes no trailing newline, so the file is byte-stable.
            futures.add(DataProvider.saveStable(cache, json, path));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    /**
     * Translate a single NeoForge condition object into its Fabric equivalent.
     * Recursive for {@code neoforge:not} (its {@code value} is itself a
     * condition). Unknown types fail the build to protect the parity contract.
     */
    private static JsonObject translate(JsonObject neoforge, Path path) {
        String type = neoforge.get(NEOFORGE_TYPE).getAsString();
        JsonObject fabric = new JsonObject();

        switch (type) {
            case "neoforge:mod_loaded" -> {
                fabric.addProperty(FABRIC_CONDITION, "fabric:all_mods_loaded");
                JsonArray values = new JsonArray();
                values.add(neoforge.get("modid").getAsString());
                fabric.add("values", values);
            }
            case "neoforge:not" -> {
                fabric.addProperty(FABRIC_CONDITION, "fabric:not");
                fabric.add("value", translate(neoforge.getAsJsonObject("value"), path));
            }
            case "thesilverage:config_boolean" -> {
                // Same condition id + field; only the dispatch key differs
                // (NeoForge "type" -> Fabric "condition").
                fabric.addProperty(FABRIC_CONDITION, "thesilverage:config_boolean");
                fabric.addProperty("settingId", neoforge.get("settingId").getAsString());
            }
            default -> throw new IllegalStateException(
                    "FabricConditionsProvider: unmapped NeoForge condition type '" + type
                            + "' in " + path + ". Add a translation to keep loader parity.");
        }

        return fabric;
    }

    @Override
    public @NotNull String getName() {
        return "The Silver Age Fabric Load Conditions";
    }
}
