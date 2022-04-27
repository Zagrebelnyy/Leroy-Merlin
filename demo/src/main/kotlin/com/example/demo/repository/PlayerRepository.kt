package com.example.demo.repository

import com.example.demo.domain.Player
import org.springframework.data.repository.CrudRepository

interface PlayerRepository : CrudRepository<Player, Long> {
    fun findByName(name: String): List<Player>
}