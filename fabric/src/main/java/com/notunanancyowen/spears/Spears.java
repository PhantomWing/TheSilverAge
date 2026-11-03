package com.notunanancyowen.spears;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

/**
 * Compile-only stub of Backported Spears' {@code Spears}, the two members the Silver Spear uses, as its 1.0.1 for
 * 1.21.10 declares them. Its Fabric jar is built with a newer Loom than this rung's, which refuses to remap it, so
 * {@code fabric/build.gradle} can't compile against it; the stub is left out of the shipped jar, where the real class
 * binds at runtime.
 */
public final class Spears {
    public static DataComponentType<?> USE_EFFECTS;

    private Spears() {
    }

    public static Item registerSpearRaw(ResourceLocation id, ToolMaterial material, float attackDuration,
                                        float damageMultiplier, float delay, float dismountTime, float dismountThreshold,
                                        float knockbackTime, float knockbackThreshold, float damageTime,
                                        float damageThreshold) {
        throw new AssertionError("stub");
    }
}
