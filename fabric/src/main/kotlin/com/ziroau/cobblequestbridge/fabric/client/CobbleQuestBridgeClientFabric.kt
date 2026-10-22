package com.ziroau.cobblequestbridge.fabric.client

import com.ziroau.cobblequestbridge.client.CobbleQuestBridgeClient
import net.fabricmc.api.ClientModInitializer

class CobbleQuestBridgeClientFabric : ClientModInitializer {
    override fun onInitializeClient() {
        CobbleQuestBridgeClient.init()
    }
}
