package com.ziroau.cobblequestbridge.neoforge.client

import com.ziroau.cobblequestbridge.CobbleQuestBridge
import com.ziroau.cobblequestbridge.client.CobbleQuestBridgeClient
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent

@Suppress("unused")
@EventBusSubscriber(
    modid = CobbleQuestBridge.MOD_ID,
    value = [Dist.CLIENT]
)
object CobbleQuestBridgeNeoForgeClient {
    @JvmStatic
    @SubscribeEvent
    fun onClientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork {
            CobbleQuestBridgeClient.init()
        }
    }
}
