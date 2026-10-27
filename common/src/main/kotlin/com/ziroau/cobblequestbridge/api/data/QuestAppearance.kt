package com.ziroau.cobblequestbridge.api.data

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.api.quests.QuestIcons
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon

data class QuestAppearance(
    val titleOverride: String = "",
    val descriptionOverride: String = "",
    val pokemonIconOverride: String = "",
    val iconOverride: QuestIcon<*> = ItemQuestIcon.AIR
) {
    companion object {
        fun codec(defaults: QuestAppearance = QuestAppearance()) : MapCodec<QuestAppearance> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    Codec.STRING
                        .optionalFieldOf("title", defaults.titleOverride)
                        .forGetter(QuestAppearance::titleOverride),

                    Codec.STRING
                        .optionalFieldOf("description", defaults.descriptionOverride)
                        .forGetter(QuestAppearance::descriptionOverride),

                    Codec.STRING
                        .optionalFieldOf("pokemonIcon", defaults.pokemonIconOverride)
                        .forGetter(QuestAppearance::pokemonIconOverride),

                    QuestIcons.CODEC
                        .lenientOptionalFieldOf("icon", defaults.iconOverride)
                        .forGetter(QuestAppearance::iconOverride)
                ).apply(instance, ::QuestAppearance)
            }

        val CODEC: MapCodec<QuestAppearance> = codec()
    }
}
