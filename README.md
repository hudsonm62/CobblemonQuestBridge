# Cobblemon Quest Bridge

**Cobblemon integration for Heracles (aka Odyssey Quests), available for Fabric and NeoForge.**

Cobblemon Quest Bridge adds Pokémon-focused tasks, rewards, and MoLang utilities!

## Requirements

- [Heracles 2.0+](https://modrinth.com/project/lo90fZoB)
- [Cobblemon 1.8+](https://modrinth.com/project/MdwFAVRL)
- Fabric or NeoForge

> [!WARNING]
> **Early Development**
>
> Both Cobblemon Quest Bridge and the [Heracles 1.21.1 port](https://github.com/terrarium-earth/Heracles/tree/1.21.1) are actively under development.
>
> Expect frequent updates, API changes, and potentially breaking changes.

---

## Task Types

Cobblemon Quest Bridge introduces several task types that can be used in Heracles quests.

### Catch Pokémon

Track Pokémon caught by a player, with optional filters for species, attributes, and minimum level.

**Task ID:** `cobblequestbridge:pokemon_catch`

| Property      | Type      | Description                                       |
| ------------- | --------- | ------------------------------------------------- |
| `species`     | `String?` | Species of Pokémon to catch.                      |
| `attributes`  | `String?` | Cobblemon attribute filters, such as `shiny=yes`. |
| `minLevel`    | `Int`     | Minimum level of the Pokémon.                     |
| `amount`      | `Int`     | Number of Pokémon required.                       |
| `title`       | `String?` | Custom task title.                                |
| `description` | `String?` | Custom task description.                          |

**Example**

Catch any Pokémon, or catch a shiny Aipom:

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

Track Pokémon defeated in battle, with optional filters for species, attributes, minimum level, and battle type.

| Property      | Type      | Description                                 |
| ------------- | --------- | ------------------------------------------- |
| `species`     | `String?` | Species of Pokémon to defeat.               |
| `attributes`  | `String?` | Cobblemon attribute filters.                |
| `minLevel`    | `Int`     | Minimum level of the Pokémon.               |
| `battleType`  | `Enum`    | Battle type: `ANY`, `PVW`, `PVP`, or `PVN`. |
| `amount`      | `Int`     | Number of Pokémon to defeat.                |
| `title`       | `String?` | Custom task title.                          |
| `description` | `String?` | Custom task description.                    |

### Battle Victory

Track battles won by a player, with optional restrictions on battle type and wild Pokémon captures.

**Task ID:** `cobblequestbridge:win_battle`

| Property             | Type       | Description                                                      |
| -------------------- | ---------- | ---------------------------------------------------------------- |
| `battleType`         | `Enum`     | Battle type: `ANY`, `PVW`, `PVP`, or `PVN`.                      |
| `ignoreWildCaptures` | `Boolean?` | Whether wild Pokémon captures should be excluded from victories. |
| `amount`             | `Int`      | Number of battle victories required.                             |
| `title`              | `String?`  | Custom task title.                                               |
| `description`        | `String?`  | Custom task description.                                         |
| `previewPokemon`     | `String?`  | Pokémon to display as the task icon.                             |

**Example**

Win any battle, win five wild battles without counting captures, or win three PvP battles:

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

## Reward Types

Reward players when they complete quests.

### Pokémon

Give a Pokémon directly to the player as a quest reward.

## MoLang Extensions

Cobblemon Quest Bridge extends server-side Cobblemon MoLang with additional functions for interacting with the quest system.

### Player Functions

_Function reference coming soon._

## Developer Info

For extending Cobblemon Quest Bridge, see files under [`common/src/main/kotlin/com/ziroau/cobblequestbridge/api`](https://github.com/hudsonm62/cobblemonquestbridge/tree/master/common/src/main/kotlin/com/ziroau/cobblequestbridge/api)

Several interfaces and functions are provided for rapidly adding new Pokémon-themed tasks, rewards and UI elements.

---

## Permissions & Modpack Usage

You're welcome to include Cobblemon Quest Bridge in your **modpack or server**, including publicly distributed modpacks.

All I ask is that you:

- **Keep attribution intact.**
  - Include the mod through your modpack's [Modrinth `.mrpack` manifest](https://support.modrinth.com/en/articles/8802351-modrinth-modpack-format-mrpack) where applicable. This helps support the project through download statistics and creator revenue. 🙏
- **Keep features accessible.**
  - Don't lock the mod's functionality behind paywalls or otherwise restrict access to its features.
- **Don't redistribute the mod separately.**
  - Please don't re-upload the mod to other platforms.
- **Respect authorship.**
  - Don't claim the mod or its code as your own.

Thanks for supporting the project! ❤️
