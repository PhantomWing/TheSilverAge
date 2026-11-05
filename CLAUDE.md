@~/Documents/Projects/Minecraft/MinecraftDeveloperPortal/.claude/profiles/architectury.md

# The Silver Age - `version/1.20`

**This file describes the `version/1.20` line**: Minecraft 1.20.1, Fabric and Forge (the Forge jar also runs on NeoForge 1.20.1).
Built with Architectury Loom (`dev.architectury.loom`) with Mojang mappings layered with Parchment; `remapJar` is the shipped jar.
Branched from `version/1.21` on 2026-11-04, at 1.4.0, to replace the single-loader `forge/1.20`.

It belongs to whichever folder has this branch checked out - the main `TheSilverAge` folder or a worktree
under `TheSilverAge/.worktrees/`. A session started in a worktree also loads the main folder's CLAUDE.md,
which describes another line; for this folder, this file is the one that applies. Confirm with
`git branch --show-current`. `MinecraftDeveloperPortal/data/mods.json` lists every line of the mod.

Vanilla-friendly mod adding oxidizable silver content. Mod ID `thesilverage`, package
`com.phantomwing.thesilverage`. `TheSilverAge-Wiki` in the workspace root is its GitHub wiki.

Every `version/*` line is Architectury. `neoforge/1.21` and `forge/1.20` are single-loader
leftovers from before the split, not lines; this line takes `forge/1.20`'s place. A root `src/` folder may linger on disk from that
layout; nothing in it is tracked or built.

## Layout

```
common/src/main/java/com/phantomwing/thesilverage/   loader-agnostic code
fabric/   ... /fabric/ + /platform/                  Fabric entry point and platform implementations
forge/    ... /forge/ + /platform/                   Forge entry point and platform implementations
```

`platform/` is the Architectury expect/actual boundary: the interface lives in `common`, each loader
supplies its own implementation. Anything loader-specific goes there rather than behind a runtime
check.

## Domain conventions

- **Weathering/oxidation** - silver blocks follow the copper pattern: `UNAFFECTED → EXPOSED →
  WEATHERED → OXIDIZED`, each with a normal and a waxed variant. Registered through generic helpers
  in `ModBlocks`; the state mappings are handed to each loader by `WeatheringPlatform` (see This line). Adding a
  silver block usually means touching both.
- **Creative tab** - `ModItems.CREATIVE_TAB_ITEMS` is a `LinkedHashSet`, so insertion order *is* tab
  order. Items are added by the registration helpers, not by hand.
- **Tags** - `ModTags` for the mod namespace, `CommonTags` for `forge:` on this line (`c:` from 1.21). Silver interoperates with other
  mods almost entirely through `forge:ingots/silver` (`c:silver_ingots` on Fabric) and friends; keep new materials tagged there.
- **Config** - options that change recipes or loot are emitted by datagen wrapped in a condition, so
  the condition lands in the JSON rather than in a runtime branch.
