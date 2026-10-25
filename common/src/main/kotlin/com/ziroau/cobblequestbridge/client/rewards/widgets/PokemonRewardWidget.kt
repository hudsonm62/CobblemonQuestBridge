package com.ziroau.cobblequestbridge.client.rewards.widgets

import com.cobblemon.mod.common.pokemon.RenderablePokemon
import com.ziroau.cobblequestbridge.api.client.widgets.AbstractPokemonRewardWidget
import com.ziroau.cobblequestbridge.rewards.PokemonReward
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.common.handlers.progress.QuestProgress
import net.minecraft.text.Text

class PokemonRewardWidget private constructor(
    override val reward: PokemonReward,
    override val questId: String,
    override val progress: QuestProgress?,
    override val interactive: Boolean
) : AbstractPokemonRewardWidget() {
    companion object {
        @JvmStatic
        fun of(reward: PokemonReward, interactive: Boolean): PokemonRewardWidget {
            val (questId, progress) = getQuestContext()
            return PokemonRewardWidget(
                reward, questId, progress, interactive
            )
        }
    }

    override val pokemonIcon: RenderablePokemon?
        get() = if(reward.pokemonIcon().isNotEmpty())
            createPokemonIcon(reward.pokemonIcon())
        else
            createPokemonIcon(reward.species, reward.attributes)

    override val widgetItemIcon: QuestIcon<*>
        get() = reward.icon()

    override val widgetTitle: Text
        get() = reward.titleOr(
            pluralizedTranslationText(
                reward.titleOverride,
                reward.amount.coerceAtLeast(1),
                getPokemonName(reward.species),
                "rewards.cobblequestbridge.pokemon_reward.title"
            )
        )

    override val widgetDescription: Text
        get() = reward.descriptionOr(
            pluralizedTranslationText(
                reward.descriptionOverride,
                reward.amount.coerceAtLeast(1),
                getPokemonName(reward.species),
                "rewards.cobblequestbridge.pokemon_reward.description"
            )
        )
}
