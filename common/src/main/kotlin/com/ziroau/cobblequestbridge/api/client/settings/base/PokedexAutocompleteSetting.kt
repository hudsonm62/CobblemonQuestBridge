package com.ziroau.cobblequestbridge.api.client.settings.base

import com.cobblemon.mod.common.api.pokedex.Dexes
import com.ziroau.iolaus.api.client.settings.base.BaseAutocompleteSetting

/**
 * Provides a [BaseAutocompleteSetting] Setting for Pokédex Regions
 */
class PokedexAutocompleteSetting : BaseAutocompleteSetting(false) {
    override val defaultValue: String = "cobblemon:national"
    override val suggestions: List<String> = Dexes.dexEntryMap.keys.map { it.toString() }
    companion object {
        @JvmField
        val INSTANCE = PokedexAutocompleteSetting()
    }
}
