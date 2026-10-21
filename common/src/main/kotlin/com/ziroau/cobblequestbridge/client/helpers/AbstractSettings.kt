package com.ziroau.cobblequestbridge.client.helpers

import earth.terrarium.heracles.api.client.settings.Setting
import earth.terrarium.heracles.api.client.settings.SettingInitializer
import earth.terrarium.heracles.api.client.settings.base.BooleanSetting
import earth.terrarium.heracles.api.client.settings.base.IntSetting
import earth.terrarium.heracles.api.client.settings.base.TextSetting
import net.minecraft.client.gui.widget.ClickableWidget

/**
 * Provides the common methods for creating settings of different types.
 * This class is intended to be extended by other classes that need to create settings.
 *
 * @sample com.ziroau.cobblequestbridge.client.rewards.settings.PokemonRewardSettings
 */
abstract class AbstractSettings<T> : SettingInitializer<T> {
    @Suppress("UNCHECKED_CAST")
    fun textSetting(): Setting<String, ClickableWidget> =
        TextSetting.INSTANCE as Setting<String, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    fun intSetting(): Setting<Int, ClickableWidget> =
        IntSetting.ONE as Setting<Int, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    fun boolTrueSetting(): Setting<Boolean, ClickableWidget> =
        BooleanSetting.TRUE as Setting<Boolean, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    fun boolFalseSetting(): Setting<Boolean, ClickableWidget> =
        BooleanSetting.FALSE as Setting<Boolean, ClickableWidget>
}