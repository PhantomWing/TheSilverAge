package com.phantomwing.thesilverage.gametest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phantomwing.thesilverage.Configuration;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.client.RecipeOverridePackHandler;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.CommonTags;
import com.phantomwing.thesilverage.utils.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * Automated checks for the 1.3.0 backport. Run with {@code ./gradlew runGameTestServer}
 * (and {@code -PloadFD=false} for the standalone Farmer's Delight fallback).
 */
@GameTestHolder(TheSilverAge.MOD_ID)
@PrefixGameTestTemplate(false)
public class SilverAgeGameTests {
    private static final String EMPTY = "empty";
    private static final List<String> LANGUAGES = List.of(
            "de_de", "en_us", "es_es", "fr_fr", "it_it", "ja_jp", "ko_kr", "nl_nl", "ro_ro", "ru_ru", "uk_ua", "zh_cn");

    // ---------------------------------------------------------------- ore drops

    @GameTest(template = EMPTY)
    public static void ore_drops_one_to_three_raw_silver(GameTestHelper helper) {
        for (RegistryObject<Block> ore : List.of(ModBlocks.SILVER_ORE, ModBlocks.DEEPSLATE_SILVER_ORE)) {
            BlockState state = ore.get().defaultBlockState();
            boolean sawOne = false, sawThree = false;
            for (int roll = 0; roll < 300; roll++) {
                List<ItemStack> drops = drops(helper, state, new ItemStack(Items.IRON_PICKAXE));
                helper.assertTrue(drops.size() == 1 && drops.get(0).is(ModItems.RAW_SILVER.get()),
                        ore.getId() + " should drop only raw silver, got " + drops);
                int count = drops.get(0).getCount();
                helper.assertTrue(count >= 1 && count <= 3, ore.getId() + " dropped " + count + " raw silver, expected 1-3");
                sawOne |= count == 1;
                sawThree |= count == 3;
            }
            // (2/3)^300 odds of never rolling a given bound: effectively impossible by chance.
            helper.assertTrue(sawOne && sawThree, ore.getId() + " never hit both ends of 1-3 in 300 rolls");

            ItemStack silkTouch = new ItemStack(Items.IRON_PICKAXE);
            silkTouch.enchant(Enchantments.SILK_TOUCH, 1);
            List<ItemStack> silk = drops(helper, state, silkTouch);
            helper.assertTrue(silk.size() == 1 && silk.get(0).is(ore.get().asItem()) && silk.get(0).getCount() == 1,
                    ore.getId() + " with silk touch should drop itself, got " + silk);
        }
        helper.succeed();
    }

    // ---------------------------------------------------------------- recipes

    @GameTest(template = EMPTY)
    public static void brick_and_pillar_crafting_recipes(GameTestHelper helper) {
        Item ingot = ModItems.SILVER_INGOT.get();
        assertCrafts(helper, ModItems.SILVER_BRICKS.get(), 4, "silver bricks from 2x2 ingots",
                ingot, ingot, null,
                ingot, ingot, null,
                null, null, null);

        Item bricks = ModItems.SILVER_BRICKS.get();
        assertCrafts(helper, ModItems.SILVER_BRICK_SLAB.get(), 6, "brick slab",
                bricks, bricks, bricks,
                null, null, null,
                null, null, null);
        assertCrafts(helper, ModItems.SILVER_BRICK_STAIRS.get(), 4, "brick stairs",
                bricks, null, null,
                bricks, bricks, null,
                bricks, bricks, bricks);

        assertPillarCraft(helper, ModItems.SILVER_BLOCK.get(), ModItems.SILVER_PILLAR.get());
        assertPillarCraft(helper, ModItems.EXPOSED_SILVER.get(), ModItems.EXPOSED_SILVER_PILLAR.get());
        assertPillarCraft(helper, ModItems.WEATHERED_SILVER.get(), ModItems.WEATHERED_SILVER_PILLAR.get());
        assertPillarCraft(helper, ModItems.OXIDIZED_SILVER.get(), ModItems.OXIDIZED_SILVER_PILLAR.get());
        assertPillarCraft(helper, ModItems.WAXED_SILVER_BLOCK.get(), ModItems.WAXED_SILVER_PILLAR.get());

        assertCrafts(helper, ModItems.WAXED_SILVER_BRICKS.get(), 1, "waxing bricks with honeycomb",
                bricks, Items.HONEYCOMB, null,
                null, null, null,
                null, null, null);
        assertCrafts(helper, ModItems.WAXED_SILVER_PILLAR.get(), 1, "waxing a pillar with honeycomb",
                ModItems.SILVER_PILLAR.get(), Items.HONEYCOMB, null,
                null, null, null,
                null, null, null);
        helper.succeed();
    }

    @GameTest(template = EMPTY)
    public static void brick_and_pillar_stonecutting_recipes(GameTestHelper helper) {
        assertStonecuts(helper, ModItems.SILVER_BLOCK.get(), ModItems.SILVER_BRICKS.get(), 4);
        assertStonecuts(helper, ModItems.SILVER_BLOCK.get(), ModItems.SILVER_BRICK_SLAB.get(), 8);
        assertStonecuts(helper, ModItems.SILVER_BLOCK.get(), ModItems.SILVER_BRICK_STAIRS.get(), 4);
        assertStonecuts(helper, ModItems.SILVER_BLOCK.get(), ModItems.SILVER_PILLAR.get(), 1);
        assertStonecuts(helper, ModItems.SILVER_BRICKS.get(), ModItems.SILVER_BRICK_SLAB.get(), 2);
        assertStonecuts(helper, ModItems.SILVER_BRICKS.get(), ModItems.SILVER_BRICK_STAIRS.get(), 1);
        assertStonecuts(helper, ModItems.OXIDIZED_SILVER.get(), ModItems.OXIDIZED_SILVER_BRICKS.get(), 4);
        assertStonecuts(helper, ModItems.WAXED_WEATHERED_SILVER.get(), ModItems.WAXED_WEATHERED_SILVER_PILLAR.get(), 1);
        helper.succeed();
    }

    // ---------------------------------------------------------------- oxidation + waxing maps

    @GameTest(template = EMPTY)
    public static void new_families_weather_and_wax(GameTestHelper helper) {
        List<Block[]> families = List.of(
                blocks(ModBlocks.SILVER_BRICKS, ModBlocks.EXPOSED_SILVER_BRICKS, ModBlocks.WEATHERED_SILVER_BRICKS, ModBlocks.OXIDIZED_SILVER_BRICKS,
                        ModBlocks.WAXED_SILVER_BRICKS, ModBlocks.WAXED_EXPOSED_SILVER_BRICKS, ModBlocks.WAXED_WEATHERED_SILVER_BRICKS, ModBlocks.WAXED_OXIDIZED_SILVER_BRICKS),
                blocks(ModBlocks.SILVER_BRICK_SLAB, ModBlocks.EXPOSED_SILVER_BRICK_SLAB, ModBlocks.WEATHERED_SILVER_BRICK_SLAB, ModBlocks.OXIDIZED_SILVER_BRICK_SLAB,
                        ModBlocks.WAXED_SILVER_BRICK_SLAB, ModBlocks.WAXED_EXPOSED_SILVER_BRICK_SLAB, ModBlocks.WAXED_WEATHERED_SILVER_BRICK_SLAB, ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_SLAB),
                blocks(ModBlocks.SILVER_BRICK_STAIRS, ModBlocks.EXPOSED_SILVER_BRICK_STAIRS, ModBlocks.WEATHERED_SILVER_BRICK_STAIRS, ModBlocks.OXIDIZED_SILVER_BRICK_STAIRS,
                        ModBlocks.WAXED_SILVER_BRICK_STAIRS, ModBlocks.WAXED_EXPOSED_SILVER_BRICK_STAIRS, ModBlocks.WAXED_WEATHERED_SILVER_BRICK_STAIRS, ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_STAIRS),
                blocks(ModBlocks.SILVER_PILLAR, ModBlocks.EXPOSED_SILVER_PILLAR, ModBlocks.WEATHERED_SILVER_PILLAR, ModBlocks.OXIDIZED_SILVER_PILLAR,
                        ModBlocks.WAXED_SILVER_PILLAR, ModBlocks.WAXED_EXPOSED_SILVER_PILLAR, ModBlocks.WAXED_WEATHERED_SILVER_PILLAR, ModBlocks.WAXED_OXIDIZED_SILVER_PILLAR));

        for (Block[] f : families) {
            for (int i = 0; i < 4; i++) {
                String name = ForgeRegistries.BLOCKS.getKey(f[i]).toString();
                Optional<Block> next = WeatheringCopper.getNext(f[i]);
                Optional<Block> previous = WeatheringCopper.getPrevious(f[i]);
                helper.assertTrue(i < 3 ? next.orElse(null) == f[i + 1] : next.isEmpty(), name + " weathers to the wrong block: " + next);
                helper.assertTrue(i > 0 ? previous.orElse(null) == f[i - 1] : previous.isEmpty(), name + " scrapes to the wrong block: " + previous);
                helper.assertTrue(HoneycombItem.WAXABLES.get().get(f[i]) == f[4 + i], name + " waxes to the wrong block");
                helper.assertTrue(HoneycombItem.WAX_OFF_BY_BLOCK.get().get(f[4 + i]) == f[i], name + "'s waxed form un-waxes to the wrong block");
                helper.assertTrue(f[i].defaultBlockState().isRandomlyTicking() == (i < 3), name + " random-ticking should be " + (i < 3));
                helper.assertTrue(WeatheringCopper.getNext(f[4 + i]).isEmpty() && !f[4 + i].defaultBlockState().isRandomlyTicking(),
                        name + "'s waxed form must not weather");
            }
        }

        // Swapping in the silver maps must keep vanilla copper working.
        helper.assertTrue(WeatheringCopper.getNext(Blocks.COPPER_BLOCK).orElse(null) == Blocks.EXPOSED_COPPER, "vanilla copper weathering lost");
        helper.assertTrue(HoneycombItem.WAXABLES.get().get(Blocks.COPPER_BLOCK) == Blocks.WAXED_COPPER_BLOCK, "vanilla copper waxing lost");
        helper.succeed();
    }

    @GameTest(template = EMPTY)
    public static void honeycomb_and_axe_keep_block_properties(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();

        BlockPos pillar = new BlockPos(1, 1, 1);
        helper.setBlock(pillar, ModBlocks.SILVER_PILLAR.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X));
        useItemOn(helper, player, new ItemStack(Items.HONEYCOMB), pillar, Direction.UP);
        helper.assertBlockState(pillar, s -> s.is(ModBlocks.WAXED_SILVER_PILLAR.get()) && s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.X,
                () -> "honeycomb on an x-axis pillar should give an x-axis waxed pillar, got " + helper.getBlockState(pillar));
        useItemOn(helper, player, new ItemStack(Items.IRON_AXE), pillar, Direction.UP);
        helper.assertBlockState(pillar, s -> s.is(ModBlocks.SILVER_PILLAR.get()) && s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.X,
                () -> "axe on a waxed pillar should remove the wax and keep the axis, got " + helper.getBlockState(pillar));

        BlockPos oxidized = new BlockPos(3, 1, 1);
        helper.setBlock(oxidized, ModBlocks.OXIDIZED_SILVER_PILLAR.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z));
        useItemOn(helper, player, new ItemStack(Items.IRON_AXE), oxidized, Direction.UP);
        helper.assertBlockState(oxidized, s -> s.is(ModBlocks.WEATHERED_SILVER_PILLAR.get()) && s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Z,
                () -> "axe on an oxidized pillar should scrape one stage and keep the axis, got " + helper.getBlockState(oxidized));

        BlockPos slab = new BlockPos(1, 1, 3);
        helper.setBlock(slab, ModBlocks.SILVER_BRICK_SLAB.get().defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP));
        useItemOn(helper, player, new ItemStack(Items.HONEYCOMB), slab, Direction.UP);
        helper.assertBlockState(slab, s -> s.is(ModBlocks.WAXED_SILVER_BRICK_SLAB.get()) && s.getValue(SlabBlock.TYPE) == SlabType.TOP,
                () -> "honeycomb on a top slab should keep it a top slab, got " + helper.getBlockState(slab));

        BlockPos stairs = new BlockPos(3, 1, 3);
        helper.setBlock(stairs, ModBlocks.OXIDIZED_SILVER_BRICK_STAIRS.get().defaultBlockState().setValue(StairBlock.FACING, Direction.EAST));
        useItemOn(helper, player, new ItemStack(Items.IRON_AXE), stairs, Direction.UP);
        helper.assertBlockState(stairs, s -> s.is(ModBlocks.WEATHERED_SILVER_BRICK_STAIRS.get()) && s.getValue(StairBlock.FACING) == Direction.EAST,
                () -> "axe on oxidized brick stairs should scrape and keep the facing, got " + helper.getBlockState(stairs));
        helper.succeed();
    }

    // ---------------------------------------------------------------- placement

    @GameTest(template = EMPTY)
    public static void pillar_placement_follows_clicked_face(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        BlockPos support = new BlockPos(2, 1, 2);
        helper.setBlock(support, Blocks.STONE);

        assertPlacedAxis(helper, player, support, Direction.UP, Direction.Axis.Y);
        assertPlacedAxis(helper, player, support, Direction.EAST, Direction.Axis.X);
        assertPlacedAxis(helper, player, support, Direction.NORTH, Direction.Axis.Z);
        helper.succeed();
    }

    @GameTest(template = EMPTY)
    public static void brick_slab_doubles_and_drops_two(GameTestHelper helper) {
        Player player = helper.makeMockPlayer();
        BlockPos pos = new BlockPos(1, 1, 1);
        helper.setBlock(pos, ModBlocks.SILVER_BRICK_SLAB.get().defaultBlockState());
        useItemOn(helper, player, new ItemStack(ModItems.SILVER_BRICK_SLAB.get()), pos, Direction.UP);
        helper.assertBlockProperty(pos, SlabBlock.TYPE, SlabType.DOUBLE);

        List<ItemStack> drops = drops(helper, helper.getBlockState(pos), new ItemStack(Items.IRON_PICKAXE));
        int total = drops.stream().filter(s -> s.is(ModItems.SILVER_BRICK_SLAB.get())).mapToInt(ItemStack::getCount).sum();
        helper.assertTrue(total == 2, "a double silver brick slab should drop 2 slabs, got " + drops);
        helper.succeed();
    }

    // ---------------------------------------------------------------- tags

    @GameTest(template = EMPTY)
    public static void slab_and_stairs_tags_include_bricks(GameTestHelper helper) {
        for (RegistryObject<Item> slab : List.of(ModItems.SILVER_BRICK_SLAB, ModItems.EXPOSED_SILVER_BRICK_SLAB, ModItems.WEATHERED_SILVER_BRICK_SLAB, ModItems.OXIDIZED_SILVER_BRICK_SLAB,
                ModItems.WAXED_SILVER_BRICK_SLAB, ModItems.WAXED_EXPOSED_SILVER_BRICK_SLAB, ModItems.WAXED_WEATHERED_SILVER_BRICK_SLAB, ModItems.WAXED_OXIDIZED_SILVER_BRICK_SLAB)) {
            helper.assertTrue(new ItemStack(slab.get()).is(ItemTags.SLABS), slab.getId() + " missing from #minecraft:slabs (item)");
            helper.assertTrue(((net.minecraft.world.item.BlockItem) slab.get()).getBlock().defaultBlockState().is(net.minecraft.tags.BlockTags.SLABS),
                    slab.getId() + " missing from #minecraft:slabs (block)");
        }
        for (RegistryObject<Item> stairs : List.of(ModItems.SILVER_BRICK_STAIRS, ModItems.EXPOSED_SILVER_BRICK_STAIRS, ModItems.WEATHERED_SILVER_BRICK_STAIRS, ModItems.OXIDIZED_SILVER_BRICK_STAIRS,
                ModItems.WAXED_SILVER_BRICK_STAIRS, ModItems.WAXED_EXPOSED_SILVER_BRICK_STAIRS, ModItems.WAXED_WEATHERED_SILVER_BRICK_STAIRS, ModItems.WAXED_OXIDIZED_SILVER_BRICK_STAIRS)) {
            helper.assertTrue(new ItemStack(stairs.get()).is(ItemTags.STAIRS), stairs.getId() + " missing from #minecraft:stairs (item)");
            helper.assertTrue(((net.minecraft.world.item.BlockItem) stairs.get()).getBlock().defaultBlockState().is(net.minecraft.tags.BlockTags.STAIRS),
                    stairs.getId() + " missing from #minecraft:stairs (block)");
        }
        helper.succeed();
    }

    @GameTest(template = EMPTY)
    public static void sable_weight_tags(GameTestHelper helper) {
        TagKey<Block> heavy = TagKey.create(Registries.BLOCK, new ResourceLocation("sable", "heavy"));
        TagKey<Block> superLight = TagKey.create(Registries.BLOCK, new ResourceLocation("sable", "super_light"));
        TagKey<Block> quarterVolume = TagKey.create(Registries.BLOCK, new ResourceLocation("sable", "quarter_volume"));

        for (RegistryObject<? extends Block> b : List.of(ModBlocks.EXPOSED_SILVER, ModBlocks.WAXED_OXIDIZED_SILVER, ModBlocks.CUT_SILVER,
                ModBlocks.WAXED_EXPOSED_CHISELED_SILVER, ModBlocks.SILVER_BRICKS, ModBlocks.WAXED_WEATHERED_SILVER_BRICKS,
                ModBlocks.SILVER_PILLAR, ModBlocks.WAXED_OXIDIZED_SILVER_PILLAR)) {
            helper.assertTrue(b.get().defaultBlockState().is(heavy), b.getId() + " should be sable:heavy");
        }
        for (RegistryObject<? extends Block> g : List.of(ModBlocks.SILVER_GRATE, ModBlocks.WAXED_OXIDIZED_SILVER_GRATE)) {
            helper.assertTrue(g.get().defaultBlockState().is(superLight) && g.get().defaultBlockState().is(quarterVolume),
                    g.getId() + " should be sable:super_light and sable:quarter_volume");
        }
        helper.succeed();
    }

    // ---------------------------------------------------------------- Farmer's Delight knife

    @GameTest(template = EMPTY)
    public static void silver_knife_matches_farmers_delight_presence(GameTestHelper helper) {
        boolean fd = ModList.get().isLoaded("farmersdelight");
        String expected = System.getProperty("thesilverage.expectFarmersDelight");
        helper.assertTrue(expected == null || Boolean.parseBoolean(expected) == fd,
                "setup: the run expected Farmer's Delight loaded=" + expected + " but it is " + fd);
        Item knife = ModItems.SILVER_KNIFE.get();
        boolean inTab = ModItems.CREATIVE_TAB_ITEMS.contains(ModItems.SILVER_KNIFE);
        boolean recipeLoaded = helper.getLevel().getRecipeManager().byKey(new ResourceLocation(TheSilverAge.MOD_ID, "silver_knife")).isPresent();

        if (fd) {
            helper.assertTrue("vectorwing.farmersdelight.common.item.KnifeItem".equals(knife.getClass().getSuperclass().getName()),
                    "with Farmer's Delight the knife must extend FD's KnifeItem, got " + knife.getClass().getSuperclass().getName());
            helper.assertTrue(inTab, "with Farmer's Delight the knife belongs in the creative tab");
            helper.assertTrue(recipeLoaded, "with Farmer's Delight the knife recipe must load");
            assertCrafts(helper, knife, 1, "silver knife",
                    null, ModItems.SILVER_INGOT.get(), null,
                    null, Items.STICK, null,
                    null, null, null);
            TagKey<Item> fdKnives = TagKey.create(Registries.ITEM, new ResourceLocation("farmersdelight", "tools/knives"));
            helper.assertTrue(new ItemStack(knife).is(fdKnives), "knife missing from farmersdelight:tools/knives");

            // Setup anchor: FD's own knife is in the tag, so the next assertion is not vacuous.
            Item ironKnife = ForgeRegistries.ITEMS.getValue(new ResourceLocation("farmersdelight", "iron_knife"));
            helper.assertTrue(ironKnife != null && new ItemStack(ironKnife).is(CommonTags.Items.TOOLS_KNIVES),
                    "setup: farmersdelight:iron_knife should be in forge:tools/knives");

            // The Cutting Board accepts a tool through the recipe's tool ingredient.
            Object cutting = helper.getLevel().getRecipeManager().byKey(new ResourceLocation("farmersdelight", "cutting/allium")).orElse(null);
            helper.assertTrue(cutting != null, "setup: farmersdelight:cutting/allium recipe is missing");
            try {
                Ingredient tool = (Ingredient) cutting.getClass().getMethod("getTool").invoke(cutting);
                helper.assertTrue(tool.test(new ItemStack(knife)), "the Cutting Board's knife recipes do not accept the Silver Knife");
                helper.assertFalse(tool.test(new ItemStack(Items.STICK)), "setup: the cutting tool ingredient accepts anything");
            } catch (ReflectiveOperationException e) {
                helper.fail("could not read the cutting recipe's tool: " + e);
            }
        } else {
            helper.assertTrue(knife instanceof SwordItem, "without Farmer's Delight the knife falls back to a SwordItem, got " + knife.getClass().getName());
            helper.assertFalse(inTab, "without Farmer's Delight the knife must stay out of the creative tab");
            helper.assertFalse(recipeLoaded, "without Farmer's Delight the knife recipe must not load");
        }
        helper.assertTrue(new ItemStack(knife).is(CommonTags.Items.TOOLS_KNIVES), "knife missing from forge:tools/knives");
        helper.succeed();
    }

    // ---------------------------------------------------------------- Moon Dial tooltip

    @GameTest(template = EMPTY, batch = "time")
    public static void moon_dial_tooltip_names_every_phase(GameTestHelper helper) {
        String[] expected = {"full", "waning_gibbous", "third_quarter", "waning_crescent", "new", "waxing_crescent", "first_quarter", "waxing_gibbous"};
        ServerLevel level = helper.getLevel();
        long originalTime = level.getDayTime();
        try {
            for (int phase = 0; phase < 8; phase++) {
                level.setDayTime(phase * 24000L + 18000L); // midnight of that phase's day
                helper.assertTrue(level.getMoonPhase() == phase, "setup: expected moon phase " + phase + ", level reports " + level.getMoonPhase());
                helper.assertTrue(tooltipKey(level).equals("thesilverage.moon_phase." + expected[phase]),
                        "phase " + phase + " tooltip is " + tooltipKey(level) + ", expected " + expected[phase]);
                helper.assertTrue(LevelUtils.getMoonPhaseSignal(level) == phase * 2,
                        "at night the dial icon frame should be " + (phase * 2) + " for phase " + phase + ", got " + LevelUtils.getMoonPhaseSignal(level));

                // Outside the night the icon sits on a between-phases frame while the tooltip keeps the phase.
                for (long timeOfDay : new long[]{6000L, 23000L}) {
                    level.setDayTime(phase * 24000L + timeOfDay);
                    helper.assertTrue(tooltipKey(level).equals("thesilverage.moon_phase." + expected[phase]),
                            "phase " + phase + " at time " + timeOfDay + " tooltip is " + tooltipKey(level));
                    helper.assertTrue(LevelUtils.getMoonPhaseSignal(level) == (phase * 2 + 15) % 16,
                            "phase " + phase + " at time " + timeOfDay + " icon frame should be " + ((phase * 2 + 15) % 16) + ", got " + LevelUtils.getMoonPhaseSignal(level));
                }
            }

            // Nether day time follows the overworld, so use a non-full phase: only the fixed-time guard yields Full Moon.
            level.setDayTime(4 * 24000L + 18000L);
            ServerLevel nether = level.getServer().getLevel(Level.NETHER);
            helper.assertTrue(nether != null, "setup: the gametest server has no nether level");
            helper.assertTrue(nether.getMoonPhase() == 4, "setup: the nether should report the overworld's phase 4, got " + nether.getMoonPhase());
            helper.assertTrue(tooltipKey(nether).equals("thesilverage.moon_phase.full"), "a fixed-time dimension should read Full Moon, got " + tooltipKey(nether));
            helper.assertTrue(LevelUtils.getMoonPhaseSignal(nether) == 0, "a fixed-time dimension should show icon frame 0");
        } finally {
            level.setDayTime(originalTime);
        }

        helper.assertTrue(tooltipKey(null).equals("thesilverage.moon_phase.full"), "a null level should read Full Moon");
        helper.succeed();
    }

    // ---------------------------------------------------------------- recipe-override texture pack

    @GameTest(template = EMPTY, batch = "config")
    public static void recipe_override_pack_follows_config(GameTestHelper helper) throws Exception {
        // The first client pack scan runs before Forge loads COMMON configs. Reproduce that with an
        // unloaded spec: reading it directly must throw here, or this check proves nothing.
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.BooleanValue unloadedValue = builder.define("override_vanilla_recipes", true);
        ForgeConfigSpec unloadedSpec = builder.build();
        helper.assertFalse(unloadedSpec.isLoaded(), "setup: a freshly built spec should not be loaded");
        boolean directReadThrows = false;
        try {
            unloadedValue.get();
        } catch (IllegalStateException expected) {
            directReadThrows = true;
        }
        helper.assertTrue(directReadThrows, "setup: reading an unloaded config should throw in dev, which is what crashed the client");
        helper.assertTrue(RecipeOverridePackHandler.readOverride(unloadedSpec, unloadedValue), "before the config loads the pack should default to on");

        Path packRoot = RecipeOverridePackHandler.packRoot();
        helper.assertTrue(packRoot != null, "the recipe-override pack root is not in the mod");

        List<Pack> on = new ArrayList<>();
        RecipeOverridePackHandler.source(packRoot, () -> true).loadPacks(on::add);
        helper.assertTrue(on.size() == 1, "with overrides on, exactly one pack should be provided, got " + on.size());
        helper.assertTrue(RecipeOverridePackHandler.appliedOverride(), "the source should record that it applied the pack");
        Pack pack = on.get(0);
        helper.assertTrue(pack.getId().equals("builtin/thesilverage/recipe_overrides"), "unexpected pack id " + pack.getId());
        helper.assertTrue(pack.isRequired(), "the override pack must be required so it is selected without user action");
        try (PackResources resources = pack.open()) {
            for (String path : List.of("textures/block/comparator.png", "textures/block/repeater_on.png", "textures/block/brewing_stand.png",
                    "textures/item/repeater.png", "models/block/comparator_on_subtract.json", "models/block/repeater_4tick_on_locked.json")) {
                helper.assertTrue(resources.getResource(PackType.CLIENT_RESOURCES, new ResourceLocation("minecraft", path)) != null,
                        "override pack is missing minecraft:" + path);
            }
        }

        List<Pack> off = new ArrayList<>();
        RecipeOverridePackHandler.source(packRoot, () -> false).loadPacks(off::add);
        helper.assertTrue(off.isEmpty(), "with overrides off, no pack should be provided (vanilla textures)");
        helper.assertFalse(RecipeOverridePackHandler.appliedOverride(), "the source should record that it omitted the pack");

        List<RepositorySource> sources = new ArrayList<>();
        RecipeOverridePackHandler.onAddPackFinders(new AddPackFindersEvent(PackType.CLIENT_RESOURCES, sources::add));
        helper.assertTrue(sources.size() == 1, "expected one repository source for client resources, got " + sources.size());
        List<Pack> real = new ArrayList<>();
        sources.get(0).loadPacks(real::add); // reads the loaded config, writes nothing
        helper.assertTrue(real.size() == (Configuration.OVERRIDE_VANILLA_RECIPES.get() ? 1 : 0), "the registered source ignores the config value");

        List<RepositorySource> serverSources = new ArrayList<>();
        RecipeOverridePackHandler.onAddPackFinders(new AddPackFindersEvent(PackType.SERVER_DATA, serverSources::add));
        helper.assertTrue(serverSources.isEmpty(), "the texture pack must not register for server data");

        // The re-skins moved into the pack; the atlases must stay unconditional.
        helper.assertTrue(modResource("assets", "minecraft", "textures", "block", "comparator.png") == null,
                "comparator.png still ships unconditionally in assets/minecraft");
        helper.assertTrue(modResource("assets", "minecraft", "atlases", "armor_trims.json") != null,
                "assets/minecraft/atlases/armor_trims.json must stay unconditional");
        helper.succeed();
    }

    // ---------------------------------------------------------------- translations + creative tab

    @GameTest(template = EMPTY)
    public static void every_language_translates_everything(GameTestHelper helper) throws Exception {
        Set<String> required = new TreeSet<>();
        ForgeRegistries.ITEMS.getEntries().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(TheSilverAge.MOD_ID))
                .forEach(e -> required.add(e.getValue().getDescriptionId()));
        ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(TheSilverAge.MOD_ID))
                .forEach(e -> required.add(e.getValue().getDescriptionId()));

        JsonObject english = lang(helper, "en_us");
        for (String code : LANGUAGES) {
            JsonObject json = lang(helper, code);
            List<String> missing = required.stream().filter(k -> !json.has(k)).toList();
            helper.assertTrue(missing.isEmpty(), code + " has no translation for " + missing);
            List<String> behindEnglish = english.keySet().stream().filter(k -> !json.has(k)).toList();
            helper.assertTrue(behindEnglish.isEmpty(), code + " is missing keys present in en_us: " + behindEnglish);
            List<String> blank = json.entrySet().stream().filter(e -> e.getValue().getAsString().isBlank()).map(e -> e.getKey()).toList();
            helper.assertTrue(blank.isEmpty(), code + " has blank translations for " + blank);
        }
        helper.succeed();
    }

    @GameTest(template = EMPTY)
    public static void creative_tab_places_new_families(GameTestHelper helper) {
        List<String> order = ModItems.CREATIVE_TAB_ITEMS.stream().map(r -> r.getId().getPath()).toList();
        assertOrder(helper, order, "waxed_oxidized_cut_silver", "silver_bricks", "cut_silver_slab");
        assertOrder(helper, order, "silver_bricks", "silver_brick_slab", "silver_brick_stairs");
        assertOrder(helper, order, "waxed_oxidized_chiseled_silver", "silver_pillar", "silver_trapdoor");
        helper.succeed();
    }

    // ---------------------------------------------------------------- helpers

    private static List<ItemStack> drops(GameTestHelper helper, BlockState state, ItemStack tool) {
        return Block.getDrops(state, helper.getLevel(), helper.absolutePos(BlockPos.ZERO), null, null, tool);
    }

    private static void assertCrafts(GameTestHelper helper, Item result, int count, String what, Item... grid) {
        TransientCraftingContainer container = new TransientCraftingContainer(new AbstractContainerMenu(null, -1) {
            @Override
            public ItemStack quickMoveStack(Player player, int index) {
                return ItemStack.EMPTY;
            }

            @Override
            public boolean stillValid(Player player) {
                return true;
            }
        }, 3, 3);
        for (int i = 0; i < grid.length; i++) {
            if (grid[i] != null) {
                container.setItem(i, new ItemStack(grid[i]));
            }
        }
        ServerLevel level = helper.getLevel();
        ItemStack out = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, container, level)
                .map(r -> r.assemble(container, level.registryAccess()))
                .orElse(ItemStack.EMPTY);
        helper.assertTrue(out.is(result) && out.getCount() == count,
                what + ": expected " + count + "x " + ForgeRegistries.ITEMS.getKey(result) + ", got " + out.getCount() + "x " + ForgeRegistries.ITEMS.getKey(out.getItem()));
    }

    private static void assertPillarCraft(GameTestHelper helper, Item block, Item pillar) {
        assertCrafts(helper, pillar, 2, "pillar from two stacked " + ForgeRegistries.ITEMS.getKey(block),
                block, null, null,
                block, null, null,
                null, null, null);
    }

    private static void assertStonecuts(GameTestHelper helper, Item input, Item output, int count) {
        ServerLevel level = helper.getLevel();
        SimpleContainer container = new SimpleContainer(new ItemStack(input));
        int got = level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, container, level).stream()
                .map(r -> r.getResultItem(level.registryAccess()))
                .filter(s -> s.is(output))
                .mapToInt(ItemStack::getCount)
                .findFirst().orElse(-1);
        helper.assertTrue(got == count, "stonecutting " + ForgeRegistries.ITEMS.getKey(input) + " -> " + ForgeRegistries.ITEMS.getKey(output)
                + ": expected " + count + ", got " + (got < 0 ? "no recipe" : got));
    }

    private static void useItemOn(GameTestHelper helper, Player player, ItemStack stack, BlockPos relative, Direction face) {
        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        BlockPos absolute = helper.absolutePos(relative);
        stack.getItem().useOn(new UseOnContext(player, InteractionHand.MAIN_HAND,
                new BlockHitResult(Vec3.atCenterOf(absolute), face, absolute, false)));
    }

    private static void assertPlacedAxis(GameTestHelper helper, Player player, BlockPos support, Direction face, Direction.Axis axis) {
        useItemOn(helper, player, new ItemStack(ModItems.SILVER_PILLAR.get()), support, face);
        BlockPos placed = support.relative(face);
        helper.assertBlockState(placed, s -> s.is(ModBlocks.SILVER_PILLAR.get()) && s.getValue(RotatedPillarBlock.AXIS) == axis,
                () -> "clicking the " + face + " face should place a pillar on axis " + axis + ", got " + helper.getBlockState(placed));
    }

    private static String tooltipKey(Level level) {
        List<Component> tooltip = new ArrayList<>();
        ModItems.MOON_DIAL.get().appendHoverText(new ItemStack(ModItems.MOON_DIAL.get()), level, tooltip, TooltipFlag.NORMAL);
        return tooltip.isEmpty() || !(tooltip.get(0).getContents() instanceof TranslatableContents contents) ? "<none>" : contents.getKey();
    }

    private static Path modResource(String... path) {
        Path p = ModList.get().getModFileById(TheSilverAge.MOD_ID).getFile().findResource(path);
        return p != null && Files.exists(p) ? p : null;
    }

    private static JsonObject lang(GameTestHelper helper, String code) throws Exception {
        Path path = modResource("assets", TheSilverAge.MOD_ID, "lang", code + ".json");
        helper.assertTrue(path != null, "lang file " + code + ".json is not in the mod");
        return JsonParser.parseString(Files.readString(path)).getAsJsonObject();
    }

    private static void assertOrder(GameTestHelper helper, List<String> order, String... ids) {
        for (int i = 0; i + 1 < ids.length; i++) {
            int a = order.indexOf(ids[i]), b = order.indexOf(ids[i + 1]);
            helper.assertTrue(a >= 0 && b >= 0 && a < b, "creative tab should list " + ids[i] + " before " + ids[i + 1] + " (indices " + a + ", " + b + ")");
        }
    }

    @SafeVarargs
    private static Block[] blocks(RegistryObject<? extends Block>... objects) {
        Block[] out = new Block[objects.length];
        for (int i = 0; i < objects.length; i++) {
            out[i] = objects[i].get();
        }
        return out;
    }
}
