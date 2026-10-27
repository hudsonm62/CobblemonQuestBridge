package com.ziroau.cobblequestbridge.events

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.ziroau.cobblequestbridge.tasks.PokemonEvolutionTask
import earth.terrarium.heracles.common.handlers.progress.QuestProgressHandler

object PokemonEvolutionHandler {
    fun register() {
        CobblemonEvents.EVOLUTION_COMPLETE.subscribe { event ->
            val sourcePlayer = event.pokemon.getOwnerPlayer() ?: return@subscribe
            sourcePlayer.server.execute {
                val progress = QuestProgressHandler.getProgress(
                    sourcePlayer.server,
                    sourcePlayer.uuid
                )

                progress.testAndProgressTaskType(
                    sourcePlayer,
                    event,
                    PokemonEvolutionTask.TYPE
                )
            }
        }
    }
}
