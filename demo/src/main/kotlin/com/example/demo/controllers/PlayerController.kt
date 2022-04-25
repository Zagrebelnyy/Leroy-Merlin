package com.example.demo.controllers

import com.example.demo.domain.Player
import com.example.demo.repos.PlayerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("player")
class PlayerController {

    @Autowired
    lateinit var playerRepo: PlayerRepo

    // http://localhost:8080/player/name/Giorgio
    @GetMapping("/name/{name}")
    @ResponseBody
    fun create(@PathVariable name: String): Long {
        val players: List<Player> = playerRepo.findByName(name)
        if (players != null && players.isNotEmpty()) {
            return players[0].id
        } else {
            val newPlayer: Player = Player(name)
            playerRepo.save(newPlayer)
            return newPlayer.id
        }
    }

    // http://localhost:8080/player/first/name/Giorgio
    @GetMapping("/first/name/{name}")
    @ResponseBody
    fun createFirstPlayer(@PathVariable name: String): Player {
        return Player(name)
    }

    @GetMapping("/second/name/{name}")
    @ResponseBody
    fun createSecondPlayer(@PathVariable name: String): Player {
        return Player(name)
    }

}