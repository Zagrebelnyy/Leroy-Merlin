package com.example.demo.repository

import com.example.demo.domain.Game
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface GameRepository : CrudRepository<Game, Long> {
    @Query("SELECT * FROM game WHERE player_one = :id OR player_two = :id", nativeQuery = true)
    fun findAllByPlayerId(id: Long): List<Game>

    @Query(
        "SELECT * FROM game JOIN player " +
                "ON player.id = game.player_one OR player.id = game.player_two " +
                "WHERE player.name = :name", nativeQuery = true
    )
    fun findAllByPlayerName(name: String): List<Game>
}