package com.example.db.dao

import com.example.db.entities.RankingEntity
import com.example.db.tables.RankingsTable
import com.example.domain.dtos.CreateRankingDTO
import com.example.domain.dtos.RankingDTO
import com.example.domain.mappers.toDto
import org.jetbrains.exposed.sql.transactions.transaction

object RankingDao {
    /**
     * Creates a new ranking with the given information.
     *
     * This function creates a new ranking entity in the database, and optionally creates associated players if provided.
     * It then returns a DTO representing the newly created ranking.
     *
     * @param ranking The data transfer object containing the ranking information.
     * @return A DTO representing the newly created ranking.
     * @throws NoSuchElementException If the ranking cannot be found after creation.
     */
    fun createRanking(ranking: CreateRankingDTO): RankingDTO = transaction {
        val createdRanking = RankingEntity.new {
            name = ranking.name
            adminPassword = ranking.adminPassword
        }
        if (ranking.players.isNullOrEmpty()) return@transaction createdRanking.toDto()
        else
            PlayerDao.createPlayers(
                players = ranking.players,
                rankingId = createdRanking.id.value
            )
        getRankingDto(
            id = createdRanking.id.value,
            adminPassword = ranking.adminPassword
        ) ?: throw NoSuchElementException()
    }

    fun getRankingDto(
        id: Int,
        adminPassword: String? = null
    ): RankingDTO? = transaction {
        val ranking = RankingEntity.find {
            RankingsTable.id eq id
        }.singleOrNull() ?: return@transaction null

        // Verifica se a senha de administração foi fornecida e se é correta
        val isAdmin = adminPassword != null && adminPassword == ranking.adminPassword

        ranking.toDto(isAdmin)
    }

    /**
     * Retrieves a RankingEntity from the database by its ID.
     *
     * @param rankingId The ID of the ranking to retrieve.
     * @return The RankingEntity with the specified ID.
     * @throws NoSuchElementException If no ranking with the given ID is found.
     */
    fun getRankingEntity(
        rankingId: Int
    ): RankingEntity = transaction {
        RankingEntity.findById(id = rankingId) ?: throw NoSuchElementException()
    }

    /**
     * Deletes a ranking by its ID.
     *
     * @param id The ID of the ranking to delete.
     * @throws NoSuchElementException If a ranking with the given ID is not found.
     */
    fun deleteRanking(id: Int) = transaction {
        RankingEntity.findById(id)?.delete() ?: throw NoSuchElementException()
    }
}