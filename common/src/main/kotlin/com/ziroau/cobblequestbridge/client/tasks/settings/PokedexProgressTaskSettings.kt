package com.ziroau.cobblequestbridge.client.tasks.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.tasks.PokedexProgressTask
import earth.terrarium.heracles.api.client.settings.SettingInitializer

class PokedexProgressTaskSettings private constructor() : AbstractSettingInitializer<PokedexProgressTask>() {
    companion object {
        @JvmField
        val INSTANCE = PokedexProgressTaskSettings()
    }

    override fun create(task: PokedexProgressTask?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()

        settings.put("dex", textSetting(), task?.dex ?: "national")
        settings.put("caughtOnly", boolFalseSetting(), task?.caughtOnly ?: false)
        settings.put("amount", intSetting(), task?.amount ?: 100)
        settings.putAppearance(task, PokedexProgressTask.DEFAULT_APPEARANCE)

        return settings
    }

    override fun create(
        id: String, task: PokedexProgressTask?, data: SettingInitializer.Data
    ): PokedexProgressTask {
        return PokedexProgressTask(
            id,
            data.get("dex", textSetting()).orElse(task?.dex ?: "national"),
            data.get("caughtOnly", boolFalseSetting()).orElse(task?.caughtOnly ?: false),
            data.get("amount", intSetting()).orElse(task?.amount ?: 100).coerceAtLeast(1),
            data.getAppearance(task, PokedexProgressTask.DEFAULT_APPEARANCE)
        )
    }
}
