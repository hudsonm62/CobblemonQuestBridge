package com.ziroau.cobblequestbridge.client.rewards.widgets

import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonRewardWidget
import com.ziroau.cobblequestbridge.rewards.MoLangScriptReward
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.common.handlers.progress.QuestProgress
import net.minecraft.text.Text

class MoLangScriptRewardWidget private constructor(
    override val reward: MoLangScriptReward,
    override val questId: String,
    override val progress: QuestProgress?,
    override val interactive: Boolean
): AbstractPokemonRewardWidget() {
    companion object {
        @JvmStatic
        fun of(reward: MoLangScriptReward, interactive: Boolean): MoLangScriptRewardWidget {
            val (questId, progress) = getQuestContext()
            return MoLangScriptRewardWidget(
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
            Text.translatable("rewards.cobblequestbridge.molangscript_reward.title", reward.script)
        )

    override val widgetDescription: Text
        get() = reward.descriptionOr(
            Text.translatable("rewards.cobblequestbridge.molangscript_reward.description", reward.script)
        )

}
