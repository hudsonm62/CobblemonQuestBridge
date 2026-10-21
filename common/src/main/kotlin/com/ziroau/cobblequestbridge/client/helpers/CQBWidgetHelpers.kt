package com.ziroau.cobblequestbridge.client.helpers

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.client.gui.drawProfilePokemon
import com.cobblemon.mod.common.client.render.models.blockbench.PosableState
import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.cobblemon.mod.common.util.math.fromEulerXYZDegrees
import com.teamresourceful.resourcefullib.client.scissor.ScissorBoxStack
import com.teamresourceful.resourcefullib.client.utils.RenderUtils
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Identifier
import org.joml.Quaternionf
import org.joml.Vector3f

/**
 * Helpers for creating Task & Reward Widgets in CobbleQuestBridge.
 */
object CQBWidgetHelpers {
    /**
     * Creates a preview [RenderablePokemon] based on the provided species and properties.
     *
     * @param species The species of the Pokémon (e.g., `pikachu`).
     * @param properties The properties of the Pokémon in string format (i.e. "`shiny=yes gender=female`").
     * @return A [RenderablePokemon] if the species is valid, otherwise null/empty.
     */
    fun createPreviewPokemon(species: String, properties: String): RenderablePokemon? {
        val name = species.trim().lowercase()

        if (name.isBlank() || name == "any") return null

        val speciesId = Identifier.tryParse(
            if ("cobblemon:" in name) name else "cobblemon:$name"
        ) ?: return null

        // Get mon by species and parse props
        val species = PokemonSpecies.getByIdentifier(speciesId) ?: return null
        val props = PokemonProperties.parse(properties)

        // Then set the species in the props and (re)apply aspects
        props.species = species.resourceIdentifier.toString()
        props.updateAspects()

        return RenderablePokemon(species, props.aspects)
    }

    /**
     * Returns the translated name of a Pokémon species based on the provided string
     * If species is `any`, it simply returns "Pokémon" -- If the species is not found, it returns the capitalized version of the input string.
     *
     * @param species of the Pokémon (e.g., `pikachu`).
     * @return The translated name of the Pokémon species, or a fallback string if not found.
     */
    fun getPokemonName(species: String): String {
        if (species.equals("any", ignoreCase = true)) return "Pokémon"

        val speciesId = Identifier.tryParse(
            if (":" in species) {
                species
            } else {
                "cobblemon:$species"
            }
        )

        return speciesId
            ?.let { PokemonSpecies.getByIdentifier(it) }
            ?.translatedName?.string
            ?: species.replaceFirstChar { it.uppercase() }
    }


    /**
     * Creates a [RenderablePokemon] preview on the widget.
     * Uses a [ScissorBoxStack] to ensure that the model is rendered within the specified bounds.
     *
     * @param pokemon The [RenderablePokemon] to render - Usually from [createPreviewPokemon].
     * @param modelState The [PosableState] of the model.
     * @param graphics The [DrawContext] to draw on.
     * @param scissor The [ScissorBoxStack] to use for scissoring.
     * @param x coordinate of the top-left corner.
     * @param y coordinate of the top-left corner.
     * @param size of the preview.
     * @param partialTicks for rendering.
     */
    fun renderPokemonIcon(
        pokemon: RenderablePokemon?, modelState: PosableState,
        graphics: DrawContext, scissor: ScissorBoxStack,
        x: Int, y: Int, size: Int,
        partialTicks: Float
    ) {
        val pokemon = pokemon ?: return
        val matrices = graphics.matrices

        // Uses Heracles' scissor stack
        RenderUtils.createScissorBoxStack(
            scissor,
            MinecraftClient.getInstance(),
            matrices,
            x, y,
            size, size
        ).use {
            matrices.push()
            try {
                matrices.translate(
                    x + size / 2.0,
                    y + 1.0,
                    100.0
                )

                // Scale the model to fit within the icon size
                // TODO: Need to play with the scaling more
                matrices.scale(2.6F, 2.6F, 1F)

                // Render the pokemon model with a slight rotation for visibility
                drawProfilePokemon(
                    renderablePokemon = pokemon,
                    matrixStack = matrices,
                    rotation = Quaternionf()
                        .fromEulerXYZDegrees(
                            Vector3f(13F, 35F, 0F)
                        ),
                    state = modelState,
                    partialTicks = partialTicks,
                    scale = 4.5F
                )
            } finally {
                matrices.pop()
            }
        }
    }
}
