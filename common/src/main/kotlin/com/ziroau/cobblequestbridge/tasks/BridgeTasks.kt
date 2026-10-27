package com.ziroau.cobblequestbridge.tasks

import com.ziroau.cobblequestbridge.api.registry.BridgeTasksRegistry

object BridgeTasks {
    fun register() {
        BridgeTasksRegistry.registerAll(
            PokemonCatchTask.TYPE,
            PokemonDefeatTask.TYPE,
            PokemonEvolutionTask.TYPE,
            BattleWinTask.TYPE
        )
    }
}
