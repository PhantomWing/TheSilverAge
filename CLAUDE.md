@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/architectury.md

# The Silver Age - `version/26.2`

**This file describes the `version/26.2` line**: Minecraft 26.2, Fabric and NeoForge.
Built with Architectury Loom without remapping (`loom-no-remap`): Minecraft 26.x ships unobfuscated, so there are no mappings and `shadowJar` is the shipped jar.

It belongs to whichever folder has this branch checked out - the main `TheSilverAge` folder or a worktree
under `TheSilverAge/.worktrees/`. A session started in a worktree also loads the main folder's CLAUDE.md,
which describes another line; for this folder, this file is the one that applies. Confirm with
`git branch --show-current`. `MinecraftDeveloperPortal/data/mods.json` lists every line of the mod.

Vanilla-friendly mod adding oxidizable silver content. Mod ID `thesilverage`, package
`com.phantomwing.thesilverage`. `TheSilverAge-Wiki` in the workspace root is its GitHub wiki.

Every `version/*` line is Architectury. `neoforge/1.21` and `forge/1.20` are single-loader
leftovers from before the split, not lines. A root `src/` folder may linger on disk from that
layout; nothing in it is tracked or built.

## Layout

```
common/src/main/java/com/phantomwing/thesilverage/   loader-agnostic code
fabric/   ... /fabric/ + /platform/                  Fabric entry point and platform implementations
neoforge/ ... /neoforge/ + /platform/                NeoForge entry point and platform implementations
```

`platform/` is the Architectury expect/actual boundary: the interface lives in `common`, each loader
supplies its own implementation. Anything loader-specific goes there rather than behind a runtime
check.

## Domain conventions

- **Weathering/oxidation** - silver blocks follow the copper pattern: `UNAFFECTED → EXPOSED →
  WEATHERED → OXIDIZED`, each with a normal and a waxed variant. Registered through generic helpers
  in `ModBlocks`; the state mappings live in `ModOxidizables` and `ModWaxables`. Adding a silver
  block usually means touching all three.
- **Creative tab** - `ModItems.CREATIVE_TAB_ITEMS` is a `LinkedHashSet`, so insertion order *is* tab
  order. Items are added by the registration helpers, not by hand.
- **Tags** - `ModTags` for the mod namespace, `CommonTags` for `c:`. Silver interoperates with other
  mods almost entirely through `c:ingots/silver` and friends; keep new materials tagged there.
- **Config** - options that change recipes or loot are emitted by datagen wrapped in a condition, so
  the condition lands in the JSON rather than in a runtime branch.
