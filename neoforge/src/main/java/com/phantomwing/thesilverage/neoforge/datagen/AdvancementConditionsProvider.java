package com.phantomwing.thesilverage.neoforge.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Writes the {@code neoforge:conditions} of {@link ModAdvancementProvider#CONDITIONS} into the advancements it has
 * written, which an advancement generator has no way to. Registered after the advancements and before
 * {@link FabricConditionsProvider}, which then adds Fabric's.
 */
public class AdvancementConditionsProvider implements DataProvider {
    private final PackOutput.PathProvider advancements;

    public AdvancementConditionsProvider(PackOutput output) {
        this.advancements = output.createPathProvider(PackOutput.Target.DATA_PACK, "advancement");
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();
        ModAdvancementProvider.CONDITIONS.forEach((id, conditions) -> {
            Path path = advancements.json(id);
            try {
                JsonObject json = JsonParser.parseString(Files.readString(path, StandardCharsets.UTF_8)).getAsJsonObject();
                JsonElement encoded = ICondition.LIST_CODEC.encodeStart(JsonOps.INSTANCE, conditions).getOrThrow();
                json.add(ConditionalOps.DEFAULT_CONDITIONS_KEY, encoded);
                futures.add(DataProvider.saveStable(cache, json, path));
            } catch (IOException e) {
                futures.add(CompletableFuture.failedFuture(e));
            }
        });
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public @NotNull String getName() {
        return "Advancement conditions";
    }
}
