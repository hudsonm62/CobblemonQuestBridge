package com.ziroau.cobblequestbridge.client.rewards.settings

import com.ziroau.cobblequestbridge.api.client.settings.AbstractSettingInitializer
import com.ziroau.cobblequestbridge.rewards.PokemonReward
import earth.terrarium.heracles.api.client.settings.SettingInitializer
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon

class PokemonRewardSettings private constructor() : AbstractSettingInitializer<PokemonReward>() {
    companion object {
        @JvmField
        val INSTANCE = PokemonRewardSettings()
    }

    override fun create(reward: PokemonReward?): SettingInitializer.CreationData {
        val settings = SettingInitializer.CreationData()
        settings.put("species", textSetting(), reward?.species ?: "pikachu")
        settings.put("attributes", textSetting(), reward?.attributes ?: "")
        settings.put("amount", intSetting(), reward?.amount ?: 1)
        settings.put("title", textSetting(), reward?.titleOverride ?: "")
        settings.put("description", textSetting(), reward?.descriptionOverride ?: "")
        settings.put("pokemonIcon", textSetting(), reward?.pokemonIconOverride ?: "")
        settings.put("icon", questIconSetting(), (reward?.icon() as? ItemQuestIcon) ?: ItemQuestIcon.AIR)
        return settings
    }

    override fun create(
        id: String, reward: PokemonReward?, data: SettingInitializer.Data
    ): PokemonReward {
        return PokemonReward(
            id,
            data.get("species", textSetting()).orElse(reward?.species ?: "pikachu"),
            data.get("attributes", textSetting()).orElse(reward?.attributes ?: ""),
            data.get("amount", intSetting()).orElse(reward?.amount ?: 1).coerceAtLeast(1),
            data.get("title", textSetting()).orElse(reward?.title() ?: ""),
            data.get("description", textSetting()).orElse(reward?.description() ?: ""),
            data.get("pokemonIcon", textSetting()).orElse(reward?.pokemonIcon() ?: ""),
            data.get("icon", questIconSetting()).orElse(reward?.icon() as? ItemQuestIcon ?: ItemQuestIcon.AIR)
        )
    }
}
