package com.ziroau.cobblequestbridge.api.client.settings

import com.ziroau.cobblequestbridge.api.data.BattleTypeEnum
import earth.terrarium.heracles.api.client.settings.Setting
import earth.terrarium.heracles.api.client.settings.SettingInitializer
import earth.terrarium.heracles.api.client.settings.base.BooleanSetting
import earth.terrarium.heracles.api.client.settings.base.DoubleSetting
import earth.terrarium.heracles.api.client.settings.base.EnumSetting
import earth.terrarium.heracles.api.client.settings.base.IntSetting
import earth.terrarium.heracles.api.client.settings.base.TextSetting
import net.minecraft.client.gui.widget.ClickableWidget

/**
 * Provides the common data methods for creation of Quest Settings.
 *
 * @sample com.ziroau.cobblequestbridge.client.tasks.settings.PokemonDefeatTaskSettings
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

    @Suppress("UNCHECKED_CAST")
    fun doubleSetting(): Setting<Double, ClickableWidget> =
        DoubleSetting.INSTANCE as Setting<Double, ClickableWidget>

    // ENUMS
    @Suppress("UNCHECKED_CAST")
    fun battleTypeSetting(): Setting<BattleTypeEnum, ClickableWidget> =
        battleType as Setting<BattleTypeEnum, ClickableWidget>
    protected val battleType = EnumSetting(
        BattleTypeEnum::class.java,
        BattleTypeEnum.ANY
    )
}
