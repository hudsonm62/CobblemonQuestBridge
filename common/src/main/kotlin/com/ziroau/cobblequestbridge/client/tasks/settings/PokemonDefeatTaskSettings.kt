package com.ziroau.cobblequestbridge.client.tasks.settings

import com.ziroau.cobblequestbridge.client.helpers.AbstractSettings
import com.ziroau.cobblequestbridge.data.BattleTypeEnum
import com.ziroau.cobblequestbridge.tasks.PokemonDefeatTask
import earth.terrarium.heracles.api.client.settings.SettingInitializer

class PokemonDefeatTaskSettings private constructor() : AbstractSettings<PokemonDefeatTask>() {
    companion object {
        @JvmField
        val INSTANCE = PokemonDefeatTaskSettings()
    }

    override fun create(task: PokemonDefeatTask?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()

        settings.put("species", textSetting(), task?.species ?: "any")
        settings.put("properties", textSetting(), task?.properties ?: "")
        settings.put("minLevel", intSetting(), task?.minLevel ?: 1)
        settings.put("battleType", battleTypeSetting(), task?.battleType ?: BattleTypeEnum.ANY)
        settings.put("amount", intSetting(), task?.amount ?: 1)
        settings.put("title", textSetting(), task?.title ?: "")
        settings.put("description", textSetting(), task?.description ?: "")

        return settings
    }

    override fun create(
        id: String, task: PokemonDefeatTask?, data: SettingInitializer.Data
    ): PokemonDefeatTask {
        return PokemonDefeatTask(
            id,
            data.get("species", textSetting()).orElse(task?.species ?: "any"),
            data.get("properties", textSetting()).orElse(task?.properties ?: ""),
            data.get("minLevel", intSetting()).orElse(task?.minLevel ?: 1),
            data.get("battleType", battleTypeSetting()).orElse(task?.battleType ?: BattleTypeEnum.ANY),
            data.get("amount", intSetting()).orElse(task?.amount ?: 1).coerceAtLeast(1),
            data.get("title", textSetting()).orElse(task?.title ?: ""),
            data.get("description", textSetting()).orElse(task?.description ?: "")
        )
    }
}
