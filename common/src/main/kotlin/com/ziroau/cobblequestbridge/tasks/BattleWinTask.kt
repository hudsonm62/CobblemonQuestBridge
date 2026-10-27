package com.ziroau.cobblequestbridge.tasks

import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.teamresourceful.resourcefullib.common.codecs.EnumCodec
import com.ziroau.cobblequestbridge.api.data.BattleTypeEnum
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import com.ziroau.cobblequestbridge.api.data.QuestAppearance
import com.ziroau.cobblequestbridge.api.tasks.CountedQuestTask
import earth.terrarium.heracles.api.tasks.QuestTaskType
import net.minecraft.util.Identifier

data class BattleWinTask(
    override val taskId: String,
    val battleType: BattleTypeEnum = BattleTypeEnum.ANY,
    val ignoreWildCaptures: Boolean,
    override val amount: Int = 1,
    override val appearance: QuestAppearance = DEFAULT_APPEARANCE
): CountedQuestTask<BattleVictoryEvent, BattleWinTask>, CustomizableQuestElement {
    override fun matches(input: BattleVictoryEvent): Boolean {
        if (battleType != BattleTypeEnum.ANY && battleType != BattleTypeEnum.fromBattle(input.battle)) return false
        if (ignoreWildCaptures && input.wasWildCapture) return false

        return true
    }

    companion object {
        val DEFAULT_APPEARANCE = QuestAppearance(pokemonIconOverride = "pikachu")

        @JvmField
        val TYPE: QuestTaskType<BattleWinTask> = Type()
    }
    override fun type(): QuestTaskType<BattleWinTask> = TYPE

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

                    QuestAppearance
                        .codec(DEFAULT_APPEARANCE)
                        .forGetter(BattleWinTask::appearance)

                ).apply(instance, ::BattleWinTask)

        }
    }
}
