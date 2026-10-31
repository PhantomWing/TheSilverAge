package com.phantomwing.thesilverage.gametest;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The calls a test makes whose shape differs between Minecraft versions, each behind a signature every
 * line shares. This is the one test file that differs from line to line, so the bodies don't have to:
 * when a port turns up another such call, it goes here rather than into a body.
 */
public final class TestCompat {
    private TestCompat() {
    }

    /** Fails the test. {@code GameTestHelper.fail} takes only a Component from 1.21.5 to 1.21.8. */
    public static void fail(GameTestHelper helper, String message) {
        helper.fail(message);
    }

    /** Kills an entity as {@code /kill} does, so it drops its loot. It takes the level from 1.21.2. */
    public static void kill(Entity entity) {
        entity.kill((ServerLevel) entity.level());
    }

    /** What a crafting grid makes, or an empty stack. Recipe lookups and assembly changed shape in 1.21.2 and 26.1. */
    public static ItemStack craft(ServerLevel level, CraftingInput input) {
        return level.recipeAccess().getRecipeFor(RecipeType.CRAFTING, input, level)
                .map(recipe -> recipe.value().assemble(input, level.registryAccess()))
                .orElse(ItemStack.EMPTY);
    }

    /** Fails the test with {@code message} unless {@code condition} holds. It takes only a Component from 1.21.5. */
    public static void assertTrue(GameTestHelper helper, boolean condition, String message) {
        helper.assertTrue(condition, message);
    }

    /** An iron pickaxe with Silk Touch. Enchantments are looked up differently from 1.21.2. */
    public static ItemStack silkTouchPickaxe(ServerLevel level) {
        ItemStack pickaxe = new ItemStack(Items.IRON_PICKAXE);
        pickaxe.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), 1);
        return pickaxe;
    }

    /** What a furnace makes of {@code input}, or an empty stack. Recipe lookups and assembly changed shape in 1.21.2 and 26.1. */
    public static ItemStack smelt(ServerLevel level, ItemStack input) {
        SingleRecipeInput recipeInput = new SingleRecipeInput(input);
        return level.recipeAccess().getRecipeFor(RecipeType.SMELTING, recipeInput, level)
                .map(recipe -> recipe.value().assemble(recipeInput, level.registryAccess()))
                .orElse(ItemStack.EMPTY);
    }

    /**
     * Everything a stonecutter offers for {@code input}. Recipes are looked up and assembled differently from 1.21.2 and
     * 26.1: RecipeManager#getRecipesFor went in 1.21.2, and vanilla has nothing in its place that common can call.
     */
    public static List<ItemStack> stonecut(ServerLevel level, ItemStack input) {
        SingleRecipeInput recipeInput = new SingleRecipeInput(input);
        return level.recipeAccess().getRecipes().stream()
                .filter(holder -> holder.value() instanceof StonecutterRecipe)
                .map(holder -> (StonecutterRecipe) holder.value())
                .filter(recipe -> recipe.matches(recipeInput, level))
                .map(recipe -> recipe.assemble(recipeInput, level.registryAccess()))
                .toList();
    }

    /** A vanilla loot table by its path, e.g. {@code chests/bastion_other}. */
    public static LootTable lootTable(ServerLevel level, String path) {
        return level.getServer().reloadableRegistries()
                .getLootTable(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace(path)));
    }

    /** A player in the test's level who can click blocks as a real one does, through its game mode. */
    public static ServerPlayer serverPlayer(GameTestHelper helper) {
        return helper.makeMockServerPlayerInLevel();
    }

    /** Takes a player made by {@link #serverPlayer} out of the game again, so no test's mob goes after it. */
    public static void removePlayer(ServerPlayer player) {
        player.server.getPlayerList().remove(player);
    }

    /**
     * The folders of Create's recipes, each with the options every recipe in it names. Create and Create: Aquatic
     * Ambitions are on 1.21.1 alone, so a line without them has none.
     */
    public static Map<String, Set<String>> createRecipeFolders() {
        return Map.of();
    }

    /** A new entity of {@code type} in {@code level}, not yet added to it. It takes a spawn reason from 1.21.2. */
    public static <T extends Entity> T create(EntityType<T> type, ServerLevel level) {
        return type.create(level, EntitySpawnReason.MOB_SUMMONED);
    }

    /** Hurts {@code entity}, answering whether it took the hit. A server's hurt is {@code hurtServer} from 1.21.2. */
    public static boolean hurt(LivingEntity entity, DamageSource source, float amount) {
        return entity.hurtServer((ServerLevel) entity.level(), source, amount);
    }

    /** {@code mob} turned into a new mob of {@code type}, as a husk drowning is. Conversions take their parameters from 1.21.2. */
    public static <T extends Mob> T convertTo(Mob mob, EntityType<T> type, boolean keepEquipment) {
        return mob.convertTo(type, ConversionParams.single(mob, keepEquipment, keepEquipment), converted -> {
        });
    }

    /** The trim of vanilla's material and pattern of those names. Registry lookups were renamed and trims moved package in 1.21.2. */
    public static ArmorTrim trim(ServerLevel level, String material, String pattern) {
        return new ArmorTrim(level.registryAccess().lookupOrThrow(Registries.TRIM_MATERIAL)
                        .getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.withDefaultNamespace(material))),
                level.registryAccess().lookupOrThrow(Registries.TRIM_PATTERN)
                        .getOrThrow(ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.withDefaultNamespace(pattern))));
    }

    /**
     * The palette a trim of {@code material} draws with on worn {@code armor}, or null for its own. Before 1.21.2 a trim
     * material named it by the armour's material, its registry holder; from 1.21.2 by the armour's equipment model.
     */
    public static String trimPalette(ServerLevel level, ResourceKey<TrimMaterial> material, Item armor) {
        Equippable equippable = armor.components().get(DataComponents.EQUIPPABLE);
        return equippable == null || equippable.model().isEmpty() ? null : level.registryAccess()
                .lookupOrThrow(Registries.TRIM_MATERIAL).getOrThrow(material).value()
                .overrideArmorMaterials().get(equippable.model().get());
    }

    /** The placed feature of that key. Registry lookups were renamed in 1.21.2. */
    public static PlacedFeature placedFeature(ServerLevel level, ResourceKey<PlacedFeature> key) {
        return level.registryAccess().lookupOrThrow(Registries.PLACED_FEATURE).getValueOrThrow(key);
    }

    /** A wind charge bursting at {@code at}, as a thrown one does: it triggers blocks and breaks none. */
    public static void windChargeBurst(ServerLevel level, Vec3 at) {
        level.explode(null, null, AbstractWindCharge.EXPLOSION_DAMAGE_CALCULATOR, at.x, at.y, at.z, 1.2f, false,
                Level.ExplosionInteraction.TRIGGER, ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE,
                SoundEvents.WIND_CHARGE_BURST);
    }

    /** The block entity at {@code pos} in the test, or null. {@code GameTestHelper.getBlockEntity} takes its class from 1.21.5. */
    public static BlockEntity blockEntity(GameTestHelper helper, BlockPos pos) {
        return helper.getBlockEntity(pos);
    }

    /** The trades of vanilla's profession of that name by level. The trade lists are keyed by the profession's key from 1.21.5. */
    public static Int2ObjectMap<VillagerTrades.ItemListing[]> villagerTrades(String profession) {
        return VillagerTrades.TRADES.get(BuiltInRegistries.VILLAGER_PROFESSION.getValue(ResourceLocation.withDefaultNamespace(profession)));
    }
}
