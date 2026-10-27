package com.ziroau.cobblequestbridge.api.rewards

import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import earth.terrarium.heracles.api.rewards.QuestReward

interface AbstractQuestReward<T : AbstractQuestReward<T>> : QuestReward<T>, CustomizableQuestElement {
    val rewardId: String
    override fun id(): String = rewardId
}
