package com.phantomwing.thesilverage.compat.create;

import java.util.List;
import java.util.function.Supplier;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.simibubi.create.api.data.recipe.DeployingRecipeGen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class ModDeployingRecipeGen extends DeployingRecipeGen {

    @SuppressWarnings("unused")
    GeneratedRecipe
        SILVER_BLOCK = silverChain(
            List.of(lk(ModBlocks.SILVER_BLOCK), lk(ModBlocks.EXPOSED_SILVER), lk(ModBlocks.WEATHERED_SILVER), lk(ModBlocks.OXIDIZED_SILVER)),
            List.of(lk(ModBlocks.WAXED_SILVER_BLOCK), lk(ModBlocks.WAXED_EXPOSED_SILVER), lk(ModBlocks.WAXED_WEATHERED_SILVER), lk(ModBlocks.WAXED_OXIDIZED_SILVER))),
        CUT_SILVER = silverChain(
            List.of(lk(ModBlocks.CUT_SILVER), lk(ModBlocks.EXPOSED_CUT_SILVER), lk(ModBlocks.WEATHERED_CUT_SILVER), lk(ModBlocks.OXIDIZED_CUT_SILVER)),
            List.of(lk(ModBlocks.WAXED_CUT_SILVER), lk(ModBlocks.WAXED_EXPOSED_CUT_SILVER), lk(ModBlocks.WAXED_WEATHERED_CUT_SILVER), lk(ModBlocks.WAXED_OXIDIZED_CUT_SILVER))),
        CUT_SILVER_SLAB = silverChain(
            List.of(lk(ModBlocks.CUT_SILVER_SLAB), lk(ModBlocks.EXPOSED_CUT_SILVER_SLAB), lk(ModBlocks.WEATHERED_CUT_SILVER_SLAB), lk(ModBlocks.OXIDIZED_CUT_SILVER_SLAB)),
            List.of(lk(ModBlocks.WAXED_CUT_SILVER_SLAB), lk(ModBlocks.WAXED_EXPOSED_CUT_SILVER_SLAB), lk(ModBlocks.WAXED_WEATHERED_CUT_SILVER_SLAB), lk(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_SLAB))),
        CUT_SILVER_STAIRS = silverChain(
            List.of(lk(ModBlocks.CUT_SILVER_STAIRS), lk(ModBlocks.EXPOSED_CUT_SILVER_STAIRS), lk(ModBlocks.WEATHERED_CUT_SILVER_STAIRS), lk(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS)),
            List.of(lk(ModBlocks.WAXED_CUT_SILVER_STAIRS), lk(ModBlocks.WAXED_EXPOSED_CUT_SILVER_STAIRS), lk(ModBlocks.WAXED_WEATHERED_CUT_SILVER_STAIRS), lk(ModBlocks.WAXED_OXIDIZED_CUT_SILVER_STAIRS))),
        CHISELED_SILVER = silverChain(
            List.of(lk(ModBlocks.CHISELED_SILVER), lk(ModBlocks.EXPOSED_CHISELED_SILVER), lk(ModBlocks.WEATHERED_CHISELED_SILVER), lk(ModBlocks.OXIDIZED_CHISELED_SILVER)),
            List.of(lk(ModBlocks.WAXED_CHISELED_SILVER), lk(ModBlocks.WAXED_EXPOSED_CHISELED_SILVER), lk(ModBlocks.WAXED_WEATHERED_CHISELED_SILVER), lk(ModBlocks.WAXED_OXIDIZED_CHISELED_SILVER))),
        SILVER_GRATE = silverChain(
            List.of(lk(ModBlocks.SILVER_GRATE), lk(ModBlocks.EXPOSED_SILVER_GRATE), lk(ModBlocks.WEATHERED_SILVER_GRATE), lk(ModBlocks.OXIDIZED_SILVER_GRATE)),
            List.of(lk(ModBlocks.WAXED_SILVER_GRATE), lk(ModBlocks.WAXED_EXPOSED_SILVER_GRATE), lk(ModBlocks.WAXED_WEATHERED_SILVER_GRATE), lk(ModBlocks.WAXED_OXIDIZED_SILVER_GRATE))),
        SILVER_BULB = silverChain(
            List.of(lk(ModBlocks.SILVER_BULB), lk(ModBlocks.EXPOSED_SILVER_BULB), lk(ModBlocks.WEATHERED_SILVER_BULB), lk(ModBlocks.OXIDIZED_SILVER_BULB)),
            List.of(lk(ModBlocks.WAXED_SILVER_BULB), lk(ModBlocks.WAXED_EXPOSED_SILVER_BULB), lk(ModBlocks.WAXED_WEATHERED_SILVER_BULB), lk(ModBlocks.WAXED_OXIDIZED_SILVER_BULB))),
        SILVER_TRAPDOOR = silverChain(
            List.of(lk(ModBlocks.SILVER_TRAPDOOR), lk(ModBlocks.EXPOSED_SILVER_TRAPDOOR), lk(ModBlocks.WEATHERED_SILVER_TRAPDOOR), lk(ModBlocks.OXIDIZED_SILVER_TRAPDOOR)),
            List.of(lk(ModBlocks.WAXED_SILVER_TRAPDOOR), lk(ModBlocks.WAXED_EXPOSED_SILVER_TRAPDOOR), lk(ModBlocks.WAXED_WEATHERED_SILVER_TRAPDOOR), lk(ModBlocks.WAXED_OXIDIZED_SILVER_TRAPDOOR))),
        SILVER_DOOR = silverChain(
            List.of(lk(ModBlocks.SILVER_DOOR), lk(ModBlocks.EXPOSED_SILVER_DOOR), lk(ModBlocks.WEATHERED_SILVER_DOOR), lk(ModBlocks.OXIDIZED_SILVER_DOOR)),
            List.of(lk(ModBlocks.WAXED_SILVER_DOOR), lk(ModBlocks.WAXED_EXPOSED_SILVER_DOOR), lk(ModBlocks.WAXED_WEATHERED_SILVER_DOOR), lk(ModBlocks.WAXED_OXIDIZED_SILVER_DOOR)));

    public ModDeployingRecipeGen(PackOutput output) {
        super(output, TheSilverAge.MOD_ID);
    }

    private GeneratedRecipe silverChain(List<Supplier<ItemLike>> unwaxed, List<Supplier<ItemLike>> waxed) {
        // De-oxidation: higher stage + axe -> lower stage (3 per chain)
        for (int i = 1; i < unwaxed.size(); i++) {
            Supplier<ItemLike> from = unwaxed.get(i);
            Supplier<ItemLike> to = unwaxed.get(i - 1);
            createWithDeferredId(modIdWithSuffix(to, "_from_deoxidising"), b -> b
                    .require(from.get())
                    .require(ItemTags.AXES)
                    .toolNotConsumed()
                    .output(to.get()));
        }
        // Wax adding: unwaxed + honeycomb -> waxed (4 per chain)
        for (int i = 0; i < unwaxed.size(); i++) {
            Supplier<ItemLike> nonWaxed = unwaxed.get(i);
            Supplier<ItemLike> waxedBlock = waxed.get(i);
            createWithDeferredId(modIdWithSuffix(waxedBlock, "_from_adding_wax"), b -> b
                    .require(nonWaxed.get())
                    .require(Items.HONEYCOMB)
                    .toolNotConsumed()
                    .output(waxedBlock.get()));
        }
        // Wax removing: waxed + axe -> unwaxed (4 per chain)
        for (int i = 0; i < waxed.size(); i++) {
            Supplier<ItemLike> waxedBlock = waxed.get(i);
            Supplier<ItemLike> nonWaxed = unwaxed.get(i);
            createWithDeferredId(modIdWithSuffix(nonWaxed, "_from_wax_removing"), b -> b
                    .require(waxedBlock.get())
                    .require(ItemTags.AXES)
                    .toolNotConsumed()
                    .output(nonWaxed.get()));
        }
        return null;
    }

    private Supplier<ResourceLocation> modIdWithSuffix(Supplier<ItemLike> item, String suffix) {
        return () -> {
            ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(item.get().asItem());
            return new ResourceLocation(TheSilverAge.MOD_ID, registryName.getPath() + suffix);
        };
    }

    private static Supplier<ItemLike> lk(Supplier<? extends ItemLike> supplier) {
        return supplier::get;
    }
}
