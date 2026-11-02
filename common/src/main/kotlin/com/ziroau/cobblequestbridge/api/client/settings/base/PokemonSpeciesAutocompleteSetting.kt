package com.ziroau.cobblequestbridge.api.client.settings.base

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.ziroau.iolaus.api.client.settings.base.BaseAutocompleteSetting
import earth.terrarium.olympus.client.components.textbox.autocomplete.AutocompleteTextBox

/**
 * Provides a [AutocompleteTextBox] for Pokémon species,
 * with an option to allow "any" as a valid value for elements that don't require a specific species.
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
