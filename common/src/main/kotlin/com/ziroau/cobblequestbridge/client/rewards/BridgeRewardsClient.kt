package com.ziroau.cobblequestbridge.client.rewards

import com.ziroau.cobblequestbridge.api.client.registry.BridgeRewardsClientRegistry
import com.ziroau.cobblequestbridge.client.rewards.settings.MoLangCommandsRewardSettings
import com.ziroau.cobblequestbridge.client.rewards.settings.MoLangScriptRewardSettings
import com.ziroau.cobblequestbridge.client.rewards.settings.PokemonRewardSettings
import com.ziroau.cobblequestbridge.client.rewards.widgets.MoLangCommandsRewardWidget
import com.ziroau.cobblequestbridge.client.rewards.widgets.MoLangScriptRewardWidget
import com.ziroau.cobblequestbridge.client.rewards.widgets.PokemonRewardWidget
import com.ziroau.cobblequestbridge.rewards.MoLangCommandsReward
import com.ziroau.cobblequestbridge.rewards.MoLangScriptReward
import com.ziroau.cobblequestbridge.rewards.PokemonReward

object BridgeRewardsClient {
    fun register() {
        BridgeRewardsClientRegistry.register(
            PokemonReward.TYPE,
            PokemonRewardSettings.INSTANCE,
            PokemonRewardWidget::of
        )
        BridgeRewardsClientRegistry.register(
            MoLangScriptReward.TYPE,
            MoLangScriptRewardSettings.INSTANCE,
            MoLangScriptRewardWidget::of
        )
        BridgeRewardsClientRegistry.register(
            MoLangCommandsReward.TYPE,
            MoLangCommandsRewardSettings.INSTANCE,
            MoLangCommandsRewardWidget::of
        )
    }
}
