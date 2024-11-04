package com.example.db.tables

import com.example.util.Constants.PLAYER_CURRENT_RANKING_POSITION_COLUMN
import com.example.util.Constants.PLAYER_NAME_COLUMN
import com.example.util.Constants.PLAYER_PREVIOUS_RANKING_POSITION_COLUMN
import com.example.util.Constants.PLAYER_SCORE_COLUMN
import com.example.util.Constants.RANKING_TABLE_NAME
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object PlayersTable : IntIdTable() {
    val name = varchar(PLAYER_NAME_COLUMN, 100)
    val currentRankingPosition = integer(PLAYER_CURRENT_RANKING_POSITION_COLUMN).default(0)
    val previousRankingPosition = integer(PLAYER_PREVIOUS_RANKING_POSITION_COLUMN).default(0)
    val score = varchar(PLAYER_SCORE_COLUMN, 100)
    val ranking = reference(
        name = RANKING_TABLE_NAME,
        foreign = RankingsTable,
        onDelete = ReferenceOption.CASCADE
    )
}