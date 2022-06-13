package com.example.demo.web.controllers

import com.example.demo.domain.Game
import com.example.demo.domain.Player
import com.example.demo.repository.GameRepository
import com.example.demo.repository.PlayerRepository
import com.example.demo.repository.model.dto.NewGameDTO
import com.example.demo.service.GameService
import com.example.demo.service.impl.PingPongTableServiceImpl
import com.example.demo.service.impl.PlayerServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*

@RestController
@RequestMapping("game")
class GameController {
    @Autowired
    lateinit var gameRepository: GameRepository

    @Autowired
    lateinit var playerRepository: PlayerRepository

    @Autowired
    lateinit var gameService: GameService

    @Autowired
    lateinit var pingPongTableService: PingPongTableServiceImpl

    @PostMapping("/play")
    @ResponseBody
    fun create(@RequestBody newGame: NewGameDTO): Game {
        val playerOne = Player(newGame.nameOne)
        val playerTwo = Player(newGame.nameTwo)

        val time = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())

        pingPongTableService = PingPongTableServiceImpl()

        val playerServiceOne = PlayerServiceImpl(
            pingPongTableService.getPlayerOneTablePoints(),
            pingPongTableService.getPlayerOneTablePointsForShouting()
        )
        val playerServiceTwo = PlayerServiceImpl(
            pingPongTableService.getPlayerTwoTablePoints(),
            pingPongTableService.getPlayerTwoTablePointsForShouting()
        )

        var score: String = gameService.run(pingPongTableService, newGame.points, playerServiceOne, playerServiceTwo)

        val game = Game(newGame.points, playerOne, playerTwo, time, score)

        gameRepository.save(game)
        playerRepository.save(playerOne)
        playerRepository.save(playerTwo)

        return game
    }


    @GetMapping("/{id}")
    @ResponseBody
    fun show(@PathVariable id: Long): Any {
        var game = gameRepository.findById(id)
        return game
    }

    @GetMapping("show/{player}")
    @ResponseBody
    fun showGameByPlayer(@PathVariable player: String): List<Game> {
        lateinit var game: List<Game>
        val id = player.toLongOrNull()
        if (id !== null)
            game = gameRepository.findAllByPlayerId(id)
        else
            game = gameRepository.findAllByPlayerName(player)

        return game
    }

}