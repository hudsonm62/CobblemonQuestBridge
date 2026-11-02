package com.ziroau.cobblequestbridge.api.data

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.iolaus.api.data.BasicQuestElementDisplay
import com.ziroau.iolaus.api.data.QuestElementDisplay

data class CobblemonQuestElementDisplay(
    val base: BasicQuestElementDisplay = BasicQuestElementDisplay(),
    val pokemonIconOverride: String = "",
)  : QuestElementDisplay by base {
    companion object {
        fun codec(
            defaults: CobblemonQuestElementDisplay = CobblemonQuestElementDisplay()
        ): MapCodec<CobblemonQuestElementDisplay> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    BasicQuestElementDisplay
                        .codec(defaults.base)
                        .forGetter(CobblemonQuestElementDisplay::base),

                    Codec.STRING
                        .optionalFieldOf(
                            "pokemonIcon",
                            defaults.pokemonIconOverride
                        )
                        .forGetter(CobblemonQuestElementDisplay::pokemonIconOverride)
                ).apply(instance, ::CobblemonQuestElementDisplay)
            }

        val CODEC = codec()
    }
}
