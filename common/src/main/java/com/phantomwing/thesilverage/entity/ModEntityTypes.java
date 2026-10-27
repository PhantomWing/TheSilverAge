package com.phantomwing.thesilverage.entity;

import com.phantomwing.thesilverage.TheSilverAge;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(TheSilverAge.MOD_ID, Registries.ENTITY_TYPE);

    // Sized, tracked and updated as vanilla's arrows are.
    public static final RegistrySupplier<EntityType<SilverArrow>> SILVER_ARROW =
            ENTITY_TYPES.register("silver_arrow", () -> EntityType.Builder.<SilverArrow>of(SilverArrow::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .eyeHeight(0.13f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("silver_arrow"));

    public static void register() {
        ENTITY_TYPES.register();
    }
}
