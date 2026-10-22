package com.ziroau.cobblequestbridge.client.tasks.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.api.data.BattleTypeEnum
import com.ziroau.cobblequestbridge.tasks.BattleWinTask
import earth.terrarium.heracles.api.client.settings.SettingInitializer
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon

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
        settings.put("title", textSetting(), task?.title() ?: "")
        settings.put("description", textSetting(), task?.description() ?: "")
        settings.put("pokemonIcon", textSetting(), task?.pokemonIcon() ?: "pikachu")
        settings.put("icon", questIconSetting(), (task?.icon() as? ItemQuestIcon) ?: ItemQuestIcon.AIR)
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
            data.get("title", textSetting()).orElse(task?.title() ?: ""),
            data.get("description", textSetting()).orElse(task?.description() ?: ""),
            data.get("pokemonIcon", textSetting()).orElse(task?.pokemonIcon() ?: "pikachu"),
            data.get("icon", questIconSetting()).orElse(task?.icon() as? ItemQuestIcon ?: ItemQuestIcon.AIR)
        )
    }
}
