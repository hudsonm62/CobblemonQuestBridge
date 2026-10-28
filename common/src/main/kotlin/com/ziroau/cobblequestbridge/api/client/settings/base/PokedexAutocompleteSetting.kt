package com.ziroau.cobblequestbridge.api.client.settings.base

import com.cobblemon.mod.common.api.pokedex.Dexes
import earth.terrarium.heracles.api.client.settings.Setting
import earth.terrarium.olympus.client.components.textbox.autocomplete.AutocompleteTextBox

/**
 * Provides a [AutocompleteTextBox] Setting for Pokédex Regions
 */
object PokedexAutocompleteSetting : Setting<String, AutocompleteTextBox<String>> {
        override fun createWidget(
            old: AutocompleteTextBox<String>?, width: Int, value: String
        ): AutocompleteTextBox<String> {
            val suggestions = buildList {
                Dexes.dexEntryMap.keys.forEach { dex ->
                    add(dex.toString())
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

    override fun getValue(widget: AutocompleteTextBox<String>?): String = widget?.value() ?: "cobblemon:national"
}
