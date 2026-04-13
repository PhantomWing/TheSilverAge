package com.phantomwing.thesilverage.compat.create;

import java.util.List;
import java.util.function.Supplier;

import com.phantomwing.thesilverage.TheSilverAge;
import com.phantomwing.thesilverage.block.ModBlocks;
import com.simibubi.create.api.data.recipe.FillingRecipeGen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;

public class ModFillingRecipeGen extends FillingRecipeGen {

    private static final int WATER_AMOUNT = 250;

    @SuppressWarnings("unused")
    GeneratedRecipe
        SILVER_BLOCK = oxidationChain(List.of(lk(ModBlocks.SILVER_BLOCK), lk(ModBlocks.EXPOSED_SILVER), lk(ModBlocks.WEATHERED_SILVER), lk(ModBlocks.OXIDIZED_SILVER))),
        CUT_SILVER = oxidationChain(List.of(lk(ModBlocks.CUT_SILVER), lk(ModBlocks.EXPOSED_CUT_SILVER), lk(ModBlocks.WEATHERED_CUT_SILVER), lk(ModBlocks.OXIDIZED_CUT_SILVER))),
        CUT_SILVER_SLAB = oxidationChain(List.of(lk(ModBlocks.CUT_SILVER_SLAB), lk(ModBlocks.EXPOSED_CUT_SILVER_SLAB), lk(ModBlocks.WEATHERED_CUT_SILVER_SLAB), lk(ModBlocks.OXIDIZED_CUT_SILVER_SLAB))),
        CUT_SILVER_STAIRS = oxidationChain(List.of(lk(ModBlocks.CUT_SILVER_STAIRS), lk(ModBlocks.EXPOSED_CUT_SILVER_STAIRS), lk(ModBlocks.WEATHERED_CUT_SILVER_STAIRS), lk(ModBlocks.OXIDIZED_CUT_SILVER_STAIRS))),
        CHISELED_SILVER = oxidationChain(List.of(lk(ModBlocks.CHISELED_SILVER), lk(ModBlocks.EXPOSED_CHISELED_SILVER), lk(ModBlocks.WEATHERED_CHISELED_SILVER), lk(ModBlocks.OXIDIZED_CHISELED_SILVER))),
        SILVER_GRATE = oxidationChain(List.of(lk(ModBlocks.SILVER_GRATE), lk(ModBlocks.EXPOSED_SILVER_GRATE), lk(ModBlocks.WEATHERED_SILVER_GRATE), lk(ModBlocks.OXIDIZED_SILVER_GRATE))),
        SILVER_BULB = oxidationChain(List.of(lk(ModBlocks.SILVER_BULB), lk(ModBlocks.EXPOSED_SILVER_BULB), lk(ModBlocks.WEATHERED_SILVER_BULB), lk(ModBlocks.OXIDIZED_SILVER_BULB))),
        SILVER_TRAPDOOR = oxidationChain(List.of(lk(ModBlocks.SILVER_TRAPDOOR), lk(ModBlocks.EXPOSED_SILVER_TRAPDOOR), lk(ModBlocks.WEATHERED_SILVER_TRAPDOOR), lk(ModBlocks.OXIDIZED_SILVER_TRAPDOOR))),
        SILVER_DOOR = oxidationChain(List.of(lk(ModBlocks.SILVER_DOOR), lk(ModBlocks.EXPOSED_SILVER_DOOR), lk(ModBlocks.WEATHERED_SILVER_DOOR), lk(ModBlocks.OXIDIZED_SILVER_DOOR)));

    public ModFillingRecipeGen(PackOutput output) {
        super(output, TheSilverAge.MOD_ID);
    }

    private GeneratedRecipe oxidationChain(List<Supplier<ItemLike>> chain) {
        GeneratedRecipe last = null;
        for (int i = 0; i < chain.size() - 1; i++) {
            Supplier<ItemLike> from = chain.get(i);
            Supplier<ItemLike> to = chain.get(i + 1);
            last = createWithDeferredId(modIdWithSuffix(to, "_from_oxidising"), b -> b
                    .require(Fluids.WATER, WATER_AMOUNT)
                    .require(from.get())
                    .output(to.get()));
        }
        return last;
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
