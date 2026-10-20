package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.tags.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/** Game tests for the tags silver joins so vanilla and other mods treat it as their own. Listed in {@link GameTests}. */
public final class SilverTagsGameTest {
    private static final TagKey<Block> CHAINS = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "chains"));
    private static final TagKey<Item> CHAIN_ITEMS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "chains"));
    private static final TagKey<Block> DENSE_ORES = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ore_rates/dense"));
    private static final TagKey<Block> SINGULAR_ORES = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", "ore_rates/singular"));
    private static final TagKey<Item> WEREWOLVES_SILVER_TOOLS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("werewolves", "tools/silver"));
    private static final TagKey<Item> WEREWOLVES_SILVER_ARMOR = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("werewolves", "armor/silver"));

    private SilverTagsGameTest() {
    }

    /** Silver ore snaps a goat's horn and is dense, the pickaxe gets a cluster's full drop, villagers open silver doors, the chain is a chain. */
    public static void silverJoinsVanillaTags(GameTestHelper helper) {
        if (!ModBlocks.SILVER_ORE.get().defaultBlockState().is(BlockTags.SNAPS_GOAT_HORN)) {
            fail(helper, "Silver Ore isn't in minecraft:snaps_goat_horn");
        } else if (!new ItemStack(ModItems.SILVER_PICKAXE.get()).is(ItemTags.CLUSTER_MAX_HARVESTABLES)) {
            fail(helper, "The Silver Pickaxe isn't in minecraft:cluster_max_harvestables");
        } else if (!ModBlocks.SILVER_DOOR.get().defaultBlockState().is(BlockTags.MOB_INTERACTABLE_DOORS)
                || !ModBlocks.WAXED_OXIDIZED_SILVER_DOOR.get().defaultBlockState().is(BlockTags.MOB_INTERACTABLE_DOORS)) {
            fail(helper, "Silver Doors aren't in minecraft:mob_interactable_doors");
        } else if (!ModBlocks.SILVER_CHAIN.get().defaultBlockState().is(CHAINS)
                || !new ItemStack(ModItems.WAXED_OXIDIZED_SILVER_CHAIN.get()).is(CHAIN_ITEMS)) {
            fail(helper, "Silver Chains aren't in c:chains");
        } else if (!ModBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState().is(DENSE_ORES)
                || ModBlocks.SILVER_ORE.get().defaultBlockState().is(SINGULAR_ORES)) {
            fail(helper, "Silver ores aren't in c:ore_rates/dense alone");
        } else if (!EntityType.HUSK.is(ModTags.EntityTypes.CAN_WEAR_SILVER_ARMOR)
                || !EntityType.VINDICATOR.is(ModTags.EntityTypes.CAN_HOLD_SILVER_TOOLS)) {
            fail(helper, "Husks or vindicators are missing from the silver gear tags");
        } else {
            helper.succeed();
        }
    }

    /**
     * Silver tools and armour are in Werewolves: Become a Beast's silver tags, so a werewolf hit with a silver weapon,
     * or a werewolf player wearing silver, is weakened as by its own silver. The tags load whether or not it's installed.
     */
    public static void silverCountsAsSilverForWerewolves(GameTestHelper helper) {
        if (!new ItemStack(ModItems.SILVER_SWORD.get()).is(WEREWOLVES_SILVER_TOOLS)
                || !new ItemStack(ModItems.SILVER_KNIFE.get()).is(WEREWOLVES_SILVER_TOOLS)) {
            fail(helper, "Silver tools aren't in werewolves:tools/silver");
        } else if (!new ItemStack(ModItems.SILVER_CHESTPLATE.get()).is(WEREWOLVES_SILVER_ARMOR)) {
            fail(helper, "Silver armor isn't in werewolves:armor/silver");
        } else {
            helper.succeed();
        }
    }
}
