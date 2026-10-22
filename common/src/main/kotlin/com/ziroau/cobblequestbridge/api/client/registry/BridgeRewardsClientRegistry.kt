package com.ziroau.cobblequestbridge.api.client.registry

import earth.terrarium.heracles.api.client.settings.SettingInitializer
import earth.terrarium.heracles.api.client.settings.Settings
import earth.terrarium.heracles.api.rewards.QuestReward
import earth.terrarium.heracles.api.rewards.QuestRewardType
import earth.terrarium.heracles.api.rewards.client.QuestRewardWidgetFactory
import earth.terrarium.heracles.api.rewards.client.QuestRewardWidgets

/**
 * Helper class for quickly registering your quest reward client-only components.
 *
 * This MUST be called on the client side, during initialization.
 */
object BridgeRewardsClientRegistry {
    /**
     * Registers all client components for a quest reward.
     *
     * @param type Your defined [QuestRewardType]
     * @param settings The initializer for your rewards settings screen in the quest editor.
     * @param widget The display for your reward in the quest viewer - This is what players will see.
     */
    @JvmStatic
    fun <T : QuestReward<T>> register(
        type: QuestRewardType<T>,
        settings: SettingInitializer<T>,
        widget: QuestRewardWidgetFactory<T>
    ) {
        registerSettings(type, settings)
        registerWidget(type, widget)
    }

    /**
     * Register the reward's settings screen.
     *
     * @param type Your defined [QuestRewardType]
     * @param settings The initializer for your rewards settings screen in the quest editor.
     */
    @JvmStatic
    fun <T : QuestReward<T>> registerSettings(
        type: QuestRewardType<T>,
        settings: SettingInitializer<T>
    ) {
        Settings.register(type, settings)
    }

    /**
     * Register the reward's display widget
     *
     * @param type Your defined [QuestRewardType]
     * @param widget The display for your reward in the quest viewer - This is what players will see.
     */
    @JvmStatic
    fun <T : QuestReward<T>> registerWidget(
        type: QuestRewardType<T>,
        widget: QuestRewardWidgetFactory<T>
    ) {
        QuestRewardWidgets.register(type, widget)
    }
}