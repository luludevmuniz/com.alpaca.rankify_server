package com.example.domain.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePlayerDTO(
    val id: Int,
    val name: String,
    val score: String
)