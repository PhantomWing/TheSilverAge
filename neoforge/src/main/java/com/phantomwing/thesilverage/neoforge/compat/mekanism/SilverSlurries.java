package com.phantomwing.thesilverage.neoforge.compat.mekanism;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.tags.CommonTags;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Silver's slurries for Mekanism's ore processing, as its gold has: ore dissolves into the dirty slurry, which washes
 * into the clean one, which crystallizes into Silver Crystals. Only loaded when Mekanism is installed, whose chemical
 * registry they go in.
 */
public final class SilverSlurries {
    /** The Silver Ingot's light shade, which the slurries' fluids are drawn in. */
    private static final int TINT = 0xB6CBE2;

    private static final DeferredRegister<Chemical> CHEMICALS =
            DeferredRegister.create(MekanismAPI.CHEMICAL_REGISTRY_NAME, TheSilverAge.MOD_ID);

    static {
        CHEMICALS.register("dirty_silver", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(TINT).ore(CommonTags.Items.ORES_SILVER)));
        CHEMICALS.register("clean_silver", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(TINT).ore(CommonTags.Items.ORES_SILVER)));
    }

    private SilverSlurries() {
    }

    public static void register(IEventBus modEventBus) {
        CHEMICALS.register(modEventBus);
    }
}
