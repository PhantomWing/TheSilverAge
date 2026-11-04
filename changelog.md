# 1.4.0
### Additions
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
  - Sold by master Fletchers, and found in pillager outposts and trial chambers
  - Can be turned off with `enable_silver_arrows`, which stops them from being crafted, traded or found
- Silver now harms undead mobs, which can all be turned off at once with `silver_affects_undead`
  - Silver tools deal +1 extra damage to undead mobs (+0.5 for the Silver Hoe), stacking with Smite, and Silver Arrows +1 (can be turned off with `enable_silver_smite`, and set with `silver_smite_damage` and `silver_arrow_smite_damage`)
  - Each piece of silver armor has a 20% chance to hurt an undead mob that hits you for 1-2 damage, like Thorns and stacking with it, and Silver Horse Armor counts as a full set (can be turned off with `enable_silver_thorns`, and set with `silver_thorns_chance`)
  - Undead mobs take damage on silver blocks and Silver Pressure Plates, the slower the more the silver tarnishes, avoid walking over them, walk off them when they have no target, and can't spawn on them except from monster spawners (can be turned off with `enable_silver_floor_damage`)
  - Undead mobs keep away from Silver Torches and Silver Lanterns when they have no target (can be turned off with `undead_avoid_silver_light`)
- Zombies, husks, zombie villagers and vindicators can now spawn with silver weapons and tools (mobs spawning with silver gear can be turned off with `enable_mob_silver_equipment`)
- Silver tools and armor can now be found in more chests, including bastions and trial chambers
- Added 2 advancements
  - Sterling Shot: kill an undead mob with a Silver Arrow
  - Look, Don't Touch: hurt an undead mob that hits you while wearing Silver Armor
- Silver items now also show up in the vanilla creative tabs, beside their vanilla counterparts
  - The Silver Knife shows up in Farmer's Delight's tab, and the Silver Sheet in Create's
  - `enable_creative_tab` and `enable_vanilla_creative_tabs` choose which tabs silver items show up in
- Added config options for modpacks
  - `enable_silver_oxidation`: turned off, silver doesn't oxidize and can't be waxed, and its weathered and waxed variants are hidden from the creative tabs; blocks already placed can still be scraped with an axe
  - `enable_silver_gear`: turned off, silver tools, weapons and armor (horse armor too) can't be crafted, found or worn by mobs, and are hidden from the creative tabs, for modpacks where another mod's silver gear takes their place, like Caverns & Chasms
  - `generate_silver_ore`, for modpacks that use another mod's silver ore
  - One for each vanilla recipe override (Lodestone, Brewing Stand, Redstone Comparator, Redstone Repeater and Glistering Melon Slice)
- Added a config screen on Fabric (opened from Mod Menu)
- Added compatibility with Create Fly on Fabric
  - Spout oxidizing, Deployer waxing and scraping, pressing Silver Sheets and crushing and washing Raw Silver work as with Create
  - The Spout oxidizing silver blocks with water can be turned off with `enable_create_spout_oxidation`
- Added compatibility with Backported Spears: the Silver Spear, crafted like other spears
- Added compatibility with Better Combat: the Silver Spear swings like a spear
- Added compatibility with JEI: Silver Pressure Plates, Silver Torches and Lanterns and Silver Arrows have an info page
- Added Spanish (Argentina), Spanish (Chile), Kazakh, Vietnamese and Traditional Chinese translations
- Updated tags for better compatibility with vanilla and other mods
  - Added `thesilverage:sensitive_to_silver`, so modpacks can make other mobs sensitive to silver
  - Added `thesilverage:cannot_press_silver_pressure_plates`, so modpacks can keep other mobs from pressing Silver Pressure Plates
  - Added `thesilverage:undead_repellents`, so modpacks can make other blocks keep undead mobs away
  - Added husks to `thesilverage:can_wear_silver_armor`, and `c:is_overworld` to `thesilverage:has_silver_ore`
  - Added Silver Ore to `minecraft:snaps_goat_horn`, Silver Doors to `minecraft:mob_interactable_doors` and the Silver Pickaxe to `minecraft:cluster_max_harvestables`
  - Added Silver Chain to `c:chains`
  - Added Silver Bars, Chains, Lanterns and Lightning Rods to `minecraft:bars`, `minecraft:chains`, `minecraft:lanterns` and `minecraft:lightning_rods`
  - Added Silver Chains, Lanterns and Brick Walls to Sable's tags

