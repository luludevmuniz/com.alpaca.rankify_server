package com.example.endpoints.player

import com.example.db.dao.PlayerDao
import com.example.domain.dtos.CreatePlayerDTO
import com.example.util.Constants.FAILED_TO_CREATE_PLAYER_MESSAGE
import com.example.util.Constants.PLAYER_ENDPOINT
import com.example.util.Constants.RANKING_NOT_FOUND_MESSAGE
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

/**
 * Creates a new player using a POST request to the `/player` endpoint.
 *
 * This function receives a JSON payload containing the player's information
 * in the form of a `CreatePlayerDTO` object. It then attempts to create a new player
 * record in the database using the `PlayerDao.createPlayer` function.
 *
 * If the player is created successfully, the function responds with a 201 (Created)
 * status code and the ID of the newly created player.
 *
 * If there is an error during the creation process, the function responds with an
 * appropriate error status code and message:
 *  - 404 (Not Found) if a required resource is not found (e.g., the ranking associated with the player).
 *  - 500 (Internal Server Error) for any other unexpected errors.
 */
fun Route.createPlayerEndpoint() {
    post(PLAYER_ENDPOINT) {
        try {
            // Recebe o JSON com as informações do jogador
            val newPlayer = call.receive<CreatePlayerDTO>()
            // Cria o jogador no banco de dados
            val createdPlayer = PlayerDao.createPlayer(player = newPlayer)
            call.respond(
                status = HttpStatusCode.Created,
                message = createdPlayer.id
            )
        } catch (e: NoSuchElementException) {
            call.respond(
                status = HttpStatusCode.NotFound,
                message = e.message ?: RANKING_NOT_FOUND_MESSAGE
            )
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = e.message ?: FAILED_TO_CREATE_PLAYER_MESSAGE
            )
        }
    }
}
