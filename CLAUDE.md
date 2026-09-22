@../MinecraftDeveloperPortal/.claude/profiles/architectury.md

# The Silver Age

Vanilla-friendly mod adding oxidizable silver content. Mod ID `thesilverage`, package
`com.phantomwing.thesilverage`.

**This branch is the 26.3 line.** Architectury multi-loader (`common` + `fabric` + `neoforge`),
Java 25, no Parchment. Every other line of this mod has its own CLAUDE.md describing that line —
the 1.21.x lines are also Architectury, while `neoforge/1.21` and `forge/1.20` are single-loader and
predate the split. Read the registry rather than assuming.

A legacy `src/` still exists at the repo root from the pre-Architectury layout. The root build
explicitly empties its source sets so it never compiles. Do not add code there.

## Layout

```
common/src/main/java/com/phantomwing/thesilverage/
  armor/ block/{custom,entity}/ client/ compat/ firework/ food/
  item/{custom}/ loot/ network/ platform/ sound/ tags/ tool/ ui/
  utils/ villager/ world/
fabric/   ... /fabric/ + /platform/      Fabric entry point and platform implementations
neoforge/ ... /neoforge/ + /platform/    NeoForge entry point and platform implementations
```

`platform/` is the Architectury expect/actual boundary: the interface lives in `common`, each loader
supplies its own implementation. Anything loader-specific goes there rather than behind a runtime
check.

## Domain conventions

- **Weathering/oxidation** — silver blocks follow the copper pattern: `UNAFFECTED → EXPOSED →
  WEATHERED → OXIDIZED`, each with a normal and a waxed variant. Registered through generic helpers
  in `ModBlocks`; the state mappings live in `ModOxidizables` and `ModWaxables`. Adding a silver
  block usually means touching all three.
- **Creative tab** — `ModItems.CREATIVE_TAB_ITEMS` is a `LinkedHashSet`, so insertion order *is* tab
  order. Items are added by the registration helpers, not by hand.
- **Tags** — `ModTags` for the mod namespace, `CommonTags` for `c:`. Silver interoperates with other
  mods almost entirely through `c:ingots/silver` and friends; keep new materials tagged there.
- **Config** — `Configuration.java` (`ModConfigSpec`) drives `SILVERFISH_DROP_SILVER`,
  `OVERRIDE_VANILLA_RECIPES`, `GENERATE_STRUCTURE_LOOT`, `ENABLE_VILLAGER_TRADES`,
  `ENABLE_WANDERING_TRADER_TRADES`. Datagen wraps the affected recipes and loot in
  `ConfigBooleanCondition` so the condition lands in the JSON rather than in a runtime branch.

## Runtime-only dependencies

JEI, AppleSkin and Jade are `localRuntime` for dev testing — not compiled against. They are free to
drop on a rung where they have no build; that does not block the line.

## Related

`TheSilverAge-Wiki` in the workspace root is this mod's GitHub wiki.
