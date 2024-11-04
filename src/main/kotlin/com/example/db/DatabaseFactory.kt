package com.example.db

import com.example.db.tables.PlayersTable
import com.example.db.tables.RankingsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val database = Database.connect(
            url = "jdbc:postgresql://dpg-cskg329u0jms73bbk490-a.virginia-postgres.render.com:5432/rankify",
            user = "lucas",
            driver = "org.postgresql.Driver",
            password = "vSVyUdbYOcP6uQJBMXhiEYkwoNLFqmXo"
        )
        transaction(database) {
            SchemaUtils.create(RankingsTable, PlayersTable)
        }
    }
}