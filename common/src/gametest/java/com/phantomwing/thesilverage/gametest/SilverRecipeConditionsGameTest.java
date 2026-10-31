package com.phantomwing.thesilverage.gametest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.recipe.RecipeOverride;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/**
 * Game tests for the options that take recipes away: each recipe they guard names its option in its conditions, for
 * NeoForge and for Fabric alike. The options are read as datapacks load, so it is the conditions that are checked, not
 * the recipes an option would leave. Listed in {@link GameTests}.
 */
public final class SilverRecipeConditionsGameTest {
    private static final List<String> GEAR = List.of("sword", "pickaxe", "axe", "shovel", "hoe",
            "helmet", "chestplate", "leggings", "boots");
    /** Each override's recipe file in the minecraft namespace. */
    private static final Map<RecipeOverride, String> OVERRIDDEN = Map.of(RecipeOverride.LODESTONE, "lodestone",
            RecipeOverride.BREWING_STAND, "brewing_stand", RecipeOverride.COMPARATOR, "comparator",
            RecipeOverride.REPEATER, "repeater", RecipeOverride.GLISTERING_MELON, "glistering_melon_slice");
    private static final String NEOFORGE = "neoforge:conditions";
    private static final String FABRIC = "fabric:load_conditions";

    private SilverRecipeConditionsGameTest() {
    }

    /**
     * The silver gear's recipes ask {@code enable_silver_gear_recipes}, the Silver Arrows' {@code enable_silver_arrows},
     * Create's Spout's {@code enable_create_spout_oxidation} and oxidation's, Aquatic Ambitions' channeling oxidation's, and
     * each vanilla override and its fallback the master switch and the override's own option.
     */
    public static void optionsGuardTheirRecipes(GameTestHelper helper) {
        ResourceManager resources = helper.getLevel().getServer().getResourceManager();
        List<String> problems = new ArrayList<>();
        for (String piece : GEAR) {
            expect(resources, mod("recipe/silver_" + piece + ".json"), Set.of("enable_silver_gear_recipes"), problems);
        }
        expect(resources, mod("recipe/silver_arrow.json"), Set.of("enable_silver_arrows"), problems);
        expectFolder(resources, "recipe/filling", Set.of("enable_silver_oxidation", "enable_create_spout_oxidation"), problems);
        expectFolder(resources, "recipe/channeling", Set.of("enable_silver_oxidation"), problems);
        for (Map.Entry<RecipeOverride, String> entry : OVERRIDDEN.entrySet()) {
            Set<String> options = Set.of("override_vanilla_recipes", entry.getKey().settingId());
            expect(resources, ResourceLocation.withDefaultNamespace("recipe/" + entry.getValue() + ".json"), options, problems);
            expect(resources, ResourceLocation.withDefaultNamespace("recipe/" + entry.getValue() + "_fallback.json"), options, problems);
        }
        if (problems.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, "Recipe conditions: " + String.join("; ", problems));
        }
    }

    private static void expectFolder(ResourceManager resources, String folder, Set<String> options, List<String> problems) {
        Set<ResourceLocation> recipes = resources.listResources(folder,
                id -> id.getNamespace().equals(TheSilverAge.MOD_ID) && id.getPath().endsWith(".json")).keySet();
        if (recipes.isEmpty()) {
            problems.add("no recipes in " + folder);
        }
        for (ResourceLocation recipe : recipes) {
            expect(resources, recipe, options, problems);
        }
    }

    /** Both modloaders' conditions in {@code recipe} name every one of {@code options}. */
    private static void expect(ResourceManager resources, ResourceLocation recipe, Set<String> options, List<String> problems) {
        Optional<Resource> resource = resources.getResource(recipe);
        if (resource.isEmpty()) {
            problems.add(recipe + " is missing");
            return;
        }
        JsonObject json;
        try (Reader reader = resource.get().openAsReader()) {
            json = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException | RuntimeException e) {
            problems.add(recipe + " can't be read: " + e.getMessage());
            return;
        }
        for (String loader : List.of(NEOFORGE, FABRIC)) {
            Set<String> named = new HashSet<>();
            settingsIn(json.get(loader), named);
            if (!named.containsAll(options)) {
                problems.add(recipe + "'s " + loader + " name " + named + ", not all of " + options);
            }
        }
    }

    /** Every {@code settingId} anywhere in {@code element}. */
    private static void settingsIn(JsonElement element, Set<String> named) {
        if (element == null) {
            return;
        }
        if (element.isJsonArray()) {
            element.getAsJsonArray().forEach(child -> settingsIn(child, named));
        } else if (element.isJsonObject()) {
            for (Map.Entry<String, JsonElement> member : element.getAsJsonObject().entrySet()) {
                if (member.getKey().equals("settingId")) {
                    named.add(member.getValue().getAsString());
                } else {
                    settingsIn(member.getValue(), named);
                }
            }
        }
    }

    private static ResourceLocation mod(String path) {
        return ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, path);
    }
}
