package com.example.demo.web.controllers

import com.example.demo.domain.Player
import com.example.demo.repository.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("player")
class PlayerController {

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @PostMapping("/name")
    @ResponseBody
    fun create(@RequestBody name: String): Long {
        val players: List<Player> = playerRepository.findByName(name)
        if (players.isNotEmpty()) {
            return players[0].id
        } else {
            val newPlayer = Player(name)
            playerRepository.save(newPlayer)
            return newPlayer.id
        }
    }
}