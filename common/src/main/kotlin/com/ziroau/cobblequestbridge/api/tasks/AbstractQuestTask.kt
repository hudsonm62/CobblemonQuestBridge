package com.ziroau.cobblequestbridge.api.tasks

import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import earth.terrarium.heracles.api.tasks.QuestTask
import net.minecraft.nbt.NbtElement

interface AbstractQuestTask<I, S : NbtElement, T : AbstractQuestTask<I, S, T>> : QuestTask<I, S, T>, CustomizableQuestElement {
    val taskId: String
    override fun id(): String = taskId
}
