package com.ziroau.cobblequestbridge.fabric

import com.ziroau.cobblequestbridge.CobbleQuestBridge
import net.fabricmc.api.ModInitializer

class CobbleQuestBridgeFabric : ModInitializer {
    override fun onInitialize() {
        CobbleQuestBridge.init()
    }
}
