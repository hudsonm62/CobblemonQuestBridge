package com.ziroau.cobblequestbridge.api.client.settings

import com.ziroau.cobblequestbridge.api.data.BattleTypeEnum
import earth.terrarium.heracles.api.client.settings.Setting
import earth.terrarium.heracles.api.client.settings.SettingInitializer
import earth.terrarium.heracles.api.client.settings.base.BooleanSetting
import earth.terrarium.heracles.api.client.settings.base.DoubleSetting
import earth.terrarium.heracles.api.client.settings.base.IntSetting
import earth.terrarium.heracles.api.client.settings.base.TextSetting
import earth.terrarium.heracles.api.client.settings.base.EnumSetting
import earth.terrarium.heracles.api.client.settings.base.QuestIconSetting
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon
import net.minecraft.client.gui.widget.ClickableWidget

/**
 * Provides some common data methods for creation of Task/Reward Settings (due to how Kotlin handles inference).
 * You _probably_ don't need to use these in pure Java though.
 *
 * @sample com.ziroau.cobblequestbridge.client.tasks.settings.PokemonDefeatTaskSettings
 */
abstract class AbstractSettingInitializer<T> : SettingInitializer<T> {
    @Suppress("UNCHECKED_CAST")
    protected fun textSetting(): Setting<String, ClickableWidget> =
        TextSetting.INSTANCE as Setting<String, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    protected fun intSetting(): Setting<Int, ClickableWidget> =
        IntSetting.ONE as Setting<Int, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    protected fun intZeroSetting(): Setting<Int, ClickableWidget> =
        IntSetting.ZERO as Setting<Int, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    protected fun boolTrueSetting(): Setting<Boolean, ClickableWidget> =
        BooleanSetting.TRUE as Setting<Boolean, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    protected fun boolFalseSetting(): Setting<Boolean, ClickableWidget> =
        BooleanSetting.FALSE as Setting<Boolean, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    protected fun doubleSetting(): Setting<Double, ClickableWidget> =
        DoubleSetting.INSTANCE as Setting<Double, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    protected fun questIconSetting(): Setting<ItemQuestIcon, ClickableWidget> =
        QuestIconSetting.INSTANCE as Setting<ItemQuestIcon, ClickableWidget>

    // ENUMS
    @Suppress("UNCHECKED_CAST")
    protected fun battleTypeSetting(): Setting<BattleTypeEnum, ClickableWidget> =
        battleType as Setting<BattleTypeEnum, ClickableWidget>
    private val battleType = EnumSetting(
        BattleTypeEnum::class.java,
        BattleTypeEnum.ANY
    )
}
