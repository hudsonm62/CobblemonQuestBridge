package com.ziroau.cobblequestbridge.events

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.battles.actor.PlayerBattleActor
import com.ziroau.cobblequestbridge.tasks.BattleWinTask
import earth.terrarium.heracles.common.handlers.progress.QuestProgressHandler

object BattleWinHandler {
    fun register() {
        CobblemonEvents.BATTLE_VICTORY.subscribe { event ->
            event.winners.filterIsInstance<PlayerBattleActor>().forEach { winner ->
                val winnerPlayer = winner.entity ?: return@forEach
                winnerPlayer.server.execute {
                    val progress = QuestProgressHandler.getProgress(winnerPlayer.server, winnerPlayer.uuid)
                    progress.testAndProgressTaskType(
                        winnerPlayer,
                        event,
                        BattleWinTask.TYPE
                    )
                }
            }
        }
    }
}
