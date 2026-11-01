package com.phantomwing.thesilverage.gametest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import net.minecraft.gametest.framework.GameTestHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/**
 * Game tests for Create's machines on silver, where Create is loaded: Create on NeoForge, or Create Fly, its Fabric
 * port, whose recipes take another format. Each recipe is read back as the recipe manager holds it, written out by its
 * own serializer, so what is checked is what Create parsed, whichever format it reads. Listed in {@link GameTests}.
 */
public final class SilverCreateGameTest {
    private static final String CREATE = ModIds.CREATE + ":";
    private static final String MOD = "thesilverage:";

    private SilverCreateGameTest() {
    }

    /**
     * The Spout weathers silver a stage, the Deployer waxes it and scrapes it, the Press makes a Silver Sheet, and
     * Crushed Raw Silver is crushed out of raw silver, washed into nuggets and smelted into an ingot. Without Create,
     * there is nothing to test.
     */
    public static void createProcessesSilver(GameTestHelper helper) {
        if (!CommonPlatform.isModLoaded(ModIds.CREATE)) {
            helper.succeed();
            return;
        }
        Map<String, JsonElement> recipes = TestCompat.recipesAsJson(helper.getLevel());
        List<String> problems = new ArrayList<>();
        expect(recipes, MOD + "filling/exposed_silver_from_oxidising", MOD + "silver_block", MOD + "exposed_silver", problems);
        expect(recipes, MOD + "deploying/waxed_silver_block_from_adding_wax", MOD + "silver_block", MOD + "waxed_silver_block", problems);
        expect(recipes, MOD + "deploying/silver_block_from_removing_wax", MOD + "waxed_silver_block", MOD + "silver_block", problems);
        expect(recipes, MOD + "deploying/silver_block_from_deoxidising", MOD + "exposed_silver", MOD + "silver_block", problems);
        expect(recipes, MOD + "pressing/silver_sheet", "c:ingots/silver", MOD + "silver_sheet", problems);
        expect(recipes, MOD + "splashing/crushed_raw_silver", CREATE + "crushed_raw_silver", MOD + "silver_nugget", problems);
        expect(recipes, MOD + "silver_ingot_from_crushed_raw_silver_smelting", CREATE + "crushed_raw_silver", MOD + "silver_ingot", problems);
        boolean crushed = recipes.values().stream().anyMatch(recipe -> recipe.isJsonObject()
                && recipe.getAsJsonObject().has("type")
                && recipe.getAsJsonObject().get("type").getAsString().equals(CREATE + "crushing")
                && turns(recipe.getAsJsonObject(), "c:raw_materials/silver", CREATE + "crushed_raw_silver"));
        if (!crushed) {
            problems.add("no crushing recipe makes Crushed Raw Silver of c:raw_materials/silver");
        }
        if (problems.isEmpty()) {
            helper.succeed();
        } else {
            fail(helper, "Create on silver: " + String.join("; ", problems));
        }
    }

    private static void expect(Map<String, JsonElement> recipes, String id, String input, String result, List<String> problems) {
        JsonElement recipe = recipes.get(id);
        if (recipe == null || !recipe.isJsonObject()) {
            problems.add(id + " is missing");
        } else if (!turns(recipe.getAsJsonObject(), input, result)) {
            problems.add(id + " doesn't turn " + input + " into " + result + ": " + recipe);
        }
    }

    /** Whether {@code recipe} names {@code input} among what it takes and {@code result} among what it gives. */
    private static boolean turns(JsonObject recipe, String input, String result) {
        List<String> taken = new ArrayList<>();
        List<String> given = new ArrayList<>();
        for (Map.Entry<String, JsonElement> member : recipe.entrySet()) {
            boolean isResult = member.getKey().equals("result") || member.getKey().equals("results");
            strings(member.getValue(), isResult ? given : taken);
        }
        return taken.contains(input) && given.contains(result);
    }

    /** Every string anywhere in {@code element}, a tag's without its {@code #}. */
    private static void strings(JsonElement element, List<String> out) {
        if (element.isJsonArray()) {
            element.getAsJsonArray().forEach(child -> strings(child, out));
        } else if (element.isJsonObject()) {
            element.getAsJsonObject().entrySet().forEach(member -> strings(member.getValue(), out));
        } else if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            String value = element.getAsString();
            out.add(value.startsWith("#") ? value.substring(1) : value);
        }
    }
}
