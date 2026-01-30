# Corpse

[![](https://jitpack.io/v/unldenis/Corpse.svg)](https://jitpack.io/#unldenis/Corpse)

Dead bodies in minecraft for 1.8-1.21.11 servers.

## Installation

1. Drop the jar into `plugins`.
2. Restart the server (no `/reload`).

## Commands

| Command | Permission | Description |
|--------|------------|-------------|
| `/spawncorpse [player]` | `corpses.spawn` | Spawns your corpse at your feet, or another player’s if you give a name. |
| `/removecorpse <radius>` | `corpses.remove` | Removes all corpses within the given radius. |

## Config (`config.yml`)

- `corpse-time` – Seconds before a corpse despawns (-1 = never).
- `on-death` – Spawn a corpse when a player dies.
- `show-tags` – Show name tags above corpses.
- `render-armor` – Render armor/items on the corpse.
- `corpse-distance` – Max blocks at which corpses are visible.
- `lootable-corpses` – Right‑click to open inventory and loot.

## API

Add the dependency (e.g. JitPack) and use `CorpseAPI.getInstance()`:

```java
CorpseAPI api = CorpseAPI.getInstance();

// At player location, with their skin and inventory
Corpse corpse = api.spawnCorpse(player);

// At a specific location
Corpse corpse = api.spawnCorpse(player, location);
Corpse corpse = api.spawnCorpse(offlinePlayer, location);

// Custom armor (player/offline + location + helmet, chestplate, leggings, boots)
Corpse corpse = api.spawnCorpse(player, location, helmet, chestPlate, leggings, boots);
Corpse corpse = api.spawnCorpse(offlinePlayer, location, helmet, chestPlate, leggings, boots);

// Remove a corpse
api.removeCorpse(corpse);
```

Listen for right‑clicks (and attack) on corpses via `AsyncCorpseInteractEvent`; use `getAction()` to tell interact vs attack.
