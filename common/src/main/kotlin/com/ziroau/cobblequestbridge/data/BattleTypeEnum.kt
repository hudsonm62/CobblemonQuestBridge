package com.ziroau.cobblequestbridge.data

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
}
