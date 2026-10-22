package com.ziroau.cobblequestbridge.molang

import com.cobblemon.mod.common.api.molang.function.PlayerMoLangFunctions
import com.ziroau.cobblequestbridge.molang.functions.PlayerFunctions
import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER

// TODO: Make this with work Cobblemon 1.7
//// Doesn't work due to refactors in 1.8 - https://gitlab.com/cable-mc/cobblemon/-/commit/9ad39ffd8f2483d17976b1d3ae35a53ff8b51733
object MoLangFunctions {
    fun register() {
        PlayerMoLangFunctions.custom.add { player ->
            PlayerFunctions.attach(player)
        }
        LOGGER.info("Registered MoLang Quests functions")
    }
}
