package com.ziroau.cobblequestbridge.client

import com.ziroau.cobblequestbridge.client.tasks.BridgeTasksClient
import com.ziroau.cobblequestbridge.client.rewards.BridgeRewardsClient
object CobbleQuestBridgeClient {
    fun init(){
        BridgeTasksClient.register()
        BridgeRewardsClient.register()
    }
}
