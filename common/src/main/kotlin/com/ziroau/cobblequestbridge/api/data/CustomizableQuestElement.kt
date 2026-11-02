package com.ziroau.cobblequestbridge.api.data

import com.ziroau.iolaus.api.data.CustomizableQuestElementExtended

/**
 * An extension of [CustomizableQuestElementExtended] to support a [pokemonIcon] in all task/reward elements.
 */
interface CustomizableQuestElement : CustomizableQuestElementExtended {
    override val display: CobblemonQuestElementDisplay

    /**
     * The Pokémon species string to use as the widget icon.
     * Supports Cobblemon attributes (e.g. `vaporeon shiny=yes`).
     */
    fun pokemonIcon(): String = display.pokemonIconOverride
}
