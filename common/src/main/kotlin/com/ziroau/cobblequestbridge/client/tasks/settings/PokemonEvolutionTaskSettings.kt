package com.ziroau.cobblequestbridge.client.tasks.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.tasks.PokemonEvolutionTask
import earth.terrarium.heracles.api.client.settings.SettingInitializer
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon

class PokemonEvolutionTaskSettings private constructor() : AbstractSettingInitializer<PokemonEvolutionTask>() {
    companion object {
        @JvmField
        val INSTANCE = PokemonEvolutionTaskSettings()
    }

    override fun create(task: PokemonEvolutionTask?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()

        settings.put("fromSpecies", textSetting(), task?.fromSpecies ?: "any")
        settings.put("fromAttributes", textSetting(), task?.fromAttributes ?: "")
        settings.put("toSpecies", textSetting(), task?.toSpecies ?: "any")
        settings.put("toAttributes", textSetting(), task?.toAttributes ?: "")
        settings.put("minLevel", intSetting(), task?.minLevel ?: 1)
        settings.put("amount", intSetting(), task?.amount ?: 1)
        settings.put("title", textSetting(), task?.title() ?: "")
        settings.put("description", textSetting(), task?.description() ?: "")
        settings.put("pokemonIcon", textSetting(), task?.pokemonIcon() ?: "")
        settings.put("icon", questIconSetting(), (task?.icon() as? ItemQuestIcon) ?: ItemQuestIcon.AIR)

        return settings
    }

    override fun create(
        id: String, task: PokemonEvolutionTask?, data: SettingInitializer.Data
    ): PokemonEvolutionTask {
        return PokemonEvolutionTask(
            id,
            data.get("fromSpecies", textSetting()).orElse(task?.fromSpecies ?: "any"),
            data.get("fromAttributes", textSetting()).orElse(task?.fromAttributes ?: ""),
            data.get("toSpecies", textSetting()).orElse(task?.toSpecies ?: "any"),
            data.get("toAttributes", textSetting()).orElse(task?.toAttributes ?: ""),
            data.get("minLevel", intSetting()).orElse(task?.minLevel ?: 1),
            data.get("amount", intSetting()).orElse(task?.amount ?: 1).coerceAtLeast(1),
            data.get("title", textSetting()).orElse(task?.title() ?: ""),
            data.get("description", textSetting()).orElse(task?.description() ?: ""),
            data.get("pokemonIcon", textSetting()).orElse(task?.pokemonIcon() ?: ""),
            data.get("icon", questIconSetting()).orElse((task?.icon() as? ItemQuestIcon) ?: ItemQuestIcon.AIR)
        )
    }
}