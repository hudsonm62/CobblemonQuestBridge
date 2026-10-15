# Cobble Quest Bridge

Adds Cobblemon compatibility to Heracles (aka Odyssey Quests) for both Fabric and NeoForge.

## Task Type Additions

### Catch Pokemon

Requires the player to catch a specific Pokemon `x` amount of time(s).

### Defeat Pokemon

Requires the player to defeat a specific Pokemon `x` amount of time(s).

## Reward Type Additions

### Pokemon

Rewards a Pokemon to the player.

### TM

Rewards a TM to the player.

## MoLang Additions

Several Serverside MoLang functions are available for use in Cobblemon MoLang scripts:

### Player

| Function                            | Description                                             |
| ----------------------------------- | ------------------------------------------------------- |
| `complete_quest("<quest_id>")`      | Completes the specified quest for the player.           |
| `has_completed_quest("<quest_id>")` | Checks if the player has completed the specified quest. |
| `has_active_quest("<quest_id>")`    | Checks if the player has the specified quest active.    |
