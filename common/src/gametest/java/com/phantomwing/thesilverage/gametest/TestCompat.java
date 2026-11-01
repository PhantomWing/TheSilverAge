package com.phantomwing.thesilverage.gametest;

import com.mojang.serialization.JsonOps;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import java.util.HashMap;
import com.phantomwing.thesilverage.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ConversionParams;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.item.equipment.trim.ArmorTrim;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.core.Holder;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.trading.MerchantOffer;
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
import net.minecraft.world.entity.projectile.hurtingprojectile.windcharge.AbstractWindCharge;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.phys.Vec3;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The calls a test makes whose shape differs between Minecraft versions, each behind a signature every
 * line shares. This is the one test file that differs from line to line, so the bodies don't have to:
 * when a port turns up another such call, it goes here rather than into a body.
 */
public final class TestCompat {
    // ENTITY TYPES THE BODIES SPAWN: 26.2 moved every constant from EntityType to EntityTypes.
    public static final EntityType<net.minecraft.world.entity.animal.cow.Cow> COW = EntityTypes.COW;
    public static final EntityType<net.minecraft.world.entity.monster.Creeper> CREEPER = EntityTypes.CREEPER;
    public static final EntityType<net.minecraft.world.entity.animal.equine.Horse> HORSE = EntityTypes.HORSE;
    public static final EntityType<net.minecraft.world.entity.monster.zombie.Husk> HUSK = EntityTypes.HUSK;
    public static final EntityType<net.minecraft.world.entity.animal.pig.Pig> PIG = EntityTypes.PIG;
    public static final EntityType<net.minecraft.world.entity.monster.Silverfish> SILVERFISH = EntityTypes.SILVERFISH;
    public static final EntityType<net.minecraft.world.entity.monster.skeleton.Skeleton> SKELETON = EntityTypes.SKELETON;
    public static final EntityType<net.minecraft.world.entity.npc.villager.Villager> VILLAGER = EntityTypes.VILLAGER;
    public static final EntityType<net.minecraft.world.entity.monster.illager.Vindicator> VINDICATOR = EntityTypes.VINDICATOR;
    public static final EntityType<net.minecraft.world.entity.monster.skeleton.WitherSkeleton> WITHER_SKELETON = EntityTypes.WITHER_SKELETON;
    public static final EntityType<net.minecraft.world.entity.monster.zombie.Zombie> ZOMBIE = EntityTypes.ZOMBIE;
    public static final EntityType<net.minecraft.world.entity.monster.zombie.ZombieVillager> ZOMBIE_VILLAGER = EntityTypes.ZOMBIE_VILLAGER;

    private TestCompat() {
    }

    /** Fails the test. {@code GameTestHelper.fail} takes only a Component from 1.21.5 to 1.21.8. */
    public static void fail(GameTestHelper helper, String message) {
        helper.fail(Component.literal(message));
    }

    /** Kills an entity as {@code /kill} does, so it drops its loot. It takes the level from 1.21.2. */
    public static void kill(Entity entity) {
        entity.kill((ServerLevel) entity.level());
    }

    /** What a crafting grid makes, or an empty stack. Recipe lookups and assembly changed shape in 1.21.2 and 26.1. */
    public static ItemStack craft(ServerLevel level, CraftingInput input) {
        return level.recipeAccess().getRecipeFor(RecipeType.CRAFTING, input, level)
                .map(recipe -> recipe.value().assemble(input))
                .orElse(ItemStack.EMPTY);
    }

    /** Fails the test with {@code message} unless {@code condition} holds. It takes only a Component from 1.21.5. */
    public static void assertTrue(GameTestHelper helper, boolean condition, String message) {
        helper.assertTrue(condition, Component.literal(message));
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
                .map(recipe -> recipe.value().assemble(recipeInput))
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
                .map(recipe -> recipe.assemble(recipeInput))
                .toList();
    }

    /** A vanilla loot table by its path, e.g. {@code chests/bastion_other}. */
    public static LootTable lootTable(ServerLevel level, String path) {
        return level.getServer().reloadableRegistries()
                .getLootTable(ResourceKey.create(Registries.LOOT_TABLE, Identifier.withDefaultNamespace(path)));
    }

    /**
     * Runs {@code body} with a player in the test's level who can click blocks as a real one does, through its game
     * mode, then takes the player out of the game again, so no test's mob goes after it. From 1.21.6 to 1.21.8 placing
     * a player waited for the entities around the world spawn; from 1.21.9 a login waits in PrepareSpawnTask instead,
     * which a mock player skips, so it is made at once. {@code getServer} left the entity in 1.21.9.
     */
    public static void withServerPlayer(GameTestHelper helper, Consumer<ServerPlayer> body) {
        ServerPlayer player = helper.makeMockServerPlayerInLevel();
        try {
            body.accept(player);
        } finally {
            helper.getLevel().getServer().getPlayerList().remove(player);
        }
    }

