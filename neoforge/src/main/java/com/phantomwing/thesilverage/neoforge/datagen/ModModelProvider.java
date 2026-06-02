package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
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
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Unified block + item model datagen for 1.21.4 (vanilla {@link ModelProvider}).
 *
 * <p>Uses the (NeoForge-AT-widened) {@link BlockModelGenerators}/{@link ItemModelGenerators}
 * helpers + their public {@code blockStateOutput}/{@code modelOutput}/{@code itemModelOutput}.
 * Render types for the transparent blocks (doors/trapdoors/grates) are registered client-side
 * (1.21.4 no longer carries render type in the model JSON).</p>
 *
 * <p>Waxed variants reuse their unwaxed counterpart's textures (vanilla-copper style). That
 * reuse is implemented for cubes/families/pillars here; for chiseled/door/trapdoor/bulb it is
 * a pending refinement (TODO) — those currently render from the block's own texture name.</p>
 */
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
        // Trimmable armor item models (show applied trims in the inventory), keyed to
        // the silver equipment asset. Mirrors how vanilla armor items are generated.
        img.generateTrimmableItem(ModItems.SILVER_HELMET.get(), SILVER_EQUIPMENT_ASSET, ItemModelGenerators.SLOT_HELMET, false);
        img.generateTrimmableItem(ModItems.SILVER_CHESTPLATE.get(), SILVER_EQUIPMENT_ASSET, ItemModelGenerators.SLOT_CHESTPLATE, false);
        img.generateTrimmableItem(ModItems.SILVER_LEGGINGS.get(), SILVER_EQUIPMENT_ASSET, ItemModelGenerators.SLOT_LEGGINS, false);
        img.generateTrimmableItem(ModItems.SILVER_BOOTS.get(), SILVER_EQUIPMENT_ASSET, ItemModelGenerators.SLOT_BOOTS, false);
        flat(img, ModItems.SILVER_HORSE_ARMOR);
        moonDial(img);

        handheld(img, ModItems.SILVER_SHOVEL);
        handheld(img, ModItems.SILVER_PICKAXE);
        handheld(img, ModItems.SILVER_AXE);
        handheld(img, ModItems.SILVER_HOE);
        handheld(img, ModItems.SILVER_SWORD);
        handheld(img, ModItems.SILVER_KNIFE);
    }

    // ---------------- block helpers ----------------

    private static void cube(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block) {
        bmg.createTrivialCube(block.get());
    }

    /** Cube whose all-faces texture is taken from another block (waxed → unwaxed reuse). */
    private static void cubeReusing(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> textureSource) {
        Block b = block.get();
        ResourceLocation model = ModelTemplates.CUBE_ALL.create(b, TextureMapping.cube(textureSource.get()), bmg.modelOutput);
        bmg.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(b, model));
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
        // The mod's pillar side texture is block/<name> (no "_side" suffix) — both
        // TexturedModel.COLUMN and TextureMapping.column use the vanilla "_side" convention,
        // so build the mapping explicitly: SIDE = block/<name>, END = block/<name>_top.
        // Works for unwaxed (textureSource == block) and waxed (textureSource == unwaxed).
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(textureSource.get()))
                .put(TextureSlot.END, TextureMapping.getBlockTexture(textureSource.get(), "_top"));
        ResourceLocation model = ModelTemplates.CUBE_COLUMN.create(
                block.get(), mapping, bmg.modelOutput);
        bmg.createAxisAlignedPillarBlockCustomModel(block.get(), model);
        bmg.registerSimpleItemModel(block.get(), model);
    }

    private static void bulb(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block) {
        bmg.createCopperBulb(block.get());
    }

    /** Waxed bulb: reuse the unwaxed bulb's 4 state models (unlit/lit/powered/lit+powered). */
    private static void bulbReusing(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> source) {
        Block s = source.get();
        bmg.blockStateOutput.accept(bmg.createCopperBulb(block.get(),
                ModelLocationUtils.getModelLocation(s),
                ModelLocationUtils.getModelLocation(s, "_lit"),
                ModelLocationUtils.getModelLocation(s, "_powered"),
                ModelLocationUtils.getModelLocation(s, "_lit_powered")));
        bmg.registerSimpleItemModel(block.get(), ModelLocationUtils.getModelLocation(s));
    }

    private static void trapdoor(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block) {
        bmg.createTrapdoor(block.get());
    }

    /** Waxed trapdoor: reuse the unwaxed trapdoor's top/bottom/open models. */
    private static void trapdoorReusing(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> source) {
        Block s = source.get();
        bmg.blockStateOutput.accept(BlockModelGenerators.createTrapdoor(block.get(),
                ModelLocationUtils.getModelLocation(s, "_top"),
                ModelLocationUtils.getModelLocation(s, "_bottom"),
                ModelLocationUtils.getModelLocation(s, "_open")));
        bmg.registerSimpleItemModel(block.get(), ModelLocationUtils.getModelLocation(s, "_bottom"));
    }

    private static void door(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block) {
        bmg.createDoor(block.get());
    }

    /** Waxed door: reuse the unwaxed door's 8 hinge/open models + the unwaxed flat item model. */
    private static void doorReusing(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> source) {
        Block s = source.get();
        bmg.blockStateOutput.accept(BlockModelGenerators.createDoor(block.get(),
                ModelLocationUtils.getModelLocation(s, "_bottom_left"),
                ModelLocationUtils.getModelLocation(s, "_bottom_left_open"),
                ModelLocationUtils.getModelLocation(s, "_bottom_right"),
                ModelLocationUtils.getModelLocation(s, "_bottom_right_open"),
                ModelLocationUtils.getModelLocation(s, "_top_left"),
                ModelLocationUtils.getModelLocation(s, "_top_left_open"),
                ModelLocationUtils.getModelLocation(s, "_top_right"),
                ModelLocationUtils.getModelLocation(s, "_top_right_open")));
        bmg.itemModelOutput.copy(s.asItem(), block.get().asItem());
    }

    /** The silver armor equipment-asset key (matches ModArmorMaterials.SILVER_ARMOR_MATERIAL). */
    private static final ResourceKey<EquipmentAsset> SILVER_EQUIPMENT_ASSET =
            ResourceKey.create(EquipmentAssets.ROOT_ID, TheSilverAge.resourceLocation("silver"));

    /** Moon Phase Detector: daylight-detector-style model; INVERTED swaps the top texture. */
    private static void moonPhaseDetector(BlockModelGenerators bmg) {
        Block block = ModBlocks.MOON_PHASE_DETECTOR.get();
        ResourceLocation side = TextureMapping.getBlockTexture(block, "_side");
        TextureMapping normalMap = new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
                .put(TextureSlot.SIDE, side);
        TextureMapping invertedMap = new TextureMapping()
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_inverted_top"))
                .put(TextureSlot.SIDE, side);
        ResourceLocation normalModel = ModelTemplates.DAYLIGHT_DETECTOR.create(block, normalMap, bmg.modelOutput);
        ResourceLocation invertedModel = ModelTemplates.DAYLIGHT_DETECTOR.createWithSuffix(block, "_inverted", invertedMap, bmg.modelOutput);
        bmg.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(MoonPhaseDetectorBlock.INVERTED)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, normalModel))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, invertedModel))));
        bmg.registerSimpleItemModel(block, normalModel);
    }

    // ---------------- item helpers ----------------

    private static void flat(ItemModelGenerators img, RegistrySupplier<Item> item) {
        img.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
    }

    private static void handheld(ItemModelGenerators img, RegistrySupplier<Item> item) {
        img.generateFlatItem(item.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    /**
     * Moon Dial: a {@code range_dispatch} item model on the custom {@code thesilverage:moon_phase}
     * property — 16 frames (moon_dial_0..15) at thresholds i/16 (matching the property's
     * {@code signal/16f}). The property type is registered into ID_MAPPER here so this run can
     * serialize {@code "property":"thesilverage:moon_phase"} (the game-side registration is in
     * the client bootstrap; separate JVM, no conflict).
     */
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
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, "item/moon_dial_" + i);
        ResourceLocation model = ModelTemplates.FLAT_ITEM.create(id, TextureMapping.layer0(id), img.modelOutput);
        return ItemModelUtils.plainModel(model);
    }
}
