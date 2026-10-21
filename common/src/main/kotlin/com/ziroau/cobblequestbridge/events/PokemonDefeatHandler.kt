package com.ziroau.cobblequestbridge.events

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.battles.actor.PlayerBattleActor
import com.ziroau.cobblequestbridge.tasks.PokemonDefeatTask
import earth.terrarium.heracles.common.handlers.progress.QuestProgressHandler
import net.minecraft.server.network.ServerPlayerEntity

object PokemonDefeatHandler {
    fun register() {
        CobblemonEvents.BATTLE_FAINTED.subscribe { event ->
            val sourcePlayer: ServerPlayerEntity =
                (event.context.origin?.actor as? PlayerBattleActor)?.entity ?: return@subscribe

            sourcePlayer.server.execute {
                val progress = QuestProgressHandler.getProgress(
                    sourcePlayer.server,
                    sourcePlayer.uuid
                )

                // Let [PokemonDefeatTask] handle the rest
                progress.testAndProgressTaskType(
                    sourcePlayer,
                    event,
                    PokemonDefeatTask.TYPE
                )
            }
        }
    }
}
