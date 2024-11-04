package com.example.db.entities

import com.example.db.tables.PlayersTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PlayerEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PlayerEntity>(PlayersTable)

    var name by PlayersTable.name
    var currentRankingPosition by PlayersTable.currentRankingPosition
    var previousRankingPosition by PlayersTable.previousRankingPosition
    var score by PlayersTable.score
    var rankingEntity by RankingEntity referencedOn PlayersTable.ranking
}