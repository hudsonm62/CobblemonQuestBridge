package com.ziroau.cobblequestbridge.tasks

import com.cobblemon.mod.common.api.events.pokemon.FossilRevivedEvent
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.api.data.Helpers.attributeMatcher
import com.ziroau.cobblequestbridge.api.data.Helpers.parseSpeciesId
import com.ziroau.cobblequestbridge.api.data.QuestAppearance
import com.ziroau.cobblequestbridge.api.tasks.CountedQuestTask
import earth.terrarium.heracles.api.tasks.QuestTaskType
import net.minecraft.util.Identifier

class FossilRestoreTask(
    override val taskId: String,
    val species: String,
    val attributes: String = "",
    override val amount: Int = 1,
    override val appearance: QuestAppearance
) : CountedQuestTask<FossilRevivedEvent, FossilRestoreTask> {
    override fun matches(input: FossilRevivedEvent): Boolean {
        val matchesSpecies: Boolean =
            species == "any" || input.pokemon.species.resourceIdentifier == parseSpeciesId(species)
        val matchesAttributes: Boolean =
            attributeMatcher(attributes).matches(input.pokemon)

        return matchesSpecies && matchesAttributes
    }

    companion object {
        val DEFAULT_APPEARANCE = QuestAppearance(pokemonIconOverride = "kabutops")

        @JvmField
        val TYPE: QuestTaskType<FossilRestoreTask> = Type()
    }
    override fun type(): QuestTaskType<FossilRestoreTask> = TYPE

    private class Type : QuestTaskType<FossilRestoreTask> {
        override fun id(): Identifier =
            Identifier.of("cobblequestbridge", "fossil_restore")

        override fun codec(id: String): MapCodec<FossilRestoreTask> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    RecordCodecBuilder.point<FossilRestoreTask, String>(id),

                    Codec.STRING
                        .optionalFieldOf("species", "any")
                        .forGetter(FossilRestoreTask::species),

                    Codec.STRING
                        .optionalFieldOf("attributes", "")
                        .forGetter(FossilRestoreTask::attributes),

                    Codec.INT
                        .optionalFieldOf("amount", 1)
                        .forGetter(FossilRestoreTask::amount),

                    QuestAppearance
                        .codec(DEFAULT_APPEARANCE)
                        .forGetter(FossilRestoreTask::appearance)

                ).apply(instance, ::FossilRestoreTask)
            }
    }
}
