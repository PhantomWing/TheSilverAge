package com.phantomwing.thesilverage.fabric.mixin;

import com.phantomwing.thesilverage.fabric.loot.SilverLootTableId;
import com.phantomwing.thesilverage.loot.SilverLootAlgorithms;
import com.phantomwing.thesilverage.loot.SilverLootSpec;
import com.phantomwing.thesilverage.platform.CommonConfig;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
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

/**
 * Fabric parity for the NeoForge Global Loot Modifiers.
 *
 * <p>NeoForge applies the Silver loot injections through GLMs (post-roll, on the
 * rolled {@code ObjectArrayList}). Fabric has no equivalent post-roll loot API,
 * so this mixin reproduces the exact GLM behaviour at the loot-roll site:</p>
 *
 * <ul>
 *   <li><b>Id stamping</b> ({@link #thesilverage$getLootTableId()} /
 *       {@link #thesilverage$setLootTableId(ResourceLocation)}): vanilla
 *       {@code LootTable} has no id, so an {@code @Unique} field is stamped once
 *       after all tables load (see {@code TheSilverAgeFabric}, via Fabric Loot
 *       API v2 {@code LootTableEvents.ALL_LOADED}). This is the Fabric
 *       equivalent of the GLM {@code neoforge:loot_table_id} condition source.</li>
 *   <li><b>Roll injection</b> ({@link #thesilverage$applySilverLoot}): injected
 *       at {@code RETURN} of the private list-returning roll
 *       {@code getRandomItems(LootContext)} — the single path every public
 *       list-returning overload ({@code getRandomItems(LootParams)},
 *       {@code (LootParams,long)}, {@code (LootParams,RandomSource)}) funnels
 *       through. It mutates the returned {@code ObjectArrayList} exactly like
 *       the GLM {@code doApply}.</li>
 * </ul>
 *
 * <p>Per spec entry the GLM conditions ({@code loot_table_id} +
 * {@code random_chance}) and the config gate are evaluated here, then the shared
 * {@link SilverLootAlgorithms} run — so the result is identical to NeoForge.</p>
 */
@Mixin(LootTable.class)
public abstract class LootTableMixin implements SilverLootTableId {
    @Unique
    @Nullable
    private ResourceLocation thesilverage$lootTableId;

    @Override
    @Nullable
    public ResourceLocation thesilverage$getLootTableId() {
        return this.thesilverage$lootTableId;
    }

    @Override
    public void thesilverage$setLootTableId(ResourceLocation id) {
        this.thesilverage$lootTableId = id;
    }

    /**
     * Container/structure loot path. Targets the private
     * {@code getRandomItems(LootContext)} (the {@code ObjectArrayList} the
     * {@code getRandomItems(LootParams[,RandomSource|long])} list overloads hand
     * back, used by chests/barrels/etc.), so REPLACE can mutate what actually
     * rolled — exactly as the GLM does.
     */
    @Inject(
            method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;",
            at = @At("RETURN")
    )
    private void thesilverage$applySilverLoot(LootContext context,
                                              CallbackInfoReturnable<ObjectArrayList<ItemStack>> cir) {
        ResourceLocation tableId = this.thesilverage$lootTableId;
        ObjectArrayList<ItemStack> generatedLoot = cir.getReturnValue();
        if (tableId == null || generatedLoot == null) {
            return;
        }
        thesilverage$applyMatchingEntries(tableId, context, generatedLoot);
    }

    /**
     * Entity/consumer loot path. {@code LivingEntity.dropFromLootTable} streams
     * mob drops through {@code getRandomItems(LootParams, long, Consumer)} —
     * which is a DISJOINT public entrypoint that never funnels through the
     * private list method above, so the container injection alone misses mob
     * drops (e.g. the silverfish silver-nugget GLM). This second injection
     * covers that path: it rebuilds the {@link LootContext} the same way the
     * vanilla overload does, runs the shared spec into a scratch list, and
     * forwards the appended stacks to the same {@code consumer}.
     *
     * <p>Disjoint entrypoints ⇒ no double-apply: chest loot uses
     * {@code getRandomItems(LootParams)} → private list method (only the
     * injection above fires); entity drops use {@code (LootParams,long,Consumer)}
     * (only this fires). REPLACE entries only ever target chest tables, so on a
     * (empty) entity scratch list they are inert — the shared helper is reused
     * verbatim to guarantee identical ADD/SILVERFISH behaviour across both
     * paths.</p>
     */
    @Inject(
            method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;JLjava/util/function/Consumer;)V",
            at = @At("TAIL")
    )
    private void thesilverage$applySilverEntityDrops(LootParams params, long seed,
                                                     Consumer<ItemStack> consumer, CallbackInfo ci) {
        ResourceLocation tableId = this.thesilverage$lootTableId;
        if (tableId == null) {
            return;
        }

        // Mirror the vanilla overload's own context construction so the
        // UniformGenerator / random_chance draws match a normal roll.
        LootContext context = new LootContext.Builder(params)
                .withOptionalRandomSeed(seed)
                .create(Optional.empty());

        ObjectArrayList<ItemStack> appended = new ObjectArrayList<>();
        thesilverage$applyMatchingEntries(tableId, context, appended);
        for (ItemStack stack : appended) {
            consumer.accept(stack);
        }
    }

    /**
     * Shared per-entry application — the single source of GLM-parity logic used
     * by both the container and entity paths. Mirrors each original GLM
     * {@code doApply}: {@code neoforge:loot_table_id} filter, the config gate,
     * the {@code minecraft:random_chance} draw
     * ({@code random.nextFloat() < chance}, identical to
     * {@code LootItemRandomChanceCondition}), then the shared algorithm.
     */
    @Unique
    private void thesilverage$applyMatchingEntries(ResourceLocation tableId,
                                                   LootContext context,
                                                   ObjectArrayList<ItemStack> sink) {
        RandomSource random = context.getRandom();
        boolean structureLootEnabled = CommonConfig.generateStructureLoot();
        boolean silverfishEnabled = CommonConfig.silverfishDropSilver();

        for (SilverLootSpec.Entry entry : SilverLootSpec.entries()) {
            // GLM condition 1: neoforge:loot_table_id — only entries for this table.
            if (!entry.targetLootTable().equals(tableId)) {
                continue;
            }

            // Config gate (the first statement of every original doApply).
            boolean enabled = entry.op() == SilverLootSpec.Op.SILVERFISH
                    ? silverfishEnabled
                    : structureLootEnabled;
            if (!enabled) {
                continue;
            }

            // GLM condition 2: minecraft:random_chance(chance) — same draw as
            // LootItemRandomChanceCondition (random.nextFloat() < chance).
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
