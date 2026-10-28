package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonProgressTaskWidget
import com.ziroau.cobblequestbridge.api.data.PokemonTradeEventWrapper
import com.ziroau.cobblequestbridge.tasks.PokemonTradeTask
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class PokemonTradeTaskWidget(
    override val task: PokemonTradeTask,
    override val progress: TaskProgress<AbstractNbtNumber>,
) : AbstractPokemonProgressTaskWidget<PokemonTradeEventWrapper, AbstractNbtNumber, PokemonTradeTask>() {
    override val widgetItemIcon = task.icon()

    override val pokemonIcon = if(task.pokemonIcon().isNotEmpty())
        createPokemonIcon(task.pokemonIcon())
    else
        createPokemonIcon(task.speciesReceived, task.attributesReceived) // use target pokemon, makes more sense to show an "end result"

    override val widgetTitle: Text = task.titleOr(getThisText("title", task.appearance.titleOverride))
    override val widgetDescription: Text = task.descriptionOr(getThisText("description", task.appearance.descriptionOverride))

    private fun getThisText(type: String, override: String): Text {
        return when {
            task.speciesSent == "any" && task.speciesReceived == "any" -> pluralizedTranslationText(
                override,
                task.amount,
                "tasks.cobblequestbridge.pokemon_trade.$type.any_to_any"
            )
            task.speciesSent != "any" && task.speciesReceived == "any" -> pluralizedTranslationText(
                override,
                task.amount,
                "tasks.cobblequestbridge.pokemon_trade.$type.specific_to_any",
                getPokemonName(task.speciesSent)
            )
            task.speciesSent == "any" && task.speciesReceived != "any" -> pluralizedTranslationText(
                override,
                task.amount,
                "tasks.cobblequestbridge.pokemon_trade.$type.any_to_specific",
                getPokemonName(task.speciesReceived)
            )
            else -> pluralizedTranslationText(
                override,
                task.amount,
                "tasks.cobblequestbridge.pokemon_trade.$type.specific_to_specific",
                getPokemonName(task.speciesSent), getPokemonName(task.speciesReceived)
            )
        }
    }
}
