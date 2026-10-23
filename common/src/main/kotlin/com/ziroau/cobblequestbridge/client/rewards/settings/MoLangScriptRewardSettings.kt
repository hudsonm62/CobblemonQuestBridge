package com.ziroau.cobblequestbridge.client.rewards.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.rewards.MoLangScriptReward
import earth.terrarium.heracles.api.client.settings.SettingInitializer
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon

class MoLangScriptRewardSettings private constructor() : AbstractSettingInitializer<MoLangScriptReward>() {
    companion object {
        @JvmField
        val INSTANCE = MoLangScriptRewardSettings()
    }

    override fun create(reward: MoLangScriptReward?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()
        settings.put("script", textSetting(), reward?.script ?: "")
        settings.put("title", textSetting(), reward?.titleOverride ?: "")
        settings.put("description", textSetting(), reward?.descriptionOverride ?: "")
        settings.put("pokemonIcon", textSetting(), reward?.pokemonIconOverride ?: "")
        settings.put("icon", questIconSetting(), (reward?.icon() as? ItemQuestIcon) ?: ItemQuestIcon.AIR)
        return settings
    }

    override fun create(
        id: String, reward: MoLangScriptReward?, data: SettingInitializer.Data
    ): MoLangScriptReward {
        return MoLangScriptReward(
            id,
            data.get("script", textSetting()).orElse(reward?.script ?: ""),
            data.get("title", textSetting()).orElse(reward?.title() ?: ""),
            data.get("description", textSetting()).orElse(reward?.description() ?: ""),
            data.get("pokemonIcon", textSetting()).orElse(reward?.pokemonIcon() ?: ""),
            data.get("icon", questIconSetting()).orElse(reward?.icon() as? ItemQuestIcon ?: ItemQuestIcon.AIR)
        )
    }
}
