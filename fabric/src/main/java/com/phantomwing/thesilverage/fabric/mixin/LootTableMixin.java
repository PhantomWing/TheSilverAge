package com.phantomwing.thesilverage.fabric.mixin;

import com.phantomwing.thesilverage.fabric.loot.SilverLootTableId;
import com.phantomwing.thesilverage.loot.SilverLootAlgorithms;
import com.phantomwing.thesilverage.loot.SilverLootSpec;
import com.phantomwing.thesilverage.platform.CommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/** Fabric parity for the NeoForge Global Loot Modifiers: applies Silver loot injections at the loot-roll site. */
@Mixin(LootTable.class)
public abstract class LootTableMixin implements SilverLootTableId {
    @Unique
    @Nullable
    private Identifier thesilverage$lootTableId;

    @Override
    @Nullable
    public Identifier thesilverage$getLootTableId() {
        return this.thesilverage$lootTableId;
    }

    @Override
    public void thesilverage$setLootTableId(Identifier id) {
        this.thesilverage$lootTableId = id;
    }

    // Container/structure loot path: targets the private getRandomItems(LootContext) that all list overloads funnel through, so REPLACE can mutate what rolled.
    @Inject(
            method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;",
            at = @At("RETURN")
    )
    private void thesilverage$applySilverLoot(LootContext context,
                                              CallbackInfoReturnable<ObjectArrayList<ItemStack>> cir) {
        Identifier tableId = this.thesilverage$lootTableId;
        ObjectArrayList<ItemStack> generatedLoot = cir.getReturnValue();
        if (tableId == null || generatedLoot == null) {
            return;
        }
        thesilverage$applyMatchingEntries(tableId, context, generatedLoot);
    }

    // Entity/consumer loot path: mob drops use the disjoint getRandomItems(LootParams,long,Consumer) which never hits the private list method, so it needs its own injection (no double-apply).
    @Inject(
            method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;JLjava/util/function/Consumer;)V",
            at = @At("TAIL")
    )
    private void thesilverage$applySilverEntityDrops(LootParams params, long seed,
                                                     Consumer<ItemStack> consumer, CallbackInfo ci) {
        Identifier tableId = this.thesilverage$lootTableId;
        if (tableId == null) {
            return;
        }

        // Mirror the vanilla overload's context construction so the random draws match a normal roll.
        LootContext context = new LootContext.Builder(params)
                .withOptionalRandomSeed(seed)
                .create(Optional.empty());

        ObjectArrayList<ItemStack> appended = new ObjectArrayList<>();
        thesilverage$applyMatchingEntries(tableId, context, appended);
        for (ItemStack stack : appended) {
            consumer.accept(stack);
        }
    }

    /** Shared per-entry GLM-parity logic used by both the container and entity paths. */
    @Unique
    private void thesilverage$applyMatchingEntries(Identifier tableId,
                                                   LootContext context,
                                                   ObjectArrayList<ItemStack> sink) {
        RandomSource random = context.getRandom();
        boolean structureLootEnabled = CommonConfig.generateStructureLoot();
        boolean silverfishEnabled = CommonConfig.silverfishDropSilver();

        for (SilverLootSpec.Entry entry : SilverLootSpec.entries()) {
            if (!entry.targetLootTable().equals(tableId)) {
                continue;
            }

            boolean enabled = entry.op() == SilverLootSpec.Op.SILVERFISH
                    ? silverfishEnabled
                    : structureLootEnabled;
            if (!enabled) {
                continue;
            }

            if (random.nextFloat() >= entry.chance()) {
                continue;
            }

            Item item = entry.item().get();
            switch (entry.op()) {
                case ADD -> SilverLootAlgorithms.applyAddItem(
                        sink, context, item, entry.min(), entry.max());
                case REPLACE -> {
                    List<Item> removed = entry.removedItems().stream()
                            .map(Supplier::get).toList();
                    SilverLootAlgorithms.applyReplaceItem(
                            sink, context, item, removed, entry.min(), entry.max());
                }
                case SILVERFISH -> SilverLootAlgorithms.applySilverfishDrops(
                        sink, context, item, entry.min(), entry.max());
            }
        }
    }
}
