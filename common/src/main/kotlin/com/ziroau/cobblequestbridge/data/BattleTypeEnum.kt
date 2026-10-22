package com.ziroau.cobblequestbridge.data

import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import net.minecraft.text.Text
import net.minecraft.util.StringIdentifiable

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

        fun BattleTypeEnum.alternateTranslation(): Text {
            return Text.translatable("gui.cobblequestbridge.battletype.${this.name.lowercase()}.alternate")
        }
    }
}
