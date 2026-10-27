package com.ziroau.cobblequestbridge.client.tasks.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.api.data.BattleTypeEnum
import com.ziroau.cobblequestbridge.tasks.BattleWinTask
import earth.terrarium.heracles.api.client.settings.SettingInitializer

class BattleWinTaskSettings private constructor() : AbstractSettingInitializer<BattleWinTask>() {
    companion object {
        @JvmField
        val INSTANCE = BattleWinTaskSettings()
    }

    override fun create(task: BattleWinTask?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()

        settings.put("battleType", battleTypeSetting(), task?.battleType ?: BattleTypeEnum.ANY)
        settings.put("ignoreWildCaptures", boolTrueSetting(), task?.ignoreWildCaptures ?: true)
        settings.put("amount", intSetting(), task?.amount ?: 1)
        settings.putAppearance(task, BattleWinTask.DEFAULT_APPEARANCE)
        return settings
    }

    override fun create(
        id: String, task: BattleWinTask?, data: SettingInitializer.Data
    ): BattleWinTask {
        return BattleWinTask(
            id,
            data.get("battleType", battleTypeSetting()).orElse(task?.battleType ?: BattleTypeEnum.ANY),
            data.get("ignoreWildCaptures", boolTrueSetting()).orElse(task?.ignoreWildCaptures ?: true),
            data.get("amount", intSetting()).orElse(task?.amount ?: 1).coerceAtLeast(1),
            data.getAppearance(task, BattleWinTask.DEFAULT_APPEARANCE)
        )
    }
}
