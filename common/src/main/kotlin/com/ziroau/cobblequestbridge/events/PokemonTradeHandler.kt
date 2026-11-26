package com.ziroau.cobblequestbridge.events

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.ziroau.cobblequestbridge.api.data.Helpers.playerFromUUID
import com.ziroau.cobblequestbridge.api.data.PokemonTradeEventWrapper
import com.ziroau.cobblequestbridge.tasks.PokemonTradeTask
import earth.terrarium.heracles.common.handlers.progress.QuestProgressHandler
import net.minecraft.server.network.ServerPlayerEntity

object PokemonTradeHandler {
    fun register() {
        CobblemonEvents.TRADE_EVENT_POST.subscribe { tradeEvent ->
            val p1: ServerPlayerEntity = playerFromUUID(tradeEvent.tradeParticipant1.uuid) ?: return@subscribe
            val p2: ServerPlayerEntity = playerFromUUID(tradeEvent.tradeParticipant2.uuid) ?: return@subscribe
            p1.server.execute {
                PokemonTradeEventWrapper(
                    p1,
                    p2,
                    tradeEvent.tradeParticipant1Pokemon,
                    tradeEvent.tradeParticipant2Pokemon,
                    tradeEvent
                ).let { testTaskOnPlayer(p1, it) }

                PokemonTradeEventWrapper(
                    p2,
                    p1,
                    tradeEvent.tradeParticipant2Pokemon,
                    tradeEvent.tradeParticipant1Pokemon,
                    tradeEvent
                ).let { testTaskOnPlayer(p2, it) }
            }
        }
    }

    private fun testTaskOnPlayer(player: ServerPlayerEntity, input: PokemonTradeEventWrapper) {
        val progress = QuestProgressHandler.getProgress(
            player.server,
            player.uuid
        )
        progress.testAndProgressTaskType(
            player,
            input,
            PokemonTradeTask.TYPE
        )
    }
}
