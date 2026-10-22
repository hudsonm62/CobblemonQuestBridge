package com.ziroau.cobblequestbridge.rewards

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.CobbleQuestBridge
import com.ziroau.cobblequestbridge.api.data.CustomizableQuestElement
import com.ziroau.cobblequestbridge.api.data.Helpers.parseSpeciesId
import com.ziroau.cobblequestbridge.tasks.PokemonCatchTask
import earth.terrarium.heracles.api.quests.QuestIcon
import earth.terrarium.heracles.api.quests.QuestIcons
import earth.terrarium.heracles.api.quests.defaults.ItemQuestIcon
import earth.terrarium.heracles.api.rewards.QuestReward
import earth.terrarium.heracles.api.rewards.QuestRewardType
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import java.util.stream.Stream

/**
 * Gives [amount] newly generated Pokémon to the player when claimed.
 *
 * [species] is required (unlike a catch task, "any" is not meaningful here).
 *
 * The properties are parsed separately for each Pokémon, so unforced values
 * (IVs, gender, shininess, etc.) are rolled independently.
 */
data class RewardPokemon(
    private val rewardId: String,
    val species: String = "pikachu",
    val attributes: String = "",
    val amount: Int = 1,
    val titleOverride: String,
    val descriptionOverride: String,
    val pokemonIconOverride: String,
    val iconOverride: QuestIcon<*> = ItemQuestIcon.AIR
) : QuestReward<RewardPokemon>, CustomizableQuestElement {

    override fun id(): String = rewardId
    override fun type(): QuestRewardType<RewardPokemon> = TYPE

    override fun title(): String = titleOverride
    override fun description(): String = descriptionOverride
    override fun pokemonIcon(): String = pokemonIconOverride
    override fun icon(): QuestIcon<*> = iconOverride

    override fun reward(player: ServerPlayerEntity): Stream<ItemStack> {
        val name = species.trim().lowercase()
        val speciesId = parseSpeciesId(name)
        val pokemonSpecies = speciesId?.let(PokemonSpecies::getByIdentifier)

        if (name.isBlank() || name == "any" || pokemonSpecies == null) {
            CobbleQuestBridge.LOGGER.error(
                "[CQB] Cannot claim Pokémon Reward from '{}' for '{}': Invalid species '{}'",
                rewardId, player.gameProfile.name, species
            )
            return Stream.empty()
        }

        // TODO: Validate party/pc space before generating Pokémon, to avoid wasting them.

        val party = Cobblemon.storage.getParty(player)
        val count = amount.coerceAtLeast(1)

        for (index in 0 until count) {
            // Reparse for each iteration: every awarded Pokémon is distinct.
            val props = PokemonProperties.parse(attributes)
            // Species field takes precedence over a species embedded in properties.
            props.species = pokemonSpecies.resourceIdentifier.toString()
            props.updateAspects()

            val pokemon = props.create(player)
            // PlayerPartyStore.add() automatically tries the PC when party is full.
            if (!party.add(pokemon)) {
                CobbleQuestBridge.LOGGER.error(
                    "[CQB] Cannot deliver Pokémon reward {} to {}: " +
                        "party and PC are full (delivered {}/{})",
                    rewardId, player.gameProfile.name, index, count
                )
                break
            }
        }

        // Heracles expects a stream of items for the toast for display purposes
        return Stream.empty()
    }

    companion object {
        @JvmField
        val TYPE: QuestRewardType<RewardPokemon> = Type()
    }

    private class Type : QuestRewardType<RewardPokemon> {
        override fun id(): Identifier = Identifier.of(CobbleQuestBridge.MOD_ID, "pokemon_reward")

        override fun codec(id: String): MapCodec<RewardPokemon> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    RecordCodecBuilder.point<RewardPokemon, String>(id),
                    Codec.STRING.fieldOf("species")
                        .forGetter(RewardPokemon::species),

                    Codec.STRING.optionalFieldOf("attributes", "")
                        .forGetter(RewardPokemon::attributes),

                    Codec.INT.optionalFieldOf("amount", 1)
                        .forGetter(RewardPokemon::amount),

                    Codec.STRING.optionalFieldOf("title", "")
                        .forGetter(RewardPokemon::titleOverride),

                    Codec.STRING.optionalFieldOf("description", "")
                        .forGetter(RewardPokemon::descriptionOverride),

                    Codec.STRING.optionalFieldOf("pokemonIcon", "")
                        .forGetter(RewardPokemon::pokemonIconOverride),

                    QuestIcons.CODEC
                        .lenientOptionalFieldOf("icon", ItemQuestIcon.AIR)
                        .forGetter(RewardPokemon::iconOverride)

                ).apply(instance) { rewardId, species, attributes, amount, title, description, pokemonIcon, icon ->
                    RewardPokemon(rewardId, species, attributes, amount, title, description, pokemonIcon, icon)
                }
            }
    }
}
