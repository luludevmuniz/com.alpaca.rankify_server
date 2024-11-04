package com.example.db.entities

import com.example.db.tables.PlayersTable
import com.example.db.tables.RankingsTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable

class RankingEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RankingEntity>(RankingsTable)

    var name by RankingsTable.name
    var lastUpdated by RankingsTable.lastUpdated
    var adminPassword by RankingsTable.adminPassword
    val players: SizedIterable<PlayerEntity> by PlayerEntity referrersOn PlayersTable.ranking
}
