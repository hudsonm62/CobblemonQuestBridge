package com.ziroau.cobblequestbridge.rewards

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.CobbleQuestBridge
import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import com.ziroau.cobblequestbridge.api.data.QuestAppearance
import com.ziroau.cobblequestbridge.api.molang.MoLangExecutor
import earth.terrarium.heracles.api.rewards.QuestReward
import earth.terrarium.heracles.api.rewards.QuestRewardType
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import java.util.stream.Stream

class MoLangScriptReward(
    private val rewardId: String,
    val script: String,
    override val appearance: QuestAppearance
) : QuestReward<MoLangScriptReward>, CustomizableQuestElement {
    override fun id(): String = rewardId
    override fun type(): QuestRewardType<MoLangScriptReward> = TYPE

    override fun reward(player: ServerPlayerEntity): Stream<ItemStack?>? {
        val scriptId = Identifier.tryParse(
            // Prepends Cobblemon namespace, if it does not contain a namespace already
            if (":" in script) script
            else "cobblemon:$script"
        )
        if(scriptId == null) {
            LOGGER.error("Invalid MoLang script identifier '$script', in reward '$rewardId' claimed by player '${player.gameProfile.name}'")
            return Stream.empty()
        }

        MoLangExecutor.execute(scriptId, player)
        return Stream.empty()
    }

    companion object {
        @JvmField
        val TYPE: QuestRewardType<MoLangScriptReward> = Type()
    }

    private class Type : QuestRewardType<MoLangScriptReward> {
        override fun id(): Identifier = Identifier.of(CobbleQuestBridge.MOD_ID, "molangscript_reward")

        override fun codec(id: String): MapCodec<MoLangScriptReward> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    RecordCodecBuilder.point<MoLangScriptReward, String>(id),

                    Codec.STRING.fieldOf("script")
                        .forGetter(MoLangScriptReward::script),

                    QuestAppearance.CODEC
                        .forGetter(MoLangScriptReward::appearance)

                ).apply(instance, ::MoLangScriptReward)
            }
    }
}
