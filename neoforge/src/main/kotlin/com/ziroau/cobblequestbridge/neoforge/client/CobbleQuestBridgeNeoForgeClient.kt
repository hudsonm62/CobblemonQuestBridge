package com.ziroau.cobblequestbridge.neoforge.client

import com.ziroau.cobblequestbridge.CobbleQuestBridge
import com.ziroau.cobblequestbridge.client.CobbleQuestBridgeClient
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent

@Suppress("unused")
@Mod(value = CobbleQuestBridge.MOD_ID, dist = [Dist.CLIENT])
class CobbleQuestBridgeNeoForgeClient(modBus: IEventBus) {
    init {
        modBus.addListener(::onClientSetup)
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork {
            CobbleQuestBridgeClient.init()
        }
    }
}
