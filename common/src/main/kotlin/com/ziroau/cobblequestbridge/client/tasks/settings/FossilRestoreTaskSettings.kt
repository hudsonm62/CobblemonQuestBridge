package com.ziroau.cobblequestbridge.client.tasks.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.tasks.FossilRestoreTask
import earth.terrarium.heracles.api.client.settings.SettingInitializer

class FossilRestoreTaskSettings private constructor() : AbstractSettingInitializer<FossilRestoreTask>() {
    companion object {
        @JvmField
        val INSTANCE = FossilRestoreTaskSettings()
    }

    override fun create(task: FossilRestoreTask?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()
        settings.put("species", anySpeciesSetting(), task?.species ?: "any")
        settings.put("attributes", textSetting(), task?.attributes ?: "")
        settings.put("amount", intSetting(), task?.amount ?: 1)
        settings.putAppearance(task, FossilRestoreTask.DEFAULT_APPEARANCE)
        return settings
    }

    override fun create(
        id: String, task: FossilRestoreTask?, data: SettingInitializer.Data
    ): FossilRestoreTask {
        return FossilRestoreTask(
            id,
            data.get("species", anySpeciesSetting()).orElse(task?.species ?: "any"),
            data.get("attributes", textSetting()).orElse(task?.attributes ?: ""),
            data.get("amount", intSetting()).orElse(task?.amount ?: 1).coerceAtLeast(1),
            data.getAppearance(task, FossilRestoreTask.DEFAULT_APPEARANCE)
        )
    }
}
