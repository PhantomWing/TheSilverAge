package com.phantomwing.thesilverage.recipe;

import org.jetbrains.annotations.Nullable;

/**
 * The vanilla recipes The Silver Age makes with silver, each with its own config option under the
 * {@code override_vanilla_recipes} master switch: an override applies only while both are on. Those
 * whose block also looks silver while it does have a built-in texture pack of their own, under
 * {@code resourcepacks/<texturePack>}.
 */
public enum RecipeOverride {
    LODESTONE("override_lodestone_recipe", null),
    BREWING_STAND("override_brewing_stand_recipe", "silver_brewing_stand"),
    COMPARATOR("override_comparator_recipe", "silver_comparator"),
    REPEATER("override_repeater_recipe", "silver_repeater"),
    GLISTERING_MELON("override_glistering_melon_recipe", null);

    private final String settingId;
    @Nullable
    private final String texturePack;

    RecipeOverride(String settingId, @Nullable String texturePack) {
        this.settingId = settingId;
        this.texturePack = texturePack;
    }

    public String settingId() {
        return settingId;
    }

    /** The folder of its texture pack under {@code resourcepacks/}, or null for a recipe that changes no look. */
    @Nullable
    public String texturePack() {
        return texturePack;
    }

    public boolean hasTexturePack() {
        return texturePack != null;
    }

    /** Whether a pack repository id names one of these texture packs, on either loader. */
    public static boolean isTexturePackId(@Nullable String id) {
        if (id == null || !id.contains("thesilverage")) {
            return false;
        }
        for (RecipeOverride override : values()) {
            if (override.hasTexturePack() && id.endsWith(override.texturePack)) {
                return true;
            }
        }
        return false;
    }
}
