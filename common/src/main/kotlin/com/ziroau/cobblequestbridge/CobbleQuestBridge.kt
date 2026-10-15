package com.ziroau.cobblequestbridge

import org.apache.logging.log4j.LogManager

object CobbleQuestBridge {
    const val MOD_ID = "cobblequestbridge"
    val LOGGER = LogManager.getLogger(MOD_ID)

    fun init() {
        LOGGER.info("Cobble Quest Bridge initialized - Happy questing!")
    }
}
