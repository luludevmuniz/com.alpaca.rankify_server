package com.example.domain.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CreateRankingDTO(
    val name: String,
    val adminPassword: String,
    val players: List<CreatePlayerDTO>? = emptyList()
)