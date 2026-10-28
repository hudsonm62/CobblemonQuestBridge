package com.ziroau.cobblequestbridge

import com.ziroau.cobblequestbridge.events.BattleWinHandler
import com.ziroau.cobblequestbridge.events.DexHandler
import com.ziroau.cobblequestbridge.events.PokemonCatchHandler
import com.ziroau.cobblequestbridge.events.PokemonDefeatHandler
import com.ziroau.cobblequestbridge.events.PokemonEvolutionHandler
import com.ziroau.cobblequestbridge.events.PokemonTradeHandler
import com.ziroau.cobblequestbridge.molang.MoLangFunctions
import com.ziroau.cobblequestbridge.rewards.BridgeRewards
import com.ziroau.cobblequestbridge.tasks.BridgeTasks
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object CobbleQuestBridge {
    const val MOD_ID = "cobblequestbridge"
    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    fun init() {
        MoLangFunctions.register()
        BridgeTasks.register()
        BridgeRewards.register()
        registerEvents()
        LOGGER.info("Cobble Quest Bridge initialized - Happy questing!")
    }

    private fun registerEvents() {
        PokemonCatchHandler.register()
        PokemonDefeatHandler.register()
        PokemonEvolutionHandler.register()
        BattleWinHandler.register()
        DexHandler.register()
        PokemonTradeHandler.register()
    }
}
