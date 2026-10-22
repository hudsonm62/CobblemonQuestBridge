package com.ziroau.cobblequestbridge.rewards

import com.ziroau.cobblequestbridge.api.registry.BridgeRewardsRegistry

object BridgeRewards {

    fun register() {
        BridgeRewardsRegistry.registerAll(
            RewardPokemon.TYPE
        )
    }
}
