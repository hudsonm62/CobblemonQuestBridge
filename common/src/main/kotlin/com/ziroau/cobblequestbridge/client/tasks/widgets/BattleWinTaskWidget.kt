package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.ziroau.cobblequestbridge.client.helpers.AbstractPokemonWidget
import com.ziroau.cobblequestbridge.client.helpers.CQBWidgetHelpers.createPreviewPokemon
import com.ziroau.cobblequestbridge.data.BattleTypeEnum
import com.ziroau.cobblequestbridge.data.BattleTypeEnum.Companion.alternateTranslation
import com.ziroau.cobblequestbridge.tasks.BattleWinTask
import earth.terrarium.heracles.api.client.WidgetUtils
import earth.terrarium.heracles.api.client.theme.QuestScreenTheme
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.client.gui.DrawContext
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class BattleWinTaskWidget(
    private val task: BattleWinTask,
    private val progress: TaskProgress<AbstractNbtNumber>
) : AbstractPokemonWidget(
    createPreviewPokemon(task.previewPokemon)
) {
    override val titleColor: Int
        get() = QuestScreenTheme.getTaskTitle()
    override val descriptionColor: Int
        get() = QuestScreenTheme.getTaskDescription()

    private fun battleWinText(custom: String?, type: String): Text {
        if (!custom.isNullOrEmpty()) return Text.literal(custom)

        val isAny = task.battleType == BattleTypeEnum.ANY
        val isPlural = task.amount > 1

        val key = buildString {
            append("tasks.cobblequestbridge.battle_win.$type")
            if (isAny) append(".any")
            if (isPlural) append(".plural")
        }

        return when {
            isAny && isPlural -> Text.translatable(key, task.amount)
            isAny -> Text.translatable(key)
            isPlural -> Text.translatable(key, task.amount, task.battleType.alternateTranslation())
            else -> Text.translatable(key, task.battleType.alternateTranslation())
        }
    }

    override val widgetTitle: Text
        get() = battleWinText(task.title, "title")

    override val widgetDescription: Text
        get() = battleWinText(task.description, "description")

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
