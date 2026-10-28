package com.ziroau.cobblequestbridge.client.tasks.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.tasks.PokemonTradeTask
import earth.terrarium.heracles.api.client.settings.SettingInitializer

class PokemonTradeTaskSettings private constructor() : AbstractSettingInitializer<PokemonTradeTask>() {
    companion object {
        @JvmField
        val INSTANCE = PokemonTradeTaskSettings()
    }

    override fun create(task: PokemonTradeTask?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()

        settings.put("speciesSent", anySpeciesSetting(), task?.speciesSent ?: "any")
        settings.put("attributesSent", textSetting(), task?.attributesSent ?: "")
        settings.put("minLevelSent", intSetting(), task?.minLevelSent ?: 1)
        settings.put("speciesReceived", anySpeciesSetting(), task?.speciesReceived ?: "any")
        settings.put("attributesReceived", textSetting(), task?.attributesReceived ?: "")
        settings.put("minLevelReceived", intSetting(), task?.minLevelReceived ?: 1)
        settings.put("amount", intSetting(), task?.amount ?: 1)
        settings.putAppearance(task)
        return settings
    }

    override fun create(
        id: String, task: PokemonTradeTask?, data: SettingInitializer.Data
    ): PokemonTradeTask {
        return PokemonTradeTask(
            id,
            data.get("speciesSent", anySpeciesSetting()).orElse(task?.speciesSent ?: "any"),
            data.get("attributesSent", textSetting()).orElse(task?.attributesSent ?: ""),
            data.get("minLevelSent", intSetting()).orElse(task?.minLevelSent ?: 1),
            data.get("speciesReceived", anySpeciesSetting()).orElse(task?.speciesReceived ?: "any"),
            data.get("attributesReceived", textSetting()).orElse(task?.attributesReceived ?: ""),
            data.get("minLevelReceived", intSetting()).orElse(task?.minLevelReceived ?: 1),
            data.get("amount", intSetting()).orElse(task?.amount ?: 1).coerceAtLeast(1),
            data.getAppearance(task)
        )
    }
}
