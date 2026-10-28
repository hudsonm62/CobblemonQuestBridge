package com.ziroau.cobblequestbridge.client.rewards.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.rewards.PokemonReward
import earth.terrarium.heracles.api.client.settings.SettingInitializer

class PokemonRewardSettings private constructor() : AbstractSettingInitializer<PokemonReward>() {
    companion object {
        @JvmField
        val INSTANCE = PokemonRewardSettings()
    }

    override fun create(reward: PokemonReward?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()
        settings.put("species", speciesSetting(), reward?.species ?: "pikachu")
        settings.put("attributes", textSetting(), reward?.attributes ?: "")
        settings.put("amount", intSetting(), reward?.amount ?: 1)
        settings.put("unclaimOnFail", boolTrueSetting(), reward?.unclaimOnFail ?: true)
        settings.putAppearance(reward)
        return settings
    }

    override fun create(
        id: String, reward: PokemonReward?, data: SettingInitializer.Data
    ): PokemonReward {
        return PokemonReward(
            id,
            data.get("species", speciesSetting()).orElse(reward?.species ?: "pikachu"),
            data.get("attributes", textSetting()).orElse(reward?.attributes ?: ""),
            data.get("amount", intSetting()).orElse(reward?.amount ?: 1).coerceAtLeast(1),
            data.get("unclaimOnFail", boolTrueSetting()).orElse(reward?.unclaimOnFail ?: true),
            data.getAppearance(reward)
        )
    }
}
