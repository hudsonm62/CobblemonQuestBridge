package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.ziroau.cobblequestbridge.client.helpers.AbstractPokemonWidget
import com.ziroau.cobblequestbridge.client.helpers.CQBWidgetHelpers.createPreviewPokemon
import com.ziroau.cobblequestbridge.client.helpers.CQBWidgetHelpers.getPokemonName
import com.ziroau.cobblequestbridge.tasks.PokemonDefeatTask
import earth.terrarium.heracles.api.client.WidgetUtils
import earth.terrarium.heracles.api.client.theme.QuestScreenTheme
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.client.gui.DrawContext
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class PokemonDefeatTaskWidget(
    private val task: PokemonDefeatTask,
    private val progress: TaskProgress<AbstractNbtNumber>
) : AbstractPokemonWidget(
    createPreviewPokemon(task.species, task.properties)
) {

    override val titleColor: Int
        get() = QuestScreenTheme.getTaskTitle()

    override val descriptionColor: Int
        get() = QuestScreenTheme.getTaskDescription()

    override val widgetTitle: Text
        get() = pluralizedTranslationText(
            task.title,
            task.amount,
            getPokemonName(task.species),
            "tasks.cobblequestbridge.pokemon_defeat.title"
        )

    override val widgetDescription: Text
        get() = pluralizedTranslationText(
            task.description,
            task.amount,
            getPokemonName(task.species),
            "tasks.cobblequestbridge.pokemon_defeat.description"
        )

    /**
     * Render Progress Bar and Text
     */
    override fun renderExtra(
        graphics: DrawContext,
        x: Int, y: Int, width: Int,
        mouseX: Int, mouseY: Int
    ) {
        WidgetUtils.drawProgressText(
            graphics, x, y, width,
            task, progress
        )

        WidgetUtils.drawProgressBar(
            graphics,
            textX(x),
            y + getHeight(width) - font.fontHeight - 5,
            x + width - 5,
            y + getHeight(width) - 6,
            task, progress
        )
    }
}
