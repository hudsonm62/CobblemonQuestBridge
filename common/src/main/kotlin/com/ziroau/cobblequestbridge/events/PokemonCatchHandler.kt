package com.ziroau.cobblequestbridge.events

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.ziroau.cobblequestbridge.tasks.PokemonCatchTask
import earth.terrarium.heracles.common.handlers.progress.QuestProgressHandler

object PokemonCatchHandler {
    fun register() {
        CobblemonEvents.POKEMON_CAPTURED.subscribe { event ->
            val player = event.player
            val pokemon = event.pokemon
            player.server.execute {
                val progress = QuestProgressHandler.getProgress(player.server, player.uuid)
                progress.testAndProgressTaskType(
                    player,
                    pokemon,
                    PokemonCatchTask.TYPE
                )
            }
        }
    }
}
