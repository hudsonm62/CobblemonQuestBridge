package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.cobblemon.mod.common.pokemon.Pokemon
import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonProgressTaskWidget
import com.ziroau.cobblequestbridge.tasks.PokemonCatchTask
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class PokemonCatchTaskWidget(
    override val task: PokemonCatchTask,
    override val progress: TaskProgress<AbstractNbtNumber>
) : AbstractPokemonProgressTaskWidget<Pokemon, AbstractNbtNumber, PokemonCatchTask>(
    createPreviewPokemon(task.species, task.attributes)
) {
    override val widgetTitle: Text
        get() = pluralizedTranslationText(
            task.title,
            task.amount,
            getPokemonName(task.species),
            "tasks.cobblequestbridge.pokemon_catch.title"
        )

    override val widgetDescription: Text
        get() = pluralizedTranslationText(
            task.description,
            task.amount,
            getPokemonName(task.species),
            "tasks.cobblequestbridge.pokemon_catch.description"
        )
}
