package com.example.demo.web.controllers

import com.example.demo.domain.Game
import com.example.demo.domain.Player
import com.example.demo.repository.GameRepository
import com.example.demo.repository.PlayerRepository
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


    // Запуск игры через rest-api, задавая имена игроков и максимальное количество очков
    // http://localhost:8080/play
    @PostMapping("/play")
    @ResponseBody
    fun create(@RequestBody newGame: Map<String, String>): Game {
        val playerOne = Player(newGame.get("nameOne")!!)
        val playerTwo = Player(newGame.get("nameTwo")!!)

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

        var score: String = gameService.run(pingPongTableService, newGame.get("points")!!.toInt(), playerServiceOne, playerServiceTwo)

        val game = Game(newGame.get("points")!!.toInt(), playerOne, playerTwo, time, score)

        playerRepository.save(playerOne)
        playerRepository.save(playerTwo)
        gameRepository.save(game)

        return game
    }


    //Эндпоинт, по которому можно получить данные игры по id
    //http://localhost:8080/game/11
    @GetMapping("/{id}")
    @ResponseBody
    fun show(@PathVariable id: Long): Any {
        var game = gameRepository.findById(id)
        return game
    }

    //Эндпоинт, по которому можно получить данные предыдущих игр по id игрока или по фамилии
    //http://localhost:8080/game/show/Oleg
    //http://localhost:8080/game/show/11
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