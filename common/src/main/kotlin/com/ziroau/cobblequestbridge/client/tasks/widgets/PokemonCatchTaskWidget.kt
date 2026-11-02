package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.cobblemon.mod.common.pokemon.Pokemon
import com.ziroau.cobblequestbridge.api.client.widgets.PokemonIconRenderer
import com.ziroau.cobblequestbridge.api.client.widgets.PokemonWidgetText.getPokemonName
import com.ziroau.cobblequestbridge.api.client.widgets.PokemonWidgetText.pluralizedTranslationText
import com.ziroau.cobblequestbridge.tasks.PokemonCatchTask
import com.ziroau.iolaus.api.client.widgets.AbstractProgressTaskWidget
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class PokemonCatchTaskWidget(
    override val task: PokemonCatchTask,
    override val progress: TaskProgress<AbstractNbtNumber>
) : AbstractProgressTaskWidget<Pokemon, AbstractNbtNumber, PokemonCatchTask>() {
    override val widgetItemIcon: QuestIcon<*>
        get() = task.icon()

    override val iconRenderer = PokemonIconRenderer.preferred(task.pokemonIcon())

    override val widgetTitle: Text
        get() = task.titleOr(
            pluralizedTranslationText(
                task.display.titleOverride,
                task.amount,
                "tasks.cobblequestbridge.pokemon_catch.title",
                getPokemonName(task.species)
            )
        )

    override val widgetDescription: Text
        get() = task.descriptionOr(
            pluralizedTranslationText(
                task.display.descriptionOverride,
                task.amount,
                "tasks.cobblequestbridge.pokemon_catch.description",
                getPokemonName(task.species)
            )
        )
}
