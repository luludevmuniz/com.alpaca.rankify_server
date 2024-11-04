package com.example.domain.mappers

import com.example.db.entities.PlayerEntity
import com.example.db.entities.RankingEntity
import com.example.domain.dtos.PlayerDTO
import com.example.domain.dtos.RankingDTO
import org.jetbrains.exposed.sql.SizedIterable

fun RankingEntity.toDto(isAdmin: Boolean = false): RankingDTO = RankingDTO(
    id = id.value,
    name = name,
    lastUpdated = lastUpdated,
    players = players.toDto(),
    isAdmin = isAdmin
)

fun PlayerEntity.toDto(): PlayerDTO = PlayerDTO(
    id = id.value,
    name = name,
    currentRankingPosition = currentRankingPosition,
    previousRankingPosition = previousRankingPosition,
    score = score
)

fun SizedIterable<PlayerEntity>.toDto() = map { player -> player.toDto() }
