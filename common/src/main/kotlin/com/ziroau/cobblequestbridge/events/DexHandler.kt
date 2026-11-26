package com.ziroau.cobblequestbridge.events

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.ziroau.cobblequestbridge.api.data.Helpers.playerFromUUID
import com.ziroau.cobblequestbridge.tasks.PokedexProgressTask
import earth.terrarium.heracles.common.handlers.progress.QuestProgressHandler

object DexHandler {
    fun register() {
        CobblemonEvents.POKEDEX_DATA_CHANGED_POST.subscribe { event ->
            val player = playerFromUUID(event.playerUUID) ?: return@subscribe
            player.server.execute {
                val progress = QuestProgressHandler.getProgress(
                    player.server,
                    player.uuid
                )
                progress.testAndProgressTaskType(
                    player,
                    event,
                    PokedexProgressTask.TYPE
                )
            }
        }
    }
}
