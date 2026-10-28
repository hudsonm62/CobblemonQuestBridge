package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.cobblemon.mod.common.api.events.pokemon.FossilRevivedEvent
import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonProgressTaskWidget
import com.ziroau.cobblequestbridge.tasks.FossilRestoreTask
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text

class FossilRestoreTaskWidget(
    override val task: FossilRestoreTask,
    override val progress: TaskProgress<AbstractNbtNumber>,
) : AbstractPokemonProgressTaskWidget<FossilRevivedEvent, AbstractNbtNumber, FossilRestoreTask>() {
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
                "tasks.cobblequestbridge.fossil_restore.title",
                getPokemonName(task.species)
            )
        )

    override val widgetDescription: Text
        get() = task.descriptionOr(
            pluralizedTranslationText(
                task.appearance.descriptionOverride,
                task.amount,
                "tasks.cobblequestbridge.fossil_restore.description",
                getPokemonName(task.species)
            )
        )
}
