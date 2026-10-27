# 1.4.0
### Additions
- Added Silver Lantern, Silver Chain and Silver Bars (including oxidized and waxed variants)
- Added Silver Brick Wall (including oxidized and waxed variants)
- Added Silver Pressure Plate (including oxidized and waxed variants)
  - Can't be pressed by hostile mobs
  - Undead mobs take damage on it and avoid it, like other silver blocks
  - The more it tarnishes, the more mobs it takes to give a full signal (1, 3, 5 or 15)
- Added Silver Lightning Rod (including oxidized and waxed variants)
- Added Silver Torch, crafted with a Silver Nugget. It burns with a violet flame.
  - Undead mobs keep away from Silver Torches and Silver Lanterns when they have no target
  - Can be turned off with the `undead_avoid_silver_light` config option
- Silver tools now deal +1 extra damage to undead mobs (+0.5 for the Silver Hoe). Stacks with Smite. Can be turned off with the `enable_silver_smite` config option.
- Silver armor now has a 20% chance per piece to burn undead mobs that hit you, dealing 1-2 damage (80% for a full set). Silver Horse Armor counts as a full set. Works like Thorns and stacks with it. Can be turned off with the `enable_silver_thorns` config option.
- Undead mobs now take damage while standing on silver blocks, and avoid walking over them
  - The more the silver tarnishes, the slower it hurts
  - Undead mobs can't spawn on silver blocks
  - Undead mobs walk off silver blocks when they have no target
  - Can be turned off with the `enable_silver_floor_damage` config option
- Added compatibility with Enhanced Celestials (and Enhanced Celestials 2) and its add-ons. The Moon Dial will display the current lunar event's name and color.
- Added compatibility with Werewolves: Become a Beast
  - Werewolves are sensitive to silver, like undead mobs
  - Silver tools and armor count as silver for the Werewolves mod
- Added the `enable_silver_oxidation` config option. Turning it off stops silver from oxidizing over time and from being waxed with honeycomb. Existing blocks stay in the world and can still be scraped back down with an axe, and the weathered and waxed variants are hidden from the creative tab.
- Zombies, husks, zombie villagers and vindicators can now spawn with silver weapons and tools
- Silver tools and armor can now be found in more chests, including bastions and trial chambers
- Added a config option for mobs spawning with silver gear
- Added the `generate_silver_ore` config option, for modpacks that use another mod's silver ore
- Added a config option for each vanilla recipe override, so they can be turned off one by one (Lodestone, Brewing Stand, Redstone Comparator, Redstone Repeater and Glistering Melon Slice)
- Added a config screen on Fabric (opened from Mod Menu)
- Updated tags for better compatibility with vanilla and other mods
  - Added the `thesilverage:sensitive_to_silver` tag, so modpacks can make other mobs sensitive to silver
  - Added the `thesilverage:cannot_press_silver_pressure_plates` tag, so modpacks can keep other mobs from pressing Silver Pressure Plates
  - Added the `thesilverage:undead_repellents` tag, so modpacks can make other blocks keep undead mobs away
  - Added husks to the `thesilverage:can_wear_silver_armor` tag
  - Added Silver Ore to the `minecraft:snaps_goat_horn` tag
  - Added Silver Doors to the `minecraft:mob_interactable_doors` tag
  - Added Silver Pickaxe to the `minecraft:cluster_max_harvestables` tag
  - Added Silver Chain to the `c:chains` tag
  - Moved Silver Ore from the `c:ore_rates/singular` tag to the `c:ore_rates/dense` tag
  - Added the `c:is_overworld` biome tag to the `thesilverage:has_silver_ore` tag

### Changes
- Silver Axe attack damage increased from 8 to 8.5
- Recipes that use Silver Ingots or Silver Nuggets now also accept silver from other mods, such as Occultism's (the c:ingots/silver and c:nuggets/silver tags). Silver tools can be repaired with it too.
- Silver Doors and Silver Trapdoors can now be opened by wind charges
- Silver Trapdoors are now crafted from 4 Silver Ingots, like iron trapdoors
- Silver Bricks are now crafted from 4 Cut Silver (including oxidized and waxed variants)
- The Fabric version now also supports Minecraft 1.21

### Fixes
- Fixed the Cleric being able to offer the same Silver Ingot trade twice on Fabric
- Crafting a Silver Bulb (and its variants) now yields 4 instead of 1
- Crafting Chiseled Silver from Cut Silver Slabs (and its variants) now yields 1 instead of 2
- Added names for the mod's tags, shown by recipe viewers like JEI and EMI, in every language.
- Added the missing Silver Knife name in every language besides English.
- Fixed jungle temples giving a Silver Ingot instead of Silver Horse Armor
- Fixed Glistering Melon Slices accepting Silver Nuggets with `override_vanilla_recipes` turned off
- Fixed the Brewing Stand needing Stone instead of Cobblestone with `override_vanilla_recipes` turned off
- Fixed the Lodestone and Glistering Melon Slice recipe overrides not applying on NeoForge with fewer than about 25 mods installed
- Fixed the Glistering Melon Slice and Lodestone recipes not accepting gold nuggets and netherite from other mods on NeoForge
- Fixed the Silver Wall Torch dropping a normal torch on Fabric
- Fixed mobs losing their armor trims and names when their gear turns into silver gear
- Fixed mobs getting another chance at silver gear each time they go through a portal
- Fixed silver trims on worn silver armor using a lighter color than in the inventory
- Fixed trims from other mods showing as silver on armor in the inventory
- Fixed the Fabric config not reloading with /reload
- Fixed the mod icon missing in Mod Menu on Fabric
- Fixed the Japanese names of Cut Silver and Silver Grates to match vanilla's
- Fixed Silver Bricks, Silver Brick Slabs, Silver Brick Stairs and Silver Pillars missing from Create's Deployer waxing and scraping recipes


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