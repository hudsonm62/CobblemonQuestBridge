package com.ziroau.cobblequestbridge.client.tasks.widgets

import com.cobblemon.mod.common.api.events.pokemon.PokedexDataChangedEvent
import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonProgressTaskWidget
import com.ziroau.cobblequestbridge.tasks.PokedexProgressTask
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.common.handlers.progress.TaskProgress
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class PokedexProgressTaskWidget(
    override val task: PokedexProgressTask,
    override val progress: TaskProgress<AbstractNbtNumber>
) : AbstractPokemonProgressTaskWidget<PokedexDataChangedEvent, AbstractNbtNumber, PokedexProgressTask>() {
    override val widgetItemIcon: QuestIcon<*>
        get() = task.icon()

    override val pokemonIcon: RenderablePokemon?
        get() = if(task.pokemonIcon().isNotEmpty())
            createPokemonIcon(task.pokemonIcon())
        else null

    private fun getTitleText(): Text {
        val translationKey = "tasks.cobblequestbridge.pokedex_progress.title.seen" + if (task.caughtOnly) ".caught" else ""
        val dexName = task.parseDexIdentifier()?.path ?: Identifier.of("cobblemon", "national").path
        val dexNameTranslated = Text.translatable("cobblemon.ui.pokedex.region.${dexName}").string
        return Text.translatable(translationKey, task.amount, dexNameTranslated)
    }
    private fun getDescriptionText(): Text {
        val translationKey = "tasks.cobblequestbridge.pokedex_progress.description.seen" + if (task.caughtOnly) ".caught" else ""
        val dexName = task.parseDexIdentifier()?.path ?: Identifier.of("cobblemon", "national").path
        val dexNameTranslated = Text.translatable("cobblemon.ui.pokedex.region.${dexName}").string
        return Text.translatable(translationKey, task.amount, dexNameTranslated)
    }

    override val widgetTitle: Text
        get() = task.titleOr(getTitleText())
    override val widgetDescription: Text
        get() = task.descriptionOr(getDescriptionText())
}
