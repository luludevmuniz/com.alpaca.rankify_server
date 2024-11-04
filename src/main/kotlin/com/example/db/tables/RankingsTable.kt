package com.example.db.tables

import com.example.util.Constants.RANKING_ADMIN_PASSWORD_COLUMN
import com.example.util.Constants.RANKING_LAST_UPDATED_COLUMN
import com.example.util.Constants.RANKING_NAME_COLUMN
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object RankingsTable : IntIdTable() {
    val name = varchar(RANKING_NAME_COLUMN, 100)
    val lastUpdated = datetime(RANKING_LAST_UPDATED_COLUMN).default(
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    )
    val adminPassword = varchar(RANKING_ADMIN_PASSWORD_COLUMN, 100)
}