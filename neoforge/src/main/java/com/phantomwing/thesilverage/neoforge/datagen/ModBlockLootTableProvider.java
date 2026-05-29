package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import dev.architectury.registry.registries.RegistrySupplier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

        // Silver Bricks
        dropSelf(ModBlocks.SILVER_BRICKS);
        dropSelf(ModBlocks.EXPOSED_SILVER_BRICKS);
        dropSelf(ModBlocks.WEATHERED_SILVER_BRICKS);
        dropSelf(ModBlocks.OXIDIZED_SILVER_BRICKS);
        dropSelf(ModBlocks.WAXED_SILVER_BRICKS);
        dropSelf(ModBlocks.WAXED_EXPOSED_SILVER_BRICKS);
        dropSelf(ModBlocks.WAXED_WEATHERED_SILVER_BRICKS);
        dropSelf(ModBlocks.WAXED_OXIDIZED_SILVER_BRICKS);

        // Silver Brick Slab
        dropSlab(ModBlocks.SILVER_BRICK_SLAB);
        dropSlab(ModBlocks.EXPOSED_SILVER_BRICK_SLAB);
        dropSlab(ModBlocks.WEATHERED_SILVER_BRICK_SLAB);
        dropSlab(ModBlocks.OXIDIZED_SILVER_BRICK_SLAB);
        dropSlab(ModBlocks.WAXED_SILVER_BRICK_SLAB);
        dropSlab(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_SLAB);
        dropSlab(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_SLAB);
        dropSlab(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_SLAB);

        // Silver Brick Stairs
        dropSelf(ModBlocks.SILVER_BRICK_STAIRS);
        dropSelf(ModBlocks.EXPOSED_SILVER_BRICK_STAIRS);
        dropSelf(ModBlocks.WEATHERED_SILVER_BRICK_STAIRS);
        dropSelf(ModBlocks.OXIDIZED_SILVER_BRICK_STAIRS);
        dropSelf(ModBlocks.WAXED_SILVER_BRICK_STAIRS);
        dropSelf(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_STAIRS);
        dropSelf(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_STAIRS);
        dropSelf(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_STAIRS);

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

        // Silver Pillar
        dropSelf(ModBlocks.SILVER_PILLAR);
        dropSelf(ModBlocks.EXPOSED_SILVER_PILLAR);
        dropSelf(ModBlocks.WEATHERED_SILVER_PILLAR);
        dropSelf(ModBlocks.OXIDIZED_SILVER_PILLAR);
        dropSelf(ModBlocks.WAXED_SILVER_PILLAR);
        dropSelf(ModBlocks.WAXED_EXPOSED_SILVER_PILLAR);
        dropSelf(ModBlocks.WAXED_WEATHERED_SILVER_PILLAR);
        dropSelf(ModBlocks.WAXED_OXIDIZED_SILVER_PILLAR);

        // Silver Grate
        dropSelf(ModBlocks.SILVER_GRATE);
        dropSelf(ModBlocks.EXPOSED_SILVER_GRATE);
        dropSelf(ModBlocks.WEATHERED_SILVER_GRATE);
        dropSelf(ModBlocks.OXIDIZED_SILVER_GRATE);
        dropSelf(ModBlocks.WAXED_SILVER_GRATE);
        dropSelf(ModBlocks.WAXED_EXPOSED_SILVER_GRATE);
        dropSelf(ModBlocks.WAXED_WEATHERED_SILVER_GRATE);
        dropSelf(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE);

        // Silver Bulb
        dropSelf(ModBlocks.SILVER_BULB);
        dropSelf(ModBlocks.EXPOSED_SILVER_BULB);
        dropSelf(ModBlocks.WEATHERED_SILVER_BULB);
        dropSelf(ModBlocks.OXIDIZED_SILVER_BULB);
        dropSelf(ModBlocks.WAXED_SILVER_BULB);
        dropSelf(ModBlocks.WAXED_EXPOSED_SILVER_BULB);
        dropSelf(ModBlocks.WAXED_WEATHERED_SILVER_BULB);
        dropSelf(ModBlocks.WAXED_OXIDIZED_SILVER_BULB);

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
        // The contents of our (Architectury) DeferredRegister.
        List<Block> blocks = new ArrayList<>();
        for (RegistrySupplier<Block> entry : ModBlocks.BLOCKS) {
            blocks.add(entry.get());
        }
        return blocks;
    }

    private <T extends Block> void dropSelf(RegistrySupplier<T> block) {
        this.dropSelf(block.get());
    }

    private void dropSlab(RegistrySupplier<SlabBlock> block) {
        add(block.get(), this::createSlabItemTable);
    }

    private void dropDoor(RegistrySupplier<DoorBlock> block) {
        add(block.get(), this::createDoorTable);
    }

    private void dropOre(RegistrySupplier<Block> block, RegistrySupplier<Item> item) {
        add(block.get(), (b) -> createSilverOreDrop(b, item.get()));
    }

    /**
     * Like vanilla {@code createOreDrop} but with a 1-3 count range instead of a
     * flat 1, so silver is plentiful enough to be a viable building/crafting
     * material (player feedback). Modelled 1:1 on Mojang's own
     * {@code createCopperOreDrops}: silk-touch still drops the ore block, the
     * Fortune ore bonus still applies on top of the range, and explosion decay
     * is preserved. Datagen output is loader-neutral
     * ({@code data/thesilverage/loot_table/blocks/*.json}), so both NeoForge
     * and Fabric pick up the new drops identically.
     */
    private LootTable.Builder createSilverOreDrop(Block block, Item item) {
        HolderLookup.RegistryLookup<Enchantment> enchantments =
                this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(block, this.applyExplosionDecay(block,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(ApplyBonusCount.addOreBonusCount(
                                enchantments.getOrThrow(Enchantments.FORTUNE)))));
    }
}
