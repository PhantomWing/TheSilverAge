package com.phantomwing.thesilverage.condition;

import net.minecraftforge.common.crafting.CraftingHelper;

public class ModConditions {
    public static void register() {
        CraftingHelper.register(ConfigBooleanCondition.Serializer.INSTANCE);
    }
}
