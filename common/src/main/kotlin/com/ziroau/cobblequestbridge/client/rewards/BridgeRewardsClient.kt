package com.ziroau.cobblequestbridge.client.rewards

import com.ziroau.cobblequestbridge.client.rewards.settings.PokemonRewardSettings
import com.ziroau.cobblequestbridge.client.rewards.widgets.PokemonRewardWidget
import com.ziroau.cobblequestbridge.rewards.RewardPokemon
import earth.terrarium.heracles.api.client.settings.Settings
import earth.terrarium.heracles.api.rewards.client.QuestRewardWidgets

object BridgeRewardsClient {
    fun register() {
        registerPokemonReward()
    }

    private fun registerPokemonReward() {
        Settings.register(
            RewardPokemon.TYPE,
            PokemonRewardSettings.INSTANCE
        )

        QuestRewardWidgets.register(
            RewardPokemon.TYPE,
            PokemonRewardWidget::of
        )
    }
}
