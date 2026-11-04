package com.phantomwing.thesilverage.forge.condition;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModConditions {
    /** Forge 1.20.1 takes condition serializers straight into its table, at mod construction. */
    public static void register(IEventBus eventBus) {
        CraftingHelper.register(ConfigBooleanCondition.Serializer.INSTANCE);
    }
}
