# 1.4.0
### Additions
- Added Fabric support (the mod now runs on both Forge and Fabric)
- Exposed, weathered and oxidized Silver Bulbs can now be crafted from their silver blocks, like copper bulbs (including waxed variants)
- Silver Ore and Deepslate Silver Ore can now be smelted and blasted into Silver Ingots
- Added Silver Bars (including oxidized and waxed variants)
- Added Silver Chain (including oxidized and waxed variants)
- Added Silver Brick Wall (including oxidized and waxed variants)
- Added Silver Lightning Rod (including oxidized and waxed variants)
- Added Silver Pressure Plate (including oxidized and waxed variants)
  - Can't be pressed by hostile mobs
  - The more it tarnishes, the more mobs it takes to give a full signal (1, 3, 5 or 15)
- Added Silver Torch and Silver Lantern (including oxidized and waxed variants), which burn with a violet flame
  - The Silver Torch is crafted with a Silver Nugget
- Added Silver Arrows, crafted from 8 arrows around a Silver Ingot
  - They fly faster than normal arrows, so they hit harder
  - Sold by master Fletchers, and found in pillager outposts
  - Can be turned off with `enable_silver_arrows`, which stops them from being crafted, traded or found
- Silver now harms undead mobs, which can all be turned off at once with `silver_affects_undead`
  - Silver tools deal +1 extra damage to undead mobs (+0.5 for the Silver Hoe), stacking with Smite, and Silver Arrows +1 (can be turned off with `enable_silver_smite`, and set with `silver_smite_damage` and `silver_arrow_smite_damage`)
  - Each piece of silver armor has a 20% chance to hurt an undead mob that hits you for 1-2 damage, like Thorns and stacking with it, and Silver Horse Armor counts as a full set (can be turned off with `enable_silver_thorns`, and set with `silver_thorns_chance`)
  - Undead mobs take damage on silver blocks and Silver Pressure Plates, the slower the more the silver tarnishes, avoid walking over them, walk off them when they have no target, and can't spawn on them except from monster spawners (can be turned off with `enable_silver_floor_damage`)
  - Undead mobs keep away from Silver Torches and Silver Lanterns when they have no target (can be turned off with `undead_avoid_silver_light`)
- Zombies, husks, zombie villagers and vindicators can now spawn with silver weapons and tools (mobs spawning with silver gear can be turned off with `enable_mob_silver_equipment`)
- Silver tools and armor can now be found in more chests, including bastions
- Added 2 advancements
  - Sterling Shot: kill an undead mob with a Silver Arrow
  - Look, Don't Touch: hurt an undead mob that hits you while wearing Silver Armor
- Silver items now also show up in the vanilla creative tabs, beside their vanilla counterparts
  - The Silver Knife shows up in Farmer's Delight's tab, and the Silver Sheet in Create's
  - `enable_creative_tab` and `enable_vanilla_creative_tabs` choose which tabs silver items show up in
- Added config options for modpacks
  - `enable_silver_oxidation`: turned off, silver doesn't oxidize and can't be waxed, and its weathered and waxed variants are hidden from the creative tabs; blocks already placed can still be scraped with an axe
  - `enable_silver_gear`: turned off, silver tools, weapons and armor (horse and nautilus armor too) can't be crafted, found or worn by mobs, and are hidden from the creative tabs, for modpacks where another mod's silver gear takes their place, like Caverns & Chasms
  - `generate_silver_ore`, for modpacks that use another mod's silver ore
  - One for each vanilla recipe override (Lodestone, Brewing Stand, Redstone Comparator, Redstone Repeater and Glistering Melon Slice)
- Added a config screen on Fabric (opened from Mod Menu)
- Added compatibility with Create
  - Crushed Raw Silver can be washed into Silver Nuggets
  - The Spout oxidizing silver blocks with water can be turned off with `enable_create_spout_oxidation`
- Added compatibility with Create: Aquatic Ambitions: channeling oxidizes silver blocks, like copper
- Added compatibility with Mekanism: Silver Dust, Dirty Silver Dust, Silver Clumps, Silver Shards, Silver Crystals and silver slurries for its ore processing, only shown when Mekanism is installed
- Added compatibility with Vanilla Backport
  - The Silver Spear, crafted like other spears
  - Silver Nautilus Armor, found in ocean chests in place of Copper Nautilus Armor
  - Sulfur Cubes can swallow silver blocks, ores and bulbs, like copper's
  - Parched can spawn with silver armor
  - Silver Bars, Chains, Lanterns and Lightning Rods are in the tags it uses for copper's
