package com.example.demo.repository

import com.example.demo.domain.Game
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface GameRepository : CrudRepository<Game, Long> {
    @Query("SELECT * FROM game WHERE first_player_id = :id OR second_player_id = :id", nativeQuery = true)
    fun findAllByPlayerId(id: Long): List<Game>

    @Query("SELECT * FROM game JOIN player " +
            "ON player.id = game.first_player_id OR player.id = game.second_player_id " +
            "WHERE player.name = :name", nativeQuery = true)
    fun findAllByPlayerName(name: String): List<Game>
}