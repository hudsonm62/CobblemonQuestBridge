package com.ziroau.cobblequestbridge.api.client.settings

import com.ziroau.cobblequestbridge.api.client.settings.base.PokedexAutocompleteSetting
import com.ziroau.cobblequestbridge.api.client.settings.base.PokemonSpeciesAutocompleteSetting
import com.ziroau.cobblequestbridge.api.data.BattleTypeEnum
import com.ziroau.cobblequestbridge.api.data.CobblemonQuestElementDisplay
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import earth.terrarium.heracles.api.client.settings.SettingInitializer
import earth.terrarium.heracles.api.client.settings.base.EnumSetting
import com.ziroau.iolaus.api.client.settings.AbstractSettingInitializer as IolausAbstractSettingInitializer

/**
 * An extension of [IolausAbstractSettingInitializer] that adds common CQB settings (autocomplete, enums, etc.).
 * Also provides a helpers ([putAppearance] & [getAppearance]) for reading and writing [CobblemonQuestElementDisplay] settings.
 */
abstract class AbstractSettingInitializer<T> : IolausAbstractSettingInitializer<T>() {
    protected fun anySpeciesSetting() = PokemonSpeciesAutocompleteSetting.WITH_ANY
    protected fun speciesSetting() = PokemonSpeciesAutocompleteSetting.ONLY_REQUIRED
    protected fun dexSetting() = PokedexAutocompleteSetting.INSTANCE
    protected fun battleTypeSetting() = battleType
    private val battleType = EnumSetting(BattleTypeEnum::class.java, BattleTypeEnum.ANY)

    /**
     * Adds the full [CobblemonQuestElementDisplay] settings.
     */
    protected fun SettingInitializer.CreationData.putAppearance(
        element: CustomizableQuestElement?, defaults: CobblemonQuestElementDisplay = CobblemonQuestElementDisplay()
    ) {
        // Common display settings from Iolaus
        putDisplaySettings(element, defaults.base)

        // CQB specific display settings
        put("pokemonIcon", textSetting(), element?.pokemonIcon() ?: defaults.pokemonIconOverride)
    }

    /**
     * Reads the full [CobblemonQuestElementDisplay] settings.
     */
    protected fun SettingInitializer.Data.getAppearance(
        element: CustomizableQuestElement?, defaults: CobblemonQuestElementDisplay = CobblemonQuestElementDisplay()
    ): CobblemonQuestElementDisplay {
        return CobblemonQuestElementDisplay(
            base = getDisplaySettings(element, defaults.base),
            pokemonIconOverride = get("pokemonIcon", textSetting())
                .orElse(element?.pokemonIcon() ?: defaults.pokemonIconOverride)
        )
    }
}
