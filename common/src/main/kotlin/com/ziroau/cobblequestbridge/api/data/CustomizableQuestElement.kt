package com.ziroau.cobblequestbridge.api.data

import earth.terrarium.heracles.api.quests.QuestIcon
import net.minecraft.text.Text

/**
 * Same as [earth.terrarium.heracles.api.CustomizableQuestElement], but extended for common CQB customization.
 */
interface CustomizableQuestElement : earth.terrarium.heracles.api.CustomizableQuestElement {
    val appearance: QuestAppearance

    override fun title(): String = appearance.titleOverride

    /**
     * The description of the element, used in the Quest Viewer.
     * Supports translatable text if used with [descriptionOr]
     */
    fun description(): String = appearance.descriptionOverride

    /**
     * The string of a Pokémon to use for the icon of the element.
     * Should support Cobblemon attributes.
     *
     * Sample: `vaporeon shiny=yes`
     */
    fun pokemonIcon(): String = appearance.pokemonIconOverride

    override fun icon(): QuestIcon<*> = appearance.iconOverride

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
