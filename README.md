# Corpse

[![](https://jitpack.io/v/denmeh/Corpse.svg)](https://jitpack.io/#denmeh/Corpse)

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
- `entity-pose` – Pose used for the corpse entity (`SLEEPING` or `SWIMMING`).

## API

Add the dependency (e.g. JitPack) and use the **builder API** via `Corpse.fromPlayer()` or `Corpse.fromLocation()`:

```java
import com.github.unldenis.corpse.corpse.Corpse;
import com.github.unldenis.corpse.model.CorpseArmor;

// At player location, with their skin and armor
Corpse corpse = Corpse.fromPlayer(player).spawn();

// At a specific location
Corpse corpse = Corpse.fromPlayer(player).location(location).spawn();
Corpse corpse = Corpse.fromLocation(location).name(offlinePlayer.getName()).spawn();

// Custom armor (use CorpseArmor)
CorpseArmor armor = new CorpseArmor().boots(boots).leggings(leggings).chestplate(chestplate).helmet(helmet);
Corpse corpse = Corpse.fromPlayer(player).location(location).armor(armor).spawn();
Corpse corpse = Corpse.fromLocation(location).name(name).armor(armor).spawn();

// Remove a corpse
corpse.destroy();
```

The builder also supports `.textures(List<TextureProperty>)` for custom skins. Listen for right‑clicks and attacks on corpses via `AsyncCorpseInteractEvent`; use `getAction()` to distinguish interact vs attack.
