@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/architectury.md

# The Silver Age - `version/1.21.8`

**This file describes the `version/1.21.8` line**: Minecraft 1.21.8 (the jar covers 1.21.7 and 1.21.8), Fabric and NeoForge.
Built with Architectury Loom (`dev.architectury.loom`) with Mojang mappings layered with Parchment; `remapJar` is the shipped jar.

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
- **Tool tiers** - silver's blocks need an iron pickaxe, as gold's do; the bars, chain and pressure plate drop to any
  pickaxe, as vanilla's iron ones and weighted plates do, and the lantern and doors drop by hand, as vanilla's have
  from 1.21.4. Where a block has a vanilla counterpart, follow it.
- **`enable_silver_oxidation`, `enable_silver_arrows` and `enable_silver_gear`** are sent to clients
  (`ContentSyncPayload`), so the creative tabs and honeycomb follow the server's (the recipe viewers too, on the lines
  that have JEI or EMI); a dispenser's honeycomb is refused too (`SilverOxidation`).
- **`enable_silver_gear`** takes away the gear in `ModItems.isSilverGear` - the tools, sword, knife, spear, armour and
  horse armour - as `enable_silver_arrows` takes the arrows: their recipes (and the Silver Grappling Hook's) and the
  armour advancement through their conditions, their loot (`SilverLootSpec.itemEnabled`), the creative tabs, and mobs
  spawning with it, folded into `mobSilverEquipment()` as `silver_affects_undead` is into its effects. Gear already made
  still works and still smelts into nuggets.
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

- Java 21, Mojang mappings with Parchment. `org.gradle.java.home` is pinned in `gradle.properties` to a JDK on this machine; if the daemon fails to start, check that path first.
- Datagen: `:neoforge:runData`. Output: `common/src/generated/resources`, never hand-edited.
- Game tests in `common/src/gametest`, run on both loaders: `:neoforge:runGameTest` and `:fabric:runGameTest`. Fabric's test source set is a mod of its own, `thesilverage_gametest`, whose `fabric.mod.json` names its registration as a `main` entrypoint: from 1.21.5 Fabric API takes one annotated method per test, so it registers into vanilla's test registries itself (the functions at once, the instances from `DynamicRegistrySetupCallback`). Laid out with The Lead Age's test shim (`verification.md`): the test classes and `GameTests` read the same on every line, `TestCompat` holds the calls whose shape differs between versions (on this line `fail` and `assertTrue` taking a Component, `create`, `hurt`, `convertTo`, `trim`, `trimPalette` from the material's asset group, `placedFeature`, `stonecut`'s lookup, `createRecipeFolders`, empty, since Create Fly's recipes carry Fabric's conditions alone, `recipesAsJson`, and `withServerPlayer`, which waits for the entities around the world spawn before making a mock server player: from 1.21.6 to 1.21.8 placing one waits for them, and they only load as the level ticks, so a player made too soon hangs the run), and each loader's `src/test` has a `GameTestRegistration` that registers them: from 1.21.5 into the `test_function` and `test_instance` registries in code, never as `test_instance` JSONs. NeoForge's skips `RegisterGameTestsEvent` on a plain dedicated server, where NeoForge 21.5 posts it again with frozen registries; its game-test run is the `gameTestServer` template (`environment` and `forgeTemplate`), since the old properties do nothing from 21.5. They run in `silver_test_arena`, a 7x6x7 box with a stone floor and no walls, the floor at y = 1 and what stands on it at y = 2, as on 1.21.1: from 1.21.2 a test's structure is placed at the test's origin rather than a layer above it, so this line's arena has an empty bottom layer and its floor in the second (1.21.1's is 7x5x7 with the floor in its first).
- NeoForge's client run has `-Dneoforge.warnings.onlyin.hide=true`: NeoForge 21.8 stops the dev client on a warnings screen about Architectury's `@OnlyIn`, and quick play into a server (the server scenarios) waits behind it.
- Play scenarios in `scenarios/`, one of them Fabric's alone (the config screen Mod Menu opens), and in `scenarios/server` one per modloader played on the line's dedicated server: turning `override_vanilla_recipes` off and on with `server_config` and `/reload`, the repeater's and comparator's textures following without a rejoin.
- The recipe overrides' textures are three built-in resource packs, `silver_repeater`, `silver_comparator` and `silver_brewing_stand`, one per override option. The repeater's and comparator's packs hold a copy of vanilla's blockstate pointing at child models in the mod's namespace (`thesilverage:block/silver_repeater_*`), never copies of vanilla's models: 1.21.2 reworked the redstone torch in them (`kb/mc-deltas/1.21.1-to-1.21.3.md`, issue #10).
- No Create for NeoForge, Create: Aquatic Ambitions, EMI, Enhanced Celestials (1 or 2) or Werewolves for 1.21.7 or 1.21.8, so that part of 1.4's compat isn't on this line: no channeling, and `compat/LunarEvents` answers as for an ordinary night (the Moon Dial keeps its layered textures, and its moon takes the `thesilverage:lunar_event` tint source in its `items/` definition, `client/ModItemColors`: from 1.21.4 an item's tint is data, not a colour handler). JEI (24.2.0.6, both loaders) shows `compat/SilverInfo`'s info pages through the common `compat/jei` plugin, compiled against `jei-1.21.8-common-api`; Fabric names it as a `jei_mod_plugin` entrypoint. The Werewolves entries in the tags stay, optional, as on 1.21.1. `enable_create_spout_oxidation` does something on Fabric alone.
- **Create Fly** (Fabric's Create, mod id `create`) gets 1.21.1's Create recipes in its own format, which isn't Create's (one `ingredient`, a Deployer's `target`, a Spout's water in droplets, 20250 for Create's 250 mB): `neoforge/.../datagen/CreateFlyRecipeProvider` writes them as JSON from `SilverWeatheringSpec` into `fabric/src/generated/resources`, which only the Fabric jar carries (`fabric/build.gradle`'s `main` resources; the data run passes the folder as `thesilverage.datagen.fabricOutput`), each under `fabric:load_conditions` alone. It also crushes the ore and raw silver, which Create Fly leaves out, and smelts Crushed Raw Silver. A Deployer's and Press's result is written as both `result` and `results`, since 6.0.9-2 reads the list and the builds before it one stack. The Fabric dev runs load Create Fly 6.0.9-1 (`enable_create_fly`), the game tests too, but for the portal's server scenarios, whose `/reload` with Create Fly held the dev server's thread past the client's 30-second timeout (`fabric/build.gradle` knows them by their init scripts); 6.0.9-2 is built with Loom 1.15, which this rung's Loom refuses. It needs Fabric Loader 0.17.2, so the dev runs use `fabric_loader_dev_version` while the manifest keeps `fabric_loader_version`. `create_processes_silver` reads each recipe back through its serializer. `common/src/generated` holds no Create recipes: 1.21.1's, in Create's format, which Create Fly fails to parse, were left there by earlier ports and removed.
- The NeoForge jar runs on NeoForge 21.7 (Minecraft 1.21.7) and 21.8, and they differ on lightning rods. NeoForge's 1.21.8 builds have `ExtendPoiTypesEvent` again and patch `LightningBolt`'s two rod checks to `instanceof LightningRodBlock`; 21.7 has neither, as 21.3 to 21.6 didn't. So the line does what works on both: the Silver Lightning Rods' states go into `GameData.getBlockStatePointOfInterestTypeMap()` in common setup (naming the event would fail to load the mod on 21.7), as Fabric's `PoiTypesAccessor` puts them into vanilla's map, and NeoForge's `LightningBoltMixin` wraps vanilla's `state.is(Blocks.LIGHTNING_ROD)` with `require = 0`, applying on 21.7 and finding nothing on 21.8. Without `require = 0` NeoForge 21.8 stops at startup. The gates run 21.8 only; nothing checks 21.7.
- Models are vanilla's `ModelProvider` (`ModModelProvider`), which also writes the `items/` client item definitions; NeoForge's datagen runs as `clientData`. Blockstates are written with 1.21.5's `MultiVariant` and `VariantMutator` (`plainVariant`, `Y_ROT_90`, `ROTATIONS_COLUMN_WITH_FACING`). Render layers aren't in the model JSON from 1.21.4: the cutout and translucent blocks (doors, trapdoors, lanterns, chains, bars, the torch, grates) are registered in `client/ModItemProperties` on both loaders, as 1.21.6's `ChunkSectionLayer`s through Architectury's `RenderTypeRegistry`. Tag providers are 1.21.6's: NeoForge's `ItemTagsProvider`, which copies no block tags, so the block items go into item tags directly, and the damage type tags extend `DamageTypeTagsProvider` (`KeyTagProvider`).
- Two config options pick the creative tabs, read from the player's own config: `enable_creative_tab` (the mod's tab,
  hidden by giving it no items) and `enable_vanilla_creative_tabs` (`ui/VanillaTabs`: each item after its counterpart,
  in vanilla's tabs and other mods' - the knife after Farmer's Delight's Golden Knife, the sheet after Create Fly's
  Golden Sheet in `create:base`). Redstone Blocks lists only the waxed bulbs, plates and rods, as vanilla does its copper
  bulbs. The Silver Spear's anchor, Backported Spears' Golden Spear, needs that mod's insertions to run first, which
  `neoforge.mods.toml` orders with an optional `AFTER` dependency. That ordering only reaches listeners on this mod's own
  bus, so on NeoForge the tabs are filled through `CreativeTabPlatform` rather than Architectury's
  `CreativeTabRegistry.modify`, which listens on Architectury's bus. NeoForge's `insertAfter` throws on an anchor the
  tab doesn't list, so `VanillaTabs` tries a list of anchors; Fabric's `addAfter` would append one it doesn't list at the
  end, so its side throws too, and inserts in a phase after the default one.
- The Silver Spear is always registered, so a world keeps it, and only shown in the creative tabs with Backported Spears
  (`spears`, `ModItems`, `BackportPlatform`), which makes it with its `Spears.registerSpearRaw(id, ModTiers.SILVER, ...)`
  and 1.21.11's numbers; without it the spear is a plain item. On Fabric Backported Spears registers the components and
  sounds a spear is built of in its own initializer, and Fabric Loader starts the mods in no set order, `suggests` or
  not, so `fabric/.../compat/spears/SilverSpear.register` registers the spear with the other items where Backported
  Spears has started (its `WOODEN_SPEAR` is set), and otherwise from a `RegistryEntryAddedCallback` on the item
  registry, with the next item after that is: built before them, it was a plain item (1.4.0). The Lead Age's 1.21.8
  line, which does the same, was played with Backported Spears 1.0.5 started last and kept its spear. Both manifests ask
  for 1.0.5 or newer (`breaks` `<1.0.5` on Fabric), the version it is built and checked against. Its `items/` definition draws it flat in the GUI, on the ground and in a frame, and its
  `_in_hand` model held (`spear_in_hand`, copied into this namespace), as Backported Spears' own spears are. Its recipe and
  smelting load with the mod; it is in `minecraft:spears` (Lunge and the melee enchantments, through Backported Spears'
  enchantable tags) and `silver_smite_tools`. `weapon_attributes/silver_spear.json` gives it Better Combat's spear
  swings. Backported Spears' Fabric build asks for Fabric Loader 0.18.4, past the dev runs' 0.17.2, so only NeoForge's dev
  runs load it.
- Yori3o's Grappling Hooks 4.5.3 on Fabric reads every mod's `yo_hooks/hooks.json`, so the Silver Grappling Hook is
  data alone, in `fabric/src/main/resources`: the hook (62 durability, 14 long, 10 enchantability), its `yo_hooks`
  `items/` definitions, models, textures, names and tags, and its recipes, hand-written in `data/thesilverage/recipe` behind Fabric's mod-loaded condition. Its NeoForge build for
  1.21.8 (4.5.1, the newest) reads only the first `yo_hooks/hooks.json` on the classpath: with the mod's in the NeoForge
  jar it read ours alone, registered none of its own hooks, and its enchantments' missing `#yo_hooks:hooks` stopped every
  world loading. So the NeoForge jar carries none of it, and NeoForge's dev runs don't load the hook mod. The hook's
  `items/` definition names Grappling Hooks' `yo_hooks:hook_active` condition, which fails to parse with an error in a
  client without the mod, so it is in a built-in pack, `resourcepacks/silver_grappling_hook`, that
  `TheSilverAgeFabricClient` registers only where the mod is loaded.
- Each optional mod the dev runs load has a switch of its own in `gradle.properties`, `enable_<mod>` (Create Fly and
  Grappling Hooks on Fabric, Backported Spears on NeoForge); `-Penable_grappling_hooks=false` and the like test without one. The game-test
  runs load none of them but Create Fly, so their servers are the check that the mod loads alone.
- Published with `publishMods` from `fabric/build.gradle` and `neoforge/build.gradle`, with the `-PpublishDryRun` flag. Uploads are tagged from `supported_minecraft_versions` (1.21.7 and 1.21.8), and the Fabric manifest asks for `fabric_api_min_version`, 1.21.7's newest Fabric API.
- GitHub Actions: `build.yml` and `release.yml`.
- Hand-authored access wideners and transformers: `common/src/main/resources/thesilverage.accesswidener` and `neoforge/src/main/resources/META-INF/accesstransformer.cfg`. A first suspect when a port fails to load.
