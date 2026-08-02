package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.armor.ModTrimMaterials;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.neoforge.item.ModItemProperties;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.neoforge.utils.BlockUtils;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import dev.architectury.registry.registries.RegistrySupplier;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TheSilverAge.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Items
        simpleItem(ModItems.SILVER_NUGGET);
        simpleItem(ModItems.SILVER_INGOT);
        simpleItem(ModItems.SILVER_SHEET);
        simpleItem(ModItems.RAW_SILVER);

        // Silver tools
        handheldItem(ModItems.SILVER_SWORD);
        handheldItem(ModItems.SILVER_SHOVEL);
        handheldItem(ModItems.SILVER_PICKAXE);
        handheldItem(ModItems.SILVER_AXE);
        handheldItem(ModItems.SILVER_HOE);
        handheldItem(ModItems.SILVER_KNIFE); // Farmer's Delight compat

        // Silver armor
        armorItem(ModItems.SILVER_HELMET);
        armorItem(ModItems.SILVER_CHESTPLATE);
        armorItem(ModItems.SILVER_LEGGINGS);
        armorItem(ModItems.SILVER_BOOTS);
        simpleItem(ModItems.SILVER_HORSE_ARMOR);

        // Utility items
        lunarClockItem(ModItems.MOON_DIAL);

        // Extend existing vanilla armor to support Silver as a trim material
        addModTrimsToArmorItem(Items.LEATHER_HELMET, true);
        addModTrimsToArmorItem(Items.LEATHER_CHESTPLATE, true);
        addModTrimsToArmorItem(Items.LEATHER_LEGGINGS, true);
        addModTrimsToArmorItem(Items.LEATHER_BOOTS, true);
        addModTrimsToArmorItem(Items.CHAINMAIL_HELMET);
        addModTrimsToArmorItem(Items.CHAINMAIL_CHESTPLATE);
        addModTrimsToArmorItem(Items.CHAINMAIL_LEGGINGS);
        addModTrimsToArmorItem(Items.CHAINMAIL_BOOTS);
        addModTrimsToArmorItem(Items.IRON_HELMET);
        addModTrimsToArmorItem(Items.IRON_CHESTPLATE);
        addModTrimsToArmorItem(Items.IRON_LEGGINGS);
        addModTrimsToArmorItem(Items.IRON_BOOTS);
        addModTrimsToArmorItem(Items.GOLDEN_HELMET);
        addModTrimsToArmorItem(Items.GOLDEN_CHESTPLATE);
        addModTrimsToArmorItem(Items.GOLDEN_LEGGINGS);
        addModTrimsToArmorItem(Items.GOLDEN_BOOTS);
        addModTrimsToArmorItem(Items.DIAMOND_HELMET);
        addModTrimsToArmorItem(Items.DIAMOND_CHESTPLATE);
        addModTrimsToArmorItem(Items.DIAMOND_LEGGINGS);
        addModTrimsToArmorItem(Items.DIAMOND_BOOTS);
        addModTrimsToArmorItem(Items.NETHERITE_HELMET);
        addModTrimsToArmorItem(Items.NETHERITE_CHESTPLATE);
        addModTrimsToArmorItem(Items.NETHERITE_LEGGINGS);
        addModTrimsToArmorItem(Items.NETHERITE_BOOTS);

        // Redstone blocks
        blockItem(ModBlocks.MOON_PHASE_DETECTOR);

        // Ores and Blocks
        blockItem(ModBlocks.SILVER_ORE);
        blockItem(ModBlocks.DEEPSLATE_SILVER_ORE);
        blockItem(ModBlocks.RAW_SILVER_BLOCK);

        // Block of Silver
        blockItem(ModBlocks.SILVER_BLOCK);
        blockItem(ModBlocks.EXPOSED_SILVER);
        blockItem(ModBlocks.WEATHERED_SILVER);
        blockItem(ModBlocks.OXIDIZED_SILVER);
        blockItem(ModBlocks.WAXED_SILVER_BLOCK);
        blockItem(ModBlocks.WAXED_EXPOSED_SILVER);
        blockItem(ModBlocks.WAXED_WEATHERED_SILVER);
        blockItem(ModBlocks.WAXED_OXIDIZED_SILVER);

        // Cut Silver
        blockItem(ModBlocks.CUT_SILVER);
        blockItem(ModBlocks.EXPOSED_CUT_SILVER);
        blockItem(ModBlocks.WEATHERED_CUT_SILVER);
        blockItem(ModBlocks.OXIDIZED_CUT_SILVER);
        blockItem(ModBlocks.WAXED_CUT_SILVER);
        blockItem(ModBlocks.WAXED_EXPOSED_CUT_SILVER);
        blockItem(ModBlocks.WAXED_WEATHERED_CUT_SILVER);
        blockItem(ModBlocks.WAXED_OXIDIZED_CUT_SILVER);

        // Silver Bricks
        blockItem(ModBlocks.SILVER_BRICKS);
        blockItem(ModBlocks.EXPOSED_SILVER_BRICKS);
        blockItem(ModBlocks.WEATHERED_SILVER_BRICKS);
        blockItem(ModBlocks.OXIDIZED_SILVER_BRICKS);
        blockItem(ModBlocks.WAXED_SILVER_BRICKS);
        blockItem(ModBlocks.WAXED_EXPOSED_SILVER_BRICKS);
        blockItem(ModBlocks.WAXED_WEATHERED_SILVER_BRICKS);
        blockItem(ModBlocks.WAXED_OXIDIZED_SILVER_BRICKS);

        // Silver Brick Slab
        blockItem(ModBlocks.SILVER_BRICK_SLAB);
        blockItem(ModBlocks.EXPOSED_SILVER_BRICK_SLAB);
        blockItem(ModBlocks.WEATHERED_SILVER_BRICK_SLAB);
        blockItem(ModBlocks.OXIDIZED_SILVER_BRICK_SLAB);
        blockItem(ModBlocks.WAXED_SILVER_BRICK_SLAB);
        blockItem(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_SLAB);
        blockItem(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_SLAB);
        blockItem(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_SLAB);

        // Silver Brick Stairs
        blockItem(ModBlocks.SILVER_BRICK_STAIRS);
        blockItem(ModBlocks.EXPOSED_SILVER_BRICK_STAIRS);
        blockItem(ModBlocks.WEATHERED_SILVER_BRICK_STAIRS);
        blockItem(ModBlocks.OXIDIZED_SILVER_BRICK_STAIRS);
        blockItem(ModBlocks.WAXED_SILVER_BRICK_STAIRS);
        blockItem(ModBlocks.WAXED_EXPOSED_SILVER_BRICK_STAIRS);
        blockItem(ModBlocks.WAXED_WEATHERED_SILVER_BRICK_STAIRS);
        blockItem(ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_STAIRS);

        // Cut Silver Slab
        blockItem(ModBlocks.CUT_SILVER_SLAB);
        blockItem(ModBlocks.EXPOSED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.WEATHERED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.OXIDIZED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.WAXED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB);

        // Cut Silver Stairs
        blockItem(ModBlocks.CUT_SILVER_STAIRS);
        blockItem(ModBlocks.EXPOSED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.WEATHERED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.WAXED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS);

        // Chiseled Silver
        blockItem(ModBlocks.CHISELED_SILVER);
        blockItem(ModBlocks.EXPOSED_CHISELED_SILVER);
        blockItem(ModBlocks.WEATHERED_CHISELED_SILVER);
        blockItem(ModBlocks.OXIDIZED_CHISELED_SILVER);
        blockItem(ModBlocks.WAXED_CHISELED_SILVER);
        blockItem(ModBlocks.WAXED_EXPOSED_CHISELED_SILVER);
        blockItem(ModBlocks.WAXED_WEATHERED_CHISELED_SILVER);
        blockItem(ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER);

        // Silver Pillar
        blockItem(ModBlocks.SILVER_PILLAR);
        blockItem(ModBlocks.EXPOSED_SILVER_PILLAR);
        blockItem(ModBlocks.WEATHERED_SILVER_PILLAR);
        blockItem(ModBlocks.OXIDIZED_SILVER_PILLAR);
        blockItem(ModBlocks.WAXED_SILVER_PILLAR);
        blockItem(ModBlocks.WAXED_EXPOSED_SILVER_PILLAR);
        blockItem(ModBlocks.WAXED_WEATHERED_SILVER_PILLAR);
        blockItem(ModBlocks.WAXED_OXIDIZED_SILVER_PILLAR);

        // Silver Grate
        blockItem(ModBlocks.SILVER_GRATE);
        blockItem(ModBlocks.EXPOSED_SILVER_GRATE);
        blockItem(ModBlocks.WEATHERED_SILVER_GRATE);
        blockItem(ModBlocks.OXIDIZED_SILVER_GRATE);
        blockItem(ModBlocks.WAXED_SILVER_GRATE);
        blockItem(ModBlocks.WAXED_EXPOSED_SILVER_GRATE);
        blockItem(ModBlocks.WAXED_WEATHERED_SILVER_GRATE);
        blockItem(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE);

        // Silver Bulb
        blockItem(ModBlocks.SILVER_BULB);
        blockItem(ModBlocks.EXPOSED_SILVER_BULB);
        blockItem(ModBlocks.WEATHERED_SILVER_BULB);
        blockItem(ModBlocks.OXIDIZED_SILVER_BULB);
        blockItem(ModBlocks.WAXED_SILVER_BULB);
        blockItem(ModBlocks.WAXED_EXPOSED_SILVER_BULB);
        blockItem(ModBlocks.WAXED_WEATHERED_SILVER_BULB);
        blockItem(ModBlocks.WAXED_OXIDIZED_SILVER_BULB);

        // Silver Trapdoor
        trapdoorItem(ModBlocks.SILVER_TRAPDOOR);
        trapdoorItem(ModBlocks.EXPOSED_SILVER_TRAPDOOR);
        trapdoorItem(ModBlocks.WEATHERED_SILVER_TRAPDOOR);
        trapdoorItem(ModBlocks.OXIDIZED_SILVER_TRAPDOOR);
        trapdoorItem(ModBlocks.WAXED_SILVER_TRAPDOOR);
        trapdoorItem(ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR);
        trapdoorItem(ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR);
        trapdoorItem(ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR);

        // Silver Door
        blockItem2D(ModBlocks.SILVER_DOOR);
        blockItem2D(ModBlocks.EXPOSED_SILVER_DOOR);
        blockItem2D(ModBlocks.WEATHERED_SILVER_DOOR);
        blockItem2D(ModBlocks.OXIDIZED_SILVER_DOOR);
        blockItem2DWithTexture(ModBlocks.WAXED_SILVER_DOOR, ModBlocks.SILVER_DOOR);
        blockItem2DWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_DOOR, ModBlocks.EXPOSED_SILVER_DOOR);
        blockItem2DWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_DOOR, ModBlocks.WEATHERED_SILVER_DOOR);
        blockItem2DWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_DOOR, ModBlocks.OXIDIZED_SILVER_DOOR);

        // Silver Lantern — flat sprite from the item texture (matches vanilla lanterns).
        blockItem2D(ModBlocks.SILVER_LANTERN);
        blockItem2D(ModBlocks.EXPOSED_SILVER_LANTERN);
        blockItem2D(ModBlocks.WEATHERED_SILVER_LANTERN);
        blockItem2D(ModBlocks.OXIDIZED_SILVER_LANTERN);
        blockItem2DWithTexture(ModBlocks.WAXED_SILVER_LANTERN, ModBlocks.SILVER_LANTERN);
        blockItem2DWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_LANTERN, ModBlocks.EXPOSED_SILVER_LANTERN);
        blockItem2DWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_LANTERN, ModBlocks.WEATHERED_SILVER_LANTERN);
        blockItem2DWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_LANTERN, ModBlocks.OXIDIZED_SILVER_LANTERN);

        // Silver Chain
        blockItem2D(ModBlocks.SILVER_CHAIN);
        blockItem2D(ModBlocks.EXPOSED_SILVER_CHAIN);
        blockItem2D(ModBlocks.WEATHERED_SILVER_CHAIN);
        blockItem2D(ModBlocks.OXIDIZED_SILVER_CHAIN);
        blockItem2DWithTexture(ModBlocks.WAXED_SILVER_CHAIN, ModBlocks.SILVER_CHAIN);
        blockItem2DWithTexture(ModBlocks.WAXED_EXPOSED_SILVER_CHAIN, ModBlocks.EXPOSED_SILVER_CHAIN);
        blockItem2DWithTexture(ModBlocks.WAXED_WEATHERED_SILVER_CHAIN, ModBlocks.WEATHERED_SILVER_CHAIN);
        blockItem2DWithTexture(ModBlocks.WAXED_OXIDIZED_SILVER_CHAIN, ModBlocks.OXIDIZED_SILVER_CHAIN);

        // Silver Bars + Torch — flat sprite from the BLOCK texture (no separate item sprite).
        blockItem2DFromBlockTexture(ModBlocks.SILVER_BARS, ModBlocks.SILVER_BARS);
        blockItem2DFromBlockTexture(ModBlocks.EXPOSED_SILVER_BARS, ModBlocks.EXPOSED_SILVER_BARS);
        blockItem2DFromBlockTexture(ModBlocks.WEATHERED_SILVER_BARS, ModBlocks.WEATHERED_SILVER_BARS);
        blockItem2DFromBlockTexture(ModBlocks.OXIDIZED_SILVER_BARS, ModBlocks.OXIDIZED_SILVER_BARS);
        blockItem2DFromBlockTexture(ModBlocks.WAXED_SILVER_BARS, ModBlocks.SILVER_BARS);
        blockItem2DFromBlockTexture(ModBlocks.WAXED_EXPOSED_SILVER_BARS, ModBlocks.EXPOSED_SILVER_BARS);
        blockItem2DFromBlockTexture(ModBlocks.WAXED_WEATHERED_SILVER_BARS, ModBlocks.WEATHERED_SILVER_BARS);
        blockItem2DFromBlockTexture(ModBlocks.WAXED_OXIDIZED_SILVER_BARS, ModBlocks.OXIDIZED_SILVER_BARS);

        blockItem2DFromBlockTexture(ModBlocks.SILVER_TORCH, ModBlocks.SILVER_TORCH);
    }

    // item/generated sprite taken from the block texture (for blocks with no dedicated item sprite).
    private <T extends Block, U extends Block> void blockItem2DFromBlockTexture(RegistrySupplier<T> block, RegistrySupplier<U> textureBlock) {
        withExistingParent(BlockUtils.getName(block.get()), ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", BlockUtils.getBlockResourceLocation(textureBlock.get()));
    }

    // A simple item with a model generated from its sprite.
    private <T extends Item> void simpleItem(RegistrySupplier<T> item) {
        withExistingParent(ItemUtils.getName(item.get()), ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", ItemUtils.getItemResourceLocation(item.get()));
    }

    private <T extends Block> void blockItem2D(RegistrySupplier<T> block) {
        withExistingParent(ItemUtils.getName(block.get()), ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", ItemUtils.getItemResourceLocation(block.get()));
    }

    private <T extends Block> void blockItem2DWithTexture(RegistrySupplier<T> block, RegistrySupplier<T> textureBlock) {
        withExistingParent(ItemUtils.getName(block.get()), ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", ItemUtils.getItemResourceLocation(textureBlock.get()));
    }

    private <T extends Item> void handheldItem(RegistrySupplier<T> item) {
        withExistingParent(ItemUtils.getName(item.get()), ResourceLocation.withDefaultNamespace("item/handheld"))
                .texture("layer0", ItemUtils.getItemResourceLocation(item.get()));
    }

    private <T extends Block> void blockItem(RegistrySupplier<T> block) {
        this.withExistingParent(BlockUtils.getNameWithNamespace(block.get()), BlockUtils.getBlockResourceLocation(block.get()));
    }

    private <T extends Block> void blockItem(RegistrySupplier<T> block, String suffix) {
        this.withExistingParent(BlockUtils.getNameWithNamespace(block.get()), BlockUtils.getBlockResourceLocation(block.get(), suffix));
    }

    private <T extends Block> void trapdoorItem(RegistrySupplier<T> block) {
        blockItem(block, "bottom");
    }


    private void lunarClockItem(RegistrySupplier<Item> deferredItem) {
        Item item = deferredItem.get();
        String itemPath = item.toString();

        for (int i = 0; i <= 15; i++) {
            String modelName = itemPath + "_" + i;
            ResourceLocation phaseModelLoc = ResourceLocation.parse(modelName);

            ResourceLocation phaseTexture = ItemUtils.getItemResourceLocation(item, i + "");
            ResourceLocation itemLoc = ItemUtils.getItemResourceLocation(item);

            // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will avoid an IllegalArgumentException
            existingFileHelper.trackGenerated(phaseTexture, PackType.CLIENT_RESOURCES, ".png", "textures");

            // Moon phase model variant.
            getBuilder(modelName)
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", itemLoc.getNamespace() + ":" + itemLoc.getPath())
                    .texture("layer1", phaseTexture);

            // Base item model, containing an override for each moon phase.
            this.withExistingParent(itemPath, mcLoc("item/generated"))
                    .override()
                    .model(new ModelFile.UncheckedModelFile(phaseModelLoc.getNamespace() + ":item/" + phaseModelLoc.getPath()))
                    .predicate(ModItemProperties.MOON_PHASE, i / 100f).end()
                    .texture("layer0", ItemUtils.getItemResourceLocation(item));
        }
    }


    /** Generate new armor models for all TrimMaterials. */
    private void armorItem(RegistrySupplier<Item> item) {
        ModTrimMaterials.ALL_TRIM_MATERIALS.forEach((trimMaterial, trimValue) -> {
            generateTrimArmorModel(item.get(), trimMaterial, false);
        });

        generateBaseArmorModel(item.get(), false);
    }

    /** Generate new armor models for a specific TrimMaterial. */
    private void addModTrimsToArmorItem(ItemLike item) {
        this.addModTrimsToArmorItem(item, false);
    }

    /** Generate new armor models for a specific TrimMaterial. */
    private void addModTrimsToArmorItem(ItemLike item, boolean hasOverlay) {
        ModTrimMaterials.MOD_TRIM_MATERIALS.forEach((trimMaterial, trimValue) -> {
            generateTrimArmorModel(item, trimMaterial, hasOverlay);
        });

        generateBaseArmorModel(item, hasOverlay);
    }

    private void generateTrimArmorModel(ItemLike item, ResourceKey<TrimMaterial> trimMaterial, boolean hasOverlay) {
        if (item.asItem() instanceof ArmorItem armorItem) {
            String armorType = switch (armorItem.getEquipmentSlot()) {
                case HEAD -> "helmet";
                case CHEST -> "chestplate";
                case LEGS -> "leggings";
                case FEET -> "boots";
                default -> "";
            };

            String trimTexturePath = "trims/items/" + armorType + "_trim_" + ItemUtils.getTrimNameForArmor(armorItem, trimMaterial);
            String trimModelName = ItemUtils.getArmorTrimModelName(armorItem, trimMaterial);
            ResourceLocation trimTextureResLoc = ResourceLocation.parse(trimTexturePath); // minecraft namespace

            // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will avoid an IllegalArgumentException
            existingFileHelper.trackGenerated(trimTextureResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

            // Trimmed armorItem file.
            var itemResLoc = ItemUtils.getItemResourceLocation(armorItem);
            var builder = getBuilder(trimModelName)
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", itemResLoc);

            // If the trim material has an overlay texture, add it as layer1. Otherwise, just use the trim texture as layer1.
            if (hasOverlay) {
                builder.texture("layer1", itemResLoc + "_overlay").texture("layer2", trimTextureResLoc);
            } else {
                builder.texture("layer1", trimTextureResLoc);
            }
        }
    }

    private void generateBaseArmorModel(ItemLike item, boolean hasOverlay) {
        if (item.asItem() instanceof ArmorItem armorItem) {
            String armorItemPath = armorItem.toString();

            ModTrimMaterials.ALL_TRIM_MATERIALS.forEach((trimMaterial, trimValue) -> {
                String trimModelName = ItemUtils.getArmorTrimModelName(armorItem, trimMaterial);
                ResourceLocation trimModelResLoc = ResourceLocation.parse(trimModelName);

                var itemResLoc = ItemUtils.getItemResourceLocation(armorItem);
                var builder = this.withExistingParent(armorItemPath, mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimModelResLoc.getNamespace() + ":item/" + trimModelResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0", itemResLoc);

                if (hasOverlay) {
                    builder.texture("layer1", itemResLoc + "_overlay");
                }
            });
        }
    }
}
