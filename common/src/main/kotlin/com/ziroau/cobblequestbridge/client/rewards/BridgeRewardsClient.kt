package com.ziroau.cobblequestbridge.client.rewards

import com.ziroau.cobblequestbridge.client.rewards.settings.MoLangCommandsRewardSettings
import com.ziroau.cobblequestbridge.client.rewards.settings.MoLangScriptRewardSettings
import com.ziroau.cobblequestbridge.client.rewards.settings.PokemonRewardSettings
import com.ziroau.cobblequestbridge.client.rewards.widgets.MoLangCommandsRewardWidget
import com.ziroau.cobblequestbridge.client.rewards.widgets.MoLangScriptRewardWidget
import com.ziroau.cobblequestbridge.client.rewards.widgets.PokemonRewardWidget
import com.ziroau.cobblequestbridge.rewards.MoLangCommandsReward
import com.ziroau.cobblequestbridge.rewards.MoLangScriptReward
import com.ziroau.cobblequestbridge.rewards.PokemonReward
import com.ziroau.iolaus.api.client.registry.RewardsClientRegistry

object BridgeRewardsClient {
    fun register() {
        RewardsClientRegistry.register(
            PokemonReward.TYPE,
            PokemonRewardSettings.INSTANCE,
            PokemonRewardWidget::of
        )
        RewardsClientRegistry.register(
            MoLangScriptReward.TYPE,
            MoLangScriptRewardSettings.INSTANCE,
            MoLangScriptRewardWidget::of
        )
        RewardsClientRegistry.register(
            MoLangCommandsReward.TYPE,
            MoLangCommandsRewardSettings.INSTANCE,
            MoLangCommandsRewardWidget::of
        )
    }
}
