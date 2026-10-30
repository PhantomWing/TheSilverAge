package com.phantomwing.thesilverage.advancement;

import com.phantomwing.thesilverage.TheSilverAge;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

public class ModCriteriaTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS =
            DeferredRegister.create(TheSilverAge.MOD_ID, Registries.TRIGGER_TYPE);

    public static final RegistrySupplier<SilverArmorHurtTrigger> SILVER_ARMOR_HURT =
            TRIGGERS.register("silver_armor_hurt", SilverArmorHurtTrigger::new);

    public static void register() {
        TRIGGERS.register();
    }
}
