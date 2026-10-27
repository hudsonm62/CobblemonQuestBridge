package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonProgressTaskWidget
import com.ziroau.cobblequestbridge.tasks.PokemonCatchTask
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class PokemonCatchTaskWidget(
    override val task: PokemonCatchTask,
    override val progress: TaskProgress<AbstractNbtNumber>
) : AbstractPokemonProgressTaskWidget<Pokemon, AbstractNbtNumber, PokemonCatchTask>() {
    override val widgetItemIcon: QuestIcon<*>
        get() = task.icon()

    override val pokemonIcon: RenderablePokemon?
        get() = if(task.pokemonIcon().isNotEmpty())
            createPokemonIcon(task.pokemonIcon())
        else
            createPokemonIcon(task.species, task.attributes)

    override val widgetTitle: Text
        get() = task.titleOr(
            pluralizedTranslationText(
                "",
                task.amount,
                "tasks.cobblequestbridge.pokemon_catch.title",
                getPokemonName(task.species)
            )
        )

    override val widgetDescription: Text
        get() = task.descriptionOr(
            pluralizedTranslationText(
                task.descriptionOverride,
                task.amount,
                "tasks.cobblequestbridge.pokemon_catch.description",
                getPokemonName(task.species)
            )
        )
}
