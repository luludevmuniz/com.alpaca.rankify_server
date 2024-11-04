package com.example.db.dao

import com.example.db.entities.PlayerEntity
import com.example.db.tables.PlayersTable
import com.example.domain.dtos.CreatePlayerDTO
import com.example.domain.dtos.PlayerDTO
import com.example.domain.dtos.UpdatePlayerDTO
import com.example.domain.mappers.toDto
import org.jetbrains.exposed.sql.transactions.transaction

object PlayerDao {

    /**
     * Creates a new player and adds them to the specified ranking.
     *
     * This function creates a new player entity with the provided information,
     * associates it with the specified ranking, and updates the ranking positions
     * of all players in that ranking.
     *
     * @param player The data transfer object containing the player information.
     * @return The newly created player as a data transfer object.
     *
     * @throws NoSuchElementException If the specified ranking ID does not exist.
     */
    fun createPlayer(player: CreatePlayerDTO): PlayerDTO {
        val ranking = RankingDao.getRankingEntity(rankingId = player.remoteRankingId ?: throw NoSuchElementException())
        val newPlayer = transaction {
            PlayerEntity.new {
                name = player.name
                score = player.score
                currentRankingPosition = -1
                previousRankingPosition = -1
                rankingEntity = ranking
            }
        }
        updatePlayerRankingPositions(rankingId = player.remoteRankingId)
        return getPlayerById(newPlayer.id.value).toDto()
    }

    /**
     * Creates new players and associates them with a ranking.
     *
     * This function iterates through a list of [CreatePlayerDTO] objects, creating a new [PlayerEntity] for each one.
     * The new players are associated with the ranking specified by [rankingId].
     * After creating the players, the ranking positions are updated using [updatePlayerRankingPositions].
     *
     * @param players A list of [CreatePlayerDTO] objects representing the players to be created.
     * @param rankingId The ID of the ranking to associate the players with.
     *
     * @return The result of the transaction.
     */
    fun createPlayers(players: List<CreatePlayerDTO>, rankingId: Int) = transaction {
        val ranking = RankingDao.getRankingEntity(rankingId = rankingId)
        players.forEach { player ->
            PlayerEntity.new {
                name = player.name
                score = player.score
                currentRankingPosition = -1
                previousRankingPosition = -1
                rankingEntity = ranking
            }
        }
        updatePlayerRankingPositions(rankingId)
    }

    /**
     * Updates a player's information and recalculates ranking positions within their ranking.
     *
     * @param updatedPlayer The DTO containing the updated player information.
     * @throws NoSuchElementException If no player with the given ID is found.
     *
     * This function performs the following actions:
     * 1. Retrieves the player entity from the database using the provided ID.
     * 2. Updates the player's name and score with the values provided in the DTO.
     * 3. Saves the updated player entity back to the database.
     * 4. Triggers the recalculation of ranking positions for all players within the same ranking as the updated player.
     *
     * If no player with the provided ID is found, a NoSuchElementException is thrown.
     */
    fun updatePlayer(updatedPlayer: UpdatePlayerDTO) = transaction {
        val player = PlayerEntity.findByIdAndUpdate(id = updatedPlayer.id) { player ->
            player.name = updatedPlayer.name
            player.score = updatedPlayer.score
        } ?: throw NoSuchElementException()

        updatePlayerRankingPositions(player.rankingEntity.id.value)
    }

    /**
     * Deletes a player from the database and updates the ranking positions of the remaining players in the ranking.
     *
     * @param id The ID of the player to delete.
     * @throws NoSuchElementException If a player with the given ID is not found.
     */
    fun deletePlayer(id: Int) = transaction {
        val player = PlayerEntity.findById(id) ?: throw NoSuchElementException()
        val rankingId = player.rankingEntity.id.value
        player.delete()

        updatePlayerRankingPositions(rankingId)
    }

    /**
     * Retrieves a PlayerEntity from the database by its ID.
     *
     * @param id The ID of the player to retrieve.
     * @return The PlayerEntity with the given ID.
     * @throws NoSuchElementException If no player with the given ID is found.
     */
    private fun getPlayerById(id: Int) = transaction {
        PlayerEntity.findById(id) ?: throw NoSuchElementException()
    }

    /**
     * Updates the ranking positions of players within a specific ranking.
     *
     * This function retrieves all players associated with the given ranking ID,
     * sorts them by their score in descending order, and then updates their
     * current and previous ranking positions based on their sorted order.
     *
     * @param rankingId The ID of the ranking to update player positions for.
     */
    private fun updatePlayerRankingPositions(rankingId: Int) = transaction {
        val players = PlayerEntity.find { PlayersTable.ranking eq rankingId }
            .sortedByDescending { it.score.toInt() }

        players.forEachIndexed { index, player ->
            player.previousRankingPosition = player.currentRankingPosition
            player.currentRankingPosition = index.plus(1)
        }
    }
}