### Changes
- Silver Axe attack damage increased from 8 to 8.5
- Silver Trapdoors are now crafted from 4 Silver Ingots, like iron trapdoors
- Silver Bricks are now crafted from 4 Cut Silver, and Silver Bricks and their stairs, slabs and walls can be cut from Cut Silver in a stonecutter (including oxidized and waxed variants)
- Recipes that take Silver Ingots or Silver Nuggets now accept other mods' silver too, such as Occultism's (the `c:ingots/silver` and `c:nuggets/silver` tags), and silver tools can be repaired with it
- Silver Doors and Silver Trapdoors can now be opened by wind charges
- Silver Bulbs and Silver Armor now have their own subtitles
- Moved Silver Ore from the `c:ore_rates/singular` tag to `c:ore_rates/dense`

### Fixes
- Fixed Silver Bulbs crafting 1 instead of 4, like copper bulbs (including oxidized and waxed variants)
- Fixed Chiseled Silver crafting 2 from Cut Silver Slabs instead of 1 (including oxidized and waxed variants)
- Fixed Glistering Melon Slices accepting Silver Nuggets, and the Brewing Stand needing Stone instead of Cobblestone, with `override_vanilla_recipes` turned off
- Fixed the Lodestone needing a Netherite Ingot instead of an Iron Ingot with `override_vanilla_recipes` turned off
- Fixed the Lodestone and Glistering Melon Slice recipe overrides on NeoForge not applying with fewer than about 25 mods installed, and not accepting other mods' gold nuggets and iron
- Fixed the textures of overridden vanilla recipes not updating after /reload until rejoining the world
- Fixed the silver Brewing Stand, Comparator and Repeater textures not showing on NeoForge
- Fixed silver Repeaters and Comparators using Minecraft's old redstone torch models
- Fixed resources reloading every time you join or leave a world on NeoForge, which could add a long loading screen in large modpacks
- Fixed the mod not loading on Fabric for Minecraft 1.21.9
- Fixed jungle temples giving a Silver Ingot instead of Silver Horse Armor
- Fixed chests sometimes keeping an iron or gold item where the item before it was swapped for a silver one
- Fixed mobs losing their armor trims and names when their gear turns into silver gear
- Fixed mobs getting another chance at silver gear each time they go through a portal
- Fixed the Silver armor trim not showing on Copper Armor in the inventory
- Fixed the Silver Wall Torch dropping a normal torch on Fabric
- Fixed the Fabric config not reloading with /reload
- Fixed the mod icon missing in Mod Menu on Fabric
- Fixed the mod's tags having no names in recipe viewers like JEI and EMI
- Fixed the Silver Knife having no name in every language besides English
- Fixed the Japanese names of Cut Silver and Silver Grates to match vanilla's
- Fixed the Moon Phase Detector subtitle saying "Comparator clicks"

# 1.3.3
### Fixes
- Fixed the Cleric being able to offer the same Silver Ingot trade twice on Fabric
- Fixed the advancements tab background not showing.
- Fixed some recipe advancements ignoring the "Override vanilla recipes" setting on Fabric.
- Fixed the Silver Knife advancement showing without Farmer's Delight installed.


# 1.3.2
### Fixes
- Fix broken compatibility with Farmer's Delight Refabricated


# 1.3.1
### Fixes
- Fixed server crashing on startup when using NeoForge.


# 1.3.0
First version released for Fabric.

### Additions
- Added new Silver Pillar, Silver Bricks, Silver Brick Stairs and Silver Brick Slab decorative blocks (including oxidized and waxed variants)
- Added compatibility with Farmer's Delight (Silver Knife, available when Farmer's Delight is installed)
- Added tag integration with Sable (Create Aeronautics)
- Added Chinese, French, Italian, Japanese, Korean, Romanian, Russian, Spanish and Ukrainian language support (auto-translated, might need adjustments)

### Changes
- Important! Now requires `Architectury API` as a dependency to support both NeoForge and Fabric platforms.
- Silver Ore and Deepslate Silver Ore now drop between 1-3 Raw Silver (instead of just 1). This makes building with silver a lot easier.
- Added tooltip to Moon Dial showing the current moon phase.
- Improved NeoForge config screen
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
- Added compatibility with EMI
  - World Interaction recipes for waxing/scraping silver blocks
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