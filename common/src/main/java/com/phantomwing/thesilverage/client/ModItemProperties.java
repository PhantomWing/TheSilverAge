package com.phantomwing.thesilverage.client;

import com.mojang.serialization.MapCodec;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.utils.LevelUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/** Client registration of the Moon Dial's {@code thesilverage:moon_phase} range-select item-model property. */
public final class ModItemProperties {
    public static final Identifier MOON_PHASE =
            Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, "moon_phase");

    private ModItemProperties() {
    }

    public static void register() {
        RangeSelectItemModelProperties.ID_MAPPER.put(MOON_PHASE, MoonPhaseProperty.MAP_CODEC);
    }

    /** 16-state moon-phase property (see {@link LevelUtils#getMoonPhaseSignal}). */
    public record MoonPhaseProperty() implements RangeSelectItemModelProperty {
        public static final MapCodec<MoonPhaseProperty> MAP_CODEC = MapCodec.unit(new MoonPhaseProperty());

        @Override
        public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner entity, int seed) {
            // 0..15 → 0..0.9375 in exact 1/16 steps (powers-of-two denominators are exact as floats).
            return LevelUtils.getMoonPhaseSignal(level) / 16f;
        }

        @Override
        public MapCodec<? extends RangeSelectItemModelProperty> type() {
            return MAP_CODEC;
        }
    }
}
