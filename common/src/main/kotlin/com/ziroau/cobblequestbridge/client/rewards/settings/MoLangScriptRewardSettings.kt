package com.ziroau.cobblequestbridge.client.rewards.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.rewards.MoLangScriptReward
import earth.terrarium.heracles.api.client.settings.SettingInitializer

class MoLangScriptRewardSettings private constructor() : AbstractSettingInitializer<MoLangScriptReward>() {
    companion object {
        @JvmField
        val INSTANCE = MoLangScriptRewardSettings()
    }

    override fun create(reward: MoLangScriptReward?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()
        settings.put("script", textSetting(), reward?.script ?: "")
        settings.putAppearance(reward)
        return settings
    }

    override fun create(
        id: String, reward: MoLangScriptReward?, data: SettingInitializer.Data
    ): MoLangScriptReward {
        return MoLangScriptReward(
            id,
            data.get("script", textSetting()).orElse(reward?.script ?: ""),
            data.getAppearance(reward)
        )
    }
}
