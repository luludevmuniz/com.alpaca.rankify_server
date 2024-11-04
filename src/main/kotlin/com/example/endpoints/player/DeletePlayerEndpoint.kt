package com.example.endpoints.player

import com.example.db.dao.PlayerDao
import com.example.util.Constants.FAILED_TO_DELETE_PLAYER_MESSAGE
import com.example.util.Constants.INVALID_ID_MESSAGE
import com.example.util.Constants.PLAYER_DELETED_MESSAGE
import com.example.util.Constants.PLAYER_ENDPOINT
import com.example.util.Constants.PLAYER_NOT_FOUND_MESSAGE
import com.example.util.Constants.QUERY_PARAMETER_ID
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete

/**
 * Endpoint to delete a player by ID.
 *
 * This endpoint handles DELETE requests to the "/player" path with an ID provided
 * as a query parameter named "id".
 *
 * It attempts to delete the player with the given ID from the database.
 *
 * **Success Response (200 OK):**
 *  - Returns a success message indicating the player was deleted.
 *
 * **Error Responses:**
 *  - **400 Bad Request:** If the provided ID is invalid (not an integer).
 *  - **404 Not Found:** If no player with the given ID exists in the database.
 *  - **500 Internal Server Error:** If an unexpected error occurs during the deletion process.
 */
fun Route.deletePlayerEndpoint() {
    delete(PLAYER_ENDPOINT) {
        try {
            val id = call.queryParameters[QUERY_PARAMETER_ID]?.toIntOrNull()
                ?: return@delete call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = INVALID_ID_MESSAGE
                )

            PlayerDao.deletePlayer(id = id)

            call.respond(
                status = HttpStatusCode.OK,
                message = PLAYER_DELETED_MESSAGE
            )
        } catch (e: NoSuchElementException) {
            call.respond(
                status = HttpStatusCode.NotFound,
                message = e.message ?:  PLAYER_NOT_FOUND_MESSAGE
            )
        }  catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = e.message ?:  FAILED_TO_DELETE_PLAYER_MESSAGE
            )
        }
    }
}