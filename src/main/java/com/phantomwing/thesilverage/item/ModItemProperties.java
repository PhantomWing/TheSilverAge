package com.phantomwing.thesilverage.item;

import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModItemProperties {
    public static final ResourceLocation MOON_PHASE = ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, "moon_phase");

    public static void register() {
        ItemProperties.register(ModItems.MOON_DIAL.get(), MOON_PHASE, (stack, world, entity, seed) -> {
            if (world != null && !world.dimensionType().hasFixedTime()) {
                // If it is day, show a "transition" between the current and next moon phase.
                // If it is night, show the current moon phase.
                double progressionInDay = ((double)world.getDayTime() / Level.TICKS_PER_DAY) % 1;
                boolean isDay = progressionInDay < 0.52 || progressionInDay > 0.95; // Consider it day for a short period of time to show the transition.

                int textureId = world.getMoonPhase() * 2 - (isDay ? 1 : 0);

                // Wrap around to ensure the texture ID is between 0 and 15 (inclusive).
                if (textureId < 0) {
                    textureId += 16;
                }

                return textureId / 100f; // Normalize between [0, 1] for texture selection
            }

            return 0;
        });
    }
}
