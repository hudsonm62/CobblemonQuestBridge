package com.ziroau.cobblequestbridge.api.registry

import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER
import earth.terrarium.heracles.api.rewards.QuestReward
import earth.terrarium.heracles.api.rewards.QuestRewardType
import earth.terrarium.heracles.api.rewards.QuestRewards

/**
 * Functions to quickly register custom quest rewards to Heracles
 *
 * Everything here should be called during initialization.
 */
object BridgeRewardsRegistry {
    /**
     * Register a custom quest reward type.
     */
    @JvmStatic
    fun <T : QuestReward<T>> register(type: QuestRewardType<T>) {
        QuestRewards.register(type)
        logDebug(type.id().toString())
    }

    /**
     * Register multiple quest reward types at once.
     *
     * @sample com.ziroau.cobblequestbridge.rewards.BridgeRewards.register()
     */
    @JvmStatic
    fun registerAll(vararg types: QuestRewardType<*>) {
        types.forEach { type ->
            QuestRewards.register(type)
            logDebug(type.id().toString())
        }
    }

    private fun logDebug(id: String) {
        LOGGER.info(
            "Registered CQB Quest Reward: {}",
            id
        )
    }
}