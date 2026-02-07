package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    public ModBlockLootTableProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
    }

    // Actually add our loot tables.
    @Override
    protected void generate() {
        dropOre(ModBlocks.SILVER_ORE, ModItems.RAW_SILVER);
        dropOre(ModBlocks.DEEPSLATE_SILVER_ORE, ModItems.RAW_SILVER);
        dropSelf(ModBlocks.RAW_SILVER_BLOCK);

        // Redstone blocks
        dropSelf(ModBlocks.MOON_PHASE_DETECTOR);

        // Block of Silver
        dropSelf(ModBlocks.SILVER_BLOCK);
        dropSelf(ModBlocks.EXPOSED_SILVER);
        dropSelf(ModBlocks.WEATHERED_SILVER);
        dropSelf(ModBlocks.OXIDIZED_SILVER);
        dropSelf(ModBlocks.WAXED_SILVER_BLOCK);
        dropSelf(ModBlocks.WAXED_EXPOSED_SILVER);
        dropSelf(ModBlocks.WAXED_WEATHERED_SILVER);
        dropSelf(ModBlocks.WAXED_OXIDIZED_SILVER);

        // Cut Silver
        dropSelf(ModBlocks.CUT_SILVER);
        dropSelf(ModBlocks.EXPOSED_CUT_SILVER);
        dropSelf(ModBlocks.WEATHERED_CUT_SILVER);
        dropSelf(ModBlocks.OXIDIZED_CUT_SILVER);
        dropSelf(ModBlocks.WAXED_CUT_SILVER);
        dropSelf(ModBlocks.WAXED_EXPOSED_CUT_SILVER);
        dropSelf(ModBlocks.WAXED_WEATHERED_CUT_SILVER);
        dropSelf(ModBlocks.WAXED_OXIDIZED_CUT_SILVER);

        // Cut Silver Slab
        dropSlab(ModBlocks.CUT_SILVER_SLAB);
        dropSlab(ModBlocks.EXPOSED_CUT_SILVER_SLAB);
        dropSlab(ModBlocks.WEATHERED_CUT_SILVER_SLAB);
        dropSlab(ModBlocks.OXIDIZED_CUT_SILVER_SLAB);
        dropSlab(ModBlocks.WAXED_CUT_SILVER_SLAB);
        dropSlab(ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB);
        dropSlab(ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB);
        dropSlab(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB);

        // Cut Silver Stairs
        dropSelf(ModBlocks.CUT_SILVER_STAIRS);
        dropSelf(ModBlocks.EXPOSED_CUT_SILVER_STAIRS);
        dropSelf(ModBlocks.WEATHERED_CUT_SILVER_STAIRS);
        dropSelf(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS);
        dropSelf(ModBlocks.WAXED_CUT_SILVER_STAIRS);
        dropSelf(ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS);
        dropSelf(ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS);
        dropSelf(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS);

        // Chiseled Silver
        dropSelf(ModBlocks.CHISELED_SILVER);
        dropSelf(ModBlocks.EXPOSED_CHISELED_SILVER);
        dropSelf(ModBlocks.WEATHERED_CHISELED_SILVER);
        dropSelf(ModBlocks.OXIDIZED_CHISELED_SILVER);
        dropSelf(ModBlocks.WAXED_CHISELED_SILVER);
        dropSelf(ModBlocks.WAXED_EXPOSED_CHISELED_SILVER);
        dropSelf(ModBlocks.WAXED_WEATHERED_CHISELED_SILVER);
        dropSelf(ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER);

        // Silver Grate
        dropSelf(ModBlocks.SILVER_GRATE);
        dropSelf(ModBlocks.EXPOSED_SILVER_GRATE);
        dropSelf(ModBlocks.WEATHERED_SILVER_GRATE);
        dropSelf(ModBlocks.OXIDIZED_SILVER_GRATE);
        dropSelf(ModBlocks.WAXED_SILVER_GRATE);
        dropSelf(ModBlocks.WAXED_EXPOSED_SILVER_GRATE);
        dropSelf(ModBlocks.WAXED_WEATHERED_SILVER_GRATE);
        dropSelf(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE);

        // Silver Trapdoor
        dropSelf(ModBlocks.SILVER_TRAPDOOR);
        dropSelf(ModBlocks.EXPOSED_SILVER_TRAPDOOR);
        dropSelf(ModBlocks.WEATHERED_SILVER_TRAPDOOR);
        dropSelf(ModBlocks.OXIDIZED_SILVER_TRAPDOOR);
        dropSelf(ModBlocks.WAXED_SILVER_TRAPDOOR);
        dropSelf(ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR);
        dropSelf(ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR);
        dropSelf(ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR);

        // Silver Door
        dropDoor(ModBlocks.SILVER_DOOR);
        dropDoor(ModBlocks.EXPOSED_SILVER_DOOR);
        dropDoor(ModBlocks.WEATHERED_SILVER_DOOR);
        dropDoor(ModBlocks.OXIDIZED_SILVER_DOOR);
        dropDoor(ModBlocks.WAXED_SILVER_DOOR);
        dropDoor(ModBlocks.WAXED_EXPOSED_SILVER_DOOR);
        dropDoor(ModBlocks.WAXED_WEATHERED_SILVER_DOOR);
        dropDoor(ModBlocks.WAXED_OXIDIZED_SILVER_DOOR);
    }

    // The contents of this Iterable are used for validation.
    // We return an Iterable over our block registry's values here.
    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        // The contents of our DeferredRegister.
        return ModBlocks.BLOCKS.getEntries()
                .stream()
                // Cast to Block here, otherwise it will be a ? extends Block and Java will complain.
                .map(e -> (Block) e.value())
                .toList();
    }

    private <T extends Block> void dropSelf(DeferredBlock<T> block) {
        this.dropSelf(block.get());
    }

    private void dropSlab(DeferredBlock<SlabBlock> block) {
        add(block.get(), this::createSlabItemTable);
    }

    private void dropDoor(DeferredBlock<DoorBlock> block) {
        add(block.get(), this::createDoorTable);
    }

    private void dropOre(DeferredBlock<Block> block, DeferredItem<Item> item) {
        add(block.get(), (b) -> createOreDrop(b, item.get()));
    }
}
