package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent
import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonProgressTaskWidget
import com.ziroau.cobblequestbridge.tasks.PokemonDefeatTask
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class PokemonDefeatTaskWidget(
    override val task: PokemonDefeatTask,
    override val progress: TaskProgress<AbstractNbtNumber>
) : AbstractPokemonProgressTaskWidget<BattleFaintedEvent, AbstractNbtNumber, PokemonDefeatTask>() {
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
                task.appearance.titleOverride,
                task.amount,
                "tasks.cobblequestbridge.pokemon_defeat.title",
                getPokemonName(task.species)
            )
        )

    override val widgetDescription: Text
        get() = task.descriptionOr(
            pluralizedTranslationText(
                task.appearance.descriptionOverride,
                task.amount,
                "tasks.cobblequestbridge.pokemon_defeat.description",
                getPokemonName(task.species)
            )
        )
}
