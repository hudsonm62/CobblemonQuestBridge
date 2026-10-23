package com.ziroau.cobblequestbridge.api.molang

import com.bedrockk.molang.runtime.MoLangRuntime
import com.cobblemon.mod.common.api.molang.MoLangFunctions.asMoLangValue
import com.cobblemon.mod.common.api.molang.MoLangFunctions.asMostSpecificMoLangValue
import com.cobblemon.mod.common.api.molang.MoLangFunctions.setup
import com.cobblemon.mod.common.api.scripting.CobblemonScripts
import com.cobblemon.mod.common.util.asExpressionLike
import com.cobblemon.mod.common.util.resolve
import com.cobblemon.mod.common.util.withPlayerValue
import com.cobblemon.mod.common.util.withQueryValue
import com.ziroau.cobblequestbridge.CobbleQuestBridge.LOGGER
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier

object MoLangExecutor {
    /**
     * Executes a list of MoLang commands.
     * Will treat the [commandList] as a single script and execute it in the context of the [player].
     */
    fun execute(commandList: List<String>, player: ServerPlayerEntity) {
        if (commandList.all { it.isBlank() }) return
        try {
            val runtime = runtime(player)

            runtime.resolve(
                commandList.asExpressionLike(),
                mapOf("player" to player.asMoLangValue())
            )
        } catch (e: Exception) {
            LOGGER.error("Error executing MoLang Scripts for ${player.gameProfile.name}", e)
        }
    }
    /**
     * Executes a MoLang script by its [Identifier].
     * Runs in context of the [player].
     */
    fun execute(scriptId: Identifier, player: ServerPlayerEntity) {
        try {
            val runtime = runtime(player)
            CobblemonScripts.run(scriptId, runtime)
        } catch (e: Exception) {
            // Log the error with the script ID and player name
            LOGGER.error("Error executing MoLang Script '$scriptId', for ${player.gameProfile.name}", e)
        }
    }

    /**
     * Creates a new [MoLangRuntime] for the given [player].
     *
     * Automatically garbage collects the runtime after execution, so it should not be stored or reused outside the execution context.
     */
    fun runtime(player: ServerPlayerEntity): MoLangRuntime {
        return MoLangRuntime()
            .setup()
            .withPlayerValue(value = player)
            .withQueryValue("entity", player.asMostSpecificMoLangValue())
    }
}
