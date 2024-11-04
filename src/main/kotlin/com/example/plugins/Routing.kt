package com.example.plugins

import com.example.endpoints.player.createPlayerEndpoint
import com.example.endpoints.player.deletePlayerEndpoint
import com.example.endpoints.ranking.createRankingEndpoint
import com.example.endpoints.ranking.getRankingEndpoint
import com.example.endpoints.player.updatePlayerEndpoint
import com.example.endpoints.ranking.deleteRankingEndpoint
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        createRankingEndpoint()
        getRankingEndpoint()
        deleteRankingEndpoint()
        createPlayerEndpoint()
        updatePlayerEndpoint()
        deletePlayerEndpoint()
    }
}