- **Undead** - every silver effect on mobs (the tools' bonus, the armour hurting undead attackers, silver floors) checks
  `thesilverage:sensitive_to_silver`, which holds `#minecraft:sensitive_to_smite` and, optionally,
  Werewolves: Become a Beast's `#werewolves:werewolf`, and lets a pack add a mob Smite leaves alone. Never check the
  smite tag directly. The `silver_affects_undead` option turns every one of them off: each loader's `CommonConfigImpl`
  folds it into the getter of each effect's own option, so a new effect's getter needs it too. The tooltips read the
  server's settings, sent on join and whenever the config changes (`SilverUndeadSettings`, `ModNetworking.configChanged`). A
  player's swing takes the tools' bonus in `ServerPlayer#getEnchantedDamage` (twin `ServerPlayerMixin`s), where Smite's goes, so the
  swing's charge scales it; every other hit takes it as it comes in. Silver armour's hurt (`combat/SilverThornsHandler`,
  `silver_thorns_chance` a piece, 20% by default, Thorns damage) is called from `LivingEntity#hurtServer`'s return (`hurt` before 1.21.2) by twin
  `LivingEntityMixin`s, so it fires only on a hit that landed, and puts back the attacker's damage cooldown after, or
  vanilla's Thorns, which hurts later in the same strike, lost the burn's damage. Silver floors (`combat/SilverFloor`, the blocks in
  `thesilverage:silver_floors`) are hooked by twin mixins in each loader: `Block#stepOn`, `isValidSpawn`, and the
  walk evaluator and `RandomPos` for avoidance. Path types can't carry the avoidance, since a level caches them once
  for every mob. `SilverUndeadGoals` gives every such mob two goals as it joins a level (NeoForge's `EntityJoinLevelEvent`,
  Fabric's `PersistentEntitySectionManagerMixin`, which Architectury's `EntityEvent.ADD` misses for chunk loads), both of
  which give way to a target: `LeaveSilverFloorGoal` walks it off silver, and `AvoidSilverLightGoal` keeps it 6 blocks
  from the blocks in `thesilverage:undead_repellents` (the Silver Torch and Lantern), The Lead Age's
  `AvoidRepellentBlockGoal` with silver's rule. The floor's damage type is in `minecraft:no_knockback`, as magma's is, and
  `SilverFloor` hops a mob itself, at most every 3 seconds. The Silver Pressure Plate is a silver floor a mob stands in rather than on: it hurts through its own
  `entityInside`, and `SilverFloor` looks at the block a mob stands in for a plate. It is pressed by any living entity
  but a hostile one (`Enemy`) or one in `thesilverage:cannot_press_silver_pressure_plates`, which holds
  `sensitive_to_silver`.
- **Tool tiers** - silver's blocks need an iron pickaxe, as gold's do; the bars, chain, lantern and pressure plate drop
  to any pickaxe, as vanilla's iron ones and weighted plates do. Where a block has a vanilla counterpart, follow it.
- **`enable_silver_oxidation` and `enable_silver_arrows`** are sent to clients (`ContentSyncPayload`), so the creative
  tabs and honeycomb follow the server's (the recipe viewers too, on the lines that have JEI or EMI); a dispenser's honeycomb is refused too (`SilverOxidation`).
- **Silver's colours** - bare silver is meant to be very light, and each tarnish stage darker; unlike
  copper, whose stages change hue, silver's change lightness. Each stage's block texture holds its ramp.
  A thin part takes a band of it: the bars' three lighter shades are the stage's chain's, their two
  darker ones the two block shades just below those, about 23 lightness points in all - the whole ramp
  read harsh, five neighbouring shades flat. An item sprite drawn apart from its block texture (the
  chain, the lantern) uses that block's shades. A silver light (torch flame, lantern) is a mystical
  bright purple to pink, where vanilla's is orange: the torch's flame `#a141ff` to `#ffe0ff`, the lantern's
  `#8e2fd8`, `#b44cff`, `#dc65ff`, `#f28bff`, `#fdb2ff`, `#ffe0ff`, recoloured onto the torch's family from a bluer
  violet that read off beside it, which the developer picked (2026-10-31). `kb/pixel-art.md` has vanilla's side of it.

JEI, AppleSkin and Jade are runtime-only dev dependencies, not compiled against. They are free to drop
on a rung where they have no build; that does not block the line.

## This line

- Java 25, no mappings and no Parchment (Mojang names are the runtime names). `org.gradle.java.home` is pinned in `gradle.properties` to a JDK on this machine; if the daemon fails to start, check that path first.
- Datagen: `:neoforge:runData`. Output: `common/src/generated/resources`, never hand-edited.
- Game tests in `common/src/gametest`, run on both loaders: `:neoforge:runGameTest` and `:fabric:runGameTest`. Fabric's test source set is a mod of its own, `thesilverage_gametest`, whose `fabric.mod.json` names its registration as a `main` entrypoint, which registers into vanilla's test registries itself (the functions at once, the instances from `DynamicRegistrySetupCallback`); NeoForge's run is the `gameTestServer` template (`environment` and `forgeTemplate`). Laid out with The Lead Age's test shim (`verification.md`): the test classes and `GameTests` read the same on every line, and `TestCompat` holds the calls whose shape differs. On this line that is 26.1's and 26.2's: the entity types the bodies spawn are constants of `TestCompat` (26.2 moved them to `EntityTypes`), `assemble` takes the input alone, `villagerOffers` makes the offers of the `minecraft:<profession>/level_N` trade sets, `setDayTime` sets the overworld's world clock, `isIn` asks an entity type's registry holder (`EntityType#is(TagKey)` went), `entityTags` is `Entity#entityTags`, and `nameTagGrid` is vanilla's Name Tag recipe (empty on the 1.21.x lines). Both registrations take `TestEnvironmentDefinition<?>`. Gradle 9 fails a `test` task that finds no JUnit tests, so both loaders set `failOnNoDiscoveredTests = false`. They run in `silver_test_arena`, a 7x6x7 box with a stone floor and no walls, the floor at y = 1 and what stands on it at y = 2.
- Play scenarios in `scenarios/`, one of them Fabric's alone (the config screen Mod Menu opens), and in `scenarios/server` one per modloader played on the line's dedicated server: turning `override_vanilla_recipes` off and on with `server_config` and `/reload`, the repeater's and comparator's textures following without a rejoin.
- The recipe overrides' textures are three built-in resource packs, `silver_repeater`, `silver_comparator` and `silver_brewing_stand`, one per override option. The repeater's and comparator's packs hold a copy of vanilla's blockstate pointing at child models in the mod's namespace (`thesilverage:block/silver_repeater_*`), never copies of vanilla's models. NeoForge's `RecipeOverridePackHandler` finds and serves them through the mod file's `JarContents`. Their `pack.mcmeta` give `min_format` 88 and `max_format` [88, 0], 26.2's resource format.
- Recipes 26.1 changed: the Lodestone is made of an Iron Ingot, so its fallback takes `c:ingots/iron`; the Firework Star's shapes are its recipe's data, so the Silver Nugget's star is a datagen override under the `override_vanilla_recipes` switch alone (`ModRecipeProvider.fireworkStarOverride`); and vanilla's Name Tag takes `#minecraft:metal_nuggets`, which holds the Silver Nugget, so it needs no override. Cooking recipes take `CookingBookCategory`.
- Villager trades are data from 26.1 (`ModVillagerTradeProvider`): the Cleric's level-2 Silver Ingot trade and the master Fletcher's level-5 Silver Arrows (5 Arrows and 2 Emeralds, as vanilla's tipped arrows) are `villager_trade` files added to vanilla's pool tags, gated by `enable_villager_trades` at load. `enable_silver_arrows` is read as the villager levels up, through the trade's merchant predicate, the loot condition `thesilverage:silver_arrows_enabled` (`villager/SilverArrowsEnabled`). `SilverVillagerTrades` and both loaders' `ModVillagerTrades` are vestigial.
- No Create, Create: Aquatic Ambitions, EMI, Enhanced Celestials (1 or 2), Werewolves or NeoForge Farmer's Delight for 26.2 (checked 2026-11-01), so that part of 1.4's compat isn't on this line: no Create recipes, and `compat/LunarEvents` answers as for an ordinary night (the Moon Dial keeps its layered textures, and its moon takes the `thesilverage:lunar_event` tint source in its `items/` definition, `client/ModItemColors`). JEI (both loaders) shows `compat/SilverInfo`'s info pages through the common `compat/jei` plugin, compiled against `jei-26.2-common-api`; Fabric names it as a `jei_mod_plugin` entrypoint. The Werewolves entries in the tags stay, optional. `enable_create_spout_oxidation` is still in both configs and does nothing here.
- Lightning rods: vanilla's `LightningBolt` powers any `LightningRodBlock` and scrapes the struck block itself when it is a `WeatheringCopper`, so a Silver Lightning Rod needs no mixin. The rods still need lightning's point of interest: NeoForge adds them through `ExtendPoiTypesEvent`, Fabric through `PoiTypesAccessor`. They are in `minecraft:lightning_rods` too, and the bars, chains and lanterns in `minecraft:bars`, `chains` and `lanterns`. Vanilla's chain is `minecraft:iron_chain`.
- Models are vanilla's `ModelProvider` (`ModModelProvider`), which also writes the `items/` client item definitions; NeoForge's datagen runs as `clientData`. From 26.1 a texture in a model is a `Material` (`TextureMapping` takes and gives them). There are no programmatic render layers from 26.1 (Architectury's `RenderTypeRegistry` is gone): the doors, trapdoors, lanterns, chains, bars and torch draw cutout from their textures' alpha.
- 1.21.11's content: the Silver Spear (`Item.Properties#spear`, `minecraft:spears`) and the Silver Nautilus Armor (loot only). The spear is one of `thesilverage:silver_smite_tools` (its hits are `minecraft:spear`) and a zombie's iron spear can turn into it as its sword does; the nautilus armour counts as a full set of silver armour for the hurt, as the horse armour does. In the vanilla tabs they follow the golden spear and nautilus armour.
- 26.1 names this line's code uses: `EntityType` tags through `builtInRegistryHolder().is(tag)`, `Entity#entityTags`, Fabric API's `ParticleProviderRegistry`, Cloth Config's `AutoConfigClient` for the Mod Menu screen (Mod Menu 20.0.3, `compileOnly`).
- 26.2's: entity types are `EntityTypes.X`; the copper families are `WeatheringCopperCollection`s (`Blocks.COPPER_BULB.weathering().unaffected()`, `Items.COPPER_CHAIN.waxed().oxidized()`), which the vanilla tabs' anchors follow; `ItemTags` lost the constants of the tags that mirror a block family (`ModItemTagsProvider.vanillaItemTag`); a tag appender takes keys, so the providers hand out The Lead Age's `ValueAppender`; advancement criteria are in `advancements.triggers` and `advancements.predicates`; `LivingEntity#knockback` takes the damage source and the damage dealt (`SilverFloor` passes its own, since a Sulfur Cube reads the source's entity); `Minecraft#gui` holds the screen and the overlay. Architectury 21: `RIGHT_CLICK_BLOCK` returns `EventResult`, and a `RegistrySupplier` is no longer a `Holder` (`asHolder()` is null on NeoForge until it registers), so the silver armour material is made when the armour registers (`ModArmorMaterials.SILVER_ARMOR_MATERIAL` is a memoized supplier).
- The Silver Spear's charge curve is vanilla copper_spear's on this version: 26.1 raised its dismount threshold from 9.0 to 12.0. The first float, 1.0, is the mod's own attack speed.
- Published with `publishMods` from `fabric/build.gradle` and `neoforge/build.gradle`, with the `-PpublishDryRun` flag. Uploads are tagged from `supported_minecraft_versions`.
- GitHub Actions: `build.yml` and `release.yml`.
- Hand-authored access wideners and transformers: `common/src/main/resources/thesilverage.accesswidener` and `neoforge/src/main/resources/META-INF/accesstransformer.cfg`. A first suspect when a port fails to load.
