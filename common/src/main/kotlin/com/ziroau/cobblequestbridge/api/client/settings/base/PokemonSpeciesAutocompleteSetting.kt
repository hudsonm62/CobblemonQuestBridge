package com.ziroau.cobblequestbridge.api.client.settings.base

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import earth.terrarium.heracles.api.client.settings.Setting
import earth.terrarium.heracles.api.client.settings.base.AutocompleteTextSetting
import earth.terrarium.olympus.client.components.textbox.autocomplete.AutocompleteTextBox

/**
 * Provides a [AutocompleteTextSetting] for Pokémon species,
 * with an option to allow "any" as a valid value for elements that don't require a specific species.
 */
object PokemonSpeciesAutocompleteSetting {
    /**
     * For Pokémon species that allows "`any`" as a valid value.
     */
    @JvmField
    val WITH_ANY: Setting<String, AutocompleteTextBox<String>> = create(allowAny = true)

    /**
     * For Pokémon species that requires an explicit species.
     */
    @JvmField
    val REQUIRED: Setting<String, AutocompleteTextBox<String>> = create(allowAny = false)

    private fun create(allowAny: Boolean): Setting<String, AutocompleteTextBox<String>> =
        object : Setting<String, AutocompleteTextBox<String>> {
            override fun createWidget(
                old: AutocompleteTextBox<String>?, width: Int, value: String
            ): AutocompleteTextBox<String> {
                val suggestions = buildList {
                    if (allowAny) add("any")
                    PokemonSpecies.species.forEach { species ->
                        add(species.resourceIdentifier.toString())
                    }
                }.distinct().sorted()

                return AutocompleteTextBox(
                    old, value, width, 24, suggestions,
                    { typed: String, candidate: String ->
                        candidate.contains(typed.trim(), ignoreCase = true) &&
                                !candidate.equals(typed.trim(), ignoreCase = true)
                    },
                    { candidate: String -> candidate }
                )
            }

            // Support free-form names, even with no match
            override fun getValue(widget: AutocompleteTextBox<String>): String = widget.rawValue.trim()
        }
}
