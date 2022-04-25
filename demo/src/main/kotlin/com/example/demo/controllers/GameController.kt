package com.example.demo.controllers

import com.example.demo.domain.Game
import com.example.demo.domain.Player
import com.example.demo.repos.GameRepo
import com.example.demo.repos.PlayerRepo
import com.example.demo.service.GameService
import com.example.demo.service.PlayerService
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
    lateinit var gameRepo: GameRepo

    @Autowired
    lateinit var playerRepo: PlayerRepo

    @Autowired
    lateinit var gameService: GameService

    @Autowired
    lateinit var pingPongTableService: PingPongTableServiceImpl


    // Запуск игры через rest-api, задавая имена игроков и максимальное количество очков
    // http://localhost:8080/game/11/players/first/Alex/second/Osip
    @GetMapping("/{points}/players/first/{name1}/second/{name2}")
    @ResponseBody
    fun create(@PathVariable points: Int, @PathVariable name1: String, @PathVariable name2: String): Game {
        val playerOne = Player(name1)
        val playerTwo = Player(name2)

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

        gameService.run(pingPongTableService, points, playerServiceOne, playerServiceTwo)

        val game = Game(points, playerOne, playerTwo, time)

        playerRepo.save(playerOne)
        playerRepo.save(playerTwo)
        gameRepo.save(game)

        return game
    }


    //Эндпоинт, по которому можно получить данные игры по id
    //http://localhost:8080/game/11
    @GetMapping("/{id}")
    @ResponseBody
    fun show(@PathVariable id: Long): Any {
        var game = gameRepo.findById(id)
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
            game = gameRepo.findAllByPlayerId(id)
        else
            game = gameRepo.findAllByPlayerName(player)

        return game
    }

}