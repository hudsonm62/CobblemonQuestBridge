package com.ziroau.cobblequestbridge.api.client.settings.base

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.ziroau.iolaus.api.client.settings.base.BaseAutocompleteSetting

/**
 * Provides a [BaseAutocompleteSetting] for Pokémon species.
 *
 * TODO: Look into a way to make this work with Cobblemon attributes???
 */
class PokemonSpeciesAutocompleteSetting(val allowAny: Boolean = false) : BaseAutocompleteSetting(allowAny) {
    override val defaultValue: String = if (allowAny) "any" else PokemonSpecies.species.first().resourceIdentifier.toString()
    override val suggestions: List<String> =
        PokemonSpecies.species.map { it.resourceIdentifier.toString() } + if (allowAny) listOf("any") else emptyList()

    companion object {
        @JvmField
        val WITH_ANY = PokemonSpeciesAutocompleteSetting(true)
        @JvmField
        val ONLY_REQUIRED = PokemonSpeciesAutocompleteSetting(false)
    }
}
