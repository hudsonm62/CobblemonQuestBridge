package com.ziroau.cobblequestbridge.tasks

import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER
import com.ziroau.iolaus.api.registry.TasksRegistry

object BridgeTasks {
    fun register() {
        TasksRegistry.registerAll(
            PokemonCatchTask.TYPE,
            PokemonDefeatTask.TYPE,
            PokemonEvolutionTask.TYPE,
            BattleWinTask.TYPE,
            PokedexProgressTask.TYPE,
            PokemonTradeTask.TYPE,
            FossilRestoreTask.TYPE
        )
        LOGGER.info("Registered {} CQB Quest Tasks", 7)
    }
}
