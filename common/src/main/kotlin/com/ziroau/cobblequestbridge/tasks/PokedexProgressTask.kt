package com.ziroau.cobblequestbridge.tasks

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.events.pokemon.PokedexDataChangedEvent
import com.cobblemon.mod.common.api.pokedex.CaughtCount
import com.cobblemon.mod.common.api.pokedex.Dexes
import com.cobblemon.mod.common.api.pokedex.SeenCount
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.api.data.QuestAppearance
import com.ziroau.cobblequestbridge.api.tasks.CountedQuestTask
import earth.terrarium.heracles.api.tasks.QuestTaskType
import net.minecraft.nbt.AbstractNbtNumber
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import java.util.*

/**
 * [PokedexProgressTask] tracks Pokédex Completion (not new entries from task unlock like other tasks).
 * Progress is determined by the number of Pokémon caught or seen in a specified Pokédex - Meaning task could be immediately completed on unlock
 *
 * TODO: Needs more testing with "fakemon" and other modded Pokémon to ensure they are counted correctly, esp in the national dex
 *
 * @param dex Pokédex type to track progress for, default is "any" for all dexes
 * @param caughtOnly If true, only counts caught Pokémon; if false, counts both caught and seen/scanned Pokémon
 */
class PokedexProgressTask(
    override val taskId: String,
    val dex: String = "cobblemon:national",
    val caughtOnly: Boolean = false,
    override val amount: Int = 100,
    override val appearance: QuestAppearance
) : CountedQuestTask<PokedexDataChangedEvent, PokedexProgressTask> {
    // not using matches() here because we want to count all dex changes, not individual event fires
    override fun matches(input: PokedexDataChangedEvent): Boolean = true

    fun parseDexIdentifier(): Identifier? {
        val dexVal = if(dex.isEmpty()) "cobblemon:national" else dex.trim().lowercase()
        val dexName = if (':' in dexVal) dexVal else "cobblemon:$dexVal"
        return Identifier.tryParse(dexName)
    }

    private fun updatePokedexProgress(
        progress: AbstractNbtNumber, playerId: UUID
    ): AbstractNbtNumber {
        val pokedex = Cobblemon.playerDataManager
            .getPokedexData(playerId)

        val dexId = parseDexIdentifier() ?: Identifier.of("cobblemon", "national")

        val count = if (dexId.path.equals("national", ignoreCase = true)) {
            if (caughtOnly) {
                pokedex.getGlobalCalculatedValue(CaughtCount)
            } else {
                pokedex.getGlobalCalculatedValue(SeenCount)
            }
        } else {
            if (!Dexes.dexEntryMap.containsKey(dexId)) return progress

            if (caughtOnly)
                pokedex.getDexCalculatedValue(dexId, CaughtCount)
            else
                pokedex.getDexCalculatedValue(dexId, SeenCount)

        }

        val current = storage().readInt(progress)
        val updated = count.coerceIn(0, amount.coerceAtLeast(1))

        if (current == updated) return progress

        return storage().set(updated)
    }

    override fun init(
        type: QuestTaskType<*>,
        progress: AbstractNbtNumber,
        player: ServerPlayerEntity
    ): AbstractNbtNumber {
        return updatePokedexProgress(progress, player.uuid)
    }
    override fun test(
        type: QuestTaskType<*>,
        progress: AbstractNbtNumber,
        input: PokedexDataChangedEvent
    ): AbstractNbtNumber {
        return updatePokedexProgress(progress, input.playerUUID)
    }

    companion object {
        val DEFAULT_APPEARANCE = QuestAppearance(pokemonIconOverride = "pikachu")

        @JvmField
        val TYPE: QuestTaskType<PokedexProgressTask> = Type()
    }

    override fun type(): QuestTaskType<PokedexProgressTask> = TYPE

    private class Type : QuestTaskType<PokedexProgressTask> {
        override fun id(): Identifier = Identifier.of("cobblequestbridge", "pokedex_progress")

        override fun codec(id: String): MapCodec<PokedexProgressTask> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    RecordCodecBuilder.point<PokedexProgressTask, String>(id),

                    Codec.STRING
                        .optionalFieldOf("dex", "cobblemon:national")
                        .forGetter(PokedexProgressTask::dex),

                    Codec.BOOL
                        .optionalFieldOf("caughtOnly", false)
                        .forGetter(PokedexProgressTask::caughtOnly),

                    Codec.INT
                        .optionalFieldOf("amount", 100)
                        .forGetter(PokedexProgressTask::amount),

                    QuestAppearance
                        .codec(DEFAULT_APPEARANCE)
                        .forGetter(PokedexProgressTask::appearance)
                ).apply(instance, ::PokedexProgressTask)
            }
    }
}
