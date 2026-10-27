package com.ziroau.cobblequestbridge.api.rewards

import com.ziroau.cobblequestbridge.CobbleQuestBridge
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import earth.terrarium.heracles.api.rewards.QuestReward
import earth.terrarium.heracles.common.handlers.progress.QuestProgressHandler
import earth.terrarium.heracles.common.handlers.quests.QuestHandler
import net.minecraft.server.network.ServerPlayerEntity

interface AbstractQuestReward<T : AbstractQuestReward<T>> : QuestReward<T>, CustomizableQuestElement {
    val rewardId: String
    override fun id(): String = rewardId

    /**
     * Marks this reward as unclaimed for the given [player].
     * Note that this does not nicely handle automatically claimed rewards and repeated quests, so it may not be appropriate to call this in all cases.
     *
     * Useful when a reward cannot be delivered due to an error, invalid configuration, etc.
     *
     * @param player The player for whom the reward should be marked as unclaimed.
     */
    fun markUnclaimed(player: ServerPlayerEntity) {
        val entry = QuestHandler.quests().entries.firstOrNull { (_, quest) ->
            quest.rewards()[rewardId] === this
        } ?: run {
            CobbleQuestBridge.LOGGER.error(
                "[CQB] Could not find quest for reward '{}'",
                rewardId
            )
            return
        }
        val questId = entry.key
        val questProgress = QuestProgressHandler.getProgress(player.server, player.uuid)
            .getProgress(questId)

        if (questProgress.claimedRewards().remove(rewardId)) {
            QuestProgressHandler.sync(player, setOf(questId))
        }
    }
}
