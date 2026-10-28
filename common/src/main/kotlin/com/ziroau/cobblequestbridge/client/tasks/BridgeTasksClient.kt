package com.ziroau.cobblequestbridge.client.tasks

import com.ziroau.cobblequestbridge.api.client.registry.BridgeTasksClientRegistry
import com.ziroau.cobblequestbridge.client.tasks.settings.BattleWinTaskSettings
import com.ziroau.cobblequestbridge.client.tasks.settings.PokedexProgressTaskSettings
import com.ziroau.cobblequestbridge.client.tasks.settings.PokemonCatchTaskSettings
import com.ziroau.cobblequestbridge.client.tasks.settings.PokemonDefeatTaskSettings
import com.ziroau.cobblequestbridge.client.tasks.settings.PokemonEvolutionTaskSettings
import com.ziroau.cobblequestbridge.client.tasks.settings.PokemonTradeTaskSettings
import com.ziroau.cobblequestbridge.client.tasks.widgets.BattleWinTaskWidget
import com.ziroau.cobblequestbridge.client.tasks.widgets.PokedexProgressTaskWidget
import com.ziroau.cobblequestbridge.client.tasks.widgets.PokemonCatchTaskWidget
import com.ziroau.cobblequestbridge.client.tasks.widgets.PokemonDefeatTaskWidget
import com.ziroau.cobblequestbridge.client.tasks.widgets.PokemonEvolutionTaskWidget
import com.ziroau.cobblequestbridge.client.tasks.widgets.PokemonTradeTaskWidget
import com.ziroau.cobblequestbridge.tasks.BattleWinTask
import com.ziroau.cobblequestbridge.tasks.PokedexProgressTask
import com.ziroau.cobblequestbridge.tasks.PokemonCatchTask
import com.ziroau.cobblequestbridge.tasks.PokemonDefeatTask
import com.ziroau.cobblequestbridge.tasks.PokemonEvolutionTask
import com.ziroau.cobblequestbridge.tasks.PokemonTradeTask

object BridgeTasksClient {
    fun register() {
        BridgeTasksClientRegistry.register(
            PokemonCatchTask.TYPE,
            PokemonCatchTaskSettings.INSTANCE,
            ::PokemonCatchTaskWidget
        ) { progress, task ->
            val caught = task.storage().readInt(progress.progress()).coerceAtLeast(0)
            "$caught/${task.amount.coerceAtLeast(1)}"
        }

        BridgeTasksClientRegistry.register(
            PokemonDefeatTask.TYPE,
            PokemonDefeatTaskSettings.INSTANCE,
            ::PokemonDefeatTaskWidget
        ) { progress, task ->
            val defeated = task.storage().readInt(progress.progress()).coerceAtLeast(0)
            "$defeated/${task.amount.coerceAtLeast(1)}"
        }

        BridgeTasksClientRegistry.register(
            PokemonEvolutionTask.TYPE,
            PokemonEvolutionTaskSettings.INSTANCE,
            ::PokemonEvolutionTaskWidget
        ) { progress, task ->
            val evolutions = task.storage().readInt(progress.progress()).coerceAtLeast(0)
            "$evolutions/${task.amount.coerceAtLeast(1)}"
        }

        BridgeTasksClientRegistry.register(
            BattleWinTask.TYPE,
            BattleWinTaskSettings.INSTANCE,
            ::BattleWinTaskWidget
        ) { progress, task ->
            val wins = task.storage().readInt(progress.progress()).coerceAtLeast(0)
            "$wins/${task.amount.coerceAtLeast(1)}"
        }

        BridgeTasksClientRegistry.register(
            PokedexProgressTask.TYPE,
            PokedexProgressTaskSettings.INSTANCE,
            ::PokedexProgressTaskWidget
        ) { progress, task ->
            val current = task.storage().readInt(progress.progress()).coerceAtLeast(0)
            "$current/${task.amount.coerceAtLeast(1)}"
        }

        BridgeTasksClientRegistry.register(
            PokemonTradeTask.TYPE,
            PokemonTradeTaskSettings.INSTANCE,
            ::PokemonTradeTaskWidget
        ) { progress, task ->
            val trades = task.storage().readInt(progress.progress()).coerceAtLeast(0)
            "$trades/${task.amount.coerceAtLeast(1)}"
        }
    }
}
