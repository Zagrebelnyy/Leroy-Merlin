package com.example.demo.repos

import com.example.demo.domain.Player
import org.springframework.data.repository.CrudRepository

interface PlayerRepo : CrudRepository<Player, Long> {
    fun findByName(name: String): List<Player>
}