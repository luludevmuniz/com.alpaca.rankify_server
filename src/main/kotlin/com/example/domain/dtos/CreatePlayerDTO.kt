package com.example.domain.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CreatePlayerDTO(
    val name: String,
    val score: String,
    val remoteRankingId: Int? = null
)
