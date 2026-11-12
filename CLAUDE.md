@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/architectury.md

# The Silver Age - `version/1.21.4`

**This file describes the `version/1.21.4` line**: Minecraft 1.21.4, Fabric and NeoForge.
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
  pickaxe, as vanilla's iron ones and weighted plates do, and the lantern drops by hand, as vanilla's has from 1.21.4.
  Where a block has a vanilla counterpart, follow it.
- **`enable_silver_oxidation`, `enable_silver_arrows` and `enable_silver_gear`** are sent to clients
  (`ContentSyncPayload`), so the creative tabs, the recipe viewers and honeycomb follow the server's; a dispenser's
  honeycomb is refused too (`SilverOxidation`).
- **`enable_silver_gear`** takes away the gear in `ModItems.isSilverGear` - the tools, sword, knife, armour and horse
  armour - as `enable_silver_arrows` takes the arrows: their recipes (and the Silver Grappling Hook's) and the armour
  advancement through their conditions, their loot (`SilverLootSpec.itemEnabled`), the creative tabs, and mobs spawning
  with it, folded into `mobSilverEquipment()` as `silver_affects_undead` is into its effects. Gear already made still
  works and still smelts into nuggets.
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
- Game tests in `common/src/gametest`, run on both loaders: `:neoforge:runGameTest` and `:fabric:runGameTest`. Fabric's test source set is a mod of its own, `thesilverage_gametest`, whose `fabric.mod.json` names the `fabric-gametest` entrypoint. Laid out with The Lead Age's test shim (`verification.md`): the test classes and `GameTests` read the same on every line, `TestCompat` holds the calls whose shape differs between versions (on this line `create`, `hurt`, `convertTo`, `trim`, `trimPalette`, `placedFeature`, `stonecut`'s lookup and `createRecipeFolders`, empty, since there is no Create), and each loader's `src/test` has a `GameTestRegistration` that registers them. They run in `silver_test_arena`, a 7x6x7 box with a stone floor and no walls, the floor at y = 1 and what stands on it at y = 2, as on 1.21.1: from 1.21.2 a test's structure is placed at the test's origin rather than a layer above it, so this line's arena has an empty bottom layer and its floor in the second (1.21.1's is 7x5x7 with the floor in its first).
- Play scenarios in `scenarios/`, one of them Fabric's alone (the config screen Mod Menu opens), and in `scenarios/server` one per modloader played on the line's dedicated server: turning `override_vanilla_recipes` off and on with `server_config` and `/reload`, the repeater's and comparator's textures following without a rejoin.
- The recipe overrides' textures are three built-in resource packs, `silver_repeater`, `silver_comparator` and `silver_brewing_stand`, one per override option. The repeater's and comparator's packs hold a copy of vanilla's blockstate pointing at child models in the mod's namespace (`thesilverage:block/silver_repeater_*`), never copies of vanilla's models: 1.21.2 reworked the redstone torch in them (`kb/mc-deltas/1.21.1-to-1.21.3.md`, issue #10).
- No Create, Create: Aquatic Ambitions, EMI, Enhanced Celestials (1 or 2) or Werewolves for 1.21.4, so that part of 1.4's compat isn't on this line: no EMI info pages, no Create recipes, and `compat/LunarEvents` answers as for an ordinary night (the Moon Dial keeps its layered textures, and its moon takes the `thesilverage:lunar_event` tint source in its `items/` definition, `client/ModItemColors`: from 1.21.4 an item's tint is data, not a colour handler). JEI has a NeoForge build (20.0.0.4): `compat/jei` and its info pages (`compat/SilverInfo`) are compiled against `jei-1.21.4-common-api` and loaded in NeoForge dev runs; JEI has no Fabric 1.21.4 build, so Fabric's manifest names no JEI plugin. The Werewolves entries in the tags stay, optional, as on 1.21.1. `enable_create_spout_oxidation` is still in both configs and does nothing here.
- NeoForge 21.4 has no `ExtendPoiTypesEvent`: the Silver Lightning Rods' states go into `GameData.getBlockStatePointOfInterestTypeMap()` in common setup, as Fabric's `PoiTypesAccessor` puts them into vanilla's map. Its `LightningBolt` checks the rod with vanilla's `state.is(Blocks.LIGHTNING_ROD)`, so NeoForge takes Fabric's `LightningBoltMixin` too.
- Models are vanilla's `ModelProvider` (`ModModelProvider`), which also writes the `items/` client item definitions; NeoForge's datagen runs as `clientData`. Render layers aren't in the model JSON from 1.21.4: the cutout and translucent blocks (doors, trapdoors, lanterns, chains, bars, the torch, grates) are registered in `client/ModItemProperties` on both loaders.
- Two config options pick the creative tabs, read from the player's own config: `enable_creative_tab` (the mod's tab,
  hidden by giving it no items) and `enable_vanilla_creative_tabs` (`ui/VanillaTabs`: each item after its counterpart,
  in vanilla's tabs and other mods' - the knife after Farmer's Delight's Golden Knife and the sheet after Create's
  Golden Sheet in `create:base`, both dormant here since neither mod has a 1.21.4 build). Redstone Blocks lists only the
  waxed bulbs, plates and rods, as vanilla does its copper bulbs; the Silver Arrow follows the Arrow. The tabs are
  filled through `CreativeTabPlatform`: on NeoForge from the mod's own bus (`BuildCreativeModeTabContentsEvent`), where
  `neoforge.mods.toml`'s ordering reaches, rather than Architectury's `CreativeTabRegistry.modify`, which listens on
  Architectury's bus; on Fabric in a phase after the default one. Both throw on an anchor the tab doesn't list, so
  `VanillaTabs` can try a list of anchors, as 1.21.1 does for the Golden Spear.
- Yori3o's Grappling Hooks reads every jar's `yo_hooks/hooks.json` from its Fabric 4.0 on 1.21.4, so the Silver
  Grappling Hook is data alone, in `common/src/main/resources`: the hook (62 durability, 14 long, 10 enchantability),
  its `yo_hooks` models, textures, names and tags, its `items/` definitions (the `yo_hooks:hook_active` condition shows
  the handle while cast) in the built-in pack `resourcepacks/grappling_hooks`, which Fabric's client turns on only with
  the mod (a 1.21.4 client logs an error for every `items/` definition naming a property nobody registered), and its recipes, hand-written in `data/thesilverage/recipe` behind the hook item being
  registered (`neoforge:registered`, `fabric:registry_contains`) and `enable_silver_gear`. Its NeoForge builds for
  1.21.4 (to 3.8) have a fixed set of hooks and no silver one, which those conditions answer. The Fabric build needs
  Fabric Loader 0.17, past this line's 0.16.14, so no dev run loads it and the hook is untested in the game here.
- Each optional mod the dev runs load has a switch of its own in `gradle.properties`, `enable_<mod>`: `enable_jade` and
  `enable_jei`, both NeoForge's; `-Penable_jei=false` and the like test without one. The game-test runs load none of
  them, so their servers are the check that the mod loads alone.
- Published with `publishMods` from `fabric/build.gradle` and `neoforge/build.gradle`, with the `-PpublishDryRun` flag. Uploads are tagged with `minecraft_version` alone, which is the whole rung here.
- Hand-authored access wideners and transformers: `common/src/main/resources/thesilverage.accesswidener` and `neoforge/src/main/resources/META-INF/accesstransformer.cfg`. A first suspect when a port fails to load.
