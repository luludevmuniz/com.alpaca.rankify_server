package com.example.util

object Constants {
    //Endpoints
    const val RANKING_ENDPOINT = "/ranking"
    const val PLAYER_ENDPOINT = "/player"
    //Endpoints - Parameters
    const val QUERY_PARAMETER_ID = "id"
    const val QUERY_PARAMETER_ADMIN_PASSWORD = "adminPassword"
    const val INVALID_ID_MESSAGE = "Invalid ID"
    //Endpoints - Response Messages - Success
    const val PLAYER_DELETED_MESSAGE = "Player deleted successfully"
    const val PLAYER_UPDATED_MESSAGE = "Player updated successfully"
    const val RANKING_DELETED_MESSAGE = "Ranking deleted successfully"
    //Endpoints - Response Messages - Failure
    const val RANKING_NOT_FOUND_MESSAGE = "Ranking not found"
    const val PLAYER_NOT_FOUND_MESSAGE = "Player not found"
    const val FAILED_TO_RETRIVE_RANKING_MESSAGE = "Failed to retrieve ranking"
    const val FAILED_TO_DELETE_RANKING_MESSAGE = "Failed to delete ranking"
    const val FAILED_TO_CREATE_RANKING_MESSAGE = "Failed to create ranking"
    const val NAME_AND_ADMIN_PASSWORD_REQUIRED_MESSAGE = "Name and admin password are required"
    const val INVALID_REQUEST_PAYLOAD_MESSAGE = "Invalid request payload"
    const val FAILED_TO_UPDATE_PLAYER_MESSAGE = "Failed to update player"
    const val FAILED_TO_CREATE_PLAYER_MESSAGE = "Failed to create player"
    const val FAILED_TO_DELETE_PLAYER_MESSAGE = "Failed to delete player"
    //Database - Tables - Rankings - Columns
    const val RANKING_TABLE_NAME = "ranking"
    const val RANKING_NAME_COLUMN = "name"
    const val RANKING_ADMIN_PASSWORD_COLUMN = "admin_password"
    const val RANKING_LAST_UPDATED_COLUMN = "last_updated"
    //Database - Tables - Players - Columns
    const val PLAYER_NAME_COLUMN = "name"
    const val PLAYER_CURRENT_RANKING_POSITION_COLUMN = "current_ranking_position"
    const val PLAYER_PREVIOUS_RANKING_POSITION_COLUMN = "previous_ranking_position"
    const val PLAYER_SCORE_COLUMN = "score"

}