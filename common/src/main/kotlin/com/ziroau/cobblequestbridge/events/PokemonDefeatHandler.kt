package com.ziroau.cobblequestbridge.events

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.battles.actor.PlayerBattleActor
import com.ziroau.cobblequestbridge.tasks.PokemonDefeatTask
import earth.terrarium.heracles.common.handlers.progress.QuestProgressHandler
import net.minecraft.server.network.ServerPlayerEntity

object PokemonDefeatHandler {
    fun register() {
        CobblemonEvents.BATTLE_FAINTED.subscribe { event ->
            val defeatedBattlePkm = event.killed
            val sourceBattlePkm = event.context.origin ?: return@subscribe

            // Ignore self-KOs and friendly fire
            if (defeatedBattlePkm.actor.getSide() == sourceBattlePkm.actor.getSide()) return@subscribe

            // Only count KOs caused by a player's Pokémon
            val sourcePlayer: ServerPlayerEntity =
                (sourceBattlePkm.actor as? PlayerBattleActor)?.entity ?: return@subscribe

            // Extract the defeated Pokémon
            val defeatedPkm = defeatedBattlePkm.effectedPokemon

            sourcePlayer.server.execute {
                val progress = QuestProgressHandler.getProgress(
                    sourcePlayer.server,
                    sourcePlayer.uuid
                )

                progress.testAndProgressTaskType(
                    sourcePlayer,
                    defeatedPkm,
                    PokemonDefeatTask.TYPE
                )
            }
        }
    }
}
