package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonProgressTaskWidget
import com.ziroau.cobblequestbridge.tasks.PokemonEvolutionTask
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class PokemonEvolutionTaskWidget(
    override val task: PokemonEvolutionTask,
    override val progress: TaskProgress<AbstractNbtNumber>,
) : AbstractPokemonProgressTaskWidget<EvolutionCompleteEvent, AbstractNbtNumber, PokemonEvolutionTask>() {
    override val widgetItemIcon = task.icon()

    override val pokemonIcon = if(task.pokemonIcon().isNotEmpty())
        createPokemonIcon(task.pokemonIcon())
    else
        createPokemonIcon(task.toSpecies, task.toAttributes) // use target pokemon, makes more sense to show an "end result"

    override val widgetTitle: Text = task.titleOr(
        pluralizedTranslationText(
            task.titleOverride,
            task.amount,
            "tasks.cobblequestbridge.pokemon_evolution.title",
            getPokemonName(task.fromSpecies), getPokemonName(task.toSpecies)
        )
    )

    override val widgetDescription: Text = task.descriptionOr(
        pluralizedTranslationText(
            task.descriptionOverride,
            task.amount,
            "tasks.cobblequestbridge.pokemon_evolution.description",
            getPokemonName(task.fromSpecies), getPokemonName(task.toSpecies),
        )
    )
}