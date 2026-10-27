package com.ziroau.cobblequestbridge.api.data

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
}
