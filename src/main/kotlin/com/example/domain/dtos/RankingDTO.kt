package com.example.domain.dtos

import com.typesafe.config.Optional
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class RankingDTO(
    @Optional val id: Int,
    val name: String,
    val lastUpdated: LocalDateTime? = null,
    val players: List<PlayerDTO> = emptyList(),
    val isAdmin: Boolean = false,
    val adminPassword: String = ""
)
