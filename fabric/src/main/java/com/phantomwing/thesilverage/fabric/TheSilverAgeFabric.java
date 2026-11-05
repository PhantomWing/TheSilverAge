package com.phantomwing.thesilverage.fabric;

import com.phantomwing.thesilverage.TheSilverAgeCommon;
import com.phantomwing.thesilverage.advancement.ModCriteriaTriggers;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.fabric.compat.create.CreateFabricCompat;
import com.phantomwing.thesilverage.fabric.condition.ConfigBooleanResourceCondition;
import com.phantomwing.thesilverage.fabric.config.TheSilverAgeFabricConfig;
import com.phantomwing.thesilverage.fabric.loot.SilverLootTableId;
import com.phantomwing.thesilverage.fabric.mixin.PoiTypesAccessor;
import com.phantomwing.thesilverage.fabric.villager.ModVillagerTrades;
import com.phantomwing.thesilverage.fabric.world.ModWorldGen;
import com.phantomwing.thesilverage.firework.ModFireworks;
import dev.architectury.event.events.common.LifecycleEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.level.storage.loot.LootTable;

/**
 * Fabric entrypoint for The Silver Age.
 *
 * <p>Delegates the loader-agnostic bootstrap to {@link TheSilverAgeCommon#init()}, then wires what Forge does through
 * its own events and data: the firework star, the villager trade, silver ore's world generation, the lightning rods'
 * point of interest, the {@code config_boolean} resource condition, and each loaded {@code LootTable}'s id, which
 * {@code LootTableMixin} (Fabric's twin of Forge's global loot modifiers) reads to know which
 * {@link com.phantomwing.thesilverage.loot.SilverLootSpec} entries to apply at roll time.</p>
 */
public final class TheSilverAgeFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // MUST be first: the loot mixin and the thesilverage:config_boolean
        // resource condition read config very early (datapack load), so the
        // AutoConfig holder must be registered before anything else runs.
        TheSilverAgeFabricConfig.register();

        TheSilverAgeCommon.init();
        ModCriteriaTriggers.register();
        LifecycleEvent.SETUP.register(TheSilverAgeCommon::setup);

        // Silver Nugget firework-star parity. Forge mutates the vanilla
        // FireworkStarRecipe static fields in FMLCommonSetupEvent; Fabric has no
        // equivalent staged setup, so do it here — registries are populated by
        // the time the ModInitializer runs (after TheSilverAgeCommon.init()),
        // and the shared loader-agnostic ModFireworks rebuilds SHAPE_INGREDIENT
        // from the merged key set (no loader-specific CompoundIngredient).
        ModFireworks.register();

        // Silver villager-trade parity. Forge adds the Cleric trade from
        // VillagerTradesEvent; Fabric registers it once here via
        // TradeOfferHelper (the ENABLE_VILLAGER_TRADES gate is evaluated live
        // inside the listing so runtime config toggles still take effect).
        ModVillagerTrades.register();

        // Silver ore world-gen parity. Forge attaches the placed features to
        // biomes via forge:add_features biome-modifier JSON (which Fabric
        // ignores); this re-creates those three injections through the Fabric
        // BiomeModifications API so silver ore actually generates on Fabric.
        ModWorldGen.register();

        registerLightningRods();

        // Twin of Forge's `thesilverage:config_boolean` recipe condition. The
        // shared generated data carries both dialects in each conditional file
        // (Forge's `conditions` and Fabric's `fabric:load_conditions`, the latter
        // added by FabricConditionsProvider in Forge's datagen); this registers
        // the handler Fabric evaluates the latter with. `fabric:all_mods_loaded` (the
        // create-gated recipes) is built into fabric-api and needs no
        // registration here.
        ConfigBooleanResourceCondition.register();

        // The home of any runtime Create integration on Fabric; none today.
        CreateFabricCompat.init();

        // Vanilla LootTable carries no id of its own. Once all loot tables are
        // loaded, stamp each instance with its registry id (read back by the
        // Silver loot mixin, as Forge's GLMs read forge:loot_table_id).
        LootTableEvents.ALL_LOADED.register((resourceManager, lootData) ->
                lootData.getKeys(LootDataType.TABLE).forEach(id -> {
                    LootTable table = lootData.getLootTable(id);
                    if (table instanceof SilverLootTableId holder) {
                        holder.thesilverage$setLootTableId(id);
                    }
                }));
    }

    /**
     * Lightning finds a rod by its point of interest, which 1.20.1 gives the vanilla rod alone. Forge answers it for
     * the Silver Lightning Rods through its {@code PoiTypesMixin}.
     */
    private static void registerLightningRods() {
        Holder<PoiType> lightningRod = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolderOrThrow(PoiTypes.LIGHTNING_ROD);
        ModBlocks.silverLightningRods().forEach(rod -> rod.getStateDefinition().getPossibleStates()
                .forEach(state -> PoiTypesAccessor.thesilverage$typeByState().put(state, lightningRod)));
    }
}
