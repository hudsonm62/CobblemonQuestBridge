package com.ziroau.cobblequestbridge.rewards

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.CobbleQuestBridge
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import com.ziroau.cobblequestbridge.api.molang.MoLangExecutor
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.api.quests.QuestIcons
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon
import earth.terrarium.heracles.api.rewards.QuestReward
import earth.terrarium.heracles.api.rewards.QuestRewardType
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import java.util.stream.Stream

class MoLangCommandsReward(
    private val rewardId: String,
    val commands: List<String>,
    val titleOverride: String,
    val descriptionOverride: String,
    val pokemonIconOverride: String,
    val iconOverride: QuestIcon<*> = ItemQuestIcon.AIR
) : QuestReward<MoLangCommandsReward>, CustomizableQuestElement {

    override fun id(): String = rewardId
    override fun type(): QuestRewardType<MoLangCommandsReward> = TYPE

    override fun title(): String = titleOverride
    override fun description(): String = descriptionOverride
    override fun pokemonIcon(): String = pokemonIconOverride
    override fun icon(): QuestIcon<*> = iconOverride

    override fun reward(player: ServerPlayerEntity): Stream<ItemStack?> {
        if (commands.all { it.isBlank() }) {
            // No commands to execute, return an empty stream
            CobbleQuestBridge.LOGGER.warn("MoLang reward '$rewardId' has no commands to execute.")
            return Stream.empty()
        }
        MoLangExecutor.execute(commands, player)
        return Stream.empty()
    }

    companion object {
        @JvmField
        val TYPE: QuestRewardType<MoLangCommandsReward> = Type()
    }

    private class Type : QuestRewardType<MoLangCommandsReward> {
        override fun id(): Identifier = Identifier.of(CobbleQuestBridge.MOD_ID, "molangcommands_reward")

        override fun codec(id: String): MapCodec<MoLangCommandsReward> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    RecordCodecBuilder.point<MoLangCommandsReward, String>(id),

                    Codec.STRING.listOf()
                        .optionalFieldOf("commands", emptyList())
                        .forGetter(MoLangCommandsReward::commands),

                    Codec.STRING.optionalFieldOf("title", "")
                        .forGetter(MoLangCommandsReward::titleOverride),

                    Codec.STRING.optionalFieldOf("description", "")
                        .forGetter(MoLangCommandsReward::descriptionOverride),

                    Codec.STRING.optionalFieldOf("pokemonIcon", "")
                        .forGetter(MoLangCommandsReward::pokemonIconOverride),

                    QuestIcons.CODEC
                        .lenientOptionalFieldOf("icon", ItemQuestIcon.AIR)
                        .forGetter(MoLangCommandsReward::iconOverride)

                ).apply(instance) { rewardId, commands, titleOverride, descriptionOverride, pokemonIconOverride, iconOverride ->
                    MoLangCommandsReward(rewardId, commands, titleOverride, descriptionOverride, pokemonIconOverride, iconOverride)
                }
            }
    }
}
