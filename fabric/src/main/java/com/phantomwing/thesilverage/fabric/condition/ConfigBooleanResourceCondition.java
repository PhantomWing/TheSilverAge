package com.phantomwing.thesilverage.fabric.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.Identifier;

/** Fabric runtime handler for the thesilverage:config_boolean load condition (parity with NeoForge ConfigBooleanCondition). */
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

    /**
     * Delegates to the config's own id lookup, which covers EVERY setting.
     *
     * <p>This used to switch over a hand-written list holding only
     * {@code override_vanilla_recipes}, so every other id fell through to {@code false} —
     * silently dropping the cleric silver trade on Fabric once 26.1 made villager trades
     * datapack entries gated on {@code enable_villager_trades}. Delegating keeps the handler
     * in step with the config automatically; the NeoForge twin already does the same.</p>
     */
    @Override
    public boolean test(RegistryOps.RegistryInfoLookup registryLookup) {
        // Unknown id: fail the condition (entry absent) rather than crash datapack loading.
        try {
            return TheSilverAgeFabricConfig.getBooleanConfigurationValue(settingId);
        } catch (Error unknownSetting) {
            TheSilverAge.LOGGER.error(
                    "Unknown config setting id '{}' in a thesilverage:config_boolean condition", settingId);
            return false;
        }
    }
}
