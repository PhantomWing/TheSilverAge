package com.phantomwing.thesilverage.forge.condition;

import com.google.gson.JsonObject;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.forge.Configuration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

/**
 * A recipe, loot or advancement that loads only while a boolean option of the config is on:
 * {@code {"type": "thesilverage:config_boolean", "settingId": "override_vanilla_recipes"}}, as Fabric's twin reads it.
 */
public record ConfigBooleanCondition(String settingId) implements ICondition {
    public static final ResourceLocation ID = TheSilverAge.resourceLocation("config_boolean");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test(IContext context) {
        return Configuration.getBooleanConfigurationValue(settingId);
    }

    public static final class Serializer implements IConditionSerializer<ConfigBooleanCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, ConfigBooleanCondition value) {
            json.addProperty("settingId", value.settingId());
        }

        @Override
        public ConfigBooleanCondition read(JsonObject json) {
            return new ConfigBooleanCondition(GsonHelper.getAsString(json, "settingId"));
        }

        @Override
        public ResourceLocation getID() {
            return ID;
        }
    }
}
