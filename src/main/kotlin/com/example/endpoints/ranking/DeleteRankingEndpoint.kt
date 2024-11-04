package com.example.endpoints.ranking

import com.example.db.dao.RankingDao
import com.example.util.Constants.FAILED_TO_DELETE_RANKING_MESSAGE
import com.example.util.Constants.INVALID_ID_MESSAGE
import com.example.util.Constants.QUERY_PARAMETER_ID
import com.example.util.Constants.RANKING_DELETED_MESSAGE
import com.example.util.Constants.RANKING_ENDPOINT
import com.example.util.Constants.RANKING_NOT_FOUND_MESSAGE
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete

/**
 * Defines the DELETE endpoint for deleting a ranking by ID.
 *
 * This endpoint expects an ID to be provided as a query parameter named "id".
 * If the ID is valid and the ranking exists, it will be deleted and a success message will be returned.
 *
 * **HTTP Request:** DELETE /ranking?id={id}
 *
 * **Possible Responses:**
 * - 200 OK: Ranking successfully deleted (RANKING_DELETED_MESSAGE)
 * - 404 Not Found: Ranking not found (RANKING_NOT_FOUND_MESSAGE)
 * - 500 Internal Server Error: Failed to delete ranking (FAILED_TO_DELETE_RANKING_MESSAGE)
 */
fun Route.deleteRankingEndpoint() {
    delete(RANKING_ENDPOINT) {
        try {
            val id = call.queryParameters[QUERY_PARAMETER_ID]?.toIntOrNull()
                ?: return@delete call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = INVALID_ID_MESSAGE
                )

            RankingDao.deleteRanking(id = id)
            call.respond(
                status = HttpStatusCode.OK,
                message = RANKING_DELETED_MESSAGE
            )
        } catch (e: NoSuchElementException) {
            call.respond(
                status = HttpStatusCode.NotFound,
                message = e.message ?:  RANKING_NOT_FOUND_MESSAGE
            )
        } catch (e: Exception) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = e.message ?:  FAILED_TO_DELETE_RANKING_MESSAGE
            )
        }
    }
}