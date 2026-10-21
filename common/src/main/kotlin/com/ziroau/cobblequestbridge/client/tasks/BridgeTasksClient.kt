package com.ziroau.cobblequestbridge.client.tasks

import com.ziroau.cobblequestbridge.client.tasks.settings.PokemonCatchTaskSettings
import com.ziroau.cobblequestbridge.client.tasks.widgets.PokemonCatchTaskWidget
import com.ziroau.cobblequestbridge.tasks.PokemonCatchTask
import earth.terrarium.heracles.api.client.settings.Settings
import earth.terrarium.heracles.api.tasks.QuestTaskDisplayFormatter
import earth.terrarium.heracles.api.tasks.client.QuestTaskWidgets

object BridgeTasksClient {

    fun register() {
        registerCatchTask()
    }

    private fun registerCatchTask() {
        Settings.register(
            PokemonCatchTask.TYPE,
            PokemonCatchTaskSettings.INSTANCE
        )

        QuestTaskWidgets.registerSimple(
            PokemonCatchTask.TYPE,
            ::PokemonCatchTaskWidget
        )

        QuestTaskDisplayFormatter.register(
            PokemonCatchTask.TYPE
        ) { progress, task ->
            val caught = task.storage()
                .readInt(progress.progress())
                .coerceAtLeast(0)

            "$caught/${task.amount.coerceAtLeast(1)}"
        }
    }
}
