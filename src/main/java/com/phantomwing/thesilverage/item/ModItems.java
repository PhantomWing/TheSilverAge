package com.phantomwing.thesilverage.item;

import com.google.common.collect.Sets;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.armor.ModArmorMaterials;
import com.phantomwing.thesilverage.tool.ModTiers;
import com.phantomwing.thesilverage.block.ModBlocks;
import net.minecraft.core.Holder;
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
    public static final DeferredItem<Item> SILVER_SWORD = registerSword("silver_sword", ModTiers.SILVER);
    public static final DeferredItem<Item> SILVER_SHOVEL = registerShovel("silver_shovel", ModTiers.SILVER);
    public static final DeferredItem<Item> SILVER_PICKAXE = registerPickaxe("silver_pickaxe", ModTiers.SILVER);
    public static final DeferredItem<Item> SILVER_AXE = registerAxe("silver_axe", ModTiers.SILVER);
    public static final DeferredItem<Item> SILVER_HOE = registerHoe("silver_hoe", ModTiers.SILVER);

    // Silver armor
    public static final DeferredItem<Item> SILVER_HELMET = registerArmor("silver_helmet", ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.HELMET, 10); // Iron is 15, Gold is 7, Leather is 5, Diamond is 33, Netherite is 37
    public static final DeferredItem<Item> SILVER_CHESTPLATE = registerArmor("silver_chestplate", ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, 10);
    public static final DeferredItem<Item> SILVER_LEGGINGS = registerArmor("silver_leggings", ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, 10);
    public static final DeferredItem<Item> SILVER_BOOTS = registerArmor("silver_boots", ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, 10);
    public static final DeferredItem<Item> SILVER_HORSE_ARMOR = register("silver_horse_armor", (props) -> new AnimalArmorItem(ModArmorMaterials.SILVER_ARMOR_MATERIAL, AnimalArmorItem.BodyType.EQUESTRIAN, false, props), baseItem().stacksTo(1));

    // Utility items
    public static final DeferredItem<Item> MOON_DIAL = register("moon_dial");
    public static final DeferredItem<Item> MOON_PHASE_DETECTOR = registerBlock(ModBlocks.MOON_PHASE_DETECTOR);

    // Silver blocks
    public static final DeferredItem<Item> SILVER_ORE = registerBlock(ModBlocks.SILVER_ORE);
    public static final DeferredItem<Item> DEEPSLATE_SILVER_ORE = registerBlock(ModBlocks.DEEPSLATE_SILVER_ORE);
    public static final DeferredItem<Item> RAW_SILVER_BLOCK = registerBlock(ModBlocks.RAW_SILVER_BLOCK);

    // Block of Silver
    public static final DeferredItem<Item> SILVER_BLOCK = registerBlock(ModBlocks.SILVER_BLOCK);
    public static final DeferredItem<Item> EXPOSED_SILVER = registerBlock(ModBlocks.EXPOSED_SILVER);
    public static final DeferredItem<Item> WEATHERED_SILVER = registerBlock(ModBlocks.WEATHERED_SILVER);
    public static final DeferredItem<Item> OXIDIZED_SILVER = registerBlock(ModBlocks.OXIDIZED_SILVER);
    public static final DeferredItem<Item> WAXED_SILVER_BLOCK = registerBlock(ModBlocks.WAXED_SILVER_BLOCK);
    public static final DeferredItem<Item> WAXED_EXPOSED_SILVER = registerBlock(ModBlocks.WAXED_EXPOSED_SILVER);
    public static final DeferredItem<Item> WAXED_WEATHERED_SILVER = registerBlock(ModBlocks.WAXED_WEATHERED_SILVER);
    public static final DeferredItem<Item> WAXED_OXIDIZED_SILVER = registerBlock(ModBlocks.WAXED_OXIDIZED_SILVER);

    // Cut Silver
    public static final DeferredItem<Item> CUT_SILVER = registerBlock(ModBlocks.CUT_SILVER);
    public static final DeferredItem<Item> EXPOSED_CUT_SILVER = registerBlock(ModBlocks.EXPOSED_CUT_SILVER);
    public static final DeferredItem<Item> WEATHERED_CUT_SILVER = registerBlock(ModBlocks.WEATHERED_CUT_SILVER);
    public static final DeferredItem<Item> OXIDIZED_CUT_SILVER = registerBlock(ModBlocks.OXIDIZED_CUT_SILVER);
    public static final DeferredItem<Item> WAXED_CUT_SILVER = registerBlock(ModBlocks.WAXED_CUT_SILVER);
    public static final DeferredItem<Item> WAXED_EXPOSED_CUT_SILVER = registerBlock(ModBlocks.WAXED_EXPOSED_CUT_SILVER);
    public static final DeferredItem<Item> WAXED_WEATHERED_CUT_SILVER = registerBlock(ModBlocks.WAXED_WEATHERED_CUT_SILVER);
    public static final DeferredItem<Item> WAXED_OXIDIZED_CUT_SILVER = registerBlock(ModBlocks.WAXED_OXIDIZED_CUT_SILVER);

    // Cut Silver Slab
    public static final DeferredItem<Item> CUT_SILVER_SLAB = registerBlock(ModBlocks.CUT_SILVER_SLAB);
    public static final DeferredItem<Item> EXPOSED_CUT_SILVER_SLAB = registerBlock(ModBlocks.EXPOSED_CUT_SILVER_SLAB);
    public static final DeferredItem<Item> WEATHERED_CUT_SILVER_SLAB = registerBlock(ModBlocks.WEATHERED_CUT_SILVER_SLAB);
    public static final DeferredItem<Item> OXIDIZED_CUT_SILVER_SLAB = registerBlock(ModBlocks.OXIDIZED_CUT_SILVER_SLAB);
    public static final DeferredItem<Item> WAXED_CUT_SILVER_SLAB = registerBlock(ModBlocks.WAXED_CUT_SILVER_SLAB);
    public static final DeferredItem<Item> WAXED_EXPOSED_CUT_SILVER_SLAB = registerBlock(ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB);
    public static final DeferredItem<Item> WAXED_WEATHERED_CUT_SILVER_SLAB = registerBlock(ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB);
    public static final DeferredItem<Item> WAXED_OXIDIZED_CUT_SILVER_SLAB = registerBlock(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB);

    // Cut Silver Stairs
    public static final DeferredItem<Item> CUT_SILVER_STAIRS = registerBlock(ModBlocks.CUT_SILVER_STAIRS);
    public static final DeferredItem<Item> EXPOSED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.EXPOSED_CUT_SILVER_STAIRS);
    public static final DeferredItem<Item> WEATHERED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.WEATHERED_CUT_SILVER_STAIRS);
    public static final DeferredItem<Item> OXIDIZED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS);
    public static final DeferredItem<Item> WAXED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.WAXED_CUT_SILVER_STAIRS);
    public static final DeferredItem<Item> WAXED_EXPOSED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS);
    public static final DeferredItem<Item> WAXED_WEATHERED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS);
    public static final DeferredItem<Item> WAXED_OXIDIZED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS);

    // Chiseled Silver
    public static final DeferredItem<Item> CHISELED_SILVER = registerBlock(ModBlocks.CHISELED_SILVER);
    public static final DeferredItem<Item> EXPOSED_CHISELED_SILVER = registerBlock(ModBlocks.EXPOSED_CHISELED_SILVER);
    public static final DeferredItem<Item> WEATHERED_CHISELED_SILVER = registerBlock(ModBlocks.WEATHERED_CHISELED_SILVER);
    public static final DeferredItem<Item> OXIDIZED_CHISELED_SILVER = registerBlock(ModBlocks.OXIDIZED_CHISELED_SILVER);
    public static final DeferredItem<Item> WAXED_CHISELED_SILVER = registerBlock(ModBlocks.WAXED_CHISELED_SILVER);
    public static final DeferredItem<Item> WAXED_EXPOSED_CHISELED_SILVER = registerBlock(ModBlocks.WAXED_EXPOSED_CHISELED_SILVER);
    public static final DeferredItem<Item> WAXED_WEATHERED_CHISELED_SILVER = registerBlock(ModBlocks.WAXED_WEATHERED_CHISELED_SILVER);
    public static final DeferredItem<Item> WAXED_OXIDIZED_CHISELED_SILVER = registerBlock(ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER);

    // Silver Grate
    public static final DeferredItem<Item> SILVER_GRATE = registerBlock(ModBlocks.SILVER_GRATE);
    public static final DeferredItem<Item> EXPOSED_SILVER_GRATE = registerBlock(ModBlocks.EXPOSED_SILVER_GRATE);
    public static final DeferredItem<Item> WEATHERED_SILVER_GRATE = registerBlock(ModBlocks.WEATHERED_SILVER_GRATE);
    public static final DeferredItem<Item> OXIDIZED_SILVER_GRATE = registerBlock(ModBlocks.OXIDIZED_SILVER_GRATE);
    public static final DeferredItem<Item> WAXED_SILVER_GRATE = registerBlock(ModBlocks.WAXED_SILVER_GRATE);
    public static final DeferredItem<Item> WAXED_EXPOSED_SILVER_GRATE = registerBlock(ModBlocks.WAXED_EXPOSED_SILVER_GRATE);
    public static final DeferredItem<Item> WAXED_WEATHERED_SILVER_GRATE = registerBlock(ModBlocks.WAXED_WEATHERED_SILVER_GRATE);
    public static final DeferredItem<Item> WAXED_OXIDIZED_SILVER_GRATE = registerBlock(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE);

    // Silver Trapdoor
    public static final DeferredItem<Item> SILVER_TRAPDOOR = registerBlock(ModBlocks.SILVER_TRAPDOOR);
    public static final DeferredItem<Item> EXPOSED_SILVER_TRAPDOOR = registerBlock(ModBlocks.EXPOSED_SILVER_TRAPDOOR);
    public static final DeferredItem<Item> WEATHERED_SILVER_TRAPDOOR = registerBlock(ModBlocks.WEATHERED_SILVER_TRAPDOOR);
    public static final DeferredItem<Item> OXIDIZED_SILVER_TRAPDOOR = registerBlock(ModBlocks.OXIDIZED_SILVER_TRAPDOOR);
    public static final DeferredItem<Item> WAXED_SILVER_TRAPDOOR = registerBlock(ModBlocks.WAXED_SILVER_TRAPDOOR);
    public static final DeferredItem<Item> WAXED_EXPOSED_SILVER_TRAPDOOR = registerBlock(ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR);
    public static final DeferredItem<Item> WAXED_WEATHERED_SILVER_TRAPDOOR = registerBlock(ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR);
    public static final DeferredItem<Item> WAXED_OXIDIZED_SILVER_TRAPDOOR = registerBlock(ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR);

    // Silver Door
    public static final DeferredItem<Item> SILVER_DOOR = registerBlock(ModBlocks.SILVER_DOOR);
    public static final DeferredItem<Item> EXPOSED_SILVER_DOOR = registerBlock(ModBlocks.EXPOSED_SILVER_DOOR);
    public static final DeferredItem<Item> WEATHERED_SILVER_DOOR = registerBlock(ModBlocks.WEATHERED_SILVER_DOOR);
    public static final DeferredItem<Item> OXIDIZED_SILVER_DOOR = registerBlock(ModBlocks.OXIDIZED_SILVER_DOOR);
    public static final DeferredItem<Item> WAXED_SILVER_DOOR = registerBlock(ModBlocks.WAXED_SILVER_DOOR);
    public static final DeferredItem<Item> WAXED_EXPOSED_SILVER_DOOR = registerBlock(ModBlocks.WAXED_EXPOSED_SILVER_DOOR);
    public static final DeferredItem<Item> WAXED_WEATHERED_SILVER_DOOR = registerBlock(ModBlocks.WAXED_WEATHERED_SILVER_DOOR);
    public static final DeferredItem<Item> WAXED_OXIDIZED_SILVER_DOOR = registerBlock(ModBlocks.WAXED_OXIDIZED_SILVER_DOOR);

    // Helper functions
    public static Item.Properties baseItem() {
        return new Item.Properties();
    }

    // Registry functions
    private static DeferredItem<Item> registerArmor(String name, Holder<ArmorMaterial> material, ArmorItem.Type armorItemType, int durabilityFactor) {
        Item.Properties baseProps = baseItem().durability(armorItemType.getDurability(durabilityFactor));
        return register(name, (props) -> new ArmorItem(material, armorItemType, props), baseProps);
    }


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
        Item.Properties baseProps = baseItem().attributes(AxeItem.createAttributes(tier, 4.5f, -3.0f));
        return register(name, (props) -> new AxeItem(tier, props), baseProps);
    }

    private static DeferredItem<Item> registerHoe(String name, Tier tier) {
        Item.Properties baseProps = baseItem().attributes(HoeItem.createAttributes(tier, -2.5f, -0.5f));
        return register(name, (props) -> new HoeItem(tier, props), baseProps);
    }

    private static <T extends Block> DeferredItem<Item> registerBlock(DeferredBlock<T> block) {
        return registerBlock(block, baseItem());
    }

    private static <T extends Block> DeferredItem<Item> registerBlock(DeferredBlock<T> block, Item.Properties properties) {
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