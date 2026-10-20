
### Mod compatibility (1.21.1, checked 2026-10-20)
Almost every mod here is on NeoForge 1.21.1 alone, so compatibility lives on this line.

Done:
- Create, Sable / Create Aeronautics, EMI, Farmer's Delight (Silver Knife), Enhanced Celestials.
- Werewolves: Become a Beast - silver tools and armour in `werewolves:tools/silver` and `werewolves:armor/silver`, and `#werewolves:werewolf` (optional) in `thesilverage:sensitive_to_silver` (1.4.0).
- `generate_silver_ore` config option, for packs that take Oreganized's or Caverns & Chasms' silver ore instead (1.4.0).

Works already, through the `c:` silver tags or their own code; nothing to add:
- Silent Gear (its own silver material, `c:ingots/silver`), Immersive Engineering (crusher, arc furnace, press), Goety (rituals), Iron Furnaces (Silver Furnace), Modern Industrialization, Occultism.
- Orevolution 6.0.0 supports The Silver Age in its own code: keep `ModTiers.SILVER` and `ModArmorMaterials.SILVER_ARMOR_MATERIAL` as they are (CLAUDE.md).
- Biome mods: Terralith, Biomes O' Plenty, Regions Unexplored, Oh The Biomes We've Gone, Nature's Spirit, Promenade, YUNG's Cave Biomes all tag their biomes `#minecraft:is_overworld`, which `has_silver_ore` takes. Tectonic and WWOO add no biomes.
- Almost Unified: has silver in its default materials, but unifies it only where a pack sets a priority.

Open, for a decision:
- Caverns & Chasms: its silver tools, armour and bars take the same grids and `c:ingots/silver` as ours, so only one mod's of each can be crafted with both installed. A config option for our gear recipes?
- Silent Gear: its `holy` trait (extra damage to `#minecraft:undead`) suits silver. Override `silentgear:silver` (needs `ordering = "AFTER"` on an optional `silentgear` dependency), add a child material, or ask upstream.
- Integrated Mining: ask its author to add The Silver Age as a silver choice beside Oreganized and Caverns & Chasms.

Bigger lifts:
- Mekanism - no silver of its own. 2x: a Silver Dust item (`c:dusts/silver`, which IE, MI, EnderIO and Occultism make too) and about 6 recipes. 5x: dust, dirty dust, clump, shard, crystal and two slurries, which need code.
- Orevolution smithing upgrades for silver gear (a design call), Spelunkery's raw silver nuggets, EnderIO SAG mill and MI macerator recipes for silver ore.

Not possible or not needed:
- Vampirism - no silver at all; `vampirism:impure_silver` doesn't exist and its vampires have no silver weakness.
- Iron Chests - no silver chest since 1.16. Thermal - nothing past 1.20.1. Galosphere - its silver became palladium (1.5.0). Bronze - no silver.
- Bewitchment, Tinkers' Construct - no 1.21.1 build. Sophisticated Backpacks - no silver tier. GregTech Modern - ships its own silver.
