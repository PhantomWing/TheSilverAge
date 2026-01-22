package com.phantomwing.thesilverage.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.utils.BlockUtils;
import com.phantomwing.thesilverage.utils.ItemUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

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
        trimmedArmorItem(ModItems.SILVER_HELMET);
        trimmedArmorItem(ModItems.SILVER_CHESTPLATE);
        trimmedArmorItem(ModItems.SILVER_LEGGINGS);
        trimmedArmorItem(ModItems.SILVER_BOOTS);

        // Blocks
        blockItem(ModBlocks.SILVER_ORE);
        blockItem(ModBlocks.DEEPSLATE_SILVER_ORE);
        blockItem(ModBlocks.RAW_SILVER_BLOCK);

        blockItem(ModBlocks.SILVER_BLOCK);
        blockItem(ModBlocks.EXPOSED_SILVER);
        blockItem(ModBlocks.WEATHERED_SILVER);
        blockItem(ModBlocks.OXIDIZED_SILVER);
        blockItem(ModBlocks.WAXED_SILVER_BLOCK);
        blockItem(ModBlocks.WAXED_EXPOSED_SILVER);
        blockItem(ModBlocks.WAXED_WEATHERED_SILVER);
        blockItem(ModBlocks.WAXED_OXIDIZED_SILVER);

        blockItem(ModBlocks.CUT_SILVER);
        blockItem(ModBlocks.EXPOSED_CUT_SILVER);
        blockItem(ModBlocks.WEATHERED_CUT_SILVER);
        blockItem(ModBlocks.OXIDIZED_CUT_SILVER);
        blockItem(ModBlocks.WAXED_CUT_SILVER);
        blockItem(ModBlocks.WAXED_EXPOSED_CUT_SILVER);
        blockItem(ModBlocks.WAXED_WEATHERED_CUT_SILVER);
        blockItem(ModBlocks.WAXED_OXIDIZED_CUT_SILVER);

        blockItem(ModBlocks.CUT_SILVER_SLAB);
        blockItem(ModBlocks.EXPOSED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.WEATHERED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.OXIDIZED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.WAXED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB);
        blockItem(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB);

        blockItem(ModBlocks.CUT_SILVER_STAIRS);
        blockItem(ModBlocks.EXPOSED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.WEATHERED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.WAXED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS);
        blockItem(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS);

        blockItem(ModBlocks.CHISELED_SILVER);
        blockItem(ModBlocks.EXPOSED_CHISELED_SILVER);
        blockItem(ModBlocks.WEATHERED_CHISELED_SILVER);
        blockItem(ModBlocks.OXIDIZED_CHISELED_SILVER);
        blockItem(ModBlocks.WAXED_CHISELED_SILVER);
        blockItem(ModBlocks.WAXED_EXPOSED_CHISELED_SILVER);
        blockItem(ModBlocks.WAXED_WEATHERED_CHISELED_SILVER);
        blockItem(ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER);

        blockItem(ModBlocks.SILVER_GRATE);
        blockItem(ModBlocks.EXPOSED_SILVER_GRATE);
        blockItem(ModBlocks.WEATHERED_SILVER_GRATE);
        blockItem(ModBlocks.OXIDIZED_SILVER_GRATE);
        blockItem(ModBlocks.WAXED_SILVER_GRATE);
        blockItem(ModBlocks.WAXED_EXPOSED_SILVER_GRATE);
        blockItem(ModBlocks.WAXED_WEATHERED_SILVER_GRATE);
        blockItem(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE);

        blockItem2D(ModBlocks.SILVER_DOOR);
        blockItem(ModBlocks.SILVER_TRAPDOOR, "bottom");
    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(DeferredItem<Item> itemDeferredItem) {
        final String MOD_ID = TheSilverAge.MOD_ID; // Change this to your mod id

        if(itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemDeferredItem.getId().getPath()));
            });
        }
    }

    // A simple item with a model generated from its sprite.
    private <T extends Item> void simpleItem(DeferredItem<T> item) {
        withExistingParent(ItemUtils.getName(item.get()), ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", ItemUtils.getItemResourceLocation(item.get()));
    }

    private <T extends Block> void blockItem2D(DeferredBlock<T> block) {
        withExistingParent(ItemUtils.getName(block.get()), ResourceLocation.withDefaultNamespace("item/generated"))
                .texture("layer0", ItemUtils.getItemResourceLocation(block.get()));
    }

    private <T extends Item> void handheldItem(DeferredItem<T> item) {
        withExistingParent(ItemUtils.getName(item.get()), ResourceLocation.withDefaultNamespace("item/handheld"))
                .texture("layer0", ItemUtils.getItemResourceLocation(item.get()));
    }

    private <T extends Block> void blockItem(DeferredBlock<T> block) {
        this.withExistingParent(BlockUtils.getNameWithNamespace(block.get()), BlockUtils.getBlockResourceLocation(block.get()));
    }

    private <T extends Block> void blockItem(DeferredBlock<T> block, String suffix) {
        this.withExistingParent(BlockUtils.getNameWithNamespace(block.get()), BlockUtils.getBlockResourceLocation(block.get(), suffix));
    }
}
