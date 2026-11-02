package com.ziroau.cobblequestbridge.client.rewards.widgets

import com.ziroau.cobblequestbridge.api.client.widgets.PokemonIconRenderer
import com.ziroau.cobblequestbridge.api.client.widgets.PokemonWidgetText.getPokemonName
import com.ziroau.cobblequestbridge.api.client.widgets.PokemonWidgetText.pluralizedTranslationText
import com.ziroau.cobblequestbridge.rewards.PokemonReward
import com.ziroau.iolaus.api.client.widgets.AbstractRewardWidget
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.common.handlers.progress.QuestProgress
import net.minecraft.text.Text

class PokemonRewardWidget private constructor(
    override val reward: PokemonReward,
    override val questId: String,
    override val progress: QuestProgress?,
    override val interactive: Boolean
) : AbstractRewardWidget() {
    companion object {
        @JvmStatic
        fun of(reward: PokemonReward, interactive: Boolean): PokemonRewardWidget {
            val (questId, progress) = getQuestContext()
            return PokemonRewardWidget(
                reward, questId, progress, interactive
            )
        }
    }

    override val iconRenderer = PokemonIconRenderer.preferred(reward.pokemonIcon(), reward.species, reward.attributes)
    override val widgetItemIcon: QuestIcon<*>
        get() = reward.icon()

    override val widgetTitle: Text
        get() = reward.titleOr(
            pluralizedTranslationText(
                reward.display.titleOverride,
                reward.amount.coerceAtLeast(1),
                "rewards.cobblequestbridge.pokemon_reward.title",
                getPokemonName(reward.species),
            )
        )

    override val widgetDescription: Text
        get() = reward.descriptionOr(
            pluralizedTranslationText(
                reward.display.descriptionOverride,
                reward.amount.coerceAtLeast(1),
                "rewards.cobblequestbridge.pokemon_reward.description",
                getPokemonName(reward.species)
            )
        )
}
