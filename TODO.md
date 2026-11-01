
### New items
- Silver bars
- Silver lanterns
- Silver lamp
- Moon Clock
- Medium Weighted Pressure Plate?

### Misc
- Add silver items to loot tables
- Add silver villager trades
- 

### Bugs
- Fix armor trims
    - Fix trim material for silver not having textures
    - Fix silver armor sprites not showing trim

### Mod compatibility (1.21.1, explored May 2026)
Already done: Create, Sable / Create Aeronautics.

Quick wins (T1, pure data):
- Vampirism + Werewolves Becoming a Beast — add `thesilverage:silver_ingot` (+ silver sword/tools) to `vampirism:impure_silver` (and weapon tag if present). One tag file covers both mods. Flagship silver-folklore match.
- Worldgen biome expansion — append modded biome ids to `#thesilverage:has_silver_ore` (and `has_extra_silver_ore` where appropriate) using `{id, required: false}` so silver ore generates in: Terralith, Tectonic, Regions Unexplored, Biomes O' Plenty, Oh The Biomes We've Gone. WWOO uses vanilla biome ids, already covered.
- Iron Chests — Silver Chest recipe reads `c:ingots/silver`; likely already works via our existing tag. Verify in-world, document.
- Goety — uses `c:ingots/silver` in recipes; likely already works. Verify only.
- Silent Gear — one JSON material file (`data/silentgear/silent_gear/materials/silver.json`) makes silver a modular tool material. Natural home for a "silvered" anti-undead trait.

Bigger lifts (T2):
- Thermal Series — mostly auto-detects via `c:` tags. Verify Pulverizer/Induction Smelter pick up silver; optionally add explicit Press Plate / Induction Smelter alloy recipes.
- Mekanism — needs explicit per-metal recipe chain (raw→dust→clump→shard→crystal; ~15 recipes). Big audience; pattern documented via "Mekanism Ores" addon. Largest tech compat surface.
- Immersive Engineering — Crusher recipe (raw → grit/dust), Arc Furnace, Press Plate mold.

Skip (with reason):
- Bewitchment — no 1.21.1 build (original archived Apr 2025; only fork is 1.20.1).
- Tinkers' Construct — no 1.21.1 port yet; SlimeKnights is on a 1.20 beta path.
- Iron Furnaces — fixed tier ladder, no silver slot (would need Java).
- Sophisticated Backpacks — hardcoded tier ladder, no hook.
- GregTech Modern — already ships silver as a built-in GT material.
- Modern Industrialization — per-material registry, not tag-driven; heavy lift for niche audience.
- EnderIO — still alpha on 1.21.1, recipe types churning.