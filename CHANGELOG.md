# ChangeLog

## [Unreleased]

### Added

- `PokedexAutocompleteSetting` for Pokédex Region Autocomplete in Quest Editor

[Unreleased]: https://github.com/hudsonm62/CobblemonQuestBridge/compare/v0.0.5...master

## [0.0.5] - 2026-10-28

### Added

- `AbstractQuestTask` & `AbstractQuestReward` for quicker quest element creation
- `CountedQuestTask` interface for tasks that "count" progression
- `QuestAppearance` data class for storing common appearance data for tasks/rewards
- Emojis to Task/Reward Titles (like how Heracles does theirs)
- Checks + reward unclaim in `PokemonReward` if the player doesn't have enough space in their Party & PC
- Auto Complete to most `species` fields in the Quest Editor
- Pokedex Progress Task
- Pokemon Trade Task
- Fossil Restore Task

[0.0.5]: https://modrinth.com/mod/cobblemon-quest-bridge/version/0.0.5

## [0.0.4] - 2026-10-27

### Added

- Evolution Task
- Some MoLang functions
- `attributeMatcher()` helper function for matching Pokémon attribute properties

### Changed

- Long Colour conversions when used, instead of on load

### Fixed

- Potential issues with namespacing/species names
- Missing modifier in setting initializer

[0.0.4]: https://modrinth.com/mod/cobblemon-quest-bridge/version/0.0.4

## [0.0.3] - 2026-10-23

### Added

- MoLang Script and Command Rewards
- MoLang Executor for tasks/rewards
- MultiLineString Setting Support in Quest Editor
- Some missing comments to common functions

### Changed

- `BattleTypeEnum.alternateTranslation()` to return `MutableText`

### Removed

- Several now unused imports

[0.0.3]: https://modrinth.com/mod/cobblemon-quest-bridge/version/0.0.3

## [0.0.2] - 2026-10-22

### Added

- Item & Pokémon Icon Overrides
- Translatable Task/Reward Titles & Descriptions
- JavaDocs

### Fixed

- Sources Jar not including the `common` project

[0.0.2]: https://modrinth.com/mod/cobblemon-quest-bridge/version/0.0.2

## [0.0.1] - 2026-10-22

- Alpha Release

[0.0.1]: https://modrinth.com/mod/cobblemon-quest-bridge/version/0.0.1