- Added compatibility with Better Combat: the Silver Spear swings like a spear
- Added compatibility with Yori3o's Grappling Hooks: the Silver Grappling Hook, with a range of 14 blocks
- Added compatibility with Enhanced Celestials (and Enhanced Celestials 2) and its add-ons: the Moon Dial shows the current lunar event's name and color
- Added compatibility with Werewolves: Become a Beast: werewolves are sensitive to silver, like undead mobs, and silver tools and armor count as silver for it
- Added compatibility with JEI and EMI: Silver Pressure Plates, Silver Torches and Lanterns and Silver Arrows have an info page
- Added Spanish (Argentina), Spanish (Chile), Kazakh, Vietnamese and Traditional Chinese translations
- Updated tags for better compatibility with vanilla and other mods
  - Added `thesilverage:sensitive_to_silver`, so modpacks can make other mobs sensitive to silver
  - Added `thesilverage:cannot_press_silver_pressure_plates`, so modpacks can keep other mobs from pressing Silver Pressure Plates
  - Added `thesilverage:undead_repellents`, so modpacks can make other blocks keep undead mobs away
  - Added husks to `thesilverage:can_wear_silver_armor`
  - Added Silver Ore to `minecraft:snaps_goat_horn` and the Silver Pickaxe to `minecraft:cluster_max_harvestables`
  - Added Silver Chains, Lanterns and Brick Walls to Sable's tags

### Changes
- Now requires Architectury API, on Forge too
- Silver Axe attack damage lowered from 9.5 to 8.5 and attack speed raised from 0.9 to 1, like on the other Minecraft versions
- Silver Trapdoors are now crafted from 4 Silver Ingots, like iron trapdoors
- Silver Bricks are now crafted from 4 Cut Silver, and Silver Bricks and their stairs, slabs and walls can be cut from Cut Silver in a stonecutter (including oxidized and waxed variants)
- Recipes that take Silver Ingots or Silver Nuggets now accept other mods' silver too, such as Occultism's (the `forge:ingots/silver` and `forge:nuggets/silver` tags, `c:` on Fabric), and silver tools can be repaired with it
- Silver Bulbs and Silver Armor now have their own subtitles
- Silver Bulbs are now crafted with the Blaze Rod in the middle and Redstone at the bottom, like copper bulbs
- Moved Silver Ore from the `forge:ore_rates/singular` tag to `forge:ore_rates/dense`

### Fixes
- Fixed Chiseled Silver crafting 2 from Cut Silver Slabs instead of 1 (including oxidized and waxed variants)
- Fixed Glistering Melon Slices accepting Silver Nuggets, and the Brewing Stand needing Stone instead of Cobblestone, with `override_vanilla_recipes` turned off
- Fixed the textures of overridden vanilla recipes not updating after /reload until rejoining the world
- Fixed jungle temples giving a Silver Ingot instead of Silver Horse Armor
- Fixed chests sometimes keeping an iron or gold item where the item before it was swapped for a silver one
- Fixed mobs losing their armor trims and names when their gear turns into silver gear
- Fixed mobs getting another chance at silver gear each time they go through a portal
- Fixed silver trims on worn silver armor using a lighter color than in the inventory
- Fixed trims from other mods showing as silver on armor in the inventory
- Fixed Create's Deployer not waxing or scraping, and its Spout not oxidizing, Silver Bricks, Silver Brick Slabs, Silver Brick Stairs and Silver Pillars
- Fixed the mod's tags having no names in recipe viewers like JEI and EMI
- Fixed the Silver Knife having no name in every language besides English
- Fixed the Japanese names of Cut Silver and Silver Grates to match vanilla's


# 1.3.0
### Additions
- Added new Silver Pillar, Silver Bricks, Silver Brick Stairs and Silver Brick Slab decorative blocks (including oxidized and waxed variants)
- Added compatibility with Farmer's Delight (Silver Knife, available when Farmer's Delight is installed)
- Added tag integration with Sable (Create Aeronautics)
- Added Chinese, French, Italian, Japanese, Korean, Romanian, Russian, Spanish and Ukrainian language support (auto-translated, might need adjustments)

### Changes
- Silver Ore and Deepslate Silver Ore now drop between 1-3 Raw Silver (instead of just 1). This makes building with silver a lot easier.
- Added tooltip to Moon Dial showing the current moon phase.
- Improved some textures

### Fixes
- Setting `override_vanilla_recipes` to `false` now also keeps the original vanilla textures, instead of my overrides.


# 1.2.0
### Additions
- Added compatibility with Create
  - Deployer (deploying) recipes for waxing/scraping silver blocks
  - Spout (filling) recipes for oxidizing silver blocks (Similar to Create: Oxidized)
  - Support for Crushed Raw Silver (obtain by crushing silver with Create's Crushing Wheel)
  - Added Silver Sheet (obtain by pressing silver ingots with Create's Mechanical Press)
    - Can be used in recipes by Create add-on mods through tags (`c:plates/silver`).
- Added Dutch and German language support

### Fixes
- Added missing crafting recipes for Silver Bulb and its variants.
- Added missing smelting recipes for Silver Ore and Deepslate Silver Ore blocks
- Fix incorrect tag `ores_silver`. Changed to `ores/silver` to ensure compatibility with other mods.
- Fix incorrect recipe categories for some items


# 1.1.0
### Additions
- Added Silver Bulb (and all its oxidized/waxed variants)

### Changes
- Renamed all "Oxidized" silver blocks to "Tarnished"

### Fixes
- Fixed Bolt and Flow armor trims not working with silver as a material


# 1.0.1
### Fixes
- Fixed leather armor items not displaying correctly in the inventory.

# 1.0.0

Initial release of the project.