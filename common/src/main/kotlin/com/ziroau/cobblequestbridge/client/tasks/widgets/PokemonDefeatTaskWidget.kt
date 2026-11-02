package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent
import com.ziroau.cobblequestbridge.api.client.widgets.PokemonIconRenderer
import com.ziroau.cobblequestbridge.api.client.widgets.PokemonWidgetText.getPokemonName
import com.ziroau.cobblequestbridge.api.client.widgets.PokemonWidgetText.pluralizedTranslationText
import com.ziroau.cobblequestbridge.tasks.PokemonDefeatTask
import com.ziroau.iolaus.api.client.widgets.AbstractProgressTaskWidget
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class PokemonDefeatTaskWidget(
    override val task: PokemonDefeatTask,
    override val progress: TaskProgress<AbstractNbtNumber>
) : AbstractProgressTaskWidget<BattleFaintedEvent, AbstractNbtNumber, PokemonDefeatTask>() {
    override val widgetItemIcon: QuestIcon<*>
        get() = task.icon()

    override val iconRenderer = PokemonIconRenderer.preferred(task.pokemonIcon(), task.species, task.attributes)

    override val widgetTitle: Text
        get() = task.titleOr(
            pluralizedTranslationText(
                task.display.titleOverride,
                task.amount,
                "tasks.cobblequestbridge.pokemon_defeat.title",
                getPokemonName(task.species)
            )
        )

    override val widgetDescription: Text
        get() = task.descriptionOr(
            pluralizedTranslationText(
                task.display.descriptionOverride,
                task.amount,
                "tasks.cobblequestbridge.pokemon_defeat.description",
                getPokemonName(task.species)
            )
        )
}
