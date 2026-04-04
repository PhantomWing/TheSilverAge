package com.phantomwing.thesilverage.condition;

import com.google.gson.JsonObject;
import com.phantomwing.thesilverage.Configuration;
import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class ConfigBooleanCondition implements ICondition {
    private static final ResourceLocation ID = new ResourceLocation(TheSilverAge.MOD_ID, "config_boolean");
    private final String settingId;

    public ConfigBooleanCondition(String settingId) {
        this.settingId = settingId;
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test(IContext context) {
        return Configuration.getBooleanConfigurationValue(settingId);
    }

    public static class Serializer implements IConditionSerializer<ConfigBooleanCondition> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void write(JsonObject json, ConfigBooleanCondition value) {
            json.addProperty("setting_id", value.settingId);
        }

        @Override
        public ConfigBooleanCondition read(JsonObject json) {
            return new ConfigBooleanCondition(GsonHelper.getAsString(json, "setting_id"));
        }

        @Override
        public ResourceLocation getID() {
            return ConfigBooleanCondition.ID;
        }
    }
}
