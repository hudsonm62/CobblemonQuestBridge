package com.ziroau.cobblequestbridge.client.tasks.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettings
import com.ziroau.cobblequestbridge.tasks.PokemonCatchTask
import earth.terrarium.heracles.api.client.settings.SettingInitializer

class PokemonCatchTaskSettings private constructor() : AbstractSettings<PokemonCatchTask>() {
    companion object {
        @JvmField
        val INSTANCE = PokemonCatchTaskSettings()
    }

    override fun create(task: PokemonCatchTask?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()
        settings.put("species", textSetting(), task?.species ?: "any")
        settings.put("attributes", textSetting(), task?.attributes ?: "")
        settings.put("minLevel", intSetting(), task?.minLevel ?: 1)
        settings.put("amount", intSetting(), task?.amount ?: 1)
        settings.put("title", textSetting(), task?.title ?: "")
        settings.put("description", textSetting(), task?.description ?: "")
        return settings
    }

    override fun create(
        id: String, task: PokemonCatchTask?, data: SettingInitializer.Data
    ): PokemonCatchTask {
        val result = PokemonCatchTask(
            id,
            data.get("species", textSetting()).orElse(task?.species ?: "any"),
            data.get("attributes", textSetting()).orElse(task?.attributes ?: ""),
            data.get("minLevel", intSetting()).orElse(task?.minLevel ?: 1),
            data.get("amount", intSetting()).orElse(task?.amount ?: 1).coerceAtLeast(1),
            data.get("title", textSetting()).orElse(task?.title ?: ""),
            data.get("description", textSetting()).orElse(task?.description ?: "")
        )
        return result
    }
}
