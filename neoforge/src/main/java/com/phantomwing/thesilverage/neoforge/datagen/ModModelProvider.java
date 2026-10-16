package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.armor.ModTrimMaterials;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.custom.MoonPhaseDetectorBlock;
import com.phantomwing.thesilverage.client.ModItemProperties;
import com.phantomwing.thesilverage.item.ModItems;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/** Block + item model datagen. Waxed variants reuse their unwaxed counterpart's textures. */
public class ModModelProvider extends ModelProvider {
    public ModModelProvider(net.minecraft.data.PackOutput output) {
        super(output, TheSilverAge.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators bmg, ItemModelGenerators img) {
        // ---------- Ores + raw storage ----------
        cube(bmg, ModBlocks.SILVER_ORE);
        cube(bmg, ModBlocks.DEEPSLATE_SILVER_ORE);
        cube(bmg, ModBlocks.RAW_SILVER_BLOCK);

        // ---------- Block of Silver (no slab/stairs) ----------
        cube(bmg, ModBlocks.SILVER_BLOCK);
        cube(bmg, ModBlocks.EXPOSED_SILVER);
        cube(bmg, ModBlocks.WEATHERED_SILVER);
        cube(bmg, ModBlocks.OXIDIZED_SILVER);
        cubeReusing(bmg, ModBlocks.WAXED_SILVER_BLOCK, ModBlocks.SILVER_BLOCK);
        cubeReusing(bmg, ModBlocks.WAXED_EXPOSED_SILVER, ModBlocks.EXPOSED_SILVER);
        cubeReusing(bmg, ModBlocks.WAXED_WEATHERED_SILVER, ModBlocks.WEATHERED_SILVER);
        cubeReusing(bmg, ModBlocks.WAXED_OXIDIZED_SILVER, ModBlocks.OXIDIZED_SILVER);

        // ---------- Cut Silver family (cube + slab + stairs) ----------
        family(bmg, ModBlocks.CUT_SILVER, ModBlocks.CUT_SILVER, ModBlocks.CUT_SILVER_SLAB, ModBlocks.CUT_SILVER_STAIRS);
        family(bmg, ModBlocks.EXPOSED_CUT_SILVER, ModBlocks.EXPOSED_CUT_SILVER, ModBlocks.EXPOSED_CUT_SILVER_SLAB, ModBlocks.EXPOSED_CUT_SILVER_STAIRS);
        family(bmg, ModBlocks.WEATHERED_CUT_SILVER, ModBlocks.WEATHERED_CUT_SILVER, ModBlocks.WEATHERED_CUT_SILVER_SLAB, ModBlocks.WEATHERED_CUT_SILVER_STAIRS);
        family(bmg, ModBlocks.OXIDIZED_CUT_SILVER, ModBlocks.OXIDIZED_CUT_SILVER, ModBlocks.OXIDIZED_CUT_SILVER_SLAB, ModBlocks.OXIDIZED_CUT_SILVER_STAIRS);
        family(bmg, ModBlocks.WAXED_CUT_SILVER, ModBlocks.CUT_SILVER, ModBlocks.WAXED_CUT_SILVER_SLAB, ModBlocks.WAXED_CUT_SILVER_STAIRS);
        family(bmg, ModBlocks.WAXED_EXPOSED_CUT_SILVER, ModBlocks.EXPOSED_CUT_SILVER, ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB, ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS);
        family(bmg, ModBlocks.WAXED_WEATHERED_CUT_SILVER, ModBlocks.WEATHERED_CUT_SILVER, ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB, ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS);
        family(bmg, ModBlocks.WAXED_OXIDIZED_CUT_SILVER, ModBlocks.OXIDIZED_CUT_SILVER, ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB, ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS);

        // ---------- Silver Bricks family (cube + slab + stairs) ----------
        family(bmg, ModBlocks.SILVER_BRICKS, ModBlocks.SILVER_BRICKS, ModBlocks.SILVER_BRICK_SLAB, ModBlocks.SILVER_BRICK_STAIRS);
        family(bmg, ModBlocks.EXPOSED_SILVER_BRICKS, ModBlocks.EXPOSED_SILVER_BRICKS, ModBlocks.EXPOSED_SILVER_BRICK_SLAB, ModBlocks.EXPOSED_SILVER_BRICK_STAIRS);
        family(bmg, ModBlocks.WEATHERED_SILVER_BRICKS, ModBlocks.WEATHERED_SILVER_BRICKS, ModBlocks.WEATHERED_SILVER_BRICK_SLAB, ModBlocks.WEATHERED_SILVER_BRICK_STAIRS);
        family(bmg, ModBlocks.OXIDIZED_SILVER_BRICKS, ModBlocks.OXIDIZED_SILVER_BRICKS, ModBlocks.OXIDIZED_SILVER_BRICK_SLAB, ModBlocks.OXIDIZED_SILVER_BRICK_STAIRS);
        family(bmg, ModBlocks.WAXED_SILVER_BRICKS, ModBlocks.SILVER_BRICKS, ModBlocks.WAXED_SILVER_BRICK_SLAB, ModBlocks.WAXED_SILVER_BRICK_STAIRS);
        family(bmg, ModBlocks.WAXED_EXPOSED_SILVER_BRICKS, ModBlocks.EXPOSED_SILVER_BRICKS, ModBlocks.WAXED_EXPOSED_SILVER_BRICK_SLAB, ModBlocks.WAXED_EXPOSED_SILVER_BRICK_STAIRS);
        family(bmg, ModBlocks.WAXED_WEATHERED_SILVER_BRICKS, ModBlocks.WEATHERED_SILVER_BRICKS, ModBlocks.WAXED_WEATHERED_SILVER_BRICK_SLAB, ModBlocks.WAXED_WEATHERED_SILVER_BRICK_STAIRS);
        family(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_BRICKS, ModBlocks.OXIDIZED_SILVER_BRICKS, ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_SLAB, ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_STAIRS);

        // ---------- Chiseled (horizontal-facing) ----------
        chiseled(bmg, ModBlocks.CHISELED_SILVER);
        chiseled(bmg, ModBlocks.EXPOSED_CHISELED_SILVER);
        chiseled(bmg, ModBlocks.WEATHERED_CHISELED_SILVER);
        chiseled(bmg, ModBlocks.OXIDIZED_CHISELED_SILVER);
        chiseledReusing(bmg, ModBlocks.WAXED_CHISELED_SILVER, ModBlocks.CHISELED_SILVER);
        chiseledReusing(bmg, ModBlocks.WAXED_EXPOSED_CHISELED_SILVER, ModBlocks.EXPOSED_CHISELED_SILVER);
        chiseledReusing(bmg, ModBlocks.WAXED_WEATHERED_CHISELED_SILVER, ModBlocks.WEATHERED_CHISELED_SILVER);
        chiseledReusing(bmg, ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER, ModBlocks.OXIDIZED_CHISELED_SILVER);

        // ---------- Pillars (axis-aligned column) ----------
        pillar(bmg, ModBlocks.SILVER_PILLAR, ModBlocks.SILVER_PILLAR);
        pillar(bmg, ModBlocks.EXPOSED_SILVER_PILLAR, ModBlocks.EXPOSED_SILVER_PILLAR);
        pillar(bmg, ModBlocks.WEATHERED_SILVER_PILLAR, ModBlocks.WEATHERED_SILVER_PILLAR);
        pillar(bmg, ModBlocks.OXIDIZED_SILVER_PILLAR, ModBlocks.OXIDIZED_SILVER_PILLAR);
        pillar(bmg, ModBlocks.WAXED_SILVER_PILLAR, ModBlocks.SILVER_PILLAR);
        pillar(bmg, ModBlocks.WAXED_EXPOSED_SILVER_PILLAR, ModBlocks.EXPOSED_SILVER_PILLAR);
        pillar(bmg, ModBlocks.WAXED_WEATHERED_SILVER_PILLAR, ModBlocks.WEATHERED_SILVER_PILLAR);
        pillar(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_PILLAR, ModBlocks.OXIDIZED_SILVER_PILLAR);

        // ---------- Grates (transparent cube; render layer set client-side) ----------
        cube(bmg, ModBlocks.SILVER_GRATE);
        cube(bmg, ModBlocks.EXPOSED_SILVER_GRATE);
        cube(bmg, ModBlocks.WEATHERED_SILVER_GRATE);
        cube(bmg, ModBlocks.OXIDIZED_SILVER_GRATE);
        cubeReusing(bmg, ModBlocks.WAXED_SILVER_GRATE, ModBlocks.SILVER_GRATE);
        cubeReusing(bmg, ModBlocks.WAXED_EXPOSED_SILVER_GRATE, ModBlocks.EXPOSED_SILVER_GRATE);
        cubeReusing(bmg, ModBlocks.WAXED_WEATHERED_SILVER_GRATE, ModBlocks.WEATHERED_SILVER_GRATE);
        cubeReusing(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_GRATE, ModBlocks.OXIDIZED_SILVER_GRATE);

        // ---------- Bulbs (LIT/POWERED states) ----------
        bulb(bmg, ModBlocks.SILVER_BULB);
        bulb(bmg, ModBlocks.EXPOSED_SILVER_BULB);
        bulb(bmg, ModBlocks.WEATHERED_SILVER_BULB);
        bulb(bmg, ModBlocks.OXIDIZED_SILVER_BULB);
        bulbReusing(bmg, ModBlocks.WAXED_SILVER_BULB, ModBlocks.SILVER_BULB);
        bulbReusing(bmg, ModBlocks.WAXED_EXPOSED_SILVER_BULB, ModBlocks.EXPOSED_SILVER_BULB);
        bulbReusing(bmg, ModBlocks.WAXED_WEATHERED_SILVER_BULB, ModBlocks.WEATHERED_SILVER_BULB);
        bulbReusing(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_BULB, ModBlocks.OXIDIZED_SILVER_BULB);

        // ---------- Trapdoors / Doors (render layer set client-side) ----------
        trapdoor(bmg, ModBlocks.SILVER_TRAPDOOR);
        trapdoor(bmg, ModBlocks.EXPOSED_SILVER_TRAPDOOR);
        trapdoor(bmg, ModBlocks.WEATHERED_SILVER_TRAPDOOR);
        trapdoor(bmg, ModBlocks.OXIDIZED_SILVER_TRAPDOOR);
        trapdoorReusing(bmg, ModBlocks.WAXED_SILVER_TRAPDOOR, ModBlocks.SILVER_TRAPDOOR);
        trapdoorReusing(bmg, ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR, ModBlocks.EXPOSED_SILVER_TRAPDOOR);
        trapdoorReusing(bmg, ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR, ModBlocks.WEATHERED_SILVER_TRAPDOOR);
        trapdoorReusing(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR, ModBlocks.OXIDIZED_SILVER_TRAPDOOR);

        door(bmg, ModBlocks.SILVER_DOOR);
        door(bmg, ModBlocks.EXPOSED_SILVER_DOOR);
        door(bmg, ModBlocks.WEATHERED_SILVER_DOOR);
        door(bmg, ModBlocks.OXIDIZED_SILVER_DOOR);
        doorReusing(bmg, ModBlocks.WAXED_SILVER_DOOR, ModBlocks.SILVER_DOOR);
        doorReusing(bmg, ModBlocks.WAXED_EXPOSED_SILVER_DOOR, ModBlocks.EXPOSED_SILVER_DOOR);
        doorReusing(bmg, ModBlocks.WAXED_WEATHERED_SILVER_DOOR, ModBlocks.WEATHERED_SILVER_DOOR);
        doorReusing(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_DOOR, ModBlocks.OXIDIZED_SILVER_DOOR);

        // ---------- Moon Phase Detector (daylight-detector style, INVERTED top swap) ----------
        moonPhaseDetector(bmg);

        // ============================ ITEMS ============================
        flat(img, ModItems.RAW_SILVER);
        flat(img, ModItems.SILVER_INGOT);
        flat(img, ModItems.SILVER_NUGGET);
        flat(img, ModItems.SILVER_SHEET);
        // Trimmable armor item models (silver trim shows in inventory; see trimmableArmor).
        trimmableArmor(img, ModItems.SILVER_HELMET.get(), "helmet");
        trimmableArmor(img, ModItems.SILVER_CHESTPLATE.get(), "chestplate");
        trimmableArmor(img, ModItems.SILVER_LEGGINGS.get(), "leggings");
        trimmableArmor(img, ModItems.SILVER_BOOTS.get(), "boots");

        // Make the silver trim material show on vanilla armor inventory icons (see vanillaArmorTrimOverride).
        for (Item[] set : VANILLA_TRIMMED_ARMOR) {
            boolean dyeable = set[0] == Items.LEATHER_HELMET;
            vanillaArmorTrimOverride(img, set[0], "helmet", dyeable);
            vanillaArmorTrimOverride(img, set[1], "chestplate", dyeable);
            vanillaArmorTrimOverride(img, set[2], "leggings", dyeable);
            vanillaArmorTrimOverride(img, set[3], "boots", dyeable);
        }
        flat(img, ModItems.SILVER_HORSE_ARMOR);
        flat(img, ModItems.SILVER_NAUTILUS_ARMOR);
        moonDial(img);

        handheld(img, ModItems.SILVER_SHOVEL);
        handheld(img, ModItems.SILVER_PICKAXE);
        handheld(img, ModItems.SILVER_AXE);
        handheld(img, ModItems.SILVER_HOE);
        handheld(img, ModItems.SILVER_SWORD);
        handheld(img, ModItems.SILVER_KNIFE);
        img.generateSpear(ModItems.SILVER_SPEAR.get());
    }

    // ---------------- block helpers ----------------

    private static void cube(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block) {
        bmg.createTrivialCube(block.get());
    }

    /** Cube whose all-faces texture is taken from another block (waxed → unwaxed reuse). */
    private static void cubeReusing(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> textureSource) {
        Block b = block.get();
        Identifier model = ModelTemplates.CUBE_ALL.create(b, TextureMapping.cube(textureSource.get()), bmg.modelOutput);
        bmg.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(b, BlockModelGenerators.plainVariant(model)));
        bmg.registerSimpleItemModel(b, model);
    }

