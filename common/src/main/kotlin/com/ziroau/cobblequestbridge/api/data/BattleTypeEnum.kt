package com.ziroau.cobblequestbridge.api.data

import com.cobblemon.mod.common.api.battles.model.PokemonBattle
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
        fun fromBattle(battle: PokemonBattle): BattleTypeEnum {
            return if(battle.isPvP)
                PVP
            else if(battle.isPvN)
                PVN
            else if(battle.isPvW)
                PVW
            else
                ANY
        }

        /**
         * Alternate translation for the battle type, used in certain contexts where a different wording is preferred.
         * For example, "PvW" might be translated instead as "Wild" for nicer display.
         */
        fun BattleTypeEnum.alternateTranslation(): Text {
            return Text.translatable("gui.cobblequestbridge.battletype.${this.name.lowercase()}.alternate")
        }
    }
}
