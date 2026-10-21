package com.ziroau.cobblequestbridge.tasks

import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER
import earth.terrarium.heracles.api.tasks.QuestTasks

object BridgeTasks {
    fun register() {
        val tasks = arrayListOf(
            PokemonCatchTask.TYPE,
            PokemonDefeatTask.TYPE
        )

        tasks.forEach { taskType ->
            val id = taskType.id()
            QuestTasks.register(taskType)

            LOGGER.info(
                "Registered CQB Quest Task: {}",
                id
            )
        }
    }
}
