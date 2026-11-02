package com.ziroau.cobblequestbridge.rewards

import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER
import com.ziroau.iolaus.api.registry.RewardsRegistry

object BridgeRewards {
    fun register() {
        RewardsRegistry.registerAll(
            PokemonReward.TYPE,
            MoLangScriptReward.TYPE,
            MoLangCommandsReward.TYPE
        )
        LOGGER.info("Registered {} CQB Quest Rewards", 3)
    }
}
