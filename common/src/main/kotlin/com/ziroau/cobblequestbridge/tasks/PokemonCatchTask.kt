package com.ziroau.cobblequestbridge.tasks

import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.pokemon.Pokemon
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.api.quests.QuestIcons
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon
import earth.terrarium.heracles.api.tasks.QuestTask
import earth.terrarium.heracles.api.tasks.QuestTaskType
import earth.terrarium.heracles.api.tasks.storage.defaults.IntegerTaskStorage
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.util.Identifier

/**
 * Heracles [QuestTask] for catching a Pokémon with optional requirements for species, attributes, and minimum level.
 */
data class PokemonCatchTask(
    private val taskId: String,
    val species: String = "any",
    val attributes: String = "",
    val minLevel: Int = 1,
    val amount: Int = 1,
    val titleOverride: String,
    val descriptionOverride: String,
    val pokemonIconOverride: String,
    val iconOverride: QuestIcon<*> = ItemQuestIcon.AIR
) : QuestTask<Pokemon, AbstractNbtNumber, PokemonCatchTask>, CustomizableQuestElement {
    override fun id(): String = taskId

    // Attribute Parser
    private val matcher: PokemonProperties by lazy {
        PokemonProperties.parse(attributes)
    }

    override fun test(
        type: QuestTaskType<*>,
        progress: AbstractNbtNumber,
        input: Pokemon
    ): AbstractNbtNumber {
        val caughtMon = input.species.resourceIdentifier.path

        // check requirements
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

    override fun title(): String = titleOverride
    override fun description(): String = descriptionOverride
    override fun pokemonIcon(): String = pokemonIconOverride
    override fun icon(): QuestIcon<*> = iconOverride

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
                        .optionalFieldOf("attributes", "")
                        .forGetter(PokemonCatchTask::attributes),

                    Codec.INT
                        .optionalFieldOf("minLevel", 1)
                        .forGetter(PokemonCatchTask::minLevel),

                    Codec.INT
                        .optionalFieldOf("amount", 1)
                        .forGetter(PokemonCatchTask::amount),

                    Codec.STRING
                        .optionalFieldOf("title", "")
                        .forGetter(PokemonCatchTask::titleOverride),

                    Codec.STRING
                        .optionalFieldOf("description", "")
                        .forGetter(PokemonCatchTask::descriptionOverride),

                    Codec.STRING
                        .optionalFieldOf("pokemonIcon", "")
                        .forGetter(PokemonCatchTask::pokemonIconOverride),

                    QuestIcons.CODEC
                        .lenientOptionalFieldOf("icon", ItemQuestIcon.AIR)
                        .forGetter(PokemonCatchTask::iconOverride)
                ).apply(instance) { taskId, species, attributes, minLevel, amount, title, description, pokemonIcon, icon ->
                    PokemonCatchTask(
                        taskId, species, attributes, minLevel, amount, title, description, pokemonIcon, icon
                    )
                }
            }
    }
}
