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
 * Translates each generated JSON's {@code neoforge:conditions} into an equivalent
 * {@code fabric:load_conditions} array in the same file, so the shared generated
 * tree gates recipes identically on both loaders. Must run last in DataGenerators.
 */
public class FabricConditionsProvider implements DataProvider {
    private static final String NEOFORGE_KEY = "neoforge:conditions";
    private static final String FABRIC_KEY = "fabric:load_conditions";
    private static final String FABRIC_CONDITION = "condition";
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

            json.add(FABRIC_KEY, fabricConditions);
            // saveStable keeps the file byte-stable across reruns.
            futures.add(DataProvider.saveStable(cache, json, path));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

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
