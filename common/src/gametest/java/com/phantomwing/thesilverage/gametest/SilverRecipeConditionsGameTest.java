package com.phantomwing.thesilverage.gametest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.recipe.RecipeOverride;
import net.minecraft.gametest.framework.GameTestHelper;
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
     * The silver gear's recipes ask {@code enable_silver_gear}, the Silver Arrows' {@code enable_silver_arrows},
     * Create's Spout's {@code enable_create_spout_oxidation} and oxidation's, Aquatic Ambitions' channeling oxidation's
     * (where the line has Create), and each vanilla override and its fallback the master switch and the override's own option.
     */
    public static void optionsGuardTheirRecipes(GameTestHelper helper) {
        ResourceManager resources = helper.getLevel().getServer().getResourceManager();
        List<String> problems = new ArrayList<>();
        for (String piece : GEAR) {
            expect(resources, TheSilverAge.MOD_ID, "recipe/silver_" + piece + ".json", Set.of("enable_silver_gear"), problems);
        }
        expect(resources, TheSilverAge.MOD_ID, "recipe/silver_arrow.json", Set.of("enable_silver_arrows"), problems);
        for (Map.Entry<String, Set<String>> folder : TestCompat.createRecipeFolders().entrySet()) {
            expectFolder(resources, folder.getKey(), folder.getValue(), problems);
        }
        for (Map.Entry<RecipeOverride, String> entry : OVERRIDDEN.entrySet()) {
            Set<String> options = Set.of("override_vanilla_recipes", entry.getKey().settingId());
            expect(resources, "minecraft", "recipe/" + entry.getValue() + ".json", options, problems);
            expect(resources, "minecraft", "recipe/" + entry.getValue() + "_fallback.json", options, problems);
        }
        if (problems.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, "Recipe conditions: " + String.join("; ", problems));
        }
    }

    private static void expectFolder(ResourceManager resources, String folder, Set<String> options, List<String> problems) {
        List<String> recipes = TestCompat.jsonPaths(resources, folder, TheSilverAge.MOD_ID);
        if (recipes.isEmpty()) {
            problems.add("no recipes in " + folder);
        }
        for (String recipe : recipes) {
            expect(resources, TheSilverAge.MOD_ID, recipe, options, problems);
        }
    }

    /** Both modloaders' conditions in {@code recipe} name every one of {@code options}. */
    private static void expect(ResourceManager resources, String namespace, String path, Set<String> options,
            List<String> problems) {
        String recipe = namespace + ":" + path;
        Optional<Resource> resource = TestCompat.resource(resources, namespace, path);
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
}
