package com.phantomwing.thesilverage.loot;

import com.mojang.serialization.Codec;
import com.phantomwing.thesilverage.TheSilverAge;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, TheSilverAge.MOD_ID);

    public static final RegistryObject<Codec<AddItemModifier>> ADD_ITEM =
            LOOT_MODIFIERS.register("add_item", () -> AddItemModifier.CODEC.get());
    public static final RegistryObject<Codec<ReplaceItemModifier>> REPLACE_ITEM =
            LOOT_MODIFIERS.register("replace_item", () -> ReplaceItemModifier.CODEC.get());
    public static final RegistryObject<Codec<SilverfishDropsModifier>> SILVERFISH_DROP_SILVER =
            LOOT_MODIFIERS.register("silverfish_drop_silver_modifier", () -> SilverfishDropsModifier.CODEC.get());

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIERS.register(eventBus);
    }
}
