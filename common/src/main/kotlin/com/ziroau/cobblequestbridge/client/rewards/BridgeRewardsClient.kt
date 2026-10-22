package com.ziroau.cobblequestbridge.client.rewards

import com.ziroau.cobblequestbridge.api.client.registry.BridgeRewardsClientRegistry
import com.ziroau.cobblequestbridge.client.rewards.settings.PokemonRewardSettings
import com.ziroau.cobblequestbridge.client.rewards.widgets.PokemonRewardWidget
import com.ziroau.cobblequestbridge.rewards.RewardPokemon

object BridgeRewardsClient {

    fun register() {
        BridgeRewardsClientRegistry.register(
            RewardPokemon.TYPE,
            PokemonRewardSettings.INSTANCE,
            PokemonRewardWidget::of
        )
    }
}
