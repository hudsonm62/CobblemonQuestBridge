package com.ziroau.cobblequestbridge

import com.ziroau.cobblequestbridge.molang.MoLangFunctions
import com.ziroau.cobblequestbridge.tasks.BridgeTasks
import com.ziroau.cobblequestbridge.rewards.BridgeRewards
import com.ziroau.cobblequestbridge.events.PokemonCatchHandler
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object CobbleQuestBridge {
    const val MOD_ID = "cobblequestbridge"
    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    fun init() {
        MoLangFunctions.init()
        BridgeTasks.register()
        BridgeRewards.register()
        PokemonCatchHandler.register()
        LOGGER.info("Cobble Quest Bridge initialized - Happy questing!")
    }
}
