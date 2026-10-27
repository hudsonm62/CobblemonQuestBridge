package com.ziroau.cobblequestbridge.rewards

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.ziroau.cobblequestbridge.CobbleQuestBridge
import com.ziroau.cobblequestbridge.api.data.Helpers.parseSpeciesId
import com.ziroau.cobblequestbridge.api.data.QuestAppearance
import com.ziroau.cobblequestbridge.api.rewards.AbstractQuestReward
import earth.terrarium.heracles.api.rewards.QuestRewardType
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Formatting
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
data class PokemonReward(
    override val rewardId: String,
    val species: String = "pikachu",
    val attributes: String = "",
    val amount: Int = 1,
    val unclaimOnFail: Boolean = true,
    override val appearance: QuestAppearance
) : AbstractQuestReward<PokemonReward> {
    override fun reward(player: ServerPlayerEntity): Stream<ItemStack> {
        val speciesId = parseSpeciesId(species) ?: return Stream.empty()
        val pokemonSpecies = speciesId.let(PokemonSpecies::getByIdentifier)
        val name = speciesId.path

        if (name.isBlank() || name == "any" || pokemonSpecies == null) {
            CobbleQuestBridge.LOGGER.error(
                "[CQB] Cannot claim Pokémon Reward from '{}' for '{}': Invalid species '{}'",
                rewardId, player.gameProfile.name, species
            )
            if (unclaimOnFail)
                markUnclaimed(player)
            return Stream.empty()
        }

        val party = Cobblemon.storage.getParty(player)
        val pc = Cobblemon.storage.getPC(player)
        val count = amount.coerceAtLeast(1)

        // Check combined party and PC capacity.
        if(unclaimOnFail) {
            val partySpace = party.size() - party.occupied()
            val pcSpace = pc.boxes.sumOf { it.unoccupiedSlots }
            val availableSpace = partySpace + pcSpace
            if (availableSpace < count) {
                CobbleQuestBridge.LOGGER.warn(
                    "[CQB] Cannot deliver Pokémon reward '{}' to '{}': Insufficient storage (required: {} | available: {})",
                    rewardId, player.gameProfile.name, count, availableSpace
                )
                markUnclaimed(player)
                player.sendMessage(
                    Text.translatable("chat.cobblequestbridge.pokemon_reward.insufficient_space", count, pokemonSpecies.translatedName)
                        .formatted(Formatting.RED)
                )
                player.sendMessage(
                    Text.translatable("chat.cobblequestbridge.pokemon_reward.insufficient_space.hint")
                        .formatted(Formatting.RED)
                )
                player.closeHandledScreen()
                return Stream.empty()
            }
        }

        repeat(count) {
            // Reparse for each iteration: every awarded Pokémon is distinct
            val props = PokemonProperties.parse(attributes)
            // Species field takes precedence over a species embedded in properties.
            props.species = pokemonSpecies.resourceIdentifier.toString()
            props.updateAspects()
            val pokemon = props.create(player)
            party.add(pokemon)
        }

        // Heracles expects a stream of items for the toast for display purposes
        return Stream.empty()
    }

    companion object {
        @JvmField
        val TYPE: QuestRewardType<PokemonReward> = Type()
    }
    override fun type(): QuestRewardType<PokemonReward> = TYPE

    private class Type : QuestRewardType<PokemonReward> {
        override fun id(): Identifier = Identifier.of(CobbleQuestBridge.MOD_ID, "pokemon_reward")

        override fun codec(id: String): MapCodec<PokemonReward> =
            RecordCodecBuilder.mapCodec { instance ->
                instance.group(
                    RecordCodecBuilder.point<PokemonReward, String>(id),
                    Codec.STRING.fieldOf("species")
                        .forGetter(PokemonReward::species),

                    Codec.STRING.optionalFieldOf("attributes", "")
                        .forGetter(PokemonReward::attributes),

                    Codec.INT.optionalFieldOf("amount", 1)
                        .forGetter(PokemonReward::amount),

                    Codec.BOOL.optionalFieldOf("unclaimOnFail", true)
                        .forGetter(PokemonReward::unclaimOnFail),

                    QuestAppearance.CODEC
                        .forGetter(PokemonReward::appearance)

                ).apply(instance, ::PokemonReward)
            }
    }
}
