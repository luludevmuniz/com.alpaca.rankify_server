package com.example.domain.dtos

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDTO(
    val id: Int = -1,
    val name: String,
    val currentRankingPosition: Int = -1,
    val previousRankingPosition: Int = -1,
    val score: String
)