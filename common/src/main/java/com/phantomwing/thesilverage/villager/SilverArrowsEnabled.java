package com.phantomwing.thesilverage.villager;

import com.mojang.serialization.MapCodec;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.platform.CommonConfig;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

/**
 * {@code thesilverage:silver_arrows_enabled}: the master Fletcher's Silver Arrow trade's merchant predicate. From 26.1 a
 * trade is data, made into an offer when a villager levels up; with this the offer is skipped while
 * {@code enable_silver_arrows} is off, read then rather than when the data loaded, as the code trades of 1.21.x did.
 */
public record SilverArrowsEnabled() implements LootItemCondition {
    public static final MapCodec<SilverArrowsEnabled> CODEC = MapCodec.unit(new SilverArrowsEnabled());

    private static final DeferredRegister<MapCodec<? extends LootItemCondition>> CONDITIONS =
            DeferredRegister.create(TheSilverAge.MOD_ID, Registries.LOOT_CONDITION_TYPE);

    public static final RegistrySupplier<MapCodec<? extends LootItemCondition>> TYPE =
            CONDITIONS.register("silver_arrows_enabled", () -> CODEC);

    @Override
    public boolean test(LootContext context) {
        return CommonConfig.silverArrows();
    }

    @Override
    public MapCodec<? extends LootItemCondition> codec() {
        return CODEC;
    }

    public static void register() {
        CONDITIONS.register();
    }
}
