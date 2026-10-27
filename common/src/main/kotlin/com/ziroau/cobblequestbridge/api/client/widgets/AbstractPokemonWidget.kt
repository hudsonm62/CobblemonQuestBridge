package com.ziroau.cobblequestbridge.api.client.widgets

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.client.gui.drawProfilePokemon
import com.cobblemon.mod.common.client.render.models.blockbench.FloatingState
import com.cobblemon.mod.common.client.render.models.blockbench.PosableState
import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.cobblemon.mod.common.util.math.fromEulerXYZDegrees
import com.teamresourceful.resourcefullib.client.scissor.ScissorBoxStack
import com.teamresourceful.resourcefullib.client.utils.RenderUtils
import com.ziroau.cobblequestbridge.api.data.Helpers.parseSpeciesId
import earth.terrarium.heracles.api.client.DisplayWidget
import earth.terrarium.heracles.api.quests.QuestIcon
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import org.joml.Quaternionf
import org.joml.Vector3f

/**
 * Base class for most Pokémon-related widgets.
 * Provides logic for displaying a Pokémon icon, overridden title, and description.
 * @sample com.ziroau.cobblequestbridge.client.tasks.widgets.PokemonCatchTaskWidget
 */
abstract class AbstractPokemonWidget : DisplayWidget {
    companion object {
        private const val ICON_SIZE = 32
        private const val ROW_HEIGHT = 42
        private const val TEXT_OFFSET = ICON_SIZE + 16

        /**
         * Creates a preview [RenderablePokemon] based on the provided species and properties.
         *
         * @param species The species of the Pokémon (e.g., `pikachu`).
         * @param attributes The properties of the Pokémon in string format (i.e. "`shiny=yes gender=female`").
         * @return A [RenderablePokemon] if the species is valid, otherwise null/empty.
         */
        fun createPokemonIcon(species: String, attributes: String): RenderablePokemon? {
            val name = species.trim().lowercase()

            if (name.isBlank() || name == "any") return null

            val speciesId = parseSpeciesId(name) ?: return null

            // Get mon by species and parse props
            val species = PokemonSpecies.getByIdentifier(speciesId) ?: return null
            val props = PokemonProperties.parse(attributes)

            // Then set the species in the props and (re)apply aspects
            props.species = species.resourceIdentifier.toString()
            props.updateAspects()

            return RenderablePokemon(species, props.aspects)
        }
        fun createPokemonIcon(pokemon: String): RenderablePokemon? {
            val input = pokemon.trim().lowercase()
            if (input.isBlank() || input.equals("any", ignoreCase = true)) return null

            val speciesName = input.substringBefore(" ")
            val attributes = input.substringAfter(" ", "").trim()

            return createPokemonIcon(speciesName, attributes)
        }
    }

    private val modelState = FloatingState()
    protected val font: TextRenderer
        get() = MinecraftClient.getInstance().textRenderer

    protected abstract val pokemonIcon: RenderablePokemon?
    protected abstract val widgetItemIcon: QuestIcon<*>

    protected abstract val widgetTitle: Text
    protected abstract val widgetDescription: Text

    protected abstract val titleColor: Int
    protected abstract val descriptionColor: Int

    protected fun textX(x: Int) = x + TEXT_OFFSET

    override fun getHeight(width: Int) = ROW_HEIGHT

    final override fun render(
        graphics: DrawContext,
        scissor: ScissorBoxStack,
        x: Int, y: Int, width: Int,
        mouseX: Int, mouseY: Int,
        hovered: Boolean,
        partialTicks: Float
    ) {
        // Render the custom Heracles icon first.
        // AIR returns false, allowing the Pokémon preview to render.
        if (!widgetItemIcon.render(graphics, x + 5, y + 5, ICON_SIZE, ICON_SIZE)) {
            if (pokemonIcon != null) {
                renderPokemonIcon(
                    pokemonIcon, modelState,
                    graphics, scissor,
                    x + 5, y + 5,
                    ICON_SIZE, partialTicks
                )
            }
        }

        // Title
        graphics.drawText(
            font, widgetTitle,
            textX(x), y + 6,
            titleColor, false
        )

        // Description
        graphics.drawText(
            font, widgetDescription,
            textX(x), y + 8 + font.fontHeight,
            descriptionColor, false
        )

        // Subclass-specific rendering
        renderExtra(
            graphics, x, y, width, mouseX, mouseY
        )
    }

    /**
     * Render additional elements specific to the subclass (e.g., progress bars, buttons)
     */
    abstract fun renderExtra(graphics: DrawContext, x: Int, y: Int, width: Int, mouseX: Int, mouseY: Int)

    /**
     * Optional Helper function to get a pluralized translation text based on the passed in amount.
     * Used for tasks and rewards that can have more than 1 amount of xyz and need to display the correct translation for that.
     *
     * @sample com.ziroau.cobblequestbridge.client.tasks.widgets.PokemonCatchTaskWidget.widgetTitle
     */
    protected fun pluralizedTranslationText(
        custom: String?,
        amount: Int,
        translationKey: String,
        vararg args: Any
    ): Text = when {
        custom?.isNotEmpty() ?: false -> Text.literal(custom)
        amount > 1 -> Text.translatable(
            "$translationKey.plural", amount, *args
        )
        else -> Text.translatable(translationKey, *args)
    }

    /**
     * Returns the translated name of a Pokémon species based on the provided string directly from Cobblemon
     *
     * If species is `any`, it simply returns "`Pokémon`".
     * If the species is not found, it returns the capitalized version of the input string.
     *
     * @param species of the Pokémon (e.g., `pikachu`).
     * @return The translated name of the Pokémon species, or a fallback string if not found.
     */
    protected fun getPokemonName(species: String): String {
        val species = species.trim().lowercase()
        if (species.equals("any", ignoreCase = true)) return "Pokémon"

        return parseSpeciesId(species)
            ?.let { PokemonSpecies.getByIdentifier(it) }
            ?.translatedName?.string
            ?: species.replaceFirstChar { it.uppercase() }
    }

    /**
     * Creates a [RenderablePokemon] preview on the widget.
     * Uses a [ScissorBoxStack] to ensure that the model is rendered within the specified bounds.
     *
     * @param pokemon The [RenderablePokemon] to render - Usually from [createPokemonIcon].
     * @param modelState The [PosableState] of the model.
     * @param graphics The [DrawContext] to draw on.
     * @param scissor The [ScissorBoxStack] to use for scissoring.
     * @param x coordinate of the top-left corner.
     * @param y coordinate of the top-left corner.
     * @param size of the preview.
     * @param partialTicks for rendering.
     */
    protected fun renderPokemonIcon(
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
            // TODO: look into a dynamic solution for better visibility for smaller & ginormous mons
            try {
                // Model Translation and scale to icon size
                matrices.translate(x + size / 2.0, y - 4.0, 100.0)
                matrices.scale(4.0F, 4.0F, 1F)

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
