@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/architectury.md

# The Silver Age - `version/1.21`

**This file describes the `version/1.21` line**: Minecraft 1.21.1 (the jar covers 1.21 and 1.21.1), Fabric and NeoForge. A **baseline** line: new features start here.
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
  `silver_thorns_chance` a piece, 20% by default, Thorns damage) is called from `LivingEntity#hurt`'s return by twin
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
  tabs, the recipe viewers and honeycomb follow the server's; a dispenser's honeycomb is refused too (`SilverOxidation`).
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
- Game tests in `common/src/gametest`, run on both loaders: `:neoforge:runGameTest` and `:fabric:runGameTest`. Fabric's test source set is a mod of its own, `thesilverage_gametest`, whose `fabric.mod.json` names the `fabric-gametest` entrypoint. Laid out with The Lead Age's test shim (`verification.md`): the test classes and `GameTests` read the same on every line, `TestCompat` holds the calls whose shape differs between versions, and each loader's `src/test` has a `GameTestRegistration` that registers them. They run in `silver_test_arena`, a 7x5x7 box with a stone floor and no walls. A test's positions count from the structure block a layer below the arena, so the floor is at y = 1 and what stands on it at y = 2: a mob spawned at y = 1 is inside the stone and plans no path.
- 11 play scenarios in `scenarios/`, one of them Fabric's alone (the config screen Mod Menu opens), and in `scenarios/server` one per modloader played on the line's dedicated server: turning `override_vanilla_recipes` off and on with `server_config` and `/reload`, the repeater's and comparator's textures following without a rejoin. Each modloader keeps its config in a file of its own, so each names its `loader`.
- Create is NeoForge's alone on this rung (no Fabric Create or Create Fly for 1.21.1), and the game-test run can't load it (`neoforge/build.gradle`), so its machines on silver are six NeoForge scenarios, `scenarios/create-*.json`, each a machine on a Create Depot driven by a Creative Motor, its result taken off the Depot by hand: the Press, the Deployer (waxing with a Honeycomb Block, scraping wax and a tarnish stage with an axe), the Spout (one stage per 250 mB; it keeps pouring while a block stays under it, so it is filled with 250 alone through the block entity's `Tanks`), the Encased Fan washing through water, the Crushing Wheels (one wheel's motor on each side, so they turn inward) and smelting Crushed Raw Silver. A Deployer takes the item in its hand from a click on its working face; a click on its side sets its filter. Create: Aquatic Ambitions (NeoForge's dev runs carry 2.0.4, which takes Create 6.0.8 and up) is played in `create-aquatic-ambitions-channeling-*.json`: a fan blowing through a Conduit Cage channels one tarnish stage and leaves waxed silver alone. The cage awakens only while it has water to burn, so it is set again with 1000 mB in its `InputTanks` (not `Tanks`) before each item, and `setblock` over the same block does nothing, so it goes through air first.
- Newer vanilla content that mods backport to 1.21.1, under vanilla's own ids (2026-11-02):
  - The Silver Spear and Silver Nautilus Armor are always registered, so a world keeps them, and only shown in the
    creative tabs with the mod that backports them (`ModItems`, `BackportPlatform`). With Backported Spears (`spears`)
    the spear is made by its `Spears.registerSpearRaw` with 1.21.11's numbers, and with Vanilla Backport the armour
    is its `NautilusArmorItem`, whose renderer reads `thesilverage:textures/entity/nautilus/armor/silver.png`.
    Without them each is a plain item. The two mods register the same spear components and crash a client holding a
    spear when both are installed, so NeoForge's game runs take Backported Spears and its datagen Vanilla Backport
    (whose Copper Nautilus Armor the nautilus loot modifiers name), and Fabric's runs Vanilla Backport alone.
  - On Fabric, Backported Spears registers its components in its own initializer, so the spear is only a spear
    where it ran first (`suggests` orders it); otherwise it falls back to a plain item and logs a warning.
  - Without Backported Spears, Vanilla Backport makes the spear: its `SpearItem` takes the same numbers in the same
    order. It draws only its own seven spears held as spears, so the Silver Spear's held model is registered with its
    Platform library's `GameRendering.registerHandHeldModels` in each loader's client setup, after the items exist.
    Its recipe and smelting load with either mod (`neoforge:or`, which `FabricConditionsProvider` turns into
    `fabric:or`). `weapon_attributes/silver_spear.json` gives it Better Combat's spear swings, as Backported Spears
    does its own.
  - Vanilla Backport's Sulfur Cube swallows silver's blocks, ores, cut and chiseled silver and bulbs as copper's,
    in `minecraft:sulfur_cube_archetype/slow_flat` (26.2's vanilla tag, which the 26.x lines lack too). Its Parched
    is in `can_wear_silver_armor`, optional; its Camel Husk, Zombie Nautilus and Parched are in vanilla's zombie and
    skeleton tags, so silver hurts them with nothing added. A play session's world is Peaceful: summon hostile mobs
    with `difficulty normal`.
  - Silver Nautilus Armor takes 1.21.11's six ocean chest swaps from Copper Nautilus Armor (`SilverLootSpec`), each
    with `requiredMod`, so its loot modifier loads, and Fabric's mixin applies it, only with Vanilla Backport.
  - `BackportTags` holds the 1.21.9 tags (`minecraft:bars`, `chains`, `lanterns`, `lightning_rods`, `spears`) that
    Vanilla Backport and Copper Age Backport fill on this version.
  - Yori3o's Grappling Hooks reads every jar's `yo_hooks/hooks.json`, so the Silver Grappling Hook is data alone, in
    `common/src/main/resources`: the hook (62 durability, 14 long, 10 enchantability, between iron's and gold's as
    silver's tools are), its `yo_hooks` models, textures (iron's recoloured onto the Silver Pickaxe's ramp), names and
    tags, and its recipes, hand-written in `data/thesilverage/recipe` behind both loaders' mod-loaded conditions.
  - Backported Spears needs Fabric Loader 0.18.1 and Grappling Hooks 0.19, past this line's 0.16.14, so both are
    only tried on NeoForge in dev. YACL's jar bundles libraries a dev run doesn't unpack, which
    `forgeRuntimeLibrary` adds.
- Mekanism (NeoForge only, 10.7, `modCompileOnly`) has no silver, so the mod adds gold's whole ore processing for it:
  Silver Dust (`c:dusts/silver`), Dirty Silver Dust, Silver Clump, Shard and Crystal (`c:dirty_dusts/silver`,
  `c:clumps/silver`, `c:shards/silver`, `c:crystals/silver`, which have no parent tags in Mekanism), and the dirty and
  clean silver slurries, registered in Mekanism's chemical registry only when it is loaded
  (`neoforge/compat/mekanism/SilverSlurries`). The recipes are hand-written in `data/thesilverage/recipe/mekanism` with
  gold's numbers, behind its mod-loaded condition. Without Mekanism the items are hidden, as the Create-only Silver Sheet
  is. Their textures are Mekanism's gold ones (MIT) recoloured onto the Silver Ingot's ramp. No Enriched Silver:
  Mekanism's enriched metals are circuit and alloy materials, which silver has no recipe for. Mekanism's machine recipes
  aren't in the recipe book, so `recipe give` answers "No new recipes were learned" for them.
- Two config options pick the creative tabs, read from the player's own config: `enable_creative_tab` (the mod's tab,
  hidden by giving it no items) and `enable_vanilla_creative_tabs` (`ui/VanillaTabs`: each item after its counterpart,
  in vanilla's tabs and other mods' - the knife after Farmer's Delight's Golden Knife, the sheet after Create's Golden
  Sheet in `create:base`, Mekanism's processing items after gold's in its tab and Ingredients). Redstone Blocks lists
  only the waxed bulbs, plates and rods, as vanilla does its copper bulbs. An anchor that only another mod adds (the
  Golden Spear, the Golden Nautilus Armor, Mekanism's gold dust) needs that mod's insertions to run first, which
  `neoforge.mods.toml` orders with optional `AFTER` dependencies. That ordering only reaches listeners on this mod's own
  bus, so on NeoForge the tabs are filled through `CreativeTabPlatform` rather than Architectury's
  `CreativeTabRegistry.modify`, which listens on Architectury's bus and ran before Backported Spears and Mekanism had
  filled the tab. NeoForge's `insertAfter` throws on an anchor the tab doesn't list, so `VanillaTabs` tries a list of
  anchors; Fabric's `addAfter` would append one it doesn't list at the end, so its side throws too, and inserts in a phase after the default one, where Vanilla Backport adds its spears.
- Published with `publishMods` from `fabric/build.gradle` and `neoforge/build.gradle`, with the `-PpublishDryRun` flag. The Fabric upload is tagged from `supported_minecraft_versions`, 1.21 and 1.21.1, and the NeoForge one from `neoforge_minecraft_versions`, 1.21.1 alone, since NeoForge 21.1 exists for 1.21.1 only. The Fabric manifest asks for `fabric-api >=0.102.0`, the newest for 1.21: a change that needs a newer Fabric API raises that floor and drops 1.21 from the list.
- Orevolution (NeoForge, 6.0.0 and up) compiles against two of this line's fields to give silver its tier and armour
  powers: `tool.ModTiers.SILVER` (a `Tier`) and `armor.ModArmorMaterials.SILVER_ARMOR_MATERIAL` (a
  `Holder<ArmorMaterial>`). Renaming, moving or retyping either crashes the game where both mods are installed.
- Other mods' compatibility is mostly through the `c:` silver tags, which Silent Gear, Immersive Engineering, Goety,
  Iron Furnaces, Modern Industrialization, Occultism and Werewolves all read; `TODO.md` has what was checked
  (2026-10-20) and what is left.
- Hand-authored access wideners and transformers: `common/src/main/resources/thesilverage.accesswidener` and `neoforge/src/main/resources/META-INF/accesstransformer.cfg`. A first suspect when a port fails to load.
