package com.ziroau.cobblequestbridge.tasks

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.pokemon.Pokemon
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import earth.terrarium.heracles.api.tasks.QuestTask
import earth.terrarium.heracles.api.tasks.QuestTaskType
import earth.terrarium.heracles.api.tasks.storage.defaults.IntegerTaskStorage
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.util.Identifier

data class PokemonDefeatTask (
    private val taskId: String,
    val species: String = "any",
    val properties: String = "",
    val minLevel: Int = 1,
    val amount: Int = 1,
    val title: String = "",
    val description: String = ""
) : QuestTask<Pokemon, AbstractNbtNumber, PokemonDefeatTask> {
    override fun id(): String = taskId

    // Property Parser
    private val matcher: PokemonProperties by lazy {
        PokemonProperties.parse(properties)
    }

    override fun test(
        type: QuestTaskType<*>,
        progress: AbstractNbtNumber,
        input: Pokemon
    ): AbstractNbtNumber {
        val defeatedMon = input.species.resourceIdentifier.path

        val matchesSpecies: Boolean = species == "any" || defeatedMon == species
        if (!matchesSpecies || input.level < minLevel || !matcher.matches(input)) {
            // return if doesn't meet the criteria
            return progress
        }

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
    override fun type(): QuestTaskType<PokemonDefeatTask> = TYPE

    companion object {
        @JvmField
        val TYPE: QuestTaskType<PokemonDefeatTask> = Type()
    }

    private class Type : QuestTaskType<PokemonDefeatTask> {
        override fun id(): Identifier =
            Identifier.of(
                "cobblequestbridge", "pokemon_defeat"
            )

        override fun codec(id: String): MapCodec<PokemonDefeatTask> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    RecordCodecBuilder.point<PokemonDefeatTask, String>(id),

                    Codec.STRING
                        .optionalFieldOf("species", "any")
                        .forGetter(PokemonDefeatTask::species),

                    Codec.STRING
                        .optionalFieldOf("properties", "")
                        .forGetter(PokemonDefeatTask::properties),

                    Codec.INT
                        .optionalFieldOf("minLevel", 1)
                        .forGetter(PokemonDefeatTask::minLevel),

                    Codec.INT
                        .optionalFieldOf("amount", 1)
                        .forGetter(PokemonDefeatTask::amount),

                    Codec.STRING
                        .optionalFieldOf("title", "")
                        .forGetter(PokemonDefeatTask::title),

                    Codec.STRING
                        .optionalFieldOf("description", "")
                        .forGetter(PokemonDefeatTask::description)
                ).apply(instance) { id, species, properties, minLevel, amount, title, description ->
                    PokemonDefeatTask(id, species, properties, minLevel, amount, title, description)
                }
            }
    }
}