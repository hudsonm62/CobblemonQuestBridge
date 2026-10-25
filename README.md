# Cobblemon Quest Bridge

**Cobblemon integration bridge for Heracles (aka Odyssey Quests) for Fabric and NeoForge.**

Track captures, defeats, and victories through tasks, reward players with Pokémon and interact with quest progression through MoLang extensions.

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

## Tasks & Rewards

The Cobblemon Quest Bridge adds several new task and reward types to quests.

The following properties are shared by nearly everything to customise its appearance in the Quest Viewer:

| Property      | Description                                                             |
| ------------- | ----------------------------------------------------------------------- |
| `amount`      | Number required to complete / reward amount                             |
| `title`       | Override Title / Translation key                                        |
| `description` | Override Description / translation key                                  |
| `pokemonIcon` | Pokémon to display as the icon - Supports attributes (i.e. `shiny=yes`) |
| `icon`        | Custom item icon override                                               |

When title or description overrides are not provided, some generic info will be generated instead.
You can also pass through translation keys to use your own translations.

For precedence of icons in the Quest Viewer, the following order is used (where applicable):

- `icon`
- `pokemonIcon`
- `species`
- empty spaceholder

### Task Types

Cobblemon Quest Bridge introduces several task types that can be used in quests.

#### Catch Pokémon

**Task ID:** `cobblequestbridge:pokemon_catch`

Track Pokémon caught by a player, with optional filters for species, attributes, and minimum level.

| Property     | Description                                      |
| ------------ | ------------------------------------------------ |
| `species`    | Species of Pokémon to catch                      |
| `attributes` | Cobblemon attribute filters, such as `shiny=yes` |
| `minLevel`   | Minimum level of the Pokémon                     |

#### Defeat Pokémon

**Task ID:** `cobblequestbridge:pokemon_defeat`

Track Pokémon KO'd in battle, with filters for species, attributes, level, and battle type.

| Property     | Description                                |
| ------------ | ------------------------------------------ |
| `species`    | Species of Pokémon to defeat               |
| `attributes` | Cobblemon attribute filters                |
| `minLevel`   | Minimum level of the Pokémon               |
| `battleType` | Battle type: `ANY`, `PVW`, `PVP`, or `PVN` |

#### Battle Victory

**Task ID:** `cobblequestbridge:battle_win`

Track battles won by a player, with restrictions on battle type and victory by capture.

| Property             | Description                                                      |
| -------------------- | ---------------------------------------------------------------- |
| `battleType`         | Battle type: `ANY`, `PVW`, `PVP`, or `PVN`                       |
| `ignoreWildCaptures` | Whether wild Pokémon captures should be ignored (default=`true`) |

### Reward Types

Reward players when they complete quests.

#### Pokémon

**Reward ID:** `cobblequestbridge:pokemon_reward`

Rewards Pokémon to the player.

| Property     | Description                                  |
| ------------ | -------------------------------------------- |
| `species`    | Species of Pokémon to reward                 |
| `attributes` | Cobblemon attributes to apply, i.e. `lvl=42` |

#### MoLang Script Reward

**Reward ID:** `cobblequestbridge:molangscript_reward`

Executes a registered MoLang script when the reward is claimed - Runs in `player` context.
Automatically prepends `cobblemon:` if a namespace is not provided (e.g. `my_script` becomes `cobblemon:my_script`)

| Property | Description                                |
| -------- | ------------------------------------------ |
| `script` | Identifier of the MoLang script to execute |

#### MoLang Commands Reward

**Reward ID:** `cobblequestbridge:molangcommands_reward`

Executes a set of MoLang Commands when the reward is claimed - Runs in `player` context.

| Property   | Description                          |
| ---------- | ------------------------------------ |
| `commands` | A list of MoLang Commands to execute |

### MoLang Extensions

Cobblemon Quest Bridge extends MoLang with functions for interacting with the quest system:

#### Player Functions

`q.player.<function_name>(<args>)`

| Function                                        | Description                                              |
| ----------------------------------------------- | -------------------------------------------------------- |
| `complete_quest_task("questId", "taskId")`      | Marks a task as completed                                |
| `has_completed_quest_task("questId", "taskId")` | Checks if a task has been completed                      |
| `claim_quest_reward("questId", "rewardId")`     | Claims a completed quest reward                          |
| `claim_quest_rewards("questId")`                | Claims all rewards in a completed quest                  |
| `complete_quest("questId")`                     | Forcefully completes a quest                             |
| `has_completed_quest("questId")`                | Checks if a quest has been completed                     |
| `is_active_quest("questId")`                    | Checks if a quest is active (not completed, but started) |

## Developer Info

For extending Cobblemon Quest Bridge, see files under [`common/src/main/kotlin/com/ziroau/cobblequestbridge/api`](https://github.com/hudsonm62/cobblemonquestbridge/tree/master/common/src/main/kotlin/com/ziroau/cobblequestbridge/api)

Several interfaces and functions are provided for rapidly adding new Cobblemon-themed tasks, rewards and UI elements to Heracles/Odyssey Quests.

```gradle
repositories {
    maven {
        name = "ziro's maven"
        url = "https://maven.ziroau.com"
    }
}
dependencies {
    modImplementation("com.ziroau:cobblemonquestbridge:1.0.0+fabric")
}
```

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
