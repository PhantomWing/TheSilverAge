package com.phantomwing.thesilverage.advancement;

import net.minecraft.advancements.CriteriaTriggers;

/** The mod's advancement triggers. 1.20.1 has no trigger registry: a trigger is added to vanilla's list by its id. */
public class ModCriteriaTriggers {
    public static final SilverArmorHurtTrigger SILVER_ARMOR_HURT = CriteriaTriggers.register(new SilverArmorHurtTrigger());

    public static void register() {
        // Loading the class registers the triggers.
    }
}
