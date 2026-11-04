package com.phantomwing.thesilverage.fabric.condition;

import com.google.gson.JsonObject;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;
import com.phantomwing.thesilverage.platform.CommonConfig;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;


/**
 * Fabric parity twin of the Forge
 * {@link com.phantomwing.thesilverage.forge.condition.ConfigBooleanCondition}.
 *
 * <p>The shared generated data ({@code common/src/generated/resources}) is
 * authored once by the Forge datagen and gates the conditional
 * vanilla-recipe overrides with a Forge-only
 * {@code "forge:conditions": [{ "type": "thesilverage:config_boolean",
 * "settingId": "override_vanilla_recipes" }]} block. Fabric cannot parse
 * {@code forge:conditions}; the Forge datagen post-processor
 * ({@code FabricConditionsProvider}) additionally emits an equivalent
 * {@code "fabric:load_conditions": [{ "condition": "thesilverage:config_boolean",
 * "settingId": "override_vanilla_recipes" }]} block. This class is the runtime
 * handler Fabric uses to evaluate that block.</p>
 *
 * <p>Field name ({@code settingId}) and condition id
 * ({@code thesilverage:config_boolean}) are kept byte-identical to the Forge
 * side so the single translated JSON is correct for both loaders. The value
 * source is the cross-loader {@link CommonConfig} bridge — exactly the same
 * boolean the Forge {@code ConfigBooleanCondition} reads via
 * {@code Configuration.getBooleanConfigurationValue(settingId)} — so the
 * conditional/fallback recipe pair resolves identically on both loaders.</p>
 */
public final class ConfigBooleanResourceCondition {
    public static final ResourceLocation ID =
            new ResourceLocation(TheSilverAge.MOD_ID, "config_boolean");

    private ConfigBooleanResourceCondition() {
    }

    /** Registers the condition with Fabric API's 1.20.1 resource conditions, which read the condition's JSON as it is. */
    public static void register() {
        ResourceConditions.register(ID, ConfigBooleanResourceCondition::test);
    }

    private static boolean test(JsonObject json) {
        // Any boolean option, as Forge's Configuration.getBooleanConfigurationValue reads it. An id
        // no option has fails the condition (the recipe is simply absent) where Forge's throws.
        String settingId = GsonHelper.getAsString(json, "settingId");
        Boolean value = TheSilverAgeFabricConfig.booleanValueOf(TheSilverAgeFabricConfig.get(), settingId);
        return value != null && value;
    }
}
