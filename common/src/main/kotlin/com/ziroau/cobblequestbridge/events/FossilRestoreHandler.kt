package com.ziroau.cobblequestbridge.events

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.ziroau.cobblequestbridge.tasks.FossilRestoreTask
import earth.terrarium.heracles.common.handlers.progress.QuestProgressHandler

object FossilRestoreHandler {
    fun register() {
        CobblemonEvents.FOSSIL_REVIVED.subscribe { event ->
            val player = event.player ?: return@subscribe
            player.server.execute {
                val progress = QuestProgressHandler.getProgress(
                    player.server,
                    player.uuid
                )

                progress.testAndProgressTaskType(
                    player,
                    event,
                    FossilRestoreTask.TYPE
                )
            }
        }
    }
}
