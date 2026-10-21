package com.ziroau.cobblequestbridge.rewards

import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER
import earth.terrarium.heracles.api.rewards.QuestRewards

object BridgeRewards {
    fun register() {
        QuestRewards.register(RewardPokemon.TYPE)
        LOGGER.info("Registered CQB Quest Reward: {}", RewardPokemon.TYPE.id())
    }
}
