package com.ziroau.cobblequestbridge.api.data

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import net.minecraft.util.Identifier

object Helpers {
    /**
     * Tries to parse a Cobblemon Species string into an [Identifier].
     * If the species string does not contain a namespace (i.e., no colon), it defaults to the "cobblemon" namespace.
     *
     * @param species The species string to parse (e.g., "pikachu", "cobblemon:pikachu", "custom:pikachu").
     * @return An [Identifier] representing the species, or null if the input is invalid.
     */
    fun parseSpeciesId(species: String): Identifier? {
        val species = species.trim().lowercase()
        return Identifier.tryParse(
            if (":" in species) {
                species
            } else {
                "cobblemon:$species"
            }
        )
    }

    /**
     * Parses a string of Pokémon attributes into a [PokemonProperties] object.
     * The input string should be in the Cobblemon Attributes format (e.g., "`gender=male shiny=true`").
     *
     * @param attributes The attributes string to parse (gets trimmed and lowercased).
     * @return A [PokemonProperties] object representing the parsed attributes, which you can use `matches()` on to check against a Pokémon.
     */
    fun attributeMatcher(attributes: String): PokemonProperties = PokemonProperties.parse(attributes.trim().lowercase())
}
