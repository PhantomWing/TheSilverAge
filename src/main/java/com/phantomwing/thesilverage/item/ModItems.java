package com.phantomwing.thesilverage.item;

import com.google.common.collect.Sets;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashSet;
import java.util.function.Function;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TheSilverAge.MOD_ID);
    public static LinkedHashSet<DeferredItem<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Silver items
    public static final DeferredItem<Item> SILVER_NUGGET = register("silver_nugget");
    public static final DeferredItem<Item> SILVER_INGOT = register("silver_ingot");
    public static final DeferredItem<Item> RAW_SILVER = register("raw_silver");

    // Silver tools
    public static final DeferredItem<Item> SILVER_SWORD = registerSword("silver_sword", ModToolTiers.SILVER);
    public static final DeferredItem<Item> SILVER_SHOVEL = registerShovel("silver_shovel", ModToolTiers.SILVER);
    public static final DeferredItem<Item> SILVER_PICKAXE = registerPickaxe("silver_pickaxe", ModToolTiers.SILVER);
    public static final DeferredItem<Item> SILVER_AXE = registerAxe("silver_axe", ModToolTiers.SILVER);
    public static final DeferredItem<Item> SILVER_HOE = registerHoe("silver_hoe", ModToolTiers.SILVER);

    // Silver armor
    public static final DeferredItem<Item> SILVER_HELMET = register("silver_helmet");
    public static final DeferredItem<Item> SILVER_CHESTPLATE = register("silver_chestplate");
    public static final DeferredItem<Item> SILVER_LEGGINGS = register("silver_leggings");
    public static final DeferredItem<Item> SILVER_BOOTS = register("silver_boots");

    // Silver blocks
    public static final DeferredItem<Item> SILVER_BLOCK = registerBlock(ModBlocks.SILVER_BLOCK);
    public static final DeferredItem<Item> RAW_SILVER_BLOCK = registerBlock(ModBlocks.RAW_SILVER_BLOCK);
    public static final DeferredItem<Item> SILVER_ORE = registerBlock(ModBlocks.SILVER_ORE);
    public static final DeferredItem<Item> DEEPSLATE_SILVER_ORE = registerBlock(ModBlocks.DEEPSLATE_SILVER_ORE);


    // Helper functions
    public static Item.Properties baseItem() {
        return new Item.Properties();
    }

    // Registry functions
    private static DeferredItem<Item> registerSword(String name, Tier tier) {
        Item.Properties baseProps = baseItem().attributes(SwordItem.createAttributes(tier, 3, -2.4f));
        return register(name, (props) -> new SwordItem(tier, props), baseProps);
    }

    private static DeferredItem<Item> registerShovel(String name, Tier tier) {
        Item.Properties baseProps = baseItem().attributes(ShovelItem.createAttributes(tier, 1.5f, -3.0f));
        return register(name, (props) -> new ShovelItem(tier, props), baseProps);
    }

    private static DeferredItem<Item> registerPickaxe(String name, Tier tier) {
        Item.Properties baseProps = baseItem().attributes(PickaxeItem.createAttributes(tier, 1.0f, -2.8f));
        return register(name, (props) -> new PickaxeItem(tier, props), baseProps);
    }

    private static DeferredItem<Item> registerAxe(String name, Tier tier) {
        Item.Properties baseProps = baseItem().attributes(AxeItem.createAttributes(tier, 5, -3.0f));
        return register(name, (props) -> new AxeItem(tier, props), baseProps);
    }

    private static DeferredItem<Item> registerHoe(String name, Tier tier) {
        Item.Properties baseProps = baseItem().attributes(HoeItem.createAttributes(tier, 0, -3.0f));
        return register(name, (props) -> new HoeItem(tier, props), baseProps);
    }

    private static DeferredItem<Item> registerBlock(DeferredBlock<Block> block) {
        return registerBlock(block, baseItem());
    }

    private static DeferredItem<Item> registerBlock(DeferredBlock<Block> block, Item.Properties properties) {
        String name = block.getRegisteredName().replaceFirst(TheSilverAge.MOD_ID + ":", "");
        return register(name, (props) -> new BlockItem(block.get(), props), properties);
    }

    private static DeferredItem<Item> register(String name) {
        return register(name, Item::new, baseItem());
    }

    private static DeferredItem<Item> register(String name, Item.Properties props) {
        return register(name, Item::new, props);
    }

    private static DeferredItem<Item> register(String name, Function<Item.Properties, Item> function, Item.Properties props) {
        DeferredItem<Item> item = ITEMS.register(name, () -> function.apply(props));
        CREATIVE_TAB_ITEMS.add(item);

        return item;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}