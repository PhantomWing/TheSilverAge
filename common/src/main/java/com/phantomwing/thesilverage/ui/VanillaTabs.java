package com.phantomwing.thesilverage.ui;

import com.phantomwing.thesilverage.TheSilverAge;
import dev.architectury.registry.CreativeTabOutput;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
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

        add(CreativeModeTabs.REDSTONE_BLOCKS, Items.WAXED_OXIDIZED_COPPER_BULB, everyStage("silver_bulb"));
        add(CreativeModeTabs.REDSTONE_BLOCKS, Items.HEAVY_WEIGHTED_PRESSURE_PLATE, everyStage("silver_pressure_plate"));
        add(CreativeModeTabs.REDSTONE_BLOCKS, Items.LIGHTNING_ROD, everyStage("silver_lightning_rod"));
        add(CreativeModeTabs.REDSTONE_BLOCKS, Items.DAYLIGHT_DETECTOR, List.of("moon_phase_detector"));

        add(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.GOLDEN_HOE,
                List.of("silver_shovel", "silver_pickaxe", "silver_axe", "silver_hoe", "silver_knife"));
        add(CreativeModeTabs.TOOLS_AND_UTILITIES, Items.CLOCK, List.of("moon_dial"));

        add(CreativeModeTabs.COMBAT, Items.GOLDEN_SWORD, List.of("silver_sword"));
        add(CreativeModeTabs.COMBAT, Items.GOLDEN_AXE, List.of("silver_axe"));
        add(CreativeModeTabs.COMBAT, Items.GOLDEN_BOOTS,
                List.of("silver_helmet", "silver_chestplate", "silver_leggings", "silver_boots"));
        add(CreativeModeTabs.COMBAT, Items.GOLDEN_HORSE_ARMOR, List.of("silver_horse_armor"));
        add(CreativeModeTabs.COMBAT, Items.SPECTRAL_ARROW, List.of("silver_arrow"));

        add(CreativeModeTabs.INGREDIENTS, Items.RAW_GOLD, List.of("raw_silver"));
        add(CreativeModeTabs.INGREDIENTS, Items.GOLD_NUGGET, List.of("silver_nugget"));
        add(CreativeModeTabs.INGREDIENTS, Items.GOLD_INGOT, List.of("silver_ingot", "silver_sheet"));
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

    private static void add(ResourceKey<CreativeModeTab> tab, ItemLike after, List<String> ids) {
        CreativeTabRegistry.modify(CreativeTabRegistry.defer(tab), (flags, output, op) -> {
            List<ItemStack> stacks = new ArrayList<>();
            for (String id : ids) {
                Item item = BuiltInRegistries.ITEM.get(TheSilverAge.resourceLocation(id));
                if (ModCreativeModeTab.isShown(item)) {
                    stacks.add(new ItemStack(item));
                }
            }
            insertAfter(output, after, stacks);
        });
    }

    // Each after the one before: inserted after the same anchor, they would come out in reverse.
    private static void insertAfter(CreativeTabOutput output, ItemLike anchor, List<ItemStack> stacks) {
        ItemStack previous = new ItemStack(anchor);
        for (ItemStack stack : stacks) {
            output.acceptAfter(previous, stack);
            previous = stack;
        }
    }
}
