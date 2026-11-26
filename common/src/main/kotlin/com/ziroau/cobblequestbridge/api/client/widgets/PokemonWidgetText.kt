package com.ziroau.cobblequestbridge.api.client.widgets

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.ziroau.cobblequestbridge.api.data.Helpers.parseSpeciesId
import net.minecraft.text.Text

object PokemonWidgetText {
    /**
     * Quick helper to get translated texts for tasks/rewards where it needs to be pluralized based on the amount of xyz.
     */
    fun pluralizedTranslationText(
        custom: String?, amount: Int, translationKey: String, vararg args: Any
    ): Text = when {
        !custom.isNullOrEmpty() -> Text.literal(custom)
        amount > 1 -> Text.translatable("$translationKey.plural", amount, *args)
        else -> Text.translatable(translationKey, *args)
    }

    /**
     * Gets the translated name of a Pokémon species based on its identifier.
     * These are pulled directly from Cobblemon.
     */
    fun getPokemonName(species: String): String {
        val name = species.trim().lowercase()
        if (name.isBlank() || name.equals("any", true)) return Text.translatable("cobblemon.ui.pokemon").string
        return parseSpeciesId(name)?.let(PokemonSpecies::getByIdentifier)
            ?.translatedName?.string ?: name.replaceFirstChar { it.uppercase() }
    }
}
