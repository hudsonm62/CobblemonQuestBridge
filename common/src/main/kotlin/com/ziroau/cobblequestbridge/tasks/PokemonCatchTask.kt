package com.ziroau.cobblequestbridge.tasks

import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER
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

/**
 * Heracles [QuestTask] for catching a Pokémon with optional requirements for species, properties, and minimum level.
 */
data class PokemonCatchTask(
    private val taskId: String,
    val species: String = "any",
    val properties: String = "",
    val minLevel: Int = 1,
    val amount: Int = 1,
    val title: String = "",
    val description: String = ""
) : QuestTask<Pokemon, AbstractNbtNumber, PokemonCatchTask> {
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
        val caughtMon = input.species.resourceIdentifier.path

        LOGGER.debug(
            "[CQB] Caught Pokemon: {}, level: {}",
            caughtMon,
            input.level.toString(),
        )
        val matchesSpecies: Boolean = species == "any" || caughtMon == species
        if (!matchesSpecies || input.level < minLevel || !matcher.matches(input)) {
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
    override fun type(): QuestTaskType<PokemonCatchTask> = TYPE

    companion object {
        @JvmField
        val TYPE: QuestTaskType<PokemonCatchTask> = Type()
    }

    private class Type : QuestTaskType<PokemonCatchTask> {
        override fun id(): Identifier =
            Identifier.of(
                "cobblequestbridge", "pokemon_catch"
            )

        override fun codec(id: String): MapCodec<PokemonCatchTask> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    RecordCodecBuilder.point<PokemonCatchTask, String>(id),

                    Codec.STRING
                        .optionalFieldOf("species", "any")
                        .forGetter(PokemonCatchTask::species),

                    Codec.STRING
                        .optionalFieldOf("properties", "")
                        .forGetter(PokemonCatchTask::properties),

                    Codec.INT
                        .optionalFieldOf("minLevel", 1)
                        .forGetter(PokemonCatchTask::minLevel),

                    Codec.INT
                        .optionalFieldOf("amount", 1)
                        .forGetter(PokemonCatchTask::amount),

                    Codec.STRING
                        .optionalFieldOf("title", "")
                        .forGetter(PokemonCatchTask::title),

                    Codec.STRING
                        .optionalFieldOf("description", "")
                        .forGetter(PokemonCatchTask::description)

                ).apply(instance) { taskId, species, properties, minLevel, amount, title, description ->
                    PokemonCatchTask(
                        taskId, species, properties, minLevel, amount, title, description
                    )
                }
            }
    }
}