    /** Full block + slab + stairs as a family. {@code textureSource} = the block whose texture to use
     *  (itself for unwaxed, the unwaxed variant for waxed reuse). fullBlock() must run before slab/stairs. */
    private static void family(BlockModelGenerators bmg, RegistrySupplier<? extends Block> base, RegistrySupplier<? extends Block> textureSource,
                               RegistrySupplier<? extends Block> slab, RegistrySupplier<? extends Block> stairs) {
        bmg.new BlockFamilyProvider(TextureMapping.cube(textureSource.get()))
                .fullBlock(base.get(), ModelTemplates.CUBE_ALL)
                .slab(slab.get())
                .stairs(stairs.get());
    }

    private static void chiseled(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block) {
        bmg.createHorizontallyRotatedBlock(block.get(), TexturedModel.CUBE);
    }

    /** Waxed chiseled: same horizontal-facing model, but textured from the unwaxed variant. */
    private static void chiseledReusing(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> source) {
        bmg.createHorizontallyRotatedBlock(block.get(),
                TexturedModel.CUBE.updateTexture(tm -> tm.put(TextureSlot.ALL, TextureMapping.getBlockTexture(source.get()))));
    }

    private static void pillar(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> textureSource) {
        // Pillar side texture is block/<name> (no "_side" suffix), so build the mapping explicitly.
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(textureSource.get()))
                .put(TextureSlot.END, TextureMapping.getBlockTexture(textureSource.get(), "_top"));
        Identifier model = ModelTemplates.CUBE_COLUMN.create(
                block.get(), mapping, bmg.modelOutput);
        bmg.createAxisAlignedPillarBlockCustomModel(block.get(), BlockModelGenerators.plainVariant(model));
        bmg.registerSimpleItemModel(block.get(), model);
    }

    private static void bulb(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block) {
        bmg.createCopperBulb(block.get());
    }

    /** Waxed bulb: reuse the unwaxed bulb's 4 state models (unlit/lit/powered/lit+powered). */
    private static void bulbReusing(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> source) {
        Block s = source.get();
        bmg.blockStateOutput.accept(BlockModelGenerators.createCopperBulb(block.get(),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s)),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_lit")),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_powered")),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_lit_powered"))));
        bmg.registerSimpleItemModel(block.get(), ModelLocationUtils.getModelLocation(s));
    }

    private static void trapdoor(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block) {
        bmg.createTrapdoor(block.get());
    }

    /** Waxed trapdoor: reuse the unwaxed trapdoor's top/bottom/open models. */
    private static void trapdoorReusing(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> source) {
        Block s = source.get();
        bmg.blockStateOutput.accept(BlockModelGenerators.createTrapdoor(block.get(),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_top")),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_bottom")),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_open"))));
        bmg.registerSimpleItemModel(block.get(), ModelLocationUtils.getModelLocation(s, "_bottom"));
    }

    private static void door(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block) {
        bmg.createDoor(block.get());
    }

    /** Waxed door: reuse the unwaxed door's 8 hinge/open models + the unwaxed flat item model. */
    private static void doorReusing(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> source) {
        Block s = source.get();
        bmg.blockStateOutput.accept(BlockModelGenerators.createDoor(block.get(),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_bottom_left")),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_bottom_left_open")),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_bottom_right")),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_bottom_right_open")),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_top_left")),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_top_left_open")),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_top_right")),
                BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(s, "_top_right_open"))));
        bmg.itemModelOutput.copy(s.asItem(), block.get().asItem());
    }

    /** Moon Phase Detector: daylight-detector-style model; INVERTED swaps the top texture. */
    private static void moonPhaseDetector(BlockModelGenerators bmg) {
        Block block = ModBlocks.MOON_PHASE_DETECTOR.get();
        Material side = TextureMapping.getBlockTexture(block, "_side");
        TextureMapping normalMap = new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
                .put(TextureSlot.SIDE, side);
        TextureMapping invertedMap = new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_inverted_top"))
                .put(TextureSlot.SIDE, side);
        Identifier normalModel = ModelTemplates.DAYLIGHT_DETECTOR.create(block, normalMap, bmg.modelOutput);
        Identifier invertedModel = ModelTemplates.DAYLIGHT_DETECTOR.createWithSuffix(block, "_inverted", invertedMap, bmg.modelOutput);
        bmg.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(MoonPhaseDetectorBlock.INVERTED)
                        .select(false, BlockModelGenerators.plainVariant(normalModel))
                        .select(true, BlockModelGenerators.plainVariant(invertedModel))));
        bmg.registerSimpleItemModel(block, normalModel);
    }

    // ---------------- item helpers ----------------

    private static void flat(ItemModelGenerators img, RegistrySupplier<Item> item) {
        img.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
    }

    /**
     * Like vanilla {@code generateTrimmableItem} but adds the mod's {@code thesilverage:silver}
     * trim material, which vanilla's hardcoded material list would otherwise omit.
     */
    private static void trimmableArmor(ItemModelGenerators img, Item item, String slot) {
        Material itemTexture = TextureMapping.getItemTexture(item);
        Identifier slotPrefix = ItemModelGenerators.prefixForSlotTrim(slot);
        // Use FLAT_ITEM directly, not generateFlatItem: the latter also registers an item-model
        // definition that would collide with the select definition accepted below.
        Identifier baseModel = ModelTemplates.FLAT_ITEM.create(
                ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(itemTexture), img.modelOutput);

        List<ItemModelGenerators.TrimMaterialData> materials = new ArrayList<>(ItemModelGenerators.TRIM_MATERIAL_MODELS);
        materials.add(new ItemModelGenerators.TrimMaterialData(ModTrimMaterials.SILVER_ASSETS, ModTrimMaterials.SILVER));

        List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> cases = new ArrayList<>();
        for (ItemModelGenerators.TrimMaterialData data : materials) {
            // assetId resolves the per-armor override (e.g. silver->silver_darker on silver armor).
            String suffix = "_" + data.assets().assetId(ModTrimMaterials.SILVER_EQUIPMENT_ASSET).suffix();
            Identifier trimModel = baseModel.withSuffix(suffix + "_trim");
            img.generateLayeredItem(trimModel, itemTexture, new Material(slotPrefix.withSuffix(suffix)));
            cases.add(ItemModelUtils.when(data.materialKey(), ItemModelUtils.plainModel(trimModel)));
        }
        img.itemModelOutput.accept(item,
                ItemModelUtils.select(new TrimMaterialProperty(), ItemModelUtils.plainModel(baseModel), cases));
    }

    /** Vanilla leather-dye default tint (0xFFA06540) — must be carried on leather trim cases + fallback. */
    private static final int LEATHER_DEFAULT_COLOR = -6265536;

    /** Vanilla armor sets we override to add the silver trim: {helmet, chestplate, leggings, boots}. */
    private static final Item[][] VANILLA_TRIMMED_ARMOR = {
            {Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS},
            {Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS},
            {Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS},
            {Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS},
            {Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS},
            {Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS},
    };

    /** Add the overridden vanilla armor to the known-items set, else datagen skips them. */
    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        Stream<Holder<Item>> vanillaArmor = java.util.Arrays.stream(VANILLA_TRIMMED_ARMOR)
                .flatMap(java.util.Arrays::stream)
                .map(Item::builtInRegistryHolder);
        return Stream.concat(super.getKnownItems(), vanillaArmor);
    }

    /**
     * Rebuilds a vanilla armor piece's trim_material select so the silver trim shows on its
     * inventory icon. Vanilla materials reference their shipped trim models; silver is generated.
     * Leather pieces are 3-layer and carry the dye tint on every case and the fallback.
     */
    private static void vanillaArmorTrimOverride(ItemModelGenerators img, Item item, String slot, boolean dyeable) {
        Identifier baseModel = ModelLocationUtils.getModelLocation(item);
        Material itemTexture = TextureMapping.getItemTexture(item);
        Material overlayTexture = dyeable ? TextureMapping.getItemTexture(item, "_overlay") : null;
        Identifier slotPrefix = ItemModelGenerators.prefixForSlotTrim(slot);

        List<ItemModelGenerators.TrimMaterialData> materials = new ArrayList<>(ItemModelGenerators.TRIM_MATERIAL_MODELS);
        materials.add(new ItemModelGenerators.TrimMaterialData(ModTrimMaterials.SILVER_ASSETS, ModTrimMaterials.SILVER));

        List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> cases = new ArrayList<>();
        for (ItemModelGenerators.TrimMaterialData data : materials) {
            // BASE material suffix matches the model names vanilla ships (e.g. iron_helmet_iron_trim).
            Identifier trimModel = baseModel.withSuffix("_" + data.assets().base().suffix() + "_trim");
            if (data.materialKey() == ModTrimMaterials.SILVER) {
                // Vanilla ships no silver trim model; generate it (always the plain silver sprite).
                Material silverSprite = new Material(slotPrefix.withSuffix("_silver"));
                if (dyeable) {
                    TextureMapping tm = new TextureMapping()
                            .put(TextureSlot.LAYER0, itemTexture)
                            .put(TextureSlot.LAYER1, overlayTexture)
                            .put(TextureSlot.LAYER2, silverSprite);
                    ModelTemplates.THREE_LAYERED_ITEM.create(trimModel, tm, img.modelOutput);
                } else {
                    img.generateLayeredItem(trimModel, itemTexture, silverSprite);
                }
            }
            ItemModel.Unbaked caseModel = dyeable
                    ? ItemModelUtils.tintedModel(trimModel, new Dye(LEATHER_DEFAULT_COLOR))
                    : ItemModelUtils.plainModel(trimModel);
            cases.add(ItemModelUtils.when(data.materialKey(), caseModel));
        }
        ItemModel.Unbaked fallback = dyeable
                ? ItemModelUtils.tintedModel(baseModel, new Dye(LEATHER_DEFAULT_COLOR))
                : ItemModelUtils.plainModel(baseModel);
        img.itemModelOutput.accept(item, ItemModelUtils.select(new TrimMaterialProperty(), fallback, cases));
    }

    private static void handheld(ItemModelGenerators img, RegistrySupplier<Item> item) {
        img.generateFlatItem(item.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    /** Moon Dial: range_dispatch over thesilverage:moon_phase, 16 frames at thresholds i/16. */
    private static void moonDial(ItemModelGenerators img) {
        RangeSelectItemModelProperties.ID_MAPPER.put(ModItemProperties.MOON_PHASE, ModItemProperties.MoonPhaseProperty.MAP_CODEC);

        ItemModel.Unbaked fallback = frameModel(img, 0);
        List<RangeSelectItemModel.Entry> entries = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            entries.add(ItemModelUtils.override(frameModel(img, i), i / 16f));
        }
        img.itemModelOutput.accept(ModItems.MOON_DIAL.get(),
                ItemModelUtils.rangeSelect(new ModItemProperties.MoonPhaseProperty(), fallback, entries));
    }

    /** Flat model {@code item/moon_dial_<i>} from texture {@code item/moon_dial_<i>}. */
    private static ItemModel.Unbaked frameModel(ItemModelGenerators img, int i) {
        Identifier id = Identifier.fromNamespaceAndPath(TheSilverAge.MOD_ID, "item/moon_dial_" + i);
        Identifier model = ModelTemplates.FLAT_ITEM.create(id, TextureMapping.layer0(new Material(id)), img.modelOutput);
        return ItemModelUtils.plainModel(model);
    }
}
