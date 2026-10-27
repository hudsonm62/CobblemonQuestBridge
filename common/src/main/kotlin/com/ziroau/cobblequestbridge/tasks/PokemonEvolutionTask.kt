package com.ziroau.cobblequestbridge.tasks

import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import com.ziroau.cobblequestbridge.api.data.Helpers.attributeMatcher
import com.ziroau.cobblequestbridge.api.data.Helpers.parseSpeciesId
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.api.quests.QuestIcons
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon
import earth.terrarium.heracles.api.tasks.QuestTask
import earth.terrarium.heracles.api.tasks.QuestTaskType
import earth.terrarium.heracles.api.tasks.storage.defaults.IntegerTaskStorage
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.util.Identifier

class PokemonEvolutionTask(
    private val taskId: String,
    val fromSpecies: String = "any",
    val fromAttributes: String = "",
    val toSpecies: String = "any",
    val toAttributes: String = "",
    val minLevel: Int = 1,
    val amount: Int = 1,
    val titleOverride: String,
    val descriptionOverride: String,
    val pokemonIconOverride: String,
    val iconOverride: QuestIcon<*> = ItemQuestIcon.AIR
) : QuestTask<EvolutionCompleteEvent, AbstractNbtNumber, PokemonEvolutionTask>, CustomizableQuestElement {
    override fun id(): String = taskId

    override fun test(
        type: QuestTaskType<*>,
        progress: AbstractNbtNumber,
        input: EvolutionCompleteEvent
    ): AbstractNbtNumber {
        val fromMon = input.sourcePokemon
        val toMon = input.pokemon

        val matchesFromSpecies: Boolean =
            fromSpecies == "any" || fromMon.species.resourceIdentifier == parseSpeciesId(fromSpecies)
        val matchesFromAttributes: Boolean = attributeMatcher(fromAttributes).matches(fromMon)

        val matchesToSpecies: Boolean =
            toSpecies == "any" || toMon.species.resourceIdentifier == parseSpeciesId(toSpecies)
        val matchesToAttributes: Boolean = attributeMatcher(toAttributes).matches(toMon)

        // validate against criteria
        if(
            !matchesFromSpecies ||
            !matchesFromAttributes ||
            !matchesToSpecies ||
            !matchesToAttributes ||
            fromMon.level < minLevel
            ) return progress

        // Increment by 1, capped at the required amount
        val current = storage().readInt(progress)
        val target = amount.coerceAtLeast(1)
        return storage().set(
            (current + 1).coerceAtMost(target)
        )
    }

    override fun getProgress(progress: AbstractNbtNumber): Float {
        val current = storage().readInt(progress)
        return (current.toFloat() / amount.coerceAtLeast(1))
            .coerceIn(0f, 1f)
    }

    override fun storage(): IntegerTaskStorage = IntegerTaskStorage.INSTANCE
    override fun type(): QuestTaskType<PokemonEvolutionTask> = TYPE

    override fun title(): String = titleOverride
    override fun description(): String = descriptionOverride
    override fun pokemonIcon(): String = pokemonIconOverride
    override fun icon(): QuestIcon<*> = iconOverride

    companion object {
        @JvmField
        val TYPE: QuestTaskType<PokemonEvolutionTask> = Type()
    }

    private class Type : QuestTaskType<PokemonEvolutionTask> {
        override fun id(): Identifier =
            Identifier.of(
                "cobblequestbridge", "pokemon_evolution"
            )

        override fun codec(id: String): MapCodec<PokemonEvolutionTask> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    RecordCodecBuilder.point<PokemonEvolutionTask, String>(id),

                    Codec.STRING
                        .optionalFieldOf("fromSpecies", "any")
                        .forGetter(PokemonEvolutionTask::fromSpecies),

                    Codec.STRING
                        .optionalFieldOf("fromAttributes", "")
                        .forGetter(PokemonEvolutionTask::fromAttributes),

                    Codec.STRING
                        .optionalFieldOf("toSpecies", "any")
                        .forGetter(PokemonEvolutionTask::toSpecies),

                    Codec.STRING
                        .optionalFieldOf("toAttributes", "")
                        .forGetter(PokemonEvolutionTask::toAttributes),

                    Codec.INT
                        .optionalFieldOf("minLevel", 1)
                        .forGetter(PokemonEvolutionTask::minLevel),

                    Codec.INT
                        .optionalFieldOf("amount", 1)
                        .forGetter(PokemonEvolutionTask::amount),

                    Codec.STRING
                        .optionalFieldOf("title", "")
                        .forGetter(PokemonEvolutionTask::titleOverride),

                    Codec.STRING
                        .optionalFieldOf("description", "")
                        .forGetter(PokemonEvolutionTask::descriptionOverride),

                    Codec.STRING
                        .optionalFieldOf("pokemonIcon", "")
                        .forGetter(PokemonEvolutionTask::pokemonIconOverride),

                    QuestIcons.CODEC
                        .optionalFieldOf("icon", ItemQuestIcon.AIR)
                        .forGetter(PokemonEvolutionTask::iconOverride)

                ).apply(instance) {
                    id, fromSpecies, fromAttributes, toSpecies, toAttributes, minLevel, amount, title, description, pokemonIcon, icon ->
                    PokemonEvolutionTask(id, fromSpecies, fromAttributes, toSpecies, toAttributes, minLevel, amount, title, description, pokemonIcon, icon)
                }
            }
    }
}
