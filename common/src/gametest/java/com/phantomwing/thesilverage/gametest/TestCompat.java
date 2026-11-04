package com.phantomwing.thesilverage.gametest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The calls a test makes whose shape differs between Minecraft versions, each behind a signature every
 * line shares. This is the one test file that differs from line to line, so the bodies don't have to:
 * when a port turns up another such call, it goes here rather than into a body.
 *
 * <p>On 1.20.1 too {@link CraftingInput}, 1.21's crafting grid, which this line has as a class of the tests', and a few
 * calls 1.21's bodies make on components and blocks that 1.20.1 makes otherwise.</p>
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

    /** The slot horse armour is worn in: 1.20.1's horses wear it in the chest slot, 1.21's in the body slot. */
    public static final net.minecraft.world.entity.EquipmentSlot HORSE_ARMOR_SLOT = net.minecraft.world.entity.EquipmentSlot.CHEST;
    /** The namespace of the tags mods share materials through: Forge's on 1.20.1, {@code c} from 1.21. */
    public static final String COMMON_NAMESPACE = "forge";
    /** Where chains are tagged: 1.21's {@code c:chains} isn't on 1.20.1, whose backports fill vanilla's {@code minecraft:chains}. */
    public static final String CHAINS_NAMESPACE = "minecraft";

    private TestCompat() {
    }

    /** A Silver Arrow at the origin, not yet added to {@code level}. An arrow carries its pickup stack from 1.20.5. */
    public static com.phantomwing.thesilverage.entity.SilverArrow silverArrow(ServerLevel level) {
        return new com.phantomwing.thesilverage.entity.SilverArrow(level, 0, 0, 0);
    }

    /** What picking {@code arrow} up gives. {@code getPickupItemStackOrigin} is 1.20.5's. */
    public static ItemStack pickupItem(com.phantomwing.thesilverage.entity.SilverArrow arrow) {
        return arrow.getPickupItem();
    }

    /** Whether a {@code type} may spawn at {@code pos}, as natural spawning asks. {@code SpawnPlacements.isSpawnPositionOk} is 1.20.5's. */
    public static boolean isSpawnPositionOk(EntityType<?> type, ServerLevel level, BlockPos pos) {
        return net.minecraft.world.level.NaturalSpawner.isSpawnPositionOk(
                net.minecraft.world.entity.SpawnPlacements.getPlacementType(type), level, pos, type);
    }

    /** Fails unless an item entity of {@code item} is anywhere in the test. {@code assertItemEntityPresent(Item)} is 1.20.5's. */
    public static void assertItemEntityPresent(GameTestHelper helper, Item item) {
        helper.assertItemEntityPresent(item, new BlockPos(3, 2, 3), 16.0);
    }

    /** Fails the test. {@code GameTestHelper.fail} takes only a Component from 1.21.5 to 1.21.8. */
    public static void fail(GameTestHelper helper, String message) {
        helper.fail(message);
    }

    /** Kills an entity as {@code /kill} does, so it drops its loot. It takes the level from 1.21.2. */
    public static void kill(Entity entity) {
        entity.kill();
    }

    /** What a crafting grid makes, or an empty stack. Recipes match a container on 1.20.1. */
    public static ItemStack craft(ServerLevel level, CraftingInput input) {
        return level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input.container(), level)
                .map(recipe -> recipe.assemble(input.container(), level.registryAccess()))
                .orElse(ItemStack.EMPTY);
    }

    /** Fails the test with {@code message} unless {@code condition} holds. It takes only a Component from 1.21.5. */
    public static void assertTrue(GameTestHelper helper, boolean condition, String message) {
        helper.assertTrue(condition, message);
    }

    /** An iron pickaxe with Silk Touch. Enchantments are registry entries from 1.21. */
    public static ItemStack silkTouchPickaxe(ServerLevel level) {
        ItemStack pickaxe = new ItemStack(Items.IRON_PICKAXE);
        pickaxe.enchant(Enchantments.SILK_TOUCH, 1);
        return pickaxe;
    }

    /** What a furnace makes of {@code input}, or an empty stack. Recipes match a container on 1.20.1. */
    public static ItemStack smelt(ServerLevel level, ItemStack input) {
        SimpleContainer container = new SimpleContainer(input);
        return level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, container, level)
                .map(recipe -> recipe.assemble(container, level.registryAccess()))
                .orElse(ItemStack.EMPTY);
    }

    /** Everything a stonecutter offers for {@code input}. Recipes match a container on 1.20.1. */
    public static List<ItemStack> stonecut(ServerLevel level, ItemStack input) {
        SimpleContainer container = new SimpleContainer(input);
        return level.getRecipeManager().getRecipesFor(RecipeType.STONECUTTING, container, level).stream()
                .map(recipe -> recipe.assemble(container, level.registryAccess()))
                .toList();
    }

    /** A vanilla loot table by its path, e.g. {@code chests/bastion_other}. Loot tables are a registry from 1.21. */
    public static LootTable lootTable(ServerLevel level, String path) {
        return level.getServer().getLootData().getLootTable(new ResourceLocation(path));
    }

    /**
     * Runs {@code body} with a player in the test's level who can click blocks as a real one does, through its game
     * mode, then takes the player out of the game again, so no test's mob goes after it. Each modloader makes the
     * player its own way on 1.20.1 ({@link TestLoader#mockServerPlayer}).
     */
    public static void withServerPlayer(GameTestHelper helper, Consumer<ServerPlayer> body) {
        ServerPlayer player = TestLoader.mockServerPlayer(helper);
        try {
            body.accept(player);
        } finally {
            if (player.server.getPlayerList().getPlayers().contains(player)) {
                player.server.getPlayerList().remove(player);
            } else {
                player.discard();
            }
        }
    }

    /**
     * Every recipe the server's data holds, by its id, as its file has it: what a mod's recipe format was read as. A
     * 1.20.1 recipe has no codec to write it back with, so its file is read.
     */
    public static Map<String, JsonElement> recipesAsJson(ServerLevel level) {
        ResourceManager resources = level.getServer().getResourceManager();
        Map<String, JsonElement> recipes = new HashMap<>();
        resources.listResources("recipes", id -> id.getPath().endsWith(".json")).forEach((file, resource) -> {
            String path = file.getPath();
            String id = file.getNamespace() + ":" + path.substring("recipes/".length(), path.length() - ".json".length());
            try (Reader reader = resource.openAsReader()) {
                recipes.put(id, JsonParser.parseReader(reader));
            } catch (IOException | RuntimeException ignored) {
                // A file that can't be read isn't a recipe the game has either.
            }
        });
        return recipes;
    }

    /**
     * The folders of Create's recipes, each with the options every recipe in it names. Create and Create: Aquatic
     * Ambitions have 1.20.1 builds for Forge. Pressing and splashing make silver from ore and ingots, which no option
     * takes away.
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

    /**
     * The trim of vanilla's material and pattern of those names. Registry lookups were renamed and trims moved package
     * in 1.21.2. A pattern newer than 1.20.1 (1.21's flow and bolt) is coast's here.
     */
    public static ArmorTrim trim(ServerLevel level, String material, String pattern) {
        if (pattern.equals("flow") || pattern.equals("bolt")) {
            pattern = "coast";
        }
        return new ArmorTrim(level.registryAccess().registryOrThrow(Registries.TRIM_MATERIAL)
                        .getHolderOrThrow(ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(material))),
                level.registryAccess().registryOrThrow(Registries.TRIM_PATTERN)
                        .getHolderOrThrow(ResourceKey.create(Registries.TRIM_PATTERN, new ResourceLocation(pattern))));
    }

    /** {@code stack} given {@code trim}. A trim is a component from 1.21, a tag before. */
    public static void setTrim(ServerLevel level, ItemStack stack, ArmorTrim trim) {
        ArmorTrim.setTrim(level.registryAccess(), stack, trim);
    }

    /** The trim {@code stack} carries, or null. A trim is a component from 1.21, a tag before. */
    public static ArmorTrim trimOf(ServerLevel level, ItemStack stack) {
        return ArmorTrim.getTrim(level.registryAccess(), stack).orElse(null);
    }

    /** {@code stack} named {@code name}, as an anvil names it. A name is a component from 1.21, a tag before. */
    public static void setCustomName(ItemStack stack, String name) {
        stack.setHoverName(Component.literal(name));
    }

    /** Whether {@code stack} carries a name it was given. A name is a component from 1.21, a tag before. */
    public static boolean hasCustomName(ItemStack stack) {
        return stack.hasCustomHoverName();
    }

    /**
     * The palette a trim of {@code material} draws with on worn {@code armor}, or null for its own. On 1.20.1 a trim
     * material's palettes are keyed by vanilla's armour materials alone, so a mod's armour always takes its own.
     */
    public static String trimPalette(ServerLevel level, ResourceKey<TrimMaterial> material, Item armor) {
        return null;
    }

    /** The placed feature of that key. Registry lookups were renamed in 1.21.2. */
    public static PlacedFeature placedFeature(ServerLevel level, ResourceKey<PlacedFeature> key) {
        return level.registryAccess().registryOrThrow(Registries.PLACED_FEATURE).getOrThrow(key);
    }

    /** A wind charge bursting at {@code at}. 1.20.1 has no wind charges, so the tests that burst one aren't on this line. */
    public static void windChargeBurst(ServerLevel level, Vec3 at) {
        throw new UnsupportedOperationException("1.20.1 has no wind charges");
    }

    /** The block entity at {@code pos} in the test, or null. {@code GameTestHelper.getBlockEntity} takes its class from 1.21.5. */
    public static BlockEntity blockEntity(GameTestHelper helper, BlockPos pos) {
        return helper.getBlockEntity(pos);
    }

    /** What a dispenser at {@code pos} dispenses from. A record of its level, place, state and block entity from 1.20.3. */
    public static BlockSource blockSource(ServerLevel level, BlockPos pos) {
        return new BlockSourceImpl(level, pos);
    }

    /** The trades of vanilla's profession of that name by level. The trade lists are keyed by the profession's key from 1.21.5. */
    public static Int2ObjectMap<VillagerTrades.ItemListing[]> villagerTrades(String profession) {
        return VillagerTrades.TRADES.get(BuiltInRegistries.VILLAGER_PROFESSION.get(new ResourceLocation(profession)));
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
        return TagKey.create(registry, new ResourceLocation(namespace, path));
    }

    /**
     * The resource of that name, if a pack has it. ResourceLocation is Identifier from 1.21.11. A path in 1.21's
     * {@code recipe} folder is read from 1.20.1's {@code recipes}, and a recipe there shows Forge's top-level
     * {@code conditions} as {@code neoforge:conditions} too, the key the bodies read a NeoForge line's by.
     */
    public static Optional<Resource> resource(ResourceManager resources, String namespace, String path) {
        Optional<Resource> resource = resources.getResource(new ResourceLocation(namespace, onThisLine(path)));
        if (resource.isEmpty() || !onThisLine(path).startsWith("recipes/")) {
            return resource;
        }
        Resource file = resource.get();
        return Optional.of(new Resource(file.source(), () -> {
            JsonElement json;
            try (Reader reader = file.openAsReader()) {
                json = JsonParser.parseReader(reader);
            }
            if (json.isJsonObject() && json.getAsJsonObject().has("conditions")) {
                json.getAsJsonObject().add("neoforge:conditions", json.getAsJsonObject().get("conditions"));
            }
            return new java.io.ByteArrayInputStream(json.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }));
    }

    /**
     * The paths of the JSON files under {@code folder} in {@code namespace}, sorted. ResourceLocation is Identifier from
     * 1.21.11. 1.21's {@code recipe} folder is 1.20.1's {@code recipes}.
     */
    public static List<String> jsonPaths(ResourceManager resources, String folder, String namespace) {
        return resources.listResources(onThisLine(folder), id -> id.getNamespace().equals(namespace) && id.getPath().endsWith(".json"))
                .keySet().stream().map(ResourceLocation::getPath).sorted().toList();
    }

    /** A data path as 1.20.1 names it: 1.21 made the recipe folder singular. */
    private static String onThisLine(String path) {
        return path.equals("recipe") || path.startsWith("recipe/") ? "recipes" + path.substring("recipe".length()) : path;
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
        return new Arrow(level, 0, 0, 0);
    }

    /** Hurts {@code mob} as {@code arrow} hitting it would, answering whether it took the hit. */
    public static boolean hurtByArrow(LivingEntity mob, Projectile arrow, float amount) {
        return hurt(mob, mob.level().damageSources().arrow((AbstractArrow) arrow, null), amount);
    }

    /** Whether {@code type} is in {@code tag}. {@code EntityType#is(TagKey)} went in 26.1, for its registry holder's. */
    public static boolean isIn(EntityType<?> type, TagKey<EntityType<?>> tag) {
        return type.is(tag);
    }

    /**
     * Whether Smite hurts a mob of {@code type} more: on 1.20.1 one whose mob type is undead, there being no
     * {@code minecraft:sensitive_to_smite} before 1.20.5.
     */
    public static boolean isSensitiveToSmite(ServerLevel level, EntityType<?> type) {
        Entity entity;
        try {
            entity = type.create(level);
        } catch (RuntimeException e) {
            return false;
        }
        if (entity == null) {
            return false;
        }
        boolean undead = entity instanceof LivingEntity living && living.getMobType() == MobType.UNDEAD;
        entity.discard();
        return undead;
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

    /** A player not in the level, in {@code mode}. {@code GameTestHelper.makeMockPlayer} takes the game mode from 1.20.5. */
    public static net.minecraft.world.entity.player.Player mockPlayer(GameTestHelper helper, net.minecraft.world.level.GameType mode) {
        return mode == net.minecraft.world.level.GameType.SURVIVAL ? helper.makeMockSurvivalPlayer() : helper.makeMockPlayer();
    }

    /** The holder of {@code material} in the trim material registry. */
    public static Holder<TrimMaterial> trimMaterial(ServerLevel level, ResourceKey<TrimMaterial> material) {
        return level.registryAccess().registryOrThrow(Registries.TRIM_MATERIAL).getHolderOrThrow(material);
    }
}
