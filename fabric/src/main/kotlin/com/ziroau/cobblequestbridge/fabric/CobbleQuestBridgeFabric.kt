package com.ziroau.cobblequestbridge.fabric

import net.fabricmc.api.ModInitializer
import com.ziroau.cobblequestbridge.CobbleQuestBridge

object CobbleQuestBridgeFabric : ModInitializer {
    override fun onInitialize() {
        CobbleQuestBridge.init()
    }
}