    /**
     * Every recipe the recipe manager holds, by its id, as its serializer writes it: what a mod's recipe format was read
     * as, whichever format that is. Recipes are looked up differently from 1.21.2 and 26.1, and from 26.3 a recipe's
     * own codec is DIRECT_CODEC, CODEC its registry holder's.
     */
    public static Map<String, JsonElement> recipesAsJson(ServerLevel level) {
        var ops = level.registryAccess().createSerializationContext(JsonOps.INSTANCE);
        Map<String, JsonElement> recipes = new HashMap<>();
        for (RecipeHolder<?> holder : level.recipeAccess().getRecipes()) {
            Recipe.DIRECT_CODEC.encodeStart(ops, holder.value()).result()
                    .ifPresent(json -> recipes.put(holder.id().identifier().toString(), json));
        }
        return recipes;
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
                        .getOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL, Identifier.withDefaultNamespace(material))),
                level.registryAccess().lookupOrThrow(Registries.TRIM_PATTERN)
                        .getOrThrow(ResourceKey.create(Registries.TRIM_PATTERN, Identifier.withDefaultNamespace(pattern))));
    }

    /**
     * The palette a trim of {@code material} draws with on worn {@code armor}, or null for its own. Before 1.21.2 a trim
     * material named it by the armour's material, its registry holder; from 1.21.2 by the armour's equipment model, from
     * 1.21.4 by its equipment asset, and from 1.21.5 in the material's asset group. 26.3 moved it into the equipment
     * asset's {@code trim_overrides}, a client asset the server never loads, so it is read from the mod's resources.
     */
    public static String trimPalette(ServerLevel level, ResourceKey<TrimMaterial> material, Item armor) {
        Equippable equippable = armor.components().get(DataComponents.EQUIPPABLE);
        if (equippable == null || equippable.assetId().isEmpty()) {
            return null;
        }
        Identifier asset = equippable.assetId().get().identifier();
        String file = "/assets/" + asset.getNamespace() + "/equipment/" + asset.getPath() + ".json";
        // Through a class of the mod's own: on NeoForge the test classes' loader didn't find the file.
        try (InputStream in = ModItems.class.getResourceAsStream(file)) {
            if (in == null) {
                return null;
            }
            JsonElement overrides = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                    .getAsJsonObject().get("trim_overrides");
            if (overrides != null) {
                for (JsonElement override : overrides.getAsJsonArray()) {
                    String when = override.getAsJsonObject().getAsJsonObject("when").get("material").getAsString();
                    if (Identifier.parse(when).equals(material.identifier())) {
                        return Identifier.parse(override.getAsJsonObject().get("palette").getAsString()).getPath()
                                .replaceFirst("^trim/", "");
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("can't read " + file, e);
        }
        return null;
    }

    /** The placed feature of that key. Registry lookups were renamed in 1.21.2. */
    public static PlacedFeature placedFeature(ServerLevel level, ResourceKey<PlacedFeature> key) {
        return level.registryAccess().lookupOrThrow(Registries.PLACED_FEATURE).getValueOrThrow(key);
    }

    /** A wind charge bursting at {@code at}, as a thrown one does: it triggers blocks and breaks none. 1.21.9 adds the block particles. */
    public static void windChargeBurst(ServerLevel level, Vec3 at) {
        level.explode(null, null, AbstractWindCharge.EXPLOSION_DAMAGE_CALCULATOR, at.x, at.y, at.z, 1.2f, false,
                Level.ExplosionInteraction.TRIGGER, ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE,
                WeightedList.of(), SoundEvents.WIND_CHARGE_BURST);
    }

    /** The block entity at {@code pos} in the test, or null. {@code GameTestHelper.getBlockEntity} takes its class from 1.21.5. */
    public static BlockEntity blockEntity(GameTestHelper helper, BlockPos pos) {
        return helper.getLevel().getBlockEntity(helper.absolutePos(pos));
    }

    /**
     * The offers the trades of vanilla's {@code profession} at {@code tradeLevel}, or at every level for 0, give a fresh
     * villager. From 26.1 trades are data: level N of a profession is the trade set {@code minecraft:<profession>/level_N},
     * and each of its trades is made into an offer with the loot context a villager levelling up gives it.
     */
    public static List<MerchantOffer> villagerOffers(ServerLevel level, String profession, int tradeLevel, RandomSource random) {
        Villager villager = create(VILLAGER, level);
        LootContext context = new LootContext.Builder(new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, villager.position())
                .withParameter(LootContextParams.THIS_ENTITY, villager)
                .withParameter(LootContextParams.ADDITIONAL_COST_COMPONENT_ALLOWED, net.minecraft.util.Unit.INSTANCE)
                .create(LootContextParamSets.VILLAGER_TRADE)).withOptionalRandomSource(random).create(Optional.empty());
        List<MerchantOffer> offers = new ArrayList<>();
        Registry<TradeSet> sets = level.registryAccess().lookupOrThrow(Registries.TRADE_SET);
        for (int i = 1; i <= 5; i++) {
            if (tradeLevel != 0 && i != tradeLevel) {
                continue;
            }
            Optional<TradeSet> set = sets.getOptional(Identifier.withDefaultNamespace(profession + "/level_" + i));
            if (set.isEmpty()) {
                continue;
            }
            for (Holder<VillagerTrade> trade : set.get().trades()) {
                MerchantOffer offer = trade.value().getOffer(context);
                if (offer != null) {
                    offers.add(offer);
                }
            }
        }
        villager.discard();
        return offers;
    }

    /** The tag of that name in {@code registry}. ResourceLocation is Identifier from 1.21.11. */
    public static <T> TagKey<T> tag(ResourceKey<? extends Registry<T>> registry, String namespace, String path) {
        return TagKey.create(registry, Identifier.fromNamespaceAndPath(namespace, path));
    }

    /** The resource of that name, if a pack has it. ResourceLocation is Identifier from 1.21.11. */
    public static Optional<Resource> resource(ResourceManager resources, String namespace, String path) {
        return resources.getResource(Identifier.fromNamespaceAndPath(namespace, path));
    }

    /** The paths of the JSON files under {@code folder} in {@code namespace}, sorted. ResourceLocation is Identifier from 1.21.11. */
    public static List<String> jsonPaths(ResourceManager resources, String folder, String namespace) {
        return resources.listResources(folder, id -> id.getNamespace().equals(namespace) && id.getPath().endsWith(".json"))
                .keySet().stream().map(Identifier::getPath).sorted().toList();
    }

    /** A plain arrow at the origin, not yet added to {@code level}. Arrows moved to {@code projectile.arrow} in 1.21.11. */
    public static Projectile plainArrow(ServerLevel level) {
        return new Arrow(level, 0, 0, 0, new ItemStack(Items.ARROW), null);
    }

    /** Sets the time of day in the test's level. {@code GameTestHelper.setDayTime} went in 1.21.11, and from 26.1 the time is a world clock's. */
    public static void setDayTime(GameTestHelper helper, int time) {
        // 26.1 keeps the time of day on the overworld's world clock.
        ServerLevel level = helper.getLevel();
        Holder<WorldClock> overworld = level.registryAccess().lookupOrThrow(Registries.WORLD_CLOCK).getOrThrow(WorldClocks.OVERWORLD);
        level.clockManager().setTotalTicks(overworld, time);
    }

    /** The id {@code key} names, as text. {@code ResourceKey#location} is {@code identifier} from 1.21.11. */
    public static String idOf(ResourceKey<?> key) {
        return key.identifier().toString();
    }

    /** Hurts {@code mob} as {@code arrow} hitting it would, answering whether it took the hit. */
    public static boolean hurtByArrow(LivingEntity mob, Projectile arrow, float amount) {
        return hurt(mob, mob.level().damageSources().arrow((AbstractArrow) arrow, null), amount);
    }

    /** Whether {@code type} is in {@code tag}. {@code EntityType#is(TagKey)} went in 26.1, for its registry holder's. */
    public static boolean isIn(EntityType<?> type, TagKey<EntityType<?>> tag) {
        return type.builtInRegistryHolder().is(tag);
    }

    /** The scoreboard tags an entity carries. {@code Entity#getTags} is {@code entityTags} from 26.1. */
    public static Set<String> entityTags(Entity entity) {
        return entity.entityTags();
    }

    /**
     * The grid of vanilla's Name Tag recipe with {@code nugget} for its nugget, where vanilla has one: from 26.1 a nugget
     * of {@code #minecraft:metal_nuggets} above and right of the paper, diagonally. Empty before 26.1, which crafts no Name Tag.
     */
    public static Optional<CraftingInput> nameTagGrid(Item nugget) {
        return Optional.of(CraftingInput.of(2, 2, List.of(ItemStack.EMPTY, new ItemStack(nugget), new ItemStack(Items.PAPER), ItemStack.EMPTY)));
    }

    /** Lets {@code entity} be hurt again at once. From 26.3 a living entity's hurt cooldown is its own damageCooldownTime, apart from invulnerableTime. */
    public static void resetDamageCooldown(LivingEntity entity) {
        entity.damageCooldownTime = 0;
    }
}
