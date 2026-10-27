package com.ziroau.cobblequestbridge.client

import com.ziroau.cobblequestbridge.client.rewards.BridgeRewardsClient
import com.ziroau.cobblequestbridge.client.tasks.BridgeTasksClient

object CobbleQuestBridgeClient {
    fun init(){
        BridgeTasksClient.register()
        BridgeRewardsClient.register()
    }
}
