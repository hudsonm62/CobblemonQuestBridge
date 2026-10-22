package com.ziroau.cobblequestbridge.api.registry

import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER
import earth.terrarium.heracles.api.tasks.QuestTask
import earth.terrarium.heracles.api.tasks.QuestTaskType
import earth.terrarium.heracles.api.tasks.QuestTasks
import net.minecraft.nbt.NbtElement

/**
 * Functions to quickly register custom quest tasks to Heracles
 *
 * Everything here should be called during initialization.
 */
object BridgeTasksRegistry {

    /**
     * Register a custom quest task type
     */
    @JvmStatic
    fun <I, S : NbtElement, T : QuestTask<I, S, T>> register(type: QuestTaskType<T>) {
        QuestTasks.register(type)
        logDebug(type.id().toString())
    }

    /**
     * Register multiple quest task types at once
     *
     * @sample com.ziroau.cobblequestbridge.tasks.BridgeTasks.register()
     */
    @JvmStatic
    fun registerAll(vararg types: QuestTaskType<*>) {
        types.forEach { type ->
            QuestTasks.register(type)
            logDebug(type.id().toString())
        }
    }

    private fun logDebug(id: String) {
        LOGGER.info(
            "Registered CQB Quest Task: {}",
            id
        )
    }
}
