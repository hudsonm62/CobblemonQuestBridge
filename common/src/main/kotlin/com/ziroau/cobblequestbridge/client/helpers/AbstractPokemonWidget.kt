package com.ziroau.cobblequestbridge.client.helpers

import com.cobblemon.mod.common.client.render.models.blockbench.FloatingState
import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.teamresourceful.resourcefullib.client.scissor.ScissorBoxStack
import com.ziroau.cobblequestbridge.client.helpers.CQBWidgetHelpers.renderPokemonIcon
import earth.terrarium.heracles.api.client.DisplayWidget
import earth.terrarium.heracles.api.client.WidgetUtils
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext
import net.minecraft.item.Items
import net.minecraft.text.Text

/**
 * Base class for most Pokémon-related widgets.
 * Provides logic for displaying a Pokémon icon, title, and description.
 *
 * @param previewPokemon The [RenderablePokemon] to preview in the widget, or null if none is available.
 * @sample com.ziroau.cobblequestbridge.client.tasks.widgets.PokemonCatchTaskWidget
 */
abstract class AbstractPokemonWidget(
    private val previewPokemon: RenderablePokemon?
) : DisplayWidget {
    companion object {
        private const val ICON_SIZE = 32
        private const val ROW_HEIGHT = 42
        private const val TEXT_OFFSET = ICON_SIZE + 16
    }

    private val modelState = FloatingState()
    protected val font: TextRenderer
        get() = MinecraftClient.getInstance().textRenderer

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
        // Pokémon preview
        /// TODO: Optional Icon/Pokemon override
        if (previewPokemon != null) {
            renderPokemonIcon(
                previewPokemon, modelState,
                graphics, scissor,
                x + 5, y + 5,
                ICON_SIZE, partialTicks
            )
        } else {
            WidgetUtils.drawItemIcon(
                graphics, null,
                x + 5, y + 5, ICON_SIZE
            )
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
     * Render additional elements specific to the subclass (e.g., progress bars, buttons).
     */
    abstract fun renderExtra(graphics: DrawContext, x: Int, y: Int, width: Int, mouseX: Int, mouseY: Int)

    /**
     * Helper function to get a pluralized translation text based on the passed in amount.
     * Used for tasks and rewards that can have more than 1 amount of xyz and need to display the correct translation for that.
     */
    protected fun pluralizedTranslationText(
        custom: String?,
        amount: Int,
        name: Any,
        translationKey: String
    ): Text = when {
        custom?.isNotEmpty() ?: false -> Text.literal(custom)
        amount > 1 -> Text.translatable(
            "$translationKey.plural", amount, name
        )
        else -> Text.translatable(translationKey, name)
    }
}
