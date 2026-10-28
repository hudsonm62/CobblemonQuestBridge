package com.ziroau.cobblequestbridge.api.data

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.util.server
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import java.util.*

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

    /**
     * Attempts to retrieve a [ServerPlayerEntity] from the server using the provided UUID string.
     * Uses Cobblemon's [server] utility function internally to access the [net.minecraft.server.MinecraftServer] instance.
     *
     * @param uuid The UUID string of the player to retrieve.
     * @return The [ServerPlayerEntity] if found, or null
     */
    fun playerFromUUID(uuid: UUID): ServerPlayerEntity? = server()?.playerManager?.getPlayer(uuid)
    fun playerFromUUID(uuid: String): ServerPlayerEntity? = server()?.playerManager?.getPlayer(UUID.fromString(uuid))
}
