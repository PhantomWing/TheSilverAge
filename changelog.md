# 1.3.3
### Fixes
- Fixed the Cleric being able to offer the same Silver Ingot trade twice on Fabric


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