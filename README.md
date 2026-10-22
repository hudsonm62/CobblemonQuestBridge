# Cobble Quest Bridge

Adds Cobblemon compatibility for Heracles (aka Odyssey Quests) for Fabric and NeoForge.

---

Both this mod & the [Heracles 1.21.1 port](https://github.com/terrarium-earth/Heracles/tree/1.21.1) are still deep in development and will receive frequent updates and breaking changes - I'd avoid using it in a production environment (for now).

## Task Type Additions

### Catch Pokémon

| Property      | Type      | Description                                |
| ------------- | --------- | ------------------------------------------ |
| `species`     | `String?` | The species of Pokémon to catch.           |
| `attributes`  | `String?` | Cobblemon attributes to check.             |
| `minLevel `   | `Int`     | The minimum level of the Pokémon to catch. |
| `amount`      | `Int`     | The amount of Pokémon to catch.            |
| `title`       | `String?` | The title override for the task.           |
| `description` | `String?` | The description override for the task.     |

#### Sample

```json
{
  "tasks": {
    "catch_any_mon": {
      "minLevel": 1,
      "amount": 1,
      "type": "cobblequestbridge:pokemon_catch"
    },
    "catch_shinyaipom": {
      "species": "aipom",
      "attributes": "shiny=yes",
      "minLevel": 1,
      "amount": 1,
      "title": "Ooo! Shiny Aipom!",
      "description": "Catch a shiny Aipom!",
      "type": "cobblequestbridge:pokemon_catch"
    }
  }
}
```

### Defeat Pokémon

| Property      | Type      | Description                                 |
| ------------- | --------- | ------------------------------------------- |
| `species`     | `String?` | The species of Pokémon to defeat.           |
| `attributes`  | `String?` | Cobblemon attributes to check.              |
| `minLevel `   | `Int`     | The minimum level of the Pokémon to defeat. |
| `battleType`  | `Enum`    | The type of battle (Any, PvW, PvP, PvN).    |
| `amount`      | `Int`     | The amount of Pokémon to defeat.            |
| `title`       | `String?` | The title override for the task.            |
| `description` | `String?` | The description override for the task.      |

### Battle Victory

| Property             | Type       | Description                               |
| -------------------- | ---------- | ----------------------------------------- |
| `battleType`         | `Enum`     | The type of battle (Any, PvW, PvP, PvN).  |
| `ignoreWildCaptures` | `Boolean?` | Whether to ignore wild captures.          |
| `amount`             | `Int`      | The amount of Pokémon to defeat.          |
| `title`              | `String?`  | The title override for the task.          |
| `description`        | `String?`  | The description override for the task.    |
| `previewPokemon`     | `String?`  | The Pokémon to preview for the task icon. |

#### Sample

```json
{
  "tasks": {
    "win_any_battle": {
      "battleType": "ANY",
      "amount": 1,
      "type": "cobblequestbridge:win_battle"
    },
    "win_5_wild_battles": {
      "battleType": "PVW",
      "ignoreWildCaptures": true,
      "amount": 5,
      "type": "cobblequestbridge:win_battle"
    },
    "win_pvp_battle": {
      "battleType": "PVP",
      "amount": 3,
      "title": "Player VS Player",
      "description": "Win 3 PvP battles!",
      "previewPokemon": "garchomp shiny=no",
      "type": "cobblequestbridge:win_battle"
    }
  }
}
```

### Evolve Pokémon

| Property          | Type      | Description                            |
| ----------------- | --------- | -------------------------------------- |
| `species_from`    | `String?` | The species of Pokémon to evolve from. |
| `species_to`      | `String?` | The species of Pokémon to evolve into. |
| `attributes_from` | `String?` | Cobblemon attributes to check.         |
| `attributes_to`   | `String?` | Cobblemon attributes to check.         |
| `amount`          | `Int`     | The amount of Pokémon to evolve.       |
| `title`           | `String?` | The title override for the task.       |
| `description`     | `String?` | The description override for the task. |

### Trade Pokémon

### Pokédex Progress

### Friendship Progress

## Reward Type Additions

### Pokémon

Rewards a Pokémon to the player.

### MoLang Command

### MoLang Script

## MoLang Additions

Several Serverside MoLang functions are available for use in Cobblemon MoLang scripts:

### Player

| Function | Description |
| -------- | ----------- |
| TODO     | TODO        |

## Permissions

You're welcome to use this in your modpack and server, all I ask is that you:

- Link back via [manifest in your modpack](https://support.modrinth.com/en/articles/8802351-modrinth-modpack-format-mrpack) (so I get stats & creator revenue 🙏)
- Don't lock features behind a paywall, or restrict access in any other way
- Don't re-upload this mod to other platforms
- Don't claim this mod as your own
