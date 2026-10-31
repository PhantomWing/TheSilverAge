package com.phantomwing.thesilverage.neoforge.datagen;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.armor.ModTrimMaterials;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.block.custom.MoonPhaseDetectorBlock;
import com.phantomwing.thesilverage.client.ModItemColors;
import com.phantomwing.thesilverage.client.ModItemProperties;
import com.phantomwing.thesilverage.item.ModItems;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.Condition;
import net.minecraft.client.color.item.ItemTintSources;
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
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.TrimMaterialProperty;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Unified block + item model datagen for 1.21.4 (vanilla {@link ModelProvider}).
 *
 * <p>Uses the (NeoForge-AT-widened) {@link BlockModelGenerators}/{@link ItemModelGenerators}
 * helpers + their public {@code blockStateOutput}/{@code modelOutput}/{@code itemModelOutput}.
 * Render types for the transparent blocks (doors/trapdoors/grates) are registered client-side
 * (1.21.4 no longer carries render type in the model JSON).</p>
 *
 * <p>Waxed variants reuse their unwaxed counterpart's textures, or its models outright
 * (vanilla-copper style).</p>
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

        // ---------- Silver Brick Wall (textured from the bricks of the matching weather stage) ----------
        wall(bmg, ModBlocks.SILVER_BRICK_WALL, ModBlocks.SILVER_BRICKS);
        wall(bmg, ModBlocks.EXPOSED_SILVER_BRICK_WALL, ModBlocks.EXPOSED_SILVER_BRICKS);
        wall(bmg, ModBlocks.WEATHERED_SILVER_BRICK_WALL, ModBlocks.WEATHERED_SILVER_BRICKS);
        wall(bmg, ModBlocks.OXIDIZED_SILVER_BRICK_WALL, ModBlocks.OXIDIZED_SILVER_BRICKS);
        wall(bmg, ModBlocks.WAXED_SILVER_BRICK_WALL, ModBlocks.SILVER_BRICKS);
        wall(bmg, ModBlocks.WAXED_EXPOSED_SILVER_BRICK_WALL, ModBlocks.EXPOSED_SILVER_BRICKS);
        wall(bmg, ModBlocks.WAXED_WEATHERED_SILVER_BRICK_WALL, ModBlocks.WEATHERED_SILVER_BRICKS);
        wall(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_WALL, ModBlocks.OXIDIZED_SILVER_BRICKS);

        // ---------- Silver Pressure Plate (textured with its stage's Block of Silver, as vanilla's weighted plates are) ----------
        pressurePlate(bmg, ModBlocks.SILVER_PRESSURE_PLATE, ModBlocks.SILVER_BLOCK);
        pressurePlate(bmg, ModBlocks.EXPOSED_SILVER_PRESSURE_PLATE, ModBlocks.EXPOSED_SILVER);
        pressurePlate(bmg, ModBlocks.WEATHERED_SILVER_PRESSURE_PLATE, ModBlocks.WEATHERED_SILVER);
        pressurePlate(bmg, ModBlocks.OXIDIZED_SILVER_PRESSURE_PLATE, ModBlocks.OXIDIZED_SILVER);
        pressurePlate(bmg, ModBlocks.WAXED_SILVER_PRESSURE_PLATE, ModBlocks.SILVER_BLOCK);
        pressurePlate(bmg, ModBlocks.WAXED_EXPOSED_SILVER_PRESSURE_PLATE, ModBlocks.EXPOSED_SILVER);
        pressurePlate(bmg, ModBlocks.WAXED_WEATHERED_SILVER_PRESSURE_PLATE, ModBlocks.WEATHERED_SILVER);
        pressurePlate(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_PRESSURE_PLATE, ModBlocks.OXIDIZED_SILVER);

        // ---------- Silver Lantern (vanilla's lantern templates; cutout layer set client-side) ----------
        lantern(bmg, ModBlocks.SILVER_LANTERN, ModBlocks.SILVER_LANTERN);
        lantern(bmg, ModBlocks.EXPOSED_SILVER_LANTERN, ModBlocks.EXPOSED_SILVER_LANTERN);
        lantern(bmg, ModBlocks.WEATHERED_SILVER_LANTERN, ModBlocks.WEATHERED_SILVER_LANTERN);
        lantern(bmg, ModBlocks.OXIDIZED_SILVER_LANTERN, ModBlocks.OXIDIZED_SILVER_LANTERN);
        lantern(bmg, ModBlocks.WAXED_SILVER_LANTERN, ModBlocks.SILVER_LANTERN);
        lantern(bmg, ModBlocks.WAXED_EXPOSED_SILVER_LANTERN, ModBlocks.EXPOSED_SILVER_LANTERN);
        lantern(bmg, ModBlocks.WAXED_WEATHERED_SILVER_LANTERN, ModBlocks.WEATHERED_SILVER_LANTERN);
        lantern(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_LANTERN, ModBlocks.OXIDIZED_SILVER_LANTERN);

        // ---------- Silver Chain ----------
        chain(bmg, ModBlocks.SILVER_CHAIN, ModBlocks.SILVER_CHAIN);
        chain(bmg, ModBlocks.EXPOSED_SILVER_CHAIN, ModBlocks.EXPOSED_SILVER_CHAIN);
        chain(bmg, ModBlocks.WEATHERED_SILVER_CHAIN, ModBlocks.WEATHERED_SILVER_CHAIN);
        chain(bmg, ModBlocks.OXIDIZED_SILVER_CHAIN, ModBlocks.OXIDIZED_SILVER_CHAIN);
        chain(bmg, ModBlocks.WAXED_SILVER_CHAIN, ModBlocks.SILVER_CHAIN);
        chain(bmg, ModBlocks.WAXED_EXPOSED_SILVER_CHAIN, ModBlocks.EXPOSED_SILVER_CHAIN);
        chain(bmg, ModBlocks.WAXED_WEATHERED_SILVER_CHAIN, ModBlocks.WEATHERED_SILVER_CHAIN);
        chain(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_CHAIN, ModBlocks.OXIDIZED_SILVER_CHAIN);

        // ---------- Silver Bars ----------
        bars(bmg, ModBlocks.SILVER_BARS, ModBlocks.SILVER_BARS);
        bars(bmg, ModBlocks.EXPOSED_SILVER_BARS, ModBlocks.EXPOSED_SILVER_BARS);
        bars(bmg, ModBlocks.WEATHERED_SILVER_BARS, ModBlocks.WEATHERED_SILVER_BARS);
        bars(bmg, ModBlocks.OXIDIZED_SILVER_BARS, ModBlocks.OXIDIZED_SILVER_BARS);
        bars(bmg, ModBlocks.WAXED_SILVER_BARS, ModBlocks.SILVER_BARS);
        bars(bmg, ModBlocks.WAXED_EXPOSED_SILVER_BARS, ModBlocks.EXPOSED_SILVER_BARS);
        bars(bmg, ModBlocks.WAXED_WEATHERED_SILVER_BARS, ModBlocks.WEATHERED_SILVER_BARS);
        bars(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_BARS, ModBlocks.OXIDIZED_SILVER_BARS);

        // ---------- Silver Lightning Rod ----------
        lightningRod(bmg, ModBlocks.SILVER_LIGHTNING_ROD);
        lightningRod(bmg, ModBlocks.EXPOSED_SILVER_LIGHTNING_ROD);
        lightningRod(bmg, ModBlocks.WEATHERED_SILVER_LIGHTNING_ROD);
        lightningRod(bmg, ModBlocks.OXIDIZED_SILVER_LIGHTNING_ROD);
        lightningRodReusing(bmg, ModBlocks.WAXED_SILVER_LIGHTNING_ROD, ModBlocks.SILVER_LIGHTNING_ROD);
        lightningRodReusing(bmg, ModBlocks.WAXED_EXPOSED_SILVER_LIGHTNING_ROD, ModBlocks.EXPOSED_SILVER_LIGHTNING_ROD);
        lightningRodReusing(bmg, ModBlocks.WAXED_WEATHERED_SILVER_LIGHTNING_ROD, ModBlocks.WEATHERED_SILVER_LIGHTNING_ROD);
        lightningRodReusing(bmg, ModBlocks.WAXED_OXIDIZED_SILVER_LIGHTNING_ROD, ModBlocks.OXIDIZED_SILVER_LIGHTNING_ROD);

        // ---------- Silver Torch (floor + wall; the wall torch has no item) ----------
        bmg.createNormalTorch(ModBlocks.SILVER_TORCH.get(), ModBlocks.SILVER_WALL_TORCH.get());

        // ---------- Moon Phase Detector (daylight-detector style, INVERTED top swap) ----------
        moonPhaseDetector(bmg);

        // ============================ ITEMS ============================
        flat(img, ModItems.RAW_SILVER);
        flat(img, ModItems.SILVER_INGOT);
        flat(img, ModItems.SILVER_NUGGET);
        flat(img, ModItems.SILVER_SHEET);
        // Trimmable armor item models (show applied trims in the inventory), keyed to
        // the silver equipment asset. Mirrors how vanilla armor items are generated.
        // Vanilla armour is left alone: on NeoForge its trimmed models resolve any registered material at runtime, and
        // shadowing vanilla's own item definitions would break that for every other mod (as The Lead Age's 1.21.4 line).
        trimmableArmor(img, ModItems.SILVER_HELMET.get(), "helmet");
        trimmableArmor(img, ModItems.SILVER_CHESTPLATE.get(), "chestplate");
        trimmableArmor(img, ModItems.SILVER_LEGGINGS.get(), "leggings");
        trimmableArmor(img, ModItems.SILVER_BOOTS.get(), "boots");
        flat(img, ModItems.SILVER_HORSE_ARMOR);
        moonDial(img);

        handheld(img, ModItems.SILVER_SHOVEL);
        handheld(img, ModItems.SILVER_PICKAXE);
        handheld(img, ModItems.SILVER_AXE);
        handheld(img, ModItems.SILVER_HOE);
        handheld(img, ModItems.SILVER_SWORD);
        handheld(img, ModItems.SILVER_KNIFE);
        flat(img, ModItems.SILVER_ARROW);
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

    private static void wall(BlockModelGenerators bmg, RegistrySupplier<? extends Block> wall, RegistrySupplier<? extends Block> textureSource) {
        bmg.new BlockFamilyProvider(TextureMapping.cube(textureSource.get())).wall(wall.get());
    }

    /** Weighted-plate models (up and down) off its stage's Block of Silver, and the up model as its item. */
    private static void pressurePlate(BlockModelGenerators bmg, RegistrySupplier<Block> plate, RegistrySupplier<Block> textureSource) {
        bmg.createWeightedPressurePlate(plate.get(), textureSource.get());
        bmg.registerSimpleItemModel(plate.get(), ModelLocationUtils.getModelLocation(plate.get()));
    }

    /** Standing and hanging lantern off vanilla's templates; a waxed lantern takes its unwaxed one's texture and item. */
    private static void lantern(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> textureSource) {
        Block b = block.get();
        TextureMapping mapping = TextureMapping.lantern(textureSource.get());
        ResourceLocation standing = ModelTemplates.LANTERN.create(b, mapping, bmg.modelOutput);
        ResourceLocation hanging = ModelTemplates.HANGING_LANTERN.create(b, mapping, bmg.modelOutput);
        bmg.blockStateOutput.accept(MultiVariantGenerator.multiVariant(b)
                .with(BlockModelGenerators.createBooleanModelDispatch(BlockStateProperties.HANGING, hanging, standing)));
        flatItemFrom(bmg, b, textureSource.get());
    }

    /** Vanilla's chain geometry (there is no chain template before 1.21.9), rotated per axis. */
    private static void chain(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> textureSource) {
        ResourceLocation texture = TextureMapping.getBlockTexture(textureSource.get());
        ResourceLocation model = CHAIN.create(block.get(), new TextureMapping()
                .put(TextureSlot.ALL, texture).put(TextureSlot.PARTICLE, texture), bmg.modelOutput);
        bmg.createAxisAlignedPillarBlockCustomModel(block.get(), model);
        flatItemFrom(bmg, block.get(), textureSource.get());
    }

    /**
     * Bars: vanilla's iron bars multipart over its own models. The glass pane's drew a lone bar as a thick post and a
     * run of them with the texture along its edges. The item is the block texture, flat, as iron bars' is.
     */
    private static void bars(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> textureSource) {
        Block b = block.get();
        ResourceLocation texture = TextureMapping.getBlockTexture(textureSource.get());
        TextureMapping mapping = new TextureMapping()
                .put(TextureSlot.PARTICLE, texture).put(BARS, texture).put(TextureSlot.EDGE, texture);
        ResourceLocation postEnds = barsModel(bmg, b, textureSource.get(), "post_ends", mapping);
        ResourceLocation post = barsModel(bmg, b, textureSource.get(), "post", mapping);
        ResourceLocation cap = barsModel(bmg, b, textureSource.get(), "cap", mapping);
        ResourceLocation capAlt = barsModel(bmg, b, textureSource.get(), "cap_alt", mapping);
        ResourceLocation side = barsModel(bmg, b, textureSource.get(), "side", mapping);
        ResourceLocation sideAlt = barsModel(bmg, b, textureSource.get(), "side_alt", mapping);
        bmg.blockStateOutput.accept(MultiPartGenerator.multiPart(b)
                .with(Variant.variant().with(VariantProperties.MODEL, postEnds))
                .with(Condition.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false)
                                .term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false),
                        Variant.variant().with(VariantProperties.MODEL, post))
                .with(Condition.condition().term(BlockStateProperties.NORTH, true).term(BlockStateProperties.EAST, false)
                                .term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false),
                        Variant.variant().with(VariantProperties.MODEL, cap))
                .with(Condition.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, true)
                                .term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, false),
                        Variant.variant().with(VariantProperties.MODEL, cap).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .with(Condition.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false)
                                .term(BlockStateProperties.SOUTH, true).term(BlockStateProperties.WEST, false),
                        Variant.variant().with(VariantProperties.MODEL, capAlt))
                .with(Condition.condition().term(BlockStateProperties.NORTH, false).term(BlockStateProperties.EAST, false)
                                .term(BlockStateProperties.SOUTH, false).term(BlockStateProperties.WEST, true),
                        Variant.variant().with(VariantProperties.MODEL, capAlt).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .with(Condition.condition().term(BlockStateProperties.NORTH, true),
                        Variant.variant().with(VariantProperties.MODEL, side))
                .with(Condition.condition().term(BlockStateProperties.EAST, true),
                        Variant.variant().with(VariantProperties.MODEL, side).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .with(Condition.condition().term(BlockStateProperties.SOUTH, true),
                        Variant.variant().with(VariantProperties.MODEL, sideAlt))
                .with(Condition.condition().term(BlockStateProperties.WEST, true),
                        Variant.variant().with(VariantProperties.MODEL, sideAlt).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)));
        bmg.registerSimpleItemModel(b, ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(b.asItem()),
                TextureMapping.layer0(texture), bmg.modelOutput));
    }

    /** A part's model: written for the unwaxed bars, and the waxed ones' blockstate points at those. */
    private static ResourceLocation barsModel(BlockModelGenerators bmg, Block block, Block textureSource, String part, TextureMapping mapping) {
        return block == textureSource
                ? barsTemplate(part).create(block, mapping, bmg.modelOutput)
                : ModelLocationUtils.getModelLocation(textureSource, "_" + part);
    }

    private static ModelTemplate barsTemplate(String part) {
        return new ModelTemplate(Optional.of(ResourceLocation.withDefaultNamespace("block/iron_bars_" + part)),
                Optional.of("_" + part), TextureSlot.PARTICLE, BARS, TextureSlot.EDGE);
    }

    /** Lightning rod: vanilla's rod geometry, pointing where it faces; struck, every stage glows vanilla's plain white. */
    private static void lightningRod(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block) {
        Block b = block.get();
        ResourceLocation texture = TextureMapping.getBlockTexture(b);
        LIGHTNING_ROD.create(b, new TextureMapping().put(TextureSlot.TEXTURE, texture).put(TextureSlot.PARTICLE, texture), bmg.modelOutput);
        LIGHTNING_ROD.createWithSuffix(b, "_on", new TextureMapping()
                .put(TextureSlot.TEXTURE, ResourceLocation.withDefaultNamespace("block/lightning_rod_on"))
                .put(TextureSlot.PARTICLE, texture), bmg.modelOutput);
        lightningRodReusing(bmg, block, block);
    }

    private static void lightningRodReusing(BlockModelGenerators bmg, RegistrySupplier<? extends Block> block, RegistrySupplier<? extends Block> source) {
        ResourceLocation off = ModelLocationUtils.getModelLocation(source.get());
        ResourceLocation on = ModelLocationUtils.getModelLocation(source.get(), "_on");
        bmg.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                .with(BlockModelGenerators.createBooleanModelDispatch(BlockStateProperties.POWERED, on, off))
                .with(PropertyDispatch.property(BlockStateProperties.FACING)
                        .select(Direction.UP, Variant.variant())
                        .select(Direction.DOWN, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.NORTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.SOUTH, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.WEST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.EAST, Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))));
        bmg.registerSimpleItemModel(block.get(), off);
    }

    /** A flat item from {@code textureSource}'s item texture: a waxed block's item looks as its unwaxed one's. */
    private static void flatItemFrom(BlockModelGenerators bmg, Block block, Block textureSource) {
        bmg.registerSimpleItemModel(block, ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(block.asItem()),
                TextureMapping.layer0(textureSource.asItem()), bmg.modelOutput));
    }

    private static final TextureSlot BARS = TextureSlot.create("bars");
    private static final ModelTemplate CHAIN = new ModelTemplate(Optional.of(ResourceLocation.withDefaultNamespace("block/chain")),
            Optional.empty(), TextureSlot.ALL, TextureSlot.PARTICLE);
    private static final ModelTemplate LIGHTNING_ROD = new ModelTemplate(Optional.of(ResourceLocation.withDefaultNamespace("block/lightning_rod")),
            Optional.empty(), TextureSlot.TEXTURE, TextureSlot.PARTICLE);


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

    /**
     * Like vanilla {@code generateTrimmableItem}, but with a case for the mod's {@code thesilverage:silver} trim
     * material, which vanilla's hardcoded list leaves out: without it a silver trim draws untrimmed in the inventory.
     * Silver on silver takes the darker palette, as the worn armour does, or the trim nearly vanishes.
     */
    private static void trimmableArmor(ItemModelGenerators img, Item item, String slot) {
        ResourceLocation itemTexture = TextureMapping.getItemTexture(item);
        ResourceLocation slotPrefix = ResourceLocation.withDefaultNamespace("trims/items/" + slot + "_trim");
        // FLAT_ITEM rather than generateFlatItem, which would also accept an item definition the select's collides with.
        ResourceLocation baseModel = ModelTemplates.FLAT_ITEM.create(
                ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(itemTexture), img.modelOutput);
        List<SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>>> cases = new ArrayList<>();
        for (ItemModelGenerators.TrimMaterialData data : ItemModelGenerators.TRIM_MATERIAL_MODELS) {
            cases.add(trimCase(img, baseModel, itemTexture, slotPrefix, data.name(), data.materialKey()));
        }
        cases.add(trimCase(img, baseModel, itemTexture, slotPrefix, "silver_darker", ModTrimMaterials.SILVER));
        img.itemModelOutput.accept(item,
                ItemModelUtils.select(new TrimMaterialProperty(), ItemModelUtils.plainModel(baseModel), cases));
    }

    /** One trim_material case: its layered model, and the case that picks it. */
    private static SelectItemModel.SwitchCase<ResourceKey<TrimMaterial>> trimCase(ItemModelGenerators img, ResourceLocation baseModel,
            ResourceLocation itemTexture, ResourceLocation slotPrefix, String palette, ResourceKey<TrimMaterial> material) {
        ResourceLocation trimModel = baseModel.withSuffix("_" + palette + "_trim");
        img.generateLayeredItem(trimModel, itemTexture, slotPrefix.withSuffix("_" + palette));
        return ItemModelUtils.when(material, ItemModelUtils.plainModel(trimModel));
    }

    private static void handheld(ItemModelGenerators img, RegistrySupplier<Item> item) {
        img.generateFlatItem(item.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    /**
     * Moon Dial: a {@code range_dispatch} item model on the custom {@code thesilverage:moon_phase}
     * property, at thresholds i/16 (matching the property's {@code signal/16f}): frames 0-15 are the
     * moon phase signals, 16 and 17 the super moon frames ModItemProperties swaps in during an
     * Enhanced Celestials super event. Each frame is two layers, an untinted base and the moon, whose
     * tint is the {@code thesilverage:lunar_event} source (ModItemColors). The property and tint types
     * are registered into their ID mappers here so this run can serialize them (the game-side
     * registration is in the client bootstrap; putting the same codec under the same id again changes nothing).
     */
    private static void moonDial(ItemModelGenerators img) {
        RangeSelectItemModelProperties.ID_MAPPER.put(ModItemProperties.MOON_PHASE, ModItemProperties.MoonPhaseProperty.MAP_CODEC);
        ItemTintSources.ID_MAPPER.put(ModItemColors.LUNAR_EVENT, ModItemColors.LunarEventTint.MAP_CODEC);

        // The fallback is the full-moon frame, matching the one LevelUtils uses for fixed-time dimensions.
        ItemModel.Unbaked fallback = frameModel(img, 0);
        List<RangeSelectItemModel.Entry> entries = new ArrayList<>();
        for (int i = 1; i <= ModItemProperties.SUPER_MOON_UPCOMING; i++) {
            entries.add(ItemModelUtils.override(frameModel(img, i), i / 16f));
        }
        img.itemModelOutput.accept(ModItems.MOON_DIAL.get(),
                ItemModelUtils.rangeSelect(new ModItemProperties.MoonPhaseProperty(), fallback, entries));
    }

    /**
     * Layered model {@code item/moon_dial_<i>}. getMoonPhaseSignal: signal = phase * 2 - (isDay ? 1 : 0), so an
     * even signal is a settled night phase and an odd one the daytime transition toward the next phase, which uses
     * the "upcoming" base. A super moon replaces the full moon it always coincides with.
     */
    private static ItemModel.Unbaked frameModel(ItemModelGenerators img, int i) {
        boolean superMoon = i >= ModItemProperties.SUPER_MOON_NIGHT;
        boolean upcoming = superMoon ? i == ModItemProperties.SUPER_MOON_UPCOMING : i % 2 != 0;
        ResourceLocation base = itemTexture(upcoming ? "moon_dial_upcoming_base" : "moon_dial_base");
        ResourceLocation moon = itemTexture(superMoon ? "moon_dial_super_moon"
                : "moon_dial_moon_" + (upcoming ? ((i + 1) / 2) % 8 : i / 2));
        ResourceLocation model = ModelTemplates.TWO_LAYERED_ITEM.create(itemTexture("moon_dial_" + i),
                TextureMapping.layered(base, moon), img.modelOutput);
        return ItemModelUtils.tintedModel(model, ItemModelUtils.constantTint(-1), new ModItemColors.LunarEventTint());
    }

    private static ResourceLocation itemTexture(String name) {
        return ResourceLocation.fromNamespaceAndPath(TheSilverAge.MOD_ID, "item/" + name);
    }
}
