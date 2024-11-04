package com.example.endpoints.ranking

import com.example.db.dao.RankingDao
import com.example.util.Constants.FAILED_TO_RETRIVE_RANKING_MESSAGE
import com.example.util.Constants.INVALID_ID_MESSAGE
import com.example.util.Constants.QUERY_PARAMETER_ADMIN_PASSWORD
import com.example.util.Constants.QUERY_PARAMETER_ID
import com.example.util.Constants.RANKING_ENDPOINT
import com.example.util.Constants.RANKING_NOT_FOUND_MESSAGE
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

/**
 * This function defines a GET endpoint for retrieving a ranking.
 *
 * It handles requests to the `/ranking` endpoint and attempts to retrieve a ranking based on the provided ID.
 * If the ID is invalid or the ranking is not found, it returns an appropriate error response.
 * If successful, it returns the ranking data.
 *
 * **Parameters:**
 * - `id` (Int): The ID of the ranking to retrieve. Obtained from the `id` query parameter.
 * - `adminPassword` (String? - optional):  The admin password. Obtained from the `adminPassword` query parameter. Used to validate if the request is from an admin.
 *
 * **Responses:**
 * - 200 OK: If the ranking is found, it returns the ranking data in the response body.
 * - 400 Bad Request: If the provided ID is invalid or if the ranking ID does not exist.
 * - 404 Not Found: If the ranking with the given ID is not found. This usually indicates an invalid id that is a valid Int.
 * - 500 Internal Server Error: If an unexpected error occurs during the process.
 *
 * **Example Usage:**
 *  GET /ranking?id=123
 *  GET /ranking?id=123&adminPassword=secret
 */
fun Route.getRankingEndpoint() {
    get(RANKING_ENDPOINT) {
        try {
            val id =
                call.queryParameters[QUERY_PARAMETER_ID]?.toIntOrNull() ?: return@get call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = INVALID_ID_MESSAGE
                )

            val adminPassword = call.queryParameters[QUERY_PARAMETER_ADMIN_PASSWORD]

            val ranking = RankingDao.getRankingDto(id, adminPassword) ?: return@get call.respond(
                status = HttpStatusCode.BadRequest,
                message = RANKING_NOT_FOUND_MESSAGE
            )
            call.respond(
                status = HttpStatusCode.OK,
                message = ranking
            )
        } catch (e: NoSuchElementException) {
            call.respond(
                status = HttpStatusCode.NotFound,
                message = e.message ?:  RANKING_NOT_FOUND_MESSAGE
            )
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = e.message ?:  FAILED_TO_RETRIVE_RANKING_MESSAGE
            )
        }
    }
}
