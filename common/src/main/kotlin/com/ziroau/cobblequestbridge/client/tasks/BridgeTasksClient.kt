package com.ziroau.cobblequestbridge.client.tasks

import com.ziroau.cobblequestbridge.client.tasks.settings.BattleWinTaskSettings
import com.ziroau.cobblequestbridge.client.tasks.settings.PokemonCatchTaskSettings
import com.ziroau.cobblequestbridge.client.tasks.settings.PokemonDefeatTaskSettings
import com.ziroau.cobblequestbridge.client.tasks.widgets.BattleWinTaskWidget
import com.ziroau.cobblequestbridge.client.tasks.widgets.PokemonCatchTaskWidget
import com.ziroau.cobblequestbridge.client.tasks.widgets.PokemonDefeatTaskWidget
import com.ziroau.cobblequestbridge.tasks.BattleWinTask
import com.ziroau.cobblequestbridge.tasks.PokemonCatchTask
import com.ziroau.cobblequestbridge.tasks.PokemonDefeatTask
import earth.terrarium.heracles.api.client.settings.Settings
import earth.terrarium.heracles.api.tasks.QuestTaskDisplayFormatter
import earth.terrarium.heracles.api.tasks.client.QuestTaskWidgets

object BridgeTasksClient {

    fun register() {
        registerCatchTask()
        registerDefeatTask()
        registerBattleWinTask()
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

    private fun registerDefeatTask() {
        Settings.register(
            PokemonDefeatTask.TYPE,
            PokemonDefeatTaskSettings.INSTANCE
        )
        QuestTaskWidgets.registerSimple(
            PokemonDefeatTask.TYPE,
            ::PokemonDefeatTaskWidget
        )
        QuestTaskDisplayFormatter.register(
            PokemonDefeatTask.TYPE
        ) { progress, task ->
            val defeated = task.storage()
                .readInt(progress.progress())
                .coerceAtLeast(0)

            "$defeated/${task.amount.coerceAtLeast(1)}"
        }
    }

    private fun registerBattleWinTask() {
        Settings.register(
                BattleWinTask.TYPE,
                BattleWinTaskSettings.INSTANCE
            )
            QuestTaskWidgets.registerSimple(
                BattleWinTask.TYPE,
                ::BattleWinTaskWidget
            )
            QuestTaskDisplayFormatter.register(
                BattleWinTask.TYPE
            ) { progress, task ->
                val wins = task.storage()
                    .readInt(progress.progress())
                    .coerceAtLeast(0)

                "$wins/${task.amount.coerceAtLeast(1)}"
            }
    }
}
