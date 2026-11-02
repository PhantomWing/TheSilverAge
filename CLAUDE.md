@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/architectury.md

# The Silver Age - `version/1.21.11`

**This file describes the `version/1.21.11` line**: Minecraft 1.21.11, Fabric and NeoForge.
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

- Java 21, Mojang mappings with Parchment. `org.gradle.java.home` is pinned in `gradle.properties` to a JDK on this machine; if the daemon fails to start, check that path first.
- Datagen: `:neoforge:runData`. Output: `common/src/generated/resources`, never hand-edited.
- Game tests in `common/src/gametest`, run on both loaders: `:neoforge:runGameTest` and `:fabric:runGameTest`. Fabric's test source set is a mod of its own, `thesilverage_gametest`, whose `fabric.mod.json` names its registration as a `main` entrypoint: from 1.21.5 Fabric API takes one annotated method per test, so it registers into vanilla's test registries itself (the functions at once, the instances from `DynamicRegistrySetupCallback`). Laid out with The Lead Age's test shim (`verification.md`): the test classes and `GameTests` read the same on every line, `TestCompat` holds the calls whose shape differs between versions (on this line `fail` and `assertTrue` taking a Component, `create`, `hurt`, `convertTo`, `trim`, `trimPalette` from the material's asset group, `placedFeature`, `stonecut`'s lookup, `createRecipeFolders`, empty, since Create Fly's recipes carry Fabric's conditions alone, `recipesAsJson`, `windChargeBurst`, which takes the block particles from 1.21.9, and `withServerPlayer`, which makes its mock server player at once: from 1.21.9 a login waits in `PrepareSpawnTask`, which a mock player skips, so the wait for the entities around spawn that 1.21.6 to 1.21.8 need would only time out), and each loader's `src/test` has a `GameTestRegistration` that registers them: from 1.21.5 into the `test_function` and `test_instance` registries in code, never as `test_instance` JSONs. NeoForge's needs no guard for a plain dedicated server: NeoForge 21.10 and 21.11 post `RegisterGameTestsEvent` only while the registries load. Its game-test run is the `gameTestServer` template (`environment` and `forgeTemplate`), since the old properties do nothing from 21.5. They run in `silver_test_arena`, a 7x6x7 box with a stone floor and no walls, the floor at y = 1 and what stands on it at y = 2, as on 1.21.1: from 1.21.2 a test's structure is placed at the test's origin rather than a layer above it, so this line's arena has an empty bottom layer and its floor in the second (1.21.1's is 7x5x7 with the floor in its first).
- Play scenarios in `scenarios/`, one of them Fabric's alone (the config screen Mod Menu opens), and in `scenarios/server` one per modloader played on the line's dedicated server: turning `override_vanilla_recipes` off and on with `server_config` and `/reload`, the repeater's and comparator's textures following without a rejoin.
- The recipe overrides' textures are three built-in resource packs, `silver_repeater`, `silver_comparator` and `silver_brewing_stand`, one per override option. The repeater's and comparator's packs hold a copy of vanilla's blockstate pointing at child models in the mod's namespace (`thesilverage:block/silver_repeater_*`), never copies of vanilla's models: 1.21.2 reworked the redstone torch in them (`kb/mc-deltas/1.21.1-to-1.21.3.md`, issue #10).
- No Create for NeoForge, Create: Aquatic Ambitions, EMI, Enhanced Celestials (1 or 2), Werewolves or NeoForge Farmer's Delight for 1.21.11 (checked 2026-11-01), so that part of 1.4's compat isn't on this line: no channeling, and `compat/LunarEvents` answers as for an ordinary night (the Moon Dial keeps its layered textures, and its moon takes the `thesilverage:lunar_event` tint source in its `items/` definition, `client/ModItemColors`: from 1.21.4 an item's tint is data, not a colour handler). JEI (27.6.0.28, both loaders: from 27.7.0.29 JEI is built with Loom 1.17, which this rung's Loom 1.14.476 refuses) shows `compat/SilverInfo`'s info pages through the common `compat/jei` plugin, compiled against `jei-1.21.11-common-api`; Fabric names it as a `jei_mod_plugin` entrypoint. The Werewolves entries in the tags stay, optional, as on 1.21.1. `enable_create_spout_oxidation` does something on Fabric alone.
- **Create Fly** (Fabric's Create, mod id `create`) gets 1.21.1's Create recipes in its own format, which isn't Create's (one `ingredient`, a Deployer's `target`, a Spout's water in droplets, 20250 for Create's 250 mB): `neoforge/.../datagen/CreateFlyRecipeProvider` writes them as JSON from `SilverWeatheringSpec` into `fabric/src/generated/resources`, which only the Fabric jar carries (`fabric/build.gradle`'s `main` resources; the data run passes the folder as `thesilverage.datagen.fabricOutput`), each under `fabric:load_conditions` alone. It also crushes the ore and raw silver, which Create Fly leaves out, and smelts Crushed Raw Silver. A Deployer's and Press's result is written as both `result` and `results`: this rung's 6.0.9-2 reads the one stack, its 6.0.9-3 and newer the list. The Fabric dev runs load Create Fly 1.21.11-6.0.9-2 (`enable_create_fly`), the game tests too, but for the portal's server scenarios, whose `/reload` with Create Fly held the dev server's thread past the client's 30-second timeout on 1.21.8 (`fabric/build.gradle` knows them by their init scripts); 6.0.9-3 and newer are built with Loom 1.15, which this rung's Loom 1.14.476 refuses. Its manifest takes any Fabric Loader, so the dev runs keep `fabric_loader_version`. `create_processes_silver` reads each recipe back through its serializer. `common/src/generated` holds no Create recipes: 1.21.1's, in Create's format, which Create Fly fails to parse, were left there by earlier ports and removed.
- Lightning rods: 1.21.9 made vanilla's own rods oxidize and changed `LightningBolt` to power any `LightningRodBlock` and to scrape the struck block itself when it is a `WeatheringCopper`, so a Silver Lightning Rod powers and scrapes back to bare silver with no mixin (the 1.21.x lines before this have a `LightningBoltMixin`). The rods still need lightning's point of interest: NeoForge adds them through `ExtendPoiTypesEvent`, which NeoForge 21.11 has too, Fabric through `PoiTypesAccessor`. They are in `minecraft:lightning_rods` too, which Channeling and the skeleton trap read, and the bars, chains and lanterns in 1.21.9's `minecraft:bars`, `chains` and `lanterns` (bars of two kinds side by side hide the faces between them only when both are in `bars`). Vanilla's chain is `minecraft:iron_chain` from 1.21.9.
- 1.21.9 removed `IModFile.findResource`: NeoForge's `RecipeOverridePackHandler` finds and serves the three packs through the mod file's `JarContents` (`containsFile`, `JarContentsResourcesSupplier`), as NeoForge's own pack finder does; a folder resolved from its content roots exists in a dev run only (`kb/mc-deltas/1.21.8-to-1.21.10.md`, issue #9). The packs' `pack.mcmeta` give `min_format` 75 and `max_format` [75, 0].
- Models are vanilla's `ModelProvider` (`ModModelProvider`), which also writes the `items/` client item definitions; NeoForge's datagen runs as `clientData`. Blockstates are written with 1.21.5's `MultiVariant` and `VariantMutator` (`plainVariant`, `Y_ROT_90`, `ROTATIONS_COLUMN_WITH_FACING`). Render layers aren't in the model JSON from 1.21.4: the cutout and translucent blocks (doors, trapdoors, lanterns, chains, bars, the torch, grates) are registered in `client/ModItemProperties` on both loaders, as `ChunkSectionLayer`s (1.21.6) through Architectury's `RenderTypeRegistry`. Tag providers are 1.21.6's: NeoForge's `ItemTagsProvider`, which copies no block tags, so the block items go into item tags directly, and the damage type tags extend `DamageTypeTagsProvider` (`KeyTagProvider`).
- 1.21.11's own content: the Silver Spear (`Item.Properties#spear`, the copper spear's charge curve, `minecraft:spears`, `generateSpear`'s two models) and the Silver Nautilus Armor (`#nautilusArmor`, loot only, swapped for copper's in the ocean chests). The spear is one of `thesilverage:silver_smite_tools` and a zombie's iron spear can turn into it as its sword does (`MonsterArmorHandler`); the nautilus armour counts as a full set of silver armour for the hurt, as the horse armour does. Horse and nautilus armour are in `c:armors/horse` and `c:armors/nautilus`, which exist from 1.21.11. In the vanilla tabs they follow the golden spear and nautilus armour.
- 1.21.11 renamed `ResourceLocation` to `Identifier`, spelt `advancements.criterion` right, moved most entities into subpackages (`monster.zombie.Zombie`, `npc.villager.*`, `projectile.arrow.*`, `projectile.hurtingprojectile.windcharge.*`) and gave `ItemListing#getOffer` the level first. The test bodies name none of them: `TestCompat` has `tag`, `resource`, `jsonPaths`, `plainArrow`, `hurtByArrow` and `villagerOffers` on every line. Item sprites have their own atlas from 1.21.11, so the trim palettes are in `assets/minecraft/atlases/items.json`.
- Published with `publishMods` from `fabric/build.gradle` and `neoforge/build.gradle`, with the `-PpublishDryRun` flag. Uploads are tagged from `supported_minecraft_versions` (1.21.11 alone).
- GitHub Actions: `build.yml` and `release.yml`.
- Hand-authored access wideners and transformers: `common/src/main/resources/thesilverage.accesswidener` and `neoforge/src/main/resources/META-INF/accesstransformer.cfg`. A first suspect when a port fails to load.
