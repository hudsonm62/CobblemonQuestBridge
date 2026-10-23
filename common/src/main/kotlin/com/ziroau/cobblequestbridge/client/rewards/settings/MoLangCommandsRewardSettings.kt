package com.ziroau.cobblequestbridge.client.rewards.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.rewards.MoLangCommandsReward
import earth.terrarium.heracles.api.client.settings.SettingInitializer
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon

class MoLangCommandsRewardSettings private constructor() : AbstractSettingInitializer<MoLangCommandsReward>() {
    companion object {
        @JvmField
        val INSTANCE = MoLangCommandsRewardSettings()
    }

    override fun create(reward: MoLangCommandsReward?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()
        settings.put("commands", stringListSetting(), reward?.commands ?: emptyList())
        settings.put("title", textSetting(), reward?.titleOverride ?: "")
        settings.put("description", textSetting(), reward?.descriptionOverride ?: "")
        settings.put("pokemonIcon", textSetting(), reward?.pokemonIconOverride ?: "")
        settings.put("icon", questIconSetting(), (reward?.icon() as? ItemQuestIcon) ?: ItemQuestIcon.AIR)
        return settings
    }

    override fun create(
        id: String, reward: MoLangCommandsReward?, data: SettingInitializer.Data
    ): MoLangCommandsReward {
        return MoLangCommandsReward(
            id,
            data.get("commands", stringListSetting()).orElse(reward?.commands ?: emptyList()),
            data.get("title", textSetting()).orElse(reward?.title() ?: ""),
            data.get("description", textSetting()).orElse(reward?.description() ?: ""),
            data.get("pokemonIcon", textSetting()).orElse(reward?.pokemonIcon() ?: ""),
            data.get("icon", questIconSetting()).orElse(reward?.icon() as? ItemQuestIcon ?: ItemQuestIcon.AIR)
        )
    }
}
