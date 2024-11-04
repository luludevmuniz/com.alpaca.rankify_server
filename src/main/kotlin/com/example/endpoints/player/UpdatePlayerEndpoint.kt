package com.example.endpoints.player

import com.example.db.dao.PlayerDao
import com.example.domain.dtos.UpdatePlayerDTO
import com.example.util.Constants.FAILED_TO_UPDATE_PLAYER_MESSAGE
import com.example.util.Constants.PLAYER_ENDPOINT
import com.example.util.Constants.PLAYER_NOT_FOUND_MESSAGE
import com.example.util.Constants.PLAYER_UPDATED_MESSAGE
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.put

/**
 * Endpoint for updating a player's information.
 *
 * This endpoint receives a JSON object representing the updated player data
 * and attempts to update the corresponding player in the database.
 *
 * **Request:**
 * - Method: PUT
 * - Path: /player
 * - Body: JSON object conforming to the `UpdatePlayerDTO` data class.
 *
 * **Response:**
 * - 200 OK: If the player was successfully updated, with a message indicating success.
 * - 404 Not Found: If the player to be updated was not found in the database.
 * - 500 Internal Server Error: If an unexpected error occurred during the update process.
 */
fun Route.updatePlayerEndpoint() {
    put(PLAYER_ENDPOINT) {
        try {
            // Recebe o JSON com as informações do jogador
            val updatedPlayer = call.receive<UpdatePlayerDTO>()
            // Atualiza o jogador no banco de dados
            PlayerDao.updatePlayer(updatedPlayer)
            call.respond(
                status = HttpStatusCode.OK,
                message = PLAYER_UPDATED_MESSAGE
            )
        } catch (e: NoSuchElementException) {
            call.respond(
                status = HttpStatusCode.NotFound,
                message = e.message ?:  PLAYER_NOT_FOUND_MESSAGE
            )
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = e.message ?:  FAILED_TO_UPDATE_PLAYER_MESSAGE
            )
        }
    }
}