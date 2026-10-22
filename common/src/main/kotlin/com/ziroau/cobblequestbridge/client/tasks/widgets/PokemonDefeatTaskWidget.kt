package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent
import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonProgressTaskWidget
import com.ziroau.cobblequestbridge.tasks.PokemonDefeatTask
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class PokemonDefeatTaskWidget(
    override val task: PokemonDefeatTask,
    override val progress: TaskProgress<AbstractNbtNumber>
) : AbstractPokemonProgressTaskWidget<BattleFaintedEvent, AbstractNbtNumber, PokemonDefeatTask>(
    createPreviewPokemon(task.species, task.attributes)
) {
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
}
