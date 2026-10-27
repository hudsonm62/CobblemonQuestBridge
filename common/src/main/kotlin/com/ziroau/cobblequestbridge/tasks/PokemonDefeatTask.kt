package com.ziroau.cobblequestbridge.tasks

import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.resourcefullib.common.codecs.EnumCodec
import com.ziroau.cobblequestbridge.api.data.BattleTypeEnum
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import com.ziroau.cobblequestbridge.api.data.Helpers.attributeMatcher
import com.ziroau.cobblequestbridge.api.data.Helpers.parseSpeciesId
import com.ziroau.cobblequestbridge.api.tasks.CountedQuestTask
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.api.quests.QuestIcons
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon
import earth.terrarium.heracles.api.tasks.QuestTaskType
import earth.terrarium.heracles.api.tasks.storage.defaults.IntegerTaskStorage
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.util.Identifier

data class PokemonDefeatTask (
    private val taskId: String,
    val species: String = "any",
    val attributes: String = "",
    val minLevel: Int = 1,
    val battleType: BattleTypeEnum = BattleTypeEnum.ANY,
    override val amount: Int = 1,
    val titleOverride: String,
    val descriptionOverride: String,
    val pokemonIconOverride: String,
    val iconOverride: QuestIcon<*> = ItemQuestIcon.AIR
) : CountedQuestTask<BattleFaintedEvent, PokemonDefeatTask>, CustomizableQuestElement {
    override fun id(): String = taskId
    override fun title(): String = titleOverride
    override fun description(): String = descriptionOverride
    override fun pokemonIcon(): String = pokemonIconOverride
    override fun icon(): QuestIcon<*> = iconOverride
    override fun type(): QuestTaskType<PokemonDefeatTask> = TYPE

    override fun matches(input: BattleFaintedEvent): Boolean {
        if (battleType != BattleTypeEnum.ANY && battleType != BattleTypeEnum.fromBattle(input.battle)) return false

        val sourceBattlePkm = input.context.origin ?: return false
        val defeatedBattlePkm = input.killed

        // Ignore self-KOs and friendly fire
        if (defeatedBattlePkm.actor.getSide() == sourceBattlePkm.actor.getSide()) return false

        val defeatedMon = defeatedBattlePkm.effectedPokemon
        val defeatedSpecies = defeatedMon.species.resourceIdentifier

        val matchesSpecies: Boolean = species == "any" || defeatedSpecies == parseSpeciesId(species)

        return matchesSpecies && defeatedMon.level >= minLevel && attributeMatcher(attributes).matches(defeatedMon)
    }

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
                        .optionalFieldOf("attributes", "")
                        .forGetter(PokemonDefeatTask::attributes),

                    Codec.INT
                        .optionalFieldOf("minLevel", 1)
                        .forGetter(PokemonDefeatTask::minLevel),

                    EnumCodec.of(BattleTypeEnum::class.java)
                        .fieldOf("battleType").orElse(BattleTypeEnum.ANY)
                        .forGetter(PokemonDefeatTask::battleType),

                    Codec.INT
                        .optionalFieldOf("amount", 1)
                        .forGetter(PokemonDefeatTask::amount),

                    Codec.STRING
                        .optionalFieldOf("title", "")
                        .forGetter(PokemonDefeatTask::titleOverride),

                    Codec.STRING
                        .optionalFieldOf("description", "")
                        .forGetter(PokemonDefeatTask::descriptionOverride),

                    Codec.STRING
                        .optionalFieldOf("pokemonIcon", "")
                        .forGetter(PokemonDefeatTask::pokemonIconOverride),

                    QuestIcons.CODEC
                        .lenientOptionalFieldOf("icon", ItemQuestIcon.AIR)
                        .forGetter(PokemonDefeatTask::iconOverride)

                ).apply(instance, ::PokemonDefeatTask)
            }
    }
}
