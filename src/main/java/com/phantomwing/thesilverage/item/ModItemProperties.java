package com.phantomwing.thesilverage.item;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.utils.LevelUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static final ResourceLocation MOON_PHASE = ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, "moon_phase");

    public static void register() {
        ItemProperties.register(ModItems.MOON_DIAL.get(), MOON_PHASE, (stack, world, entity, seed) -> {
            int moonPhaseSignal = LevelUtils.getMoonPhaseSignal(world);
            return moonPhaseSignal / 100f; // Normalize between [0, 1] for texture selection
        });
    }
}
