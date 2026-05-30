package com.phantomwing.thesilverage.item;

import com.google.common.collect.Sets;
import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.armor.ModArmorMaterials;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.phantomwing.thesilverage.compat.ModIds;
import com.phantomwing.thesilverage.item.custom.MoonDialItem;
import com.phantomwing.thesilverage.platform.CommonPlatform;
import com.phantomwing.thesilverage.platform.KnifePlatform;
import com.phantomwing.thesilverage.tool.ModTiers;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;

import java.util.LinkedHashSet;
import java.util.function.Function;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(TheSilverAge.MOD_ID, Registries.ITEM);
    public static LinkedHashSet<RegistrySupplier<Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

    // Silver items
    public static final RegistrySupplier<Item> RAW_SILVER = register("raw_silver");
    public static final RegistrySupplier<Item> SILVER_INGOT = register("silver_ingot");
    public static final RegistrySupplier<Item> SILVER_NUGGET = register("silver_nugget");
    // Silver sheet is a Create-compat item (obtained via Mechanical Press).
    // Only appears in the creative tab when Create is loaded.
    public static final RegistrySupplier<Item> SILVER_SHEET = registerWithModCompat("silver_sheet", ModIds.CREATE);

    // Silver tools
    public static final RegistrySupplier<Item> SILVER_SHOVEL = registerShovel("silver_shovel", ModTiers.SILVER);
    public static final RegistrySupplier<Item> SILVER_PICKAXE = registerPickaxe("silver_pickaxe", ModTiers.SILVER);
    public static final RegistrySupplier<Item> SILVER_AXE = registerAxe("silver_axe", ModTiers.SILVER);
    public static final RegistrySupplier<Item> SILVER_HOE = registerHoe("silver_hoe", ModTiers.SILVER);
    public static final RegistrySupplier<Item> SILVER_SWORD = registerSword("silver_sword", ModTiers.SILVER);
    // Farmer's Delight compat: a real FD KnifeItem when FD is present, a plain
    // SwordItem fallback otherwise (so the mod loads standalone). Only appears in
    // the creative tab when FD is loaded — same pattern as the Create-gated sheet.
    public static final RegistrySupplier<Item> SILVER_KNIFE = registerKnife("silver_knife", ModTiers.SILVER, ModIds.FARMERS_DELIGHT);

    // Silver armor
    public static final RegistrySupplier<Item> SILVER_HELMET = registerArmor("silver_helmet", ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.HELMET, 10); // Iron is 15, Gold is 7, Leather is 5, Diamond is 33, Netherite is 37
    public static final RegistrySupplier<Item> SILVER_CHESTPLATE = registerArmor("silver_chestplate", ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, 10);
    public static final RegistrySupplier<Item> SILVER_LEGGINGS = registerArmor("silver_leggings", ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, 10);
    public static final RegistrySupplier<Item> SILVER_BOOTS = registerArmor("silver_boots", ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, 10);
    public static final RegistrySupplier<Item> SILVER_HORSE_ARMOR = register("silver_horse_armor", (props) -> new AnimalArmorItem(ModArmorMaterials.SILVER_ARMOR_MATERIAL, AnimalArmorItem.BodyType.EQUESTRIAN, false, props), baseItem().stacksTo(1));

    // Utility items
    public static final RegistrySupplier<Item> MOON_DIAL = register("moon_dial", MoonDialItem::new, baseItem());
    public static final RegistrySupplier<Item> MOON_PHASE_DETECTOR = registerBlock("moon_phase_detector", ModBlocks.MOON_PHASE_DETECTOR);

    // Silver blocks
    public static final RegistrySupplier<Item> SILVER_ORE = registerBlock("silver_ore", ModBlocks.SILVER_ORE);
    public static final RegistrySupplier<Item> DEEPSLATE_SILVER_ORE = registerBlock("deepslate_silver_ore", ModBlocks.DEEPSLATE_SILVER_ORE);
    public static final RegistrySupplier<Item> RAW_SILVER_BLOCK = registerBlock("raw_silver_block", ModBlocks.RAW_SILVER_BLOCK);

    // Block of Silver
    public static final RegistrySupplier<Item> SILVER_BLOCK = registerBlock("silver_block", ModBlocks.SILVER_BLOCK);
    public static final RegistrySupplier<Item> EXPOSED_SILVER = registerBlock("exposed_silver", ModBlocks.EXPOSED_SILVER);
    public static final RegistrySupplier<Item> WEATHERED_SILVER = registerBlock("weathered_silver", ModBlocks.WEATHERED_SILVER);
    public static final RegistrySupplier<Item> OXIDIZED_SILVER = registerBlock("oxidized_silver", ModBlocks.OXIDIZED_SILVER);
    public static final RegistrySupplier<Item> WAXED_SILVER_BLOCK = registerBlock("waxed_silver_block", ModBlocks.WAXED_SILVER_BLOCK);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_SILVER = registerBlock("waxed_exposed_silver", ModBlocks.WAXED_EXPOSED_SILVER);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_SILVER = registerBlock("waxed_weathered_silver", ModBlocks.WAXED_WEATHERED_SILVER);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_SILVER = registerBlock("waxed_oxidized_silver", ModBlocks.WAXED_OXIDIZED_SILVER);

    // Cut Silver
    public static final RegistrySupplier<Item> CUT_SILVER = registerBlock("cut_silver", ModBlocks.CUT_SILVER);
    public static final RegistrySupplier<Item> EXPOSED_CUT_SILVER = registerBlock("exposed_cut_silver", ModBlocks.EXPOSED_CUT_SILVER);
    public static final RegistrySupplier<Item> WEATHERED_CUT_SILVER = registerBlock("weathered_cut_silver", ModBlocks.WEATHERED_CUT_SILVER);
    public static final RegistrySupplier<Item> OXIDIZED_CUT_SILVER = registerBlock("oxidized_cut_silver", ModBlocks.OXIDIZED_CUT_SILVER);
    public static final RegistrySupplier<Item> WAXED_CUT_SILVER = registerBlock("waxed_cut_silver", ModBlocks.WAXED_CUT_SILVER);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_CUT_SILVER = registerBlock("waxed_exposed_cut_silver", ModBlocks.WAXED_EXPOSED_CUT_SILVER);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_CUT_SILVER = registerBlock("waxed_weathered_cut_silver", ModBlocks.WAXED_WEATHERED_CUT_SILVER);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_CUT_SILVER = registerBlock("waxed_oxidized_cut_silver", ModBlocks.WAXED_OXIDIZED_CUT_SILVER);

    // Silver Bricks
    public static final RegistrySupplier<Item> SILVER_BRICKS = registerBlock("silver_bricks", ModBlocks.SILVER_BRICKS);
    public static final RegistrySupplier<Item> EXPOSED_SILVER_BRICKS = registerBlock("exposed_silver_bricks", ModBlocks.EXPOSED_SILVER_BRICKS);
    public static final RegistrySupplier<Item> WEATHERED_SILVER_BRICKS = registerBlock("weathered_silver_bricks", ModBlocks.WEATHERED_SILVER_BRICKS);
    public static final RegistrySupplier<Item> OXIDIZED_SILVER_BRICKS = registerBlock("oxidized_silver_bricks", ModBlocks.OXIDIZED_SILVER_BRICKS);
    public static final RegistrySupplier<Item> WAXED_SILVER_BRICKS = registerBlock("waxed_silver_bricks", ModBlocks.WAXED_SILVER_BRICKS);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_SILVER_BRICKS = registerBlock("waxed_exposed_silver_bricks", ModBlocks.WAXED_EXPOSED_SILVER_BRICKS);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_SILVER_BRICKS = registerBlock("waxed_weathered_silver_bricks", ModBlocks.WAXED_WEATHERED_SILVER_BRICKS);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_SILVER_BRICKS = registerBlock("waxed_oxidized_silver_bricks", ModBlocks.WAXED_OXIDIZED_SILVER_BRICKS);

    // Silver Brick Slab
    public static final RegistrySupplier<Item> SILVER_BRICK_SLAB = registerBlock("silver_brick_slab", ModBlocks.SILVER_BRICK_SLAB);
    public static final RegistrySupplier<Item> EXPOSED_SILVER_BRICK_SLAB = registerBlock("exposed_silver_brick_slab", ModBlocks.EXPOSED_SILVER_BRICK_SLAB);
    public static final RegistrySupplier<Item> WEATHERED_SILVER_BRICK_SLAB = registerBlock("weathered_silver_brick_slab", ModBlocks.WEATHERED_SILVER_BRICK_SLAB);
    public static final RegistrySupplier<Item> OXIDIZED_SILVER_BRICK_SLAB = registerBlock("oxidized_silver_brick_slab", ModBlocks.OXIDIZED_SILVER_BRICK_SLAB);
    public static final RegistrySupplier<Item> WAXED_SILVER_BRICK_SLAB = registerBlock("waxed_silver_brick_slab", ModBlocks.WAXED_SILVER_BRICK_SLAB);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_SILVER_BRICK_SLAB = registerBlock("waxed_exposed_silver_brick_slab", ModBlocks.WAXED_EXPOSED_SILVER_BRICK_SLAB);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_SILVER_BRICK_SLAB = registerBlock("waxed_weathered_silver_brick_slab", ModBlocks.WAXED_WEATHERED_SILVER_BRICK_SLAB);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_SILVER_BRICK_SLAB = registerBlock("waxed_oxidized_silver_brick_slab", ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_SLAB);

    // Silver Brick Stairs
    public static final RegistrySupplier<Item> SILVER_BRICK_STAIRS = registerBlock("silver_brick_stairs", ModBlocks.SILVER_BRICK_STAIRS);
    public static final RegistrySupplier<Item> EXPOSED_SILVER_BRICK_STAIRS = registerBlock("exposed_silver_brick_stairs", ModBlocks.EXPOSED_SILVER_BRICK_STAIRS);
    public static final RegistrySupplier<Item> WEATHERED_SILVER_BRICK_STAIRS = registerBlock("weathered_silver_brick_stairs", ModBlocks.WEATHERED_SILVER_BRICK_STAIRS);
    public static final RegistrySupplier<Item> OXIDIZED_SILVER_BRICK_STAIRS = registerBlock("oxidized_silver_brick_stairs", ModBlocks.OXIDIZED_SILVER_BRICK_STAIRS);
    public static final RegistrySupplier<Item> WAXED_SILVER_BRICK_STAIRS = registerBlock("waxed_silver_brick_stairs", ModBlocks.WAXED_SILVER_BRICK_STAIRS);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_SILVER_BRICK_STAIRS = registerBlock("waxed_exposed_silver_brick_stairs", ModBlocks.WAXED_EXPOSED_SILVER_BRICK_STAIRS);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_SILVER_BRICK_STAIRS = registerBlock("waxed_weathered_silver_brick_stairs", ModBlocks.WAXED_WEATHERED_SILVER_BRICK_STAIRS);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_SILVER_BRICK_STAIRS = registerBlock("waxed_oxidized_silver_brick_stairs", ModBlocks.WAXED_OXIDIZED_SILVER_BRICK_STAIRS);

    // Cut Silver Slab
    public static final RegistrySupplier<Item> CUT_SILVER_SLAB = registerBlock("cut_silver_slab", ModBlocks.CUT_SILVER_SLAB);
    public static final RegistrySupplier<Item> EXPOSED_CUT_SILVER_SLAB = registerBlock("exposed_cut_silver_slab", ModBlocks.EXPOSED_CUT_SILVER_SLAB);
    public static final RegistrySupplier<Item> WEATHERED_CUT_SILVER_SLAB = registerBlock("weathered_cut_silver_slab", ModBlocks.WEATHERED_CUT_SILVER_SLAB);
    public static final RegistrySupplier<Item> OXIDIZED_CUT_SILVER_SLAB = registerBlock("oxidized_cut_silver_slab", ModBlocks.OXIDIZED_CUT_SILVER_SLAB);
    public static final RegistrySupplier<Item> WAXED_CUT_SILVER_SLAB = registerBlock("waxed_cut_silver_slab", ModBlocks.WAXED_CUT_SILVER_SLAB);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_CUT_SILVER_SLAB = registerBlock("waxed_exposed_cut_silver_slab", ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_CUT_SILVER_SLAB = registerBlock("waxed_weathered_cut_silver_slab", ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_CUT_SILVER_SLAB = registerBlock("waxed_oxidized_cut_silver_slab", ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB);

    // Cut Silver Stairs
    public static final RegistrySupplier<Item> CUT_SILVER_STAIRS = registerBlock("cut_silver_stairs", ModBlocks.CUT_SILVER_STAIRS);
    public static final RegistrySupplier<Item> EXPOSED_CUT_SILVER_STAIRS = registerBlock("exposed_cut_silver_stairs", ModBlocks.EXPOSED_CUT_SILVER_STAIRS);
    public static final RegistrySupplier<Item> WEATHERED_CUT_SILVER_STAIRS = registerBlock("weathered_cut_silver_stairs", ModBlocks.WEATHERED_CUT_SILVER_STAIRS);
    public static final RegistrySupplier<Item> OXIDIZED_CUT_SILVER_STAIRS = registerBlock("oxidized_cut_silver_stairs", ModBlocks.OXIDIZED_CUT_SILVER_STAIRS);
    public static final RegistrySupplier<Item> WAXED_CUT_SILVER_STAIRS = registerBlock("waxed_cut_silver_stairs", ModBlocks.WAXED_CUT_SILVER_STAIRS);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_CUT_SILVER_STAIRS = registerBlock("waxed_exposed_cut_silver_stairs", ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_CUT_SILVER_STAIRS = registerBlock("waxed_weathered_cut_silver_stairs", ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_CUT_SILVER_STAIRS = registerBlock("waxed_oxidized_cut_silver_stairs", ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS);

    // Chiseled Silver
    public static final RegistrySupplier<Item> CHISELED_SILVER = registerBlock("chiseled_silver", ModBlocks.CHISELED_SILVER);
    public static final RegistrySupplier<Item> EXPOSED_CHISELED_SILVER = registerBlock("exposed_chiseled_silver", ModBlocks.EXPOSED_CHISELED_SILVER);
    public static final RegistrySupplier<Item> WEATHERED_CHISELED_SILVER = registerBlock("weathered_chiseled_silver", ModBlocks.WEATHERED_CHISELED_SILVER);
    public static final RegistrySupplier<Item> OXIDIZED_CHISELED_SILVER = registerBlock("oxidized_chiseled_silver", ModBlocks.OXIDIZED_CHISELED_SILVER);
    public static final RegistrySupplier<Item> WAXED_CHISELED_SILVER = registerBlock("waxed_chiseled_silver", ModBlocks.WAXED_CHISELED_SILVER);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_CHISELED_SILVER = registerBlock("waxed_exposed_chiseled_silver", ModBlocks.WAXED_EXPOSED_CHISELED_SILVER);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_CHISELED_SILVER = registerBlock("waxed_weathered_chiseled_silver", ModBlocks.WAXED_WEATHERED_CHISELED_SILVER);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_CHISELED_SILVER = registerBlock("waxed_oxidized_chiseled_silver", ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER);

    // Silver Pillar
    public static final RegistrySupplier<Item> SILVER_PILLAR = registerBlock("silver_pillar", ModBlocks.SILVER_PILLAR);
    public static final RegistrySupplier<Item> EXPOSED_SILVER_PILLAR = registerBlock("exposed_silver_pillar", ModBlocks.EXPOSED_SILVER_PILLAR);
    public static final RegistrySupplier<Item> WEATHERED_SILVER_PILLAR = registerBlock("weathered_silver_pillar", ModBlocks.WEATHERED_SILVER_PILLAR);
    public static final RegistrySupplier<Item> OXIDIZED_SILVER_PILLAR = registerBlock("oxidized_silver_pillar", ModBlocks.OXIDIZED_SILVER_PILLAR);
    public static final RegistrySupplier<Item> WAXED_SILVER_PILLAR = registerBlock("waxed_silver_pillar", ModBlocks.WAXED_SILVER_PILLAR);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_SILVER_PILLAR = registerBlock("waxed_exposed_silver_pillar", ModBlocks.WAXED_EXPOSED_SILVER_PILLAR);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_SILVER_PILLAR = registerBlock("waxed_weathered_silver_pillar", ModBlocks.WAXED_WEATHERED_SILVER_PILLAR);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_SILVER_PILLAR = registerBlock("waxed_oxidized_silver_pillar", ModBlocks.WAXED_OXIDIZED_SILVER_PILLAR);

    // Silver Grate
    public static final RegistrySupplier<Item> SILVER_GRATE = registerBlock("silver_grate", ModBlocks.SILVER_GRATE);
    public static final RegistrySupplier<Item> EXPOSED_SILVER_GRATE = registerBlock("exposed_silver_grate", ModBlocks.EXPOSED_SILVER_GRATE);
    public static final RegistrySupplier<Item> WEATHERED_SILVER_GRATE = registerBlock("weathered_silver_grate", ModBlocks.WEATHERED_SILVER_GRATE);
    public static final RegistrySupplier<Item> OXIDIZED_SILVER_GRATE = registerBlock("oxidized_silver_grate", ModBlocks.OXIDIZED_SILVER_GRATE);
    public static final RegistrySupplier<Item> WAXED_SILVER_GRATE = registerBlock("waxed_silver_grate", ModBlocks.WAXED_SILVER_GRATE);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_SILVER_GRATE = registerBlock("waxed_exposed_silver_grate", ModBlocks.WAXED_EXPOSED_SILVER_GRATE);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_SILVER_GRATE = registerBlock("waxed_weathered_silver_grate", ModBlocks.WAXED_WEATHERED_SILVER_GRATE);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_SILVER_GRATE = registerBlock("waxed_oxidized_silver_grate", ModBlocks.WAXED_OXIDIZED_SILVER_GRATE);

    // Silver Bulb
    public static final RegistrySupplier<Item> SILVER_BULB = registerBlock("silver_bulb", ModBlocks.SILVER_BULB);
    public static final RegistrySupplier<Item> EXPOSED_SILVER_BULB = registerBlock("exposed_silver_bulb", ModBlocks.EXPOSED_SILVER_BULB);
    public static final RegistrySupplier<Item> WEATHERED_SILVER_BULB = registerBlock("weathered_silver_bulb", ModBlocks.WEATHERED_SILVER_BULB);
    public static final RegistrySupplier<Item> OXIDIZED_SILVER_BULB = registerBlock("oxidized_silver_bulb", ModBlocks.OXIDIZED_SILVER_BULB);
    public static final RegistrySupplier<Item> WAXED_SILVER_BULB = registerBlock("waxed_silver_bulb", ModBlocks.WAXED_SILVER_BULB);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_SILVER_BULB = registerBlock("waxed_exposed_silver_bulb", ModBlocks.WAXED_EXPOSED_SILVER_BULB);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_SILVER_BULB = registerBlock("waxed_weathered_silver_bulb", ModBlocks.WAXED_WEATHERED_SILVER_BULB);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_SILVER_BULB = registerBlock("waxed_oxidized_silver_bulb", ModBlocks.WAXED_OXIDIZED_SILVER_BULB);

    // Silver Trapdoor
    public static final RegistrySupplier<Item> SILVER_TRAPDOOR = registerBlock("silver_trapdoor", ModBlocks.SILVER_TRAPDOOR);
    public static final RegistrySupplier<Item> EXPOSED_SILVER_TRAPDOOR = registerBlock("exposed_silver_trapdoor", ModBlocks.EXPOSED_SILVER_TRAPDOOR);
    public static final RegistrySupplier<Item> WEATHERED_SILVER_TRAPDOOR = registerBlock("weathered_silver_trapdoor", ModBlocks.WEATHERED_SILVER_TRAPDOOR);
    public static final RegistrySupplier<Item> OXIDIZED_SILVER_TRAPDOOR = registerBlock("oxidized_silver_trapdoor", ModBlocks.OXIDIZED_SILVER_TRAPDOOR);
    public static final RegistrySupplier<Item> WAXED_SILVER_TRAPDOOR = registerBlock("waxed_silver_trapdoor", ModBlocks.WAXED_SILVER_TRAPDOOR);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_SILVER_TRAPDOOR = registerBlock("waxed_exposed_silver_trapdoor", ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_SILVER_TRAPDOOR = registerBlock("waxed_weathered_silver_trapdoor", ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_SILVER_TRAPDOOR = registerBlock("waxed_oxidized_silver_trapdoor", ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR);

    // Silver Door
    public static final RegistrySupplier<Item> SILVER_DOOR = registerBlock("silver_door", ModBlocks.SILVER_DOOR);
    public static final RegistrySupplier<Item> EXPOSED_SILVER_DOOR = registerBlock("exposed_silver_door", ModBlocks.EXPOSED_SILVER_DOOR);
    public static final RegistrySupplier<Item> WEATHERED_SILVER_DOOR = registerBlock("weathered_silver_door", ModBlocks.WEATHERED_SILVER_DOOR);
    public static final RegistrySupplier<Item> OXIDIZED_SILVER_DOOR = registerBlock("oxidized_silver_door", ModBlocks.OXIDIZED_SILVER_DOOR);
    public static final RegistrySupplier<Item> WAXED_SILVER_DOOR = registerBlock("waxed_silver_door", ModBlocks.WAXED_SILVER_DOOR);
    public static final RegistrySupplier<Item> WAXED_EXPOSED_SILVER_DOOR = registerBlock("waxed_exposed_silver_door", ModBlocks.WAXED_EXPOSED_SILVER_DOOR);
    public static final RegistrySupplier<Item> WAXED_WEATHERED_SILVER_DOOR = registerBlock("waxed_weathered_silver_door", ModBlocks.WAXED_WEATHERED_SILVER_DOOR);
    public static final RegistrySupplier<Item> WAXED_OXIDIZED_SILVER_DOOR = registerBlock("waxed_oxidized_silver_door", ModBlocks.WAXED_OXIDIZED_SILVER_DOOR);

    // Helper functions
    public static Item.Properties baseItem() {
        return new Item.Properties();
    }

    // Registry functions
    private static RegistrySupplier<Item> registerArmor(String name, Holder<ArmorMaterial> material, ArmorItem.Type armorItemType, int durabilityFactor) {
        Item.Properties baseProps = baseItem().durability(armorItemType.getDurability(durabilityFactor));
        return register(name, (props) -> new ArmorItem(material, armorItemType, props), baseProps);
    }


    private static RegistrySupplier<Item> registerSword(String name, Tier tier) {
        Item.Properties baseProps = baseItem().attributes(SwordItem.createAttributes(tier, 3, -2.4f));
        return register(name, (props) -> new SwordItem(tier, props), baseProps);
    }

    /**
     * Register the Silver Knife. The concrete class is chosen per loader by
     * {@link com.phantomwing.thesilverage.platform.KnifePlatform} (FD's KnifeItem
     * when present, SwordItem fallback otherwise), so the item is always
     * registered but the FD-referencing class only loads when FD is installed.
     * Attack attributes ({@code 0.5}, {@code -2.0}) match FD's own knives, set
     * here via the vanilla {@link DiggerItem#createAttributes}. The item only
     * joins the creative tab when {@code modId} is loaded.
     */
    private static RegistrySupplier<Item> registerKnife(String name, Tier tier, String modId) {
        Item.Properties props = baseItem().attributes(DiggerItem.createAttributes(tier, 0.5f, -2.0f));
        RegistrySupplier<Item> item = ITEMS.register(name, () -> KnifePlatform.createSilverKnife(props, tier));
        if (CommonPlatform.isModLoaded(modId)) {
            CREATIVE_TAB_ITEMS.add(item);
        }
        return item;
    }

    private static RegistrySupplier<Item> registerShovel(String name, Tier tier) {
        Item.Properties baseProps = baseItem().attributes(ShovelItem.createAttributes(tier, 1.5f, -3.0f));
        return register(name, (props) -> new ShovelItem(tier, props), baseProps);
    }

    private static RegistrySupplier<Item> registerPickaxe(String name, Tier tier) {
        Item.Properties baseProps = baseItem().attributes(PickaxeItem.createAttributes(tier, 1.0f, -2.8f));
        return register(name, (props) -> new PickaxeItem(tier, props), baseProps);
    }

    private static RegistrySupplier<Item> registerAxe(String name, Tier tier) {
        Item.Properties baseProps = baseItem().attributes(AxeItem.createAttributes(tier, 4.5f, -3.0f));
        return register(name, (props) -> new AxeItem(tier, props), baseProps);
    }

    private static RegistrySupplier<Item> registerHoe(String name, Tier tier) {
        Item.Properties baseProps = baseItem().attributes(HoeItem.createAttributes(tier, -2.5f, -0.5f));
        return register(name, (props) -> new HoeItem(tier, props), baseProps);
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlock(String name, RegistrySupplier<T> block) {
        return registerBlock(name, block, baseItem());
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlock(String name, RegistrySupplier<T> block, Item.Properties properties) {
        return register(name, (props) -> new BlockItem(block.get(), props), properties);
    }

    private static RegistrySupplier<Item> register(String name) {
        return register(name, Item::new, baseItem());
    }

    /** Register an item that only appears in the creative tab when the given mod is loaded. */
    private static RegistrySupplier<Item> registerWithModCompat(String name, String modId) {
        RegistrySupplier<Item> item = ITEMS.register(name, () -> new Item(baseItem()));
        if (CommonPlatform.isModLoaded(modId)) {
            CREATIVE_TAB_ITEMS.add(item);
        }
        return item;
    }

    @SuppressWarnings("unused")
    private static RegistrySupplier<Item> register(String name, Item.Properties props) {
        return register(name, Item::new, props);
    }

    private static RegistrySupplier<Item> register(String name, Function<Item.Properties, Item> function, Item.Properties props) {
        RegistrySupplier<Item> item = ITEMS.register(name, () -> function.apply(props));
        CREATIVE_TAB_ITEMS.add(item);

        return item;
    }

    public static void register() {
        ITEMS.register();
    }
}
