package com.ziroau.cobblequestbridge.tasks

import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.api.data.Helpers.attributeMatcher
import com.ziroau.cobblequestbridge.api.data.Helpers.parseSpeciesId
import com.ziroau.cobblequestbridge.api.data.QuestAppearance
import com.ziroau.cobblequestbridge.api.tasks.CountedQuestTask
import earth.terrarium.heracles.api.tasks.QuestTaskType
import net.minecraft.util.Identifier

class PokemonEvolutionTask(
    override val taskId: String,
    val fromSpecies: String = "any",
    val fromAttributes: String = "",
    val toSpecies: String = "any",
    val toAttributes: String = "",
    val minLevel: Int = 1,
    override val amount: Int = 1,
    override val appearance: QuestAppearance
) : CountedQuestTask<EvolutionCompleteEvent, PokemonEvolutionTask> {
    override fun matches(input: EvolutionCompleteEvent): Boolean {
        val fromMon = input.sourcePokemon
        val toMon = input.pokemon

        val matchesFromSpecies: Boolean =
            fromSpecies == "any" || fromMon.species.resourceIdentifier == parseSpeciesId(fromSpecies)
        val matchesFromAttributes: Boolean = attributeMatcher(fromAttributes).matches(fromMon)

        val matchesToSpecies: Boolean =
            toSpecies == "any" || toMon.species.resourceIdentifier == parseSpeciesId(toSpecies)
        val matchesToAttributes: Boolean = attributeMatcher(toAttributes).matches(toMon)

        // validate against criteria
        return matchesFromSpecies && matchesFromAttributes &&
            matchesToSpecies && matchesToAttributes &&
            fromMon.level >= minLevel
    }

    companion object {
        @JvmField
        val TYPE: QuestTaskType<PokemonEvolutionTask> = Type()
    }
    override fun type(): QuestTaskType<PokemonEvolutionTask> = TYPE

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

                    QuestAppearance.CODEC
                        .forGetter(PokemonEvolutionTask::appearance)

                ).apply(instance, ::PokemonEvolutionTask)
            }
    }
}
