package com.ziroau.cobblequestbridge.api.data

import net.minecraft.text.Text

/**
 * Same as [earth.terrarium.heracles.api.CustomizableQuestElement], but extended for common CQB customization.
 */
interface CustomizableQuestElement : earth.terrarium.heracles.api.CustomizableQuestElement {
    /**
     * The description of the element, used in the Quest Viewer.
     * Supports translatable text if used with [descriptionOr]
     */
    fun description(): String

    /**
     * The string of a Pokémon to use for the icon of the element.
     * Should support Cobblemon attributes.
     *
     * Sample: `vaporeon shiny=yes`
     */
    fun pokemonIcon(): String

    /**
     * The description if it is not empty, otherwise returns the overridden description.
     */
    fun descriptionOr(overridden: Text): Text {
        return if (description().isNotEmpty()) {
            Text.translatable(description())
        } else {
            overridden
        }
    }
}
