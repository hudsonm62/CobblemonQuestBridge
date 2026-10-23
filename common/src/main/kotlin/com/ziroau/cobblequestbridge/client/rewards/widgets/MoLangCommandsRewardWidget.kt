package com.ziroau.cobblequestbridge.client.rewards.widgets

import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonRewardWidget
import com.ziroau.cobblequestbridge.rewards.MoLangCommandsReward
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.common.handlers.progress.QuestProgress
import net.minecraft.text.Text

class MoLangCommandsRewardWidget private constructor (
    override val reward: MoLangCommandsReward,
    override val questId: String,
    override val progress: QuestProgress?,
    override val interactive: Boolean
): AbstractPokemonRewardWidget() {
    companion object {
        @JvmStatic
        fun of(reward: MoLangCommandsReward, interactive: Boolean): MoLangCommandsRewardWidget {
            val (questId, progress) = getQuestContext()
            return MoLangCommandsRewardWidget(
                reward, questId, progress, interactive
            )
        }
    }

    override val pokemonIcon: RenderablePokemon?
        get() = if(reward.pokemonIcon().isNotEmpty())
            createPokemonIcon(reward.pokemonIcon())
        else
            null

    override val widgetItemIcon: QuestIcon<*>
        get() = reward.icon()

    override val widgetTitle: Text
        get() = reward.titleOr(
            Text.translatable("rewards.cobblequestbridge.molangcommands_reward.title")
        )

    override val widgetDescription: Text
        get() = reward.descriptionOr(
            Text.translatable("rewards.cobblequestbridge.molangcommands_reward.description")
        )
}
