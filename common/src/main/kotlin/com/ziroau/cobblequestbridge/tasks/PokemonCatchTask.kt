package com.ziroau.cobblequestbridge.tasks

import com.cobblemon.mod.common.pokemon.Pokemon
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import com.ziroau.cobblequestbridge.api.data.Helpers.attributeMatcher
import com.ziroau.cobblequestbridge.api.data.Helpers.parseSpeciesId
import com.ziroau.cobblequestbridge.api.data.QuestAppearance
import com.ziroau.cobblequestbridge.api.tasks.CountedQuestTask
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.api.quests.QuestIcons
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon
import earth.terrarium.heracles.api.tasks.QuestTaskType
import net.minecraft.util.Identifier

/**
 * [CountedQuestTask] for catching a Pokémon with optional requirements for species, attributes, and minimum level.
 */
data class PokemonCatchTask(
    private val taskId: String,
    val species: String = "any",
    val attributes: String = "",
    val minLevel: Int = 1,
    override val amount: Int = 1,
    val titleOverride: String,
    val descriptionOverride: String,
    val pokemonIconOverride: String,
    val iconOverride: QuestIcon<*> = ItemQuestIcon.AIR,
) : CountedQuestTask<Pokemon, PokemonCatchTask>, CustomizableQuestElement {
    override fun id(): String = taskId
    override fun title(): String = titleOverride
    override fun description(): String = descriptionOverride
    override fun pokemonIcon(): String = pokemonIconOverride
    override fun icon(): QuestIcon<*> = iconOverride
    override fun type(): QuestTaskType<PokemonCatchTask> = TYPE

    override fun matches(input: Pokemon): Boolean {
        val caughtMon = input.species.resourceIdentifier
        val matchesSpecies: Boolean = species == "any" || caughtMon == parseSpeciesId(species)

        return matchesSpecies && input.level >= minLevel && attributeMatcher(attributes).matches(input)
    }

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
                ).apply(instance, ::PokemonCatchTask)
            }
    }
}
