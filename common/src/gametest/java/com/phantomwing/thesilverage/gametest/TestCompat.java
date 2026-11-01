package com.phantomwing.thesilverage.gametest;

import net.minecraft.core.Registry;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.trading.MerchantOffer;
import java.util.ArrayList;
import java.util.Optional;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
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
import net.minecraft.util.random.WeightedList;
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
    // ENTITY TYPES THE BODIES SPAWN: 26.2 moved every constant from EntityType to EntityTypes.
    public static final EntityType<net.minecraft.world.entity.animal.Cow> COW = EntityType.COW;
    public static final EntityType<net.minecraft.world.entity.monster.Creeper> CREEPER = EntityType.CREEPER;
    public static final EntityType<net.minecraft.world.entity.animal.horse.Horse> HORSE = EntityType.HORSE;
    public static final EntityType<net.minecraft.world.entity.monster.Husk> HUSK = EntityType.HUSK;
    public static final EntityType<net.minecraft.world.entity.animal.Pig> PIG = EntityType.PIG;
    public static final EntityType<net.minecraft.world.entity.monster.Silverfish> SILVERFISH = EntityType.SILVERFISH;
    public static final EntityType<net.minecraft.world.entity.monster.Skeleton> SKELETON = EntityType.SKELETON;
    public static final EntityType<net.minecraft.world.entity.npc.Villager> VILLAGER = EntityType.VILLAGER;
    public static final EntityType<net.minecraft.world.entity.monster.Vindicator> VINDICATOR = EntityType.VINDICATOR;
    public static final EntityType<net.minecraft.world.entity.monster.WitherSkeleton> WITHER_SKELETON = EntityType.WITHER_SKELETON;
    public static final EntityType<net.minecraft.world.entity.monster.Zombie> ZOMBIE = EntityType.ZOMBIE;
    public static final EntityType<net.minecraft.world.entity.monster.ZombieVillager> ZOMBIE_VILLAGER = EntityType.ZOMBIE_VILLAGER;

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
                .map(recipe -> recipe.value().assemble(input, level.registryAccess()))
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
     * material named it by the armour's material, its registry holder; from 1.21.2 by the armour's equipment model, from 1.21.4 by its equipment asset, and from 1.21.5 in the material's asset group.
     */
    public static String trimPalette(ServerLevel level, ResourceKey<TrimMaterial> material, Item armor) {
        Equippable equippable = armor.components().get(DataComponents.EQUIPPABLE);
        if (equippable == null || equippable.assetId().isEmpty()) {
            return null;
        }
        var override = level.registryAccess().lookupOrThrow(Registries.TRIM_MATERIAL).getOrThrow(material).value()
                .assets().overrides().get(equippable.assetId().get());
        return override == null ? null : override.suffix();
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

    /** The trades of vanilla's profession of that name by level. The trade lists are keyed by the profession's key from 1.21.5. */
    public static Int2ObjectMap<VillagerTrades.ItemListing[]> villagerTrades(String profession) {
        return VillagerTrades.TRADES.get(ResourceKey.create(Registries.VILLAGER_PROFESSION, ResourceLocation.withDefaultNamespace(profession)));
    }

    /**
     * The offers the trades of vanilla's {@code profession} at {@code tradeLevel}, or at every level for 0, give a fresh
     * villager. Villagers and their trades move to {@code npc.villager} in 1.21.11, where a listing takes the level too.
     */
    public static List<MerchantOffer> villagerOffers(ServerLevel level, String profession, int tradeLevel, RandomSource random) {
        Villager villager = create(EntityType.VILLAGER, level);
        List<MerchantOffer> offers = new ArrayList<>();
        for (var entry : villagerTrades(profession).int2ObjectEntrySet()) {
            if (tradeLevel != 0 && entry.getIntKey() != tradeLevel) {
                continue;
            }
            for (VillagerTrades.ItemListing listing : entry.getValue()) {
                MerchantOffer offer = listing.getOffer(villager, random);
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
        return TagKey.create(registry, ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    /** The resource of that name, if a pack has it. ResourceLocation is Identifier from 1.21.11. */
    public static Optional<Resource> resource(ResourceManager resources, String namespace, String path) {
        return resources.getResource(ResourceLocation.fromNamespaceAndPath(namespace, path));
    }

    /** The paths of the JSON files under {@code folder} in {@code namespace}, sorted. ResourceLocation is Identifier from 1.21.11. */
    public static List<String> jsonPaths(ResourceManager resources, String folder, String namespace) {
        return resources.listResources(folder, id -> id.getNamespace().equals(namespace) && id.getPath().endsWith(".json"))
                .keySet().stream().map(ResourceLocation::getPath).sorted().toList();
    }

    /** Sets the time of day in the test's level. {@code GameTestHelper.setDayTime} goes in 1.21.11. */
    public static void setDayTime(GameTestHelper helper, int time) {
        helper.setDayTime(time);
    }

    /** The id {@code key} names, as text. {@code ResourceKey#location} is {@code identifier} from 1.21.11. */
    public static String idOf(ResourceKey<?> key) {
        return key.location().toString();
    }

    /** A plain arrow at the origin, not yet added to {@code level}. Arrows move to {@code projectile.arrow} in 1.21.11. */
    public static Projectile plainArrow(ServerLevel level) {
        return new Arrow(level, 0, 0, 0, new ItemStack(Items.ARROW), null);
    }

    /** Hurts {@code mob} as {@code arrow} hitting it would, answering whether it took the hit. */
    public static boolean hurtByArrow(LivingEntity mob, Projectile arrow, float amount) {
        return hurt(mob, mob.level().damageSources().arrow((AbstractArrow) arrow, null), amount);
    }

    /** Whether {@code type} is in {@code tag}. {@code EntityType#is(TagKey)} went in 26.1, for its registry holder's. */
    public static boolean isIn(EntityType<?> type, TagKey<EntityType<?>> tag) {
        return type.is(tag);
    }

    /** The scoreboard tags an entity carries. {@code Entity#getTags} is {@code entityTags} from 26.1. */
    public static Set<String> entityTags(Entity entity) {
        return entity.getTags();
    }

    /**
     * The grid of vanilla's Name Tag recipe with {@code nugget} for its nugget, where vanilla has one: from 26.1 a nugget
     * of {@code #minecraft:metal_nuggets} over paper. Empty before 26.1, which crafts no Name Tag.
     */
    public static Optional<CraftingInput> nameTagGrid(Item nugget) {
        return Optional.empty();
    }

    /** Lets {@code entity} be hurt again at once. From 26.3 a living entity's hurt cooldown is its own damageCooldownTime, apart from invulnerableTime. */
    public static void resetDamageCooldown(LivingEntity entity) {
        entity.invulnerableTime = 0;
    }
}
