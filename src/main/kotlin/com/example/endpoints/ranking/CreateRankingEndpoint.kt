package com.example.endpoints.ranking

import com.example.db.dao.RankingDao
import com.example.domain.dtos.CreateRankingDTO
import com.example.util.Constants.FAILED_TO_CREATE_RANKING_MESSAGE
import com.example.util.Constants.INVALID_REQUEST_PAYLOAD_MESSAGE
import com.example.util.Constants.NAME_AND_ADMIN_PASSWORD_REQUIRED_MESSAGE
import com.example.util.Constants.RANKING_ENDPOINT
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.SerializationException


/**
 * Creates an endpoint for creating a new ranking.
 *
 * This endpoint handles POST requests to the `/ranking` path.
 * It expects a JSON payload conforming to the `CreateRankingDTO` data class,
 * containing the name and admin password for the new ranking.
 *
 * If the request is successful, it responds with the newly created ranking and an HTTP status code of 201 (Created).
 * If the request payload is invalid or missing required fields, it responds with an HTTP status code of 400 (Bad Request).
 * If there is an internal server error during the ranking creation process, it responds with an HTTP status code of 500 (Internal Server Error).
 *
 * **Request Body:**
 * ```json
 * {
 *   "name": "Ranking Name",
 *   "adminPassword": "Admin Password"
 * }
 * ```
 *
 * **Response Body (Success):**
 * ```json
 * {
 *   "id": 1,
 *   "name": "Ranking Name"
 * }
 * ```
 *
 * **Response Body (Error):**
 * - 400 Bad Request: `"Name and adminPassword are required."` or `"Invalid request payload."`
 * - 500 Internal Server Error: `"Failed to create ranking."`
 */
fun Route.createRankingEndpoint() {
    post(RANKING_ENDPOINT) {
        try {
            val request = call.receive<CreateRankingDTO>()
            if (request.name.isBlank() || request.adminPassword.isBlank()) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = NAME_AND_ADMIN_PASSWORD_REQUIRED_MESSAGE
                )
                return@post
            }
            val newRanking = RankingDao.createRanking(request)
            call.respond(
                status = HttpStatusCode.Created,
                message = newRanking
            )
        } catch (e: SerializationException) {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = e.message ?:  INVALID_REQUEST_PAYLOAD_MESSAGE
            )
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = e.message ?:  FAILED_TO_CREATE_RANKING_MESSAGE
            )
        }
    }
}