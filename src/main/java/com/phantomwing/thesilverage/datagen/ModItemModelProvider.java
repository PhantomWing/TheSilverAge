package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.utils.BlockUtils;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheSilverAge.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Items
        simpleItem(ModItems.SILVER_NUGGET);
        simpleItem(ModItems.SILVER_INGOT);
        simpleItem(ModItems.RAW_SILVER);

        // Silver tools
        handheldItem(ModItems.SILVER_SWORD);
        handheldItem(ModItems.SILVER_SHOVEL);
        handheldItem(ModItems.SILVER_PICKAXE);
        handheldItem(ModItems.SILVER_AXE);
        handheldItem(ModItems.SILVER_HOE);

        // Silver armor
        simpleItem(ModItems.SILVER_HELMET);
        simpleItem(ModItems.SILVER_CHESTPLATE);
        simpleItem(ModItems.SILVER_LEGGINGS);
        simpleItem(ModItems.SILVER_BOOTS);

        // Blocks
        simpleBlock(ModBlocks.SILVER_BLOCK);
        simpleBlock(ModBlocks.RAW_SILVER_BLOCK);
        simpleBlock(ModBlocks.SILVER_ORE);
        simpleBlock(ModBlocks.DEEPSLATE_SILVER_ORE);
    }

    // A simple item with a model generated from its sprite.
    private <T extends Item> void simpleItem(DeferredItem<T> item) {
        withExistingParent(ItemUtils.getName(item.get()), ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", ItemUtils.getItemResourceLocation(item.get()));
    }

    private <T extends Item> void handheldItem(DeferredItem<T> item) {
        withExistingParent(ItemUtils.getName(item.get()), ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", ItemUtils.getItemResourceLocation(item.get()));
    }

    private <T extends Block> void simpleBlock(DeferredBlock<T> block) {
        this.withExistingParent(BlockUtils.getNameWithNamespace(block.get()), BlockUtils.getBlockResourceLocation(block.get()));
    }
}
