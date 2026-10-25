package com.ziroau.cobblequestbridge.molang.functions

import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.value.DoubleValue
import com.cobblemon.mod.common.api.molang.function.AbstractMoLangFunctionHolder
import earth.terrarium.heracles.api.events.HeraclesEvents
import earth.terrarium.heracles.api.events.TaskEventTarget
import earth.terrarium.heracles.api.tasks.defaults.DummyTask
import earth.terrarium.heracles.common.handlers.progress.QuestProgressHandler
import earth.terrarium.heracles.common.handlers.quests.QuestHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.entity.player.PlayerEntity as Player

/**
 * Collection of MoLang functions that can be used in player context
 * Used to interact with quests and tasks in molang code in Cobblemon.
 *
 * Still experimental/wip, and subject to change in the future (of course, with ample notification).
 */
object PlayerFunctions : AbstractMoLangFunctionHolder<Player>() {
    override fun Player.moLangFunctions(): MutableMap<String, (MoParams) -> Any> {
        val player = this

        fun func1(
            action: (ServerPlayerEntity, String) -> Any
        ): (MoParams) -> Any = func1@ { params ->
            val serverPlayer = player as? ServerPlayerEntity
                ?: return@func1 DoubleValue.ZERO
            action(serverPlayer, params.getString(0))
        }
        fun func2(
            action: (ServerPlayerEntity, String, String) -> Any
        ): (MoParams) -> Any = func2@ { params ->
            val serverPlayer = player as? ServerPlayerEntity
                ?: return@func2 DoubleValue.ZERO
            action(serverPlayer, params.getString(0), params.getString(1))
        }

        return hashMapOf(
            // Tasks (a single objective, in any quest)

            "complete_quest_task" to func2 { player, questId, taskId ->
                /// Completes a specific task in a specific quest, regardless of the task type
                //// Warning: This ignores tasks requirements

                val quest = QuestHandler.get(questId)
                    ?: return@func2 DoubleValue.ZERO

                val task = quest.tasks()[taskId]
                    ?: return@func2 DoubleValue.ZERO

                val questsProgress = QuestProgressHandler.getProgress(
                    player.server, player.uuid
                )

                val questProgress = questsProgress.getProgress(questId)
                val taskProgress = questProgress.getTask(task)

                if (taskProgress.isComplete) {
                    return@func2 DoubleValue.ZERO
                }

                // Force completion regardless of task type
                taskProgress.isComplete = true

                // Trigger Heracles' task completion event
                HeraclesEvents.TaskCompleteListener.fire(
                    TaskEventTarget.create(task, player)
                )

                // Recalculate quest completion, unlocks and team progress
                questsProgress.sendOutQuestChanged(
                    questId, quest, questProgress, player
                )

                // Sync updated progress to the client
                QuestProgressHandler.sync(player, listOf(questId))

                // sanity return
                if(taskProgress.isComplete)
                    return@func2 DoubleValue.ONE

                DoubleValue.ZERO
            },
            "has_completed_quest_task" to func2 { player, questId, taskId ->
                /// Checks if a specific task in a specific quest is completed
                val questsProgress = QuestProgressHandler.getProgress(player.server, player.uuid)
                val questProgress = questsProgress.getProgress(questId)
                val task = QuestHandler.get(questId)?.tasks()?.get(taskId) ?: return@func2 DoubleValue.ZERO

                val taskProgress = questProgress?.getTask(task)

                if (taskProgress != null && taskProgress.isComplete)
                    return@func2 DoubleValue.ONE

                DoubleValue.ZERO
            },

            // Rewards (collection of rewards in a quest once tasks are marked completed)
            "claim_quest_reward" to func2 { player, questId, rewardId ->
                /// Claims a single reward for a specific quest, if all tasks are completed
                val questObj = QuestHandler.get(questId) ?: return@func2 DoubleValue.ZERO
                questObj.claimAllowedReward(player, questId, rewardId)
            },
            "claim_quest_rewards" to func1 { player, questId ->
                /// Claims all rewards for a specific quest, if all tasks are completed
                val questObj = QuestHandler.get(questId) ?: return@func1 DoubleValue.ZERO
                questObj.claimAllowedRewards(player, questId)
            },

            // Quests (a container of tasks & rewards)
            "complete_quest" to func1 { player, questId ->
                /// Forcefully Completes a quest
                //// Warning: This ignores tasks requirements
                val questObj = QuestHandler.get(questId) ?: return@func1 DoubleValue.ZERO
                QuestProgressHandler.getProgress(player.server, player.uuid)
                    .completeQuest(questId, questObj, player)
            },

            "has_completed_quest" to func1 { player, questId ->
                /// Checks if the player has completed a quest
                val questsProgress = QuestProgressHandler.getProgress(player.server, player.uuid)
                if (questsProgress.isComplete(questId))
                    return@func1 DoubleValue.ONE

                DoubleValue.ZERO
            },

            "is_active_quest" to func1 { player, questId ->
                /// Checks if a quest is active for the player (not completed and started)
                val questsProgress = QuestProgressHandler.getProgress(player.server, player.uuid)
                val questProgress = questsProgress.getProgress(questId)

                if (questProgress != null && !questProgress.isComplete)
                    return@func1 DoubleValue.ONE

                DoubleValue.ZERO
            }
        )
    }
}
