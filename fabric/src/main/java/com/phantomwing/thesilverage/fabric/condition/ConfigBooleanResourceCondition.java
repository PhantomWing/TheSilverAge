package com.phantomwing.thesilverage.fabric.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.platform.CommonConfig;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.Identifier;

/**
 * Fabric parity twin of the NeoForge
 * {@link com.phantomwing.thesilverage.neoforge.condition.ConfigBooleanCondition}.
 *
 * <p>The shared generated data ({@code common/src/generated/resources}) is
 * authored once by the NeoForge datagen and gates the conditional
 * vanilla-recipe overrides with a NeoForge-only
 * {@code "neoforge:conditions": [{ "type": "thesilverage:config_boolean",
 * "settingId": "override_vanilla_recipes" }]} block. Fabric cannot parse
 * {@code neoforge:conditions}; the NeoForge datagen post-processor
 * ({@code FabricConditionsProvider}) additionally emits an equivalent
 * {@code "fabric:load_conditions": [{ "condition": "thesilverage:config_boolean",
 * "settingId": "override_vanilla_recipes" }]} block. This class is the runtime
 * handler Fabric uses to evaluate that block.</p>
 *
 * <p>Field name ({@code settingId}) and condition id
 * ({@code thesilverage:config_boolean}) are kept byte-identical to the NeoForge
 * side so the single translated JSON is correct for both loaders. The value
 * source is the cross-loader {@link CommonConfig} bridge — exactly the same
 * boolean the NeoForge {@code ConfigBooleanCondition} reads via
 * {@code Configuration.getBooleanConfigurationValue(settingId)} — so the
 * conditional/fallback recipe pair resolves identically on both loaders.</p>
 */
public record ConfigBooleanResourceCondition(String settingId) implements ResourceCondition {
    public static final Identifier ID =
            Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, "config_boolean");

    public static final MapCodec<ConfigBooleanResourceCondition> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.STRING.fieldOf("settingId").forGetter(ConfigBooleanResourceCondition::settingId)
    ).apply(inst, ConfigBooleanResourceCondition::new));

    public static final ResourceConditionType<ConfigBooleanResourceCondition> TYPE =
            ResourceConditionType.create(ID, CODEC);

    @Override
    public ResourceConditionType<?> getType() {
        return TYPE;
    }

    @Override
    public boolean test(RegistryOps.RegistryInfoLookup registryLookup) {
        // Mirrors NeoForge Configuration.getBooleanConfigurationValue(settingId):
        // the only settingId emitted into the shared generated data is
        // override_vanilla_recipes (the conditional/fallback recipe pairs).
        return switch (settingId) {
            case "override_vanilla_recipes" -> CommonConfig.overrideVanillaRecipes();
            // Defensive: an unknown id means the datapack referenced a setting the
            // Fabric bridge doesn't expose yet. NeoForge's switch throws for
            // unknown ids; here we fail the condition (recipe simply absent)
            // rather than hard-crash datapack loading. No such id is generated.
            default -> false;
        };
    }
}
