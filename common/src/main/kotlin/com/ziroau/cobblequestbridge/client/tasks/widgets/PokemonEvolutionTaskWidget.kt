package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import com.ziroau.cobblequestbridge.api.client.widgets.PokemonIconRenderer
import com.ziroau.cobblequestbridge.api.client.widgets.PokemonWidgetText.getPokemonName
import com.ziroau.cobblequestbridge.api.client.widgets.PokemonWidgetText.pluralizedTranslationText
import com.ziroau.cobblequestbridge.tasks.PokemonEvolutionTask
import com.ziroau.iolaus.api.client.widgets.AbstractProgressTaskWidget
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class PokemonEvolutionTaskWidget(
    override val task: PokemonEvolutionTask,
    override val progress: TaskProgress<AbstractNbtNumber>,
) : AbstractProgressTaskWidget<EvolutionCompleteEvent, AbstractNbtNumber, PokemonEvolutionTask>() {
    override val widgetItemIcon = task.icon()

    override val iconRenderer = PokemonIconRenderer.preferred(
        task.pokemonIcon(), task.toSpecies, task.toAttributes
    )

    override val widgetTitle: Text = task.titleOr(
        pluralizedTranslationText(
            task.display.titleOverride,
            task.amount,
            "tasks.cobblequestbridge.pokemon_evolution.title",
            getPokemonName(task.fromSpecies), getPokemonName(task.toSpecies)
        )
    )

    override val widgetDescription: Text = task.descriptionOr(
        pluralizedTranslationText(
            task.display.descriptionOverride,
            task.amount,
            "tasks.cobblequestbridge.pokemon_evolution.description",
            getPokemonName(task.fromSpecies), getPokemonName(task.toSpecies),
        )
    )
}
