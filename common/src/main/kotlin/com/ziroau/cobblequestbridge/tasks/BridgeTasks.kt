package com.ziroau.cobblequestbridge.tasks

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
    }
}
