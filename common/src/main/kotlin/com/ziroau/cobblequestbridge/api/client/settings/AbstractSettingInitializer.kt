package com.ziroau.cobblequestbridge.api.client.settings

import com.ziroau.cobblequestbridge.api.client.settings.base.PokemonSpeciesAutocompleteSetting
import com.ziroau.cobblequestbridge.api.client.settings.base.StringListSetting
import com.ziroau.cobblequestbridge.api.data.BattleTypeEnum
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import com.ziroau.cobblequestbridge.api.data.QuestAppearance
import earth.terrarium.heracles.api.client.settings.Setting
import earth.terrarium.heracles.api.client.settings.SettingInitializer
import earth.terrarium.heracles.api.client.settings.base.BooleanSetting
import earth.terrarium.heracles.api.client.settings.base.DoubleSetting
import earth.terrarium.heracles.api.client.settings.base.EnumSetting
import earth.terrarium.heracles.api.client.settings.base.IntSetting
import earth.terrarium.heracles.api.client.settings.base.QuestIconSetting
import earth.terrarium.heracles.api.client.settings.base.TextSetting
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon
import net.minecraft.client.gui.widget.ClickableWidget

/**
 * Provides some common data methods for creation of Task/Reward Settings.
 *
 * Most of the methods here are just type-safe wrappers for the [SettingInitializer] methods,
 * but there are also some helper methods for working with [CustomizableQuestElement] & [QuestAppearance].
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
    protected fun stringListSetting(): Setting<List<String>, ClickableWidget> =
        StringListSetting as Setting<List<String>, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    protected fun questIconSetting(): Setting<ItemQuestIcon, ClickableWidget> =
        QuestIconSetting.INSTANCE as Setting<ItemQuestIcon, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    protected fun anySpeciesSetting(): Setting<String, ClickableWidget> =
        PokemonSpeciesAutocompleteSetting.WITH_ANY as Setting<String, ClickableWidget>

    @Suppress("UNCHECKED_CAST")
    protected fun speciesSetting(): Setting<String, ClickableWidget> =
        PokemonSpeciesAutocompleteSetting.REQUIRED as Setting<String, ClickableWidget>

    // ENUMS
    @Suppress("UNCHECKED_CAST")
    protected fun battleTypeSetting(): Setting<BattleTypeEnum, ClickableWidget> =
        battleType as Setting<BattleTypeEnum, ClickableWidget>
    private val battleType = EnumSetting(
        BattleTypeEnum::class.java,
        BattleTypeEnum.ANY
    )

    /**
     * Helper method to add the appearance settings to a [SettingInitializer.CreationData] object.
     * @param element The [CustomizableQuestElement] to get the appearance data from.
     * @param defaults The default [QuestAppearance] to use if the element is null.
     */
    protected fun SettingInitializer.CreationData.putAppearance(
        element: CustomizableQuestElement?, defaults: QuestAppearance = QuestAppearance()
    ) {
        put("title", textSetting(),
            element?.title() ?: defaults.titleOverride)

        put("description", textSetting(),
            element?.description() ?: defaults.descriptionOverride)

        put("pokemonIcon", textSetting(),
            element?.pokemonIcon() ?: defaults.pokemonIconOverride)

        put("icon", questIconSetting(),
            (element?.icon() as? ItemQuestIcon)
                ?: (defaults.iconOverride as? ItemQuestIcon)
                ?: ItemQuestIcon.AIR)
    }

    /**
     * Helper method to get the appearance data from a [SettingInitializer.Data] object.
     * @param element The [CustomizableQuestElement] to get the appearance data from.
     * @param defaults The default [QuestAppearance] to use if the element is null.
     */
    protected fun SettingInitializer.Data.getAppearance(
        element: CustomizableQuestElement?, defaults: QuestAppearance = QuestAppearance()
    ): QuestAppearance {
        return QuestAppearance(
            titleOverride = get("title", textSetting())
                .orElse(element?.title() ?: defaults.titleOverride),

            descriptionOverride = get("description", textSetting())
                .orElse(element?.description() ?: defaults.descriptionOverride),

            pokemonIconOverride = get("pokemonIcon", textSetting())
                .orElse(element?.pokemonIcon() ?: defaults.pokemonIconOverride),

            iconOverride = get("icon", questIconSetting())
                .orElse(
                    (element?.icon() as? ItemQuestIcon)
                        ?: (defaults.iconOverride as? ItemQuestIcon)
                        ?: ItemQuestIcon.AIR
                )
        )
    }
}
