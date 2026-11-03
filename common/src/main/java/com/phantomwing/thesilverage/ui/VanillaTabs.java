package com.phantomwing.thesilverage.ui;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.platform.CommonConfig;
import com.phantomwing.thesilverage.platform.CreativeTabPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

/**
 * The mod's items in vanilla's creative tabs too, each beside its vanilla counterpart, as The Tin Age's are: silver's
 * blocks after copper's, every stage in turn and then their waxed forms, as vanilla lays copper's out; its ore, tools,
 * armour and ingredients after gold's, the metal it stands beside. The mod's own tab keeps all of them together, and
 * an item it hides - the weathered and waxed forms with oxidation off, the arrows, the knife without Farmer's Delight -
 * is left out here too.
 */
public final class VanillaTabs {
    private static final String[] STAGES = {"", "exposed_", "weathered_", "oxidized_", "waxed_", "waxed_exposed_",
            "waxed_weathered_", "waxed_oxidized_"};
    private static final String[] BUILDING = {"silver", "chiseled_silver", "silver_grate", "cut_silver",
            "cut_silver_stairs", "cut_silver_slab", "silver_bricks", "silver_brick_stairs", "silver_brick_slab",
            "silver_brick_wall", "silver_pillar", "silver_bars", "silver_chain", "silver_door", "silver_trapdoor",
            "silver_pressure_plate", "silver_bulb"};

    private static final ResourceKey<CreativeModeTab> FARMERS_DELIGHT_TAB = modTab("farmersdelight", "farmersdelight");
    private static final ResourceKey<CreativeModeTab> CREATE_TAB = modTab("create", "base");

    private VanillaTabs() {
    }

    public static void register() {
        List<String> building = new ArrayList<>();
        for (String stage : STAGES) {
            for (String family : BUILDING) {
                building.add(staged(stage, family));
            }
        }
        add(CreativeModeTabs.BUILDING_BLOCKS, Items.WAXED_OXIDIZED_COPPER_BULB, building);

        add(CreativeModeTabs.NATURAL_BLOCKS, Items.DEEPSLATE_GOLD_ORE, List.of("silver_ore", "deepslate_silver_ore"));
        add(CreativeModeTabs.NATURAL_BLOCKS, Items.RAW_GOLD_BLOCK, List.of("raw_silver_block"));

        add(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.SOUL_TORCH, List.of("silver_torch"));
        add(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.SOUL_LANTERN, everyStage("silver_lantern"));
        add(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.CHAIN, everyStage("silver_chain"));
        add(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.WAXED_OXIDIZED_COPPER_BULB, everyStage("silver_bulb"));
        add(CreativeModeTabs.FUNCTIONAL_BLOCKS, Items.LIGHTNING_ROD, everyStage("silver_lightning_rod"));

        // Only the waxed forms, as vanilla lists only its waxed copper bulbs here.
        add(CreativeModeTabs.REDSTONE_BLOCKS, Items.WAXED_OXIDIZED_COPPER_BULB, waxedStages("silver_bulb"));
        add(CreativeModeTabs.REDSTONE_BLOCKS, Items.HEAVY_WEIGHTED_PRESSURE_PLATE, waxedStages("silver_pressure_plate"));
        add(CreativeModeTabs.REDSTONE_BLOCKS, Items.LIGHTNING_ROD, waxedStages("silver_lightning_rod"));
        add(CreativeModeTabs.REDSTONE_BLOCKS, Items.DAYLIGHT_DETECTOR, List.of("moon_phase_detector"));

        add(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.GOLDEN_HOE,
                List.of("silver_shovel", "silver_pickaxe", "silver_axe", "silver_hoe"));
        add(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.CLOCK, List.of("moon_dial"));
        // Farmer's Delight lists its knives in its own tab, not vanilla's.
        add(FARMERS_DELIGHT_TAB, List.of(farmersDelight("golden_knife")), List.of("silver_knife"));

        add(CreativeModeTabs.COMBAT, Items.GOLDEN_SWORD, List.of("silver_sword"));
        add(CreativeModeTabs.COMBAT, Items.GOLDEN_AXE, List.of("silver_axe"));
        add(CreativeModeTabs.COMBAT, Items.GOLDEN_BOOTS,
                List.of("silver_helmet", "silver_chestplate", "silver_leggings", "silver_boots"));
        add(CreativeModeTabs.COMBAT, Items.GOLDEN_HORSE_ARMOR, List.of("silver_horse_armor"));
        add(CreativeModeTabs.COMBAT, Items.ARROW, List.of("silver_arrow"));

        add(CreativeModeTabs.INGREDIENTS, Items.RAW_GOLD, List.of("raw_silver"));
        add(CreativeModeTabs.INGREDIENTS, Items.GOLD_NUGGET, List.of("silver_nugget"));
        add(CreativeModeTabs.INGREDIENTS, Items.GOLD_INGOT, List.of("silver_ingot"));

        // Create lists its sheets in its own tab, not vanilla's.
        add(CREATE_TAB, List.of(create("golden_sheet")), List.of("silver_sheet"));
    }

