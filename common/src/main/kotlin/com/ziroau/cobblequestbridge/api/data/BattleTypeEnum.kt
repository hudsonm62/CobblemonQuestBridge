package com.ziroau.cobblequestbridge.api.data

import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.StringIdentifiable

/**
 * Enum representing different types of Pokémon battle types.
 * Follows the Cobblemon naming conventions.
 *
 * PVW: Player vs Wild
 * PVP: Player vs Player
 * PVN: Player vs NPC
 * ANY: Any type of battle
 */
enum class BattleTypeEnum : StringIdentifiable {
    ANY,
    PVW,
    PVN,
    PVP;

    override fun asString(): String {
        return Text.translatable("gui.cobblequestbridge.battletype.${name.lowercase()}").string
    }

    companion object {
        /**
         * Returns the corresponding [BattleTypeEnum] for a given [PokemonBattle] instance.
         * If the battle type does not match any known types, it defaults to [ANY].
         *
         * @param battle The [PokemonBattle] instance to evaluate.
         * @return The corresponding [BattleTypeEnum].
         */
        fun fromBattle(battle: PokemonBattle): BattleTypeEnum {
            return when {
                battle.isPvP -> PVP
                battle.isPvN -> PVN
                battle.isPvW -> PVW
                else -> ANY
            }
        }

        /**
         * Alternate translation for the battle type, used in certain contexts where a different wording is preferred.
         * For example, "PvW" might be translated instead as "Wild" for nicer display.
         *
         * @receiver The [BattleTypeEnum] instance to translate.
         * @return The alternate translation as a [MutableText].
         */
        fun BattleTypeEnum.alternateTranslation(): MutableText {
            return Text.translatable("gui.cobblequestbridge.battletype.${this.name.lowercase()}.alternate")
        }
    }
}
