package com.phantomwing.thesilverage.gametest;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
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
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.projectile.windcharge.AbstractWindCharge;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

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
        entity.kill();
    }

    /** What a crafting grid makes, or an empty stack. Recipe lookups and assembly changed shape in 1.21.2 and 26.1. */
    public static ItemStack craft(ServerLevel level, CraftingInput input) {
        return level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level)
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
        pickaxe.enchant(level.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SILK_TOUCH), 1);
        return pickaxe;
    }

    /** What a furnace makes of {@code input}, or an empty stack. Recipe lookups and assembly changed shape in 1.21.2 and 26.1. */
    public static ItemStack smelt(ServerLevel level, ItemStack input) {
        SingleRecipeInput recipeInput = new SingleRecipeInput(input);
        return level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, recipeInput, level)
                .map(recipe -> recipe.value().assemble(recipeInput, level.registryAccess()))
                .orElse(ItemStack.EMPTY);
    }

    /** Everything a stonecutter offers for {@code input}. Recipes are looked up and assembled differently from 1.21.2 and 26.1. */
    public static List<ItemStack> stonecut(ServerLevel level, ItemStack input) {
        SingleRecipeInput recipeInput = new SingleRecipeInput(input);
        return level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, recipeInput, level).stream()
                .map(recipe -> recipe.value().assemble(recipeInput, level.registryAccess()))
                .toList();
    }

    /** A vanilla loot table by its path, e.g. {@code chests/bastion_other}. */
    public static LootTable lootTable(ServerLevel level, String path) {
        return level.getServer().reloadableRegistries()
                .getLootTable(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.withDefaultNamespace(path)));
    }

    /**
     * Runs {@code body} with a player in the test's level who can click blocks as a real one does, through its game
     * mode, then takes the player out of the game again, so no test's mob goes after it. On 1.21.6 to 1.21.8 the player
     * can only be made once the entities around the world spawn have loaded, so the body may run some ticks later.
     */
    public static void withServerPlayer(GameTestHelper helper, Consumer<ServerPlayer> body) {
        ServerPlayer player = helper.makeMockServerPlayerInLevel();
        try {
            body.accept(player);
        } finally {
            player.server.getPlayerList().remove(player);
        }
    }

    /**
     * The folders of Create's recipes, each with the options every recipe in it names. Create and Create: Aquatic
     * Ambitions are on 1.21.1 alone, so a line without them has none. Pressing and splashing make silver from ore and
     * ingots, which no option takes away.
     */
    public static Map<String, Set<String>> createRecipeFolders() {
        return Map.of(
                "recipe/filling", Set.of("enable_silver_oxidation", "enable_create_spout_oxidation"),
                "recipe/deploying", Set.of("enable_silver_oxidation"),
                "recipe/channeling", Set.of("enable_silver_oxidation"));
    }

    /** A new entity of {@code type} in {@code level}, not yet added to it. It takes a spawn reason from 1.21.2. */
    public static <T extends Entity> T create(EntityType<T> type, ServerLevel level) {
        return type.create(level);
    }

    /** Hurts {@code entity}, answering whether it took the hit. A server's hurt is {@code hurtServer} from 1.21.2. */
    public static boolean hurt(LivingEntity entity, DamageSource source, float amount) {
        return entity.hurt(source, amount);
    }

    /** {@code mob} turned into a new mob of {@code type}, as a husk drowning is. Conversions take their parameters from 1.21.2. */
    public static <T extends Mob> T convertTo(Mob mob, EntityType<T> type, boolean keepEquipment) {
        return mob.convertTo(type, keepEquipment);
    }

    /** The trim of vanilla's material and pattern of those names. Registry lookups were renamed and trims moved package in 1.21.2. */
    public static ArmorTrim trim(ServerLevel level, String material, String pattern) {
        return new ArmorTrim(level.registryAccess().registryOrThrow(Registries.TRIM_MATERIAL)
                        .getHolderOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.withDefaultNamespace(material))),
                level.registryAccess().registryOrThrow(Registries.TRIM_PATTERN)
                        .getHolderOrThrow(ResourceKey.create(Registries.TRIM_PATTERN, ResourceLocation.withDefaultNamespace(pattern))));
    }

    /**
     * The palette a trim of {@code material} draws with on worn {@code armor}, or null for its own. Before 1.21.2 a trim
     * material named it by the armour's material, its registry holder, which only the registry's holder of the trim
     * material finds; from 1.21.2 by the armour's equipment model.
     */
    public static String trimPalette(ServerLevel level, ResourceKey<TrimMaterial> material, Item armor) {
        if (!(armor instanceof ArmorItem armorItem)) {
            return null;
        }
        Holder<TrimMaterial> holder = level.registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolderOrThrow(material);
        return holder.value().overrideArmorMaterials().get(armorItem.getMaterial());
    }

    /** The placed feature of that key. Registry lookups were renamed in 1.21.2. */
    public static PlacedFeature placedFeature(ServerLevel level, ResourceKey<PlacedFeature> key) {
        return level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getOrThrow(key);
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
        return VillagerTrades.TRADES.get(BuiltInRegistries.VILLAGER_PROFESSION.get(ResourceLocation.withDefaultNamespace(profession)));
    }
}
