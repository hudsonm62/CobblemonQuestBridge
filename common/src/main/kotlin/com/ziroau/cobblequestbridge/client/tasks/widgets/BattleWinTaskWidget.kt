package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonProgressTaskWidget
import com.ziroau.cobblequestbridge.api.data.BattleTypeEnum
import com.ziroau.cobblequestbridge.api.data.BattleTypeEnum.Companion.alternateTranslation
import com.ziroau.cobblequestbridge.tasks.BattleWinTask
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class BattleWinTaskWidget(
    override val task: BattleWinTask,
    override val progress: TaskProgress<AbstractNbtNumber>
) : AbstractPokemonProgressTaskWidget<BattleVictoryEvent, AbstractNbtNumber, BattleWinTask>(
    createPreviewPokemon(task.previewPokemon)
) {
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

}
