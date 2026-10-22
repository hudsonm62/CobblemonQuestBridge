package com.ziroau.cobblequestbridge.tasks

import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.resourcefullib.common.codecs.EnumCodec
import com.ziroau.cobblequestbridge.api.data.BattleTypeEnum
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.api.quests.QuestIcons
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon
import earth.terrarium.heracles.api.tasks.QuestTask
import earth.terrarium.heracles.api.tasks.QuestTaskType
import earth.terrarium.heracles.api.tasks.storage.defaults.IntegerTaskStorage
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.util.Identifier

data class BattleWinTask(
    private val taskId: String,
    val battleType: BattleTypeEnum = BattleTypeEnum.ANY,
    val ignoreWildCaptures: Boolean,
    val amount: Int = 1,
    val titleOverride: String,
    val descriptionOverride: String,
    val pokemonIconOverride: String,
    val iconOverride: QuestIcon<*> = ItemQuestIcon.AIR
): QuestTask<BattleVictoryEvent, AbstractNbtNumber, BattleWinTask>, CustomizableQuestElement {
    override fun id(): String = taskId

    override fun test(
        type: QuestTaskType<*>,
        progress: AbstractNbtNumber,
        input: BattleVictoryEvent
    ): AbstractNbtNumber {
        if (battleType != BattleTypeEnum.ANY && battleType != BattleTypeEnum.fromBattle(input.battle))
            return progress

        // ignore wild captures if the task is set to ignore them
        if(ignoreWildCaptures && input.wasWildCapture)
            return progress

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

    override fun type(): QuestTaskType<BattleWinTask> = TYPE

    override fun title(): String = titleOverride
    override fun description(): String = descriptionOverride
    override fun pokemonIcon(): String = pokemonIconOverride
    override fun icon(): QuestIcon<*> = iconOverride


    companion object {
        @JvmField
        val TYPE: QuestTaskType<BattleWinTask> = Type()
    }

    private class Type : QuestTaskType<BattleWinTask> {
        override fun id(): Identifier =
            Identifier.of(
                "cobblequestbridge", "battle_win"
            )

        override fun codec(id: String): MapCodec<BattleWinTask> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    RecordCodecBuilder.point<BattleWinTask, String>(id),

                    EnumCodec.of(BattleTypeEnum::class.java)
                        .fieldOf("battleType").orElse(BattleTypeEnum.ANY)
                        .forGetter(BattleWinTask::battleType),

                    Codec.BOOL.optionalFieldOf("ignoreWildCaptures", true)
                        .forGetter(BattleWinTask::ignoreWildCaptures),

                    Codec.INT.optionalFieldOf("amount", 1)
                        .forGetter(BattleWinTask::amount),

                    Codec.STRING.optionalFieldOf("title", "")
                        .forGetter(BattleWinTask::titleOverride),

                    Codec.STRING.optionalFieldOf("description", "")
                        .forGetter(BattleWinTask::descriptionOverride),

                    Codec.STRING.optionalFieldOf("pokemonIcon", "pikachu")
                        .forGetter(BattleWinTask::pokemonIconOverride),

                    QuestIcons.CODEC
                        .lenientOptionalFieldOf("icon", ItemQuestIcon.AIR)
                        .forGetter(BattleWinTask::iconOverride)

                ).apply(instance) {
                    taskId, battleType, ignoreWildCaptures, amount, title, description, pokemonIcon, icon ->
                    BattleWinTask(taskId, battleType, ignoreWildCaptures, amount, title, description, pokemonIcon, icon)
                }

        }
    }
}
