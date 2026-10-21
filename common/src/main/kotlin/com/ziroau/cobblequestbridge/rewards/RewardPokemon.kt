package com.ziroau.cobblequestbridge.rewards

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.CobbleQuestBridge
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
 * [properties] accepts the same Cobblemon property syntax as the catch task,
 * for example "shiny=yes level=50 gender=female".
 *
 * The properties are parsed separately for each Pokémon, so unforced values
 * (IVs, gender, shininess, etc.) are rolled independently.
 */
data class RewardPokemon(
    private val rewardId: String,
    val species: String = "pikachu",
    val properties: String = "",
    val amount: Int = 1,
    val title: String = "",
    val description: String = ""
) : QuestReward<RewardPokemon> {

    override fun id(): String = rewardId

    override fun type(): QuestRewardType<RewardPokemon> = TYPE

    override fun reward(player: ServerPlayerEntity): Stream<ItemStack> {
        val name = species.trim().lowercase()
        val speciesId = Identifier.tryParse(
            if (':' in name) name else "cobblemon:$name"
        )
        val pokemonSpecies = speciesId?.let(PokemonSpecies::getByIdentifier)

        if (name.isBlank() || name == "any" || pokemonSpecies == null) {
            CobbleQuestBridge.LOGGER.error(
                "[CQB] Cannot claim Pokémon reward {}: invalid species '{}'",
                rewardId, species
            )
            return Stream.empty()
        }

        // TODO: Validate party/pc space before generating Pokémon, to avoid wasting them.

        val party = Cobblemon.storage.getParty(player)
        val count = amount.coerceAtLeast(1)

        for (index in 0 until count) {
            // Reparse for each iteration: every awarded Pokémon is distinct.
            val props = PokemonProperties.parse(properties)
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
                    Codec.STRING.optionalFieldOf("species", "pikachu")
                        .forGetter(RewardPokemon::species),
                    Codec.STRING.optionalFieldOf("properties", "")
                        .forGetter(RewardPokemon::properties),
                    Codec.INT.optionalFieldOf("amount", 1)
                        .forGetter(RewardPokemon::amount),
                    Codec.STRING.optionalFieldOf("title", "")
                        .forGetter(RewardPokemon::title),
                    Codec.STRING.optionalFieldOf("description", "")
                        .forGetter(RewardPokemon::description),
                ).apply(instance) { rewardId, species, properties, amount, title, description ->
                    RewardPokemon(rewardId, species, properties, amount, title, description)
                }
            }
    }
}
