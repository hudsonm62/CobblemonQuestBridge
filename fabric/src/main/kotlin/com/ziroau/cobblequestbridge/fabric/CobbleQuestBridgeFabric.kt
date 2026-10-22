package com.ziroau.cobblequestbridge.fabric

import net.fabricmc.api.ModInitializer
import com.ziroau.cobblequestbridge.CobbleQuestBridge

class CobbleQuestBridgeFabric : ModInitializer {
    override fun onInitialize() {
        CobbleQuestBridge.init()
    }
}
