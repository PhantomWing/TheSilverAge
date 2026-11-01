package com.phantomwing.thesilverage.fabric.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;
import com.phantomwing.thesilverage.platform.CommonConfig;
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

    /** Delegates to the config's own id lookup, which covers every setting. */
    @Override
    public boolean test(RegistryOps.RegistryInfoLookup registryLookup) {
        // Any boolean option, as NeoForge's Configuration.getBooleanConfigurationValue reads it. An id
        // no option has fails the condition (the recipe is simply absent) where NeoForge's throws.
        Boolean value = TheSilverAgeFabricConfig.booleanValueOf(TheSilverAgeFabricConfig.get(), settingId);
        return value != null && value;
    }
}