- **Undead** - every silver effect on mobs (the tools' bonus, the armour hurting undead attackers, silver floors, the
  goals, the pressure plate) asks `combat/SilverSensitivity`: a mob whose `getMobType()` is `MobType.UNDEAD`, as Smite
  asks on 1.20.1, so another mod's undead count too, or one in `thesilverage:sensitive_to_silver`, which lists vanilla's
  undead (1.20.1 has no `#minecraft:sensitive_to_smite`) and, optionally, Werewolves: Become a Beast's
  `#werewolves:werewolf`, and lets a pack add a mob Smite leaves alone. A check with a type and no entity (a spawn's)
  takes the tag and the types an entity has shown to be undead, which every mob joining a level teaches it. Never
  check the tag directly; the advancement's entity predicate is the one place that reads it alone. The `silver_affects_undead` option turns every one of them off: each loader's `CommonConfigImpl`
  folds it into the getter of each effect's own option, so a new effect's getter needs it too. The tooltips read the
  server's settings, sent on join and whenever the config changes (`SilverUndeadSettings`, `ModNetworking.configChanged`). A
  player's swing takes the tools' bonus in `Player#attack`'s `EnchantmentHelper.getDamageBonus` (twin `PlayerMixin`s), where
  Smite's goes, so the swing's charge scales it; every other hit, a Silver Arrow's among them, takes it at the head of
  `LivingEntity#hurt` (twin `LivingEntityMixin`s' `@ModifyVariable`), before the damage cooldown and armour. Forge's
  `LivingHurtEvent` is not used: it comes inside `actuallyHurt`, after the cooldown, and counted other hits than Fabric. Silver armour's hurt (`combat/SilverThornsHandler`,
  `silver_thorns_chance` a piece, 20% by default, Thorns damage) is called from `LivingEntity#hurt`'s return by twin
  `LivingEntityMixin`s, so it fires only on a hit that landed, and puts back the attacker's damage cooldown after, or
  vanilla's Thorns, which hurts later in the same strike, lost the burn's damage. Silver floors (`combat/SilverFloor`, the blocks in
  `thesilverage:silver_floors`) are hooked by twin mixins in each loader: `Block#stepOn`, `isValidSpawn`, and the
  walk evaluator and `RandomPos` for avoidance. Path types can't carry the avoidance, since a level caches them once
  for every mob. `SilverUndeadGoals` gives every such mob two goals as it joins a level (Forge's `EntityJoinLevelEvent`,
  Fabric's `PersistentEntitySectionManagerMixin`, which Architectury's `EntityEvent.ADD` misses for chunk loads), both of
  which give way to a target: `LeaveSilverFloorGoal` walks it off silver, and `AvoidSilverLightGoal` keeps it 6 blocks
  from the blocks in `thesilverage:undead_repellents` (the Silver Torch and Lantern), The Lead Age's
  `AvoidRepellentBlockGoal` with silver's rule. 1.20.1 has no `minecraft:no_knockback`; a hurt with no entity behind it
  knocks nothing back here, as magma's doesn't, and `SilverFloor` hops a mob itself, at most every 3 seconds. The Silver Pressure Plate is a silver floor a mob stands in rather than on: it hurts through its own
  `entityInside`, and `SilverFloor` looks at the block a mob stands in for a plate. It is pressed by any living entity
  but a hostile one (`Enemy`) or one in `thesilverage:cannot_press_silver_pressure_plates`, which holds
  `sensitive_to_silver`, besides every mob `SilverSensitivity` counts.
- **Tool tiers** - silver's blocks need an iron pickaxe, as gold's do; the bars, chain, lantern and pressure plate drop
  to any pickaxe, as vanilla's iron ones and weighted plates do. Where a block has a vanilla counterpart, follow it.
- **`enable_silver_oxidation`, `enable_silver_arrows` and `enable_silver_gear`** are sent to clients
  (`ContentSyncPayload`, with `UndeadSettingsSyncPayload` and `RecipeOverrideSyncPayload`, on join, after a reload and
  when the config changes; to a Forge client unguarded - see This line), so the creative tabs, the recipe viewers and honeycomb follow the server's; a dispenser's
  honeycomb is refused too (`SilverOxidation`).
- **`enable_silver_gear`** takes away the gear in `ModItems.isSilverGear` - the tools, sword, knife, spear, armour, horse
  and nautilus armour - as `enable_silver_arrows` takes the arrows: their recipes (and the Silver Grappling Hook's) and
  the armour advancement through their conditions, their loot (`SilverLootSpec.itemEnabled`), the creative tabs, and mobs
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

- Java 17 (`options.release = 17`; the daemon may run on JDK 21), Mojang mappings with Parchment 2023.09.03, Architectury Loom 1.7 on the Forge platform (`forge/gradle.properties`: `loom.platform=forge`), Forge 47.4.20, Fabric Loader 0.16.10, Fabric API 0.92.2, Architectury 9.2.14. `org.gradle.java.home` is pinned in `gradle.properties` to a JDK on this machine; if the daemon fails to start, check that path first.
- Forge's mixins are `thesilverage.forge.mixins.json`, with its own refmap. Forge 47 doesn't bundle MixinExtras, so `forge/build.gradle` jar-in-jars `mixinextras-forge`. The common access widener is converted into Forge's access transformer (`convertAccessWideners`); there is no hand-written `accesstransformer.cfg`.
- Datagen: `:forge:runData` (`DataGenerators` subscribes on the MOD bus; Forge 47's default is the FORGE bus, where it never fires). Output: `common/src/generated/resources`, never hand-edited, in 1.20.1's folder names (`recipes`, `advancements`, `loot_tables`, `tags/items`, `tags/blocks`). Conditional recipes and advancements carry Forge's top-level `conditions`, built through `datagen/RecipeOutput`, and `FabricConditionsProvider` adds the `fabric:load_conditions` twin to every one. A global loot modifier's `conditions` are loot conditions on 1.20.1, so a modifier naming another mod's item stores its id and resolves it at roll time instead of being gated.
- The portal has no Forge tasks for an Architectury line yet: it offers datagen and game tests only for `neoforge` and `fabric` subprojects. Run Forge's datagen and game tests as Gradle tasks (`:forge:runData`, `:forge:runGameTest`); its client load check (`checkClient:forge`) works.
- Game tests in `common/src/gametest`, run on both loaders: `:forge:runGameTest` and `:fabric:runGameTest`, 65 each (2026-11-04). Forge's run is `server()` with the `gameTestServer` template and the `forge.gameTestServer` property, which Forge's patched `Main` reads (Loom launches a `server()` run with the plain server's arguments, so without it the run waits on its EULA). Fabric's test source set is a mod of its own, `thesilverage_gametest`, with the `fabric-gametest` entrypoint. Laid out with The Lead Age's test shim: `TestCompat` holds 1.20.1's calls - horse armour in the chest slot, `forge:` as the material namespace and `minecraft:chains`, an arrow without its pickup stack, `NaturalSpawner.isSpawnPositionOk`, `CraftingInput` as a shim over a `TransientCraftingContainer`, NBT names and trims, loot tables through `getLootData`, 1.21's `recipe/` paths read from `recipes/` with Forge's `conditions` shown as `neoforge:conditions`, and 1.21's bolt and flow trim patterns mapped to coast. The bodies still differ from `version/1.21`'s where 1.21's name an API 1.20.1 hasn't (`EquipmentSlot.BODY`, `DataComponents`, `CraftingInput`, the `BlockSource` record, `CopperBulbBlock`, `makeMockPlayer(GameType)`, `SpawnPlacements.isSpawnPositionOk`, `EntityTypeTags.SENSITIVE_TO_SMITE`, `assertItemEntityPresent(Item)`, `BlockTags.MOB_INTERACTABLE_DOORS`, the 1.20.5 arrow constructor): making them one needs those calls moved into `TestCompat` on every line. Forge's `TestLoader` makes a `FakePlayer` (the mock server player's connection has no channel on Forge 47) and sets a config option's cached value rather than calling `set`, which writes the file Forge's watcher then holds. Left out, with no 1.20.1 counterpart: the wind charge tests, trial chamber loot and `silver_trim_darkens`.
- Tags: `CommonTags` is the `forge:` namespace here (`forge:ingots/silver`, `forge:tools/knives`), and `FabricTags` the Fabric conventional ones of 1.20.1 (`c:silver_ingots`, `c:silver_nuggets`, `c:tools/knives`). Datagen gives each `forge:` tag its Fabric twin as an optional tag, and the `c:` tag lists the mod's own items, so one tree serves both loaders. Mekanism's tags are in `mekanism:`. Every tag the mod generates in its own namespace, `forge:`, `mekanism:` and Fabric's `c:` has a name in each lang file (`tag.item.forge.ingots.silver`, `tag.item.c.silver_ingots`), except the generic Forge parents (`forge:ores`, `forge:ingots`, `ores_in_ground/*`, `ore_rates/*`). `thesilverage:sensitive_to_silver` lists vanilla's undead itself, since 1.20.1 has no `minecraft:sensitive_to_smite`; the code adds every `MobType.UNDEAD` mob (`SilverSensitivity`).
- What goes in vanilla's static maps - the dispenser behaviours (`TheSilverAgeCommon.setup()`: the Silver Arrow's, and honeycomb's refusal) and the advancement trigger (`ModCriteriaTriggers.register()`, into `CriteriaTriggers`' plain `HashMap`) - is registered on Forge from common setup's `enqueueWork`, since Architectury 9 fires `LifecycleEvent.SETUP` straight from the parallel `FMLCommonSetupEvent` and Forge constructs mods in parallel; on Fabric from its entrypoint and `LifecycleEvent.SETUP`. `BlockSetType.register` stays at `ModBlockSetTypes`' class load, which only the block registry event's suppliers reach, on the main thread.
- `Properties.copy` on 1.20.1 copies neither `isValidSpawn`, `isRedstoneConductor`, `isSuffocating`, `isViewBlocking` nor `hasPostProcess`: of the vanilla blocks `ModBlocks` copies, only the iron trapdoor sets one (`isValidSpawn` never), so the silver trapdoors set it themselves.
- 1.20.1's API, as this line uses it: blocks have no codecs and their methods are public; `onRandomTick` in place of `changeOverTime`; no vanilla copper door, trapdoor, grate or bulb, so `SilverDoorBlock`, `SilverGrateBlock` and `SilverBulbBlock` are the mod's own; `ModArmorMaterials` is an enum implementing `ArmorMaterial`, named `thesilverage:silver` (Fabric resolves the armour texture's namespace through `HumanoidArmorLayerMixin`); horse armour is `SilverHorseArmorItem`; tiers take `getLevel()`; payloads are `FriendlyByteBuf` writers through Architectury 9's `NetworkManager`, whose Forge `canPlayerReceive` answers from the ids the client sends back for `architectury:sync_ids` - its own C2S receivers, not the S2C ones the server sends, and only after the login event the join sync is sent from - so it never holds for this mod's packets, and `ModNetworking.canReceive` takes every Forge client as able (Forge refuses one without the mod at login) and asks Architectury on Fabric alone; `SilverArmorHurtTrigger` is registered with `CriteriaTriggers.register`. A player's swing takes the tools' bonus in `Player#attack`'s `EnchantmentHelper.getDamageBonus` (twin `PlayerMixin`s), where Smite's goes.
- Weathering: Forge 47 has no data maps, so `WeatheringPlatformImpl.apply()` swaps `WeatheringCopper.NEXT_BY_BLOCK` and `HoneycombItem.WAXABLES` through `Unsafe` at common setup for Guava memoizers that merge silver's pairs into the suppliers that were there when first read, and reads nothing itself: Vanilla Backport's Platform adds its copper at load complete, after common setup, by swapping the delegate of `NEXT_BY_BLOCK`'s memoizer and wrapping `WAXABLES`, so a map merged at setup lost its blocks' scraping and unwaxing. Vanilla's `PREVIOUS_BY_BLOCK` and `WAX_OFF_BY_BLOCK` are left as they are, since they invert whatever those two hold when first read. Fabric uses `OxidizableBlocksRegistry`. Lightning rods: Forge patches `PoiTypes`, so its `PoiTypesMixin` answers lightning's point of interest for the silver rods, and `LightningBoltMixin` powers and scrapes them on both loaders.
- Compat on this line: JEI (both, runtime; its common API compiled against), EMI (runtime only on Forge; its own vanilla plugin covers the info pages), Farmer's Delight (Forge) and Farmer's Delight Refabricated (Fabric) for the Silver Knife, Create 6.0.8 and Create: Aquatic Ambitions (Forge), Mekanism 10.4 (Forge, `Slurry`), Vanilla Backport (Forge) for the Silver Spear and Silver Nautilus Armor (the nautilus armour is a plain `Item` there, so the armour's hurt counts anything in `c:armors/nautilus` as a full set, as it does horse armour; the spear's held model is registered by the client-only `client/compat/SilverSpearModels` on each loader, on Forge also through `ModelEvent.RegisterAdditional`, since Forge's model bakery can be made before client setup), Mekanism's slurries named `slurry.thesilverage.*` (Mekanism 10.4's key, `chemical.` from 1.21), Yori3o's Grappling Hooks (data only, both; 5.1.1's held-hook predicate is `yo_hooks:grappling_hook_handle`, 1.21's `hook_active`; its four hand-written recipes in `common/src/main/resources` have hand-written unlocks beside them in `advancements/recipes`, with the same conditions; Forge's dev runs need YACL's bundled libraries as `forgeRuntimeLibrary`), Enhanced Celestials 1 and 2 (compiled against only). Not here: Backported Spears (no 1.20.1 build), Jade (its Forge mixin crashes the dev client), Vanilla Backport on Fabric's dev runs (its nested MixinSquared isn't unpacked) and in Forge's game runs (its sulfur caves mixin names an SRG field, `f_189553_`, so every world a dev run generates crashes with `NoSuchFieldError`; Forge's datagen still loads it), a Forge config screen.
- Enhanced Celestials can't be loaded in the Forge dev runs: with it on the classpath Loom fails to set up Minecraft at its access-widener step, so `enhanced_celestials=none` and the Moon Dial's lunar events are untested in dev.
- Content 1.20.1 has no room for: wind-charge door opening, trial chamber loot, the darker silver trim on silver armour, `minecraft:mob_interactable_doors`, `c:chains`, the enchantable armour tags and `minecraft:no_knockback`, and the bolt and flow trims in the armour trim atlas.
- The manifests ask for 1.20.1 alone and Architectury 9: `fabric.mod.json` `~1.20.1` and `>=9.2.14 <10`, `mods.toml` `minecraft_version_range` (`[1.20.1,1.20.2)`, expanded from `gradle.properties`) and `[9.2.14,10)`. A swapped vanilla stack (`ItemUtils.tryTransmuteStack`) keeps what 1.21's keeps: its name and lore of `display`, `Trim`, `Unbreakable` and every key vanilla doesn't use.
- Published with `publishMods` from `fabric/build.gradle` and `forge/build.gradle`, with the `-PpublishDryRun` flag. Uploads are tagged from `supported_minecraft_versions`, 1.20.1; the Forge upload names both `forge` and `neoforge` as its loaders, since NeoForge 1.20.1 loads Forge mods.
- Hand-authored access widener: `common/src/main/resources/thesilverage.accesswidener`. A first suspect when a port fails to load.
