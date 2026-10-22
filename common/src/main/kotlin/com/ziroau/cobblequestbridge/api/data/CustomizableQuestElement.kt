package com.ziroau.cobblequestbridge.api.data

import com.teamresourceful.resourcefullib.common.codecs.predicates.NbtPredicate
import earth.terrarium.heracles.api.quests.QuestIcon
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.Text

/**
 * Similar to [earth.terrarium.heracles.api.CustomizableQuestElement],
 * but with extended functionality for Cobblemon Quest Bridge.
 */
interface CustomizableQuestElement {
    fun title(): String
    fun description(): String
    fun pokemonIcon(): String
    fun icon(): QuestIcon<*>

    fun titleOr(overridden: Text): Text {
        return if (title().isNotEmpty()) {
            Text.translatable(title())
        } else {
            overridden
        }
    }

    fun descriptionOr(overridden: Text): Text {
        return if (description().isNotEmpty()) {
            Text.translatable(description())
        } else {
            overridden
        }
    }

    companion object {
        @JvmStatic
        fun nbtPredicate(): NbtPredicate {
            return NbtPredicate(NbtCompound())
        }
    }
}
