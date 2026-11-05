package com.phantomwing.thesilverage.advancement;

import net.minecraft.advancements.CriteriaTriggers;

/**
 * The mod's advancement triggers. 1.20.1 has no trigger registry: a trigger is added to vanilla's map by its id, a
 * plain {@code HashMap}, so Forge registers them from common setup's {@code enqueueWork} rather than during its
 * parallel mod construction, and Fabric from its entrypoint.
 */
public class ModCriteriaTriggers {
    public static final SilverArmorHurtTrigger SILVER_ARMOR_HURT = new SilverArmorHurtTrigger();

    private static boolean registered;

    public static synchronized void register() {
        if (!registered) {
            registered = true;
            CriteriaTriggers.register(SILVER_ARMOR_HURT);
        }
    }
}
