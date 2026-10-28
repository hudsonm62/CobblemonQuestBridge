package com.ziroau.cobblequestbridge.api.data

import com.cobblemon.mod.common.api.events.pokemon.TradeEvent
import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.server.network.ServerPlayerEntity

/**
 * Data Wrapper to correctly identify a player and their partner in task code.
 * Required due to how the [TradeEvent] is structured.
 *
 * @sample com.ziroau.cobblequestbridge.events.PokemonTradeHandler
 */
data class PokemonTradeEventWrapper(
    val player: ServerPlayerEntity,
    val partner: ServerPlayerEntity,
    val playerPokemon: Pokemon,
    val partnerPokemon: Pokemon,
    val tradeEvent: TradeEvent
)
