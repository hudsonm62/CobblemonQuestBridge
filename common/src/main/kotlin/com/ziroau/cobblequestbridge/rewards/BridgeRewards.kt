package com.ziroau.cobblequestbridge.rewards

import com.ziroau.iolaus.api.registry.RewardsRegistry

object BridgeRewards {
    fun register() {
        RewardsRegistry.registerAll(
            PokemonReward.TYPE,
            MoLangScriptReward.TYPE,
            MoLangCommandsReward.TYPE
        )
    }
}
