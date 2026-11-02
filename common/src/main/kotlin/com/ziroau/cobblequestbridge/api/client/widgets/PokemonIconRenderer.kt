package com.ziroau.cobblequestbridge.api.client.widgets

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.client.gui.drawProfilePokemon
import com.cobblemon.mod.common.client.render.models.blockbench.FloatingState
import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.cobblemon.mod.common.util.math.fromEulerXYZDegrees
import com.teamresourceful.resourcefullib.client.scissor.ScissorBoxStack
import com.teamresourceful.resourcefullib.client.utils.RenderUtils
import com.ziroau.cobblequestbridge.api.data.Helpers.parseSpeciesId
import com.ziroau.iolaus.api.client.widgets.renderers.WidgetIconRenderer
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import org.joml.Quaternionf
import org.joml.Vector3f

/**
 * Cobblemon-specific plugin for Iolaus' WidgetIconRenderer slot.
 * Item quest icons still take priority, as they did in CQB.
 */
class PokemonIconRenderer private constructor(private val pokemon: RenderablePokemon) : WidgetIconRenderer {
    private val modelState = FloatingState()

    override fun render(
        context: DrawContext, scissor: ScissorBoxStack, x: Int, y: Int, size: Int
    ): Boolean {
        val matrices = context.matrices
        RenderUtils.createScissorBoxStack(
            scissor, MinecraftClient.getInstance(), matrices, x, y, size, size
        ).use {
            matrices.push()
            try {
                matrices.translate(x + size / 2.0, y - 4.0, 100.0)
                matrices.scale(4.0f, 4.0f, 1f)
                drawProfilePokemon(
                    renderablePokemon = pokemon,
                    matrixStack = matrices,
                    rotation = Quaternionf().fromEulerXYZDegrees(Vector3f(13f, 35f, 0f)),
                    state = modelState,
                    partialTicks = MinecraftClient.getInstance().renderTickCounter.getTickDelta(false),
                    scale = 4.5f
                )
            } finally {
                matrices.pop()
            }
        }
        return true
    }

    companion object {
        /** Returns null for blank, `any`, or unknown Pokémon species. */
        fun of(species: String, attributes: String = ""): PokemonIconRenderer? {
            val name = species.trim().lowercase()
            if (name.isBlank() || name == "any") return null
            val id = parseSpeciesId(name) ?: return null
            val pokemonSpecies = PokemonSpecies.getByIdentifier(id) ?: return null
            val props = PokemonProperties.parse(attributes)
            props.species = pokemonSpecies.resourceIdentifier.toString()
            props.updateAspects()
            return PokemonIconRenderer(RenderablePokemon(pokemonSpecies, props.aspects))
        }

        /** Handles species name + attributes: `pokemonIcon: "vaporeon shiny=yes"`. */
        fun parse(propertyString: String): PokemonIconRenderer? {
            val input = propertyString.trim().lowercase()
            if (input.isBlank() || input == "any") return null
            return of(input.substringBefore(" "), input.substringAfter(" ", "").trim())
        }

        /** A configured icon supersedes the task/reward species-based fallback, even if invalid. */
        fun preferred(override: String, species: String = "", attributes: String = ""): PokemonIconRenderer? =
            if (override.isNotEmpty()) parse(override) else of(species, attributes)
    }
}
