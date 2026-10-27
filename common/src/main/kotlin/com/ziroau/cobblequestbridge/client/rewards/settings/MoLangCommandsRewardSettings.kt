package com.ziroau.cobblequestbridge.client.rewards.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.rewards.MoLangCommandsReward
import earth.terrarium.heracles.api.client.settings.SettingInitializer

class MoLangCommandsRewardSettings private constructor() : AbstractSettingInitializer<MoLangCommandsReward>() {
    companion object {
        @JvmField
        val INSTANCE = MoLangCommandsRewardSettings()
    }

    override fun create(reward: MoLangCommandsReward?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()
        settings.put("commands", stringListSetting(), reward?.commands ?: emptyList())
        settings.putAppearance(reward)
        return settings
    }

    override fun create(
        id: String, reward: MoLangCommandsReward?, data: SettingInitializer.Data
    ): MoLangCommandsReward {
        return MoLangCommandsReward(
            id,
            data.get("commands", stringListSetting()).orElse(reward?.commands ?: emptyList()),
            data.getAppearance(reward)
        )
    }
}
