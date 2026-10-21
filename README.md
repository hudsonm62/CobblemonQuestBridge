# Cobble Quest Bridge

Adds Cobblemon compatibility for Heracles (aka Odyssey Quests) for Fabric and NeoForge.

---

Both this mod & the [Heracles 1.21.1 port](https://github.com/terrarium-earth/Heracles/tree/1.21.1) are still deep in development and will receive frequent updates and breaking changes - I'd avoid using it in a production environment (for now).

## Task Type Additions

### Catch Pokémon

| Property     | Type      | Description                      |
| ------------ | --------- | -------------------------------- |
| `species`    | `String?` | The species of Pokémon to catch. |
| `properties` | `String?` | Cobblemon properties to check.   |
| `amount`     | `Int`     | The amount of Pokémon to catch.  |

### Defeat Pokémon

| Property     | Type      | Description                       |
| ------------ | --------- | --------------------------------- |
| `species`    | `String?` | The species of Pokémon to defeat. |
| `properties` | `String?` | Cobblemon properties to check.    |
| `amount`     | `Int`     | The amount of Pokémon to defeat.  |

### Win Battle

### Pokédex Progress

### Deal Damage

### Friendship Progress

## Reward Type Additions

### Pokémon

Rewards a Pokémon to the player.

## MoLang Additions

Several Serverside MoLang functions are available for use in Cobblemon MoLang scripts:

### Player

| Function | Description |
| -------- | ----------- |
| TODO     | TODO        |

## Permissions

You're welcome to use this in your modpack and server, all I ask is that you:

- Link back via manifest in your modpack (so I get stats & creator revenue 🙏)
- Don't lock features behind a paywall, or restrict access in any other way
- Don't re-upload this mod to other platforms
- Don't claim this mod as your own
