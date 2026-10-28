package com.ziroau.cobblequestbridge.tasks

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.api.data.Helpers.attributeMatcher
import com.ziroau.cobblequestbridge.api.data.Helpers.parseSpeciesId
import com.ziroau.cobblequestbridge.api.data.PokemonTradeEventWrapper
import com.ziroau.cobblequestbridge.api.data.QuestAppearance
import com.ziroau.cobblequestbridge.api.tasks.CountedQuestTask
import earth.terrarium.heracles.api.tasks.QuestTaskType
import net.minecraft.util.Identifier

class PokemonTradeTask(
    override val taskId: String,
    val speciesSent: String = "any",
    val attributesSent: String = "",
    val minLevelSent: Int = 1,
    val speciesReceived: String = "any",
    val attributesReceived: String = "",
    val minLevelReceived: Int = 1,
    override val amount: Int = 1,
    override val appearance: QuestAppearance
) : CountedQuestTask<PokemonTradeEventWrapper, PokemonTradeTask> {
    override fun matches(input: PokemonTradeEventWrapper): Boolean {
        val sentMon = input.playerPokemon
        val receivedMon = input.partnerPokemon

        val matchesSentSpecies: Boolean =
            speciesSent == "any" || sentMon.species.resourceIdentifier == parseSpeciesId(speciesSent)
        val matchesSentAttributes: Boolean = attributeMatcher(attributesSent).matches(sentMon)

        val matchesReceivedSpecies: Boolean =
            speciesReceived == "any" || receivedMon.species.resourceIdentifier == parseSpeciesId(speciesReceived)
        val matchesReceivedAttributes: Boolean = attributeMatcher(attributesReceived).matches(receivedMon)

        // validate against criteria
        return matchesSentSpecies && matchesSentAttributes &&
            matchesReceivedSpecies && matchesReceivedAttributes &&
            sentMon.level >= minLevelSent && receivedMon.level >= minLevelReceived
    }

    companion object {
        @JvmField
        val TYPE: QuestTaskType<PokemonTradeTask> = Type()
    }
    override fun type(): QuestTaskType<PokemonTradeTask> = TYPE

    private class Type : QuestTaskType<PokemonTradeTask> {
        override fun id(): Identifier =
            Identifier.of(
                "cobblequestbridge", "pokemon_trade"
            )

        override fun codec(id: String): MapCodec<PokemonTradeTask> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    RecordCodecBuilder.point<PokemonTradeTask, String>(id),

                    Codec.STRING
                        .optionalFieldOf("speciesSent", "any")
                        .forGetter(PokemonTradeTask::speciesSent),

                    Codec.STRING
                        .optionalFieldOf("attributesSent", "")
                        .forGetter(PokemonTradeTask::attributesSent),

                    Codec.INT
                        .optionalFieldOf("minLevelSent", 1)
                        .forGetter(PokemonTradeTask::minLevelSent),

                    Codec.STRING
                        .optionalFieldOf("speciesReceived", "any")
                        .forGetter(PokemonTradeTask::speciesReceived),

                    Codec.STRING
                        .optionalFieldOf("attributesReceived", "")
                        .forGetter(PokemonTradeTask::attributesReceived),

                    Codec.INT
                        .optionalFieldOf("minLevelReceived", 1)
                        .forGetter(PokemonTradeTask::minLevelReceived),

                    Codec.INT
                        .optionalFieldOf("amount", 1)
                        .forGetter(PokemonTradeTask::amount),

                    QuestAppearance.CODEC
                        .forGetter(PokemonTradeTask::appearance)
                ).apply(instance, ::PokemonTradeTask)
            }
    }
}
