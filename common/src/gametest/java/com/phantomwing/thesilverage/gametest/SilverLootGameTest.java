package com.phantomwing.thesilverage.gametest;

import com.phantomwing.thesilverage.item.ModItems;
import com.phantomwing.thesilverage.loot.SilverLootAlgorithms;
import com.phantomwing.thesilverage.loot.SilverLootSpec;
import dev.architectury.registry.registries.RegistrySupplier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.List;

import static com.phantomwing.thesilverage.gametest.SilverBlocksGameTest.CENTER;
import static com.phantomwing.thesilverage.gametest.TestCompat.fail;

/** Game tests for silver in loot: mob drops and the chests silver stands in for iron or gold in. Listed in {@link GameTests}. */
public final class SilverLootGameTest {
    /**
     * How many times a chest's loot is rolled. The rarest swap tested comes up about once in a hundred
     * rolls (an iron axe in one trial chamber corridor chest in ten, swapped one time in ten).
     */
    private static final int ROLLS = 3000;

    private SilverLootGameTest() {
    }

    /**
     * Killing silverfish drops Silver Nuggets. A kill drops 0 to 2, so a batch is killed: the chance
     * that none drops anything is (1/3)^15. It waits two ticks first: in a test's first tick the
     * chunk's entity sections may not be ready, and the drops would be missed.
     */
    public static void silverfishDropsSilver(GameTestHelper helper) {
        helper.startSequence()
                .thenExecuteAfter(2, () -> {
                    for (int i = 0; i < 15; i++) {
                        Silverfish silverfish = helper.spawnWithNoFreeWill(EntityType.SILVERFISH, CENTER);
                        TestCompat.kill(silverfish);
                    }
                })
                .thenExecuteAfter(2, () -> helper.assertItemEntityPresent(ModItems.SILVER_NUGGET.get()))
                .thenSucceed();
    }

    /** Every loot entry gives the item its id names: {@code silver_horse_armor_from_...} gives Silver Horse Armor. */
    public static void lootEntriesGiveWhatTheyName(GameTestHelper helper) {
        for (SilverLootSpec.Entry entry : SilverLootSpec.entries()) {
            String item = BuiltInRegistries.ITEM.getKey(entry.item().get()).getPath();
            if (!entry.id().startsWith(item + "_from_")) {
                fail(helper, "Loot entry " + entry.id() + " gives " + item);
                return;
            }
        }
        helper.succeed();
    }

    /** A jungle temple's horse armor can be Silver Horse Armor. */
    public static void jungleTemplesGiveSilverHorseArmor(GameTestHelper helper) {
        expectIn(helper, "chests/jungle_temple", List.of(ModItems.SILVER_HORSE_ARMOR));
    }

    /** Silver gear stands in for a stronghold corridor's iron gear. */
    public static void silverGearInStrongholdCorridors(GameTestHelper helper) {
        expectIn(helper, "chests/stronghold_corridor", List.of(ModItems.SILVER_SWORD, ModItems.SILVER_PICKAXE,
                ModItems.SILVER_HELMET, ModItems.SILVER_CHESTPLATE, ModItems.SILVER_LEGGINGS, ModItems.SILVER_BOOTS));
    }

    /** A Silver Shovel stands in for a village toolsmith's iron one. */
    public static void silverShovelInToolsmithChests(GameTestHelper helper) {
        expectIn(helper, "chests/village/village_toolsmith", List.of(ModItems.SILVER_SHOVEL));
    }

    /** A Silver Axe stands in for a trial chamber corridor's iron one. */
    public static void silverAxeInTrialChamberCorridors(GameTestHelper helper) {
        expectIn(helper, "chests/trial_chambers/corridor", List.of(ModItems.SILVER_AXE));
    }

    /** A trial vault's rare reward can be a Silver Axe or Chestplate, which it reaches through a nested table. */
    public static void silverGearInTrialVaults(GameTestHelper helper) {
        expectIn(helper, "chests/trial_chambers/reward", List.of(ModItems.SILVER_AXE, ModItems.SILVER_CHESTPLATE));
    }

    /** A Silver Sword stands in for a bastion's iron one. */
    public static void silverSwordInBastions(GameTestHelper helper) {
        expectIn(helper, "chests/bastion_other", List.of(ModItems.SILVER_SWORD));
    }

    /** A swap that takes every stack takes two lying side by side too: the one after each swapped stack was skipped. */
    public static void replacingTakesNeighbouringStacks(GameTestHelper helper) {
        ObjectArrayList<ItemStack> loot = ObjectArrayList.of(new ItemStack(Items.ARROW, 4), new ItemStack(Items.ARROW, 4),
                new ItemStack(Items.STONE), new ItemStack(Items.ARROW, 4));
        // With no maximum the context's random is never drawn.
        SilverLootAlgorithms.applyReplaceItem(loot, null, ModItems.SILVER_ARROW.get(), List.of(Items.ARROW), 0, 0);
        long silver = loot.stream().filter(stack -> stack.is(ModItems.SILVER_ARROW.get())).count();
        if (silver != 3 || loot.size() != 4) {
            fail(helper, "Expected 3 stacks of Silver Arrows among 4, found " + loot);
            return;
        }
        helper.succeed();
    }

    /** Succeeds when one of {@value #ROLLS} rolls of a vanilla chest holds one of {@code wanted}. */
    private static void expectIn(GameTestHelper helper, String table, List<RegistrySupplier<Item>> wanted) {
        ServerLevel level = helper.getLevel();
        List<Item> items = wanted.stream().map(RegistrySupplier::get).toList();
        LootTable loot = TestCompat.lootTable(level, table);
        LootParams params = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(helper.absolutePos(BlockPos.ZERO)))
                .create(LootContextParamSets.CHEST);
        for (int i = 0; i < ROLLS; i++) {
            if (loot.getRandomItems(params).stream().anyMatch(stack -> items.contains(stack.getItem()))) {
                helper.succeed();
                return;
            }
        }
        fail(helper, "None of " + items + " in " + ROLLS + " rolls of " + table);
    }
}