    /** {@code family} at {@code stage}: the Block of Silver is {@code silver_block} bare and waxed, {@code exposed_silver} weathered. */
    private static String staged(String stage, String family) {
        if (family.equals("silver") && (stage.isEmpty() || stage.equals("waxed_"))) {
            return stage + "silver_block";
        }
        return stage + family;
    }

    private static List<String> everyStage(String family) {
        List<String> ids = new ArrayList<>();
        for (String stage : STAGES) {
            ids.add(staged(stage, family));
        }
        return ids;
    }

    private static List<String> waxedStages(String family) {
        return everyStage(family).stream().filter(id -> id.startsWith("waxed_")).toList();
    }

    private static void add(ResourceKey<CreativeModeTab> tab, ItemLike after, List<String> ids) {
        add(tab, List.of(() -> after.asItem()), ids);
    }

    /** {@code ids} after the first of {@code anchors} the tab lists; an item no mod registers is air, and skipped. */
    private static void add(ResourceKey<CreativeModeTab> tab, List<ItemLike> anchors, List<String> ids) {
        CreativeTabPlatform.modify(tab, output -> {
            if (!CommonConfig.vanillaCreativeTabs()) {
                return;
            }
            List<ItemStack> stacks = new ArrayList<>();
            for (String id : ids) {
                Item item = BuiltInRegistries.ITEM.getValue(TheSilverAge.resourceLocation(id));
                if (ModCreativeModeTab.isShown(item)) {
                    stacks.add(new ItemStack(item));
                }
            }
            if (stacks.isEmpty()) {
                return;
            }
            for (ItemLike anchor : anchors) {
                if (anchor.asItem() == Items.AIR) {
                    continue;
                }
                try {
                    insertAfter(output, anchor, stacks);
                    return;
                } catch (IllegalArgumentException notListed) {
                    // The tab doesn't list this anchor, so the next one is tried; a failed insert has inserted nothing.
                }
            }
        });
    }

    private static ItemLike farmersDelight(String path) {
        return modItem("farmersdelight", path);
    }

    private static ItemLike create(String path) {
        return modItem("create", path);
    }

    private static ItemLike modItem(String namespace, String path) {
        return () -> BuiltInRegistries.ITEM.getValue(ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    private static ResourceKey<CreativeModeTab> modTab(String namespace, String path) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    // Each after the one before: inserted after the same anchor, they would come out in reverse.
    private static void insertAfter(CreativeTabPlatform.Inserter output, ItemLike anchor, List<ItemStack> stacks) {
        ItemStack previous = new ItemStack(anchor);
        for (ItemStack stack : stacks) {
            output.insertAfter(previous, stack);
            previous = stack;
        }
    }
}
