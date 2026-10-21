package com.ziroau.cobblequestbridge.molang

import com.cobblemon.mod.common.api.molang.function.PlayerMoLangFunctions
import com.ziroau.cobblequestbridge.molang.functions.PlayerFunctions
import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER

object MoLangFunctions {
    fun register() {
        PlayerMoLangFunctions.custom.add { player ->
            PlayerFunctions.attach(player)
        }
        LOGGER.info("Registered MoLang Quests functions")
    }
}
