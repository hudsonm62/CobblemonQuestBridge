package com.ziroau.cobblequestbridge.client.rewards.settings

import com.ziroau.cobblequestbridge.client.helpers.AbstractSettings
import com.ziroau.cobblequestbridge.rewards.RewardPokemon
import earth.terrarium.heracles.api.client.settings.SettingInitializer

class PokemonRewardSettings private constructor() : AbstractSettings<RewardPokemon>() {
    companion object {
        @JvmField
        val INSTANCE = PokemonRewardSettings()
    }

    override fun create(reward: RewardPokemon?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()
        settings.put("title", textSetting(), reward?.title ?: "")
        settings.put("species", textSetting(), reward?.species ?: "pikachu")
        settings.put("properties", textSetting(), reward?.properties ?: "")
        settings.put("amount", intSetting(), reward?.amount ?: 1)
        settings.put("description", textSetting(), reward?.description ?: "")
        return settings
    }

    override fun create(
        id: String, reward: RewardPokemon?, data: SettingInitializer.Data
    ): RewardPokemon {
        return RewardPokemon(
            id,
            data.get("title", textSetting()).orElse(reward?.title ?: ""),
            data.get("species", textSetting()).orElse(reward?.species ?: "pikachu"),
            data.get("properties", textSetting()).orElse(reward?.properties ?: ""),
            data.get("amount", intSetting()).orElse(reward?.amount ?: 1).coerceAtLeast(1),
            data.get("description", textSetting()).orElse(reward?.title ?: ""),
        )
    }
}
