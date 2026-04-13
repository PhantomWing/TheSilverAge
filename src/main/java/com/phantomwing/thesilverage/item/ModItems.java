package com.phantomwing.thesilverage.item;

import com.google.common.collect.Sets;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.armor.ModArmorMaterials;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.tool.ModTiers;
import com.phantomwing.thesilverage.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashSet;
import java.util.function.Function;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TheSilverAge.MOD_ID);
    public static LinkedHashSet<RegistryObject<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Silver items
    public static final RegistryObject<Item> RAW_SILVER = register("raw_silver");
    public static final RegistryObject<Item> SILVER_INGOT = register("silver_ingot");
    public static final RegistryObject<Item> SILVER_NUGGET = register("silver_nugget");

    // Silver sheet is a Create-compat item (obtained via Mechanical Press).
    // Only appears in the creative tab when Create is loaded.
    public static final RegistryObject<Item> SILVER_SHEET = registerWithModCompat("silver_sheet", ModIds.CREATE);

    // Silver tools
    public static final RegistryObject<Item> SILVER_SHOVEL = registerShovel("silver_shovel", ModTiers.SILVER);
    public static final RegistryObject<Item> SILVER_PICKAXE = registerPickaxe("silver_pickaxe", ModTiers.SILVER);
    public static final RegistryObject<Item> SILVER_AXE = registerAxe("silver_axe", ModTiers.SILVER);
    public static final RegistryObject<Item> SILVER_HOE = registerHoe("silver_hoe", ModTiers.SILVER);
    public static final RegistryObject<Item> SILVER_SWORD = registerSword("silver_sword", ModTiers.SILVER);

    // Silver armor
    public static final RegistryObject<Item> SILVER_HELMET = registerArmor("silver_helmet", ModArmorMaterials.SILVER, ArmorItem.Type.HELMET);
    public static final RegistryObject<Item> SILVER_CHESTPLATE = registerArmor("silver_chestplate", ModArmorMaterials.SILVER, ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<Item> SILVER_LEGGINGS = registerArmor("silver_leggings", ModArmorMaterials.SILVER, ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<Item> SILVER_BOOTS = registerArmor("silver_boots", ModArmorMaterials.SILVER, ArmorItem.Type.BOOTS);
    public static final RegistryObject<Item> SILVER_HORSE_ARMOR = register("silver_horse_armor",
            (props) -> new HorseArmorItem(8, new ResourceLocation(TheSilverAge.MOD_ID, "textures/entity/horse/armor/horse_armor_silver.png"), props),
            baseItem());

    // Utility items
    public static final RegistryObject<Item> MOON_DIAL = register("moon_dial");
    public static final RegistryObject<Item> MOON_PHASE_DETECTOR = registerBlock(ModBlocks.MOON_PHASE_DETECTOR);

    // Silver blocks
    public static final RegistryObject<Item> SILVER_ORE = registerBlock(ModBlocks.SILVER_ORE);
    public static final RegistryObject<Item> DEEPSLATE_SILVER_ORE = registerBlock(ModBlocks.DEEPSLATE_SILVER_ORE);
    public static final RegistryObject<Item> RAW_SILVER_BLOCK = registerBlock(ModBlocks.RAW_SILVER_BLOCK);

    // Block of Silver
    public static final RegistryObject<Item> SILVER_BLOCK = registerBlock(ModBlocks.SILVER_BLOCK);
    public static final RegistryObject<Item> EXPOSED_SILVER = registerBlock(ModBlocks.EXPOSED_SILVER);
    public static final RegistryObject<Item> WEATHERED_SILVER = registerBlock(ModBlocks.WEATHERED_SILVER);
    public static final RegistryObject<Item> OXIDIZED_SILVER = registerBlock(ModBlocks.OXIDIZED_SILVER);
    public static final RegistryObject<Item> WAXED_SILVER_BLOCK = registerBlock(ModBlocks.WAXED_SILVER_BLOCK);
    public static final RegistryObject<Item> WAXED_EXPOSED_SILVER = registerBlock(ModBlocks.WAXED_EXPOSED_SILVER);
    public static final RegistryObject<Item> WAXED_WEATHERED_SILVER = registerBlock(ModBlocks.WAXED_WEATHERED_SILVER);
    public static final RegistryObject<Item> WAXED_OXIDIZED_SILVER = registerBlock(ModBlocks.WAXED_OXIDIZED_SILVER);

    // Cut Silver
    public static final RegistryObject<Item> CUT_SILVER = registerBlock(ModBlocks.CUT_SILVER);
    public static final RegistryObject<Item> EXPOSED_CUT_SILVER = registerBlock(ModBlocks.EXPOSED_CUT_SILVER);
    public static final RegistryObject<Item> WEATHERED_CUT_SILVER = registerBlock(ModBlocks.WEATHERED_CUT_SILVER);
    public static final RegistryObject<Item> OXIDIZED_CUT_SILVER = registerBlock(ModBlocks.OXIDIZED_CUT_SILVER);
    public static final RegistryObject<Item> WAXED_CUT_SILVER = registerBlock(ModBlocks.WAXED_CUT_SILVER);
    public static final RegistryObject<Item> WAXED_EXPOSED_CUT_SILVER = registerBlock(ModBlocks.WAXED_EXPOSED_CUT_SILVER);
    public static final RegistryObject<Item> WAXED_WEATHERED_CUT_SILVER = registerBlock(ModBlocks.WAXED_WEATHERED_CUT_SILVER);
    public static final RegistryObject<Item> WAXED_OXIDIZED_CUT_SILVER = registerBlock(ModBlocks.WAXED_OXIDIZED_CUT_SILVER);

    // Cut Silver Slab
    public static final RegistryObject<Item> CUT_SILVER_SLAB = registerBlock(ModBlocks.CUT_SILVER_SLAB);
    public static final RegistryObject<Item> EXPOSED_CUT_SILVER_SLAB = registerBlock(ModBlocks.EXPOSED_CUT_SILVER_SLAB);
    public static final RegistryObject<Item> WEATHERED_CUT_SILVER_SLAB = registerBlock(ModBlocks.WEATHERED_CUT_SILVER_SLAB);
    public static final RegistryObject<Item> OXIDIZED_CUT_SILVER_SLAB = registerBlock(ModBlocks.OXIDIZED_CUT_SILVER_SLAB);
    public static final RegistryObject<Item> WAXED_CUT_SILVER_SLAB = registerBlock(ModBlocks.WAXED_CUT_SILVER_SLAB);
    public static final RegistryObject<Item> WAXED_EXPOSED_CUT_SILVER_SLAB = registerBlock(ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB);
    public static final RegistryObject<Item> WAXED_WEATHERED_CUT_SILVER_SLAB = registerBlock(ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB);
    public static final RegistryObject<Item> WAXED_OXIDIZED_CUT_SILVER_SLAB = registerBlock(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB);

    // Cut Silver Stairs
    public static final RegistryObject<Item> CUT_SILVER_STAIRS = registerBlock(ModBlocks.CUT_SILVER_STAIRS);
    public static final RegistryObject<Item> EXPOSED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.EXPOSED_CUT_SILVER_STAIRS);
    public static final RegistryObject<Item> WEATHERED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.WEATHERED_CUT_SILVER_STAIRS);
    public static final RegistryObject<Item> OXIDIZED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS);
    public static final RegistryObject<Item> WAXED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.WAXED_CUT_SILVER_STAIRS);
    public static final RegistryObject<Item> WAXED_EXPOSED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS);
    public static final RegistryObject<Item> WAXED_WEATHERED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS);
    public static final RegistryObject<Item> WAXED_OXIDIZED_CUT_SILVER_STAIRS = registerBlock(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS);

    // Chiseled Silver
    public static final RegistryObject<Item> CHISELED_SILVER = registerBlock(ModBlocks.CHISELED_SILVER);
    public static final RegistryObject<Item> EXPOSED_CHISELED_SILVER = registerBlock(ModBlocks.EXPOSED_CHISELED_SILVER);
    public static final RegistryObject<Item> WEATHERED_CHISELED_SILVER = registerBlock(ModBlocks.WEATHERED_CHISELED_SILVER);
    public static final RegistryObject<Item> OXIDIZED_CHISELED_SILVER = registerBlock(ModBlocks.OXIDIZED_CHISELED_SILVER);
    public static final RegistryObject<Item> WAXED_CHISELED_SILVER = registerBlock(ModBlocks.WAXED_CHISELED_SILVER);
    public static final RegistryObject<Item> WAXED_EXPOSED_CHISELED_SILVER = registerBlock(ModBlocks.WAXED_EXPOSED_CHISELED_SILVER);
    public static final RegistryObject<Item> WAXED_WEATHERED_CHISELED_SILVER = registerBlock(ModBlocks.WAXED_WEATHERED_CHISELED_SILVER);
    public static final RegistryObject<Item> WAXED_OXIDIZED_CHISELED_SILVER = registerBlock(ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER);

    // Silver Trapdoor
    public static final RegistryObject<Item> SILVER_TRAPDOOR = registerBlock(ModBlocks.SILVER_TRAPDOOR);
    public static final RegistryObject<Item> EXPOSED_SILVER_TRAPDOOR = registerBlock(ModBlocks.EXPOSED_SILVER_TRAPDOOR);
    public static final RegistryObject<Item> WEATHERED_SILVER_TRAPDOOR = registerBlock(ModBlocks.WEATHERED_SILVER_TRAPDOOR);
    public static final RegistryObject<Item> OXIDIZED_SILVER_TRAPDOOR = registerBlock(ModBlocks.OXIDIZED_SILVER_TRAPDOOR);
    public static final RegistryObject<Item> WAXED_SILVER_TRAPDOOR = registerBlock(ModBlocks.WAXED_SILVER_TRAPDOOR);
    public static final RegistryObject<Item> WAXED_EXPOSED_SILVER_TRAPDOOR = registerBlock(ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR);
    public static final RegistryObject<Item> WAXED_WEATHERED_SILVER_TRAPDOOR = registerBlock(ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR);
    public static final RegistryObject<Item> WAXED_OXIDIZED_SILVER_TRAPDOOR = registerBlock(ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR);

    // Silver Door
    public static final RegistryObject<Item> SILVER_DOOR = registerBlock(ModBlocks.SILVER_DOOR);
    public static final RegistryObject<Item> EXPOSED_SILVER_DOOR = registerBlock(ModBlocks.EXPOSED_SILVER_DOOR);
    public static final RegistryObject<Item> WEATHERED_SILVER_DOOR = registerBlock(ModBlocks.WEATHERED_SILVER_DOOR);
    public static final RegistryObject<Item> OXIDIZED_SILVER_DOOR = registerBlock(ModBlocks.OXIDIZED_SILVER_DOOR);
    public static final RegistryObject<Item> WAXED_SILVER_DOOR = registerBlock(ModBlocks.WAXED_SILVER_DOOR);
    public static final RegistryObject<Item> WAXED_EXPOSED_SILVER_DOOR = registerBlock(ModBlocks.WAXED_EXPOSED_SILVER_DOOR);
    public static final RegistryObject<Item> WAXED_WEATHERED_SILVER_DOOR = registerBlock(ModBlocks.WAXED_WEATHERED_SILVER_DOOR);
    public static final RegistryObject<Item> WAXED_OXIDIZED_SILVER_DOOR = registerBlock(ModBlocks.WAXED_OXIDIZED_SILVER_DOOR);

    // Silver Grate
    public static final RegistryObject<Item> SILVER_GRATE = registerBlock(ModBlocks.SILVER_GRATE);
    public static final RegistryObject<Item> EXPOSED_SILVER_GRATE = registerBlock(ModBlocks.EXPOSED_SILVER_GRATE);
    public static final RegistryObject<Item> WEATHERED_SILVER_GRATE = registerBlock(ModBlocks.WEATHERED_SILVER_GRATE);
    public static final RegistryObject<Item> OXIDIZED_SILVER_GRATE = registerBlock(ModBlocks.OXIDIZED_SILVER_GRATE);
    public static final RegistryObject<Item> WAXED_SILVER_GRATE = registerBlock(ModBlocks.WAXED_SILVER_GRATE);
    public static final RegistryObject<Item> WAXED_EXPOSED_SILVER_GRATE = registerBlock(ModBlocks.WAXED_EXPOSED_SILVER_GRATE);
    public static final RegistryObject<Item> WAXED_WEATHERED_SILVER_GRATE = registerBlock(ModBlocks.WAXED_WEATHERED_SILVER_GRATE);
    public static final RegistryObject<Item> WAXED_OXIDIZED_SILVER_GRATE = registerBlock(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE);

    // Silver Bulb
    public static final RegistryObject<Item> SILVER_BULB = registerBlock(ModBlocks.SILVER_BULB);
    public static final RegistryObject<Item> EXPOSED_SILVER_BULB = registerBlock(ModBlocks.EXPOSED_SILVER_BULB);
    public static final RegistryObject<Item> WEATHERED_SILVER_BULB = registerBlock(ModBlocks.WEATHERED_SILVER_BULB);
    public static final RegistryObject<Item> OXIDIZED_SILVER_BULB = registerBlock(ModBlocks.OXIDIZED_SILVER_BULB);
    public static final RegistryObject<Item> WAXED_SILVER_BULB = registerBlock(ModBlocks.WAXED_SILVER_BULB);
    public static final RegistryObject<Item> WAXED_EXPOSED_SILVER_BULB = registerBlock(ModBlocks.WAXED_EXPOSED_SILVER_BULB);
    public static final RegistryObject<Item> WAXED_WEATHERED_SILVER_BULB = registerBlock(ModBlocks.WAXED_WEATHERED_SILVER_BULB);
    public static final RegistryObject<Item> WAXED_OXIDIZED_SILVER_BULB = registerBlock(ModBlocks.WAXED_OXIDIZED_SILVER_BULB);

    // Helper functions
    public static Item.Properties baseItem() {
        return new Item.Properties();
    }

    // Registry functions
    private static RegistryObject<Item> registerArmor(String name, ArmorMaterial material, ArmorItem.Type armorItemType) {
        return register(name, (props) -> new ArmorItem(material, armorItemType, props), baseItem());
    }

    private static RegistryObject<Item> registerSword(String name, Tier tier) {
        return register(name, (props) -> new SwordItem(tier, 3, -2.4f, props), baseItem());
    }

    private static RegistryObject<Item> registerShovel(String name, Tier tier) {
        return register(name, (props) -> new ShovelItem(tier, 1.5f, -3.0f, props), baseItem());
    }

    private static RegistryObject<Item> registerPickaxe(String name, Tier tier) {
        return register(name, (props) -> new PickaxeItem(tier, 1, -2.8f, props), baseItem());
    }

    private static RegistryObject<Item> registerAxe(String name, Tier tier) {
        return register(name, (props) -> new AxeItem(tier, 6.0f, -3.1f, props), baseItem());
    }

    private static RegistryObject<Item> registerHoe(String name, Tier tier) {
        return register(name, (props) -> new HoeItem(tier, -2, -1.0f, props), baseItem());
    }

    private static RegistryObject<Item> registerBlock(RegistryObject<? extends Block> block) {
        return registerBlock(block, baseItem());
    }

    private static RegistryObject<Item> registerBlock(RegistryObject<? extends Block> block, Item.Properties properties) {
        String name = block.getId().getPath();
        return register(name, (props) -> new BlockItem(block.get(), props), properties);
    }

    private static RegistryObject<Item> register(String name) {
        return register(name, Item::new, baseItem());
    }

    private static RegistryObject<Item> register(String name, Item.Properties props) {
        return register(name, Item::new, props);
    }

    /** Register an item that only appears in the creative tab when the given mod is loaded. */
    private static RegistryObject<Item> registerWithModCompat(String name, String modId) {
        RegistryObject<Item> item = ITEMS.register(name, () -> new Item(baseItem()));
        if (ModList.get().isLoaded(modId)) {
            CREATIVE_TAB_ITEMS.add(item);
        }
        return item;
    }

    private static RegistryObject<Item> register(String name, Function<Item.Properties, Item> function, Item.Properties props) {
        RegistryObject<Item> item = ITEMS.register(name, () -> function.apply(props));
        CREATIVE_TAB_ITEMS.add(item);

        return item;
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
