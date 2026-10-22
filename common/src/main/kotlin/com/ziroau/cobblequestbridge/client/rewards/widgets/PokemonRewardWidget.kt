package com.ziroau.cobblequestbridge.client.rewards.widgets

import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonRewardWidget
import com.ziroau.cobblequestbridge.rewards.RewardPokemon
import earth.terrarium.heracles.common.handlers.progress.QuestProgress
import net.minecraft.text.Text

class PokemonRewardWidget private constructor(
    override val reward: RewardPokemon,
    override val questId: String,
    override val progress: QuestProgress?,
    override val interactive: Boolean
) : AbstractPokemonRewardWidget(
    createPreviewPokemon(reward.species, reward.attributes)
) {
    companion object {
        @JvmStatic
        fun of(reward: RewardPokemon, interactive: Boolean): PokemonRewardWidget {
            val (questId, progress) = getQuestContext()
            return PokemonRewardWidget(
                reward, questId, progress, interactive
            )
        }
    }

    override val widgetTitle: Text
        get() = pluralizedTranslationText(
            reward.title,
            reward.amount.coerceAtLeast(1),
            getPokemonName(reward.species),
            "rewards.cobblequestbridge.pokemon_reward.title"
        )

    override val widgetDescription: Text
        get() = pluralizedTranslationText(
            reward.description,
            reward.amount.coerceAtLeast(1),
            getPokemonName(reward.species),
            "rewards.cobblequestbridge.pokemon_reward.description"
        )
}
